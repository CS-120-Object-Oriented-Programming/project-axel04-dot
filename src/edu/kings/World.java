package edu.kings;
import java.util.HashMap;

/**

 * @author Axel E

 */
public class World {
	
	private HashMap<String, Room> rooms;

	
	public World() {
		rooms = new HashMap<String, Room>();
		createRooms();
		createItems();
	}

	
	public Room getRoom(String name) {
		return rooms.get(name.toLowerCase());
	}

	
	private void addRoom(Room theRoom) {
		rooms.put(theRoom.getName().toLowerCase(), theRoom);
	}

	private void createItems() {
	  
	
	   

	    
	    Item statue1 = new Item("Statue", "A glowing statue. Rest here to recover.", 0, 0);
	    Item statue2 = new Item("Statue", "A glowing statue. Rest here to recover.", 0, 0);
	    Item statue3 = new Item("Statue", "A glowing statue. Rest here to recover.", 0, 0);

	
	    Room libertadBossRoom = getRoom("freedom Room");

	    Room identidadBossRoom = getRoom("identity Room");
	    Room finalBossRoom = getRoom("reflection");

	    Room libertadPath = getRoom("freedom path");
	    Room voluntadPath = getRoom("endurance path");
	    Room identidadPath = getRoom("false memories");
	    Room bossPath = getRoom("...");

	    

	   
	    getRoom("rest area").addItem(statue1);
	    getRoom("campfire").addItem(statue2);
	    getRoom("soul society").addItem(statue3);

	    
	    Container backpack = new Container("backpack", "A small leather backpack", 2, 10);
	    getRoom("hole").addItem(backpack);
	    

	
	 getRoom("heart district").addEnemy(new Enemy("Lost Soul", 5, 2));
	 getRoom("heart district").addEnemy(new Enemy("Broken Heart", 5, 2));
	 getRoom("scarlet hall").addEnemy(new Enemy("Phantom", 5, 2));

	 
	 getRoom("freedom valley").addEnemy(new Enemy("Chain Shade", 5, 2));
	 getRoom("freedom valley").addEnemy(new Enemy("Caged Spirit", 6, 2));
	 getRoom("freedom path").addEnemy(new Enemy("Shadow Guard", 6, 3));

	 
	 getRoom("willpower arena").addEnemy(new Enemy("Weak Will", 7, 3));
	 getRoom("willpower arena").addEnemy(new Enemy("Doubt Shade", 5, 2));
	 getRoom("endurance path").addEnemy(new Enemy("Exhausted Soul", 6, 2));

	 getRoom("identity realm").addEnemy(new Enemy("Lost Self", 7, 3));
	 getRoom("identity realm").addEnemy(new Enemy("False Mirror", 7, 2));
	 getRoom("false memories").addEnemy(new Enemy("Illusion", 7, 3));
	 

	 getRoom("path").addEnemy(new Enemy("Lost Soul", 5, 2));
	 getRoom("hidden path").addEnemy(new Enemy("Wanderer", 5, 2));
	 getRoom("fading garden").addEnemy(new Enemy("Dead Flower Spirit", 5, 2));
	 getRoom("lonely bridge").addEnemy(new Enemy("Bridge Shade", 5, 2));
	 getRoom("dust road").addEnemy(new Enemy("Dust Wraith", 5, 2));
	 getRoom("burning trail").addEnemy(new Enemy("Ember Spirit", 5, 2));
	 getRoom("open horizon").addEnemy(new Enemy("Wind Shade", 5, 2));
	 getRoom("wind passage").addEnemy(new Enemy("Storm Spirit", 5, 2));
	 getRoom("ruined gate").addEnemy(new Enemy("Gate Guardian", 5, 2));
	 getRoom("empty corridor").addEnemy(new Enemy("Echo", 5, 2));

	
	 getRoom("identity realm").addEnemy(new Enemy("Lost Self", 5, 2));
	 getRoom("false memories").addEnemy(new Enemy("Illusion", 6, 2));
	 getRoom("true self corridor").addEnemy(new Enemy("Reflection Shade", 5, 2));

	 
	 getRoom("practice room").addEnemy(new Enemy("Training Dummy", 20, 0));
	
	}
	
