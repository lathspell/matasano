package de.lathspell.matasano_challenge.set1.challenge6;

public class HammingDistance {

    /**
     * Calculates the Hamming distance, which is the number of differing bits.
     *
     * @param a
     * @param b
     * @return Number of differing bits.
     */
    public static int calcHammingDistance(byte[] a, byte[] b) {
        if (a == null || b == null || a.length != b.length) {
            throw new RuntimeException("Needs equal-length input!");
        }
        int[] bitmasks = new int[]{1, 2, 4, 8, 16, 32, 64, 128};

        int result = 0;
        for (int pos = 0; pos < a.length; pos++) {
            for (int bit = 0; bit < bitmasks.length; bit++) {
                int bitmask = bitmasks[bit];
                result += (((a[pos] & bitmask) == (b[pos] & bitmask)) ? 0 : 1);
            }
        }

        return result;

    }
}
