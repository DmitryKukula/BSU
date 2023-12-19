package com.example.end_to_end;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Archive {
    public static void recordArchive1(byte[] bytesToWrite,String zipFilePath, String entryName) {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFilePath))) {
            ZipEntry entry = new ZipEntry(entryName);
            zipOutputStream.putNextEntry(entry);
            zipOutputStream.write(bytesToWrite);
            zipOutputStream.closeEntry();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void recordArchive(byte[] bytesToWrite, String zipFilePath, String entryName) {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFilePath))) {
            ZipEntry entry = new ZipEntry(entryName);
            zipOutputStream.putNextEntry(entry);
            zipOutputStream.write(bytesToWrite);
            zipOutputStream.closeEntry();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при архивировании массива байтов", e);
        }
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
}
