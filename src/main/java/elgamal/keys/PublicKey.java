package elgamal.keys;

import java.math.BigInteger;

public class PublicKey {
    BigInteger p, g, h;

    public PublicKey(BigInteger p, BigInteger g, BigInteger h) {
        this.p = p;
        this.g = g;
        this.h = h;
    }

    public BigInteger getP() {
        return p;
    }

    public BigInteger getG() {
        return g;
    }

    public BigInteger getH() {
        return h;
    }
}
