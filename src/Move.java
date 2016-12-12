
public class Move {
	
	enum type{ WATER, LIGHTNING, FIRE, NORMAL}
	
	String name;
	int damage;
	int maxPP;
	int currentPP;
	type moveType;
	
	
	public Move(int ID){
		switch(ID){
		case 1:
			name="Tackle";
			moveType=type.NORMAL;
			damage=10;
			maxPP=35;
			break;
		case 2:
			name="Bubble Beam";
			moveType=type.WATER;
			damage=20;
			maxPP=10;
			break;
		case 3:
			name="Thunder";
			moveType=type.LIGHTNING;
			damage=20;
			maxPP=10;
			break;
		case 4:
			name="Flame Thrower";
			moveType=type.FIRE;
			damage=20;
			maxPP=10;
			break;
		case 5:
			name="Fire Punch";
			moveType=type.FIRE;
			damage=15;
			maxPP=15;
			break;
		case 6:
			name="Ember";
			moveType=type.FIRE;
			damage=10;
			maxPP=25;
			break;
		case 7:
			name="Overheat";
			moveType=type.FIRE;
			damage=30;
			maxPP=5;
			break;
		case 8:
			name="Hydro Cannon";
			moveType=type.WATER;
			damage=20;
			maxPP=15;
			break;
		case 9:
			name="Water Fall";
			moveType=type.WATER;
			damage=10;
			maxPP=25;
			break;
		case 10:
			name="Surf";
			moveType=type.WATER;
			damage=15;
			maxPP=15;
			break;
		case 11:
			name="Spark";
			moveType=type.LIGHTNING;
			damage=5;
			maxPP=25;
			break;
		case 12:
			name="Thunder Punch";
			moveType=type.LIGHTNING;
			damage=25;
			maxPP=5;
			break;
		}
		currentPP=maxPP;
	}
	
}
