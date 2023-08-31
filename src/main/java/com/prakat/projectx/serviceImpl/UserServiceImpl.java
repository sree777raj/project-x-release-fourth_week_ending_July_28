package com.prakat.projectx.serviceImpl;

import com.prakat.projectx.dto.AuthenticationRequest;
import com.prakat.projectx.dto.AuthenticationResponse;
import com.prakat.projectx.dto.EmailDTO;
import com.prakat.projectx.dto.OTPsealedobject;
import com.prakat.projectx.dto.ResetPasswordDto;
import com.prakat.projectx.dto.UserDto;
import com.prakat.projectx.email.EmailClient;
import com.prakat.projectx.entity.User;
import com.prakat.projectx.enums.PasswordStatus;
import com.prakat.projectx.exception.CustomException;
import com.prakat.projectx.repo.UserRepository;
import com.prakat.projectx.service.UserService;
import com.prakat.projectx.utils.AESUtil;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private  UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private EmailClient emailClient;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private   ModelMapper modelMapper;
    @Autowired
    private AuthenticationServiceImpl authenticationServiceImpl;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Generate a secret key and initialization vector (IV)
    //SecretKey key = new SecretKeySpec("0123456789abcdef".getBytes(), "AES");
    SecretKey key=new SecretKeySpec("MfrMxlLZjAVd5dKziGw3oEQ315b5Qs6b".getBytes(),"AES");
    String ivs="uSïÅ£äUH˜+?#æ=]Ý";
    //IvParameterSpec iv = new IvParameterSpec("0123456789abcdef".getBytes());
    IvParameterSpec iv = new IvParameterSpec("9U2kpZVbt9jLjASI".getBytes());

    private static final int SECRET_CODE_LENGTH = 16;
    public static String generateSecretCode(){
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(SECRET_CODE_LENGTH);
        for(int i=0;i < SECRET_CODE_LENGTH; i++){
            int randomIndex = new Random().nextInt(characters.length());
            sb.append(characters.charAt(randomIndex));
        }
        return sb.toString();
    }
    public void updateSecretCode(String userEmail, String newSecretCode) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException("User not found", 404));
        user.setSecretCode(newSecretCode);
        userRepository.save(user);
    }
@Override
    public UserDto createUser(UserDto userDto) {
        // Perform any necessary validation or business logic before saving the user
        // For example, you can check if the email is unique or enforce password requirements
     String secretCode = generateSecretCode();
        userDto.setSecretCode(secretCode);
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User savedUser = modelMapper.map(userDto,User.class);
        return modelMapper.map(userRepository.save(savedUser),UserDto.class);

    }
@Override
    public UserDto updateUser(UserDto userDto){
        User user=modelMapper.map(userDto,User.class);
        User savedUpdate=userRepository.save(user);
        return modelMapper.map(savedUpdate,UserDto.class);
    }
