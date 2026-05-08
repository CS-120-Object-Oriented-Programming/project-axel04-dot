package edu.kings;


public class Game {
	
	private World world;
	private Player player; 
	private Room previousRoom;
	
	private boolean liebeDefeated;
	private boolean alanDefeated;
	private boolean kiroDefeated;
	private boolean identityDefeated;
	
	private int score;
	private int turns;
	
	
	
	public Game() {
		world = new World();
		
		player = new Player(world.getRoom("Hole"));
		previousRoom = null;
		score = 0;
		turns = 0;
		this.liebeDefeated = false;
		this.alanDefeated = false;
		this.kiroDefeated = false;
		this.identityDefeated = false;
		
		
		WeaponTest test = new WeaponTest();
	    Weapon weapon = test.run();
	    player.setWeapon(weapon);
	    Writer.println("your soul partner is  " + weapon.getIcon() + " " + weapon.getName());
	    Writer.println(weapon.getDescription());
	    Writer.println();
	    
	  
	}
	
	

	
	public void play() {
		printWelcome();

		boolean wantToQuit = false;
		while (!wantToQuit) {
			Command command = Reader.getCommand();
			wantToQuit = processCommand(command);
		}
		printGoodbye();
	}

	

	private boolean processCommand(Command command) {
		boolean wantToQuit = false;

		if (command.isUnknown()) {
			Writer.println("I don't know what you mean...");
			return wantToQuit;
		
		}
		
		

		CommandEnum commandWord = command.getCommandEnum();
		switch (commandWord) {
            case help:
                printHelp();
                break;
            case go:
                goRoom(command);
                break;
            case look:
                look();
                break;
            case status:
                status();
                break;
            case back:
                back();
                break;
            case take: 
                takeItem(command);
                break;
            case drop: 
                dropItem(command);
                break;
            case examine: 
                examineItem(command);
                break;
            case talk:
            	talk();
            	break;
            case inventory: 
                showInventory();
                break;
            case unpack:
                if (command.hasSecondWord()) {
                    unpack(command.getWord(0));
                } else {
                    unpack(null);
                }
                break;
            case pack:
            	 if (command.hasSecondWord()) {
                     pack(command.getWord(0));
                 } else {
                     pack(null);
                 }
            	 break;
            case unlock:
            	 if (command.hasSecondWord()) {
                     unlock(command.getWord(0));
                 } else {
                     unlock(null);
                 }
            	 break;
            case lock:
            	 if (command.hasSecondWord()) {
                     lock(command.getWord(0));
                 } else {
                     lock(null);
                 }
            	 break;
            case quit:
                wantToQuit = quit(command);
                break;
           
        }
        return wantToQuit;
        
        }
	
	private void talk() {
	    Room currentRoom = player.getCurrentLocation();
	    if (currentRoom.getName().equals("Soul Society")) {
	        Writer.println("Andy: Hey, you made it out of the Hole.");
	        Writer.println("Andy: This place is called Soul Society. It's the last safe city.");
	        Writer.println("Andy: Out there are 4 bosses");
	        Writer.println("Andy: Each one represents something you've lost.");
	        Writer.println("Andy: Defeat them all and face what's waiting at the end.");
	        Writer.println("Andy: The statues will restore you. Use them wisely.");
	        Writer.println("Andy: Good luck. You'll need it.");
	    } else {
	        Writer.println("There's no one to talk to here.");
	    }
	}
        private void unpack(String itemName) {
            if (itemName == null) {
                Writer.println("Unpack what?");
                return;
            }

            Writer.println("From what?");
            String containerName = Reader.getResponse();

            Room currentRoom = player.getCurrentLocation();
            Item item = currentRoom.getItem(containerName);

            if (item instanceof Container) {
                Container container = (Container) item;

                Item removed = container.removeItem(itemName);

                if (removed != null) {
                    player.addItem(removed);
                    Writer.println("You unpacked the " + itemName + ".");
                } else {
                    Writer.println("That item is not in the container.");
                }
            } else {
                Writer.println("That is not a container.");
            }
        }
        
        private void pack(String itemName) {
            if (itemName == null) {
                Writer.println("Pack what?");
                return;
            }

            Writer.println("In what?");
            String containerName = Reader.getResponse();

            Room currentRoom = player.getCurrentLocation();
            Item item = currentRoom.getItem(containerName);

            if (item instanceof Container) {
                Container container = (Container) item;

                Item playerItem = player.removeItem(itemName);

                if (playerItem != null) {
                    container.addItem(playerItem);
                    Writer.println("You packed the " + itemName + ".");
                } else {
                    Writer.println("You don't have that item.");
                }

            } else {
                Writer.println("That is not a container.");
            }
        }
	
