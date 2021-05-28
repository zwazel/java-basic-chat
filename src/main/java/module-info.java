module dev.zwazel {
    requires javafx.controls;
    requires javafx.fxml;

    opens dev.zwazel to javafx.fxml;
    exports dev.zwazel;
}