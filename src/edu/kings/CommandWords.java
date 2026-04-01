package edu.kings;
/**
 * This class is part of the "Campus of Kings" application. "Campus of Kings" is a
 * very simple, text based adventure game.
 *
 * This class holds an enumeration of all command words known to the game. It is
 * used to recognize commands as they are typed in.
 *
 * @author Maria Jump
 * @version 2015.02.01
 *
 * Used with permission from Dr. Maria Jump at Northeastern University
 */

public class CommandWords { 
	/** A constant array that holds all valid command words. */
	private static CommandEnum[] validCommands;

	/**
	 * Static block to initialize the fields of CommandWords.
	 */
	static {  validCommands = CommandEnum.values();
	}
	/**
	 * Converts a String into a CommandEnum object.
	 *
	 * @param theString The String containing the command word.
	 * @return The CommandEnum object representing the command, or null if
	the command does
	 * not exist.
	*/
	public static CommandEnum getCommand(String theString) {
        CommandEnum foundCommand = null;
        for (CommandEnum command : validCommands) {
        
            if (command.toString().equals(theString)) {
                foundCommand = command;
            }
        }
        return foundCommand;
    }
	/**
	 * Check whether a given String is a valid command word.
	 *
	 * @param aString The string to determine whether it is a valid command.
	 * @return true if a given string is a valid command, false if it isn't.
	 */
	public static boolean isCommand(String aString) {
		return getCommand(aString) != null;
		
	}
}
