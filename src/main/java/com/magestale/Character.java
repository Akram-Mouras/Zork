package com.magestale;

import java.io.Serializable;
import java.util.*;

public class Character implements Serializable, NPCSpeak{

    public static final long serialVersionUID = 1L;
    protected String name;
    protected Room currentRoom;
    protected int HP;
    protected WorldState currentWorldState;

    public void setWorldState(WorldState worldState) {
        this.currentWorldState = worldState;
    }


    public Character(String name, Room startingRoom, int HP) {
        this.name = name;
        this.currentRoom = startingRoom;
        this.HP = HP;
    }

    public Character(String name, Room startingRoom) {
        this.name = name;
        this.currentRoom = startingRoom;
        this.HP = 0;
    }

    public Character() {}

    public String getName() {
        return name;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room room) {
        this.currentRoom = room;
    }

    public int attackWithWeapon(String weapon, Inventory inventory){
        int pDamage = 0;

        if (weapon == null) {
            System.out.println("No weapon provided.");
            return 0;
        }

        if (weapon.equalsIgnoreCase("sword")) {
            if (!inventory.hasItem("sword")) {
                System.out.println("You don't have a sword.");
                return 0;
            }
            pDamage = new Random().nextInt(1,7);
            System.out.println("You slash with the sword for " + pDamage + " damage.");
            return pDamage;
        }

        if (weapon.equalsIgnoreCase("wand")) {
            if (!inventory.hasItem("wand")) {
                System.out.println("You don't have a wand.");
                return 0;
            }
            pDamage = new Random().nextInt(3,12);
            System.out.println("You blast with the wand for " + pDamage + " damage.");
            return pDamage;
        }

        System.out.println("You can't attack with '" + weapon + "'.");
        return 0;
    }

    public void move(Direction direction) {
        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom != null) {
            currentRoom = nextRoom;
            System.out.println("You moved to: " + currentRoom.getDescription());
        } else {
            System.out.println("You can't go that way!");
        }
    }

    public void displayHP(){
        System.out.print("HP: " + this.HP + "\n");
    }

    public void takeDamage(int damage){
        this.HP -= damage;
    }

    public void speak(){
        System.out.println("You are speaking!");
    }

    public void endCredits() {
            System.out.println("\n\n================= THE END =================\n");

            System.out.println("\nRolling credits...");

            System.out.println("\n\nA Game By: Akram");

            System.out.println("Programming: Also Akram");

            System.out.println("Plot: Also Akram\n\n");

            System.out.println("Bug Fixing: Hahaha");

            System.out.println("Special Thanks:");

            System.out.println(" - Dumbledore for being copyright free");
            System.out.println("ChatGPT for the times i was down ");
    }

    @Override
    public String toString() {
        return "Name: " + name +
                "\nCurrent Room: " + currentRoom.getName();
    }
}

