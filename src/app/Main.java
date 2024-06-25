package app;
import javafx.application.Application;
import javafx.stage.Stage;
import view.GUI;




public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        GUI g1 = new GUI();
        g1.start(primaryStage);
    }

}

