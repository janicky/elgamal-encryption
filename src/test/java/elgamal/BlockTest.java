package elgamal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlockTest {

    short[] data;
    Block block;

    @BeforeEach
    void initialize() {
        data = new short[] { 123, 255, 15, 99, 100 };
        block = new Block(data);
    }

    @Test
    void getNumber() {
        Number number = block.getNumber();
        assertEquals("123255015099100", number.toString());
    }
}