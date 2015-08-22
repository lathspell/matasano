package de.lathspell.matasano_challenge;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class UtilsTest {

    @Test
    public void testHex2Bytes() {
        byte[] expected = new byte[]{(byte) 0x20, (byte) 0x00, (byte) 0x42, (byte) 0xFF};
        byte[] actual = Utils.hex2bytes("200042FF");
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testBytes2Hex() {
        String expected = "200042FF";
        String actual = Utils.bytes2hex(new byte[]{(byte) 0x20, (byte) 0x00, (byte) 0x42, (byte) 0xFF});
        assertEquals(expected, actual);

        assertEquals("96", Utils.bytes2hex(new byte[] { (byte)0x96 }));
    }
}
