package elgamal;

import elgamal.exceptions.CorruptedDataException;
import elgamal.exceptions.OutOfRangeException;
import elgamal.keys.PrivateKey;
import elgamal.keys.PublicKey;

public class Decryption extends Cryptography {

    Block[] encrypted;
    Block[] results;
    PrivateKey privateKey;
    PublicKey publicKey;

    public Decryption(Block[] encrypted, PrivateKey privateKey, PublicKey publicKey) {
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

        results = new Block[encrypted.length / 2];
        for (int i = 0; i < (encrypted.length / 2); i++) {
            Block b1 = encrypted[i * 2];
            Block b2 = encrypted[i * 2 + 1];

            Number c1 = b1.getNumber();
            Number c2 = b2.getNumber();

            Number m = null;
            try {
                m = c2.divide(c1.modPower(a, p), new Number("0"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            results[i] = new Block(m);
        }
    }

    public Block[] getResults() {
        return results;
    }
}
