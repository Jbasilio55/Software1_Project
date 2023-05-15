module com.jorge.passessmentqkm {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.jorge.passessmentqkm2.controller;
    opens com.jorge.passessmentqkm2.controller to javafx.fxml;
    exports com.jorge.passessmentqkm2;
    opens com.jorge.passessmentqkm2 to javafx.fxml;
    opens com.jorge.passessmentqkm2.model to javafx.base;
}