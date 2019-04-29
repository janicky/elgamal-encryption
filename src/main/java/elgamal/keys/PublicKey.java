package elgamal.keys;

import elgamal.Key;
import elgamal.Number;

public class PublicKey implements Key {
    Number p, g, h;

    public PublicKey(Number p, Number g, Number h) {
        this.p = p;
        this.g = g;
        this.h = h;
    }

    public Number getP() {
        return p;
    }

    public Number getG() {
        return g;
    }

    public Number getH() {
        return h;
    }

    public byte[] getBytes() {
        return toString().getBytes();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(p.toString());
        sb.append("%");
        sb.append(g.toString());
        sb.append("%");
        sb.append(h.toString());
        return sb.toString();
    }

    public static String getPattern() {
        return "^([0-9]+)\\%([0-9]+)\\%([0-9]+)$";
    }

    public int getMaxLength() {
        return p.toString().length() - 1;
    }

    public int getFillSize() {
        return getMaxLength() * 3;
    }
}
