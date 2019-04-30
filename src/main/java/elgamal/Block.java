package elgamal;

import java.util.Arrays;

public class Block {

    private byte[] data;
    public int fill = -1;

    public Block(byte[] data) {
        this.data = data.clone();
    }

    public Block(byte[] data, int fill) {
        this(Operations.fillArray(data, fill));
        this.fill = fill;
    }

    public Block(Number number, int fill) {
        this(Operations.fillArray(number.getVal(), fill));
        this.fill = fill;
    }

    public Number getNumber() {
        System.out.println("GET NUMBER: " + Arrays.toString(data) + " / " + data.length);
        if (data[0] < 0) {
            byte[] tmp = new byte[data.length + 1];
            tmp[0] = 0;
            System.arraycopy(data, 0, tmp, 1, data.length);
            data = tmp;
        }
        return new Number(data);
    }

    public byte[] getData() {
        System.out.println("GET DATA: " + Arrays.toString(data) + " / " + data.length + " / " + fill);
        if (data.length > 1 && data[0] == 0 && data[1] < 0) {
            byte[] tmp = new byte[data.length - 1];
            System.out.println(data.length + " REDUCED TO " + tmp.length);
            System.arraycopy(data, 1, tmp, 0, data.length - 1);
            return tmp;
        }
        return data;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < data.length; i++) {
            sb.append(data[i]);
            if (i != data.length - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
