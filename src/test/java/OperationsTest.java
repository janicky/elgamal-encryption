import aes.Operations;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OperationsTest {

    @Test
    void subBytes() {
        assertEquals(0xff, Operations.subByte((short) 0x7d));
        assertEquals(0x16, Operations.subByte((short) 0xff));
    }

    @Test
    void invSubBytes() {
        assertEquals(0x7d, Operations.invSubByte((short) 0xff));
        assertEquals(0xff, Operations.invSubByte((short) 0x16));
    }

    @Test
    void invRotWord() {
        short[] word = new short[] { 0x01, 0x02, 0x03, 0x04 };
        Operations.invRotWord(word);
        assertEquals(0x04, word[0]);
        assertEquals(0x01, word[1]);
        assertEquals(0x02, word[2]);
        assertEquals(0x03, word[3]);
    }

    @Test
    void shiftRow() {
        short[] row = new short[] { 1, 2, 3, 4 };
        Operations.shiftRow(row, 2);
        assertEquals(3, row[0]);
        assertEquals(4, row[1]);
        assertEquals(1, row[2]);
    }

    @Test
    void invShiftRow() {
        short[] row = new short[] { 1, 2, 3, 4 };
        Operations.invShiftRow(row, 2);
        assertEquals(3, row[0]);
        assertEquals(4, row[1]);
        assertEquals(1, row[2]);
        assertEquals(2, row[3]);
    }

    @Test
    void mixColumn() {
        short[] column = new short[] { 0xd4, 0xbf, 0x5d, 0x30 };
        short[] result = Operations.mixColumn(column);
        assertEquals((byte)0x04, result[0]);
        assertEquals((byte)0x66, result[1]);
        assertEquals((byte)0x81, result[2]);
        assertEquals((byte)0xe5, result[3]);
    }

    @Test
    void invMixColumn() {
        short[] column = new short[] { 0x04, 0x66, 0x81, 0xe5 };
        short[] result = Operations.invMixColumn(column);
        assertEquals((byte)0xd4, result[0]);
        assertEquals((byte)0xbf, result[1]);
        assertEquals((byte)0x5d, result[2]);
        assertEquals((byte)0x30, result[3]);
    }
}