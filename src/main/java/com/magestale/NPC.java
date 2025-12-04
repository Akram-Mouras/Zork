package com.magestale;

import java.util.*;

public class NPC extends Character{

    public NPC(String name, Room startingRoom, int HP) {
        super(name, startingRoom, HP);
    }

    @Override
    public void speak() {
        System.out.println("Is an NPC actually speaking!");
    }
}

class Dumbledore extends NPC{
    public Dumbledore(String name, Room startingRoom) {
        super(name, startingRoom,0);
    }

    public void speak(Inventory inventory, Map<String, Room> roomMap) {
        if (currentWorldState.getEventFlag("ask dumbledore")) {
            System.out.println("Dumbledore: You already possess the amulet, brave mage. Proceed on your quest.");
            System.out.println("----- You must examine the mirror again, boohoo-----");
            return;
        }

        System.out.println("Dumbledore: Brave mage, Voldemort is causing havoc\n" +
                "This amulet shall guide you");

        inventory.pickUp(roomMap.get("Dumbledore's Chamber").getItemByName("amulet"));

        System.out.println("----- To progress you must examine the mirror -----");
        currentWorldState.setEventFlag("ask dumbledore", Boolean.TRUE);
    }
}

class Allegra extends NPC{

    public Allegra(String name, Room startingRoom) {
        super(name, startingRoom, 0);
    }

    public void speak(Character player, Inventory inventory, Map<String, Room> roomMap) {}
}

class Amarok extends NPC {
    public Amarok (String name, Room startingRoom){
        super(name, startingRoom, 20);
    }

    private boolean alive = true;
    public boolean isAlive() {
        return alive;
    }

    public void kill() {
        alive = false;
    }

    public int attack(){
        int Damage;
        Damage = new java.util.Random().nextInt(2,8);

        System.out.println("Amarok dealt " + Damage + " damage");
        return Damage;
    }
}

class Oriz extends NPC {

    public Oriz(String name, Room startingRoom, int HP) {
        super(name, startingRoom, 20);
    }

    private boolean alive = true;
    public boolean isAlive() {
        return alive;
    }

    public void kill() {
        alive = false;
    }

    public int attack() {
        int Damage;
        Damage = new java.util.Random().nextInt(5, 15);

        System.out.println("Oriz dealt " + Damage + " damage");
        return Damage;
    }
}