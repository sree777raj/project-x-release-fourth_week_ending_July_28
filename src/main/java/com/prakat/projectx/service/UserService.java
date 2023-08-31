package com.prakat.projectx.service;

import com.prakat.projectx.dto.AuthenticationResponse;
import com.prakat.projectx.dto.ResetPasswordDto;
import com.prakat.projectx.dto.UserDto;
import com.prakat.projectx.entity.User;
import com.prakat.projectx.exception.CustomException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto);

    UserDto getByEmail(String email);

    User getUserById(Integer id);

    String forgotPassword(String forgotEmail);

    AuthenticationResponse resetPassword(ResetPasswordDto resetPasswordDto) throws CustomException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, IOException, InvalidKeyException, ClassNotFoundException;
}
