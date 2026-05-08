package edu.kings;

import java.util.Random;

/**
 * Handles turn-based combat between the player and an enemy.
 */
public class Combat {

    private Player player;
    private Enemy enemy;
    private Random random;

    // Ult
    private boolean ultUsed;

    // Status effects on player
    private boolean playerDodging;
    private boolean playerParrying;
    private boolean bloodBondActive;
    private boolean berserkerActive;
    private int berserkerTurns;
    private boolean zoneActive;
    private int zoneTurns;
    private boolean sepukuActive;
    private int sepukuTurns;
    private int sepukuBonus;
    private boolean deathPenaltyActive;
    private int deathPenaltyTurns;
    private boolean mutualLoveActive;
    private int mutualLoveTurns;
    private boolean shieldActive;
    private boolean divineShieldActive;
    private int defenseBonus;
    private boolean handToHandCounter;
    private boolean pencilBoostActive;
    private int pencilBoostTurns;
    
 // Boss effects on player
    private boolean restringedActive;  
    private boolean whoYouAreActive;   

    // Status effects on enemy
    private boolean enemyWeakened;
    private boolean enemyBleeding;
    private int bleedTurns;
    private boolean devilsBloodActive;
    private int devilsBloodTurns;

    public Combat(Player player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;
        this.random = new Random();
        this.ultUsed = false;
        this.playerDodging = false;
        this.playerParrying = false;
        this.bloodBondActive = false;
        this.berserkerActive = false;
        this.berserkerTurns = 0;
        this.zoneActive = false;
        this.zoneTurns = 0;
        this.sepukuActive = false;
        this.sepukuTurns = 0;
        this.sepukuBonus = 0;
        this.deathPenaltyActive = false;
        this.deathPenaltyTurns = 0;
        this.mutualLoveActive = false;
        this.mutualLoveTurns = 0;
        this.shieldActive = false;
        this.divineShieldActive = false;
        this.defenseBonus = 0;
        this.handToHandCounter = false;
        this.enemyWeakened = false;
        this.enemyBleeding = false;
        this.bleedTurns = 0;
        this.devilsBloodActive = false;
        this.devilsBloodTurns = 0;
    }

    public boolean start() {
        Writer.println("=================================");
        Writer.println("  " + enemy.getName() + " appears!");
        Writer.println("=================================");
        Writer.println();

        while (player.isAlive() && enemy.isAlive()) {
            printStatus();
            playerTurn();
            if (!enemy.isAlive()) break;
            applyDotsOnEnemy();
            if (!enemy.isAlive()) break;
            enemyTurn();
            applyEndOfTurnEffects();
        }

        if (player.isAlive()) {
            Writer.println("You defeated " + enemy.getName() + "!");
            Writer.println();
            return true;
        } else {
            Writer.println("You were defeated...");
            Writer.println();
            return false;
        }
    }

    private void printStatus() {
        Writer.println("--- Status ---");
        Writer.println("Your HP: " + player.getCurrentHp() + "/" + player.getMaxHp()
                + "  |  Flasks: " + player.getFlasks());
        Writer.println(enemy.toString());
        Writer.println();
    }

    private void playerTurn() {
        Writer.println("What will you do?");
        Writer.println("1. Attack");
        Writer.println("2. Skill  (" + player.getWeapon().getNormalAbilityName() + ")");

        Weapon w = player.getWeapon();
        int cd = w.getUltCurrentCooldown();
        if (ultUsed) {
            Writer.println("3. Ult    [already used]");
        } else if (cd > 0) {
            Writer.println("3. Ult    [ready in " + cd + " turn(s)]");
        } else {
            Writer.println("3. Ult    (" + w.getUltName() + ")");
        }

        Writer.println("4. Dodge");
        Writer.println("5. Use Flask (" + player.getFlasks() + " left)");
        Writer.println();

        String input = Reader.getResponse();
        Writer.println();
        
  
        if (enemy instanceof Boss) {
            Boss boss = (Boss) enemy;
            
       
            if (boss.isTransquiloActive()) {
                Writer.println("[Your ult is sealed!]");
            }
            
      
            if (boss.isWhoYouAreActive()) {
                Writer.println("[Your skills are sealed this turn!]");
            }
        }

        switch (input) {
            case "1": doAttack(); break;
            case "2":
                if (enemy instanceof Boss && ((Boss)enemy).isWhoYouAreActive()) {
                    Writer.println("Your skills are sealed!");
                    ((Boss)enemy).clearWhoYouAre();
                } else {
                    doSkill();
                }
                break;
            case "3":
                if (enemy instanceof Boss && ((Boss)enemy).isTransquiloActive()) {
                    Writer.println("Your ult is sealed!");
                } else {
                    doUlt();
                }
                break;
            case "4": doDodge(); break;
            case "5": doFlask(); break;
            default:
                Writer.println("Invalid action. You lose your turn.");
        }

        if (w.getUltCurrentCooldown() > 0) {
            w.setUltCurrentCooldown(w.getUltCurrentCooldown() - 1);
        }
    }

