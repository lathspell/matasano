package de.lathspell.matasano_challenge.set1.challenge4;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import de.lathspell.matasano_challenge.Utils;
import de.lathspell.matasano_challenge.set1.challenge3.SingleByteXor;

public class DetectSingleByteXorTest {

    private static final String DIR = "src/test/java/" + DetectSingleByteXorTest.class.getPackage().getName().replace(".", "/") + "/";

    @Test
    public void testSet1Challenge4() throws Exception {
        List<String> lines = FileUtils.readLines(new File(DIR + "/4.txt"));
        assertEquals(327, lines.size());

        String decoded = tryAllCombinations(lines);
        assertEquals("Now that the party is jumping\n", decoded);
    }

    private String tryAllCombinations(List<String> lines) {
        for (String line : lines) {
            byte[] encoded = Utils.hex2bytes(line);
            for (int i = 0; i <= 255; i++) {
                byte[] decoded = SingleByteXor.xor(encoded, (byte) i);

                // apply my fuzzy English language detection...
                int score = Utils.scoreEnglishText(decoded);
                int printable = Utils.countPrintableAsciiCharacters(decoded, false);
                int nonPrintable = Utils.countPrintableAsciiCharacters(decoded, true);
                int numSpaces = Utils.countSpaces(decoded);
                if (score >= 5 && printable == 30 && nonPrintable == 0 && numSpaces >= 3) {
                    return new String(decoded);
                }
            }
        }
        return null;
    }
}
