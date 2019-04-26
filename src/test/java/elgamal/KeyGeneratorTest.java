package elgamal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class KeyGeneratorTest {

    KeyGenerator keygen;

    @BeforeEach
    void initialize() {
        keygen = new KeyGenerator(10);
    }

    @Test
    void generate() {
        keygen.generate();
    }

    @Test
    void fermatTest() {
        Number number1 = new Number("22801763479");
        assertFalse(KeyGenerator.fermatTest(number1, 4));
        Number number2 = new Number("22801763489");
        assertTrue(KeyGenerator.fermatTest(number2, 4));
    }
}