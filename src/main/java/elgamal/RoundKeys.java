package elgamal;

public class RoundKeys {
    private int rounds;
    private Key key;
    private short[][] roundKeys;
    private int roundKeysLength;
    private short[][] roundConstants;

    public RoundKeys(Key key) {
        rounds = key.getRounds();
        this.key = key;
        roundKeysLength = Constants.BLOCK_SIZE * (rounds + 1);
        roundKeys = new short[Constants.BLOCK_SIZE][roundKeysLength];
        fillWithKey();
        buildRoundConstants();
        buildRoundKeys();
    }

    private void fillWithKey() {
        short[] data = key.getData();
        for (int column = 0; column < key.getLength() / Constants.BLOCK_SIZE; column++) {
            for (int j = 0; j < Constants.BLOCK_SIZE; j++) {
                roundKeys[column][j] = data[Constants.BLOCK_SIZE * column + j];
            }
        }
    }

    private void buildRoundConstants() {
        roundConstants = new short[Constants.BLOCK_SIZE][rounds];
        for (int column = 0; column < roundConstants[0].length; column++) {
            roundConstants[0][column] = Constants.RCON[column];
            for (int j = 1; j < Constants.BLOCK_SIZE; j++) {
                roundConstants[j][column] = 0x0;
            }
        }
    }

    private void buildRoundKeys() {
        int i = 0;
        for (int column = key.getLength() / Constants.BLOCK_SIZE; column < roundKeysLength; column++) {
//            Assign key columns to temporary variable
            short[] w1 = new short[Constants.BLOCK_SIZE];
            short[] w4 = new short[Constants.BLOCK_SIZE];

            for (int row = 0; row < Constants.BLOCK_SIZE; row++) {
                w1[row] = roundKeys[row][column - 1];
                w4[row] = roundKeys[row][column - 4];
            }

//            First column in block
            if (column % Constants.BLOCK_SIZE == 0) {
//            Use rotWord function
                Operations.rotWord(w1);
//            Change bytes - subBytes
                for (int row = 0; row < Constants.BLOCK_SIZE; row++) {
                    w1[row] = Operations.subByte(w1[row]);
                }
            }

            for (int row = 0; row < Constants.BLOCK_SIZE; row++) {
                roundKeys[row][column] = (short) (w4[row] ^ w1[row]);
                if (column % Constants.BLOCK_SIZE == 0) {
                    roundKeys[row][column] ^= roundConstants[row][i];
                }
            }

            if (column % Constants.BLOCK_SIZE == 0) {
                i++;
            }
        }
    }

    public short[][] getKeys() {
        return roundKeys;
    }

    public short[][] getRoundConstants() {
        return roundConstants;
    }

    @Override
    public String toString() {
        return Operations.hexToString(roundKeys);
    }
}