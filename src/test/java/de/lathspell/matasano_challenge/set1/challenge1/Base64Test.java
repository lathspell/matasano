package de.lathspell.matasano_challenge.set1.challenge1;

import java.util.Arrays;

import org.apache.commons.codec.binary.Base64;
import org.junit.Assert;
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
    public void testEncodeWithPadding() {
        assertEquals(Base64.encodeBase64String(new byte[]{0x00, 0x10, 0x0F}), MyBase64.encodeBase64(new byte[]{0x00, 0x10, 0x0F}));
        assertEquals(Base64.encodeBase64String(new byte[]{0x00, 0x10, 0x0F, 0x42}), MyBase64.encodeBase64(new byte[]{0x00, 0x10, 0x0F, 0x42}));
        assertEquals(Base64.encodeBase64String(new byte[]{0x00, 0x10, 0x0F, 0x42, 0x04}), MyBase64.encodeBase64(new byte[]{0x00, 0x10, 0x0F, 0x42, 0x04}));
    }

    @Test
    public void testDecodeWithPadding() {
        String[] tests = new String[]{
            Base64.encodeBase64String(new byte[]{(byte)0xE0, (byte)0xAB, (byte)0xCD, (byte)0xEF}),
            Base64.encodeBase64String(new byte[]{0x00, 0x10, 0x0F}),
            Base64.encodeBase64String(new byte[]{0x00, 0x10, 0x0F, 0x42}),
            Base64.encodeBase64String(new byte[]{0x00, 0x10, 0x0F, 0x42, 0x04}),
            Base64.encodeBase64String("Hello World".getBytes())
        };
        for (String test : tests) {
            Assert.assertArrayEquals("Failed decoding: " + test + " aka " + Arrays.toString(Base64.decodeBase64(test)), Base64.decodeBase64(test), MyBase64.decodeBase64(test));
        }
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
