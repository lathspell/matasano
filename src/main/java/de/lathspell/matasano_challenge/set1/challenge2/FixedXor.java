package de.lathspell.matasano_challenge.set1.challenge2;

public class FixedXor {

    public static byte[] fixedXor(byte[] a, byte[] b) {
        if (a.length != b.length) {
            throw new RuntimeException("a.length != b.length");
        }

        byte[] x = new byte[a.length];

        for (int i = 0; i < a.length; i++) {
            x[i] = (byte) (a[i] ^ b[i]);
        }

        return x;
    }
}
