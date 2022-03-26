module bse.sda.jigsaw {
    requires javafx.controls;
    requires javafx.fxml;


    opens bse202.sda.jigsaw to javafx.fxml;
    exports bse202.sda.jigsaw;
    exports bse202.sda.jigsaw.controllers;
    opens bse202.sda.jigsaw.controllers to javafx.fxml;

    exports bse202.sda.jigsaw.models.fxml to javafx.fxml;
    opens bse202.sda.jigsaw.models.fxml to javafx.fxml;

    exports bse202.sda.jigsaw.utils;
}