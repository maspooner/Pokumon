import java.util.ArrayList;


public class Pokumon {
	enum type{ WATER, LIGHTNING, FIRE}
	
	static boolean[] caught=new boolean[5];
	static Pokumon[] playerPoke=new Pokumon[]{null, null,null};
	static ArrayList<Pokumon> stored=new ArrayList<Pokumon>();
	
	String name;
	int currentHP;
	int maxHP;
	int level;
	int xp;
	Move[] attacks;
	type pokeType;
	
	String pictureName;
	
	boolean fainted=false;
	
	
	public Pokumon(int ID){
		switch(ID){
		case 1:
			name="Clepixie";
			maxHP=100;
			pokeType=type.FIRE;
			attacks=new Move[]{new Move(1), new Move(6), new Move(4), new Move(5)};
			pictureName="clefairy";
			break;
		case 2:
			name="Aquachu";
			maxHP=90;
			pokeType=type.WATER;
			attacks=new Move[]{new Move(1), new Move(2), new Move(10), new Move(9)};
			pictureName="pikachu";
			break;
		case 3:
			name="Phanpy";
			maxHP=80;
			pokeType=type.WATER;
			attacks=new Move[]{new Move(1), new Move(2), new Move(8), new Move(9)};
			pictureName="phanpy";
			break;
		case 4:
			name="Blazeon";
			maxHP=90;
			pokeType=type.FIRE;
			attacks=new Move[]{new Move(1), new Move(7), new Move(5), new Move(4)};
			pictureName="flareon";
			break;
		case 5:
			name="Plusle";
			maxHP=70;
			pokeType=type.LIGHTNING;
			attacks=new Move[]{new Move(1), new Move(11), new Move(12), new Move(3)};
			pictureName="plusle";
			break;
		}
		currentHP=maxHP;
		level=1;
		xp=1;
	}
	
	public boolean calculateLevel(){
		int oldLevel=level;
		if(xp/20!=0)
			level = xp/20;
		return oldLevel==level ? false : true;
	}
	
	public void calculateXP(Pokumon oppo){
		xp = 10*oppo.level +5;
	}
}
