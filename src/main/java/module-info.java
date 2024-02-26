module isen.project {
    requires javafx.controls;
    requires javafx.fxml;
	requires java.sql;
	requires sqlite.jdbc;

    opens isen.project.view to javafx.fxml;
    exports isen.project;
    exports isen.project.view;
}
