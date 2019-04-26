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
        for (int i = p.getDigits().length - 1; i > 0; i--) {
            sb.append(p.getDigits()[i]);
        }
        sb.append("%");
        for (int i = g.getDigits().length - 1; i > 0; i--) {
            sb.append(g.getDigits()[i]);
        }
        sb.append("%");
        for (int i = h.getDigits().length - 1; i > 0; i--) {
            sb.append(h.getDigits()[i]);
        }
        return sb.toString();
    }

    public static String getPattern() {
        return "^([0-9]+)\\%([0-9]+)\\%([0-9]+)$";
    }

    public int getMaxLength() {
        return p.toString().length() - 1;
    }
}
