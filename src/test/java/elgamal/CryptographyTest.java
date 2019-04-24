package elgamal;

import elgamal.exceptions.CorruptedDataException;
import elgamal.keys.PrivateKey;
import elgamal.keys.PublicKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Arrays;

class CryptographyTest {

    Encryption encryption;
    PublicKey publicKey;
    PrivateKey privateKey;

    @BeforeEach
    void initialize() {
        byte[] data = new byte[] { 1, 4, 6, 8 };
        KeyGenerator keygen = new KeyGenerator();
        keygen.generate();
        publicKey = keygen.getPublicKey();
        privateKey = keygen.getPrivateKey();
        encryption = new Encryption(data, publicKey);
    }

    @Test
    void cryptography() throws CorruptedDataException {
        encryption.encrypt();
        Decryption d = new Decryption(encryption.getResults(), privateKey, publicKey);
        System.out.println("------");
        d.decrypt();
        System.out.println(Arrays.toString(d.getResults()));
    }
}