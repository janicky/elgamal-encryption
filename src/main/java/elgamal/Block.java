package elgamal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Block {

    private short[] data;
    private int length;

    public Block(short[] data, int length) {
        System.out.println("B:"+Arrays.toString(data));
        this.data = data;
//        this.data = new short[2 * length];
//        this.length = length;
//        if (data.length > length) {
//            return;
//        }
//
//        System.arraycopy(data, 0, this.data, length * 2 - data.length, data.length);
    }

    public Block(Number number, int length) {
        this.length = length;
        setNumber(number);
    }

    public Number getNumber() {
        StringBuilder sb = new StringBuilder();
        for (short d : data) {
            StringBuilder digit = new StringBuilder(Short.toString(d));
            int condition = 2 - digit.length();
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
        if (digits.length() % 2 != 0) {
            StringBuilder f = new StringBuilder(digits);
            f.insert(0, "0");
            digits = f.toString();
        }

        StringBuilder db = new StringBuilder();
        int prepared = 0;
        for (int i = 0; i < digits.length(); i++) {
            db.append(digits.charAt(i));
            prepared++;
            if (prepared == 2) {
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


        data = new short[length * 2];
        System.arraycopy(output, 0, data, length * 2 - output.length, output.length);
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