	private void createDoor(Room from, String direction, Room to) {
		Door theDoor = new Door(to);
		from.setExit(direction, theDoor);
	}
	
	

	
	private void createRooms() {
		// Creating all the rooms.
		Room Hole = new Room("Hole", "in one Hole.");
		
		Room Path1 = new Room("Path", "at West of Soul Society.");
		Room SoulSociety = new Room("Soul Society", "At The main and the only one city");
		Room PracticePath = new Room("Practice Room", "training room.");
		Room Path2 = new Room("Path", "Path of the Lost");
		Room Path21 = new Room("Path", "Path of the Lost");
		Room BossPath = new Room("...", "...");
		Room FinalBoosRoom = new Room("Reflection","The place where you face your true self.");
		Room HiddenPath = new Room("Hidden Path", "Adventures Path");
		Room Path3=new Room("Hidden Path","Adventures Path");
		Room Path4=new Room("Fading Garden", "Dead flowers cover the ground.");
		Room Stop=new Room("Rest Area", "A small peaceful place to recover.");
		Room Path41=new Room("Lonely Bridge", "A bridge surrounded by darkness.");
		Room AmorArea=new Room("Heart District", "A place shaped by emotions and memories.");
		Room FinalBossArea=new Room("....",".....");
		Room AmorPath=new Room("Scarlet Hall", "The walls pulse like a heartbeat.");
		Room AmorBossRoom=new Room("Love Room", "Liebe - Representation of Lost Love");
		Room Path5=new Room("Dust Road", "A dry road that tests your will.");
		Room Path51=new Room("Burning Trail", "The heat pushes your determination.");
		Room voluntadArea=new Room("Willpower Arena", "Only those with strong determination survive.");
		Room voluntadPath=new Room("Endurance Path", "Every step feels heavier than the last.");
		Room voluntadBossRoom=new Room("Determination Room","Kiro-Representation of Determination");
		Room Path6 = new Room("Open Horizon", "The endless sky symbolizes freedom.");
		Room Path61 = new Room("Wind Passage", "Strong winds push against you.");
		Room libertadArea=new Room("Freedom Valley", "Chains broken long ago lie on the ground.");
		Room libertadPath=new Room("Freedom path", "Path of liberty");
		Room libertadBossRoom=new Room("Freedom Room","Alan - Representation of Freedom");
		Room Path62=new Room("Ruined Gate", "The remains of an abandoned entrance.");
		Room Path63=new Room("Ruined Gate", "The remains of an abandoned entrance.");
		Room Stop2=new Room("Campfire", "A quiet place illuminated by weak flames.");
		Room Path7=new Room("Empty Corridor", "The walls feel strangely familiar.");
		Room Path71=new Room("Empty Corridor", "The walls feel strangely familiar.");
		Room Path72=new Room("Empty Corridor", "The walls feel strangely Repetitive.");
		Room Path73=new Room("Empty Corridor", "The walls feel strangely Repetitive.");
		Room Path74=new Room("Empty Corridor", "The walls feel strangely Repetitive.");
		Room Path75=new Room("Empty Corridor", "The walls feel strangely Repetitive.");
		Room Stop3=new Room("Silent Chamber", "An unnaturally calm room.");
		Room Path8=new Room("Distorted Hall", "Everything here changes shape constantly.");
		Room Path81=new Room("Distorted Hall", "Everything here changes shape constantly.");
		Room identidadArea=new Room("Identity Realm", "A world formed from shattered personalities.");
		Room identidadPath=new Room("False Memories", "Memories mix with illusions.");
		Room identidadPath1=new Room("True Self Corridor", "The final path toward the truth.");
		Room identidadBossRoom=new Room("Identity Room","Identity-representation");
	
		
		this.addRoom(Path1);//
		this.addRoom(Path2);//
		this.addRoom(Path21);//
		this.addRoom(Path3);//
		this.addRoom(Path4);
		this.addRoom(Path41);
		this.addRoom(Path5);
		this.addRoom(Path51);
		this.addRoom(Path6);
		this.addRoom(Path61);
		this.addRoom(Path62);
		this.addRoom(Path63);
		this.addRoom(Path7);
		this.addRoom(Path71);
		this.addRoom(Path72);
		this.addRoom(Path73);
		this.addRoom(Path74);
		this.addRoom(Path75);
		this.addRoom(Path8);
		this.addRoom(Path81);
		this.addRoom(HiddenPath);
		
		this.addRoom(AmorPath);
		this.addRoom(voluntadPath);
		this.addRoom(identidadPath);
		this.addRoom(identidadPath1);
		this.addRoom(libertadPath);
		this.addRoom(BossPath);
		
		this.addRoom(AmorArea);
		this.addRoom(FinalBossArea);
		this.addRoom(libertadArea);
		this.addRoom(voluntadArea);
		this.addRoom(identidadArea);
	
		
		this.addRoom(Hole);
		this.addRoom(SoulSociety);
		this.addRoom(PracticePath);
		
		this.addRoom(identidadBossRoom);
		this.addRoom(libertadBossRoom);
		this.addRoom(FinalBoosRoom);
		this.addRoom(AmorBossRoom);
		this.addRoom(voluntadBossRoom);
	
		this.addRoom(Stop);
		this.addRoom(Stop2);
		this.addRoom(Stop3);
		
	///////////////////////////////////// MAP Desing //////////////////////////////////////////////
	
		this.createDoor(Hole, "east", Path1);
		this.createDoor(Path1, "west", Hole);

		this.createDoor(Path1, "east", SoulSociety);
		this.createDoor( SoulSociety, "west", Path1);

		this.createDoor(SoulSociety, "east", PracticePath);
		this.createDoor(PracticePath, "west", SoulSociety);

		this.createDoor(PracticePath, "south", Path2);
		this.createDoor(Path2, "north", PracticePath);
		
		this.createDoor(Path2, "south", Path21);
		this.createDoor(Path21, "north", Path2);

		this.createDoor(Path21, "south", BossPath);
		this.createDoor(BossPath, "north", Path21);

		this.createDoor(BossPath, "west", FinalBoosRoom);
		this.createDoor(FinalBoosRoom, "east", BossPath);

		this.createDoor(Hole, "west", HiddenPath);
		this.createDoor(HiddenPath, "east", Hole);
		
		this.createDoor(HiddenPath, "west", Path3);
		this.createDoor(Path3, "east", HiddenPath);
		
		this.createDoor(Path3,"west",Path4);
		this.createDoor(Path4, "east",Path3);
		
		this.createDoor(Path4, "south",Path41);
		this.createDoor(Path41, "north",Path4);
		
		this.createDoor(Path41, "west",Stop);
		this.createDoor(Stop, "east",Path41);
		
		this.createDoor(Path41,"south",Path5);
		this.createDoor(Path5,"north",Path41);
		
		this.createDoor(Path5,"east", Path51);
		this.createDoor(Path51, "west", Path5);
		
		this.createDoor(Path51,"south",AmorArea);
		this.createDoor(AmorArea, "north", Path51);
		
		this.createDoor(AmorArea, "west",AmorPath);
		this.createDoor(AmorPath,"east", AmorArea);
		
		this.createDoor(AmorPath, "west", AmorBossRoom);
		this.createDoor(AmorBossRoom , "east",AmorPath);
		
		this.createDoor(Hole,"north",Path6);
		this.createDoor(Path6,"south", Hole);
		
		this.createDoor(Path61,"west",voluntadArea);
		this.createDoor(voluntadArea,"east",Path61);
		
		this.createDoor(voluntadArea,"west",voluntadPath);
		this.createDoor(voluntadPath,"east", voluntadArea);
		
		this.createDoor(voluntadPath,"north",voluntadBossRoom);
		this.createDoor(voluntadBossRoom, "south", voluntadPath);
		
		this.createDoor(Path6,"north", Path61);
		this.createDoor(Path61, "south",Path6);
		
		this.createDoor(Path61, "east",Path62);
		this.createDoor(Path62,"west",Path61);
		
		this.createDoor(Path62,"east",Path63);
		this.createDoor(Path63, "west", Path62);
		
		this.createDoor(Path62,"north",libertadArea);
		this.createDoor(libertadArea, "south", Path62);
		
		this.createDoor(libertadArea,"north", libertadPath);
		this.createDoor(libertadPath,"south",libertadArea);
		
		this.createDoor(libertadPath, "east",libertadBossRoom);
		this.createDoor(libertadBossRoom,"west",libertadPath);
		
		this.createDoor(Path63,"east",Stop2);
		this.createDoor(Stop2,"west",Path63);
		
		this.createDoor(Stop2,"south",Path7);
		this.createDoor(Path7,"north",Stop2);
		
		this.createDoor(Path7,"south",Path71);
		this.createDoor(Path71,"north",Path7);
		
		this.createDoor(Path71,"south",Path72);
		this.createDoor(Path72,"north",Path71);
		
		this.createDoor(Path72,"west",Path73);
		this.createDoor(Path73,"east",Path72);
		
		this.createDoor(Path73,"west",Path74);
		this.createDoor(Path74,"east",Path73);
		
		this.createDoor(Path74,"west",Path75);
		this.createDoor(Path75,"east",Path74);
		
		this.createDoor(Path75,"south",identidadArea);
		this.createDoor(identidadArea,"north",Path75);
		
		this.createDoor(identidadArea,"east",identidadPath);
		this.createDoor(identidadPath,"west",identidadArea);
		
		this.createDoor(identidadPath,"east",identidadBossRoom);
		this.createDoor(identidadBossRoom,"west",identidadPath);
		
		
		
	}
}