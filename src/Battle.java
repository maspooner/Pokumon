
public class Battle {
	
	private final int NORMAL=1;
	private final int DOUBLE=2;
	
	static Pokumon currentPlayerPoke;
	private static Pokumon currentOppoPoke;
	
	private boolean playerTurn=false;
	private static boolean oppoCaught;
	
	private boolean playerLost;
	private boolean oppoLost;
	public static volatile boolean usedItem;
	public volatile static boolean changedPoke;
	public static volatile boolean madeMove=false;
	static volatile boolean running=false;
	public static int buttonState;
	
	private static final int SLEEP_TIME=1000;
	
	public Battle(){
		currentPlayerPoke=Pokumon.playerPoke[0];
		currentOppoPoke=getRandomPokumon();
		playerLost=false;
		oppoLost=false;
		oppoCaught=false;
		changedPoke=false;
		running=false;
		start();
	}
	
	private Pokumon getRandomPokumon(){
		int i=Main.getRandomNumber(5);
		return new Pokumon(i+1);
	}
	
	public void start(){
		Main.menusEnabled=false;
		AudioPlayer.playm(2);
		
		GUIBattleSetup();
		GUI.oppoHealth.setValue(currentOppoPoke.maxHP);
		
		while(true){
			if(calculateFirst()){
				while(true){
					playerMove();
					if(checkBattleStatus())
						break;
					oppMove();
					if(checkBattleStatus())
						break;
				}
			}
			else{
				while(true){
					oppMove();
					if(checkBattleStatus())
						break;
					playerMove();
					if(checkBattleStatus())
						break;
				}
			}
			if(oppoCaught || running)
				break;
			if(playerLost || oppoLost)
				break;
		}
		if(!oppoCaught && !running){
			if(playerLost){
				GUI.console.setText("You lost!");
				sleep(2000);
				System.exit(0);
			}
			else{
				AudioPlayer.playm(3);
				GUI.oppoIcon.setIcon(GUI.getImageIcon("blank"));
				GUI.console.setText("You won!");
				sleep(3000);
				GUI.console.setText("You got $20");
				Main.money+=20;
				currentPlayerPoke.calculateXP(currentOppoPoke);
				if(currentPlayerPoke.calculateLevel()){
					AudioPlayer.plays(6);
					GUI.console.setText(currentPlayerPoke.name+" is now level "+Pokumon.playerPoke[0].level+"!");
				}
			}
		}
		GUI.playerIcon.setIcon(GUI.getImageIcon(Main.getPlayerPicture()));
		GUI.oppoIcon.setIcon(GUI.getImageIcon("blank"));
		GUI.playerHealth.setValue(0);
		GUI.oppoHealth.setValue(0);
		GUI.oppoName.setText("");
		GUI.oppoPoke.setText("");
		GUI.playerPoke.setText("");
		Main.menusEnabled=true;
	}
	
	private static void GUIBattleSetup() {
		GUI.playerHealth.setMaximum(currentPlayerPoke.maxHP);
		GUI.playerHealth.setValue(currentPlayerPoke.currentHP);
		GUI.oppoHealth.setMaximum(currentOppoPoke.maxHP);
		GUI.oppoHealth.setValue(currentOppoPoke.currentHP);
		GUI.playerIcon.setIcon(GUI.getImageIcon(currentPlayerPoke.pictureName));
		GUI.oppoIcon.setIcon(GUI.getImageIcon(currentOppoPoke.pictureName));
		GUI.playerName.setText("Trainer "+Main.playerName);
		GUI.playerPoke.setText(currentPlayerPoke.name);
		GUI.oppoName.setText("Wild");
		GUI.oppoPoke.setText(currentOppoPoke.name);
		setBattleButtons(3);
	}