    // -----------------------------------------------------------------------
    // Basic attack
    // -----------------------------------------------------------------------

    private void doAttack() {
        int damage = getPlayerDamage(); 
        Writer.println("You attack for " + damage + " damage!");
        enemy.takeDamage(damage);
        if (sepukuActive) {
            sepukuBonus = 2;
            Writer.println("Sepuku bonus: +" + sepukuBonus + " cumulative damage.");
        }
    }

    private int getPlayerDamage() {
        int base = (int) player.getBaseDamage();
        if (pencilBoostActive) base = (int)(base * 2);
        if (berserkerActive) base = (int)(base * 2);
        if (devilsBloodActive) {
            double hpPercent = (double) player.getCurrentHp() / player.getMaxHp();
            double multiplier = 1.0 + (1.0 - hpPercent) * 2.0;
            base = (int)(base * multiplier);
        }
        if (mutualLoveActive) base = (int)(base * 1.3);
        base += sepukuBonus;
        return base;
    }

    // -----------------------------------------------------------------------
    // Skills
    // -----------------------------------------------------------------------

    private void doSkill() {
        Writer.println("You use " + player.getWeapon().getNormalAbilityName() + "!");
        Writer.println();
        String weaponName = player.getWeapon().getName();

        if (weaponName.equals("A coin"))         skillCoin();
        else if (weaponName.equals("A pencil"))   skillPencil();
        else if (weaponName.equals("A GreatSword")) skillGreatsword();
        else if (weaponName.equals("A ring"))     skillRing();
        else if (weaponName.equals("A bow"))      skillBow();
        else if (weaponName.equals("A Daggers"))  skillDaggers();
        else if (weaponName.equals("A Machete"))  skillMachete();
        else if (weaponName.equals("Hand to Hand")) skillHandToHand();
        else if (weaponName.equals("A Katana"))   skillKatana();
        else if (weaponName.equals("Magic"))      skillMagic();
        else if (weaponName.equals("A Scythe"))   skillScythe();
        else if (weaponName.equals("A Sword"))    skillSword();
        else if (weaponName.equals("A Gun"))      skillGun();
        else if (weaponName.equals("Energy"))     skillEnergy();
        else if (weaponName.equals("Green"))      skillGreenRandom();
    }

    private void skillCoin() {
        int roll = random.nextInt(9) + 1;
        Writer.println("You roll... " + roll + "!");
        if (roll <= 4) {
            Writer.println("Nothing happens.");
        } else if (roll <= 7) {
            shieldActive = true;
            Writer.println("A shield forms — 50% chance to block the next hit.");
        } else if (roll <= 8) {
            int damage = (int)(player.getBaseDamage() * 2);
            Writer.println("Critical! " + damage + " damage!");
            enemy.takeDamage(damage);
        } else {
            if (enemy instanceof Boss) {
                int damage = (int)(player.getBaseDamage() * 3);
                Writer.println("9! Boss recive hit for " + damage + " damage!");
                enemy.takeDamage(damage);
            } else {
                Writer.println("9!  what happend ?!");
                enemy.takeDamage(enemy.getCurrentHp());
            }
        }
    }

    private void skillPencil() {
        Writer.println("Choose your Shikigami:");
        Writer.println("1. Turtle - +50% Defense");
        Writer.println("2. Deer   - Heal 15% HP");
        Writer.println("3. Lynx   - Quick attack x1.5");
        String choice = Reader.getResponse();
        switch (choice) {
            case "1":
                defenseBonus += (int)(player.getMaxHp() * 0.50);
                Writer.println("Turtle! Defense up.");
                break;
            case "2":
                int heal = (int)(player.getMaxHp() * 0.15);
                player.heal(heal);
                Writer.println("Deer! Healed " + heal + " HP.");
                break;
            case "3":
                int dmg = (int)(player.getBaseDamage() * 1.5);
                enemy.takeDamage(dmg);
                Writer.println("Lynx! " + dmg + " damage.");
                break;
            default:
                Writer.println("Nothing happens.");
        }
    }

