package elgamal;

import elgamal.exceptions.CorruptedDataException;
import elgamal.exceptions.OutOfRangeException;
import elgamal.keys.PrivateKey;
import elgamal.keys.PublicKey;

public class Decryption extends Cryptography {

    Number[] encrypted;
    byte[] results;
    PrivateKey privateKey;
    PublicKey publicKey;

    public Decryption(Number[] encrypted, PrivateKey privateKey, PublicKey publicKey) {
        this.encrypted = encrypted;
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public void decrypt() throws CorruptedDataException {
        if (encrypted.length % 2 != 0) {
            throw new CorruptedDataException();
        }

        Number a = privateKey.getA();
        Number p = publicKey.getP();

        results = new byte[encrypted.length / 2];
        for (int i = 0; i < (encrypted.length / 2); i++) {
            Number c1 = encrypted[i * 2];
            Number c2 = encrypted[i * 2 + 1];

            Number m = null;
            try {
                m = c2.divide(c1.modPower(a, p), new Number("0"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                results[i] = (byte)(m.getValue() & 0xff);
            } catch (OutOfRangeException e) {
                e.printStackTrace();
            }
        }
    }

    public byte[] getResults() {
        return results;
    }
}
