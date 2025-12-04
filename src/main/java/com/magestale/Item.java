package com.magestale;

import java.io.Serializable;

public class Item implements Serializable {
    public static final long serialVersionUID = 1L;

    private String name;
    private String description;

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return name;
    }

    public String detailedInfo() {
        return name + ": " + description;
    }
}
