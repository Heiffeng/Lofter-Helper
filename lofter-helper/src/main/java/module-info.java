module site.achun.lofter.lofterhelper {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
                        requires org.kordamp.bootstrapfx.core;
            
    opens site.achun.lofter.lofterhelper to javafx.fxml;
    exports site.achun.lofter.lofterhelper;
}