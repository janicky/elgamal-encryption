package elgamal;

import java.util.Random;
import java.math.BigInteger;

public class Number {

    private final byte[] ONE_ARRAY = {(byte) 1};
    private final byte[] ZERO_ARRAY = {(byte) 0};
    private final byte[] TWO_ARRAY = {(byte) 2};

    public static final Number ZERO = new Number(new byte[]{(byte) 0});
    public static final Number ONE = new Number(new byte[]{(byte) 1});
    public static final Number TWO = new Number(new byte[]{(byte) 2});

    private byte[] val;

    public Number(byte[] b) {
        val = b;
    }

    public Number(String digits) {
        val = new byte[digits.length()];
        for (int i = 0; i < digits.length(); i++) {
            val[i] = (byte)(digits.charAt(i) - '0');
        }
    }

    public Number(int n, Random rnd) {
        val = BigInteger.probablePrime(n, rnd).toByteArray();
    }

    public byte[] getVal() {
        return val;
    }

    public int length() {
        return val.length;
    }

    public void extend(byte extension) {
        byte[] newv = new byte[val.length + 1];
        newv[0] = extension;
        for (int i = 0; i < val.length; i++) {
            newv[i + 1] = val[i];
        }
        val = newv;
    }

    public boolean isNegative() {
        return (val[0] < 0);
    }

    public Number add(Number other) {
        byte[] a, b;

        if (val.length < other.length()) {
            a = other.getVal();
            b = val;
        } else {
            a = val;
            b = other.getVal();
        }

        if (b.length < a.length) {
            int diff = a.length - b.length;

            byte pad = (byte) 0;
            if (b[0] < 0) {
                pad = (byte) 0xFF;
            }

            byte[] newb = new byte[a.length];
            for (int i = 0; i < diff; i++) {
                newb[i] = pad;
            }

            for (int i = 0; i < b.length; i++) {
                newb[i + diff] = b[i];
            }

            b = newb;
        }

        int carry = 0;
        byte[] res = new byte[a.length];
        for (int i = a.length - 1; i >= 0; i--) {
            carry = ((int) a[i] & 0xFF) + ((int) b[i] & 0xFF) + carry;
            res[i] = (byte) (carry & 0xFF);
            carry = carry >>> 8;
        }

        Number res_li = new Number(res);

        if (!this.isNegative() && !other.isNegative()) {
            if (res_li.isNegative()) {
                res_li.extend((byte) carry);
            }
        }
        else if (this.isNegative() && other.isNegative()) {
            if (!res_li.isNegative()) {
                res_li.extend((byte) 0xFF);
            }
        }

        return res_li;
    }

    public Number negate() {
        byte[] neg = new byte[val.length];
        int offset = 0;

        if (val[0] == (byte) 0x80) {
            boolean needs_ex = true;
            for (int i = 1; i < val.length; i++) {
                if (val[i] != (byte) 0) {
                    needs_ex = false;
                    break;
                }
            }
            if (needs_ex) {
                neg = new byte[val.length + 1];
                neg[0] = (byte) 0;
                offset = 1;
            }
        }

        for (int i = 0; i < val.length; i++) {
            neg[i + offset] = (byte) ~val[i];
        }

        Number neg_li = new Number(neg);

        // add 1 to complete TWO_ARRAY's complement negation
        return neg_li.add(new Number(ONE_ARRAY));
    }

    public Number subtract(Number other) {
        return this.add(other.negate());
    }

    public Number multiply(Number other) {
        Number x = this, y = other;

        if (this.isNegative())
            x = this.negate();
        if (other.isNegative())
            y = other.negate();

        Number product = new Number(new byte[this.length() + other.length()]);

        for (int i = x.length() - 1; i >= 0; i--) {
            int currentBit = 1;
            for (int j = 8; j > 0; j--) {
                if ((x.getVal()[i] & currentBit) > 0)
                    product = product.add(y);
                currentBit = currentBit << 1;
                y = y.shiftLeft();
            }
        }
        if (this.isNegative() == other.isNegative())
            return fill(product);
        else
            return fill(product.negate());
    }

