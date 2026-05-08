package edu.kings;

import java.util.Random;

/**
 * Represents a boss enemy with special abilities.
 * Extends Enemy.
 */
public class Boss extends Enemy {

    private Random random;

    // Tracks special states
    private boolean lastBreathUsed;   // Kiro
    private boolean pressureTracking; // Alan
    private String lastPlayerMove;    // Alan pressure
    private int pressureCount;        // Alan pressure

    // Identity — tracks which skills were copied
    private String copiedSkillName;

    // Reference to player and combat for special effects
    // These are set by Combat when the boss takes its turn
    private int coldHeartStacks;      // Liebe
    private boolean tranquiloActive;  // Alan
    private int tranquiloTurns;
    private boolean whoYouAreActive;  // Identity
    private int kaiokenStacks;        // Kiro
    private Weapon copiedWeapon;  
    private boolean youHealed;    
    private boolean youUltUsed;   

    public Boss(String name, int hp, int damage) {
        super(name, hp, damage);
        this.random = new Random();
        this.lastBreathUsed = false;
        this.pressureTracking = false;
        this.lastPlayerMove = "";
        this.pressureCount = 0;
        this.copiedSkillName = "";
        this.coldHeartStacks = 0;
        this.tranquiloActive = false;
        this.tranquiloTurns = 0;
        this.whoYouAreActive = false;
        this.kaiokenStacks = 0;
    } 
    public Boss(String name, int hp, int damage, Weapon weapon) {
        this(name, hp, damage);
        this.copiedWeapon = weapon;
        this.youHealed = false;
        this.youUltUsed = false;
    }

    // -----------------------------------------------------------------------
    // Getters for Combat to check active effects
    // -----------------------------------------------------------------------

    public boolean isColdHeartActive() { return coldHeartStacks > 0; }
    public int getColdHeartStacks() { return coldHeartStacks; }
    public boolean isTransquiloActive() { return tranquiloActive; }
    public boolean isWhoYouAreActive() { return whoYouAreActive; }
    public void clearWhoYouAre() { whoYouAreActive = false; }
    public void clearTranquilo() { tranquiloActive = false; tranquiloTurns = 0; }
    public void tickTranquilo() {
        if (tranquiloActive) {
            tranquiloTurns--;
            if (tranquiloTurns <= 0) tranquiloActive = false;
        }
    }

    // -----------------------------------------------------------------------
    // Pressure tracking — called by Combat
    // -----------------------------------------------------------------------

    public boolean checkPressure(String move) {
        if (move.equals(lastPlayerMove)) {
            pressureCount++;
            if (pressureCount >= 2) {
                pressureCount = 0;
                lastPlayerMove = move;
                return true; // damage should be reflected
            }
        } else {
            pressureCount = 1;
            lastPlayerMove = move;
        }
        return false;
    }

    // -----------------------------------------------------------------------
    // Last Breath — Kiro survives one hit
    // -----------------------------------------------------------------------

    public boolean tryLastBreath(int incomingDamage) {
        if (!lastBreathUsed && getCurrentHp() - incomingDamage <= 0) {
            lastBreathUsed = true;
            Writer.println("Last Breath! " + getName() + " refuses to fall — survives with 3 HP!");
            return true;
        }
        return false;
    }

    // -----------------------------------------------------------------------
    // Boss turn
    // -----------------------------------------------------------------------

    @Override
    public void takeTurn(Player player) {
        if (random.nextInt(100) < 20) {
           
            normalAttack(player);
        } else {
           
            useAbility(player);
        }
    }

    private void normalAttack(Player player) {
        int dmg = getDamage() + kaiokenStacks;
        Writer.println(getName() + " attacks you for " + dmg + " damage!");
        player.takeDamage(dmg);
    }

    private void useAbility(Player player) {
        String name = getName();
        if (name.equals("Liebe"))     liebeAbility(player);
        else if (name.equals("Alan")) alanAbility(player);
        else if (name.equals("Kiro")) kiroAbility(player);
        else if (name.equals("Identity")) identityAbility(player);
        else if (name.equals("you"))  Mirror(player);
    }

    // -----------------------------------------------------------------------
    // Liebe abilities
    // -----------------------------------------------------------------------

    private void liebeAbility(Player player) {
        int roll = random.nextInt(3);
        switch (roll) {
            case 0: liebeColdHeart(player); break;
            case 1: liebeILoveYou(player);  break;
            case 2: HateAttack(player); break;
        }
    }

    private void liebeColdHeart(Player player) {
        coldHeartStacks++;
        Writer.println("Liebe uses Cold Heart! Your healing is reduced. (Stack " + coldHeartStacks + ")");
    }

    private void liebeILoveYou(Player player) {
        int dmg = getDamage();
        player.takeDamage(dmg);
        int reflected = (int)(dmg * 0.5);
        // Heals self
        Writer.println("Liebe uses I Love You! Deals " + dmg + " damage and recovers " + reflected + " HP.");
        // Simulate self-heal by reducing effective damage taken
    }

    private void HateAttack(Player player) {
        if (random.nextInt(100) < 60) {
            int critDmg = getDamage() * 2;
            Writer.println("Liebe uses Your Like My Brother — CRITICAL! " + critDmg + " damage!");
            player.takeDamage(critDmg);
        } else {
            int dmg = getDamage();
            Writer.println("Liebe uses Your Like My Brother — normal hit for " + dmg + " damage.");
            player.takeDamage(dmg);
        }
    }

