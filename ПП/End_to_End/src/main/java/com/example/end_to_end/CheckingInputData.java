package com.example.end_to_end;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CheckingInputData {
    public static boolean isFileExistsInDirectory(String fileName) {
        Path filePath = Paths.get(ConstantVariable.PATH_IN, fileName);
        return Files.exists(filePath) && Files.isRegularFile(filePath);
    }
    public static boolean isArchiveExistsInDirectory(String archiveName) {
        Path archivePath = Paths.get(ConstantVariable.PATH_IN, archiveName);
        if (Files.exists(archivePath) && Files.isRegularFile(archivePath)) {
            return archiveName.toLowerCase().endsWith(".zip");
        }
        return false;
    }
    public static boolean checkNameFile(String name){
        int dotIndex = name.indexOf('.');
        String substringAfterDot = name.substring(dotIndex + 1);
        if (dotIndex <= 0 || (!substringAfterDot.equals("txt") && !substringAfterDot.equals("json") && !substringAfterDot.equals("xml"))){
            return false;
        }
        return true;
    }
    public static boolean checkNameArchive(String name){
        int dotIndex = name.indexOf('.');
        String substringAfterDot = name.substring(dotIndex + 1);
        if (name == null || name.trim().isEmpty() || dotIndex <= 0 || !substringAfterDot.equals("zip")) {
            return false;
        }
        return true;
    }
}
