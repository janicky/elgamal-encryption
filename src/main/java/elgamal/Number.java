package elgamal;

import elgamal.exceptions.NegativeNumberException;

import java.util.Arrays;

public class Number {

    private byte[] digits;

    public Number(byte[] digits) {
        this.digits = digits;
    }

    public Number(String digits) {
        this.digits = new byte[digits.length()];
        for (int i = digits.length() - 1, l = 0; i >= 0; i--, l++) {
            this.digits[l] = charToByte(digits.charAt(i));
        }
    }

    public static final Number ZERO = new Number("0");

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

      // 5 6 0 8 1 -> [1, 8, 0, 6, 5]
      // -
      // 8 7 7 5 0 -> [0, 5, 7, 7, 8]
      // =
    // - 7 8 3 3 1

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

//        byte borrow = 0;
//        for (int i = 0; i <= a.length; i++) {
//            if (b.length > i) {
//                tmp[i] = (byte)((a[i] + b[i] + borrow) % 10);
//                borrow = (byte)((a[i] + b[i] + borrow) / 10);
//            } else if (a.length > i) {
//                tmp[i] = (byte)((a[i] + borrow) % 10);
//                borrow = (byte)((a[i] + borrow) / 10);
//            } else {
//                tmp[i] = borrow;
//                borrow = 0;
//            }
//        }

        return new Number("0");
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

    public byte[] getDigits() {
        return digits;
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
