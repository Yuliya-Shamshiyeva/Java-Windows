module src.lab2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens src.lab2 to javafx.fxml;
    exports src.lab2;
}