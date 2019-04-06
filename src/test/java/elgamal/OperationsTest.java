package elgamal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OperationsTest {

    @Test
    void mod() {
        assertEquals(0, Operations.mod("4", 2));
        assertEquals(1, Operations.mod("1312312312321412413908123908131", 10));
    }
}