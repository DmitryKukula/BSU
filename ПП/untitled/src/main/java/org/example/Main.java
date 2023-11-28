package org.example;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws Exception {

        Scanner console_in = new Scanner(System.in);

        System.out.print("Write the name of input file: ");
        String input_file= console_in.next();
        byte[] bytes = new byte[4096];

        System.out.println("Does file archived? (Yes/No)");
        String temp_arg = console_in.next();
        if (temp_arg.equals("Yes") || temp_arg.equals("yes")) {
            bytes = Archive.dataToReadArchive(input_file);
        } else {
            bytes = Reading_file.readFileTXTToBytes(input_file, ConstantVariable.PATH_IN);
        }

        String content = new String(bytes);
        System.out.println("Content:\n" + content);
        System.out.println(bytes.length);

        System.out.println("\nIs the file encrypted? (Yes/No):");
        temp_arg = console_in.next();
        if (temp_arg.equals("Yes") || temp_arg.equals("yes")) {
            try {
                bytes = Encrypt_file_AES.decrypt(bytes);
                String decrContent = new String(bytes);
                System.out.println(decrContent + "\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        bytes = FilterBytes.filterBytes(bytes);
        content = new String(bytes);
        System.out.println("Decrypted content:\n" + content);
        System.out.println(bytes.length);
        byte[] resultBytes = MathExpressionEvaluator.calculate(content);

        System.out.println("Write the name of output file: ");
        String nameOutputFile = console_in.next();
        int dotIndex = nameOutputFile.indexOf('.');
        String substringAfterDot = nameOutputFile.substring(dotIndex + 1);
        //Output.writeBytesToFileTXT(nameOutputFile, resultBytes);
        switch (substringAfterDot){
            case "txt":
                break;
            case "json":
                resultBytes = Output.convertBytesForJSON(resultBytes);
                break;
            case "xml":
                resultBytes = Output.convertBytesForXML(resultBytes);
                break;
        }

        System.out.println("Encrypt the response?: (Yes/No)");
        String answer = console_in.next();
        if (answer.equals("Yes") || answer.equals("yes")) {
            resultBytes = Encrypt_file_AES.encrypt(resultBytes);
        }

        System.out.println("Do archive this file?: (Yes/No)");
        answer = console_in.next();
        if (answer.equals("Yes") || answer.equals("yes")) {
            System.out.println("Write the name of output Archive: ");
            String nameOutputArchive = console_in.next();
            Archive.recordArchive(resultBytes, ConstantVariable.PATH_OUT + nameOutputArchive, nameOutputFile);
        } else {
            Output.writeBytesToFile(nameOutputFile, ConstantVariable.PATH_OUT, resultBytes);
        }
        console_in.close();
    }
}