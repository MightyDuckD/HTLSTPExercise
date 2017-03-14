/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whitening;

import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Simon
 */
public class AESVerfahren {

    private static byte[] key = "MZygpewJsCpRrfOr".getBytes(StandardCharsets.UTF_8);

    public static void main(String[] args) throws Exception {
        
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] plainText = Data.loremParagraph1.getBytes();

        byte[] result = cipher.doFinal(plainText);
        for (int i = 0; i < plainText.length; i++) {
            System.out.printf("%d\t%d\t%d\n", i+1, plainText[i], result[i] & 0xFF);
        }
        System.out.println(key.length);
    }
}
