package elgamal;

public class Number {

    private byte[] digits;

    public Number(byte[] digits) {
        this.digits = digits;
    }

    public Number(String digits) {
        this.digits = new byte[digits.length()];
        for (int i = 0; i < digits.length(); i++) {
            this.digits[i] = (byte) digits.charAt(i);
        }
    }

    public static byte charToByte(char digit) {
        return (byte) (digit - '0');
    }
}
