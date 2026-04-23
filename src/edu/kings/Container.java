package edu.kings;

import java.util.ArrayList;

public class Container extends Item {
    
    private ArrayList<Item> items;

    public Container(String name, String description, Integer weight, Integer value) {
      
        super(name, description, weight, value);
        this.items = new ArrayList<>();
    }

    
    public void addItem(Item item) {
        items.add(item);
    }

   
    public Item removeItem(String name) {
        Item foundItem = null;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getName().equalsIgnoreCase(name)) {
                foundItem = items.remove(i);
                break; 
            }
        }
        return foundItem;
    }

   
    @Override
    public String toString() {
        String baseDescription = super.toString();
        if (items.isEmpty()) {
            return baseDescription + " (empty)";
        } else {
            String content = " you can see:";
            for (Item item : items) {
                content += "\n - " + item.getName();
            }
            return baseDescription + content;
        }
    }
}