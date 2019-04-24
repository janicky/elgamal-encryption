package elgamal;

import elgamal.exceptions.CorruptedDataException;
import elgamal.keys.PrivateKey;
import elgamal.keys.PublicKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class CryptographyTest {

    Encryption encryption;
    PublicKey publicKey;
    PrivateKey privateKey;
    byte[] data;

    @BeforeEach
    void initialize() {
        data = new byte[] { 1, 4, 6, 8 };
        publicKey = new PublicKey(
                new Number("1499562501887"),
                new Number("54412"),
                new Number("192888196932"));
        privateKey = new PrivateKey(new Number("407984421"));
        encryption = new Encryption(data, publicKey);
    }

    @Test
    void cryptography() throws CorruptedDataException {
        encryption.encrypt();
        Decryption d = new Decryption(encryption.getResults(), privateKey, publicKey);
        d.decrypt();
        assertArrayEquals(data, d.getResults());
    }
}