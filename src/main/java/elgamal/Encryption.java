package elgamal;

import elgamal.keys.PublicKey;

import java.math.BigInteger;

public class Encryption extends Cryptography {

    private byte[] data;
    private BigInteger[] results;
    private PublicKey publicKey;

    public Encryption(byte[] data, PublicKey key) {
        this.data = data;
        publicKey = key;
    }

    public void encrypt() {
        results = new BigInteger[data.length * 2];
        BigInteger p = publicKey.getP();
        BigInteger g = publicKey.getG();
        BigInteger h = publicKey.getH();

        for (int i = 0; i < data.length; i++) {
            BigInteger r = KeyGenerator.generateNumber(2, p.toString().length() - 1);
            BigInteger m = BigInteger.valueOf(data[i]);
            BigInteger c1 =  g.modPow(r, p);
            BigInteger c2 =  m.multiply(h.modPow(r, p));
            results[i * 2] = c1;
            results[i * 2 + 1] = c2;
        }
    }

    public BigInteger[] getResults() {
        return results;
    }
}
