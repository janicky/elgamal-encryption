package elgamal.keys;

import elgamal.Key;
import elgamal.Number;

public class PrivateKey implements Key {
    private Number a;
    private Number p;

    public PrivateKey(Number a, Number p) {
        this.a = a;
        this.p = p;
    }

    public Number getA() {
        return a;
    }

    public Number getP() {
        return p;
    }

    public byte[] getBytes() {
        byte separator = '%';
        byte[] a_bytes = a.toString().getBytes();
        byte[] p_bytes = p.toString().getBytes();
        byte[] output = new byte[a_bytes.length + 1 + p_bytes.length];
        System.arraycopy(a_bytes, 0, output, 0, a_bytes.length);
        output[a_bytes.length] = separator;
        System.arraycopy(p_bytes, 0, output, a_bytes.length + 1, p_bytes.length);
        return output;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int d : a.getDigits()) {
            sb.append(d);
        }
        sb.append("%");
        for (int d : p.getDigits()) {
            sb.append(d);
        }
        return sb.toString();
    }
}
