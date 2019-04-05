package aes;

public class Cryptography {

    protected Block[] blocks;
    protected int rounds;
    protected RoundKeys roundKeys;

    public Cryptography(Block[] blocks, Key key) {
//        Set blocks
        this.blocks = blocks;

//        Get rounds count from key
        rounds = key.getRounds();

//        Create round keys
        roundKeys = new RoundKeys(key);
    }

    public void subBytes(short[][] data, boolean inverse) {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                if (inverse) {
                    data[i][j] = Operations.invSubByte(data[i][j]);
                } else {
                    data[i][j] = Operations.subByte(data[i][j]);
                }
            }
        }
    }

    public void shiftRows(short[][] data, boolean inverse) {
        for (int i = 0; i < data.length; i++) {
            if (inverse) {
                Operations.invShiftRow(data[i], i);
            } else {
                Operations.shiftRow(data[i], i);
            }
        }
    }

    public void mixColumns(short[][] data, boolean inverse) {
        for (int i = 0; i < data.length; i++) {
            short[] tmp = new short[data.length];
            for (int j = 0; j < data[0].length; j++) {
                tmp[j] = data[j][i];
            }

            short[] mixed = (inverse ? Operations.invMixColumn(tmp) : Operations.mixColumn(tmp));
            for (int j = 0; j < data[0].length; j++) {
                data[j][i] = mixed[j];
            }
        }
    }

    public void addRoundKey(short[][] data, int round) {
        Operations.addRoundKey(data, roundKeys, round);
    }

    public Block[] getBlocks() {
        return blocks;
    }
}