    private void skillGreatsword() {
        int damage = (int)(getPlayerDamage() * 1.75); 
        Writer.println("Spinning Slash! " + damage + " damage!");
        enemy.takeDamage(damage);
    }
    private void skillRing() {
        bloodBondActive = true;
        Writer.println("Blood Bond! part of damage you receive reflects back.");
    }

    private void skillBow() {
        enemyWeakened = true;
        Writer.println("Direct Shoot! Enemy weakened — next attack deals 35% less damage.");
    }

    private void skillDaggers() {
        enemyBleeding = true;
        bleedTurns = 1;
        Writer.println("Blood Slash! Bleeding — 2% max HP damage next turn.");
    }

    private void skillMachete() {
        int damage = (int)(player.getBaseDamage() * 1.8);
        Writer.println("Tajo Único! " + damage + " damage!");
        enemy.takeDamage(damage);
    }

    private void skillHandToHand() {
        handToHandCounter = true;
        Writer.println("Maji Kick stance! You will counter the next attack.");
    }

    private void skillKatana() {
        playerParrying = true;
        Writer.println("Parry ready! Nullify and counterattack the next hit.");
    }

    private void skillMagic() {
        int damage = (int)(player.getBaseDamage() * 1.25);
        Writer.println("Zoltraak! " + damage + " damage!");
        enemy.takeDamage(damage);
    }

    private void skillScythe() {
        double hpPercent = (double) enemy.getCurrentHp() / enemy.getMaxHp();
        if (hpPercent < 0.15) {
            int damage = (int)(player.getBaseDamage() * 3);
            Writer.println("Reaper's Cut — CRITICAL EXECUTION! " + damage + " damage!");
            enemy.takeDamage(damage);
        } else {
            int damage = getPlayerDamage();
            Writer.println("Reaper's Cut — " + damage + " damage. (Execution below 15% HP)");
            enemy.takeDamage(damage);
        }
    }

    private void skillSword() {
        int damage = getPlayerDamage();
        defenseBonus = (int)(player.getMaxHp() * 0.15);
        Writer.println("Royal Guard! " + damage + " damage + 15% defense next turn.");
        enemy.takeDamage(damage);
    }

    private void skillGun() {
        int damage = (int)(player.getBaseDamage() * 2);
        Writer.println("Precise Shot — critical! " + damage + " damage!");
        enemy.takeDamage(damage);
    }

    private void skillEnergy() {
        int damage = (int)(player.getBaseDamage() * 1.2);
        Writer.println("Energy Blast! " + damage + " damage!");
        enemy.takeDamage(damage);
    }

    private void skillGreenRandom() {
        Writer.println("??? Oh what is this...");
        int roll = random.nextInt(14);
        switch (roll) {
            case 0:  skillCoin(); break;
            case 1:  skillPencil(); break;
            case 2:  skillGreatsword(); break;
            case 3:  skillRing(); break;
            case 4:  skillBow(); break;
            case 5:  skillDaggers(); break;
            case 6:  skillMachete(); break;
            case 7:  skillHandToHand(); break;
            case 8:  skillKatana(); break;
            case 9:  skillMagic(); break;
            case 10: skillScythe(); break;
            case 11: skillSword(); break;
            case 12: skillGun(); break;
            case 13: skillEnergy(); break;
        }
    }

    // -----------------------------------------------------------------------
    // Ults
    // -----------------------------------------------------------------------

