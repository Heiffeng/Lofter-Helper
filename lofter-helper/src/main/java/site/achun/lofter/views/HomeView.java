package site.achun.lofter.views;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class HomeView extends VBox {

    public HomeView(){
        this.getChildren().add(new Label("Hello"));
    }
}
