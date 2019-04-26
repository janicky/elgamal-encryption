package elgamal;

import elgamal.exceptions.NegativeNumberException;
import elgamal.keys.PrivateKey;
import elgamal.keys.PublicKey;

public class KeyGenerator {

    Number p, g, a, h;
    PublicKey publicKey;
    PrivateKey privateKey;
    int length;

    public KeyGenerator(int length) {
        this.length = length;
    }

    public void generate() {
        Number prime_number;
        while (true) {
            prime_number = generateNumber(length, length + 1);
            if (fermatTest(prime_number, 3)) {
                p = prime_number;
                break;
            }
        }
        g = generateNumber(2, p.toString().length() - 2);
        a = generateNumber(2, p.toString().length() - 2);
        h = g.modPower(a, p);

        publicKey = new PublicKey(p, g, h);
        privateKey = new PrivateKey(a, p);
    }

    public static Number generateNumber(int min, int max) {
        int digitsNumber = Operations.getRandomNumberInRange(min, max);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < digitsNumber; i++) {
            if (i > 0) {
                sb.append(Operations.getRandomNumberInRange(0, 9));
            } else {
                sb.append(Operations.getRandomNumberInRange(1, 9));
            }
        }
        return new Number(sb.toString());
    }

    public static boolean fermatTest(Number number, int k) {
        Number a;
        int numberLength = number.toString().length();
        for (int i = 0; i < k; i++) {
            a = generateNumber(2, numberLength - 1);
            Number m = null;
            try {
                m = a.modPower(number.subtract(Number.ONE), number);
            } catch (NegativeNumberException e) {
                e.printStackTrace();
            }
            if (!m.equals(Number.ONE)) {
                return false;
            }
        }
        return true;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }
}
