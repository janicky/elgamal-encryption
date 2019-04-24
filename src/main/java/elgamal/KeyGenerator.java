package elgamal;

import java.math.BigInteger;

public class KeyGenerator {

    BigInteger p, g, a, h;

    public void generate() {
        BigInteger prime_number;
        while (true) {
            prime_number = generateNumber(310, 320);
            if (fermatTest(prime_number, 10)) {
                p = prime_number;
                break;
            }
        }
        g = generateNumber(2, p.toString().length() - 2);
        a = generateNumber(2, p.toString().length() - 2);
        h = g.modPow(a, p);
    }

    private static BigInteger generateNumber(int min, int max) {
        int digitsNumber = Operations.getRandomNumberInRange(min, max);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < digitsNumber; i++) {
            if (i > 0) {
                sb.append(Operations.getRandomNumberInRange(0, 9));
            } else {
                sb.append(Operations.getRandomNumberInRange(1, 9));
            }
        }
        return new BigInteger(sb.toString());
    }

    public static boolean fermatTest(BigInteger number, int k) {
        BigInteger a;
        int numberLength = number.toString().length();
        for (int i = 0; i < k; i++) {
            a = generateNumber(2, numberLength - 1);
            BigInteger m = a.modPow(number.subtract(BigInteger.ONE), number);
            if (!m.equals(BigInteger.ONE)) {
                return false;
            }
        }
        return true;
    }
}
