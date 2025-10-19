import java.util.Base64;

public class Encrypt {

    private static int modValue=0;

    // Encrypt: shift bytes + encode with Base64
    public static String create(String plaintext, byte[] key) {

        byte min = key[0];
        byte max = key[0];
        for (byte b : key) {
            if (b < min) min = b;
            if (b > max) max = b;
        }
        modValue = max % min;
        if (modValue == 0) modValue = 1;


        byte[] textArray = plaintext.getBytes();
        //System.out.println("Original text array: " + Arrays.toString(textArray));

        byte[] shifted = shiftKey(textArray, key);
        //System.out.println("Shifted bytes: " + Arrays.toString(shifted));

        byte[] xor = toXor(shifted);
        //System.out.println("XOR bytes: " + Arrays.toString(xor));

        byte[] mixed = applyKeyMixing(xor,key);
        //System.out.println("Mix bytes: " + Arrays.toString(mixed));

        // Base64 encode for readability
        String encoded = Base64.getEncoder().encodeToString(mixed);
        //System.out.println("Base64 Encrypted: " + encoded);


        return encoded;
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

    

    private static byte[] toXor(byte[] text){
        byte[] result = new byte[text.length];

        for (int i = 0; i < text.length; i++) {
            int xorValue = text[i] ^ modValue;
            result[i] =(byte) xorValue;
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
