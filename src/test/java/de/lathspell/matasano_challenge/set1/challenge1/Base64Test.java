package de.lathspell.matasano_challenge.set1.challenge1;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import de.lathspell.matasano_challenge.Utils;

public class Base64Test {

    @Test
    public void testByteAndChar() {
        byte b = 0x41;
        char c = (char) b;
        assertEquals('A', c);
    }

    @Test
    public void testPadding() {
        assertEquals(Base64.encodeBase64String(new byte[]{0x00, 0x10, 0x0F}), MyBase64.encodeBase64(new byte[]{0x00, 0x10, 0x0F}));
        assertEquals(Base64.encodeBase64String(new byte[]{0x00, 0x10, 0x0F, 0x42}), MyBase64.encodeBase64(new byte[]{0x00, 0x10, 0x0F, 0x42}));
        assertEquals(Base64.encodeBase64String(new byte[]{0x00, 0x10, 0x0F, 0x42, 0x04}), MyBase64.encodeBase64(new byte[]{0x00, 0x10, 0x0F, 0x42, 0x04}));
    }

    @Test
    public void testChallenge() {
        String plaintextHex = "49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d";
        String expected = "SSdtIGtpbGxpbmcgeW91ciBicmFpbiBsaWtlIGEgcG9pc29ub3VzIG11c2hyb29t";

        byte[] plaintextBytes = Utils.hex2bytes(plaintextHex);
        String actual = MyBase64.encodeBase64(plaintextBytes);
        assertEquals(expected, actual);
    }
}
