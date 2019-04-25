package elgamal;

public class Block {

    private short[] data;

    public Block(short[] data) {
        this.data = new short[data.length];
        System.arraycopy(data, 0, this.data, 0, data.length);
    }

    public Number getNumber() {
        StringBuilder sb = new StringBuilder();
        for (short d : data) {
            StringBuilder digit = new StringBuilder(Short.toString(d));
            for (int i = 0; i < 3 - digit.length(); i++) {
                digit.insert(0, "0");
            }
            sb.append(digit);
        }
        return new Number(sb.toString());
    }

    public short[] getData() {
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
