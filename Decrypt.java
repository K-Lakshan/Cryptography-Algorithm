import java.util.Base64;

public class Decrypt {

    private static int modValue = 0;

    public static String decode(String encryptedText, byte[] key) {
        byte min = key[0];
        byte max = key[0];
        for (byte b : key) {
            if (b < min) min = b;
            if (b > max) max = b;
        }
        modValue = max % min;
        if (modValue == 0) modValue = 1;

        // Decode and process the encrypted data
        byte[] mixed = Base64.getDecoder().decode(encryptedText);
        byte[] unmixed = reverseKeyMixing(mixed, key);
        byte[] unxor = unXor(unmixed);
        byte[] unshifted = unshiftKey(unxor, key);

        // Extract IV (first 16 bytes) and actual message
        int ivLength = 16; // IV_LENGTH from Key.java
        if (unshifted.length <= ivLength) {
            throw new IllegalArgumentException("Invalid encrypted data: missing or corrupted IV");
        }
        
        // Extract the message (skip the IV at the beginning)
        byte[] messageBytes = new byte[unshifted.length - ivLength];
        System.arraycopy(unshifted, ivLength, messageBytes, 0, messageBytes.length);

        return new String(messageBytes);
    }

    private static byte[] reverseKeyMixing(byte[] text, byte[] key) {
        for (int j = key.length - 1; j >= 0; j--) {
            for (int i = 0; i < text.length; i++) {
                int value = text[i] - key[j];
                text[i] = (byte) value;
            }
        }
        return text;
    }

    private static byte[] unXor(byte[] text) {
        byte[] result = new byte[text.length];
        for (int i = 0; i < text.length; i++) {
            // Match the encryption's XOR operation
            int xorValue = text[i] ^ (modValue + (i & 0xFF));
            result[i] = (byte) xorValue;
        }
        return result;
    }

    private static byte[] unshiftKey(byte[] text, byte[] key) {
        byte[] result = new byte[text.length];
        for (int i = 0; i < text.length; i++) {
            int unshifted = text[i] - modValue;
            result[i] = (byte) unshifted;
        }
        return result;
    }
}
