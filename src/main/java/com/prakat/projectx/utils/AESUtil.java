package com.prakat.projectx.utils;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.prakat.projectx.exception.CustomException;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.Base64;

/**
 * The AESUtil class provides utility methods for encryption and decryption using AES algorithm.(e.g.Triple DES,RSA Security,Blowfish,Twofish)
 * For more information on supported list of Algorithms refer Javadoc for javax.crypto.Cipher.
 */
public class AESUtil  {

    private static final Logger logger = LogManager.getLogger(AESUtil.class);
    /**
     * Generates an initialization vector (IV) for AES encryption.
     *
     * @return IvParameterSpec object representing the generated IV.
     */
    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    /**
     * Encrypts a Serializable object using the specified algorithm, secret key, and initialization vector (IV).
     *
     * @param algorithm  the name of the encryption algorithm, e.g., "AES/CBC/PKCS5Padding".
     * @param object     the Serializable object to be encrypted.
     * @param key        the secret key used for encryption.
     * @param iv         the initialization vector (IV) used for encryption.
     * @return SealedObject representing the encrypted object.
     * @throws NoSuchPaddingException              if the requested padding scheme is not available.
     * @throws NoSuchAlgorithmException            if the requested algorithm is not available.
     * @throws InvalidAlgorithmParameterException  if the provided algorithm parameters are invalid.
     * @throws InvalidKeyException                 if the provided secret key is invalid.
     * @throws IOException                         if an I/O error occurs during encryption.
     * @throws IllegalBlockSizeException           if the object cannot be sealed due to its size.
     */
    public static SealedObject encryptObject(String algorithm, Serializable object,
                                             SecretKey key, IvParameterSpec iv) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            InvalidKeyException, IOException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        SealedObject sealedObject = new SealedObject(object, cipher);
        return sealedObject;
    }

    /**
     * Decrypts a SealedObject using the specified algorithm, secret key, and initialization vector (IV).
     *
     * @param algorithm  the name of the encryption algorithm, e.g., "AES/CBC/PKCS5Padding".
     * @param sealedObject  the SealedObject to be decrypted.
     * @param key        the secret key used for decryption.
     * @param iv         the initialization vector (IV) used for decryption.
     * @return Serializable object representing the decrypted data.
     * @throws NoSuchPaddingException              if the requested padding scheme is not available.
     * @throws NoSuchAlgorithmException            if the requested algorithm is not available.
     * @throws InvalidAlgorithmParameterException  if the provided algorithm parameters are invalid.
     * @throws InvalidKeyException                 if the provided secret key is invalid.
     * @throws ClassNotFoundException              if the class of the serialized object cannot be found.
     * @throws BadPaddingException                 if the decryption padding is invalid.
     * @throws IllegalBlockSizeException           if the object cannot be unsealed due to its size.
     * @throws IOException                         if an I/O error occurs during decryption.
     */
    public static Serializable decryptObject(String algorithm, SealedObject sealedObject,
                                             SecretKey key, IvParameterSpec iv) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException,
            ClassNotFoundException, BadPaddingException, IllegalBlockSizeException,
            IOException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        Serializable unsealObject = (Serializable) sealedObject.getObject(cipher);
        return unsealObject;
    }
    
    public static String serializeToBase64(Serializable obj) throws CustomException {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      ObjectOutputStream oos = null;
      try {
        oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.flush();
      } catch (Exception e) {
        logger.error("Error Serializing object:", e);
        throw new CustomException("Error Serializing object:", 500);
      } finally {
          try {
            bos.close();
          } catch (IOException ex) {
              // ignore close exception
          }
      }     
      byte[] data = bos.toByteArray();
      return Base64.getEncoder().encodeToString(data);
    }

    public static Serializable  derializeFromBase64(String b64Str) throws CustomException {
      byte[] data = Base64.getDecoder().decode(b64Str);
      ByteArrayInputStream bis = new ByteArrayInputStream(data);
      ObjectInput in = null; Object out = null; 
      try {
        in  = new ObjectInputStream(bis);
        out = in.readObject();
      } catch (Exception e) {
        logger.error("Error Deserilizing object:", e);
        throw new CustomException("Error Deserilizing object:", 500);
      } finally {        
          try {
            if (in != null) {
              in.close();
            }
          } catch (IOException ex) {
            // ignore close exception
          }
      }  
      return (Serializable) out;
    }   
}
