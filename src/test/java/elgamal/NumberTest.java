package elgamal;

import elgamal.exceptions.DivideRestException;
import elgamal.exceptions.NegativeNumberException;
import elgamal.exceptions.OutOfRangeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

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
        Number number1 = new Number("12321353530149145");
        Number number2 = new Number("1238713891319");
        Number result1 = number1.multiply(number2);
        assertEquals(new Number("15262631777648144989390772255"), result1);
    }

    @Test
    void divide() throws DivideRestException {
        Number number1 = new Number("125");
        Number number2 = new Number("5");
        Number result1 = number1.divide(number2);
        assertEquals(new Number("25"), result1);
    }

    @Test
    void divideWithRest() throws DivideRestException, NegativeNumberException {
        Number number1 = new Number("101");
        Number number2 = new Number("5");
        Number rest = Number.ZERO;
        Number result1 = number1.divide(number2, rest);
        assertEquals(Number.ONE, rest);
        assertEquals(new Number("20"), result1);
    }

    @Test
    void power() {
        Number number1 = new Number("5");
        Number number2 = new Number("3");
        Number result = number1.power(number2);
        assertEquals(new Number("125"), result);
    }

    @Test
    void mod() throws NegativeNumberException {
        Number number1 = new Number("951231");
        Number number2 = new Number("1314");
        Number result1 = number1.mod(number2);
        assertEquals(new Number("1209"), result1);

        Number number3 = new Number("16");
        Number number4 = new Number("2");
        Number result2 = number3.mod(number4);
        assertEquals(new Number("0"), result2);
    }

    @Test
    void modPower() throws NegativeNumberException {
        Number number1 = new Number("2123");
        Number number2 = new Number("313");
        Number number3 = new Number("52");
        Number result1 = number1.modPower(number2, number3);
        assertEquals(new Number("43"), result1);

        Number number4 = new Number("2");
        Number number5 = new Number("3");
        Number number6 = new Number("5");
        Number result = number4.modPower(number5, number6);
        assertEquals(new Number("3"), result);

        Number number7 = new Number("121");
        Number number8 = new Number("112");
        Number number9 = new Number("5");
    }

    @Test
    void getValue() throws OutOfRangeException {
        Number number1 = new Number("1231233");
        assertEquals(1231233, number1.getValue());
    }

    @Test
    void setValue() throws OutOfRangeException {
        Number number1 = new Number("82432213");
        assertEquals(82432213, number1.getValue());
        number1.setValue(5971613);
        assertEquals(5971613, number1.getValue());
        assertEquals("3161790", number1.toString());
    }

    @Test
    void getBinary() {
        Number number1 = new Number("8");
        boolean[] expected1 = new boolean[] { false, false, false, true };
        assertArrayEquals(expected1, number1.getBinary());

        Number number2 = new Number("9");
        boolean[] expected2 = new boolean[] { true, false, false, true };
        assertArrayEquals(expected2, number2.getBinary());
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
    void isPrime() {
        assertFalse((new Number("1")).isPrime());
        assertTrue((new Number("2")).isPrime());
        assertTrue((new Number("3")).isPrime());
        assertTrue((new Number("5")).isPrime());
        assertTrue((new Number("7")).isPrime());
        assertTrue((new Number("11")).isPrime());
        assertFalse((new Number("12")).isPrime());
    }

    @Test
    void charToByte() {
        byte digit = Number.charToByte('1');
        assertEquals(1, digit);
    }
}