package elgamal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class OperationsTest {

    byte[] data;
    Block[] blocks;
    final int length = 3;

    @BeforeEach
    void initialize() {
        data = new byte[] { 1, 2, 3, 15, 1, 2, 3, 16, (byte)255, 100, 99, 98, 2, 1, 1, 9, 5, 1, 2 };
        blocks = Operations.generateBlocks(data, length);
    }

    @Test
    void generateBlocks() {
        final String expected = "[[1,2,3], [15,1,2], [3,16,255], [100,99,98], [2,1,1], [9,5,1], [0,0,2]]";
        assertEquals(expected, Arrays.toString(blocks));
    }

    @Test
    void blocksToBytes() {
        byte[] bytes = Operations.blocksToBytes(blocks, length);
        byte[] expected = new byte[] { 1, 2, 3, 15, 1, 2, 3, 16, (byte)255, 100, 99, 98, 2, 1, 1, 9, 5, 1, 0, 0, 2 };
        assertArrayEquals(expected, bytes);
    }

}