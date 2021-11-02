module com.fox.rgr_tf {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.fox.rgr_tf to javafx.fxml;
    exports com.fox.rgr_tf;
}