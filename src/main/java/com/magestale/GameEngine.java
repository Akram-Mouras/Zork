package com.magestale;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class GameEngine {
    private HashMap<String, Room> roomMap = new HashMap<>();
    private Character player;
    private Parser parser;
    private Inventory<Item> inventory;
    private Dumbledore dumbledore;
    private GameState gameState;
    private WorldState worldState;
    private Amarok amarok;
    private Allegra allegra;
    private Oriz oriz;

    private boolean inCombat = false;
    private boolean fightingOriz = false;
    private boolean awaitingAllegraRiddle = false;
    private boolean awaitingAllegraGuess = false;

    public GameEngine() throws IOException, ClassNotFoundException {
        parser = new Parser();
        inventory = new Inventory();
        player = new Character();

        createRooms();
        createItems();
        NPCRooms();

        WorldState worldState = new WorldState();

        inventory.setWorldState(worldState);
        player.setWorldState(worldState);
        dumbledore.setWorldState(worldState);
        amarok.setWorldState(worldState);
        oriz.setWorldState(worldState);
//setter injection as worldState in each is private

    }

    public String getInitialRoomInfo(){
        StringBuilder sb = new StringBuilder();

        sb.append("\n");
        sb.append(player.getCurrentRoom().getDescription());

        sb.append("\n");
        return sb.toString();
    }

    private void createRooms() {
        Room chamber, crossroad, entrance, forest, castle, library, hall;

        chamber = new Room("Dumbledore's Chamber", "You are in Dumbledore's office. A magic mirror glows in the corner.\n(\"----- ask dumbledore -----\")\n");
        crossroad = new Room("Crossroad", "You are stranded on a crossroad\n");
        entrance = new Room("Castle Entrance", "You stand at the gate of Voldemort's castle. A poster hangs on the wall");
        forest = new Room("Death Forest", "Tall trees loom over you. Silent. Eerie.");
        castle = new Room("Castle", "You reach the inside of the castle");
        library = new Room("Library","Books stacked upon books.\nYou see the librarian allegra at her desk");
        hall = new Room("Royal Hall","You arrive at the Royal Hall");

        roomMap.put(chamber.getName(), chamber);
        roomMap.put(crossroad.getName(), crossroad);
        roomMap.put(entrance.getName(), entrance);
        roomMap.put(forest.getName(), forest);
        roomMap.put(castle.getName(), castle);
        roomMap.put(library.getName(), library);
        roomMap.put(hall.getName(), hall);

        crossroad.setExit(Direction.EAST , entrance);
        crossroad.setExit(Direction.NORTH, forest);

        forest.setExit(Direction.SOUTH, crossroad);

        entrance.setExit(Direction.WEST, crossroad);
        entrance.setExit(Direction.NORTH, castle);

        castle.setExit(Direction.SOUTH, entrance);
        castle.setExit(Direction.WEST, library);
        castle.setExit(Direction.NORTH, hall);

        library.setExit(Direction.EAST, castle);

        hall.setExit(Direction.SOUTH, castle);
    }

    private void createItems(){
        Room chamber = roomMap.get("Dumbledore's Chamber");
        Room crossroad = roomMap.get("Crossroad");
        Room entrance = roomMap.get("Castle Entrance");
        Room forest = roomMap.get("Death Forest");
        Room library = roomMap.get("Library");

        Item amulet, sword, key, wand, potion;

        amulet = new Item("amulet", " A magical equipment that will increase your health when used");
        sword = new Item("sword"," A three bladed weapon\nDamage: 1-7. ");
        key = new Item("key", " The key to enter Voldemort's castle");
        wand = new Item("wand","The great sorcerer Wazif's ancient wand\nDamage:3-12");
        potion = new Item("potion", "Oriz's health potion");

        chamber.addItem(amulet);
        entrance.addItem(sword);
        forest.addItem(key);
        library.addItem(wand);
    }

    private void NPCRooms(){
        Room chamber = roomMap.get("Dumbledore's Chamber");
        Room crossroad = roomMap.get("Crossroad");
        Room entrance = roomMap.get("Castle Entrance");
        Room forest = roomMap.get("Death Forest");
        Room library = roomMap.get("Library");
        Room hall = roomMap.get("Royal Hall");

        Dumbledore dumbledore;
        dumbledore = new Dumbledore("dumbledore", chamber);
        chamber.addCharacter(dumbledore);
        this.dumbledore = dumbledore;

        Amarok amarok;
        amarok = new Amarok("amarok", forest);
        forest.addCharacter(amarok);
        this.amarok = amarok;

        Allegra allegra;
        allegra = new Allegra("allegra",library);
        library.addCharacter(allegra);
        this.allegra = allegra;

        Oriz oriz;
        oriz = new Oriz("oriz", hall, 30);
        hall.addCharacter(oriz);
        this.oriz = oriz;
    }

    public void LoadGame() throws IOException, ClassNotFoundException {

        WorldState loadWorldState;
        loadWorldState = SaveManager.loadWorldState(Paths.get("saves"), "slot1_world");

        this.worldState = loadWorldState;

        inventory.setWorldState(loadWorldState);
        player.setWorldState(loadWorldState);   // get info from what was changed in the save file
        dumbledore.setWorldState(loadWorldState);
        amarok.setWorldState(worldState);
        allegra.setWorldState(worldState);
        oriz.setWorldState(worldState);

        GameState LoadState =
                SaveManager.loadGameState(Paths.get("saves"), "slot1_game"); //get all data in saved file

        LoadState.getPlayerName();
        LoadState.getCurrentRoomName();
        LoadState.getInventoryItems();
        LoadState.getHP();


        List<String> savedItemNames = LoadState.getInventoryItems();
        Room currentRoom = roomMap.get(LoadState.getCurrentRoomName());
        int playerHP = LoadState.getHP();


        Character Lplayer = new Character(LoadState.getPlayerName(), currentRoom, playerHP);

        this.player = Lplayer;

        for (String itemName : savedItemNames) {
            for (Room room : roomMap.values()) {
                Item item = room.getItemByName(itemName);

                if (item != null) {
                    inventory.pickUp(item);
                    room.removeItem(item);
                    break;
                }
            }
        }


        for (Room rooms : roomMap.values()) {
            for (Item items : rooms.getItems()) {
                if(savedItemNames.contains(items.getName())) {
                    inventory.pickUp(items);
                }
            }
        }

        System.out.println("\n" + player.getCurrentRoom().Exits());
        System.out.print("HP: " + playerHP +"\n");
        System.out.println(player.getCurrentRoom().getDescription());

       // play();
    }

    public String startNewGame(String playerName) {
        worldState = new WorldState();

        inventory.setWorldState(worldState);       // 1) Give it to inventory
        player.setWorldState(worldState);
        dumbledore.setWorldState(worldState);
        amarok.setWorldState(worldState);
        allegra.setWorldState(worldState);
        oriz.setWorldState(worldState);

        // In GUI version, player name is passed from GameController
        // We set it later in startNewGame()
        int playerHP = 30;

        Room startRoom = roomMap.get("Dumbledore's Chamber");

        player = new Character(playerName, startRoom, playerHP );
        player.setWorldState(worldState);

        return "Welcome, " + player.getName() + "! Your adventure begins...\n";
    }

    private String processLine(String input){    //use parser without reading from keyboard itself
        StringBuilder output = new StringBuilder();

        Command command = parser.getCommand(input);
        boolean finished = processCommand(command);
        return output.toString();

    }

    public boolean processCommand(Command command) {
        String commandWord = command.getCommandWord();

        if (commandWord == null) {
            System.out.println("I don't understand your command...");
            return false;
        }
        switch (commandWord) {
            case "go":
                goRoom(command);
                break;
            case "quit":
                if (command.hasSecondWord()) {
                    System.out.println("Quit what?");
                    return false;
                } else {
                    return true; // signal to quit
                }
            case "help":
                printHelp();
                break;

            case "inventory":
                showInv(command);
                break;

            case "pick":
                pick(command);
                break;

            case "drop":
                drop(command);
                break;

            case "teleport":
                teleport();
                break;

            case "win":
                System.out.println("So you found the easiest way to win, GGs");
                System.out.println("Enjoy the credits anyways");
                player.endCredits();
                break;

            case "attack":
                if (!inCombat) {
                    System.out.println("You swing at nothing – there’s no one to attack!");
                } else if(inCombat && fightingOriz) {
                        handleOrizCombat(command);
                    }
                    else if(inCombat && !fightingOriz) {
                        handleAmarokAttack(command);
                    }
                break;

            case "show":
                if (command.getSecondWord() == null){
                    System.out.println("--- You must say ' show [items] ' ---");
                    return false;
                }
                else if (command.getSecondWord().equalsIgnoreCase("items")&& inventory.getItemNames() == null){
                    System.out.println("You have nothing");

                } else if (command.getSecondWord().equalsIgnoreCase("items")){
                    for(Item invite: inventory.getItems()){
                        System.out.println(invite.getName() + " — " + invite.getDescription());
                    }
                }
                break;

            case "use":
                if (command.getSecondWord() == null){
                    System.out.println("--- You must say ' use [item] ' ---");
                    return false;
                }
                else if (command.getSecondWord().equalsIgnoreCase("amulet") && inventory.hasItem("amulet")){
                    player.setHP(player.getHP() + 20);
                    System.out.println("Positive energy glows around your body\n You feel rejuvenated\n You receive (+ 20 HP)");
                    System.out.println("New HP: " + player.getHP());

                    Item Uamulet = inventory.getItemByName("amulet");
                    inventory.removeItem(Uamulet);
                }
                else{
                    System.out.println("You dont have that item");
                }
                break;

            case "answer":
                if (awaitingAllegraGuess) {
                    // Now player is answering the riddle
                    allegraCheckGuess(command);
                    break;
                }
                if (awaitingAllegraRiddle) {
                    allegraCombat(command);
                    break;
                }
                else if (!awaitingAllegraRiddle) {
                    System.out.println("You aren't answering any riddles.");
                }
                break;

            case "save":
                int playerHP = player.getHP();
                gameState = new GameState(player.getName(), player.getCurrentRoom().getName(), inventory.getItemNames(), playerHP );

                SaveManager.saveGameState(gameState, Paths.get("saves"), "slot1_game");
                SaveManager.saveWorldState(worldState, Paths.get("saves"), "slot1_world");

                System.out.println("Game Saved!");
                System.exit(0);

            case "ask":
                String target = command.getSecondWord();
                if (target == null) {
                    System.out.println("--- You must say ' ask [char] ' ---");
                    return false; // done with this input but not quitting continue tp the next step
                }

                Character npc = player.getCurrentRoom().getCharacter(target);

                if (npc instanceof Dumbledore) {
                    ((Dumbledore) npc).speak(inventory, roomMap);   // Dumbledore being casted

                } else if (npc instanceof Allegra && !awaitingAllegraRiddle && !awaitingAllegraGuess) {
                    awaitingAllegraRiddle = true;

                    System.out.println("Allegra: Well well well, " + player.getName());
                    System.out.println("Looking to solve a riddle?\n(yes / no)");
                    System.out.println("To asnwer say: 'answer + an option'");
                }

                else if (npc != null) {
                    npc.speak();
                } else {
                    System.out.println("No one is here to ask.");
                }
                break;

            case "say":
                if (command.getSecondWord().equalsIgnoreCase("tralfaz") &&
                        player.getCurrentRoom().getName().equals("Dumbledore's Chamber")) {
                    teleport();
                } else {
                    System.out.println("Nothing happens.");
                }
                break;

            case "examine":
                if (command.getSecondWord().equalsIgnoreCase("mirror") &&
                        player.getCurrentRoom().getName().equals("Dumbledore's Chamber")) {
                    System.out.println("The mirror shimmers. The words 'say tralfaz' glow ...");

                } else if (command.getSecondWord().equalsIgnoreCase("poster") &&
                        player.getCurrentRoom().getName().equals("Castle Entrance")) {
                    System.out.println("The door North is locked. You must retrieve the key from the wolf Amarok");

                } else {
                    System.out.println("There’s nothing special here.");
                }
                break;

            case "look":
                player.getCurrentRoom().detailedInfo();
                player.getCurrentRoom().showItems();
                break;

            case "fight":
                if (player.getCurrentRoom().getName().equals("Death Forest")) {

                    if (worldState.getEventFlag("Amarok slain")) {
                        System.out.println("Amarok was slain");
                        break;
                    }
                    System.out.println("Amarok must be defeated to retrieve the key\n");
                    startCombat("Amarok");
                } else if (player.getCurrentRoom().getName().equals("Royal Hall")) {

                    if (worldState.getEventFlag("Oriz Defeated")) {
                        System.out.println("Oriz has already been defeated.");
                        break;
                    }
                        System.out.println("You challenge Oriz to a duel!");
                        startCombat("Oriz");
                } else {
                    System.out.println("There is nothing here to fight.");
                }
                break;

            default:
                System.out.println("I don't know what you mean...");
                break;
        }
        return false;
    }


    public void startCombat(String enemyName){
        if(enemyName.equalsIgnoreCase("Oriz")){
            inCombat= true;
            fightingOriz = true;

            System.out.println("You engage Oriz The Great");
            System.out.println("You have the first move. (Use \"attack <weapon>\" to strike)");
            return;
        }

        else if (enemyName.equalsIgnoreCase("Amarok")) {

            inCombat = true;
            fightingOriz = false;
            System.out.println("You engage Amarok!");
            System.out.println("You have the first move. (Use \"attack <weapon>\" to strike)");
            return;
        } else{
            System.out.println("There is nothing to fight here.");
        }
        if (worldState.getEventFlag("Amarok slain")) {
            System.out.println("Amarok is already dead.");
            return;
        }
    }

    public void handleOrizCombat(Command command) {
    String weapon = command.getSecondWord();

    if (weapon == null) {
        System.out.println("Attack with what");
        return;
    }
    int damage = 0;
    try {
        damage = player.attackWithWeapon(weapon, inventory);
        oriz.takeDamage(damage);
        System.out.println("Oriz HP: " + oriz.getHP());
    } catch (Exception e) {
        System.out.println("CRASH during weapon attack: " + e.getMessage());
        e.printStackTrace();
        inCombat = false;
        fightingOriz = false;
        return;
    }

    if (oriz.getHP() <= 0) {
        oriz.kill();
        System.out.println("You have defeated Oriz the Fraud");
        System.out.println("WOOOOHHHHHOOOO");
        worldState.setEventFlag("Oriz Defeated", true);
        player.endCredits();
        inCombat = false;
        fightingOriz = false;
        return;
    }

    if(oriz.getHP() <= 12 && !worldState.getEventFlag("Oriz Heal")){

        oriz.setHP(oriz.getHP() + 20);
        System.out.println("Hahaha You wont defeat me");
        System.out.println("Oriz drinks a health potion");
        System.out.println("Oriz new HP: " + oriz.getHP());
        worldState.setEventFlag("Oriz Heal", Boolean.TRUE);
    }

    if (!oriz.isAlive()) {
        System.out.println("No Great to sit on the throne");
        inCombat = false;
        fightingOriz = false;
        return;
    }

    int enemydamage = oriz.attack();
    player.takeDamage(enemydamage);
    System.out.println("HP: " + player.getHP());

    if (player.getHP() <= 0) {
        System.out.println("You died LOL: GG");
        System.exit(0);

        System.out.println("Choose your next attack: ");
    }
}

    public void handleAmarokAttack(Command command){
      String weapon = command.getSecondWord();

      if (weapon == null){
          System.out.println("attack with what");
          return;
      }

      int damage = player.attackWithWeapon(weapon, inventory);
      amarok.takeDamage(damage);
      System.out.println("Amarok HP: " + amarok.getHP());

        if (amarok.getHP() <= 0) {
            amarok.kill();
            System.out.println("You have slain the mythical Amarok!");
            System.out.println("You can now pick up the key.");
            worldState.setEventFlag("Amarok slain", true);
            inCombat = false;
            return;
        }

        if(!amarok.isAlive()){
            System.out.println("You have slain the myhtical Amarok\nYou can now pick up the key");
            worldState.setEventFlag("Amarok slain", Boolean.TRUE);
            inCombat = false;
            return;
        }

        int enemydamage = amarok.attack();
        player.takeDamage(enemydamage);
        System.out.println("HP: " + player.getHP());

        if (player.getHP() <= 0) {
            System.out.println("You died LOL: GG");
            System.exit(0);

            System.out.println("Choose your next attack: ");
        }
    }

    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }
        Direction direction = Direction.fromString(command.getSecondWord());
        if (direction == null) {
            System.out.println("Invalid direction.");
            return;
        }
        Room nextRoom = player.getCurrentRoom().getExit(direction);

        try {
            if (nextRoom == null) {
                throw new Exception();
                // System.out.println("There is no path that way!");
            }
        }
        catch (Exception RE) {
            System.out.println("There is no path that way!");
            return;
        }
        try{
            if (player.getCurrentRoom().getName().equals("Castle Entrance") && direction.equals(Direction.NORTH) && !inventory.hasItem("key")) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            if (player.getCurrentRoom().getName().equals("Castle Entrance") && direction.equals(Direction.NORTH)) {
                System.out.println("Did you examine the poster? Obviously you cant go through");
                return;
            }
        }

        player.displayHP();
        player.setCurrentRoom(nextRoom);
        System.out.println(player.getCurrentRoom() );
        System.out.println(nextRoom.Exits() + "\n" + nextRoom.Info());

        if (player.getCurrentRoom().getName().equals("Death Forest") && !worldState.getEventFlag("Amarok slain")) {
            System.out.println("You see Amarok in the distance. He growls...");
            System.out.println("Do you fight or retreat?");

        } else if(player.getCurrentRoom().getName().equals("Death Forest") && worldState.getEventFlag("Amarok slain")) {
            System.out.println("The remains of your battle with Amarok lie quietly.");

        }else if(player.getCurrentRoom().getName().equalsIgnoreCase("Royal Hall") && !worldState.getEventFlag("Oriz Defeated")){
            System.out.println("You notice Oriz The Great sitting on his throne looking down on you");
            System.out.println();
            System.out.println("Oriz: So you're the renknowned sorcer who defeated Amarok\nWhat a pleasure it is for " + player.getName() +
                    " to set foot in my premise.\nOn guard ");
            System.out.println("Type 'fight' to begin your battle against Oriz.");

        }
    }

    private void allegraCombat(Command command){
        if(worldState.getEventFlag("ask allegra")){
            System.out.println("Allegra: Look at you, a cunning sorcerer you are.");
            System.out.println("You got the wand already, there's no joy without a prize, get outta here");
            System.out.print("HP: " + player.getHP());
            System.out.println("\n" + player.getCurrentRoom().Exits());
            return;
        }
        String participate = command.getSecondWord();

        if(participate.equalsIgnoreCase("yes")){
            System.out.println("Allegra: " + "“Forged from nothing, born in dreams,\n" +
                    "I shape the worlds and split the seams.\n" +  "I grow with thought, I shrink with fear—\n" + "Found in every mind, yet owned by none,\n" +
                    "What am I?”");

            awaitingAllegraGuess = true;   // wait for next input
            awaitingAllegraRiddle = false; // finished this step
            return;

        } else if (participate.equalsIgnoreCase("no")) {
            System.out.println("Allegra: Very well continue on your journey but you may regret not playing ");
            System.out.println("\n");
            System.out.print("HP: " + player.getHP());
            System.out.println("\n" + player.getCurrentRoom().Exits());
            awaitingAllegraRiddle = false;
            return;

        } else{
            System.out.println("Come on " + player.getName() + " Your wasting my time, illetirate sorcerer");
            return;
        }
    }

    private void allegraCheckGuess(Command command) {
        awaitingAllegraGuess = false;

        String answer = command.getSecondWord();

        if(answer.equalsIgnoreCase("imagination")){
            System.out.println("Allegra: Hahaha well played " + player.getName());
            System.out.println("Well as a reward here you go");
            inventory.pickUp(roomMap.get("Library").getItemByName("wand"));
            System.out.println("Allegra: Not too shabby eh, now skedaddle and play with your wand\n");
            System.out.print("HP: " + player.getHP());
            System.out.println("\n" + player.getCurrentRoom().Exits());
            worldState.setEventFlag("ask allegra", Boolean.TRUE);
            awaitingAllegraRiddle = false;
            return;
        }
        else if(answer.equalsIgnoreCase("quit")){
            System.out.println("Allegra: Very well, better luck next time " + player.getName());
            System.out.print("\nHP: " + player.getHP());
            System.out.println("\n" + player.getCurrentRoom().Exits());
            awaitingAllegraRiddle = false;
            return;
        }
        else{
            System.out.println("Incorrect");
            System.out.println("Allegra: Not so easy is it");
            System.out.println("Here it is again\n");
            System.out.println("Allegra: " + "“Forged from nothing, born in dreams,\n" +
                    "I shape the worlds and split the seams.\n" +
                    "I grow with thought, I shrink with fear—\n" + "Found in every mind, yet owned by none,\n" +
                    "What am I?”");
        }
    }


    private void showInv(Command command) {
        inventory.showInventory();
    }

    private void printHelp() {
        System.out.println("You are lost.");
        System.out.print("Your command words are: ");
        parser.showCommands();
    }

    private void pick(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Pick what?");
            return;
        }
        String itemName = command.getSecondWord();
        Room currentRoom = player.getCurrentRoom();
        Item itemPick = currentRoom.getItemByName(itemName);

        if (itemPick == null) {
            System.out.println("There is no " + itemName + " here.");
            return;
        }
        else if (itemPick.getName().equalsIgnoreCase("key") && amarok.isAlive()){
            System.out.println("You must slay Amarok first to take the key");
        }
        else{
            inventory.pickUp(itemPick);
            currentRoom.removeItem(itemPick);
        }
    }

    private void drop(Command command) {

        if (!command.hasSecondWord()) {
            System.out.println("Drop what?");
            return;
        }

        String itemName = command.getSecondWord();
        Room currentRoom = player.getCurrentRoom();
        Item itemDrop = inventory.getItemByName(itemName);

        if (itemDrop == null) {
            System.out.println("There is no " + itemName + "in your inventory.");
        }
        else{
            inventory.drop(itemDrop);
            currentRoom.addItem(itemDrop);
        }
    }

    private void teleport() {
        if (player.getCurrentRoom().getName().equals("Dumbledore's Chamber")) {
            System.out.println("You float up and through the mirror...");
            player.setCurrentRoom(roomMap.get("Crossroad"));
            System.out.println(player.getCurrentRoom().getDescription() + player.getCurrentRoom().Exits());

        } else{
            System.out.println("teleport not usable here");
        }
    }

    public Parser getParser() {
        return parser;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("-------------------- Voldemorts Revenge ---------------------");
        GameEngine game = new GameEngine();
    }
}