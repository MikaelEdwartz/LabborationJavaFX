module se.iths.labboration3.labborationjavafx {
    requires javafx.controls;
    requires javafx.fxml;

    opens se.iths.labboration3.labborationjavafx to javafx.fxml;
    exports se.iths.labboration3.labborationjavafx;
    exports se.iths.labboration3.labborationjavafx.controller;
    opens se.iths.labboration3.labborationjavafx.controller to javafx.fxml;
    exports se.iths.labboration3.labborationjavafx.tools;
    opens se.iths.labboration3.labborationjavafx.tools to javafx.fxml;
}