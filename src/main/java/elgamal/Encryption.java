package elgamal;

import elgamal.keys.PublicKey;

public class Encryption extends Cryptography {

    private Block[] blocks;
    private Block[] results;
    private PublicKey publicKey;

    public Encryption(Block[] blocks, PublicKey key) {
        this.blocks = blocks;
        publicKey = key;
    }

    public void encrypt() {
        results = new Block[blocks.length * 2];
        Number p = publicKey.getP();
        Number g = publicKey.getG();
        Number h = publicKey.getH();

        for (int i = 0; i < blocks.length; i++) {
            Number r = KeyGenerator.generateNumber(2, p.toString().length() - 1);

            Number m = blocks[i].getNumber();
            Number c1 = g.modPower(r, p);
            Number c2 = m.multiply(h.modPower(r, p));

            results[i * 2] = new Block(c1, publicKey.getFillSize());
            results[i * 2 + 1] = new Block(c2, publicKey.getFillSize());
        }
    }

    public Block[] getResults() {
        return results;
    }
}
