package com.example.end_to_end;

import java.io.*;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Archive {
    public static void recordArchive(byte[] bytesToWrite,String zipFilePath, String entryName){
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFilePath))) {
            ZipEntry entry = new ZipEntry(entryName);
            zipOutputStream.putNextEntry(entry);
            zipOutputStream.write(bytesToWrite);
            zipOutputStream.closeEntry();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static byte[] dataToReadArchive(String nameFile) throws Exception {
        Scanner console_in = new Scanner(System.in);
        System.out.print("Write the name of archive (FULL): ");
        String nameArchive = console_in.next();
        return readArchive(nameFile, nameArchive, ConstantVariable.PATH_IN);
    }

    public static byte[] readArchive(String nameFile, String nameArchive, String path){
        String zipFilePath = path + nameArchive;
        String entryName = nameFile;
        byte[] resultBytes = null;

        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                if (entry.getName().equals(entryName)) {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = zipInputStream.read(buffer)) != -1) {
                        byteArrayOutputStream.write(buffer, 0, bytesRead);
                    }

                    resultBytes = byteArrayOutputStream.toByteArray();
                    break;
                }
                zipInputStream.closeEntry();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resultBytes;
    }
    public static void unarchiving(String name_file) throws IOException {
        Scanner console_in = new Scanner(System.in);
        System.out.print("Write the name of archive (FULL): ");
        String name_archive = console_in.next();
        unarchive(name_archive);
    }

    public static void archiving(String name_file) {
        Scanner console_in = new Scanner(System.in);
        System.out.print("Write the output archive(FULL): ");
        String output_archive = console_in.next();
        Archive.archive(name_file, output_archive);
    }

    public static void archive(String name_file, String name_zip) {
        String sourceFile = ConstantVariable.PATH_TEST + name_file;
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        File temp_output = new File(ConstantVariable.PATH_OUT + name_file);
        temp_output.delete();
    }

    public static void unarchive(String name) throws IOException {
        String zipFilePath = ConstantVariable.PATH_IN + name;
        String destinationFolderPath = ConstantVariable.PATH_TEST;

        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath))) {
            File destinationFolder = new File(destinationFolderPath);
            if (!destinationFolder.exists()) {
                destinationFolder.mkdirs();
            }

            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                String entryName = entry.getName();
                String entryPath = destinationFolderPath + File.separator + entryName;

                File entryFile = new File(entryPath);
                if (entry.isDirectory()) {
                    entryFile.mkdirs();
                } else {
                    try (FileOutputStream outputStream = new FileOutputStream(entryFile)) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = zipInputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                    }
                }

                zipInputStream.closeEntry();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
