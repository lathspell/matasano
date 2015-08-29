package de.lathspell.matasano_challenge.set1.challenge6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.lathspell.matasano_challenge.Utils;
import de.lathspell.matasano_challenge.set1.challenge3.SingleByteXor;
import de.lathspell.matasano_challenge.set1.challenge5.RepeatedKeyXor;

import static de.lathspell.matasano_challenge.set1.challenge6.HammingDistance.calcHammingDistance;

public class BreakRepeatedKeyXor {

    public static class Result {

        public byte[] key;

        public byte[] decrypted;

        int score;

    }

    public Result guess(byte[] encrypted) throws Exception {
        List<Result> results = new ArrayList<>();

        List<Integer> keysizes = guessKeysize(encrypted, 2, 40, 1);
        for (int keysize : keysizes) {
            results.add(guess(encrypted, keysize));
        }
        System.out.println("#42# results: " + new ObjectMapper().writeValueAsString(results));

        results.sort(new Comparator<Result>() {

            @Override
            public int compare(Result o1, Result o2) {
                return Integer.compare(o1.score, o2.score);
            }
        });

        System.out.println("#42# results: " + new ObjectMapper().writeValueAsString(results));

        return results.get(0);
    }

    public Result guess(byte[] encrypted, int keysize) throws Exception {
        byte[] key = new byte[keysize];
        byte[][] chunks = splitBytesIntoChunks(encrypted, keysize);
        for (int keyPos = 0; keyPos < keysize; keyPos++) {
            byte[] row = getRow(chunks, keyPos);
            key[keyPos] = guessSingleByteXor(row);
        }

        Result r = new Result();
        r.key = key;
        r.decrypted = RepeatedKeyXor.xor(encrypted, r.key);
        r.score = Utils.countPrintableAsciiCharacters(r.decrypted, false);
        return r;
    }

    public byte guessSingleByteXor(byte[] encrypted) {
        int winnerScore = -1;
        byte winner = 0;
        for (int i = 0; i <= 255; i++) {
            byte[] decrypted = SingleByteXor.xor(encrypted, (byte) i);
            int score = Utils.countPrintableAsciiCharacters(decrypted, false);
            System.out.printf("i=%d score=%d descrpted=%s %s\n", i, score, Arrays.toString(decrypted), new String(decrypted));
            if (winnerScore < score) {
                winnerScore = score;
                winner = (byte) i;
            }
        }
        return winner;
    }

    public byte[] getRow(byte[][] chunks, int keyPos) {
        byte[] row = new byte[chunks[0].length];
        for (int i = 0; i < chunks[0].length; i++) {
            row[i] = chunks[i][keyPos];
        }
        return row;
    }

    public byte[][] splitBytesIntoChunks(byte[] bytes, int size) throws Exception {
        byte[][] result = new byte[bytes.length / size + 1][size];
        for (int i = 0; i < bytes.length / size + 1; i++) {
            result[i] = Arrays.copyOfRange(bytes, i * size, i * size + size);
        }
        return result;
    }

    public List<Integer> guessKeysize(byte[] encrypted, int min, int max, int num) {
        if (num > max - min + 1) {
            throw new RuntimeException("num > max-min+1!");
        }

        TreeMap<Double, Integer> results = new TreeMap<>();
        for (int keysize = min; keysize <= max; keysize++) {
            byte[] block1 = Arrays.copyOfRange(encrypted, 0, keysize);
            byte[] block2 = Arrays.copyOfRange(encrypted, keysize, 2 * keysize);
            int hammingDistance = calcHammingDistance(block1, block2);
            double normalizedHammingDistance = (double) hammingDistance / keysize;
            results.put(normalizedHammingDistance, keysize);
        }

        int effectiveNum = results.size() < num ? results.size() : num;
        List<Integer> top = new ArrayList<>();
        results.descendingMap().values().stream().limit(effectiveNum).forEach((i) -> {
            top.add(i);
        });
        System.out.println("top: " + top);
        return top;
    }
}
