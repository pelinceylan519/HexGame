module com.example.vpporject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.vpporject to javafx.fxml;
    exports com.example.vpporject;
    opens edu.erciyes.project to javafx.fxml;
    exports edu.erciyes.project;
}