package elgamal;

import elgamal.exceptions.DivideRestException;
import elgamal.exceptions.NegativeNumberException;

import java.math.BigInteger;

public class KeyGenerator {

    BigInteger p;
    BigInteger g;
    BigInteger a;

    public void generate() {
        BigInteger pnumber = generateNumber(10, 50);
        while (true) {
            if (fermatTest(pnumber, 2)) {
                break;
            }
            pnumber = generateNumber(10, 50);
            System.out.println(pnumber);
        }
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

//    private String generateG(final String p) {
//        StringBuilder sb = new StringBuilder();
//        int digitsShift = Operations.getRandomNumberInRange(2, 5);
//        for (int i = 0; i < p.length() - digitsShift; i++) {
//            if (i > 1) {
//                sb.append(Operations.getRandomNumberInRange(0, 9));
//            } else {
//                sb.append(p.charAt(i));
//            }
//        }
//        return sb.toString();
//    }
}
