package elgamal;

import elgamal.keys.PublicKey;

public class Encryption extends Cryptography {

    private byte[] data;
    private Number[] results;
    private PublicKey publicKey;

    public Encryption(byte[] data, PublicKey key) {
        this.data = data;
        publicKey = key;
    }

    public void encrypt() {
        results = new Number[data.length * 2];
        Number p = publicKey.getP();
        Number g = publicKey.getG();
        Number h = publicKey.getH();

        for (int i = 0; i < data.length; i++) {
            Number r = KeyGenerator.generateNumber(2, p.toString().length() - 1);
            Number m = new Number(data[i]);
            Number c1 = g.modPower(r, p);
            Number c2 = m.multiply(h.modPower(r, p));
            results[i * 2] = c1;
            results[i * 2 + 1] = c2;
        }
    }

    public Number[] getResults() {
        return results;
    }
}
