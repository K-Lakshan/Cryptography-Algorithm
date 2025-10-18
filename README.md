# Cryptography Algorithm

This is a custom symmetric encryption algorithm that transforms plaintext into ciphertext through multiple layers of operations. Here's a detailed breakdown:

## Core Components

1. **Key Generation**
   - A 256-bit (32-byte) key is generated using a cryptographically secure random number generator
   - The key is used for both encryption and decryption (symmetric encryption)

2. **Key Processing**
   - Finds minimum and maximum values in the key
   - Calculates `modValue = max % min` (minimum value of 1)
   - This `modValue` becomes crucial for subsequent transformations

## Encryption Process (4 Layers)

1. **Shift Operation**
   - Adds `modValue` to each byte of the plaintext
   - This is a basic Caesar cipher-like transformation
   - Example: If modValue=5, 'A' (65) becomes 'F' (70)

2. **XOR Operation**
   - Performs bitwise XOR between each byte and `modValue`
   - XOR is a reversible operation, making it useful in cryptography
   - Example: 65 (01000001) XOR 5 (00000101) = 68 (01000100)

3. **Key Mixing**
   - Alternates between adding and subtracting key bytes
   - Even indices: byte = byte + key[i]
   - Odd indices: byte = byte - key[i]
   - This adds key-dependent confusion

4. **Base64 Encoding**
   - Converts the final byte array to a Base64 string
   - Makes the output safe for text transmission

## Decryption Process
The decryption reverses the encryption steps in opposite order:
1. Base64 decode the ciphertext
2. Reverse key mixing (subtract for even indices, add for odd)
3. XOR with modValue (same as encryption since XOR is its own inverse)
4. Subtract modValue from each byte

## Security Considerations

1. **Strengths**:
   - Multiple transformation layers increase complexity
   - Key-dependent operations make analysis harder
   - Uses secure random number generation for keys

2. **Limitations**:
   - Custom algorithms aren't vetted by cryptanalysis
   - Vulnerable to known-plaintext attacks
   - No authentication or integrity checks

3. **Best Practices**:
   - Use established algorithms (AES, ChaCha20) for production
   - Always pair encryption with authentication (AEAD)
   - Use proper key management

## Example Usage
```java
// Generate a secure random key
byte[] key = Key.generateKey();  // 256-bit key

// Encrypt
String message = "Secret Message";
String encrypted = Encrypt.create(message, key);  // Returns Base64 string

// Decrypt
String decrypted = Decrypt.decode(encrypted, key);  // Returns original message
```