	private void takeItem(Command command) {
		if (!command.hasSecondWord()) {
			Writer.println("Take what?");
			return;
		}
		String itemName = command.getWord(0);
		Room currentRoom = player.getCurrentLocation();
		Item item = currentRoom.getItem(itemName);

		if (item == null) {
			Writer.println("That item is not here.");
		} else {
			if (player.addItem(item)) {
				currentRoom.removeItem(itemName);
				Writer.println("You took the " + itemName + ".");
			} else {
				Writer.println("The " + itemName + " is too heavy to carry.");
			}
		}
	}

	
	private void dropItem(Command command) {
		if (!command.hasSecondWord()) {
			Writer.println("Drop what?");
			return;
		}
		String itemName = command.getWord(0);
		Item item = player.removeItem(itemName);

		if (item == null) {
			Writer.println("You are not carrying that.");
		} else {
			player.getCurrentLocation().addItem(item);
			Writer.println("You dropped the " + itemName + ".");
		}
	}

	
	private void examineItem(Command command) {
	    if (!command.hasSecondWord()) {
	        Writer.println("Examine what?");
	        return;
	    }
	    String itemName = command.getWord(0);
	    Item item = player.getCurrentLocation().getItem(itemName);
	    if (item == null) {
	        item = player.getItem(itemName);
	    }

	    if (item == null) {
	        Writer.println("You don't see that here.");
	        return;
	    }

	    if (item.getName().equalsIgnoreCase("Statue")) {
	        player.heal(player.getMaxHp());
	        player.refillFlasks();
	        Writer.println("The statue glows... HP restored and flasks refilled.");
	        if (liebeDefeated || alanDefeated || kiroDefeated || identityDefeated) {
	            Writer.println("The statue also offers a power upgrade.");
	            Writer.println("Do you want to increase your damage? (yes/no)");
	            String answer = Reader.getResponse();
	            if (answer.equals("yes")) {
	                player.setBaseDamage(player.getBaseDamage() + 0.5);
	                Writer.println("Your damage increased to " + player.getBaseDamage() + "!");
	            }
	        }
	        return;
	    }

	    Writer.println(item.toString());
	}
	
	private void showInventory() {
		Writer.println(player.getInventoryString());
	}

	///////////////////////////////////////////////////////////////////////////

	private void printLocationInformation() {
		Writer.println(player.getCurrentLocation().toString());
	}
	
	private void printGameOver() {
	    Writer.println("=================================");
	    Writer.println("  YOU DIED.");
	    Writer.println("=================================");
	    return;
	}

