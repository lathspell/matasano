package de.lathspell.matasano_challenge.set1.challenge3;

public class SingleByteXor {

    /**
     * Return cipherBytes with every byte XOR'ed with key.
     *
     * @param cipherBytes
     * @param key
     * @return New byte[] with xor'ed bytes.
     */
    public static byte[] xor(byte[] cipherBytes, byte key) {
        byte[] decodedBytes = new byte[cipherBytes.length];

        for (int i = 0; i < cipherBytes.length; i++) {
            decodedBytes[i] = (byte) (cipherBytes[i] ^ key);
        }

        return decodedBytes;
    }

}
