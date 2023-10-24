package site.achun.lofter;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import site.achun.lofter.views.HomeView;

public class LofterApplication extends Application {
    @Override
    public void start(Stage stage){
        Scene scene = new Scene(new HomeView(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}