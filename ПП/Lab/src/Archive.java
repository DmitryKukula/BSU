import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Archive {

    public static String unarchiving(String name_file) throws IOException {
        Scanner console_in = new Scanner(System.in);
        System.out.print("Write the name of archive (FULL): ");
        String name_archive = console_in.next();
        return unarchive(name_archive, name_file);
    }

    public static void archiving(String name_file) {
        Scanner console_in = new Scanner(System.in);
        System.out.print("Write the output archive(FULL): ");
        String output_archive = console_in.next();
        Archive.archive(name_file, output_archive);
    }

    public static void archive(String name_file, String name_zip) {
        String sourceFile = ConstantVariable.PATH_TEMP + name_file;
        String zipFile = ConstantVariable.PATH_OUT + name_zip;

        try {
            FileOutputStream fos = new FileOutputStream(zipFile);
            ZipOutputStream zos = new ZipOutputStream(fos);
            FileInputStream fis = new FileInputStream(sourceFile);

            ZipEntry zipEntry = new ZipEntry(name_file);
            zos.putNextEntry(zipEntry);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, length);
            }

            fis.close();
            zos.closeEntry();
            zos.close();
            System.out.println("File successfully archived!\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        File temp_output = new File(ConstantVariable.PATH_OUT + name_file);
        temp_output.delete();
    }

    public static String unarchive(String name, String file) throws IOException {
        String zipFilePath = ConstantVariable.PATH_IN + name;
        StringBuilder fileContent = new StringBuilder();

        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            while (zipEntry != null) {
                String fileName = zipEntry.getName();

                if (fileName.equals(file)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;

                    while ((bytesRead = zipInputStream.read(buffer)) != -1) {
                        fileContent.append(new String(buffer, 0, bytesRead));
                    }
                    break;
                }

                zipEntry = zipInputStream.getNextEntry();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent.toString();
    }
}
