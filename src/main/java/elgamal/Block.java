package elgamal;

public class Block {

    private byte[] data;

    public Block(byte[] data) {
        this.data = data.clone();
    }

    public Block(Number number) {
        this(number.getVal());
    }

    public Block(Number number, int fill) {
        this(Operations.fillArray(number.getVal(), fill));
    }

    public Number getNumber() {
        if (data[0] < 0) {
            byte[] tmp = new byte[data.length + 1];
            tmp[0] = 0;
            System.arraycopy(data, 0, tmp, 1, data.length);
            data = tmp;
        }
        return new Number(data);
    }

    public byte[] getData() {
        if (data[0] == 0) {
            byte[] tmp = new byte[data.length - 1];
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
