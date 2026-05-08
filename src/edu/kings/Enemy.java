package edu.kings;

/**
 * Represents an enemy in the game.
 */
public class Enemy {

    private String name;
    private int maxHp;
    private int currentHp;
    private int damage;

    public Enemy(String name, int hp, int damage) {
        this.name = name;
        this.maxHp = hp;
        this.currentHp = hp;
        this.damage = damage;
    }

    public String getName() { return name; }
    public int getCurrentHp() { return currentHp; }
    public int getMaxHp() { return maxHp; }
    public int getDamage() { return damage; }

    public boolean isAlive() { return currentHp > 0; }

    public void takeDamage(int amount) {
        currentHp -= amount;
        if (currentHp < 0) currentHp = 0;
    }

    public void heal(int amount) {
        currentHp += amount;
        if (currentHp > maxHp) currentHp = maxHp;
    }

    public void takeTurn(Player player) {
        Writer.println(name + " attacks you for " + damage + " damage!");
        player.takeDamage(damage);
    }

    @Override
    public String toString() {
        return name + " | HP: " + currentHp + "/" + maxHp;
    }
}