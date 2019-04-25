package elgamal;

import java.util.ArrayList;
import java.util.List;

public class Block {

    private short[] data;

    public Block(short[] data) {
        this.data = new short[data.length];
        System.arraycopy(data, 0, this.data, 0, data.length);
    }

    public Block(Number number) {
        setNumber(number);
    }

    public Number getNumber() {
        StringBuilder sb = new StringBuilder();
        for (short d : data) {
            StringBuilder digit = new StringBuilder(Short.toString(d));
            int condition = 3 - digit.length();
            for (int i = 0; i < condition; i++) {
                digit.insert(0, "0");
            }
            sb.append(digit);
        }
        return new Number(sb.toString());
    }

    public void setNumber(Number number) {
        List<Short> tmp = new ArrayList<>();

        String digits = number.toString();
        if (digits.length() % 3 != 0) {
            StringBuilder f = new StringBuilder(digits);
            if (digits.length() % 3 == 1) {
                f.insert(0, "00");
            } else {
                f.insert(0, "0");
            }
            digits = f.toString();
        }

        StringBuilder db = new StringBuilder();
        int prepared = 0;
        for (int i = 0; i < digits.length(); i++) {
            db.append(digits.charAt(i));
            prepared++;
            if (prepared == 3) {
                tmp.add(Short.parseShort(db.toString()));
                prepared = 0;
                db = new StringBuilder();
            }
        }
        if (prepared > 0) {
            tmp.add(Short.parseShort(db.toString()));
        }

        short[] output = new short[tmp.size()];
        for (int i = 0; i < output.length; i++) {
            output[i] = tmp.get(i);
        }

        this.data = output;
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
