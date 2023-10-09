import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
public class EncryptFile {
    public static void decrypt(String key,File input_file, File output_file) throws Exception{
        FileInputStream inputStream = new FileInputStream(input_file);
        byte[] inputBytes = new byte[(int) input_file.length()];
        inputStream.read(inputBytes);

        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] outputBytes = cipher.doFinal(inputBytes);

        FileOutputStream outputStream = new FileOutputStream(output_file);
        outputStream.write(outputBytes);

        inputStream.close();
        outputStream.close();
    }

    public static void encrypt(String key, File input_file, File output_file) throws Exception{
        FileInputStream inputStream = new FileInputStream(input_file);
        byte[] inputBytes = new byte[(int) input_file.length()];
        inputStream.read(inputBytes);

        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] outputBytes = cipher.doFinal(inputBytes);

        FileOutputStream outputStream = new FileOutputStream(output_file);
        outputStream.write(outputBytes);

        inputStream.close();
        outputStream.close();
    }
}