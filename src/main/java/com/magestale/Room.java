package com.magestale;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class Room implements Serializable {
    public static final long serialVersionUID = 1L;

    private String description;
    private Map<Direction, Room> exits; // Map direction to neighboring Room
    private String name;
    private ArrayList<Item> Items = new ArrayList<>();
    private ArrayList<Character> characters = new ArrayList<>();


    public Room(String description) {
        this.description = description;
        exits = new HashMap<>(); // new hashmap for exits so room connects to others
    }

    public Room(String name, String description) {
        this.description = description;
        exits = new HashMap<>();
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public void setExit(Direction direction, Room neighbor) {

        exits.put(direction, neighbor);
    }

    public Room getExit(Direction direction) {

        return exits.get(direction);
    }

    public String getExitString() {
        StringBuilder sb = new StringBuilder();
        for (Direction direction : exits.keySet()) {
            sb.append(direction).append(" ");
        }
        return sb.toString().trim();
    }

    public String Exits() {
        return "Exits: " + getExitString();
    }

    public void addItem(Item item) {
        Items.add(item);
    }

    public void removeItem(Item item) {
        Items.remove(item);
    }

    public ArrayList<Item> getItems() {
        return Items;
    }

    public Item getItemByName(String name) {
        for(Item item: Items){
            if(item.getName().equalsIgnoreCase(name)){
                return item;
            }
        }
        return null;
    }

    public void showItems() {
        for (Item item : Items) {
            if(Items.isEmpty()){
                System.out.println("No items here");
            }
            else {
                System.out.println(item.getName() + ":" + item.getDescription());
            }
        }
    }

    @Override
    public String toString() {
        return name;
    }

    public String detailedInfo() {
        return name + ": " + description;
    }

    public String Info() {
        return description;
    }

    public void addCharacter(Character c) {
        characters.add(c);
    }

    public Character getCharacter(String name) {
        for (Character c : characters) {
            if (c.getName().equalsIgnoreCase(name)) {
                return c;
            }
        }
        return null;
    }
}