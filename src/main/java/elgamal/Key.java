package elgamal;

public class Key {
    private int rounds;
    private short[] data;

    public Key(String key) {
        data = new short[key.length()];
        char[] chars = key.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            data[i] = (short) chars[i];
        }
        rounds = calcRounds(data.length);
    }

    public Key(short[] key) {
        data = key;
        rounds = calcRounds(data.length);
    }

    public Key(byte[] key) {
        data = new short[key.length];
        for (int i = 0; i < key.length; i++) {
            data[i] = (short)(key[i] & 0xff);
        }
    }

    private int calcRounds(int key_length) {
        switch (key_length) {
            case 24:
                return 12;
            case 32:
                return 14;
            default:
                return 10;
        }
    }

    public int getLength() {
        return data.length;
    }

    public short[] getData() {
        return data;
    }

    public byte[] getBytes() {
        byte[] bytes = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            bytes[i] = (byte)(data[i] & 0xff);
        }
        return bytes;
    }

    public int getRounds() {
        return rounds;
    }
}
