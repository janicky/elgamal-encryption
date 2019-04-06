package elgamal;

public class KeyGenerator {

    String p;
    String g;
    String a;

    public void generate() {
        p = generateP();
        g = generateG(p);
        a = generateG(p);
    }

    private String generateP() {
//        TODO: Make it prime number
        StringBuilder sb = new StringBuilder();
        int digitsNumber = Operations.getRandomNumberInRange(310, 320);
        for (int i = 0; i < digitsNumber; i++) {
            if (i > 0) {
                sb.append(Operations.getRandomNumberInRange(0, 9));
            } else {
                sb.append(Operations.getRandomNumberInRange(1, 9));
            }
        }
        return sb.toString();
    }

    private String generateG(final String p) {
        StringBuilder sb = new StringBuilder();
        int digitsShift = Operations.getRandomNumberInRange(2, 5);
        for (int i = 0; i < p.length() - digitsShift; i++) {
            if (i > 1) {
                sb.append(Operations.getRandomNumberInRange(0, 9));
            } else {
                sb.append(p.charAt(i));
            }
        }
        return sb.toString();
    }
}
