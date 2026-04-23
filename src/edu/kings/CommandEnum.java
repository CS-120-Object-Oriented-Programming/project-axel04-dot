package edu.kings;

public enum CommandEnum {  
	go (),
	look (),
	status (),
	back (),
	help (),
	quit (),
	take (),
	drop(),
	examine(),
	inventory(),
	unlock("unlock"), 
    lock("lock"), 
    pack("pack"), 
    unpack("unpack");

    private String commandString;

    CommandEnum(String commandString) {
        this.commandString = commandString;
    }

    @Override
    public String toString() {
        return commandString;
    }
}
	
	
