module com.mycompany.ssproject {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.mycompany.ssproject to javafx.fxml;
    exports com.mycompany.ssproject;
}
