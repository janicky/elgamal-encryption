import aes.Key;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KeyTest {

    private Key key;

    @BeforeEach
    void initialize() {
        key = new Key("abcdefghijklmnop");
    }

    @Test
    void getRounds() {
        assertEquals(10, key.getRounds());
    }
}