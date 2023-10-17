import java.io.*;
import java.util.Scanner;
import java.lang.String;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner console_in = new Scanner(System.in);

        System.out.print("Write the name of input file: ");
        String input_file_name = console_in.next();
        System.out.print("Write the input file extension: ");
        String input_file_ext = console_in.next();
        String input_file = input_file_name + "." + input_file_ext;
        System.out.println("Yours input file is " + input_file + "\n");

        System.out.print("Is the file archived? (Yes/No):");
        String temp_arg = console_in.next();

        //String extension_archive = null;
        if (temp_arg.equals("Yes")){
            Archive.unarchiving();
        }

        System.out.print("Write the name of output file: ");
        String output_file_name = console_in.next();
        System.out.print("Write the output file extension: ");
        String output_file_ext = console_in.next();
        String output_file = output_file_name + "." + output_file_ext;
        System.out.println("Yours output file is " + output_file);

        String content = reading_file.file_content(input_file, output_file);
        //System.out.print("Expressions:\n" + content);

        System.out.print("Archive the file?\t(Yes/No): ");
        temp_arg = console_in.next();
        if (temp_arg.equals("Yes")) {
            Archive.archiving(output_file);
        }
        console_in.close();
    }
}