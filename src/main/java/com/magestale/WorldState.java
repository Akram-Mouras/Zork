package com.magestale;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorldState implements Serializable {   //store facts abt what changed, memory of the world discluding player

    List<String> RemovedItems;
    Map<String, Boolean> CheckEvents;

    public WorldState () {
        RemovedItems = new ArrayList<>();
        CheckEvents = new HashMap<>();
    }

    public void addRemovedItem(String name) {
        if(!RemovedItems.contains(name)) {
            RemovedItems.add(name);
        }
    }

    public boolean isItemRemoved(String name) {
        return RemovedItems.contains(name);
    }

    public List<String> getRemovedItems() {
        return RemovedItems;
    }

    public void setEventFlag(String EventName, Boolean value) {
        CheckEvents.put(EventName, value);
    }

    public boolean getEventFlag(String EventName) {   //Did this event happen
        return CheckEvents.getOrDefault(EventName, false);  //if event exists return stored value if not return false
    }
}