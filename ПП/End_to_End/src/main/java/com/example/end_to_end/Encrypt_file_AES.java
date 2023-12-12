package com.example.end_to_end;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Encrypt_file_AES {
    private static void getKey(){
        SecretKeySpec secretKey = new SecretKeySpec(ConstantVariable.PASSWORD.getBytes(), "AES");
        ConstantVariable.key = secretKey;
    }
    public static byte[] encrypt(byte[] input) throws Exception {
        getKey();
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, ConstantVariable.key);
        return cipher.doFinal(input);
    }

    public static byte[] decrypt(byte[] input) throws Exception {
        getKey();
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, ConstantVariable.key);
        return cipher.doFinal(input);
    }
}
