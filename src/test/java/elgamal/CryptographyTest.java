package elgamal;

import elgamal.exceptions.CorruptedDataException;
import elgamal.keys.PrivateKey;
import elgamal.keys.PublicKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class CryptographyTest {

    Encryption encryption;
    PublicKey publicKey;
    PrivateKey privateKey;
    Block block;
    short[] data;

    @BeforeEach
    void initialize() {
        data = new short[] { 11, 4, 6, 255 };
        block = new Block(data);
        publicKey = new PublicKey(
                new Number("1499562501887"),
                new Number("54412"),
                new Number("192888196932"));
        privateKey = new PrivateKey(new Number("407984421"), publicKey.getP());
        encryption = new Encryption(new Block[] { block }, publicKey);
    }

    @Test
    void cryptography() throws CorruptedDataException {
        encryption.encrypt();
        Decryption d = new Decryption(encryption.getResults(), privateKey);
        d.decrypt();
        assertArrayEquals(data, block.getData());
        assertArrayEquals(data, d.getResults()[0].getData());
    }
}