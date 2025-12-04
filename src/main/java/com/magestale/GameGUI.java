package com.magestale;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameGUI extends Application {
    private static Stage primaryStage;
    private static FXMLLoader lastLoader;

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static FXMLLoader getLastLoader() {
        return lastLoader;
    }

public static Scene loadScene(String fxml) throws Exception{        
        lastLoader = new FXMLLoader(GameGUI.class.getResource(fxml));
        return new Scene(lastLoader.load());
}

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage= stage;

        Scene scene = loadScene("start_view.fxml");
        stage.setScene(scene);
        stage.setTitle("A Mage's Tale");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
