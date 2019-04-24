package elgamal;

import elgamal.exceptions.CorruptedDataException;
import elgamal.keys.PrivateKey;
import elgamal.keys.PublicKey;

import java.math.BigInteger;

public class Decryption extends Cryptography {

    BigInteger[] encrypted;
    byte[] results;
    PrivateKey privateKey;
    PublicKey publicKey;

    public Decryption(BigInteger[] encrypted, PrivateKey privateKey, PublicKey publicKey) {
        this.encrypted = encrypted;
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public void decrypt() throws CorruptedDataException {
        if (encrypted.length % 2 != 0) {
            throw new CorruptedDataException();
        }

        BigInteger a = privateKey.getA();
        BigInteger p = publicKey.getP();

        results = new byte[encrypted.length / 2];
        for (int i = 0; i < (encrypted.length / 2); i++) {
            BigInteger c1 = encrypted[i * 2];
            BigInteger c2 = encrypted[i * 2 + 1];
            BigInteger m = c2.divide(c1.modPow(a, p));
            results[i] = m.byteValue();
        }
    }

    public byte[] getResults() {
        return results;
    }
}
