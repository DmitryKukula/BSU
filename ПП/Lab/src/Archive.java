import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Archive {
    public static void archiveZIP(String name_file, String name_zip){
        String sourceFile = name_file;
        String zipFile = "D:\\БГУ\\2 курс\\ПП\\Project1\\" + name_zip + ".zip";

        try {
            FileOutputStream fos = new FileOutputStream(zipFile);
            ZipOutputStream zos = new ZipOutputStream(fos);
            FileInputStream fis = new FileInputStream(sourceFile);

            ZipEntry zipEntry = new ZipEntry(sourceFile);
            zos.putNextEntry(zipEntry);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, length);
            }

            fis.close();
            zos.closeEntry();
            zos.close();
            System.out.println("File successfully compressed!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void unarchiveZIP(String name_zip) throws IOException {
        //ZipInputStream zin = new ZipInputStream(new FileInputStream(""));
        String zipFilePath = "D:\\БГУ\\2 курс\\ПП\\Project1\\" + name_zip + ".zip";
        System.out.println("D:\\БГУ\\2 курс\\ПП\\Project1\\" + name_zip + ".zip");
        String destFolderPath = "D:\\БГУ\\2 курс\\ПП\\Project1";

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
                } else {
                    new File(filePath).mkdirs();
                }

                entry = zis.getNextEntry();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
