package edu.kings;

/**
 * This class is the main class of the "Campus of Kings" application.
 * ... (
 */
public class Game {
	/** The world where the game takes place. */
	private World world;
	/** The player character in the game. */
	private Player player; 
	/** The room the player character was previously in. */
	private Room previousRoom;
	
	private int score;
	private int turns;
	
	
	/**
	 * Create the game and initialize its internal map.
	 */
	public Game() {
		world = new World();
		
		player = new Player(world.getRoom("outside"));
		previousRoom = null;
		score = 0;
		turns = 0;
	}

	/**
	 * Main play routine. Loops until end of play.
	 */
	public void play() {
		printWelcome();

		boolean wantToQuit = false;
		while (!wantToQuit) {
			Command command = Reader.getCommand();
			wantToQuit = processCommand(command);
		}
		printGoodbye();
	}

	/**
	 * Given a command, process (that is: execute) the command.
	 *
	 * @param command The command to be processed.
	 * @return true If the command ends the game, false otherwise.
	 */
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
            case inventory: 
                showInventory();
                break;
            case unpack:
                unpack(command.getSecondWord());
                break;
            case pack:
                pack(command.getSecondWord());
                break;
            case unlock:
                unlock(command.getSecondWord());
                break;
            case lock:
                lock(command.getSecondWord());
                break;
            case quit:
                wantToQuit = quit(command);
                break;
           
        }
        return wantToQuit;
        
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
	/**
	 * Implementation of the take command.
	 */
	private void takeItem(Command command) {
		if (!command.hasSecondWord()) {
			Writer.println("Take what?");
			return;
		}
		String itemName = command.getSecondWord();
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

	/**
	 * Implementation of the drop command.
	 */
	private void dropItem(Command command) {
		if (!command.hasSecondWord()) {
			Writer.println("Drop what?");
			return;
		}
		String itemName = command.getSecondWord();
		Item item = player.removeItem(itemName);

		if (item == null) {
			Writer.println("You are not carrying that.");
		} else {
			player.getCurrentLocation().addItem(item);
			Writer.println("You dropped the " + itemName + ".");
		}
	}

	/**
	 * Implementation of the examine command.
	 */
	private void examineItem(Command command) {
		if (!command.hasSecondWord()) {
			Writer.println("Examine what?");
			return;
		}
		String itemName = command.getSecondWord();
		Item item = player.getCurrentLocation().getItem(itemName);
		if (item == null) {
			item = player.getItem(itemName);
		}

		if (item == null) {
			Writer.println("You don't see that here.");
		} else {
			Writer.println(item.toString());
		}
	}

	/**
	 * Implementation of the inventory command.
	 */
	private void showInventory() {
		Writer.println(player.getInventoryString());
	}

	///////////////////////////////////////////////////////////////////////////

	private void printLocationInformation() {
		Writer.println(player.getCurrentLocation().toString());
	}

	private void goRoom(Command command) {
		if (!command.hasSecondWord()) {
			Writer.println("Go where?");
		} else {
			String direction = command.getRestOfLine();
			Door doorway = player.getCurrentLocation().getExit(direction);

			if (doorway == null) {
				Writer.println("There is no door!");
			} else {
				previousRoom = player.getCurrentLocation();
				player.setCurrentLocation(doorway.getDestination());
				turns++;
				printLocationInformation();
			}
			
			if (doorway.isLocked()) {
			    Writer.println("The door is locked.");
			    return;
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

	    if (door.getKey().equals(key)) {
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

	


	/**
	 * Take the player to the previous room.
	 */
	private void back() {
		if (previousRoom == null) {
			Writer.println("You have nowhere to go back to!");
		} else {
			Room temp = currentRoom;
			currentRoom = previousRoom;
			previousRoom = temp;
			turns++;
			Writer.println("You went back.");
			printLocationInformation();
		}
	}

	/**
	 * Print out some help information.
	 */
	private void printHelp() {
		Writer.println("You are lost. You are alone. You wander");
		Writer.println("around at the university.");
		Writer.println();
		Writer.println("Your command words are:");
		Writer.println(" look  go quit help back status");
		Writer.println();
		printLocationInformation();
	}

	/**
	 * Print out the opening message for the player.
	 */
	private void printWelcome() {
		Writer.println();
		Writer.println("Welcome to the Campus of Kings!");
		Writer.println("Campus of Kings is a new, incredibly boring adventure game.");
		Writer.println("Type 'help' if you need help.");
		Writer.println();
		printLocationInformation();
	}

	/**
	 * Print out the closing message for the player.
	 */
	private void printGoodbye() {
		Writer.println("I hope you weren't too bored here on the Campus of Kings!");
		Writer.println("Thank you for playing. Good bye.");
	}

	/**
	 * "Quit" was entered.
	 *
	 * @param command
	 * The command to be processed.
	 * @return true, if this command quits the game, false otherwise.
	 */
	private boolean quit(Command command) {
		boolean wantToQuit = true;
		if (command.hasSecondWord()) {
			Writer.println("Quit what?");
			wantToQuit = false;
		}
		return wantToQuit;
	}
}
