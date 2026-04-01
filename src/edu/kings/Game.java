package edu.kings;
/**
 * This class is the main class of the "Campus of Kings" application.
 * "Campus of Kings" is a very simple, text based adventure game. Users can walk
 * around some scenery. That's all. It should really be extended to make it more
 * interesting!
 *
 * This game class creates and initializes all the others: it creates all rooms,
 * creates the parser and starts the game. It also evaluates and executes the
 * commands that the parser returns.
 *
 * @author Maria Jump
 * @version 2015.02.01
 *
 * Used with permission from Dr. Maria Jump at Northeastern University
 */

public class Game {
	/** The world where the game takes place. */
	private World world;
	/** The room the player character is currently in. */
	private Room currentRoom;
	
	private Room previousRoom;
	
	private int score;
	
	private int turns;
	
	/**
	 * Create the game and initialize its internal map.
	 */
	public Game() {
		world = new World();
		// set the starting room
		currentRoom = world.getRoom("outside");
		
		previousRoom = null;
		
		score=0;
		turns=0;
		
	}

	/**
	 * Main play routine. Loops until end of play.
	 */
	public void play() {
		printWelcome();

		// Enter the main game loop. Here we repeatedly read commands and
		// execute them until the game is over.
		boolean wantToQuit = false;
		while (!wantToQuit) {
			Command command = Reader.getCommand();
			wantToQuit = processCommand(command);
			// other stuff that needs to happen every turn can be added here.
		}
		printGoodbye();
	}

	///////////////////////////////////////////////////////////////////////////
	// Helper methods for processing the commands

	/**
	 * 
	 * Given a command, process (that is: execute) the command.
	 *
	 * @param command
	 *            The command to be processed.
	 * @return true If the command ends the game, false otherwise.
	 */
	
    private void printLocationInformation() {
        System.out.println("You are in : " + currentRoom.getDescription());
        
        System.out.println("exit: ");
    }

   
    private void look() {
        printLocationInformation(); 
        }


    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        if (command.isUnknown()) {
            Writer.println("I don't know what you mean...");
            return wantToQuit;
        }

 
        CommandEnum commandWord = command.getCommandEnum();
        return wantToQuit;
    }

	///////////////////////////////////////////////////////////////////////////
	// Helper methods for implementing all of the commands.
	// It helps if you organize these in alphabetical order.

	/**
	 * Try to go to one direction. If there is an exit, enter the new room,
	 * otherwise print an error message.
	 *
	 * @param command
	 *            The command to be processed.
	 */
	private void goRoom(Command command) {
		if (!command.hasSecondWord()) {
			// if there is no second word, we don't know where to go...
			Writer.println("Go where?");
		} else {
			String direction = command.getRestOfLine();

			// Try to leave current.
			Door doorway = null;
			if (direction.equals("north")) {
				doorway = currentRoom.northExit;
			}
			if (direction.equals("east")) {
				doorway = currentRoom.eastExit;
			}
			if (direction.equals("south")) {
				doorway = currentRoom.southExit;
			}
			if (direction.equals("west")) {
				doorway = currentRoom.westExit;
			}

			if (doorway == null) {
				Writer.println("There is no door!");
			} else {
					Room newRoom = doorway.getDestination();
				
				
				previousRoom = currentRoom; 
				currentRoom = newRoom;     
				turns++;
				
				currentRoom = newRoom;
				Writer.println(newRoom.getName() + ":");
				Writer.println("You are " + newRoom.getDescription());
				Writer.print("Exits: ");
				
				if (newRoom.northExit != null) {
					Writer.print("north ");
				}
				if (newRoom.eastExit != null) {
					Writer.print("east ");
				}
				if (newRoom.southExit != null) {
					Writer.print("south ");
				}
				if (newRoom.westExit != null) {
					Writer.print("west ");
				}
				Writer.println();
			}
		}
	}


	private void status() {
	    Writer.println("--- Status ---");
	    Writer.println("Score: " + score);
	    Writer.println("Turns: " + turns);
	   
	    printLocationInformation(); 
	}

	
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
	 * Print out the closing message for the player.
	 */
	private void printGoodbye() {
		Writer.println("I hope you weren't too bored here on the Campus of Kings!");
		Writer.println("Thank you for playing.  Good bye.");
	}

	/**
	 * Print out some help information. Here we print some stupid, cryptic
	 * message and a list of the command words.
	 */
	private void printHelp() {
		Writer.println("You are lost. You are alone. You wander");
		Writer.println("around at the university.");
		Writer.println();
		Writer.println("Your command words are:");
		Writer.println(" look  go quit help");
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
		Writer.println(currentRoom.getName() + ":");
		Writer.println("You are " + currentRoom.getDescription());
		Writer.print("Exits: ");
		
	
		if (currentRoom.northExit != null) {
			Writer.print("north ");
		}
		if (currentRoom.eastExit != null) {
			Writer.print("east ");
		}
		if (currentRoom.southExit != null) {
			Writer.print("south ");
		}
		if (currentRoom.westExit != null) {
			Writer.print("west ");
		}
		Writer.println("");
		
	}

	/**
	 * "Quit" was entered. Check the rest of the command to see whether we
	 * really quit the game.
	 *
	 * @param command
	 *            The command to be processed.
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
