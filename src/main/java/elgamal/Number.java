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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = digits.length - 1; i >= 0; i--) {
            sb.append(digits[i]);
        }
        return sb.toString();
    }
}
