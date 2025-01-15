package project.board.auth.enc;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AES256Enc {

    private static String key = SecretKey.generateEncryptionKey();
    private static String iv = IVKey.generateIVKey();


    public static String encrypt(String password){
        try{
            byte[] decodedKey = Base64.getDecoder().decode(key);
            byte[] decodedIV = Base64.getDecoder().decode(iv);

            SecretKeySpec secretKeySpec = new SecretKeySpec(decodedKey, "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(decodedIV);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

            byte[] encryptedBytes = cipher.doFinal(password.getBytes());

            return Base64.getEncoder().encodeToString(encryptedBytes);

        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public static String decrypt(String encryptedPassword){
        try{
            byte[] decodedKey = Base64.getDecoder().decode(key);
            byte[] decodedIV = Base64.getDecoder().decode(iv);
            byte[] decodedEncryptedPassword = Base64.getDecoder().decode(encryptedPassword);

            SecretKeySpec secretKeySpec = new SecretKeySpec(decodedKey, "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(decodedIV);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

            byte[] decrtpytedBytes = cipher.doFinal(decodedEncryptedPassword);

            return new String(decrtpytedBytes);
        }catch( Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
