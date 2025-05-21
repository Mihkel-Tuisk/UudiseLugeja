module com.uudiselugeja.uudiselugeja {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.uudiselugeja.uudiselugeja to javafx.fxml;
    exports com.uudiselugeja.uudiselugeja;
}