	private void goRoom(Command command) {
	    if (!command.hasSecondWord()) {
	        Writer.println("Go where?");
	        return;
	    }

	    String direction = command.getRestOfLine();
	    Door doorway = player.getCurrentLocation().getExit(direction);

	    if (doorway == null) {
	        Writer.println("There is no door!");
	        return;
	    }

	    if (doorway.isLocked()) {
	        Writer.println("The door is locked.");
	        return;
	    }

	    previousRoom = player.getCurrentLocation();
	    player.setCurrentLocation(doorway.getDestination());
	    turns++;
	    printLocationInformation();

	    Room currentRoom = player.getCurrentLocation();

	  
	    if (currentRoom.hasEnemies()) {
	        for (Enemy enemy : currentRoom.getEnemies()) {
	            if (enemy.isAlive()) {
	                Writer.println("An enemy appears!");
	                Combat combat = new Combat(player, enemy);
	                boolean won = combat.start();
	                if (!won) {
	                    printGameOver();
	                    return;
	                } else {
	                    player.gainMaxHp(1);
	                }
	            }
	        }
	    }

	   
	    String roomName = currentRoom.getName();
	    if (roomName.equals("Love Room")) {
	        Boss liebe = new Boss("Liebe", 20, 3);
	        Combat combat = new Combat(player, liebe);
	        boolean won = combat.start();
	        if (!won) { printGameOver(); return; }
	        else {
	            Item liebeKey = new Item("Liebe's Key", "A key stained with tears.", 1, 0);
	            player.addItem(liebeKey);
	            Writer.println("You obtained Liebe's Key!");
	            player.gainMaxHp(3);
	        }
	    } else if (roomName.equals("Freedom Room")) {
	        Boss alan = new Boss("Alan", 25, 4);
	        Combat combat = new Combat(player, alan);
	        boolean won = combat.start();
	        if (!won) { printGameOver(); return; }
	        else {
	            Item alanKey = new Item("Alan's Key", "A key that feels weightless.", 1, 0);
	            player.addItem(alanKey);
	            Writer.println("You obtained Alan's Key!");
	            player.gainMaxHp(3);
	        }
	    } else if (roomName.equals("Determination Room")) {
	        Boss kiro = new Boss("Kiro", 30, 4);
	        Combat combat = new Combat(player, kiro);
	        boolean won = combat.start();
	        if (!won) { printGameOver(); return; }
	        else {
	            Item kiroKey = new Item("Kiro's Key", "A key burning with willpower.", 1, 0);
	            player.addItem(kiroKey);
	            Writer.println("You obtained Kiro's Key!");
	            player.gainMaxHp(3);
	        }
	    } else if (roomName.equals("Identity Room")) {
	        Boss identity = new Boss("Identity", 38, 5);
	        Combat combat = new Combat(player, identity);
	        boolean won = combat.start();
	        if (!won) { printGameOver(); return; }
	        else {
	            Item identityKey = new Item("Identity's Key", "A key that reflects yourself.", 1, 0);
	            player.addItem(identityKey);
	            Writer.println("You obtained Identity's Key!");
	            player.gainMaxHp(3);
	        }
	    } else if (roomName.equals("Reflection")) {
	        Boss you = new Boss("you", (int)(player.getMaxHp() * 1.5),
	                            (int)(player.getBaseDamage() * 1.3), player.getWeapon());
	        Combat combat = new Combat(player, you);
	        boolean won = combat.start();
	        if (!won) { printGameOver(); return; }
	        else {
	            Writer.println("=================================");
	            Writer.println("  YOU WON...");
	            Writer.println("At the end, you have love, you have Freedom, you have Determination,you have Identity,at the end you are just you");
	            Writer.println("=================================");
	        }
	    }
	}

	
	private void unlock(String direction) {
	    if (direction == null) {
	        Writer.println("Unlock what?");
	        return;
	    }

	    Door door = player.getCurrentLocation().getExit(direction);

	    if (door == null) {
	        Writer.println("There is no door.");
	        return;
	    }

	    Writer.println("With what?");
	    String keyName = Reader.getResponse();

	    Item key = player.getItem(keyName);

	    if (key == null) {
	        Writer.println("You don't have that key.");
	        return;
	    }

	    if (door.getKey().getName().equalsIgnoreCase(key.getName())) {
	        door.setLocked(false);
	        Writer.println("The door is now unlocked.");
	    } else {
	        Writer.println("That key doesn't work.");
	    }
	}
	
	private void lock(String direction) {
	    if (direction == null) {
	        Writer.println("Lock what?");
	        return;
	    }

	    Door door = player.getCurrentLocation().getExit(direction);

	    if (door == null) {
	        Writer.println("There is no door.");
	        return;
	    }

	    Writer.println("With what?");
	    String keyName = Reader.getResponse();

	    Item key = player.getItem(keyName);

	    if (key == null) {
	        Writer.println("You don't have that key.");
	        return;
	    }

	    if (door.getKey().equals(key)) {
	        door.setLocked(true);
	        Writer.println("The door is now locked.");
	    } else {
	        Writer.println("That key doesn't work.");
	    }
	}
	private void look() {
		printLocationInformation();
	}

	private void status() {
		Writer.println("--- Status ---");
		Writer.println("Score: " + score);
		Writer.println("Turns: " + turns);
		Writer.println();
		printLocationInformation();
	}

	


	
	private void back() {
	    if (previousRoom == null) {
	        Writer.println("You have nowhere to go back to!");
	    } else {
	        Room temp = player.getCurrentLocation();
	        player.setCurrentLocation(previousRoom);
	        previousRoom = temp;
	        turns++;
	        Writer.println("You went back.");
	        printLocationInformation();
	    }
	}

	
	private void printHelp() {
		Writer.println("You are lost. You are not alone.");
		Writer.println("around at the Souls World.");
		Writer.println();
		Writer.println("Your command words are:");
		Writer.println(" go, quit, help, back, status, talk,Examine");
		Writer.println();
		printLocationInformation();
	}


	private void printWelcome() {
		Writer.println();
		Writer.println("Welcome to Souls");
		Writer.println("Souls is a new, incredibly boring adventure game.");
		Writer.println("Type 'help' if you need help.");
		Writer.println();
		printLocationInformation();
	}

	
	private void printGoodbye() {
		Writer.println("I hope you weren't too bored here on the Soul game");
		Writer.println("Thank you for playing. Good bye.");
	}

	
	private boolean quit(Command command) {
		boolean wantToQuit = true;
		if (command.hasSecondWord()) {
			Writer.println("Quit what?");
			wantToQuit = false;
		}
		return wantToQuit;
	}
}
