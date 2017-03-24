
import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
/**
 *
 * @author Simon Lehner-Dittenberger
 */
public class AESVerfahren {

    public static byte[] cryptit(int ciphermode, char[] password, byte[] salt, byte[] iv, byte[] data) throws Exception {
        /* Derive the key, given password and salt. */
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password, salt, 65536, 128);
        SecretKey tmp = factory.generateSecret(spec);
        SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
        
        /* Encrypt the message. */
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(ciphermode, secret, new IvParameterSpec(iv));
        AlgorithmParameters params = cipher.getParameters();
        return cipher.doFinal(data);
    }

    public static void main(String[] args) throws Exception {

        byte[] test;

        SecureRandom rand = new SecureRandom();
        test = new byte[16];
        rand.nextBytes(test);

        test = Base64.getEncoder().encode(test);
        String t = new String(test);

        char password[] = "asdfg12345".toCharArray();
        byte salt[] = Base64.getDecoder().decode("TfK0IAP2QP7Y+xTGQ3wJpQ==");
        byte iv[] = Base64.getDecoder().decode("hsYhWSqQRS8+sAb8Jxl2Tw==");
        byte plain[] =Data.loremParagraph1.getBytes();
        byte encrypted[] = null;
        byte decrypted[] = null;
        
        encrypted = cryptit(Cipher.ENCRYPT_MODE, password, salt, iv, plain);
        decrypted = cryptit(Cipher.DECRYPT_MODE, password, salt, iv, encrypted);
		
        assert(Arrays.equals(decrypted, plain));
        
        for(int i = 0; i< 460; i++) {
            System.out.printf("%d\t%d\t%d\n",i+1,decrypted[i]&0xFF,encrypted[i]&0xFF);
        }

    }
}
