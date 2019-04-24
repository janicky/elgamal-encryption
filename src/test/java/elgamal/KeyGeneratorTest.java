package elgamal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigInteger;

class KeyGeneratorTest {

    KeyGenerator keygen;

    @BeforeEach
    void initialize() {
        keygen = new KeyGenerator();
    }

    @Test
    void generate() {
        keygen.generate();
    }

    @Test
    void fermatTest() {
        BigInteger number1 = new BigInteger("22801763479");
        assertFalse(KeyGenerator.fermatTest(number1, 4));
        BigInteger number2 = new BigInteger("22801763489");
        assertTrue(KeyGenerator.fermatTest(number2, 4));
    }
}