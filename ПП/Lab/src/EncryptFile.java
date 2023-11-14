import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
public class EncryptFile {
    public static void encryptFile(String file) throws IOException {
        String inputFile = ConstantVariable.PATH_TEMP + file;
        String encryptedFile = ConstantVariable.PATH_OUT + file;

        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;

        try {
            inputStream = new FileInputStream(inputFile);
            outputStream = new FileOutputStream(encryptedFile);

            int byteData;
            while ((byteData = inputStream.read()) != -1) {
                byte encryptedByte = (byte) (byteData ^ ConstantVariable.KEY);
                outputStream.write(encryptedByte);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    public static String decryptFile(String encryptedString) throws IOException {
        String decryptedFile = ConstantVariable.PATH_TEMP + "temp.txt";

        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;

        StringBuilder decryptedString;
        decryptedString = new StringBuilder();

        try {
            outputStream = new FileOutputStream(decryptedFile);

            for (int i = 0; i < encryptedString.length(); i++) {
                char decryptedChar = (char) (encryptedString.charAt(i) ^ ConstantVariable.KEY);
                decryptedString.append(decryptedChar);
            }
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
        return decryptedString.toString();
    }
}