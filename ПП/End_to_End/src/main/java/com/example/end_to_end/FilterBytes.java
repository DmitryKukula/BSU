package com.example.end_to_end;

import java.util.ArrayList;
import java.util.List;

public class FilterBytes {
    public static byte[] filterBytes(byte[] originalBytes) {
        List<Byte> filteredList = new ArrayList<>();
        boolean lastRight = false;
        final byte sep = '\n';
        boolean lastOperator = false;
        for (byte b : originalBytes) {
            if (b == ' ' && !Character.isDigit(b) && !isArithmeticOperator(b)) {
                continue;
            }

            if (Character.isDigit((char) b) || (char) b == '(') {
                filteredList.add(b);
                lastRight = true;
                lastOperator = false;
            } else if (isArithmeticOperator(b) || b == '(' || b == ')') {
                if (lastRight && !lastOperator) {
                    filteredList.add(b);
                    lastOperator = true;
                    if (b == '(' || b == ')'){
                        lastOperator = false;
                    }
                } else if (lastOperator) {
                    filteredList.remove(filteredList.size() - 1);
                    filteredList.add(b);
                }
            } else if (lastRight) {
                filteredList.add(sep);
                lastRight = false;
                lastOperator = false;
            }
        }

        byte[] filteredBytes = new byte[filteredList.size()];
        for (int i = 0; i < filteredList.size(); i++) {
            filteredBytes[i] = filteredList.get(i);
        }
        return filteredBytes;
    }
    private static boolean isArithmeticOperator(byte b) {
        return b == '+' || b == '-' || b == '*' || b == '/';
    }
}
