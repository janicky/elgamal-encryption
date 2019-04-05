package aes;

public class Operations {

    private static short _subByte(short data, boolean inverse) {
        int x = (data & 0xf0) >> 4;
        int y = (data & 0x0f);
        return (inverse ? Constants.INVSBOX[x][y] : Constants.SBOX[x][y]);
    }

    public static short subByte(short data) {
        return _subByte(data, false);
    }

    public static short invSubByte(short data) {
        return _subByte(data, true);
    }

    public static void rotWord(short[] column) {
        short first = column[0];
        for (int i = 0; i < Constants.BLOCK_SIZE - 1; i++) {
            column[i] = column[i + 1];
        }
        column[column.length - 1] = first;
    }

    public static void invRotWord(short[] column) {
        short last = column[column.length - 1];
        for (int i = column.length - 1; i > 0; i--) {
            column[i] = column[i - 1];
        }
        column[0] = last;
    }

    public static void shiftRow(short[] row, int shift) {
        for (int i = 0; i < shift; i++) {
            rotWord(row);
        }
    }

    public static void invShiftRow(short[] row, int shift) {
        for (int i = 0; i < shift; i++) {
            invRotWord(row);
        }
    }

    private static short[] _mixColumn(short[] column, boolean inverse) {
//        Temporary array for results
        short[] output = new short[column.length];
//        Set galois array
        short[][] galois = (inverse ? Constants.INVGALOIS : Constants.GALOIS);

//        Assign values
        for (int i = 0; i < column.length; i++) {
            short sum = 0;
            for (int j = 0; j < column.length; j++) {
                try {
                    sum ^= GF(column[j], (byte)galois[i][j]);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
            output[i] = sum;
        }
        return output;
    }

    public static short[] mixColumn(short[] column) {
        return _mixColumn(column, false);
    }

    public static short[] invMixColumn(short[] column) {
        return _mixColumn(column, true);
    }

    public static byte GF(short data, byte multiplier)
            throws Exception {
        byte m2 = (byte) 0x02;
        switch (multiplier) {
            case (byte)0x01:
                return (byte)data;
            case (byte)0x02:
                if ((short)(data & 0xff) < 0x80) {
                    return (byte)((byte)data << 1);
                } else {
                    return (byte)((byte)(data << 1) ^ (byte)0x1b);
                }
            case (byte)0x03:
                byte x = GF(data, (byte)0x02);
                return (byte)(x ^ (byte)data);
            case (byte)0x09:
                return (byte)(GF(GF(GF(data, m2), m2), m2) ^ data);
            case (byte)0x0b:
                return (byte)(GF(GF(GF(data, m2), m2), m2) ^ GF(data, m2) ^ (byte)data);
            case (byte)0x0d:
                return (byte)(GF(GF(GF(data, m2), m2), m2) ^ GF(GF(data, m2), m2) ^ (byte)data);
            case (byte)0x0e:
                return (byte)(GF(GF(GF(data, m2), m2), m2) ^ GF(GF(data, m2), m2) ^ GF(data, m2));
            default:
                throw new Exception("Multiplier not implemented.");
        }
    }

    public static String hexToString(short[][] data) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                sb.append(String.format("0x%02X", (byte)(data[i][j] & 0xff)) + " ");
            }
            sb.append("\n");
        }

        return sb.toString() + "\n";
    }

    public static void addRoundKey(short[][] data, RoundKeys roundKeys, int round) {
        short[][] keys = roundKeys.getKeys();
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                int roundShift = round * Constants.BLOCK_SIZE;
                data[i][j] = (short)(data[i][j] ^ keys[i][j + roundShift]);
            }
        }
    }
}
