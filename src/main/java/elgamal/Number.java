package elgamal;

import elgamal.exceptions.DivideRestException;
import elgamal.exceptions.NegativeNumberException;
import elgamal.exceptions.OutOfRangeException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Number {

    private int[] digits;
    private boolean[] binaryNumber;
    private long value = -1;

    public Number(int[] digits) {
        this.digits = digits;
    }

    public Number(int[] digits, boolean reversed) {
        int tmp[] = new int[digits.length];
        int index = 0;
        for (int i = digits.length - 1; i >= 0; i--) {
            tmp[index++] = digits[i];
        }
        this.digits = tmp;
    }

    public Number(List<Byte> digits) {
        this.digits = new int[digits.size()];
        for (int i = 0; i < digits.size(); i++) {
            this.digits[i] = digits.get(i);
        }
    }

    public Number(String digits) {
        this.digits = new int[digits.length()];
        for (int i = digits.length() - 1, l = 0; i >= 0; i--, l++) {
            this.digits[l] = charToByte(digits.charAt(i));
        }
    }

    public static final Number ZERO = new Number("0");
    public static final Number ONE = new Number("1");
    public static final Number TWO = new Number("2");

    public static byte charToByte(char digit) {
        return (byte) (digit - '0');
    }

    public Number add(Number number) {
        int[] a = (digits.length >= number.getDigits().length ? digits : number.getDigits());
        int[] b = (digits.length >= number.getDigits().length ? number.getDigits() : digits);
        int[] tmp = new int[a.length + 1];

        int carry = 0;
        for (int i = 0; i <= a.length; i++) {
                if (b.length > i) {
                    tmp[i] = (a[i] + b[i] + carry) % 10;
                    carry = (a[i] + b[i] + carry) / 10;
                } else if (a.length > i) {
                    tmp[i] = (a[i] + carry) % 10;
                    carry = (a[i] + carry) / 10;
                } else {
                    tmp[i] = carry;
                    carry = 0;
                }
        }

        if (tmp[tmp.length - 1] == 0) {
            tmp = Arrays.copyOfRange(tmp, 0, tmp.length - 1);
        }
        return new Number(tmp);
    }

    public Number subtract(Number number) throws NegativeNumberException {
        if (equals(number)) {
            return new Number("0");
        }
        if (!isGreaterThan(number)) {
            throw new NegativeNumberException();
        }

        int[] a = digits;
        int[] b = number.getDigits();
        int[] tmp = new int[a.length];

        int borrow = 0;
        for (int i = 0; i < a.length; i++) {
            if (b.length > i) {
                tmp[i] = a[i] - b[i] - borrow;
            } else {
                tmp[i] = a[i] - borrow;
            }
            borrow = 0;
            if (tmp[i] < 0) {
                tmp[i] += 10;
                borrow = 1;
            }
        }

        return new Number(deleteZeros(tmp));
    }

    public Number multiply(Number number){
        int[] a = (digits.length > number.getDigits().length ? digits : number.getDigits());
        int[] b = (digits.length > number.getDigits().length ? number.getDigits() : digits);
        int[] tmp = new int[a.length + b.length];
        int digit;

        for (int i = 0; i < number.getDigits().length + b.length; i++) {
            tmp[i] = 0;
        }

        int carry = 0;
        for (int i = 0; i < b.length; i++) {
            int j;

            for (j = i; j < a.length + i; j++) {
                digit = tmp[j] + (b[i] * a[j - i]) + carry;
                carry = digit / 10;
                tmp[j] = digit % 10;
            }
            if (carry > 0) {
                digit = tmp[j] + carry;
                carry = digit / 10;
                tmp[j] = digit % 10;
            }
        }

        return new Number(deleteZeros(tmp));
    }

    public Number divide(Number number) throws DivideRestException {
        Number product = new Number(digits);
        Number tmp = ZERO;

        while (!product.isZero()) {
            try {
                product = product.subtract(number);
                tmp = tmp.add(ONE);
            } catch (NegativeNumberException e) {
                throw new DivideRestException();
            }
        }

        return tmp;
    }

    public Number power(Number number) {
        Number result = ONE;
        Number exponent = new Number(number.getDigits());

        while (!exponent.isZero()) {
            result = result.multiply(this);
            try {
                exponent = exponent.subtract(ONE);
            } catch (NegativeNumberException e) {
                e.printStackTrace();
                break;
            }
        }

        return result;
    }

    public Number mod(Number m) throws NegativeNumberException {
        if (m.isZero()) {
            throw new ArithmeticException("Divisor is zero");
        }
        Number iterator = ONE;
        Number product = ZERO;
        while (this.isEqualOrGreaterThan(product)) {
            product = m.multiply(iterator);
            iterator = iterator.add(ONE);
        }
        return this.subtract(product.subtract(m));
    }

    public Number modPower(Number number, Number m) throws NegativeNumberException {
        Number result = ONE;
        Number x = this.mod(m);
        boolean[] booleanNumber = number.getBinary();
        for (int i = 0; i < booleanNumber.length; i++) {
            x = x.mod(m);
            if (booleanNumber[i]) {
                result = result.multiply(x).mod(m);
            }
            x = x.multiply(x);
        }


        return result;
    }

    private void setShiftLeft(Number number, int count) throws OutOfRangeException {
        int[] new_digits;
        int digits_length;

        if (number.getValue() <= Long.MAX_VALUE) {
            if (count < 32) {
                number.setValue(number.getValue() << count);
                return;
            }
            new_digits = new int[1];
            new_digits[0] = (int) number.getValue();
            digits_length = 1;
        } else {
            new_digits = number.getDigits();
            digits_length = new_digits.length;
        }

        int digits_count = count >> 5;
        count &= 31;
        int new_len = digits_length + digits_count;

        if (count == 0) {
//            TODO: Realloc
            for (int i = digits_length - 1; i > 0; i--) {
                digits[i + digits_count] = new_digits[i];
            }
        } else {
            new_len++;
            int shift_out = MPN.lshift(digits, digits_count, new_digits, digits_length, count);
        }

//        byte[] tmp = tmp_number.getDigits();
//        byte[] digits;

    }

    public long getValue() throws OutOfRangeException {
        if (value != -1) {
            return value;
        }
        if (digits.length >= 32) {
            throw new OutOfRangeException();
        }
        value = 0;
        for (int i = 0; i < digits.length; i++) {
            value += digits[i] * Math.pow(10, i);
        }
        return value;
    }

    public void setValue(long value) {
        this.value = value;

        String stringValue = Long.toString(value);
        int[] tmp = new int[stringValue.length()];

        for (int i = tmp.length - 1; i > 0; i--) {
            tmp[i] = stringValue.charAt(i) - '0';
        }

        digits = tmp;
        binaryNumber = getBinary();
    }

    public boolean[] getBinary() {
        if (binaryNumber != null) {
            return binaryNumber;
        }

        List<Boolean> tmp = new ArrayList<>();
        Number number = new Number(this.getDigits());

        while (!number.isZero()) {
            try {
                tmp.add(!number.mod(TWO).isZero());
            } catch (NegativeNumberException e) {
                tmp.add(true);
            }
            try {
                number = number.divide(TWO);
            } catch (DivideRestException e) {
                try {
                    number = number.subtract(ONE);
                    number = number.divide(TWO);
                } catch (Exception ex) {
                    break;
                }
            }
        }

        binaryNumber = new boolean[tmp.size()];
        for (int i = 0; i < tmp.size(); i++) {
            binaryNumber[i] = tmp.get(i);
        }
        return binaryNumber;
    }

    public boolean isZero() {
        if (digits.length == 0) {
            return true;
        }
        for (int i = 0; i < digits.length; i++) {
            if (digits[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public boolean isGreaterThan(Number number) {
        if (digits.length < number.getDigits().length) {
            return false;
        } else if (digits.length > number.getDigits().length) {
            return true;
        }

        int[] b = number.getDigits();
        for (int i = digits.length - 1; i >= 0; i--) {
            if (b[i] > digits[i]) {
                return false;
            } else if (b[i] < digits[i]) {
                return true;
            }
        }
        return false;
    }

    public boolean isEqualOrGreaterThan(Number number) {
        return equals(number) || isGreaterThan(number);
    }

    public int[] getDigits() {
        return digits;
    }

    public boolean isPrime() {
        if (this.equals(ONE) || this.equals(ZERO)) {
            return false;
        }

        Number iterator = TWO;
        while (true) {
            try {
                if (this.equals(iterator)) {
                    return true;
                }
                this.divide(iterator);
                return false;
            } catch (DivideRestException e) {
                iterator = iterator.add(ONE);
            }
        }
    }

    public static int[] deleteZeros(int[] digits) {
        int[] tmp = digits;

        int zeros = 0;
        for (int i = digits.length - 1; i > 0; i--) {
            if (digits[i] == 0) {
                zeros++;
            } else {
                break;
            }
        }

        if (zeros > 0) {
            tmp = Arrays.copyOfRange(digits, 0, digits.length - zeros);
        }
        return tmp;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }

        if (!(object instanceof Number)) {
            return false;
        }

        Number number = (Number) object;
        int[] b = number.getDigits();

        if (digits.length != b.length) {
            return false;
        }

        for (int i = 0; i < digits.length; i++) {
            if (digits[i] != b[i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = digits.length - 1; i >= 0; i--) {
            sb.append(digits[i]);
        }
        return sb.toString();
    }
}
