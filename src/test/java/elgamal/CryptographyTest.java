package elgamal;

import elgamal.exceptions.CorruptedDataException;
import elgamal.keys.PrivateKey;
import elgamal.keys.PublicKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class CryptographyTest {

    Encryption encryption;
    PublicKey publicKey;
    PrivateKey privateKey;
    Block block;
    byte[] data;

    @BeforeEach
    void initialize() {
        data = new byte[] { 11, 4, 6, (byte)255 };
        KeyGenerator kg = new KeyGenerator(10);
        kg.generate();
        block = new Block(data);
        privateKey = kg.getPrivateKey();
        encryption = new Encryption(new Block[] { block }, kg.getPublicKey());
    }

    @Test
    void cryptography() throws CorruptedDataException {
        encryption.encrypt();
        Decryption d = new Decryption(encryption.getResults(), privateKey);
        d.decrypt();
        assertArrayEquals(data, d.getResults()[0].getData());
    }

    @Test
    void testWithBytes() throws CorruptedDataException {
        byte[] data = new byte[] { 99, 88, 9, 127, 25, -127, 61, 0, 0, 0 };
        Decryption d = new Decryption(Operations.generateBlocks(data, privateKey.getMaxLength()), privateKey);
        d.decrypt();
    }
}