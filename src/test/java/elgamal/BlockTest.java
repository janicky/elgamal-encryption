package elgamal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlockTest {

    short[] data;
    Block block;

    @BeforeEach
    void initialize() {
        data = new short[] { 12, 25, 5, 99, 10 };
        block = new Block(data, 6);
    }

    @Test
    void getNumber() {
        Number number = block.getNumber();
        assertEquals("1225059910", number.toString());
    }

    @Test
    void setNumber() {
        Number number = new Number("1234560001");
        block.setNumber(number);
        short[] expected = new short[] { 12, 34, 56, 0, 1 };
        assertArrayEquals(expected, block.getData());
    }
}