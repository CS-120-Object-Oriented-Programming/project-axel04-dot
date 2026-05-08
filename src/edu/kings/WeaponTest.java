package edu.kings;

import java.util.ArrayList;

/**
 * Handles the weapon selection test.
 * Uses text input/output like the rest of the game.
 */

public class WeaponTest {

    // --- Weapons ---
    private Weapon coin    = new Weapon("A coin",   "Anything can happen. Trust your luck.",              "🎲");
    private Weapon pencil     = new Weapon("A pencil",    "draw something, idk",                         "✏️");
    private Weapon greatsword   = new Weapon("A GreatSword",  "AAAAAAAAHHHHH !!!!",                    "🗡️");
    private Weapon ring    = new Weapon("A ring",   "you not are alone",                            "💍");
    private Weapon bow      = new Weapon("A bow",     "you are a Quincy ?",                      "🏹");
    private Weapon daggers     = new Weapon("A Daggers",   "you are fast ",                      "🔪");
    private Weapon machete   = new Weapon("A Machete",  "Not even the devil could.",                "🌿");
    private Weapon handtohand = new Weapon("Hand to Hand", "Hand to Hand Without weapon",               "👊");
    private Weapon katana    = new Weapon("A Katana",   "With honor until the end",         "⚔️");
    private Weapon magic     = new Weapon("Magic",    "Down to every last drop",               "🪄");
    private Weapon scythe   = new Weapon("A Scythe",  "The end of all",                  "⚖️");
    private Weapon sword    = new Weapon("A Sword",   "The clasic Hero",                           "🗡️");
    private Weapon gun   = new Weapon("A Gun",  "Pragmatics and Fatigue",                     "🔫");
    private Weapon energy   = new Weapon("Energy",     "Down to the very last sparkle",                           "🔋");
    private Weapon green     = new Weapon("Green",       "Green ?",                     "🍃");
    
 
    
    // --- Questions: {+10, +5 } ---
    private String[][] questions = {
   	{ "Do you rely more on luck than a plan?",                          "coin", "green", "gun" },
   	{ "Do you prefer to solve problems creatively?",                    "pencil", "ring", "magic" },
    { "Are you willing to sacrifice yourself for total victory?", 		"greatsword", "machete", "energy" },
    { "Are you stronger when you have someone by your side?",           "ring",   "pencil", "handtohand" },
    { "Do you prefer to attack from afar and control the situation?",	"bow", "gun", "daggers" },
    { "Do you act quickly?",                                            "daggers", "bow", "katana" },
    { "When you're in trouble, do you become more dangerous?",          "machete", "greatsword", "energy" },
    { "Do you trust yourself more than any tool?",                      "handtohand", "katana", "ring" },
    { "Do you read situations well before acting?",                     "katana", "hantohand", "sword" },
    { "Do you consider yourself a person of spiritual or mental power?","magic", "energy", "pencil" },
    { "Do you accept the end of things without fear?",                  "scythe", "sword", "daggers" },
    { "Do you see yourself as someone who protects others?",            "sword", "katana", "scythe" },
    { "Do you do what works without complicating things?",              "gun",   "bow", "coin" },
    { "Do you give your all even if it leaves you with nothing?",       "energy", "magic", "machete" },
    { "Do you prefer the unexpected over the predictable?",             "green", "coin", "pencil" },
    		};

