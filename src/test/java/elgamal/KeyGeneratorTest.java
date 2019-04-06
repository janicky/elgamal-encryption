package elgamal;

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
        System.out.println(keygen.g);
    }
}