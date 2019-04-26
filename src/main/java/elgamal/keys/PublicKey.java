package elgamal.keys;

import elgamal.Key;
import elgamal.Number;

public class PublicKey implements Key {
    Number p, g, h;

    public PublicKey(Number p, Number g, Number h) {
        this.p = p;
        this.g = g;
        this.h = h;
    }

    public Number getP() {
        return p;
    }

    public Number getG() {
        return g;
    }

    public Number getH() {
        return h;
    }

    public byte[] getBytes() {
        byte separator = '%';
        byte[] p_bytes = p.toString().getBytes();
        byte[] g_bytes = g.toString().getBytes();
        byte[] h_bytes = h.toString().getBytes();
        byte[] output = new byte[p_bytes.length + g_bytes.length + h_bytes.length + 2];
        System.arraycopy(p_bytes, 0, output, 0, p_bytes.length);
        output[p_bytes.length] = separator;
        System.arraycopy(g_bytes, 0, output, p_bytes.length + 1, g_bytes.length);
        output[p_bytes.length + 1 + g_bytes.length] = separator;
        System.arraycopy(h_bytes, 0, output, p_bytes.length + 2 + g_bytes.length, h_bytes.length);
        return output;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int d : p.getDigits()) {
            sb.append(d);
        }
        sb.append("%");
        for (int d : g.getDigits()) {
            sb.append(d);
        }
        sb.append("%");
        for (int d : h.getDigits()) {
            sb.append(d);
        }
        return sb.toString();
    }
}
