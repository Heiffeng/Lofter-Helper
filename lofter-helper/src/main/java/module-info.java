module site.achun.lofter.lofterhelper {
    requires javafx.controls;
    requires javafx.fxml;
            
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    requires java.net.http;
    requires org.jsoup;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    opens site.achun.lofter to javafx.fxml;
    exports site.achun.lofter.bean to com.fasterxml.jackson.databind;

    exports site.achun.lofter;
}