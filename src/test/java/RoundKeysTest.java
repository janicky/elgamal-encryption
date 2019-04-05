import elgamal.Constants;
import elgamal.Key;
import elgamal.RoundKeys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoundKeysTest {
    private RoundKeys roundKeys;
    private String keyString = "1234567890123456";
    private Key key;

    @BeforeEach
    void initialize() {
        key = new Key(keyString);
        roundKeys = new RoundKeys(key);
    }

    @Test
    void getKeys() {
        short[][] keys = roundKeys.getKeys();
        for (int i = 0; i < key.getLength() / Constants.BLOCK_SIZE; i++) {
            for (int j = 0; j < Constants.BLOCK_SIZE; j++) {
                assertEquals(keyString.toCharArray()[Constants.BLOCK_SIZE * i + j], keys[i][j]);
            }
        }
    }

    @Test
    void getRoundConstants() {
        short[][] roundConstants = roundKeys.getRoundConstants();
        for (int i = 0; i < key.getRounds(); i++) {
            assertEquals(Constants.RCON[i], roundConstants[0][i]);
            for (int j = 1; j < Constants.BLOCK_SIZE; j++) {
                assertEquals(0x0, roundConstants[j][i]);
            }
        }
    }

}