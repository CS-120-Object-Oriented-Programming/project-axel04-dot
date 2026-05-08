package edu.kings;

import java.util.HashMap;

/**
 * * @author Axel E
 * @version 1.1
 */ 
public class Player { 

    private Room currentLocation;
    /** The player's inventory. */
    private HashMap<String, Item> inventory;
    /** The player's maximum carry weight. */
    private static final int MAX_WEIGHT = 20; 
    private int maxHp;
    private int currentHp;
    private double baseDamage;
    private int flasks;
    private int maxFlasks;
    private Weapon weapon;

    public Player(Room startingRoom) {
        this.currentLocation = startingRoom;
        this.inventory = new HashMap<String, Item>();
        this.maxHp = 20;
        this.currentHp = 20;
        this.baseDamage = 3;
        this.flasks = 4;
        this.maxFlasks = 4;
        this.weapon = null;
    }

    public int getCurrentHp() { return currentHp; }
    public int getMaxHp() { return maxHp; }
    public double getBaseDamage() { return baseDamage; }
    public int getFlasks() { return flasks; }
    public Weapon getWeapon() { return weapon; }

    public void setWeapon(Weapon weapon) { this.weapon = weapon; }
    public void setBaseDamage(double damage) { this.baseDamage = damage; }

    public void takeDamage(int amount) {
        currentHp -= amount;
        if (currentHp < 0) currentHp = 0;
    }

    public void heal(int amount) {
        currentHp += amount;
        if (currentHp > maxHp) currentHp = maxHp;
    }

    public void gainMaxHp(int amount) {
        maxHp += amount;
        currentHp += amount;
    }

    public boolean useFlask() {
        if (flasks > 0) {
            flasks--;
            heal((int)(maxHp * 0.25));
            return true;
        }
        return false;
    }

    public void refillFlasks() {
        flasks = maxFlasks;
    }

    public boolean isAlive() {
        return currentHp > 0;
    }
   
    
    public String getInventoryString() {
        if (inventory.isEmpty()) {
            return "You are not carrying anything.";
        }
        String result = "You are carrying:";
        for (Item item : inventory.values()) {
            result += "\n - " + item.getName();
        }
        return result;
    }
    
    
    public boolean addItem(Item item) {
        boolean added = false;
        int currentWeight = 0;

       
        for (Item i : inventory.values()) {
            currentWeight += i.getWeight();
        }

       
        if ((currentWeight + item.getWeight()) <= MAX_WEIGHT) {
            inventory.put(item.getName().toLowerCase(), item);
            added = true;
        }
        
        return added;
    }

   
   
    public Item getItem(String name) {
        return inventory.get(name.toLowerCase());
    }

   
    public Item removeItem(String name) {
        return inventory.remove(name.toLowerCase());
    }

    public Room getCurrentLocation() {
        return currentLocation;
    }

  
    public void setCurrentLocation(Room nextRoom) {
        this.currentLocation = nextRoom;
    }
}