    // -----------------------------------------------------------------------
    // Alan abilities
    // -----------------------------------------------------------------------

    private void alanAbility(Player player) {
        int roll = random.nextInt(3);
        switch (roll) {
            case 0: alanRestringed(player); break;
            case 1: alanTranquilo(player);  break;
            case 2: alanPressure(player);   break;
        }
    }

    private void alanRestringed(Player player) {
        Writer.println("Alan uses Restringed! You can only dodge next turn.");
        // Combat checks this via a flag — handled in Combat.java
    }

    private void alanTranquilo(Player player) {
        tranquiloActive = true;
        tranquiloTurns = 2;
        Writer.println("Alan uses Tranquilo! Your ult is sealed for 2 turns.");
    }

    private void alanPressure(Player player) {
        pressureTracking = true;
        Writer.println("Alan uses Pressure! If you repeat the same move twice, it will be reflected back.");
    }

    // -----------------------------------------------------------------------
    // Kiro abilities
    // -----------------------------------------------------------------------

    private void kiroAbility(Player player) {
        int roll = random.nextInt(3);
        switch (roll) {
            case 0: kiroKaioken(player);   break;
            case 1: kiroFirePunch(player); break;
            case 2: kiroLastBreath(player); break;
        }
    }

    private void kiroKaioken(Player player) {
        kaiokenStacks++;
        int dmg = getDamage() + kaiokenStacks;
        Writer.println("Kiro uses Kaio Ken! Attack boosted (+" + kaiokenStacks + "). Hits for " + dmg + "!");
        player.takeDamage(dmg);
    }

    private void kiroFirePunch(Player player) {
        if (random.nextInt(100) < 50) {
            int critDmg = getDamage() * 2;
            Writer.println("Kiro uses Fire Punch — CRITICAL! " + critDmg + " damage!");
            player.takeDamage(critDmg);
        } else {
            int dmg = getDamage();
            Writer.println("Kiro uses Fire Punch — " + dmg + " damage.");
            player.takeDamage(dmg);
        }
    }

    private void kiroLastBreath(Player player) {
        // Passive — triggered in takeDamage override
        int dmg = getDamage();
        Writer.println("Kiro attacks with determination for " + dmg + " damage!");
        player.takeDamage(dmg);
        Writer.println("(Last Breath is passive — Kiro will survive one killing blow.)");
    }

    // -----------------------------------------------------------------------
    // Identity abilities
    // -----------------------------------------------------------------------

    private void identityAbility(Player player) {
        int roll = random.nextInt(3);
        switch (roll) {
            case 0: identityIAmYou(player);    break;
            case 1: identityWhoYouAre(player); break;
            case 2: identityWhoIAm(player);    break;
        }
    }

    private void identityIAmYou(Player player) {
        if (player.getWeapon() != null) {
            copiedSkillName = player.getWeapon().getNormalAbilityName();
            int dmg = (int)(player.getBaseDamage() * 1.5);
            Writer.println("Identity uses I Am You — copies " + copiedSkillName + "! Deals " + dmg + " damage!");
            player.takeDamage(dmg);
        } else {
            normalAttack(player);
        }
    }

    private void identityWhoYouAre(Player player) {
        whoYouAreActive = true;
        Writer.println("Identity uses Who You Are? Your skills are sealed for 1 turn!");
    }

    private void identityWhoIAm(Player player) {
        // Uses a random boss ability
        int roll = random.nextInt(3);
        Writer.println("Identity uses Who I Am? — channeling a past boss...");
        switch (roll) {
            case 0:
                Writer.println("(Liebe's power!)");
                HateAttack(player);
                break;
            case 1:
                Writer.println("(Alan's power!)");
                alanTranquilo(player);
                break;
            case 2:
                Writer.println("(Kiro's power!)");
                kiroFirePunch(player);
                break;
        }
    }
 // -----------------------------------------------------------------------
    // You abilities (mirror of player) - High Copy Probability Version
    // -----------------------------------------------------------------------

    private void Mirror(Player player) {
        Weapon w = copiedWeapon != null ? copiedWeapon : player.getWeapon();
        double hpPercent = (double) getCurrentHp() / getMaxHp();


        if (hpPercent < 0.35 && !youHealed) {
            youHealed = true;
            int healAmt = (int)(getMaxHp() * 0.20);
            heal(healAmt);
            Writer.println("You... use a shadow flask. (+" + healAmt + " HP)");
            return;
        }

        int roll = random.nextInt(100);
        Writer.println("DEBUG roll: " + roll);

        if (random.nextInt(100) < 70 || youUltUsed) {
            int dmg = (int)(getDamage() * 1.5);
            Writer.println("You use " + w.getNormalAbilityName() + "! " + dmg + " damage!");
            player.takeDamage(dmg);
        } else {
            youUltUsed = true;
            int dmg = (int)(getDamage() * 3);
            Writer.println("You use " + w.getUltName() + "! " + dmg + " damage!");
            player.takeDamage(dmg);
        }
    }
   
    // -----------------------------------------------------------------------
    // Override takeDamage for Last Breath (Kiro)
    // -----------------------------------------------------------------------

    @Override
    public void takeDamage(int amount) {
        if (getName().equals("Kiro") && tryLastBreath(amount)) {
            // Set HP to 3 instead of dying
            super.takeDamage(getCurrentHp() - 3);
        } else {
            super.takeDamage(amount);
        }
    }
}