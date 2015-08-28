package de.lathspell.matasano_challenge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {

    private static final Logger log = LoggerFactory.getLogger(Utils.class);

    /**
     * Guess if the input is an English text.
     *
     * More ideas on https://de.wikipedia.org/wiki/ETAOIN_SHRDLU
     *
     * @param bytes
     * @return The higher the score, the more likely it was English.
     */
    public static int scoreEnglishText(byte[] bytes) {
        boolean noNonUsAscii = true;
        boolean hasSpace = false;
        int[] charmap = new int[256];

        for (byte b : bytes) {
            if (b < 0x20 || b > 0x7E) {
                noNonUsAscii = false;
            }
            if (b == ' ') {
                hasSpace = true;
            }
            // log.info("adding: " + b);
            charmap[b & 0xFF]++;
        }

        // Get top5 characters
        List<Integer[]> sortedCharmap = new ArrayList<>();
        for (int i = 0; i < charmap.length; i++) {
            sortedCharmap.add(new Integer[]{i, charmap[i]});
        }
        Collections.sort(sortedCharmap, (Integer[] o1, Integer[] o2) -> Integer.compare(o2[1], o1[1]));
        // log.debug("sortedCharmap: " + sortedCharmap);
        List<Integer[]> topCharmap = sortedCharmap.subList(0, 10);
        // log.debug("topCharmap: " + topCharmap);

        int score = 0;
        if (noNonUsAscii) {
            score++;
        }
        if (hasSpace) {
            score += 2;
        }
        // log.debug("scores for noNonUsAscii={} hasSpace={}", noNonUsAscii, hasSpace);

        for (int c : Arrays.asList('e', 'E', 'a', 'A', 'i', 'I')) {
            // log.debug("Scanning for: " + (char) c);
            for (Integer[] entry : topCharmap) {
                if (entry[1] > 0) {
                    // log.debug("  Comparing with: " + (char) (entry[0].byteValue()) + ": " + entry[1]);
                    if (entry[0] == c) {
                        score++;
                        // log.debug("    score!");
                        break;
                    }
                }
            }
        }
        // log.debug("score after topCharmap: " + score);

        return score;
    }

    public static int countPrintableAsciiCharacters(byte[] bytes, boolean inverse) {
        int c = 0;
        for (byte b : bytes) {
            if (inverse ^ ((b >= ' ' && b <= '~') || b == '\n' || b == '\r')) {
                c++;
            }
        }
        return c;
    }

    public static int countSpaces(byte[] bytes) {
        int c = 0;
        for (byte b : bytes) {
            if (b == ' ') {
                c++;
            }
        }
        return c;
    }

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
            char c1 = (char) (hi <= 9 ? ('0' + hi) : ('a' + hi - 10));
            sb.append(c1);

            int lo = (b & 0x0F);
            char c2 = (char) (lo <= 9 ? ('0' + lo) : ('a' + lo - 10));
            sb.append(c2);
        }

        return sb.toString();
    }

}