    private void doUlt() {
        Weapon w = player.getWeapon();
        if (ultUsed) { Writer.println("Already used your ult."); return; }
        if (w.getUltCurrentCooldown() > 0) { Writer.println("Not ready yet."); return; }

        Writer.println("You use " + w.getUltName() + "!");
        Writer.println();

        String weaponName = w.getName();
        if (weaponName.equals("A coin"))           ultCoin();
        else if (weaponName.equals("A pencil"))    ultPencil();
        else if (weaponName.equals("A GreatSword")) ultGreatsword();
        else if (weaponName.equals("A ring"))      ultRing();
        else if (weaponName.equals("A bow"))       ultBow();
        else if (weaponName.equals("A Daggers"))   ultDaggers();
        else if (weaponName.equals("A Machete"))   ultMachete();
        else if (weaponName.equals("Hand to Hand")) ultHandToHand();
        else if (weaponName.equals("A Katana"))    ultKatana();
        else if (weaponName.equals("Magic"))       ultMagic();
        else if (weaponName.equals("A Scythe"))    ultScythe();
        else if (weaponName.equals("A Sword"))     ultSword();
        else if (weaponName.equals("A Gun"))       ultGun();
        else if (weaponName.equals("Energy"))      ultEnergy();
        else if (weaponName.equals("Green"))       ultGreenRandom();

        ultUsed = true;
        w.setUltCurrentCooldown(w.getUltCooldown());
    }

    private void ultCoin() {
        Writer.println("Lady Luck... 50% chance.");
        if (random.nextInt(2) == 0) {
            Writer.println("Bingo, lets choose:");
            Writer.println("1. Divine Shield (100% block + 5% heal)");
            Writer.println("2. Guaranteed Critical");
            Writer.println("3. Jackpot (1 in 4: all buffs + 50% heal)");
            String choice = Reader.getResponse();
            switch (choice) {
                case "1":
                    divineShieldActive = true;
                    int heal = (int)(player.getMaxHp() * 0.05);
                    player.heal(heal);
                    Writer.println("Divine Shield active! +" + heal + " HP.");
                    break;
                case "2":
                    int dmg = (int)(player.getBaseDamage() * 3);
                    enemy.takeDamage(dmg);
                    Writer.println("Critical! " + dmg + " damage!");
                    break;
                case "3":
                    if (random.nextInt(4) == 0) {
                        divineShieldActive = true;
                        int bigHeal = (int)(player.getMaxHp() * 0.5);
                        player.heal(bigHeal);
                        int bigDmg = (int)(player.getBaseDamage() * 3);
                        enemy.takeDamage(bigDmg);
                        Writer.println("JACKPOT! +" + bigHeal + " HP and " + bigDmg + " damage!");
                    } else {
                        Writer.println("No luck this time...");
                    }
                    break;
            }
        } else {
            Writer.println("Lady Luck didn't answer....");
        }
    }

    private void ultPencil() {
        Writer.println("System.out.override(    );" );
        Writer.println("1. Freeze ");
        Writer.println("2. MaxHP  ");
        Writer.println("3. Boost  ");
        String choice = Reader.getResponse();
        switch (choice) {
            case "1":
                enemyWeakened = true;
                Writer.println("Enemy frozen for next turn!");
                break;
            case "2":
                player.heal(player.getMaxHp());
                Writer.println("HP fully restored!");
                break;
            case "3":
                pencilBoostActive = true;
                pencilBoostTurns = 2;
                Writer.println("Damage x2 for 2 turns!");
                break;
        }
    }

    private void ultGreatsword() {
        berserkerActive = true;
        berserkerTurns = 3;
        Writer.println("BERSERKER! Damage x2 for 2 turns. Losing 5% HP per turn.");
    }

    private void ultRing() {
        mutualLoveActive = true;
        mutualLoveTurns = 3;
        player.heal((int)(player.getMaxHp() * 0.15));
        Writer.println("Mutual Love! Attack x1.3 for 2 turns.");
    }

    private void ultBow() {
        if (random.nextInt(100) < 75) {
            int damage = (int)(player.getBaseDamage() * 2.5);
            Writer.println("Piercing Shot — CRITICAL! " + damage + " damage!");
            enemy.takeDamage(damage);
        } else {
            int damage = (int)(player.getBaseDamage() * 1.8);
            Writer.println("Missed critical but still hits for " + damage + "!");
            enemy.takeDamage(damage);
        }
    }

    private void ultDaggers() {
        int damage = (int)(enemy.getMaxHp() * 0.10);
        Writer.println("Critical Point! 10% of enemy  HP: " + damage + " damage!");
        enemy.takeDamage(damage);
    }

    private void ultMachete() {
        devilsBloodActive = true;
        devilsBloodTurns = 3;
        Writer.println("Devil's Blood! More damage the lower your HP for 3 turns.");
    }

