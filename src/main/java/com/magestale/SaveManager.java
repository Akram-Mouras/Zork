package com.magestale;

import java.io.*;
import java.nio.file.*;

public class SaveManager {

    public static void saveGameState(GameState state, Path directory, String saveName)  {
        try {
            Files.createDirectories(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Path file = directory.resolve(saveName + ".ser");

        try (ObjectOutputStream out =
                     new ObjectOutputStream(Files.newOutputStream(file))) {
            out.writeObject(state); // writes/serializes the entire character object into file

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static GameState loadGameState (Path directory, String saveName) throws IOException, ClassNotFoundException {
        Path  file = directory.resolve(saveName + ".ser");

        try (ObjectInputStream in =
                     new ObjectInputStream(Files.newInputStream(file))) {
            return (GameState) in.readObject();
        }
    }

    public static void saveWorldState(WorldState state, Path dir, String saveName){
        try {
            Files.createDirectories(dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Path file = dir.resolve(saveName + ".ser");
        try (ObjectOutputStream out =
                     new ObjectOutputStream(Files.newOutputStream(file))) {
            out.writeObject(state); // writes/serializes the entire character object into file

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static WorldState loadWorldState (Path directory, String saveName) throws IOException, ClassNotFoundException {
        Path  file = directory.resolve(saveName + ".ser");

        try (ObjectInputStream in =
                     new ObjectInputStream(Files.newInputStream(file))) {
            return (WorldState) in.readObject();
        }
    }
}