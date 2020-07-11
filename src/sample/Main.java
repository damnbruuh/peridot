package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Peridot");
        primaryStage.setScene(new Scene(root, 685, 575));
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/peridot.png")));
        Alert alert = new Alert(Alert.AlertType.WARNING, "Peridot is a network stress tester. You are responsible for your actions!");
        primaryStage.show();
        alert.show();

    }


    public static void main(String[] args) {

        launch(args);

    }
}
