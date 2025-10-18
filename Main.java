import java.util.Base64;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static byte[] secretKey = null;

    public static void main(String[] args) {
        boolean running = true;
        
        System.out.println("=== Cryptography Tool ===");
        
        while (running) {
        
            System.out.println("\nMenu:");
            System.out.println("1. Encrypt Message");
            System.out.println("2. Decrypt Message");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            
            int choice = getIntInput(0, 2);
            
            switch (choice) {
                case 1:
                    encryptMessage();
                    break;
                case 2:
                    decryptMessage();
                    break;
                case 0:
                    running = false;
                    System.out.println("Exiting...");
                    break;
            }
        }
        
        scanner.close();
    }
    
    private static void encryptMessage() {
        secretKey = Key.generateKey();
        scanner.nextLine();
        System.out.print("\nEnter message to encrypt: ");
        String message = scanner.nextLine();
        
        String encrypted = Encrypt.create(message, secretKey);
        System.out.println("\nKey: " + Base64.getEncoder().encodeToString(secretKey));
        System.out.println("Encrypted: " + encrypted);
    }
    
    private static void decryptMessage() {
        scanner.nextLine();
        System.out.print("\nEnter message to decrypt: ");
        String encryptedMessage = scanner.nextLine();

        System.out.print("Enter key: ");
        String key = scanner.nextLine();
        
        try {
            byte[] keyBytes = Base64.getDecoder().decode(key.trim());
            String decrypted = Decrypt.decode(encryptedMessage.trim(), keyBytes);
            System.out.println("\nDecrypted message: " + decrypted);
        } catch (Exception e) {
            System.out.println("\nError: Failed to decrypt. Invalid message or key.");
        }
    }
    
    private static int getIntInput(int min, int max) {
        while (true) {
            try {
                int input = scanner.nextInt();
                if (input >= min && input <= max) {
                    return input;
                }
                System.out.print("Please enter a number between " + min + " and " + max + ": ");
            } catch (Exception e) {
                System.out.print("Invalid input. Please enter a number: ");
                scanner.next();
            }
        }
    }
}