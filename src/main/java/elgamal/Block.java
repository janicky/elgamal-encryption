package elgamal;

import java.util.Arrays;

public class Block {

    private byte[] data;

    public Block(byte[] data) {
        this.data = data.clone();
    }

    public Block(Number number) {
        this(number.getVal());
    }

    public Block(Number number, int fill) {
        byte[] out = new byte[fill];
        byte[] in = number.getVal();
        Arrays.fill(out, (byte) 0);

        int ptr = 0;
        for (int i = out.length - in.length; i < out.length; i++){
            out[i] = in[ptr++];
        }

        data = out.clone();
    }

    public Number getNumber() {
        return new Number(data);
    }

    public byte[] getData() {
        return data;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < data.length; i++) {
            sb.append((data[i] & 0xff));
            if (i != data.length - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
