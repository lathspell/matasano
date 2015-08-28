package de.lathspell.matasano_challenge.set1.challenge5;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import de.lathspell.matasano_challenge.Utils;

public class RepeatedKeyXorTest {

    @Test
    public void testSet1Challenge5() {
        String plaintext
                = "Burning 'em, if you ain't quick and nimble\n"
                + "I go crazy when I hear a cymbal";
        String password = "ICE";
        String expectedHex
                = "0b3637272a2b2e63622c2e69692a23693a2a3c6324202d623d63343c2a26226324272765272"
                + "a282b2f20430a652e2c652a3124333a653e2b2027630c692b20283165286326302e27282f";

        byte[] actual = RepeatedKeyXor.xor(plaintext.getBytes(), password.getBytes());

        assertEquals(expectedHex, Utils.bytes2hex(actual));
    }
}
