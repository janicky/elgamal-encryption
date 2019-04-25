package elgamal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlockTest {

    short[] data;
    Block block;

    @BeforeEach
    void initialize() {
        data = new short[] { 123, 255, 5, 99, 100 };
        block = new Block(data);
    }

    @Test
    void getNumber() {
        Number number = block.getNumber();
        assertEquals("123255005099100", number.toString());
    }

    @Test
    void setNumber() {
        Number number = new Number("123456001");
        block.setNumber(number);
        short[] expected = new short[] { 123, 456, 1 };
        assertArrayEquals(expected, block.getData());
    }
}