	public static void sleep(int time){
		try {
			Thread.sleep(time);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void setBattleButtons(int buttonstate){
		//1 = all options
		//2 = fight options
		//3 = no press
		if(buttonstate==1){
			GUI.upperLeft.setText("Fight");
			GUI.upperRight.setText("Bag");
			GUI.lowerLeft.setText("Pokumon");
			GUI.lowerRight.setText("Run");
		}
		else if(buttonstate==2){
			String name, total; 
			int pp, ppMax;
			for(int i=0; i<4; i++){
				name = currentPlayerPoke.attacks[i].name;
				pp = currentPlayerPoke.attacks[i].currentPP;
				ppMax = currentPlayerPoke.attacks[i].maxPP;
				total ="<html>"+name+":<br>("+pp+"/"+ppMax+") PP";
				if(i==0)
					GUI.upperLeft.setText(total);
				if(i==1)
					GUI.upperRight.setText(total);
				if(i==2)
					GUI.lowerLeft.setText(total);
				if(i==3)
					GUI.lowerRight.setText(total);
			}
		}
		else if(buttonstate==3){
			GUI.upperLeft.setText("-");
			GUI.upperRight.setText("-");
			GUI.lowerLeft.setText("-");
			GUI.lowerRight.setText("-");
		}
		else if(buttonstate==4){
			GUI.upperLeft.setText("Next");
			GUI.upperRight.setText("-");
			GUI.lowerLeft.setText("-");
			GUI.lowerRight.setText("-");
		}
		buttonState=buttonstate;
	}
	
	public static void switchPokumon() {
		int i;
		while(true){
			try{
				i=(int) GUI.showPokeDialog()-1;
			}
			catch(Exception e){
				continue;
			}
			if(Pokumon.playerPoke[i].fainted)
				continue;
			else{
				GUI.console.setText("Go "+Pokumon.playerPoke[i].name+"!");
				currentPlayerPoke=Pokumon.playerPoke[i];
				GUIBattleSetup();
				break;
			}
		}
	}

	private boolean checkBattleStatus() {
		boolean allFeinted=true;
		if(currentOppoPoke.fainted){
			oppoLost=true;
			return true;
		}
		if(oppoCaught){
			return true;
		}
		if(running)
			return true;
		for(int i=0; i<3;i++){
			if(Pokumon.playerPoke[i]!=null){
				if(!Pokumon.playerPoke[i].fainted){
					allFeinted=false;
					break;
				}
			}
		}
		if(allFeinted){
			playerLost=true;
			return true;
		}
		if(currentPlayerPoke.fainted){
			switchPokumon();
		}
		if(currentPlayerPoke.fainted)
			return true;
		
		return false;
	}

	private boolean calculateFirst() {
		int num= Main.getRandomNumber(2);
		if(num==0)
			return true;
		else
			return false;
	}

	private void playerMove(){
		playerTurn=true;
		setBattleButtons(1);
		GUI.console.setText("What will "+currentPlayerPoke.name+" do?");
		while(!madeMove){
		}
		madeMove=false;
		if(changedPoke){
			switchPokumon();
			changedPoke=false;
			playerTurn=false;
			return;
		}
		if(usedItem){
			useItem();
			usedItem=false;
			playerTurn=false;
			return;
		}
		if(running){
			return;
		}
		setBattleButtons(3);
		Move selectedMove=getMoveNumber();
		int damage=selectedMove.damage;
		
		GUI.console.setText(currentPlayerPoke.name+" uses "+selectedMove.name);
		selectedMove.currentPP-=1;
		sleep(SLEEP_TIME);
		AudioPlayer.plays(1);
		damage*=getTypeAdvantage(selectedMove);
		oppoDamaged(damage);
		playerTurn=false;
	}
	
	private void oppMove(){
		int moveNum=Main.getRandomNumber(4);
		int damage=0;
		GUI.console.setText(currentOppoPoke.name+" uses "+currentOppoPoke.attacks[moveNum].name);
		sleep(SLEEP_TIME);
		AudioPlayer.plays(1);
		damage=currentOppoPoke.attacks[moveNum].damage;
		damage*=getTypeAdvantage(currentOppoPoke.attacks[moveNum]);
		playerDamaged(damage);
	}
	
	private Move getMoveNumber(){
		int n=GUI.moveNumber;
		for(int i=0;i<4;i++){
			if(n==i+1)
				return currentPlayerPoke.attacks[i];
		}
		return null;
	}
	
	private void oppoDamaged(int amount){
		currentOppoPoke.currentHP-=amount;
		if(currentOppoPoke.currentHP<=0){
			currentOppoPoke.currentHP=0;
			currentOppoPoke.fainted=true;
			GUI.console.setText(currentOppoPoke.name+" has fainted");
		}
		GUI.oppoHealth.setValue(currentOppoPoke.currentHP);
	}
	
	private void playerDamaged(int amount){
		currentPlayerPoke.currentHP-=amount;
		if(currentPlayerPoke.currentHP<=0){
			currentPlayerPoke.currentHP=0;
			currentPlayerPoke.fainted=true;
			GUI.console.setText(currentPlayerPoke.name+" has fainted");
		}
		GUI.playerHealth.setValue(currentPlayerPoke.currentHP);
	}
	
	private Pokumon getOppo(){
		if(playerTurn)
			return currentOppoPoke;
		else
			return currentPlayerPoke;
	}
	
	private double getTypeAdvantage(Move m){
		Pokumon p=getOppo();
		
		if(m.moveType==Move.type.NORMAL)
			return NORMAL;
		if(p.pokeType==Pokumon.type.FIRE){
			if(m.moveType==Move.type.FIRE)
				return NORMAL;
			if(m.moveType==Move.type.LIGHTNING)
				return NORMAL;
			if(m.moveType==Move.type.WATER){
				GUI.console.setText("It's super effective!");
				sleep(SLEEP_TIME);
				return DOUBLE;
			}		
		}
		if(p.pokeType==Pokumon.type.LIGHTNING){
			if(m.moveType==Move.type.FIRE){
				GUI.console.setText("It's super effective!");
				sleep(SLEEP_TIME);
				return DOUBLE;
			}
			if(m.moveType==Move.type.LIGHTNING)
				return NORMAL;
			if(m.moveType==Move.type.WATER)
				return NORMAL;
		}
		if(p.pokeType==Pokumon.type.WATER){
			if(m.moveType==Move.type.FIRE)
				return NORMAL;
			if(m.moveType==Move.type.LIGHTNING){
				GUI.console.setText("It's super effective!");
				sleep(SLEEP_TIME);
				return DOUBLE;
			}
			if(m.moveType==Move.type.WATER)
				return NORMAL;
		}
		return 0;
	}
	
	
	public static void useItem(){
		int i;
		try{
			i =(int) GUI.showItemDialog()-1;
		}
		catch(Exception e){
			return;
		}
		if(Main.holding[0].numberOwned!=0){
			if(i==0){
				Main.holding[0].numberOwned-=1;
				Battle.catchPoke();
			}
		}
		if(Main.holding[0].numberOwned!=0){
			if(i==1){
				Main.holding[1].numberOwned-=1;
				Battle.heal();
			}
		}
	}
	


	public static void catchPoke() {
		GUI.oppoIcon.setIcon(GUI.getImageIcon("ball"));
		setBattleButtons(3);
		if(getCatchRate()){
			sleep(2000);
			AudioPlayer.playm(12);
			GUI.console.setText("You caught a "+currentOppoPoke.name+"!");
			sleep(2500);
			setBattleButtons(4);
			while(!Main.buttonPressed){
			}
			 Main.buttonPressed=false;
			for(int i=0;i<3;i++){
				if(Pokumon.playerPoke[i]==null){
					Pokumon.playerPoke[i]=currentOppoPoke;
					oppoCaught=true;
					return;
				}
			}
			//if no space
			GUI.console.setText("The caught pokumon was stored.");
			oppoCaught=true;
			Pokumon.stored.add(currentOppoPoke);
			while(!Main.buttonPressed){
			}
			Main.buttonPressed=false;
		}
		else{
			GUI.oppoIcon.setIcon(GUI.getImageIcon(currentOppoPoke.pictureName));
			GUI.console.setText("Oh no! It escaped.");
			sleep(1000);
		}
	}

	private static boolean getCatchRate(){
		int i=Main.getRandomNumber(2);
		if(i==0)
			return true;
		else
			return false;
	}

	public static void heal() {
		Main.holding[1].numberOwned--;
		GUI.console.setText(Main.playerName+" used a potion!");
		AudioPlayer.plays(7);
		currentPlayerPoke.currentHP+=Item.HEAL;
		if(currentPlayerPoke.currentHP>currentPlayerPoke.maxHP)
			currentPlayerPoke.currentHP=currentPlayerPoke.maxHP;
		GUI.playerHealth.setValue(currentPlayerPoke.currentHP);
		usedItem=true;
		madeMove=true;
	}
}
