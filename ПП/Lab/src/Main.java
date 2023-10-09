import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.String;
import java.util.zip.*;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner console_in = new Scanner(System.in);

        System.out.print("Write the name of input file: ");
        String input_file_name = console_in.next();
        System.out.print("write the input file extension: ");
        String input_file_ext = console_in.next();
        String input_file = input_file_name + "." + input_file_ext;
        System.out.println("Yours input file is " + input_file);

        System.out.print("Is the file archived?(ZIP) (Yes/No):");
        String bool_arch = console_in.next();
        if (bool_arch.equals("Yes")){
            System.out.println("Write the name of archive (without extension):");
            String name_archive = console_in.next();
            Archive.unarchiveZIP(name_archive);
        }

        System.out.print("Write the name of output file: ");
        String output_file_name = console_in.next();
        System.out.print("write the output file extension: ");
        String output_file_ext = console_in.next();
        String output_file = output_file_name + "." + output_file_ext;
        System.out.println("Yours output file is " + output_file);

        String content = reading_file.file_content(input_file, output_file);
        //System.out.print("Expressions:\n" + content);
        if (bool_arch.equals("Yes")) {
            System.out.print("Write the output archive: ");
            String output_archive = console_in.next();
            Archive.archiveZIP(output_file, output_archive);
        }
        console_in.close();
    }
}