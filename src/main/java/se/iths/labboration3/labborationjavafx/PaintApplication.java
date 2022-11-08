package se.iths.labboration3.labborationjavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import se.iths.labboration3.labborationjavafx.controller.Paint;

import java.io.IOException;

public class PaintApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        var fxmlLoader = new FXMLLoader(PaintApplication.class.getResource("paint-view.fxml"));
        var scene = new Scene(fxmlLoader.load(), 949, 750);
        Paint controller = fxmlLoader.getController();
        controller.setStage(stage);
        stage.setTitle("Paint-2.0");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}