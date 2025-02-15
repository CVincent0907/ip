package gui;

import java.io.IOException;

import tearit.TearIT;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for TearIT using FXML.
 */
public class Main extends Application {

    private TearIT tearIT = new TearIT();

    @Override
    public void start(Stage stage) {
        try {
            //  Load in FXML file to tweak the visual of Java GUI corresponding to the Java controller
            //  (component) we define in Java class
            Image icon = new Image(getClass().getResourceAsStream("/images/sunset.jpeg"));
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);

            stage.setScene(scene);

            //  Set controller to Main Window
            //  Set entry point to backend TearIT
            fxmlLoader.<MainWindow>getController().setTearIT(tearIT);
            stage.setTitle("tearit");
            stage.getIcons().add(icon);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
