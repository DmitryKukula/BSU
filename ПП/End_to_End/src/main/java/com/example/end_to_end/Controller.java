package com.example.end_to_end;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private CheckBox CheckArch;

    @FXML
    private CheckBox CheckArch1;

    @FXML
    private CheckBox CheckEncr;

    @FXML
    private CheckBox CheckEncr1;

    @FXML
    private TextField InFileName;

    @FXML
    private TextField InZipArchive;

    @FXML
    private TextField OutFileName;

    @FXML
    private TextField OutZipArchive;

    @FXML
    private Button butClose;

    @FXML
    void isArchiveOut(ActionEvent event) {
        if (!OutZipArchive.isVisible()) {
            OutZipArchive.setVisible(true);
            OutZipArchive.setDisable(false);
        }
        else{
            OutZipArchive.setVisible(false);
            OutZipArchive.setDisable(true);
        }
    }

    @FXML
    void isArchivedIn(ActionEvent event) {
        if (!InZipArchive.isVisible()) {
            InZipArchive.setVisible(true);
            InZipArchive.setDisable(false);
        }
        else{
            InZipArchive.setVisible(false);
            InZipArchive.setDisable(true);
        }
    }

    @FXML
    void Select(ActionEvent event) throws Exception {
        byte[] bytes;
        String nameZip;
        String fileName = InFileName.getText();
        if (CheckArch.isSelected()){
            nameZip = InZipArchive.getText();
            bytes = Archive.readArchive(fileName, nameZip, ConstantVariable.PATH_IN);
        }
        else {
            bytes = Reading_file.readFileToBytes(fileName, ConstantVariable.PATH_IN);
        }

        if (CheckEncr.isSelected()) {
            try {
                bytes = Encrypt_file_AES.decrypt(bytes);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        bytes = FilterBytes.filterBytes(bytes);
        String content = new String(bytes);
        byte[] resultBytes = MathExpressionEvaluator.calculate(content);
        String nameOutputFile = OutFileName.getText();
        int dotIndex = nameOutputFile.indexOf('.');
        String substringAfterDot = nameOutputFile.substring(dotIndex + 1);
        switch (substringAfterDot){
            case "txt":
                break;
            case "json":
                resultBytes = Output.convertBytesForJSON(resultBytes);
                break;
            case "xml":
                resultBytes = Output.convertBytesForXML(resultBytes);
                break;
        }

        if (CheckEncr1.isSelected()) {
            resultBytes = Encrypt_file_AES.encrypt(resultBytes);
        }

        if (CheckArch1.isSelected()) {
            String nameOutputArchive = OutZipArchive.getText();
            Archive.recordArchive(resultBytes, ConstantVariable.PATH_OUT + nameOutputArchive, nameOutputFile);
        } else {
            Output.writeBytesToFile(nameOutputFile, ConstantVariable.PATH_OUT, resultBytes);
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ResultAPP.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage newWindow = new Stage();
            newWindow.initModality(Modality.APPLICATION_MODAL);
            newWindow.setScene(scene);
            newWindow.setTitle("Result");
            newWindow.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Close(ActionEvent event) {
        Stage stage = (Stage) butClose.getScene().getWindow();
        stage.close();
    }

    @FXML
    void initialize() {
    }

}