    private Number shiftLeft() {
        byte[] shifted;
        if((val[0] & 0xC0) == 0x40)
            shifted = new byte[val.length + 1];
        else
            shifted = new byte[val.length];

        int prevMsb = 0, msb;
        for(int i = 1; i <= val.length; i++) {
            msb = (val[val.length - i] & 0x80) >> 7;
            shifted[shifted.length - i] = (byte) (val[val.length - i] << 1);
            shifted[shifted.length - i] |= prevMsb;
            prevMsb = msb;
        }
        return new Number(shifted);
    }

    public Number shiftRight() {
        byte[] shifted;
        int i;
        if(val[0] == 0 && (val[1] & 0x80) == 0x80) {
            shifted = new byte[val.length - 1];
            i = 1;
        } else {
            shifted = new byte[val.length];
            i = 0;
        }
        int prevMsb, msb;
        if(this.isNegative())
            prevMsb = 1;
        else
            prevMsb = 0;

        for(int j = 0; j < shifted.length; j++, i++) {
            msb = val[i] & 0x01;
            shifted[j] = (byte) ((val[i] >> 1) & 0x7f);
            shifted[j] |= prevMsb << 7;
            prevMsb = msb;
        }
        return fill(new Number(shifted));
    }

    public Number[] XGCD (Number other) {
        if (other.equals(new Number(ZERO_ARRAY))) {
            return new Number[]{this, new Number(ONE_ARRAY), new Number(ZERO_ARRAY)};
        } else {
            Number[] result = other.XGCD(this.mod(other));
            return new Number[]{result[0], fill(result[2]), fill(result[1].subtract(this.divide(other).multiply(result[2])))};
        }
    }

    public Number mod(Number other) {
        return fill(this.subtract(other.multiply(this.divide(other))));
    }

    public Number divide(Number other) {
        Number quotient = new Number(ZERO_ARRAY), dividend = this, divisor = other;

        if (this.isNegative())
            dividend = this.negate();
        if (other.isNegative())
            divisor = other.negate();

        int shift = 0;
        while (!dividend.subtract(divisor).isNegative()) {
            divisor = divisor.shiftLeft();
            shift++;
        }
        divisor = divisor.shiftRight();

        while (shift > 0) {
            quotient = quotient.shiftLeft();
            if (!dividend.subtract(divisor).isNegative()) {
                dividend = dividend.subtract(divisor);
                quotient = quotient.add(new Number(ONE_ARRAY));
            }
            divisor = divisor.shiftRight();
            shift--;
        }

        if (this.isNegative() == other.isNegative())
            return fill(quotient);
        else
            return fill(quotient.negate());
    }

    public Number modPower(Number y, Number n) {
        if(y.equals(new Number(ONE_ARRAY).negate())) {
            Number[] GCD = n.XGCD(this);
            if (GCD[2].isNegative())
                return n.add(GCD[2]);
            else
                return GCD[2];

        } else {
            Number result = new Number(ONE_ARRAY);
            Number value = this.mod(n);
            Number pow = y;
            while (!pow.isNegative() && !pow.equals(new Number(ZERO_ARRAY))) {
                if (pow.mod(new Number(TWO_ARRAY)).equals(new Number(ONE_ARRAY))) {
                    result = (result.multiply(value)).mod(n);
                }
                pow = pow.shiftRight();
                value = (value.multiply(value)).mod(n);
            }
            return result;
        }
    }

    public Number fill(Number other) {
        if (other.length() > 1 && (((other.getVal()[0] == 0) && ((other.getVal()[1] & 0x80) == 0x00)) || ((other.getVal()[0] & 0xff) == 0xff && (other.getVal()[1] & 0x80) == 0x80))) {
            byte[] result = new byte[other.length() - 1];
            for (int i = 1; i <= result.length; i++)
                result[i - 1] = other.val[i];
            return fill(new Number(result));
        }
        return other;
    }

    public boolean equals(Number other) {
        if (this.length() != other.length())
            return false;
        for (int i = 0; i < this.length(); i++)
            if (this.getVal()[i] != other.getVal()[i])
                return false;
        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < val.length; i++) {
            sb.append((val[i] & 0xff));
        }
        return sb.toString();
    }
}