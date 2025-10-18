import java.util.Base64;
public class Main{
    
    public static void main(String[] args) {
        String originalMessage = "Hellow world ";
        byte[] secretKey = GenerateKey.generate();

        String encrypted = Encrypt.create(originalMessage,secretKey);
        String decrypted = Decrypt.decode(encrypted, secretKey);
        System.out.println("Secret key: " + Base64.getEncoder().encodeToString(secretKey));
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);
        
    }
    
}