import java.security.SecureRandom;

public class Key {

    private static final int KEY_LENGTH = 32; // 256 bits
    private static final int IV_LENGTH = 16;  // 128 bits
    
    public static byte[] generateKey() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[KEY_LENGTH];
        random.nextBytes(key);
        return key;
    }

    public static byte[] generateIV() {
        SecureRandom random = new SecureRandom();
        byte[] iv = new byte[IV_LENGTH];
        random.nextBytes(iv);
        return iv;
    }

}
