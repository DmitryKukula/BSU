import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class reading_file {
    public static String file_content(String input_file_name, String output_file_name) throws FileNotFoundException {
        File file = new File(input_file_name);
        Scanner file_in = null;
        try {
            file_in = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        File file_out = new File(output_file_name);
        try {
            boolean newFile = file_out.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        StringBuilder content;
        try (PrintWriter pw = new PrintWriter(file_out)) {

            content = new StringBuilder();
            int temp = 0;
            while (file_in.hasNextLine()) {
                temp++;
                String temp_content = file_in.nextLine();
                content.append(temp_content);
                content.append("\n");
                List<counting.Lexeme> lexemes = counting.lexAnalyze(temp_content);
                counting.LexemeBuffer lexemeBuffer = new counting.LexemeBuffer(lexemes);
                pw.println(temp + ". " + temp_content + " = " + counting.expr(lexemeBuffer));
            }
        }
        return content.toString();
    }
}
