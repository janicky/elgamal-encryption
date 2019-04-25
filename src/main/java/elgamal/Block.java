package elgamal;

public class Block {

    private short[] data;

    public Block(short[] data) {
        this.data = data;
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
}
