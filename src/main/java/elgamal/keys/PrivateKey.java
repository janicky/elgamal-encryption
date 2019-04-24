package elgamal.keys;

import elgamal.Number;

public class PrivateKey {
    private Number a;

    public PrivateKey(Number a) {
        this.a = a;
    }

    public Number getA() {
        return a;
    }
}
