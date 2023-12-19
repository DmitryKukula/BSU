package com.example.end_to_end;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ProgramBuilder {
    private boolean checkArch = false;
    private boolean checkEncr = false;
    private boolean checkEncr1 = false;
    private boolean checkArch1 = false;
    private boolean useLibCounting = false;
    private String inFileName;
    private String inZipArchive;
    private String outFileName;
    private String outZipArchive;

    public ProgramBuilder setUseLibCounting(boolean useLibCounting){
        this.useLibCounting = useLibCounting;
        return this;
    }
    public ProgramBuilder setCheckArch(boolean checkArch) {
        this.checkArch = checkArch;
        return this;
    }

    public ProgramBuilder setCheckEncr(boolean checkEncr) {
        this.checkEncr = checkEncr;
        return this;
    }

    public ProgramBuilder setCheckEncr1(boolean checkEncr1) {
        this.checkEncr1 = checkEncr1;
        return this;
    }

    public ProgramBuilder setCheckArch1(boolean checkArch1) {
        this.checkArch1 = checkArch1;
        return this;
    }

    public ProgramBuilder setInFileName(String inFileName) {
        this.inFileName = inFileName;
        return this;
    }

    public ProgramBuilder setInZipArchive(String inZipArchive) {
        this.inZipArchive = inZipArchive;
        return this;
    }

    public ProgramBuilder setOutFileName(String outFileName) {
        this.outFileName = outFileName;
        return this;
    }

    public ProgramBuilder setOutZipArchive(String outZipArchive) {
        this.outZipArchive = outZipArchive;
        return this;
    }

    public void runProgram() throws Exception {
        byte[] bytes;
        if (checkArch) {
            bytes = Archive.readArchive(inFileName, inZipArchive, ConstantVariable.PATH_IN);
        } else {
            bytes = Reading_file.readFileToBytes(inFileName, ConstantVariable.PATH_IN);
        }

        if (checkEncr) {
            bytes = Encrypt_file_AES.decrypt(bytes);
        }
        bytes = FilterBytes.filterBytes(bytes);
        String content = new String(bytes);
        byte[] resultBytes = MathExpressionEvaluator.calculate(content, useLibCounting);

        int dotIndex = outFileName.indexOf('.');
        String substringAfterDot = outFileName.substring(dotIndex + 1);
        switch (substringAfterDot) {
            case "txt":
                break;
            case "json":
                resultBytes = Output.convertBytesForJSON(resultBytes);
                break;
            case "xml":
                resultBytes = Output.convertBytesForXML(resultBytes);
                break;
        }

        if (checkEncr1) {
            resultBytes = Encrypt_file_AES.encrypt(resultBytes);
        }

        if (checkArch1) {
            Archive.recordArchive(resultBytes, ConstantVariable.PATH_OUT + outZipArchive, outFileName);
        } else {
            Output.writeBytesToFile(outFileName, ConstantVariable.PATH_OUT, resultBytes);
        }

//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("ResultAPP.fxml"));
//            Parent root = loader.load();
//            Scene scene = new Scene(root);
//            Stage newWindow = new Stage();
//            newWindow.initModality(Modality.APPLICATION_MODAL);
//            newWindow.setScene(scene);
//            newWindow.setTitle("Result");
//            newWindow.showAndWait();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
