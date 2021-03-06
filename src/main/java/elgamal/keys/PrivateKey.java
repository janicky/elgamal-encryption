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
        sb.append(a.toString());
        sb.append("%");
        sb.append(p.toString());
        return sb.toString();
    }

    public static String getPattern() {
        return "^([0-9]+)\\%([0-9]+)$";
    }

    public int getMaxLength() {
        return p.toString().length() - 1;
    }

    public int getFillSize() {
        return getMaxLength() * 3;
    }
}
