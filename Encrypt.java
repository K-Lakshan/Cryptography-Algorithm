import java.util.Base64;

public class Encrypt {

    private static int modValue=0;

    public static String create(String plaintext, byte[] key, byte[] iv) {
        byte min = key[0];
        byte max = key[0];
        for (byte b : key) {
            if (b < min) min = b;
            if (b > max) max = b;
        }
        modValue = max % min;
        if (modValue == 0) modValue = 1;

        if (iv == null) {
            iv = Key.generateIV();
        }

        // Add IV to the beginning of the message
        byte[] textArray = plaintext.getBytes();
        byte[] dataWithIV = new byte[iv.length + textArray.length];
        System.arraycopy(iv, 0, dataWithIV, 0, iv.length);
        System.arraycopy(textArray, 0, dataWithIV, iv.length, textArray.length);


        byte[] shifted = shiftKey(dataWithIV, key);
        byte[] xor = toXor(shifted);
        byte[] mixed = applyKeyMixing(xor, key);

        return Base64.getEncoder().encodeToString(mixed);
    }

    

    // Shift encryption logic
    private static byte[] shiftKey(byte[] text, byte[] key) {
        byte[] result = new byte[text.length];
        for (int i = 0; i < text.length; i++) {
            int shifted = text[i] + modValue;
            result[i] = (byte) shifted;
        }

        return result;
    }

    

    private static byte[] toXor(byte[] text) {
        byte[] result = new byte[text.length];
        for (int i = 0; i < text.length; i++) {
            int xorValue = text[i] ^ (modValue + (i & 0xFF));
            result[i] = (byte) xorValue;
        }

        return result;

    }

    private static byte[] applyKeyMixing(byte[] text, byte[] key) {
        for (int j = 0; j < key.length; j++) {
            for (int i = 0; i < text.length; i++) {
                int value = text[i] + key[j];
                text[i] = (byte) value;
            }
        }
        return text;
    }
    
}
