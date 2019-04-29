package elgamal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlockTest {

    byte[] data;
    Block block;

    @BeforeEach
    void initialize() {
        data = new byte[] { 12, 25, 5, 99, 10 };
        block = new Block(data);
    }

    @Test
    void numberConstructor() {
        Number number = new Number(data);
        block = new Block(number);
        assertArrayEquals(data, block.getData());
    }

    @Test
    void getNumber() {
        Number number = block.getNumber();
        assertEquals("122559910", number.toString());
    }

}