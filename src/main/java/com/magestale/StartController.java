package com.magestale;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartController {

    @FXML
    private void handleNewGame() throws Exception {
        Stage stage = GameGUI.getPrimaryStage();
        Scene gameScene = GameGUI.loadScene("game_view.fxml"); //loads gamecontroller

        stage.setScene(gameScene);
        System.out.println("Welcome to 'A Mage’s Tale!'\nEnter your name, brave sorcerer:\n");
        System.out.flush();
    }

    @FXML
    private void handleLoadGame() throws Exception {
        Stage stage = GameGUI.getPrimaryStage();
        Scene gameScene = GameGUI.loadScene("game_view.fxml");
        stage.setScene(gameScene);

        //gets controller that belongs to most recent loaded FXML files
       Platform.runLater(() ->{
           GameController controller = GameGUI.getLastLoader().getController();
            controller.loadSavedGame();
        });
    }
}
