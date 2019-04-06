package elgamal;

import java.util.Random;

public class Operations {
    static int mod(String number, int a) {
        int output = 0;
        
        for (int i = 0; i < number.length(); i++) {
            output = (output * 10 + (int)number.charAt(i) - '0') % a;
        }
        return output;
    }

    public static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
