package edu.kings;

import java.util.HashMap;
import java.util.ArrayList;
/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "Campus of Kings" application. "Campus of Kings" is a
 * very simple, text based adventure game.
 *
 * A "Room" represents one location in the scenery of the game. It is connected
 * to other rooms via doors. 
 * * @author Maria Jump
 * @version 2015.02.01
 *
 * Used with permission from Dr. Maria Jump at Northeastern University
 */

public class Room {
    /** Counter for the total number of rooms created in the world. */
    private static int counter;
    /** The name of this room. Room names should be unique. */
    private String name;
    /** The description of this room. */
    private String description;
    
    /** The exits of this room. */
    private HashMap<String, Door> exits;
    
    /** The items in this room. */
    private HashMap<String, Item> items;

    private ArrayList<Enemy> enemies; 
    
    /**
     * Static initializer.
     */
    static {
        counter = 0;
    }
    
    	
    
  
    /**
     * Create a room described "description". Initially, it has no exits.
     * "description" is something like "a kitchen" or "an open court yard".
     *
     * @param name  The room's name.
     * @param description The room's description.
     */
    public Room(String name, String description) {
        this.name = name;
        this.description = description;
        this.exits = new HashMap<String, Door>();
        this.items = new HashMap<String, Item>(); 
        this.enemies = new ArrayList<Enemy>();
        counter++;
    }
    
    
    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public boolean hasEnemies() {
        return !enemies.isEmpty();
    }
    
    public String getName() {
        return name;
    }
   

    /**
     * Add an item to this room. 
     * @param item The item to be added.
     */
    public void addItem(Item item) {
        items.put (item.getName().toLowerCase(), item); 
    }

    /**
     * Get an item from this room by its name. 
     * @param name The name of the item.
     * @return The item or null if not found.
     */
    public Item getItem(String name) {
        return items.get(name.toLowerCase()); 
    }

    
    
    /**
     * Remove an item from the room by its name. 
     * @param name The name of the item to remove.
     * @return The actual item removed, or null if it doesn't exist. 
     */
    public Item removeItem(String name) {
        return items.remove(name.toLowerCase()); 
    }

    /**
     * Defines an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor The door in the given direction.
     */
    public void setExit(String direction, Door neighbor) {
        exits.put(direction, neighbor);
    }

    /**
     * Gets a door in a specified direction if it exists.
     * @param direction The direction of the door.
     * @return The door in the specified direction or null if it does not exist.
     */
    public Door getExit(String direction) {
        return exits.get(direction);
    }

    /**
     * Returns a description of the room, including exits and items. [cite: 39]
     * @return A string description of the room.
     */
    @Override
    public String toString() {
        String returnString = name + ":\n"; 
        returnString += "You are " + description + "\n";
        
       
        returnString += "Exits: "; 
        for (String exitName : exits.keySet()) {
            returnString += exitName + " "; 
        }
        returnString += "\n";
        
    
        if (!items.isEmpty()) {
            returnString += "Items: "; 
            for (String itemName : items.keySet()) {
                returnString += itemName + " "; 
            }
            returnString += "\n";
        }
        
        return returnString; 
    }

    /**
     * Returns the number of rooms that have been created in the world.
     * @return The number of rooms that have been created in the world.
     */
    public static int getCounter() {
        return counter;
    }
}