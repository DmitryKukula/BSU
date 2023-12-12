module com.example.end_to_end {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires exp4j;
    requires java.scripting;
    requires com.google.gson;
    requires java.xml;

    opens com.example.end_to_end to javafx.fxml;
    exports com.example.end_to_end;
}