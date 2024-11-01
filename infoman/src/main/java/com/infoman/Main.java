package com.infoman;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/main.fxml")));
        primaryStage.setTitle("Infoman");
        Paint v;
        primaryStage.setScene(new Scene(root, 610, 590));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
        databaseConnection db = new databaseConnection();
    }
}
