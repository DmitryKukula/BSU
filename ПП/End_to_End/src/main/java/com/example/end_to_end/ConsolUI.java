package com.example.end_to_end;

import java.util.Scanner;

class ConsolUI {
    public static void start() throws Exception {
        Scanner cin = new Scanner(System.in);
        ProgramBuilder builder = new ProgramBuilder();
        String nameFile = "";
        while (true) {
            System.out.print("Enter the name of the source file: ");
            nameFile = cin.nextLine();
            if (!CheckingInputData.checkNameFile(nameFile)) {
                System.err.println("Incorrect file name! Press ENTER to retry");
                cin.nextLine();
            } else if (!CheckingInputData.isFileExistsInDirectory(nameFile)) {
                System.err.println("There is no such file! Press ENTER to retry");
                cin.nextLine();
            } else {
                break;
            }
        }
        builder.setInFileName(nameFile);

        System.out.println("Is the file archived? (Yes/No)");
        String temp = cin.nextLine();
        if (temp.equals("Yes") || temp.equals("yes")){
            builder.setCheckArch(true);
            String nameZip = "";
            while (!CheckingInputData.checkNameArchive(nameZip) || !CheckingInputData.isArchiveExistsInDirectory(nameZip)) {
                System.out.print("Enter the name of the archive: ");
                nameZip = cin.nextLine();
                if (!CheckingInputData.checkNameArchive(nameZip)){
                    System.err.println("Incorrect archive name! Press ENTER to retry");
                    cin.nextLine();
                }
                else if(!CheckingInputData.isArchiveExistsInDirectory(nameZip)){
                    System.err.println("There is no such archive! Press ENTER to retry");
                    cin.nextLine();
                }
            }
            builder.setInZipArchive(nameZip);
        }

        System.out.println("Is the file encrypt? (Yes/No)");
        temp = cin.nextLine();
        if (temp.equals("Yes") || temp.equals("yes")) {
            builder.setCheckEncr(true);
        }

        System.out.println("Should I use the library for counting? (Yes/No)");
        temp = cin.nextLine();
        if (temp.equals("Yes") || temp.equals("yes")) {
            builder.setUseLibCounting(true);
        }



        nameFile = "";
        while (!CheckingInputData.checkNameFile(nameFile)) {
            System.out.print("Enter the name of the output file: ");
            nameFile = cin.nextLine();
            if (!CheckingInputData.checkNameFile(nameFile)){
                System.err.println("Incorrect file name!  Press ENTER to retry");
                cin.nextLine();
            }
        }
        builder.setOutFileName(nameFile);

        System.out.println("Do I need to archive a file? (Yes/No)");
        temp = cin.nextLine();
        if (temp.equals("Yes") || temp.equals("yes")){
            builder.setCheckArch1(true);
            String nameZip = "";
            while (!CheckingInputData.checkNameArchive(nameZip)) {
                System.out.print("Enter the name of the archive: ");
                nameZip = cin.nextLine();
                if (!CheckingInputData.checkNameArchive(nameZip)){
                    System.err.println("Incorrect archive name!  Press ENTER to retry");
                    cin.nextLine();
                }
            }
            builder.setOutZipArchive(nameZip);
        }

        System.out.println("Is the file encrypt? (Yes/No)");
        temp = cin.nextLine();
        if (temp.equals("Yes") || temp.equals("yes")) {
            builder.setCheckEncr1(true);
        }

        builder.runProgram();
        System.out.println("The operation was successful");
        cin.close();
    }
}
