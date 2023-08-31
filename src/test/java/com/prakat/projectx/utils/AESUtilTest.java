package com.prakat.projectx.utils;

import org.junit.jupiter.api.Test;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


import static org.junit.jupiter.api.Assertions.*;

public class AESUtilTest {

    @Test
    public void testGenerateIv() {
        IvParameterSpec ivParameterSpec = AESUtil.generateIv();

        assertNotNull(ivParameterSpec);
        assertEquals(16, ivParameterSpec.getIV().length);
    }

    @Test
    public void testEncryptAndDecryptObject() throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException, IOException,
            IllegalBlockSizeException, ClassNotFoundException, BadPaddingException {

        // Generate a secret key and initialization vector (IV)
        SecretKey key = new SecretKeySpec("0123456789abcdef".getBytes(), "AES");
        IvParameterSpec iv = new IvParameterSpec("0123456789abcdef".getBytes());

        // Create an object to encrypt
        SampleObject originalObject = new SampleObject("Hello, World!");

        // Encrypt the object
        SealedObject sealedObject = AESUtil.encryptObject("AES/CBC/PKCS5Padding", originalObject, key, iv);

        // Decrypt the sealed object
        Serializable decryptedObject = AESUtil.decryptObject("AES/CBC/PKCS5Padding", sealedObject, key, iv);

        // Verify that the decrypted object matches the original object
        assertTrue(decryptedObject instanceof SampleObject);
        SampleObject decryptedSampleObject = (SampleObject) decryptedObject;
        assertEquals(originalObject.getMessage(), decryptedSampleObject.getMessage());
    }

    private static class SampleObject implements Serializable {
        private final String message;

        public SampleObject(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }}
