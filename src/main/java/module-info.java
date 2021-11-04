module com.fox.rgr_tf {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.fox.rgr_tf to javafx.fxml;
    exports com.fox.rgr_tf;
    exports com.fox.rgr_tf.compiler;
    opens com.fox.rgr_tf.compiler to javafx.fxml;
    exports com.fox.rgr_tf.compiler.model;
    opens com.fox.rgr_tf.compiler.model to javafx.fxml;
    exports com.fox.rgr_tf.controller;
    opens com.fox.rgr_tf.controller to javafx.fxml;
}