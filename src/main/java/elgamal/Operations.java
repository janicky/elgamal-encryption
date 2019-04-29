package elgamal;

import java.util.Arrays;
import java.util.Random;

public class Operations {
    public static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static Block[] generateBlocks(byte[] data, int length) {
        Block[] blocks = new Block[(int)Math.ceil(data.length / (double)length)];

        byte[] tmp = new byte[length];
        int b = 0, i;

        for (i = 0; i < data.length; i++) {
            tmp[i % length] = data[i];

            if (i % length == length - 1) {
                blocks[b++] = new Block(tmp);
                Arrays.fill(tmp, (byte)0);
            }
        }

        if (i % length != length - 1) {
            int ptr = 0;
            byte[] f_block = new byte[length];
            Arrays.fill(f_block, (byte)0);
            for (int j = length - (i % length); j < length; j++) {
                f_block[j] = tmp[ptr++];
            }

            blocks[blocks.length - 1] = new Block(f_block);
        }

        return blocks;
    }

    public static byte[] blocksToBytes(Block[] blocks) {
        return new byte[2];
    }

//    public static byte[] blocksToBytes(Block[] blocks, int length) {
//        byte[] output = new byte[blocks.length * length * 2];
//        int n = 0;
//        for (Block b : blocks) {
//            short[] data = b.getData();
//            for (int i = 0; i < data.length; i++) {
//                output[n++] = (byte)(data[i] & 0xff);
//            }
//        }
//        return output;
//    }
}
