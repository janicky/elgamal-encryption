package elgamal.keys;

import elgamal.Key;
import elgamal.Number;

public class PrivateKey implements Key {
    private Number a;
    private Number p;

    public PrivateKey(Number a, Number p) {
        this.a = a;
        this.p = p;
    }

    public Number getA() {
        return a;
    }

    public Number getP() {
        return p;
    }

    public byte[] getBytes() {
        return toString().getBytes();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int d : a.getDigits()) {
            sb.append(d);
        }
        sb.append("%");
        for (int d : p.getDigits()) {
            sb.append(d);
        }
        return sb.toString();
    }

    public static String getPattern() {
        return "^([0-9]+)\\%([0-9]+)$";
    }
}
