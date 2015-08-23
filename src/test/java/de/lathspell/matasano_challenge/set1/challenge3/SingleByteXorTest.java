package de.lathspell.matasano_challenge.set1.challenge3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import de.lathspell.matasano_challenge.Utils;

public class SingleByteXorTest {

    public static final Logger log = LoggerFactory.getLogger(SingleByteXorTest.class);

    @Test
    public void testSingleByteXor() {
        assertArrayEquals(new byte[]{'S'}, SingleByteXor.xor(new byte[]{'A'}, (byte) 0x12));
        assertArrayEquals(new byte[]{'u', 'v', 0x07}, SingleByteXor.xor(new byte[]{'A', 'B', '3'}, (byte) 0x34));
    }

    @Test
    public void challenge2() throws Exception {
        String ciphertext = "1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736";
        byte[] cipherbytes = Utils.hex2bytes(ciphertext);

        List<Result> winners = new ArrayList<>();
        for (int i = 0; i <= 255; i++) {
            byte[] plaintextBytes = SingleByteXor.xor(cipherbytes, (byte) i);
            String plaintext = new String(plaintextBytes);
            int numE = Utils.scoreEnglishText(plaintextBytes);
            winners.add(new Result(i, numE, plaintextBytes, numE > 0 ? plaintext : ""));
        }

        // Order by number of occurances of the letter 'e' and show all candidates
        Collections.sort(winners, (Result o1, Result o2) -> Integer.compare(o2.numE, o1.numE));
        // log.debug("Results: " + new ObjectMapper().enable(INDENT_OUTPUT).writeValueAsString(winners));

        assertEquals(88, winners.get(0).key);
        assertEquals("Cooking MC's like a pound of bacon", winners.get(0).plaintext);

    }

    private class Result {

        public int key;

        public int numE;

        public byte[] plaintextBytes;

        public String plaintext;

        public Result(int key, int numE, byte[] plaintextBytes, String plaintext) {
            this.key = key;
            this.numE = numE;
            this.plaintextBytes = plaintextBytes;
            this.plaintext = plaintext;
        }

    }

    private int countChar(byte[] bytes, char c) {
        int num = 0;
        for (byte b : bytes) {
            if (b == c) {
                num++;
            }
        }
        return num;
    }
}
