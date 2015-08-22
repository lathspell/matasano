package de.lathspell.matasano_challenge;

public class Utils {

    public static byte[] hex2bytes(String plaintextHex) {
        if (plaintextHex == null) {
            throw new RuntimeException("Hex string is null!");
        }
        String plaintextHexLower = plaintextHex.toLowerCase();
        if (!plaintextHexLower.matches("^([0-9a-f]{2})+$")) {
            throw new RuntimeException("Invalid hex string!");
        }
        byte[] bytes = new byte[plaintextHex.length() / 2];
        for (int i = 0; i < plaintextHex.length() / 2; i++) {
            char char1 = plaintextHexLower.charAt(i * 2);
            char char2 = plaintextHexLower.charAt(i * 2 + 1);
            byte byte1 = (byte) ((char1 >= '0' && char1 <= '9') ? (char1 - '0') : (char1 - 'a' + 10));
            byte byte2 = (byte) ((char2 >= '0' && char2 <= '9') ? (char2 - '0') : (char2 - 'a' + 10));
            bytes[i] = (byte) (byte1 * 16 + byte2);
        }
        return bytes;
    }

    public static String bytes2hex(byte[] ba) {
        StringBuilder sb = new StringBuilder();

        for (byte b : ba) {
            // beware of Java's lack of unsigned byte and the
            // implicit expansion from signed byte to signed int!
            int hi = (b & 0xff) >>> 4;
            char c1 = (char) (hi <= 9 ? ('0' + hi) : ('A' + hi - 10));
            sb.append(c1);

            int lo = (b & 0x0F);
            char c2 = (char) (lo <= 9 ? ('0' + lo) : ('A' + lo - 10));
            sb.append(c2);
        }

        return sb.toString();
    }

}
