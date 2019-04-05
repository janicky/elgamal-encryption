package elgamal;

public class Block {

    private short[][] data;

    public Block(short[][] data) {
        this.data = fillBytes(data);
    }

    private short[][] fillBytes(short[][] data) {
        if (data.length == Constants.BLOCK_SIZE * Constants.BLOCK_SIZE) {
            return data;
        }
        short[][] output = new short[Constants.BLOCK_SIZE][Constants.BLOCK_SIZE];
        for (int i = 0; i < Constants.BLOCK_SIZE; i++) {
            for (int j = 0; j < Constants.BLOCK_SIZE; j++) {
                if (i >= data.length || j >= data[i].length) {
                    output[i][j] = 0;
                } else {
                    output[i][j] = data[i][j];
                }
            }
        }
        return output;
    }

    public short[][] getData() {
        return data;
    }

    @Override
    public String toString() {
        return Operations.hexToString(data);
    }
}
