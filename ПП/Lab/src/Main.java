import java.io.*;
import java.util.Scanner;
import java.lang.String;

public class Main {

    public static void main(String[] args) throws IOException {
        File theDir = new File(ConstantVariable.PATH_TEMP);
        if (!theDir.exists()) {
            theDir.mkdirs();
        } else {
            theDir.delete();
            theDir.mkdirs();
        }

        Scanner console_in = new Scanner(System.in);

        System.out.print("Write the name of input file: ");
        String input_file_name = console_in.next();
        System.out.print("Write the extension of input file: ");
        String input_file_ext = console_in.next();
        String input_file = input_file_name + "." + input_file_ext;
        System.out.println("Yours input file is " + input_file + "\n");

        System.out.print("Is the file archived? (Yes/No):");
        String temp_arg = console_in.next();
        String unarchive_content = "";
        if (temp_arg.equals("Yes")) {
            unarchive_content = Archive.unarchiving(input_file);
        } else {
            String filePath = ConstantVariable.PATH_IN + input_file;

            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    unarchive_content += line;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.print("\nIs the file encrypted? (Yes/No):");
        temp_arg = console_in.next();
        if (temp_arg.equals("Yes")) {
            EncryptFile.decryptFile(input_file);
        }

        System.out.print("Write the name of output file: ");
        String output_file = console_in.next();
        System.out.println("Yours output file is " + output_file + "\n");

        reading_file.file_content(input_file, output_file);

        System.out.print("Archive the file?\t(Yes/No): ");
        temp_arg = console_in.next();
        if (temp_arg.equals("Yes")) {
            Archive.archiving(output_file);
        }

        theDir.delete();
        console_in.close();
    }
}