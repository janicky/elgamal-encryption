package elgamal.keys;

import elgamal.Number;

public class PrivateKey {
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
}
