/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.mightyduck.lehnerreisenconsole.util;

import at.mightyduck.lehnerreisenconsole.model.Benutzer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 *
 * @author Simon
 */
public class UserUtil {

    private static MessageDigest digest;
    private static SecureRandom random = new SecureRandom();//TODO: check if seed needed

    static {
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException ex) {
            digest = null;
        }
    }

    public static boolean authenticateUser(Benutzer user, String subittedEmail, String submittedPassword) {
        String dbEmail = user != null ? user.getEmail() : null;
        String salt = user != null ? user.getSalt() : "";
        String dbPassword = user != null ? user.getPasswort() : null;

        //Add salt to submitted password and hash it with sha256
        String saltedPassword = submittedPassword + salt;
        String hashedPassword = sha256(saltedPassword);

        System.out.println(user + " " + hashedPassword + " " + dbPassword);

        //check if password matches the db password
        if (user != null && subittedEmail.equals(dbEmail) && hashedPassword.equals(dbPassword)) {
            return true;
        }
        return false;
    }

    public static String generateSalt() {
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        return byteArrayToHex(bytes);
    }

    /**
     * Creates a new user with email = email and password =
     * sha256(sha256(email+password) + salt). Salt is generated via the
     * generateSalt() method.
     *
     * @return
     */
    public static Benutzer createUser(String email, String password) {
        String submittedPassword = sha256(email + password);
        String salt = generateSalt();
        return new Benutzer(email,sha256(submittedPassword + salt),salt);
    }

    public static String sha256(String str) {
        byte[] hash = digest.digest(str.getBytes(StandardCharsets.UTF_8));
        return byteArrayToHex(hash);
    }

    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for (byte b : a) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }
}
