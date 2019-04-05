package aes;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DataUtils {

    public static Block[] loadText(String text) {
        char[] chars = text.toCharArray();
        int numberOfBlocks = (int) Math.ceil(chars.length / (double)(Constants.BLOCK_SIZE * Constants.BLOCK_SIZE));

        Block[] blocks = new Block[numberOfBlocks];
        int processed = 0, bi = 0;

        while (processed < chars.length - 1) {
            short[][] tmp = new short[Constants.BLOCK_SIZE][Constants.BLOCK_SIZE];
            for (int i = 0; i < Constants.BLOCK_SIZE; i++) {
                for (int j = 0; j < Constants.BLOCK_SIZE; j++) {
                    if (processed < chars.length) {
//                        System.out.print((char)bytes[processed]);
                        tmp[j][i] = (short)chars[processed++];
                    }
                }
            }
            blocks[bi++] = new Block(tmp);
        }

        return blocks;
    }

    public static String saveText(Block[] blocks) {
        StringBuilder sb = new StringBuilder();
        for (Block b : blocks) {
            for (int i = 0; i < Constants.BLOCK_SIZE; i++) {
                for (int j = 0; j < Constants.BLOCK_SIZE; j++) {
                    sb.append((char) b.getData()[i][j]);
                }
            }
        }
        return sb.toString();
    }

    public static Block[] loadFile(String filename) throws IOException {
        Path path = Paths.get(filename);
        byte[] bytes = Files.readAllBytes(path);
        int numberOfBlocks = (int) Math.ceil(bytes.length / (double)(Constants.BLOCK_SIZE * Constants.BLOCK_SIZE));
        Block[] blocks = new Block[numberOfBlocks];
        int processed = 0, bi = 0;

        while (processed < bytes.length - 1) {
            short[][] tmp = new short[Constants.BLOCK_SIZE][Constants.BLOCK_SIZE];
            for (int i = 0; i < Constants.BLOCK_SIZE; i++) {
                for (int j = 0; j < Constants.BLOCK_SIZE; j++) {
                    if (processed < bytes.length) {
//                        System.out.print((char)bytes[processed]);
                        tmp[j][i] = (short)(bytes[processed++] & 0xff);
                    }
                }
            }
            blocks[bi++] = new Block(tmp);
        }

        return blocks;
    }

    public static byte[] loadBytes(String filename) throws IOException {
        Path path = Paths.get(filename);
        return Files.readAllBytes(path);
    }

    public static void saveFile(Block[] blocks, String filename) throws IOException {
        byte[] output = new byte[blocks.length * Constants.BLOCK_SIZE * Constants.BLOCK_SIZE];

        int o = 0;
        for (Block b : blocks) {
            short[][] data = b.getData();
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data.length; j++) {
                    output[o++] = (byte) data[j][i];
                }
            }
        }

        try (FileOutputStream fos = new FileOutputStream(filename)) {
            fos.write(output);
        }
    }

    public static void saveBytes(byte[] bytes, String filename) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            fos.write(bytes);
        }
    }
}
