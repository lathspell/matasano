package de.lathspell.matasano_challenge.set1.challenge1;

public class MyBase64 {

    public static final char[] BASE64_CHARSET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();

    /**
     * Encodes binary bytes to base64 ASCII.
     *
     * Every 6 bits of input produce one char output. That char is transposed
     * into the ASCII characters A-Z, a-z, 0-9, '/', and '+'.
     * To make encoding easier, 3 8-bit bytes are encoded into 4 6-bit chars.
     * If the input string was to short, one or two zero bytes are appended
     * and encoded as "=" to mark them for decoding.
     *
     * @see https://tools.ietf.org/html/rfc4648
     *
     * @param plaintext
     * @return
     */
    public static String encodeBase64(byte[] plaintext) {
        int numPadding = ((plaintext.length % 3) == 0) ? 0 : (3 - (plaintext.length % 3));

        byte[] padded = new byte[plaintext.length + numPadding];
        System.arraycopy(plaintext, 0, padded, 0, plaintext.length);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < padded.length; i += 3) {
            char chr1 = (char) (padded[i] >> 2);
            char chr2 = (char) (((padded[i] & 0x03) << 4) + (padded[i + 1] >> 4));
            char chr3 = (char) (((padded[i + 1] & 0x0F) << 2) + ((padded[i + 2] & 0xC0) >> 6));
            char chr4 = (char) ((padded[i + 2] & 0x3F));
            sb.append(BASE64_CHARSET[chr1]).append(BASE64_CHARSET[chr2]).append(BASE64_CHARSET[chr3]).append(BASE64_CHARSET[chr4]);
        }

        if (numPadding == 2) {
            sb.replace(sb.length() - numPadding, sb.length(), "==");
        } else if (numPadding == 1) {
            sb.replace(sb.length() - numPadding, sb.length(), "=");
        }

        return sb.toString();
    }
}
