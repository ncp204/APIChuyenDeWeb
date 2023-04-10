package vn.edu.hcmuaf.st.chuyendeweb.hash;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Primary
@Component
public class MD5 implements Hashing{
    public static String MD5 = "MD5";

    @Override
    public String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance(MD5);
            byte[] messageDigest = md.digest(password.getBytes());
            return byteToHex(messageDigest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean validatePassword(String originalPassword, String storedPassword) {
        return false;
    }

    // convert byte sang hex
    public static String byteToHex(byte[] data) {
        BigInteger number = new BigInteger(1, data);
        String hashtext = number.toString(16);
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        return hashtext;
    }
}
