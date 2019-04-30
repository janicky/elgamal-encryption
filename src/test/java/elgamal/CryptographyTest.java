package elgamal;

import elgamal.exceptions.CorruptedDataException;
import elgamal.keys.PrivateKey;
import elgamal.keys.PublicKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CryptographyTest {

    Encryption encryption;
    PublicKey publicKey;
    PrivateKey privateKey;
    Block block;
    byte[] data;

    @BeforeEach
    void initialize() {
        data = new byte[] { 11, 4, 6, (byte)255 };
        KeyGenerator kg = new KeyGenerator(6);
        kg.generate();
        privateKey = kg.getPrivateKey();
        publicKey = kg.getPublicKey();
        block = new Block(data, privateKey.getMaxLength());
        encryption = new Encryption(new Block[] { block }, publicKey);
    }

    @Test
    void cryptography() throws CorruptedDataException {
        encryption.encrypt();
        System.out.println(Arrays.toString(encryption.getResults()));
        Decryption d = new Decryption(encryption.getResults(), privateKey);
        d.decrypt();
        byte[] expected = { 0, 11, 4, 6, (byte)255 };
        assertArrayEquals(expected, d.getResults()[0].getData());
    }

    @Test
    void testWithBytes() throws CorruptedDataException {
        byte[] data = new byte[] { 99, 88, 9, 127, 25, -127, 61, 0, 0, 0 };
        Decryption d = new Decryption(Operations.generateBlocks(data, privateKey.getMaxLength()), privateKey);
        d.decrypt();
    }

    @Test
    void testWithOperations() throws CorruptedDataException {
        byte[] d = new byte[] { 99, 88, 9, 127, 25, -127, 61, 1, 2, 5 };
        Block[] bs = Operations.generateBlocks(d, publicKey.getMaxLength());

        Encryption enc = new Encryption(bs, publicKey);
        enc.encrypt();
        Decryption dec = new Decryption(enc.getResults(), privateKey);
        dec.decrypt();

        byte[] result = Operations.blocksToBytes(dec.getResults(), publicKey.getMaxLength());
        assertEquals("[[99,88,9,127,25], [0,-127,61,1,2,5]]", Arrays.toString(bs));

        assertArrayEquals(d, result);
    }
}