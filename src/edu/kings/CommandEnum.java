package edu.kings;

public enum CommandEnum {  
	go("go"),
	look("look"),
	status("status"),
	back("back"),
	help("help"),
	quit("quit"),
	take("take"),
	drop("drop"),
	examine("examine"),
	inventory("inventory"),
	unlock("unlock"), 
    lock("lock"), 
    pack("pack"), 
    unpack("unpack"),
	talk("talk");

    private String commandString;

    CommandEnum(String commandString) {
        this.commandString = commandString;
    }

    @Override
    public String toString() {
        return commandString;
    }
}
	
	