    private void ultHandToHand() {
        zoneActive = true;
        zoneTurns = 3;
        Writer.println("THE ZONE! 100% evasion + One-Inch Punch (100% crit) for 2 turns.");
    }

    private void ultKatana() {
        int selfDmg = (int)(player.getMaxHp() * 0.10);
        player.takeDamage(selfDmg);
        sepukuActive = true;
        sepukuTurns = 3;
        sepukuBonus = 2;
        Writer.println("Sepuku! Lost " + selfDmg + " HP. Each hit gains +2 cumulative damage.");
    }

    private void ultMagic() {
        int damage = (int)(player.getBaseDamage() * 2);
        Writer.println("Full Cannon — Final Flash! " + damage + " damage!");
        enemy.takeDamage(damage);
    }

    private void ultScythe() {
        deathPenaltyActive = true;
        deathPenaltyTurns = 3;
        Writer.println("Death Penalty! Heal 10% of damage dealt, crit at 30% HP for 3 turns.");
    }

    private void ultSword() {
        int damage = (int)(player.getBaseDamage() * 2.5);
        Writer.println("EXCALIBUR! " + damage + " damage, ignores all armor!");
        enemy.takeDamage(damage);
    }

    private void ultGun() {
        Writer.println("I'm tired......");
        for (int i = 1; i <= 6; i++) {
            int bulletDmg = Math.max(1, (int)(enemy.getMaxHp() * 0.02));
            Writer.println("Bullet " + i + ": " + bulletDmg + " damage.");
            enemy.takeDamage(bulletDmg);
            if (!enemy.isAlive()) break;
        }
    }

    private void ultEnergy() {
        int damage = (int)(player.getBaseDamage() * 4.5);
        Writer.println("SEE YOU IN HELL !! " + damage + " damage!");
        enemy.takeDamage(damage);
        player.takeDamage(player.getCurrentHp() - 5);
        Writer.println("Am I alive?...");
    }

    private void ultGreenRandom() {
        Writer.println("??? come on do something.....");
        boolean saved = ultUsed;
        ultUsed = false;
        int roll = random.nextInt(14);
        switch (roll) {
            case 0:  ultCoin(); break;
            case 1:  ultPencil(); break;
            case 2:  ultGreatsword(); break;
            case 3:  ultRing(); break;
            case 4:  ultBow(); break;
            case 5:  ultDaggers(); break;
            case 6:  ultMachete(); break;
            case 7:  ultHandToHand(); break;
            case 8:  ultKatana(); break;
            case 9:  ultMagic(); break;
            case 10: ultScythe(); break;
            case 11: ultSword(); break;
            case 12: ultGun(); break;
            case 13: ultEnergy(); break;
        }
        ultUsed = saved;
    }

    // -----------------------------------------------------------------------
    // Dodge & Flask
    // -----------------------------------------------------------------------

    private void doDodge() {
        Writer.println("You prepare to dodge...");
        playerDodging = true;
    }

    private void doFlask() {
        if (player.useFlask()) {
            Writer.println("You drink a flask and recover " + (int)(player.getMaxHp() * 0.25) + " HP.");
        } else {
            Writer.println("No flasks left!");
        }
    }

    // -----------------------------------------------------------------------
    // Enemy turn
    // -----------------------------------------------------------------------

    private void enemyTurn() {
        
        if (playerParrying) {
            Writer.println("PARRY! Attack nullified — counterattack!");
            int counterDmg = (int)(player.getBaseDamage() * 0.5);
            enemy.takeDamage(counterDmg);
            playerParrying = false;
            return; 
        }
        if (zoneActive) {
        
                Writer.println("The Zone: You flow like water and dodge!");
                int punch = (int)(player.getBaseDamage() * 2);
                Writer.println("One-Inch Punch! " + punch + " damage!");
                enemy.takeDamage(punch);
                return; 
            }

        if (handToHandCounter) {
            Writer.println("Maji Kick counter!");
            int counterDmg = 1 + (int)(enemy.getDamage() * 0.5);
            enemy.takeDamage(counterDmg);
            handToHandCounter = false;
            return;
        }

        if (enemy instanceof Boss) {
            ((Boss)enemy).tickTranquilo();
        }

        
        if (playerDodging) {
            playerDodging = false; 
            if (random.nextInt(100) < (zoneActive ? 100 : 100)) { 
                Writer.println("You dodge the attack!");
                return; 
            }
            Writer.println("Dodge failed!");
            
        }

        
        boolean attackBlocked = checkShieldsAndReflect();

        if (!attackBlocked) {
          
            if (enemy instanceof Boss) {
            	((Boss) enemy).takeTurn(player);
            } else {
              
                applyNormalEnemyDamage();
            }
            Writer.println("You have " + player.getCurrentHp() + " HP left.");
        }
        Writer.println();
    }
    

