package com.pllug.course.ivankiv.passmanager.utils;

import android.util.Base64;

import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;

public class Crypto {

    public String encryptedString(String password, String message) {
        String encryptedStr = "";
        try {
            encryptedStr = AESCrypt.encrypt(password, message);
        }catch (GeneralSecurityException e){
            //handle error
        }

        return encryptedStr;
    }

    public String decryptString(String password, String encryptedString) {
        String messageAfterDecrypt = "";

        try {
            messageAfterDecrypt = AESCrypt.decrypt(password, encryptedString);
        }catch (GeneralSecurityException e){
            //handle error - could be due to incorrect password or tampered encryptedMsg
            String s = e.getMessage();
        }

        return messageAfterDecrypt;
    }

    public static String keyGenerator() {
        KeyGenerator keyGenerator = null;
        try {
            keyGenerator = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        keyGenerator.init(128);

        return Base64.encodeToString(keyGenerator.generateKey().getEncoded(),
                Base64.DEFAULT);
    }
}