    /**
     * Runs the weapon test and returns the chosen weapon.
     * @return The weapon the player ends up with.
     */
    public Weapon run() {
    	  
    	coin.setNormalAbility("flip", "Roll a 100-sided die.");
    	coin.setUlt("Lady luck", "Guaranteed x2 Critical, Divine Shield or JACKPOT .", 1);

    	pencil.setNormalAbility("draw", "Draw a Shikigami: Turtle, Deer, or Lynx.");
    	pencil.setUlt("System.out.override", "Write a Java command: `System.out.override` (Freeze enemy or MaxHP or Damage Boost);", 1);

    	greatsword.setNormalAbility("Lion Claw", " Spinning Slash: +50% Power; Unblockable.");
    	greatsword.setUlt("Berserker", "Sacrifice 5% of your HP per turn; damage x2 for 2 turns.", 3);

    	ring.setNormalAbility("Blood Bond", "part of damage received is reflected back to the enemy.");
    	ring.setUlt("Mutual and True Love"," Summon a Spirit; Life and Attack x1.30.for the next 2 turns.", 3);

    	bow.setNormalAbility("Direct Shoot", "Applies 35% Weakness to the enemy.");
    	bow.setUlt("Pircing shoot", "75% chance of a critical hit; high damage if it misses.", 1);

    	daggers.setNormalAbility("Blood slash", "2% Bleeding per turn for 1 turn.");
    	daggers.setUlt("critical point", "Removes 10% of total HP, ignoring defense.", 1);

    	machete.setNormalAbility("Tajo Único", "High-power, direct, dry damage.");
    	machete.setUlt("Devil's Blood", "Deals more damage the less health you have for the next 3 turns.", 3);

    	handtohand.setNormalAbility("Maji Kick", "Counter: I strike back, and the damage received is reduced.");
    	handtohand.setUlt("The zone", "80% auto Evasion, and upon entering Maji mode, switches to a One-Inch Punch with a 100% critical hit rate for the next 2 turns.", 3);

    	katana.setNormalAbility("Parry", "If you are attacked, you nullify the damage and counterattack.");
    	katana.setUlt("Sepuku", "You lose 10% of your health; each hit adds +2 cumulative damage for the next 2 turns.", 2);

    	magic.setNormalAbility("Zoltraak", "Energy Beam: +25% Base Damage");
    	magic.setUlt("Full Cannon", "Final Flash, dmage x2.", 1);

    	scythe.setNormalAbility("Reaper's Cut"," If the enemy has less than 15% health, critical..");
    	scythe.setUlt("Death Penalty", "You heal 10% of the damage dealt; critical at 30% for the next 3 turns.", 3);

    	sword.setNormalAbility("Royal Guard", "Attack that grants +15% Defense on the next turn");
    	sword.setUlt("Excalibur","—a sword slash that deals x2.5 damage and ignores all armor.", 1);

    	gun.setNormalAbility("Precise Shot"," Always a critical hit if the enemy is not under cover.");
    	gun.setUlt("Im tired", "6 bullets; each deals 2% damage.", 1);

    	energy.setNormalAbility("energy blast", "a shoot with energy");
    	energy.setUlt("last chance", "An explosion that deals x4.5 damage but leaves you at 5 HP.", 1);

    	green.setNormalAbility("???", "I dont Know");
    	green.setUlt("???", "gree is just a color ", 1);
    	   
    	   
        Writer.println("=================================");
        Writer.println("  Soul Partner.");
        Writer.println("  answer with yes or no.");
        Writer.println("=================================");
        Writer.println();

        for (int i = 0; i < questions.length; i++) {
            Writer.println("Question " + (i + 1) + " about " + questions.length + ":");
            Writer.println(questions[i][0]);

            String answer = "";
            while (!answer.equals("yes") && !answer.equals("no")) {
                answer = Reader.getResponse();
                if (!answer.equals("yes") && !answer.equals("no")) {
                    Writer.println("answer yes or not ._. .");
                }
            }

            if (answer.equals("yes")) {
                weaponByKey(questions[i][1]).addScore(20);
                weaponByKey(questions[i][2]).addScore(10);
                weaponByKey(questions[i][3]).addScore(5);
            }

            Writer.println();
        }

        return determineWinner();
    }

    /**
     * Finds the weapon with the highest score.
     * If there's a tie, lets the player choose.
     */
    private Weapon determineWinner() {
        int maxScore = 0;
        for (Weapon w : allWeapons()) {
            if (w.getScore() > maxScore) {
                maxScore = w.getScore();
            }
        }

        ArrayList<Weapon> winners = new ArrayList<>();
        for (Weapon w : allWeapons()) {
            if (w.getScore() == maxScore) {
                winners.add(w);
            }
        }

        if (winners.size() == 1) {
            return winners.get(0);
        }

        // Tie — let the player choose
        Writer.println("¡Mmm here is a tie, well, let you pick, which you wanna ?");
        for (int i = 0; i < winners.size(); i++) {
            Writer.println((i + 1) + ". " + winners.get(i).getIcon() + " " + winners.get(i).getName());
        }

        int choice = -1;
        while (choice < 1 || choice > winners.size()) {
            String input = Reader.getResponse();
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                Writer.println("write the number of your election ");
            }
        }

        return winners.get(choice - 1);
    }

    private Weapon[] allWeapons() {
        return new Weapon[]{ coin, pencil, greatsword, ring, bow, daggers,
                             machete, handtohand, katana, magic, scythe,
                             sword, gun, energy, green };
    }

    private Weapon weaponByKey(String key) {
        switch (key) {
            case "coin":       return coin;
            case "pencil":     return pencil;
            case "greatsword": return greatsword;
            case "ring":       return ring;
            case "bow":        return bow;
            case "daggers":    return daggers;
            case "machete":    return machete;
            case "handtohand": return handtohand;
            case "katana":     return katana;
            case "magic":      return magic;
            case "scythe":     return scythe;
            case "sword":      return sword;
            case "gun":        return gun;
            case "energy":     return energy;
            case "green":      return green;
            default:           return green;
        }
    }
}