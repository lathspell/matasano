package de.lathspell.matasano_challenge.set1.challenge1;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import de.lathspell.matasano_challenge.set1.challenge2.FixedXor;

import static de.lathspell.matasano_challenge.Utils.bytes2hex;
import static de.lathspell.matasano_challenge.Utils.hex2bytes;


public class FixedXorTest {

    @Test
    public void testFixedXor() {
        String aHex = "1c0111001f010100061a024b53535009181c".toUpperCase();
        String bHex = "686974207468652062756c6c277320657965".toUpperCase();
        String expected = "746865206b696420646f6e277420706c6179".toUpperCase();
        byte[] expectedBytes = hex2bytes(expected);

        byte[] aBytes = hex2bytes(aHex);
        byte[] bBytes = hex2bytes(bHex);
        byte[] actualBytes = FixedXor.fixedXor(aBytes, bBytes);
        assertArrayEquals(expectedBytes, actualBytes);
        String actual = bytes2hex(actualBytes);
        assertEquals(expected, actual);
    }

}
