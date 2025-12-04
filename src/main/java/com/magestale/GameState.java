package com.magestale;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameState implements Serializable { //player progress
    private static final long serialVersionUID = 1L;

    private String playerName;
    private String currentRoomName;
    private List<String> inventoryItems;
    private WorldState worldState;
    private Item item;
    private int HP;

    public GameState(String playerName, String currentRoomName, List<String> inventoryItems, int HP) {
        this.playerName = playerName;
        this.currentRoomName = currentRoomName;
        this.inventoryItems = inventoryItems;
        this.worldState = worldState;
        this.item = item;
        this.HP = HP;
    }

    public String getPlayerName() {
        return playerName;
    }
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getCurrentRoomName() {
        return currentRoomName;
    }
    public void setCurrentRoomName(String currentRoomName) {
        this.currentRoomName = currentRoomName;
    }

    public List<String> getInventoryItems() {
        return inventoryItems;
    }
    public void setInventoryItems(List<String> inventoryItems) {
        this.inventoryItems = inventoryItems;
    }

    public int getHP(){
        return HP;
    }
    public void setHP(int HP) {
        this.HP = HP;
    }

    public WorldState getWorldState() {
        return worldState;
    }
    public void setWorldState(WorldState worldState) {
        this.worldState = worldState;
    }
}
