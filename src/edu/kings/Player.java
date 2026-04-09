package edu.kings;

/**
 * * @author Axel E
 * @version 1.0
 */ 
public class Player { 

	private Room currentLocation;

	public Player(Room startingRoom) {
		this.currentLocation = startingRoom;
	}

	public Room getCurrentLocation() {
		return currentLocation;
		
	}

	/**
	 *Changes the player's location to a new room.
	 * @param nextRoom The new room the player moves to.
	 */
	public void setCurrentLocation(Room nextRoom) {
		this.currentLocation = nextRoom;
	}
}
