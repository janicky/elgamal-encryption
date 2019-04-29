package elgamal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        Block[] blocks = new Block[(int)Math.ceil(data.length / length)];

        byte[] tmp = new byte[length];
        int ptr = 0;

        for (int i = 0; i < data.length; i++) {
            tmp[i % length] = data[i];

            if (i % length == length - 1) {
                blocks[ptr++] = new Block(tmp);
            }
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