@Override
    public UserDto getByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new CustomException("User not found with email: " + email + "", 400));
        return modelMapper.map(user, UserDto.class);
    }
    @Override
        public User getUserById(Integer id) {
        if (userRepository == null) {
            throw new IllegalStateException("UserTempRepository null.");
        }

        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);

    }

    /**
     * validate user,passwordStatus
     * And send OTP if user matches the checks
     * @param forgotEmail
     * @return
     * @throws Exception
     */
    @Override
    public String forgotPassword(String forgotEmail) throws CustomException {
        //Check user exist?
        User user=userRepository.findByEmail(forgotEmail).orElseThrow(()->
                new CustomException("User Not Found",404));

        //check is passwordStatus Blocked?
        if (user.getPasswordStatus().equals(PasswordStatus.BLOCKED))
            throw new CustomException("User is already blocked",400);

        //Check Isn't passwordStatus resetInitiated and OTP attempts more than 5
        if (user.getPasswordStatus().equals(PasswordStatus.RESET_INITIATED) && (user.getOtpAttempts()>5)){

            //Block the user and send mail to Admin
            user.setPasswordStatus(PasswordStatus.BLOCKED);
            userRepository.save(user);
            EmailDTO email = new EmailDTO();
            email.setTo("michael.f@prakat.com");
            String userName = user.getFirstname();
            email.setSubject(""+userName+ "Blocked due to OTP attempts exceeded");
            email.setTemplate("/email/adminTemplate.ftlh");

            // Populate the template data
            Map<String, Object> templateData = new HashMap<>();
            templateData.put("name", userName);
            email.setEmailData(templateData);
            if(!emailClient.sendEmail(email)){
                logger.warn("Admin email failed for :"+userName+" exceeds OTP attempts!! ");
            }
            throw new CustomException(""+userName+" Blocked due OTP attempts exceeded !! ",400);

        }
           //Generate OTP
           //All conditions passed generate OTP
            Integer otp = new Random().nextInt(900000) + 100000;
            user.setOtp(otp);
            logger.info("OTP is: " + otp);

            user.setPasswordStatus(PasswordStatus.RESET_INITIATED);
            user.setOtpAttempts((user.getOtpAttempts())+1);
            userRepository.save(user);

            //set emailDto object
            EmailDTO email = new EmailDTO();
            email.setTo(forgotEmail);
            String userName = user.getFirstname();
            email.setSubject("OTP for password reset");
            email.setTemplate("/email/OTPtemplate.ftlh");

            // Populate the template data
            Map<String, Object> templateData = new HashMap<>();
            templateData.put("otp", otp.toString());
            templateData.put("name", userName);
            email.setEmailData(templateData);
            if (!emailClient.sendEmail(email)) {
                throw new CustomException("Failed to send the email",400);
            }

            //Set sealedObject
            OTPsealedobject otpSealedObject = new OTPsealedobject();
            otpSealedObject.setOtp(otp);
            otpSealedObject.setUserEmail(forgotEmail);
            otpSealedObject.setTimestamp(new Date(System.currentTimeMillis()+5*60*1000));
            logger.info(String.valueOf(otpSealedObject));
            SealedObject sealedObject = null;
            try {
             sealedObject= AESUtil.encryptObject
                    ("AES/CBC/PKCS5Padding",(Serializable) otpSealedObject,key,iv);
            } catch(Exception e) {
              logger.error("Error encryptiong object:",e);
              throw new CustomException("Internal Server Error",500);
            }              
            return AESUtil.serializeToBase64(sealedObject);
        }

    /**
     * Check expiration time,user exist,password status and otp match
     * Once all checks satisfied, user will reset password
     * @param resetPasswordDto
     * @return
     * @throws Exception
     */
@Override
    public AuthenticationResponse resetPassword(ResetPasswordDto resetPasswordDto) throws CustomException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, IOException, InvalidKeyException, ClassNotFoundException {

        SealedObject so = (SealedObject) AESUtil.derializeFromBase64(resetPasswordDto.getOtpsealedobject());
        OTPsealedobject decryptedObject = new OTPsealedobject();
        try {
            decryptedObject =(OTPsealedobject)AESUtil.decryptObject
                ("AES/CBC/PKCS5Padding",so,key,iv);            
        } catch(Exception e) {
              logger.error("Error decrypting object:",e);
              throw new CustomException("Internal Server Error",500);
        }
        
        //Check expiration time
        Date currentTime=new Date(System.currentTimeMillis());

        Date expiration=decryptedObject.getTimestamp();


        if(currentTime.after(expiration))throw new CustomException("OTP is expired",404);

        Integer otpFromSealedObject=decryptedObject.getOtp();

        //Check IsOtp invalid
        if (!otpFromSealedObject.equals(resetPasswordDto.getOtp()))
        {throw new CustomException("OTP is invalid",400);}

        //User exists? and password_status check
        Optional<User> user=userRepository.findByEmail(decryptedObject.getUserEmail());
        if (!user.isPresent() || (!user.get().getPasswordStatus().equals(PasswordStatus.RESET_INITIATED)) ) {
            EmailDTO email = new EmailDTO();
            email.setTo("michael.f@prakat.com");
            String userEmail = decryptedObject.getUserEmail();
            email.setSubject("CAUTION: Security Breach at reset password process.");
            email.setTemplate("/email/securityBreachTemplate.ftlh");

            // Populate the template data
            Map<String, Object> templateData = new HashMap<>();
            templateData.put("email", userEmail);
            email.setEmailData(templateData);
            if (!emailClient.sendEmail(email)){
                logger.warn("OTP Security breach unable to send email admin!! ");
            }
            throw new CustomException("User not found or Invalid user state",400);
        }

       //send encrypted password and noOfAttempts and ALl conditions passed

       user.get().setPassword(passwordEncoder.encode(resetPasswordDto.getNewPassword()));
       user.get().setOtpAttempts(0);
       user.get().setPasswordStatus(PasswordStatus.RESET_DONE);
       User savedUser=userRepository.save(user.get());
       AuthenticationRequest authenticationRequest=new AuthenticationRequest();
       authenticationRequest.setEmail(savedUser.getEmail());
       authenticationRequest.setPassword(resetPasswordDto.getNewPassword());
       return authenticationServiceImpl.authenticate(authenticationRequest);
    }
}


