package elgamal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NumberTest {
    private Number number;

    @BeforeEach
    void initialize() {
        number = new Number("12313871289371238912");
    }

    @Test
    void construct() {
        assertEquals(number.toString(), "12313871289371238912");
    }

    @Test
    void isGreaterThan() {
        Number number1 = new Number("211");
        Number number2 = new Number("122");
        assertTrue(number1.isGreaterThan(number2));

        Number number3 = new Number("231");
        Number number4 = new Number("222");
        assertTrue(number3.isGreaterThan(number4));

        Number number5 = new Number("231");
        Number number6 = new Number("22");
        assertTrue(number5.isGreaterThan(number6));

        Number number7 = new Number("31");
        Number number8 = new Number("223");
        assertFalse(number7.isGreaterThan(number8));

        Number number9 = new Number("325");
        Number number10 = new Number("326");
        assertFalse(number9.isGreaterThan(number10));
    }

    @Test
    void add() {
        Number number1 = new Number("1111111111111111111111111");
        Number number2 = new Number("22222222222222222222222222");
        assertEquals("23333333333333333333333333", number1.add(number2).toString());

        Number number3 = new Number("1");
        Number number4 = new Number("99999999999999999999999999");
        assertEquals("100000000000000000000000000", number3.add(number4).toString());
    }

    @Test
    void charToByte() {
        byte digit = Number.charToByte('1');
        assertEquals(1, digit);
    }
}