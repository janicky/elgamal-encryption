package elgamal;

import elgamal.exceptions.DivideRestException;
import elgamal.exceptions.NegativeNumberException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Number {

    private byte[] digits;

    public Number(byte[] digits) {
        this.digits = digits;
    }

    public Number(byte[] digits, boolean reversed) {
        byte tmp[] = new byte[digits.length];
        int index = 0;
        for (int i = digits.length - 1; i >= 0; i--) {
            tmp[index++] = digits[i];
        }
        this.digits = tmp;
    }

    public Number(String digits) {
        this.digits = new byte[digits.length()];
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
        byte[] a = (digits.length >= number.getDigits().length ? digits : number.getDigits());
        byte[] b = (digits.length >= number.getDigits().length ? number.getDigits() : digits);
        byte[] tmp = new byte[a.length + 1];

        byte carry = 0;
        for (int i = 0; i <= a.length; i++) {
                if (b.length > i) {
                    tmp[i] = (byte)((a[i] + b[i] + carry) % 10);
                    carry = (byte)((a[i] + b[i] + carry) / 10);
                } else if (a.length > i) {
                    tmp[i] = (byte)((a[i] + carry) % 10);
                    carry = (byte)((a[i] + carry) / 10);
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

        byte[] a = digits;
        byte[] b = number.getDigits();
        byte[] tmp = new byte[a.length];

        byte borrow = 0;
        for (int i = 0; i < a.length; i++) {
            if (b.length > i) {
                tmp[i] = (byte)(a[i] - b[i] - borrow);
            } else {
                tmp[i] = (byte)(a[i] - borrow);
            }
            borrow = 0;
            if (tmp[i] < 0) {
                tmp[i] += 10;
                borrow = 1;
            }
        }

        int zeros = 0;
        for (int i = tmp.length - 1; i > 0; i--) {
            if (tmp[i] == 0) {
                zeros++;
            } else {
                break;
            }
        }

        if (zeros > 0) {
            tmp = Arrays.copyOfRange(tmp, 0, tmp.length - zeros);
        }

        return new Number(tmp);
    }

    public Number multiply(Number number) {
        Number product = Number.ZERO;
        Number tmp = new Number(number.getDigits());

        while (!tmp.isZero()) {
            product = product.add(this);
            try {
                tmp = tmp.subtract(Number.ONE);
            } catch (NegativeNumberException e) {
                e.printStackTrace();
                break;
            }
        }
        return product;
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

    public boolean[] getBinary() {
        List<Boolean> tmp = new ArrayList<>();
        Number number = new Number(this.getDigits());

        while (!number.isZero()) {
            try {
                System.out.println(number + " " + number.mod(TWO));
                tmp.add(number.mod(TWO).isZero());
            } catch (NegativeNumberException e) {
                tmp.add(true);
            }
            try {
                number = number.divide(TWO);
            } catch (DivideRestException e) {
                break;
            }
        }

        boolean[] output = new boolean[tmp.size()];
        for (int i = 0; i < tmp.size(); i++) {
            output[i] = tmp.get(i);
        }
        return output;
    }

    public Number modPower(Number number, Number m) throws NegativeNumberException {
        Number result = ONE;
        Number x = this.mod(m);
        boolean[] booleanNumber = number.getBinary();

        for (int i = 0; i < number.getDigits().length; i++) {
            x = x.mod(m);
            if (booleanNumber[i]) {
                result = result.multiply(x).mod(m);
            }
            x = x.multiply(x);
        }

        return result;
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

        byte[] b = number.getDigits();
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

    public byte[] getDigits() {
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

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }

        if (!(object instanceof Number)) {
            return false;
        }

        Number number = (Number) object;
        byte[] b = number.getDigits();

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
