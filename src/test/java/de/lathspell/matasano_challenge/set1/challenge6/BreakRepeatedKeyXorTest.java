package de.lathspell.matasano_challenge.set1.challenge6;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import de.lathspell.matasano_challenge.set1.challenge1.MyBase64;
import de.lathspell.matasano_challenge.set1.challenge3.SingleByteXor;
import de.lathspell.matasano_challenge.set1.challenge6.BreakRepeatedKeyXor.Result;

import static de.lathspell.matasano_challenge.Utils.bytes2hex;

public class BreakRepeatedKeyXorTest {

    private static final String DIR = "src/test/java/" + BreakRepeatedKeyXorTest.class.getPackage().getName().replace('.', '/');

    private final byte[] encrypted;

    private BreakRepeatedKeyXor breaker;

    public BreakRepeatedKeyXorTest() throws Exception {
        String encryptedBase64 = FileUtils.readFileToString(new File(DIR + "/6.txt"));
        encrypted = MyBase64.decodeBase64(encryptedBase64);
    }

    @Before
    public void before() {
        breaker = new BreakRepeatedKeyXor();
    }

    @Test
    public void testHammeringDistance() {
        assertEquals(0, HammingDistance.calcHammingDistance("abc".getBytes(), "abc".getBytes()));
        assertEquals(2, HammingDistance.calcHammingDistance("abc".getBytes(), "adc".getBytes()));
        assertEquals(3, HammingDistance.calcHammingDistance("abc".getBytes(), "ada".getBytes()));
        assertEquals(37, HammingDistance.calcHammingDistance("this is a test".getBytes(), "wokka wokka!!!".getBytes()));
    }

    @Test
    public void testGuessKeySizes() {
        List<Integer> expected = Arrays.asList(6, 37, 32, 28, 27);
        List<Integer> actual = breaker.guessKeysize(encrypted, 2, 40, 5);
        assertEquals(expected, actual);
    }

    @Test
    public void testSplitBytesIntoChunks() throws Exception {
        byte[][] result = breaker.splitBytesIntoChunks(encrypted, 6);
        assertEquals(488, result.length);
        assertArrayEquals(new byte[]{88, 0, 0, 0, 0, 0}, result[487]);
    }

    @Test
    public void testGuessSingleByteXor() {
        byte key = 42;
        byte[] plaintext = "secret".getBytes();
        byte[] xored = SingleByteXor.xor(plaintext, key);
        assertEquals(key, breaker.guessSingleByteXor(xored));
    }

    @Test
    public void testSet1Challenge6() throws Exception {
        Result result = breaker.guess(encrypted);
        assertEquals("FIXME", bytes2hex(result.key));
        assertEquals("FIXME", bytes2hex(result.decrypted));
    }

}
