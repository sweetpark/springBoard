package project.board.common.auth.enc;

import java.security.SecureRandom;
import java.util.Base64;

public class IVKey {
    public static String generateIVKey(){
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);

        return Base64.getEncoder().encodeToString(iv);
    }

}
