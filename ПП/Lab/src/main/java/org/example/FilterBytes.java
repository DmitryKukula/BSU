package org.example;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilterBytes {
    public static byte[] filterBytes(byte[] originalBytes) {
        List<Byte> filteredList = new ArrayList<>();
        boolean lastRight = false;
        byte sep = '\n';
        for (byte b : originalBytes) {
            if (b == ' '){
                continue;
            }
            if (Character.isDigit((char) b)) {
                filteredList.add(b);
                lastRight = true;
            } else if (isArithmeticOperator(b)) {
                if (lastRight) {
                    filteredList.add(b);
                }
            } else if (lastRight) {
                filteredList.add(sep);
                lastRight = false;
            }
            else{
                lastRight = false;
            }
        }

        byte[] filteredBytes = new byte[filteredList.size()];
        for (int i = 0; i < filteredList.size(); i++) {
            filteredBytes[i] = filteredList.get(i);
        }
//        String[] lines = new String(filteredBytes, StandardCharsets.UTF_8).split("\n");
//        filteredBytes = FilterBytes.removeEmptyStrings(lines);
        return filteredBytes;
    }
    private static boolean isArithmeticOperator(byte b) {
        return b == '+' || b == '-' || b == '*' || b == '/' || b == '(' || b == ')';
    }
    private static byte[] removeEmptyStrings(String[] lines) {
        String[] nonEmptyLines = Arrays.stream(lines)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);
        String resultString = String.join("\n", nonEmptyLines);
        return resultString.getBytes(StandardCharsets.UTF_8);
    }
}
