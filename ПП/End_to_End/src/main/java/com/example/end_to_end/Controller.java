package com.example.end_to_end;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
        boolean chArchIn = CheckArch.isSelected();
        boolean chEncrIn = CheckEncr.isSelected();
        String fileNameIn = InFileName.getText();
        int dotIndex = fileNameIn.indexOf('.');
        String substringAfterDot = fileNameIn.substring(dotIndex + 1);
        if (dotIndex <= 0 || (!substringAfterDot.equals("txt") && !substringAfterDot.equals("json") && !substringAfterDot.equals("xml"))){
            showErrorAlert("Ошибка", "Неверный ввод", "Введите корректное имя для входного файла.");
            return;
        }

        String zipNameIn = "";

        if (chArchIn) {
            zipNameIn = InZipArchive.getText();
            zipNameIn = zipNameIn.trim();
            dotIndex = zipNameIn.indexOf('.');
            substringAfterDot = zipNameIn.substring(dotIndex + 1);
            if (zipNameIn == null || zipNameIn.trim().isEmpty() || dotIndex <= 0 || !substringAfterDot.equals("zip")) {
                showErrorAlert("Ошибка", "Неверный ввод", "Введите корректное имя для входного архива.");
                return;
            }
        }

        boolean chArchOut = CheckArch1.isSelected();
        boolean chAEncrOut = CheckEncr1.isSelected();
        String fileNameOut = OutFileName.getText();
        dotIndex = fileNameOut.indexOf('.');
        substringAfterDot = fileNameOut.substring(dotIndex + 1);
        if (dotIndex <= 0 || (!substringAfterDot.equals("txt") && !substringAfterDot.equals("json") && !substringAfterDot.equals("xml"))){
            showErrorAlert("Ошибка", "Неверный ввод", "Введите корректное имя для выходного файла.");
            return;
        }
        String zipNameOut = "";

        if (chArchOut) {
            zipNameOut = OutZipArchive.getText();
        }

        try {
            new ProgramBuilder()
                    .setCheckArch(chArchIn)
                    .setCheckEncr(chEncrIn)
                    .setCheckArch1(chArchOut)
                    .setCheckEncr1(chAEncrOut)
                    .setInFileName(fileNameIn)
                    .setInZipArchive(zipNameIn)
                    .setOutFileName(fileNameOut)
                    .setOutZipArchive(zipNameOut)
                    .runProgram();
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

    private void showErrorAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
