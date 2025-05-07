module com.example.cs157proj {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.cs157proj to javafx.fxml;
    exports com.example.cs157proj;
    exports com.example.cs157proj.fxmlControllers;
    opens com.example.cs157proj.fxmlControllers to javafx.fxml;
    exports com.example.cs157proj.model;
    opens com.example.cs157proj.model to javafx.fxml;
    opens com.example.cs157proj.dataObjects to javafx.base;
}