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