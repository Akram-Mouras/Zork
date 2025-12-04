package com.magestale;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Inventory<T extends Item> implements Serializable {
    public static final long serialVersionUID = 1L;
    private ArrayList<T> items = new ArrayList<>();
    private WorldState worldState;

    public void setWorldState(WorldState worldState) {
        this.worldState = worldState;
    }

    public void removeItem(T item) {
        items.remove(item);
    }
    // Add an item
    public void pickUp(T item) {
        if (!items.contains(item)) {
            items.add(item);
            System.out.println("You picked up: " + item);
            worldState.addRemovedItem(item.getName());

        } else {
            System.out.println("You already have the " + item + ".");
        }
    }
    // Remove an item
    public void drop(T item) {
        if (items.contains(item)) {
            items.remove(item);
            System.out.println("You dropped: " + item);
        } else {
            System.out.println("You don’t have that item.");
        }
    }

    public boolean hasItem(String name) {
        for (T item : items) {
            if (item.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
    // Display inventory
    public void showInventory() {
        if (items.isEmpty()) {
            System.out.println("You have nothing.");
        } else {
            System.out.print("You have: ");
            for (T item : items) {
                System.out.print(item + " ");
            }
            System.out.println();
        }
    }

    public void ClearInventory() {
        items.clear();
        System.out.println("You have nothing.");
    }

    public T getItemByName(String name) {
        for (T item : items) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item; // found item
            }
        }
        return null; // not found
    }

    public List<String> getItemNames() {
        List<String> names = new ArrayList<>();
        for (T item : items) {
            names.add(item.getName());
        }
        return names;
    }

    public ArrayList<T> getItems() {
        return items;
    }
}