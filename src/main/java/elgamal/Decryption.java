package elgamal;

import elgamal.exceptions.CorruptedDataException;
import elgamal.keys.PrivateKey;

public class Decryption extends Cryptography {

    Block[] encrypted;
    Block[] results;
    PrivateKey privateKey;

    public Decryption(Block[] encrypted, PrivateKey privateKey) {
        this.encrypted = encrypted;
        this.privateKey = privateKey;
    }

    public void decrypt() throws CorruptedDataException {
        if (encrypted.length % 2 != 0) {
            throw new CorruptedDataException();
        }

        Number a = privateKey.getA();
        Number p = privateKey.getP();

        results = new Block[encrypted.length / 2];
        for (int i = 0; i < (encrypted.length / 2); i++) {
            Block b1 = encrypted[i * 2];
            Block b2 = encrypted[i * 2 + 1];

            Number c1 = b1.getNumber();
            Number c2 = b2.getNumber();

            Number m = c2.divide(c1.modPower(a, p));
            results[i] = new Block(m);
        }
    }

    public Block[] getResults() {
        return results;
    }
}
