package elgamal;

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

    public static byte charToByte(char digit) {
        return (byte) (digit - '0');
    }

    public Number add(Number number) {
        byte[] a = (digits.length >= number.getDigits().length ? digits : number.getDigits());
        byte[] b = (digits.length < number.getDigits().length ? number.getDigits() : digits);
        byte[] tmp = new byte[a.length + 1];

        byte carry = 0;
        for (int i = 0; i <= a.length; i++) {
                if (b.length >= i - 1) {
                    tmp[i] = (byte)((digits[i] + b[i] + carry) % 10);
                    carry = (byte)((digits[i] + b[i] + carry) / 10);
                } else {
                    tmp[i] = (byte)(digits[i] + carry);
                    carry = 0;
                }
        }

        return new Number("0");
    }

    public byte[] getDigits() {
        return digits;
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
