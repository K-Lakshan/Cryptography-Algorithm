import java.util.Random;

public class GenerateKey {
    public static byte[] generate() {
        int min = 0; 
        int max = 127;

        Random rand = new Random();
        int len = rand.nextInt(6) + 10;

        byte[] randomArray = new byte[len];
        for (int i = 0; i < len; i++) {
            randomArray[i] = (byte) (rand.nextInt((max - min + 1)) + min);
        }

        //System.out.println("Random byte array: " + Arrays.toString(randomArray));
        return randomArray;
    }

}
