package elgamal.keys;

import elgamal.Number;

public class PublicKey {
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
}
