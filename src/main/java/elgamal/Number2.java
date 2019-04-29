//package elgamal;
//
//import elgamal.exceptions.DivideRestException;
//import elgamal.exceptions.NegativeNumberException;
//import elgamal.exceptions.OutOfRangeException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//public class Number {
//
//    private int[] digits;
//    private boolean[] binaryNumber;
//    private long value = -1;
//
//    public Number(int[] digits) {
//        int[] tmp = new int[digits.length];
//        System.arraycopy(digits, 0, tmp, 0, digits.length);
//        this.digits = deleteZeros(tmp);
//    }
//
//    public Number(int[] digits, boolean reversed) {
//        int tmp[] = new int[digits.length];
//        int index = 0;
//        for (int i = digits.length - 1; i >= 0; i--) {
//            tmp[index++] = digits[i];
//        }
//        this.digits = tmp;
//    }
//
//    public Number(long value) {
//        this("0");
//        setValue(value);
//    }
//
//    public Number(String digits) {
//        this.digits = new int[digits.length()];
//        for (int i = digits.length() - 1, l = 0; i >= 0; i--, l++) {
//            this.digits[l] = charToByte(digits.charAt(i));
//        }
//    }
//
//    public static final Number ZERO = new Number("0");
//    public static final Number ONE = new Number("1");
//    public static final Number TWO = new Number("2");
//
//    public static byte charToByte(char digit) {
//        return (byte) (digit - '0');
//    }
//
//    public void setDigits(int[] digits) {
//        this.digits = digits;
//    }
//
//    public Number add(Number number) {
//        int[] a = (digits.length >= number.getDigits().length ? digits : number.getDigits());
//        int[] b = (digits.length >= number.getDigits().length ? number.getDigits() : digits);
//        int[] tmp = new int[a.length + 1];
//
//        int carry = 0;
//        for (int i = 0; i <= a.length; i++) {
//            if (b.length > i) {
//                tmp[i] = (a[i] + b[i] + carry) % 10;
//                carry = (a[i] + b[i] + carry) / 10;
//            } else if (a.length > i) {
//                tmp[i] = (a[i] + carry) % 10;
//                carry = (a[i] + carry) / 10;
//            } else {
//                tmp[i] = carry;
//                carry = 0;
//            }
//        }
//
//        if (tmp[tmp.length - 1] == 0) {
//            tmp = Arrays.copyOfRange(tmp, 0, tmp.length - 1);
//        }
//        return new Number(tmp);
//    }
//
//    public Number subtract(Number number) throws NegativeNumberException {
//        if (equals(number)) {
//            return new Number("0");
//        }
//        if (!isGreaterThan(number)) {
//            throw new NegativeNumberException();
//        }
//
//        int[] a = digits;
//        int[] b = number.getDigits();
//        int[] tmp = new int[a.length];
//
//        int borrow = 0;
//        for (int i = 0; i < a.length; i++) {
//            if (b.length > i) {
//                tmp[i] = a[i] - b[i] - borrow;
//            } else {
//                tmp[i] = a[i] - borrow;
//            }
//            borrow = 0;
//            if (tmp[i] < 0) {
//                tmp[i] += 10;
//                borrow = 1;
//            }
//        }
//
//        return new Number(deleteZeros(tmp));
//    }
//
//    public Number multiply(Number number){
//        int[] a = (digits.length > number.getDigits().length ? digits : number.getDigits());
//        int[] b = (digits.length > number.getDigits().length ? number.getDigits() : digits);
//        int[] tmp = new int[a.length + b.length];
//        int digit;
//
//        for (int i = 0; i < number.getDigits().length + b.length; i++) {
//            tmp[i] = 0;
//        }
//
//        int carry = 0;
//        for (int i = 0; i < b.length; i++) {
//            int j;
//
//            for (j = i; j < a.length + i; j++) {
//                digit = tmp[j] + (b[i] * a[j - i]) + carry;
//                carry = digit / 10;
//                tmp[j] = digit % 10;
//            }
//            if (carry > 0) {
//                digit = tmp[j] + carry;
//                carry = digit / 10;
//                tmp[j] = digit % 10;
//            }
//        }
//
//        return new Number(deleteZeros(tmp));
//    }
//
//    public Number divide2(Number number, Number rest) throws NegativeNumberException, DivideRestException {
//        Number x = ZERO, last_x = ZERO, multiplier, last_multiplier = ONE;
//        Number pi;
//        Number xi_sum = ZERO;
//        Number iterator = ZERO;
//
//        // A - this
//        // B - number
//        while (true) {
//            multiplier = TWO.power(iterator);
//            pi = number.multiply(multiplier).add(x);
//
//            if (equals(pi)) {
//                return (xi_sum.isZero() ? multiplier : xi_sum.add(ONE));
//            } else if (pi.isGreaterThan(this)) {
//                x = last_x;
//
//                if (iterator.equals(ZERO)) {
//                    if (rest != null) {
//                        Number r = subtract(number.multiply(xi_sum));
//                        rest.setDigits(r.getDigits());
//                    } else {
//                        throw new DivideRestException();
//                    }
//
//                    return xi_sum;
//                }
//
//                iterator = ZERO;
//                xi_sum = xi_sum.add(last_multiplier);
//                continue;
//            } else {
//                last_x = new Number(pi.getDigits());
//                last_multiplier = multiplier;
//            }
//            iterator = iterator.add(ONE);
//        }
//    }
//
//    public Number divide(Number number, Number rest) {
//        try {
//            long x = getValue();
//            long y = number.getValue();
//            rest.setValue(x % y);
//            return new Number(x / y);
//        } catch (OutOfRangeException ex) {
//            List<Integer> results = new ArrayList<>();
//            int[] collection = digits.clone();
//            int shift = 0;
//
//            while (true) {
//                for (int i = collection.length - 1 - shift; i >= 0; i--) {
//                    int[] tmp = new int[collection.length - i - shift];
//                    int k = tmp.length - 1;
//                    for (int j = collection.length - 1 - shift; j >= i; j--) {
//                        tmp[k--] = collection[j];
//                    }
//
//                    Number split = new Number(tmp);
//                    if (split.equals(number)) {
//                        results.add(1);
//                        for (int n = collection.length - tmp.length - shift; n < collection.length; n++) {
//                            collection[n] = 0;
//                        }
//                        shift += tmp.length;
//                        break;
//                    }
//                    if (split.isEqualOrGreaterThan(number)) {
//
//                        for (int n = collection.length - tmp.length - shift; n < collection.length; n++) {
//                            collection[n] = 0;
//                        }
//                        Number tmp_rest = new Number("0");
//                        try {
//                            int res = (int) split.divide(number, tmp_rest).getValue();
//                            results.add(res);
//                        } catch (OutOfRangeException e) {
//                            e.printStackTrace();
//                        }
//                        int[] rest_digits = tmp_rest.getDigits();
//
//                        int t = 0;
//                        for (int m = collection.length - tmp.length - shift; m < collection.length - shift - tmp.length + rest_digits.length; m++) {
//                            collection[m] = rest_digits[t++];
//                        }
//                        shift += tmp.length - rest_digits.length;
//                        break;
//                    } else {
//                        if (results.size() - 1 < (collection.length - 1 - i)) {
//                            results.add(0);
//                        }
//                    }
//
//                    if (i == 0) {
//                        Number trest = new Number(collection);
//                        rest.setDigits(trest.getDigits());
//
//                        int[] tmp_output = new int[results.size()];
//                        int t = 0;
//                        for (int f = results.size() - 1; f >= 0; f--) {
//                            tmp_output[t++] = results.get(f);
//                        }
//                        return new Number(tmp_output);
//                    }
//                }
//            }
//        }
//    }
//
//    public Number divide2(Number number) throws DivideRestException {
//        try {
//            return divide2(number, null);
//        } catch (NegativeNumberException e) {
//            return null;
//        }
//    }
//
//    public Number power(Number number) {
//        Number result = ONE;
//        Number exponent = new Number(number.getDigits());
//
//        while (!exponent.isZero()) {
//            result = result.multiply(this);
//            try {
//                exponent = exponent.subtract(ONE);
//            } catch (NegativeNumberException e) {
//                e.printStackTrace();
//                break;
//            }
//        }
//
//        return result;
//    }
//
//    public Number mod(Number m) {
//        Number rest = new Number("0");
//        try {
//            divide(m, rest);
//        } catch (Exception e) {
////            ...
//        }
//        return rest;
//    }
//
//    public Number modPower(Number number, Number m){
//        Number accum = new Number(digits);
//        if (number.isZero()) {
//            return new Number("1");
//        }
//        boolean[] binary = number.getBinary();
//
//        int bitptr = binary.length - 1;
//        for (bitptr--; bitptr >= 0; bitptr--) {
//            accum = accum.multiply(accum).mod(m);
//            if (binary[bitptr]) {
//                accum = multiply(accum).mod(m);
//            }
//        }
//        return accum;
//    }
//
//    public boolean[] getBinary() {
//        if (binaryNumber != null) {
//            return binaryNumber;
//        }
//
//        List<Boolean> tmp = new ArrayList<>();
//        Number tmp_number = new Number(this.getDigits());
//
//        while (!tmp_number.isZero()) {
//            Number remainder = new Number(ZERO.getDigits());
//            try {
//                tmp_number = tmp_number.divide(TWO, remainder);
//                tmp.add(!remainder.isZero());
//            } catch (Exception e) {
//                tmp.add(true);
//                break;
//            }
//            if (tmp_number.equals(ONE)) {
//                tmp.add(true);
//                break;
//            }
//        }
//
//        binaryNumber = new boolean[tmp.size()];
//        for (int i = 0; i < tmp.size(); i++) {
//            binaryNumber[i] = tmp.get(i);
//        }
//        return binaryNumber;
//    }
//
//    public long getValue() throws OutOfRangeException {
//        if (value != -1) {
//            return value;
//        }
//        if (digits.length > 16) {
//            throw new OutOfRangeException();
//        }
//        value = 0;
//        for (int i = 0; i < digits.length; i++) {
//            value += (long)(digits[i] * Math.pow(10, i));
//        }
//        return value;
//    }
//
//    public void setValue(long value) {
//        this.value = value;
//
//        String stringValue = Long.toString(value);
//        int[] tmp = new int[stringValue.length()];
//        int t = 0;
//        for (int i = tmp.length - 1; i >= 0; i--) {
//            tmp[t++] = stringValue.charAt(i) - '0';
//        }
//        digits = tmp;
//    }
//
//    public boolean isZero() {
//        if (digits.length == 0) {
//            return true;
//        }
//        for (int i = 0; i < digits.length; i++) {
//            if (digits[i] != 0) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    public boolean isGreaterThan(Number number) {
//        if (digits.length < number.getDigits().length) {
//            return false;
//        } else if (digits.length > number.getDigits().length) {
//            return true;
//        }
//
//        int[] b = number.getDigits();
//        for (int i = digits.length - 1; i >= 0; i--) {
//            if (b[i] > digits[i]) {
//                return false;
//            } else if (b[i] < digits[i]) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public boolean isEqualOrGreaterThan(Number number) {
//        return equals(number) || isGreaterThan(number);
//    }
//
//    public int[] getDigits() {
//        return digits;
//    }
//
//    public static int[] deleteZeros(int[] digits) {
//        int[] tmp = digits;
//
//        int zeros = 0;
//        for (int i = digits.length - 1; i > 0; i--) {
//            if (digits[i] == 0) {
//                zeros++;
//            } else {
//                break;
//            }
//        }
//
//        if (zeros > 0) {
//            tmp = Arrays.copyOfRange(digits, 0, digits.length - zeros);
//        }
//        return tmp;
//    }
//
//    @Override
//    public boolean equals(Object object) {
//        if (object == this) {
//            return true;
//        }
//
//        if (!(object instanceof Number)) {
//            return false;
//        }
//
//        Number number = (Number) object;
//        int[] b = number.getDigits();
//
//        if (digits.length != b.length) {
//            return false;
//        }
//
//        for (int i = 0; i < digits.length; i++) {
//            if (digits[i] != b[i]) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        for (int i = digits.length - 1; i >= 0; i--) {
//            sb.append(digits[i]);
//        }
//        return sb.toString();
//    }
//}
