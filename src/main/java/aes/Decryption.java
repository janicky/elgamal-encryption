package aes;

public class Decryption extends Cryptography {

    public Decryption(Block[] blocks, Key key) {
        super(blocks, key);
    }

    public void decrypt() {
//        Rounds
        for (int i = rounds; i > 0; i--) {
            for (int b = 0; b < blocks.length; b++) {
                decryptBlock(blocks[b], i);
            }
        }

//        Add round key
        for (int b = 0; b < blocks.length; b++) {
            addRoundKey(blocks[b].getData(), 0);
        }
    }

    public void decryptBlock(Block block, int round) {
        short[][] data = block.getData();

//        AddRoundKey
        addRoundKey(data, round);
//        InvMixColumns
        if (rounds != round) {
            mixColumns(data, true);
        }
//        InvShiftRows
        shiftRows(data, true);
//        InvSubBytes
        subBytes(data, true);
    }
}
