package edu.kings;

import java.util.HashMap;

/**
 * * @author Axel E
 * @version 1.1
 */ 
public class Player { 

    private Room currentLocation;
    /** The player's inventory. */
    private HashMap<String, Items> inventory;
    /** The player's maximum carry weight. */
    private static final int MAX_WEIGHT = 20; 

    public Player(Room startingRoom) {
        this.currentLocation = startingRoom;
        this.inventory = new HashMap<String, Items>();
    }

    /**
     * Add an item to the player's inventory. [cite: 48]
     * This method returns whether or not the item was successfully added. [cite: 49]
     * The player's inventory is limited by some maximum weight. [cite: 50]
     * @param item The item to add.
     * @return true if added, false if it's too heavy.
     */
    public boolean addItem(Items item) {
        boolean added = false;
        int currentWeight = 0;

        // Calculate current weight of all items
        for (Items i : inventory.values()) {
            currentWeight += i.getWeight();
        }

        // Check if adding the new item exceeds MAX_WEIGHT [cite: 51]
        if ((currentWeight + item.getWeight()) <= MAX_WEIGHT) {
            inventory.put(item.getName().toLowerCase(), item);
            added = true;
        }
        
        return added;
    }

    /**
     * Get an item from the player's inventory by its name. [cite: 52]
     * @param name The name of the item.
     * @return The item or null if not found.
     */
    public Items getItem(String name) {
        return inventory.get(name.toLowerCase());
    }

    /**
     * Remove an item from the player's inventory. [cite: 53]
     * @param name The name of the item to remove.
     * @return The removed item or null.
     */
    public Items removeItem(String name) {
        return inventory.remove(name.toLowerCase());
    }

    public Room getCurrentLocation() {
        return currentLocation;
    }

    /**
     * Changes the player's location to a new room.
     * @param nextRoom The new room the player moves to.
     */
    public void setCurrentLocation(Room nextRoom) {
        this.currentLocation = nextRoom;
    }
}