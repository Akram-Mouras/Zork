package com.magestale;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class GameController {

    private boolean nameEntered = false;

    private GameEngine engine;

    @FXML
    private Button saveButton;
    @FXML private TextArea outputArea;
    @FXML private TextField inputField;

    public void initialize() throws IOException, ClassNotFoundException {

        engine = new GameEngine();

        PrintStream ps = new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
                outputArea.appendText(String.valueOf((char) b));
            }
        });

        outputArea.setEditable(false);
        System.setOut(ps);
        System.setErr(ps);
    }

    @FXML
    private void processCommand() {
        String userInput = inputField.getText();
        inputField.clear();
        System.out.println(userInput);
        System.out.flush();

        if (!nameEntered) {
            System.out.println(engine.startNewGame(userInput));
            System.out.println(engine.getInitialRoomInfo());
            System.out.println("\n");
            nameEntered = true;
            return;
        }

        //after the name we use normal commands
        engine.processCommand(engine.getParser().getCommand(userInput));
        System.out.println("\n");
    }
    @FXML
    private void handleNorth(){
        String cmd = "go north";
        System.out.println(cmd);
        System.out.flush();
        engine.processCommand(engine.getParser().getCommand(cmd));

    }

    @FXML
    private void handleSave(){
        String cmd = "save";
        System.out.println(cmd);
        System.out.flush();
        engine.processCommand(engine.getParser().getCommand(cmd));
    }

    @FXML
    private void handleSouth() {
        String cmd = "go south";
        System.out.println(cmd);
        System.out.flush();
        engine.processCommand(engine.getParser().getCommand(cmd));
    }

    @FXML
    private void handleEast(){
        String cmd = "go east";
        System.out.println(cmd);
        System.out.flush();
        engine.processCommand(engine.getParser().getCommand(cmd));
    }

    @FXML
    private void handleWest(){
        String cmd = "go west";
        System.out.println(cmd);
        System.out.flush();
        engine.processCommand(engine.getParser().getCommand(cmd));
    }

    @FXML
    public void loadSavedGame(){
        try {
            outputArea.appendText("Game Loaded.\n");
            engine.LoadGame();
            System.out.flush();

        } catch (Exception e) {
            outputArea.appendText("Error loading save.\n");
            e.printStackTrace();
        }
    }
}