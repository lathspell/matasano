package de.lathspell.matasano_challenge.set1.challenge5;

public class RepeatedKeyXor {

    public static byte[] xor(byte[] plaintext, byte[] key) {
        byte[] result = new byte[plaintext.length];
        int k = 0;
        for (int i = 0; i < plaintext.length; i++) {
            result[i] = (byte) (plaintext[i] ^ key[k++ % key.length]);
        }
        return result;
    }
}
