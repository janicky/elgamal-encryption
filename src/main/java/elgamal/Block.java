package elgamal;

public class Block {

    private byte[] data;

    public Block(byte[] data) {
        this.data = data.clone();
    }

    public Block(Number number) {
        this(number.getVal());
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
