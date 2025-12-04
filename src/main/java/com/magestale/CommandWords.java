package com.magestale;

import java.util.HashMap;
import java.util.Map;

public class CommandWords {
    private Map<String, String> validCommands;

    public CommandWords() {
        validCommands = new HashMap<>();
        validCommands.put("go", "Move to another room");
        validCommands.put("quit", "End the game");
        validCommands.put("help", "Show help");
        validCommands.put("inventory", "Show inventory");
        validCommands.put("pick", "pick up");
        validCommands.put("drop", "remove");
        validCommands.put("teleport", "teleport to another room");
        validCommands.put("ask", "talk to NPC");
        validCommands.put("say","To say tralfaz");
        validCommands.put("examine", "examine the room");
        validCommands.put("save", "Save the game");
        validCommands.put("look", "Look around");
        validCommands.put("fight", "fight an enemy");
        validCommands.put("use","to utilise certain items");
        validCommands.put("win","you just win");
        validCommands.put("show", "show items");
        validCommands.put("attack", "attack 'weapon'");
        validCommands.put("yes", "For allegra quiz");
        validCommands.put("no", "For allegra quiz");
        validCommands.put("quit", "quit");
        validCommands.put("answer", "answer quiz");




    }

    public boolean isCommand(String commandWord) {
        return validCommands.containsKey(commandWord);
    }

    public void showAll() {
        System.out.println("Valid commands:");
        for (Map.Entry<String, String> entry : validCommands.entrySet()) {
            System.out.println("  " + entry.getKey() + " – " + entry.getValue());
        }
    }
}