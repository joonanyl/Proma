module r8 {
    requires javafx.controls;
    requires javafx.fxml;

    opens r8 to javafx.fxml;
    exports r8;
}
