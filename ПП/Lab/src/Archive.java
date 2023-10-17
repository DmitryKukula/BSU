import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Archive {

    public static void unarchiving() throws IOException {
        Scanner console_in = new Scanner(System.in);
        System.out.print("Write the name of archive (with extension): ");
        String name_archive = console_in.next();
        unarchive(name_archive);
    }
    public static void archiving(String name_file){
        Scanner console_in = new Scanner(System.in);
        System.out.print("Write the output archive: ");
        String output_archive = console_in.next();
        System.out.print("Write the archive extension: ");
        String extension_archive = console_in.next();
        Archive.archive(name_file, output_archive, extension_archive);
    }
    public static void archive(String name_file, String name_zip, String extension){
        String sourceFile = "D:\\БГУ\\2 курс\\ПП\\Project1\\output\\" + name_file;
        String zipFile = "D:\\БГУ\\2 курс\\ПП\\Project1\\output\\" + name_zip + "." + extension;

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
            //System.out.println("File successfully archived!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        File temp_output = new File(ConstantVariable.Variable.PATH_OUT + name_file);
        temp_output.delete();
    }
    public static void unarchive(String name) throws IOException {
        String zipFilePath = ConstantVariable.Variable.PATH_IN + name;
        //System.out.println("D:\\БГУ\\2 курс\\ПП\\Project1\\input\\" + name);
        String destFolderPath = ConstantVariable.Variable.PATH_IN;

        try (FileInputStream fis = new FileInputStream(zipFilePath);
             ZipInputStream zis = new ZipInputStream(fis)) {
                ZipEntry entry = zis.getNextEntry();

                while (entry != null) {
                    String entryName = entry.getName();
                    String filePath = destFolderPath + "/" + entryName;

                    if (!entry.isDirectory()) {
                        try (FileOutputStream fos = new FileOutputStream(filePath)) {
                            byte[] buffer = new byte[1024];
                            int bytesRead;
                            while ((bytesRead = zis.read(buffer)) != -1) {
                                fos.write(buffer, 0, bytesRead);
                            }
                        }
                    }
                    else {
                        new File(filePath).mkdirs();
                    }
                    entry = zis.getNextEntry();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
