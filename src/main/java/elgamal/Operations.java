package elgamal;

public class Operations {
    static int mod(String number, int a) {
        int output = 0;
        
        for (int i = 0; i < number.length(); i++)
            output = (output * 10 + (int)number.charAt(i) - '0') % a;

        return output;
    }
}
