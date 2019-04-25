package elgamal;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class OperationsTest {

    @Test
    void generateBlocks() {
        byte[] data = new byte[] { 1, 2, 3, 15, 1, 2, 3, 16, (byte)255, 100, 99, 98, 2, 1, 1, 9, 5, 1 };
        Block[] blocks = Operations.generateBlocks(data, 4 - 1);
        System.out.println(Arrays.toString(blocks));
    }
}