    private boolean checkShieldsAndReflect() {
      
        if (divineShieldActive) {
            Writer.println("Divine Shield absorbs the attack!");
            divineShieldActive = false;
            return true; 
        }

       
        if (shieldActive) {
            shieldActive = false; 
            if (random.nextInt(2) == 0) {
                Writer.println("Coin shield blocks the attack!");
                return true;
            } else {
                Writer.println("Coin shield failed!");
            }
        }

       
        if (bloodBondActive) {
            int reflected = (int)(enemy.getDamage() * 0.5);
            enemy.takeDamage(reflected);
            Writer.println("Blood Bond reflects " + reflected + " damage!");
        }
        
      
        return false;
    }

    private void applyNormalEnemyDamage() {
        int dmg = enemy.getDamage();

        if (enemyWeakened) {
            dmg = (int)(dmg * 0.65);
            enemyWeakened = false;
        }

        
        if (defenseBonus > 0) {
            dmg = Math.max(0, dmg - defenseBonus);
            defenseBonus = 0;
        }

        if (zoneActive) {
            int punch = (int)(player.getBaseDamage() * 2);
            Writer.println("One-Inch Punch! " + punch + " damage!");
            enemy.takeDamage(punch);
        }

       
        player.takeDamage(dmg); 
        Writer.println(enemy.getName() + " attacks for " + dmg + " damage!");
    }

    // -----------------------------------------------------------------------
    // DoTs and end of turn effects
    // -----------------------------------------------------------------------

    private void applyDotsOnEnemy() {
        if (enemyBleeding && bleedTurns > 0) {
            int bleedDmg = (int)(enemy.getMaxHp() * 0.02);
            Writer.println(enemy.getName() + " bleeds for " + bleedDmg + " damage!");
            enemy.takeDamage(bleedDmg);
            bleedTurns--;
            if (bleedTurns <= 0) enemyBleeding = false;
        }

        if (deathPenaltyActive) {
            double hpPercent = (double) enemy.getCurrentHp() / enemy.getMaxHp();
            if (hpPercent <= 0.30) {
                int dmg = (int)(player.getBaseDamage() * 2);
                Writer.println("Death Penalty triggers! " + dmg + " damage!");
                enemy.takeDamage(dmg);
                player.heal((int)(dmg * 0.10));
            }
        }
    }

    private void applyEndOfTurnEffects() {
        if (berserkerActive) {
            int sacrifice = (int)(player.getMaxHp() * 0.05);
            player.takeDamage(sacrifice);
            Writer.println("Berserker costs " + sacrifice + " HP.");
            berserkerTurns--;
            if (berserkerTurns <= 0) { berserkerActive = false; Writer.println("Berserker ends."); }
        }
        if (zoneActive) {
            zoneTurns--;
            if (zoneTurns <= 0) { zoneActive = false; Writer.println("The Zone fades."); }
        }
        if (sepukuActive) {
            sepukuTurns--;
            if (sepukuTurns <= 0) { sepukuActive = false; sepukuBonus = 0; Writer.println("Sepuku ends."); }
        }
        if (deathPenaltyActive) {
            deathPenaltyTurns--;
            if (deathPenaltyTurns <= 0) { deathPenaltyActive = false; Writer.println("Death Penalty ends."); }
        }
        if (mutualLoveActive) {
            mutualLoveTurns--;
            if (mutualLoveTurns <= 0) { mutualLoveActive = false; Writer.println("The spirit departs."); }
        }
        if (devilsBloodActive) {
            devilsBloodTurns--;
            if (devilsBloodTurns <= 0) { devilsBloodActive = false; Writer.println("Devil's Blood fades."); }
        }
        if (pencilBoostActive) {
            pencilBoostTurns--;
            if (pencilBoostTurns <= 0) { pencilBoostActive = false; Writer.println("Boost fades."); }
        }
    }
}