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
        short[] split = new short[length];
        int prepared = 0;
        for (int i = 0; i < data.length; i++) {
            split[i % length] = (short)((short)data[i] & 0xff);
            prepared++;
            if (prepared == length) {
                blocks.add(new Block(split));
                Arrays.fill(split, (short)0);
                prepared = 0;
            }
        }
        if (prepared > 0) {
            blocks.add(new Block(split));
        }

        Block[] output = new Block[blocks.size()];
        for (int i = 0; i < blocks.size(); i++) {
            output[i] = blocks.get(i);
        }

        return output;
    }
}
