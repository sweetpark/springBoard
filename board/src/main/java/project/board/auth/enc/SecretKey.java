package project.board.auth.enc;

import java.security.SecureRandom;
import java.util.Base64;

public class SecretKey {

    public static String generateEncryptionKey(){
        byte[] key = new byte[32];
        new SecureRandom().nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }
}
