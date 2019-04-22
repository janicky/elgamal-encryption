package elgamal;

import elgamal.exceptions.NegativeNumberException;

import java.util.Arrays;

public class KeyGenerator {

    Number p;
    Number g;
    Number a;

    public void generate() {
        Number pnumber = generateNumber(10, 50);
        while (true) {
            try {
                if (fermatTest(pnumber, 2)) {
                    break;
                }
            } catch (NegativeNumberException e) {}
            pnumber = generateNumber(10, 50);
            System.out.println(pnumber);
        }
    }

    private static Number generateNumber(int min, int max) {
        int digitsNumber = Operations.getRandomNumberInRange(min, max);
        int[] digits = new int[digitsNumber];

        for (int i = 0; i < digitsNumber; i++) {
            if (i > 0) {
                digits[i] = Operations.getRandomNumberInRange(0, 9);
            } else {
                digits[i] = Operations.getRandomNumberInRange(1, 9);
            }
        }
        return new Number(digits, false);
    }

    public static boolean fermatTest(Number number, int k) throws NegativeNumberException {
        Number a;
        int numberLength = number.getDigits().length;
        for (int i = 0; i < k; i++) {
            a = generateNumber(1, numberLength - 1);
            System.out.println(a);
            if (!a.modPower(number.subtract(Number.ONE), number).equals(Number.ONE)) {
                return false;
            }
            System.out.println(a);
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
