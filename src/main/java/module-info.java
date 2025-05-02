module com.example.cs157proj {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.cs157proj to javafx.fxml;
    exports com.example.cs157proj;
}