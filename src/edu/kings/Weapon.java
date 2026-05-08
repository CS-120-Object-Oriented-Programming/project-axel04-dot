package edu.kings;


public class Weapon {

    /** The name of the weapon. */
    private String name;
    /** The philosophy/description of the weapon. */
    private String description;
    /** The emoji icon for the weapon. */
    private String icon;
    /** The affinity score accumulated during the test. */
    private int score;
    private String normalAbilityName;
    private String normalAbilityDescription;
    private String ultName;
    private String ultDescription;
    private int ultCooldown;
    private int ultCurrentCooldown;

  
    public void setNormalAbility(String name, String description) {
        this.normalAbilityName = name;
        this.normalAbilityDescription = description;
    }

    public void setUlt(String name, String description, int cooldown) {
        this.ultName = name;
        this.ultDescription = description;
        this.ultCooldown = cooldown;
        this.ultCurrentCooldown = 0;
    }
    public String getNormalAbilityName() { return normalAbilityName; }
    
    public String getNormalAbilityDescription() { return normalAbilityDescription; }
    
    public String getUltName() { return ultName; }
    
    public String getUltDescription() { return ultDescription; }
    
    public int getUltCooldown() { return ultCooldown; }
    
    public int getUltCurrentCooldown() { return ultCurrentCooldown; }
    
    public void setUltCurrentCooldown(int cd) { this.ultCurrentCooldown = cd; }
   

    /**
     * Constructor for Weapon.
     * @param name The weapon's name.
     * @param description The weapon's philosophy.
     * @param icon The weapon's emoji icon.
     */
    public Weapon(String name, String description, String icon) {
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.score = 0;
    }

    /** @return The weapon's name. */
    public String getName() {
        return name;
    }

    /** @return The weapon's description. */
    public String getDescription() {
        return description;
    }

    /** @return The weapon's icon. */
    public String getIcon() {
        return icon;
    }

    /** @return The weapon's current score. */
    public int getScore() {
        return score;
    }

    /** Adds points to this weapon's score. */
    public void addScore(int points) {
        this.score += points;
    }

    /** Resets the weapon's score to 0. */
    public void resetScore() {
        this.score = 0;
    }
}