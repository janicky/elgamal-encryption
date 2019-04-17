package elgamal;

import elgamal.exceptions.NegativeNumberException;
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
    void equals() {
        Number number1 = new Number("11111111111111111111");
        Number number2 = new Number("11111111111111111111");
        assertTrue(number1.equals(number2));

        Number number3 = new Number("11111111111111211111");
        Number number4 = new Number("11111111111111111111");
        assertFalse(number3.equals(number4));
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
    void subtract() throws NegativeNumberException {
        Number number1 = new Number("111111221111111111111111133111111");
        Number number2 = new Number("111111221111111111111111133111111");
        Number result = number1.subtract(number2);
        assertEquals(Number.ZERO, result);

        Number number3 = new Number("306401");
        Number number4 = new Number("243321");
        assertEquals(new Number("63080"), number3.subtract(number4));
    }

    @Test
    void multiply() {
        Number number1 = new Number("123");
        Number number2 = new Number("123");
        Number result1 = number1.multiply(number2);
        assertEquals(new Number("15129"), result1);
    }

    @Test
    void isZero() {
        Number number1 = new Number("0");
        assertTrue(number1.isZero());
        Number number2 = new Number("1");
        assertFalse(number2.isZero());
        Number number3 = new Number("12321311");
        assertFalse(number3.isZero());
        Number number4 = new Number("0000");
        assertTrue(number4.isZero());
    }

    @Test
    void charToByte() {
        byte digit = Number.charToByte('1');
        assertEquals(1, digit);
    }
}