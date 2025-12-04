package com.magestale;

public enum Direction {
    NORTH, EAST, SOUTH, WEST;

    public static Direction fromString(String input) {
        if (input == null) return null;
        input = input.toLowerCase();

        switch (input) {
            case "north":
            case "n":
                return NORTH;
            case "east":
            case "e":
                return EAST;
            case "south":
            case "s":
                return SOUTH;
            case "west":
            case "w":
                return WEST;
            default:
                return null;
        }
    }
}
