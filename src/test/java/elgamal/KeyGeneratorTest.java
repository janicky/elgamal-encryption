package elgamal;

import elgamal.exceptions.NegativeNumberException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KeyGeneratorTest {

    KeyGenerator keygen;

    @BeforeEach
    void initialize() {
        keygen = new KeyGenerator();
    }

    @Test
    void generateP() {
        keygen.generate();
    }

    @Test
    void fermatTest() throws NegativeNumberException {
        Number number = new Number("3486612315657879987");
        KeyGenerator.fermatTest(number, 2);
    }
}