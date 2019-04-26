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
        for (int d : p.getDigits()) {
            sb.append(d);
        }
        sb.append("%");
        for (int d : g.getDigits()) {
            sb.append(d);
        }
        sb.append("%");
        for (int d : h.getDigits()) {
            sb.append(d);
        }
        return sb.toString();
    }
}
