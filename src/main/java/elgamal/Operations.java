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
        List<Block> blocks = new ArrayList<>();
        List<Short> split = new ArrayList<>();
        int prepared = 0;
        for (int i = 0; i < data.length; i++) {
            split.add((short)((short)data[i] & 0xff));
            prepared++;
            if (prepared == length * 2) {
                System.out.println("S: " + split);
                short[] x = new short[split.size()];
                for (int xi = 0; xi < split.size(); xi++) {
                    x[xi] = split.get(xi);
                }
                blocks.add(new Block(x, length));
                split.clear();
                prepared = 0;
            }
        }
        if (prepared > 0) {
            short[] x = new short[split.size()];
            for (int xi = 0; xi < split.size(); xi++) {
                x[xi] = split.get(xi);
            }
            blocks.add(new Block(x, length));
        }

        Block[] output = new Block[blocks.size()];
        for (int i = 0; i < blocks.size(); i++) {
            output[i] = blocks.get(i);
        }

        return output;
    }

    public static byte[] blocksToBytes(Block[] blocks, int length) {
        byte[] output = new byte[blocks.length * length * 2];
        int n = 0;
        for (Block b : blocks) {
            short[] data = b.getData();
            for (int i = 0; i < data.length; i++) {
                output[n++] = (byte)(data[i] & 0xff);
            }
        }
        return output;
    }
}
