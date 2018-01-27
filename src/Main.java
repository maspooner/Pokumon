import java.util.Random;


public class Main {
	protected static final boolean IS_TEST = false;
	
	public static String playerName="Matt";
	public static boolean male=false;
	public static int money=100;
	public static Item[] holding=new Item[]{new Item(true),new Item(false)};
	
	public static final String timeLine="Monday, May 13 to Saturday, May 19 2013";
	public static final String hoursWorked="20 hours";
	
	private static final String malePic="male";
	private static final String femalePic="female";
	public static volatile boolean titleClosed=false;
	public static volatile boolean buttonPressed=false;
	
	public static volatile int placeChosen=1;
	private static boolean firstTime=true;
	public static boolean ablePokeMenu=false;
	public static volatile boolean choseGrass=false;
	public static volatile boolean chosePotion=false;
	public static volatile boolean choseBack=false;
	public static boolean menusEnabled=false;
	
	
	
	 public static void main(String[] args) {
		 new GUI();
		 AudioPlayer.playm(1);
		 while(!titleClosed){
		 }
		 opening();
		 AudioPlayer.playm(6);
		 while(true){
			 menusEnabled=true;
			 GUI.console.setText("***LittleLeaf Town***");
			 GUI.playerMoney.setText("$"+money);
			 setStoryButtons(2);
			 while(!buttonPressed){
			 }
			 buttonPressed=false;
			 if(placeChosen==1)
				 adventure();
			 if(placeChosen==2)
				 mart();
			 if(placeChosen==3)
				 center();
			 if(placeChosen==4)
				 lab();
		 }
	 }
	 
	public static String getPlayerPicture(){
		return male? malePic : femalePic;
	}
	
	private static void lab() {
		AudioPlayer.playm(7);
		GUI.oppoIcon.setIcon(GUI.getImageIcon("oak"));
		setStoryButtons(1);
		if(firstTime){
			GUI.console.setText("PROF. PINE:\tOh, hello there. Welcome to the Pokumon Lab!");
			while(!buttonPressed){
			}
			buttonPressed=false;
			GUI.console.setText("PROF. PINE:\tI almost forgot. Today's your birthday.\nLet me give you a present.");
			Battle.sleep(1000);
			AudioPlayer.plays(2);
			while(!buttonPressed){
			}
			buttonPressed=false;
			Pokumon.playerPoke[0]=new Pokumon(2);
			GUI.console.setText("You got an "+Pokumon.playerPoke[0].name+"!");
			GUI.playerIcon.setIcon(GUI.getImageIcon(Pokumon.playerPoke[0].pictureName));
			AudioPlayer.plays(5);
			while(!buttonPressed){
			}
			buttonPressed=false;
			GUI.console.setText("PROF. PINE:\tTake good care of it.\nThis will allow you to battle.");
			while(!buttonPressed){
			}
			buttonPressed=false;
			GUI.playerIcon.setIcon(GUI.getImageIcon(getPlayerPicture()));
			GUI.console.setText("PROF. PINE:\tI have a favor to ask of you.\nMy goal is to collect all the pokumon.\nI am too old. Can you collect all 5 pokumon?");
			while(!buttonPressed){
			}
			buttonPressed=false;
			GUI.console.setText("PROF. PINE:\tYou will need to buy pokuballs in the shop.\nCome back here when you have collected all 5 pokumon.");
			while(!buttonPressed){
			}
			buttonPressed=false;
			firstTime=false;
			ablePokeMenu=true;
		}
		else{
			boolean allCaught=true;
			
			GUI.console.setText("PROF. PINE:\tWelcome to the Pokumon Lab!\nLet's check how many pokumon you have caught...");
			while(!buttonPressed){
			}
			buttonPressed=false;
			for(int i=0;i<3;i++){
				if(Pokumon.playerPoke[i]!=null){
					if(Pokumon.playerPoke[i].name.equals("Clepixie"))
						Pokumon.caught[0]=true;
					if(Pokumon.playerPoke[i].name.equals("Aquachu"))
						Pokumon.caught[1]=true;
					if(Pokumon.playerPoke[i].name.equals("Phanpy"))
						Pokumon.caught[2]=true;
					if(Pokumon.playerPoke[i].name.equals("Blazeon"))
						Pokumon.caught[3]=true;
					if(Pokumon.playerPoke[i].name.equals("Plusle"))
						Pokumon.caught[4]=true;
				}
			}
			for(int i=0;i<Pokumon.stored.size();i++){
				if(Pokumon.stored.get(i).name.equals("Clepixie"))
					Pokumon.caught[0]=true;
				if(Pokumon.stored.get(i).name.equals("Aquachu"))
					Pokumon.caught[1]=true;
				if(Pokumon.stored.get(i).name.equals("Phanpy"))
					Pokumon.caught[2]=true;
				if(Pokumon.stored.get(i).name.equals("Blazeon"))
					Pokumon.caught[3]=true;
				if(Pokumon.stored.get(i).name.equals("Plusle"))
					Pokumon.caught[4]=true;
			}
			for(int i=0;i<Pokumon.caught.length;i++){
				if(!Pokumon.caught[i])
					allCaught=false;
			}
			if(allCaught){
				setStoryButtons(3);
				GUI.console.setText("PROF. PINE: You've collected them all!\nCongradulations!");
				AudioPlayer.playm(13);
				Battle.sleep(5000);
				GUI.console.setText("PROF. PINE: That's the end.\nHave a great Birthday!\nLove, Matt");
				AudioPlayer.playm(14);
				Battle.sleep(100000000);
				//TODO
			}
			else{
				GUI.console.setText("PROF. PINE:\tYou haven't caught all the pokumon yet.\nKeep trying!");
				while(!buttonPressed){
				}
				buttonPressed=false;
			}
		}
		AudioPlayer.playm(6);
		GUI.oppoIcon.setIcon(GUI.getImageIcon("blank"));
	}

	private static void center() {
		AudioPlayer.playm(8);
		setStoryButtons(1);
		
		GUI.console.setText("Welcome to the Pokumon Center.\nWe restore your pokumon to full health.");
		while(!buttonPressed){
		}
		buttonPressed=false;
		AudioPlayer.playm(9);
		GUI.console.setText("Healing...");
		setStoryButtons(3);
		Battle.sleep(2000);
		for(int i=0;i<3;i++){
			if(Pokumon.playerPoke[i]!=null){
				Pokumon.playerPoke[i].fainted=false;
				Pokumon.playerPoke[i].currentHP=Pokumon.playerPoke[i].maxHP;
				for(int j=0;j<4;j++){
					Pokumon.playerPoke[i].attacks[j].currentPP=Pokumon.playerPoke[i].attacks[j].maxPP;
				}
			}
		}
		AudioPlayer.playm(6);
	}

	private static void mart() {
		AudioPlayer.playm(10);
		choseBack=false;
		while(true){
			GUI.console.setText("Welcome to the Poku Mart!\nWhat would you like?");
			setStoryButtons(5);
			while(!buttonPressed){
			}
			buttonPressed=false;
			if(choseBack)
				break;
			if(chosePotion && money-20>0){
				money-=20;
				holding[1].numberOwned+=1;
			}
			else if(!chosePotion && money-15>0){
				money-=15;
				holding[0].numberOwned+=1;
			}
			GUI.playerMoney.setText("$"+money);
		}
		AudioPlayer.playm(6);
	}

	private static void adventure() {
		boolean allNull=true;
		for(int i=0;i<3;i++){
			if(Pokumon.playerPoke[i]!=null)
				allNull=false;
		}
		if(allNull){
			GUI.console.setText("You don't have any pokumon!");
			AudioPlayer.plays(3);
			Battle.sleep(1000);
			return;
		}
		while(true){
			GUI.console.setText("***Route 101***");
			setStoryButtons(4);
			AudioPlayer.playm(11);
			while(!buttonPressed){
			}
			buttonPressed=false;
			if(choseGrass){
				choseGrass=false;
				new Battle();
			}
			else
				break;
		}
		AudioPlayer.playm(6);
	}

	public static int getRandomNumber(int largest){
		 Random r=new Random();
		 return r.nextInt(largest);
	 }

	 private static void setStoryButtons(int buttonState){
		 //1 = one option
		 //2 town options
		 //3 disabled
		 //4 adventure
		 //5 shop
		 if(buttonState==1){
			 GUI.upperLeft.setText("Next");
			 GUI.upperRight.setText("-");
			 GUI.lowerLeft.setText("-");
			 GUI.lowerRight.setText("-");
		 }
		 else if(buttonState==2){
			 GUI.upperLeft.setText("Adventure");
			 GUI.upperRight.setText("Mart");
			 GUI.lowerLeft.setText("Pokumon Center");
			 GUI.lowerRight.setText("Lab");
		 }
		 else if(buttonState==3){
			 GUI.upperLeft.setText("-");
			 GUI.upperRight.setText("-");
			 GUI.lowerLeft.setText("-");
			 GUI.lowerRight.setText("-");
		 }
		 else if(buttonState==4){
			 GUI.upperLeft.setText("Search grass");
			 GUI.upperRight.setText("Back to town");
			 GUI.lowerLeft.setText("-");
			 GUI.lowerRight.setText("-");
		 }
		 else if(buttonState==5){
			 GUI.upperLeft.setText("Potion");
			 GUI.upperRight.setText("Pokuball");
			 GUI.lowerLeft.setText("<html>Potion = $20<br>Pokuball = $25");
			 GUI.lowerRight.setText("Back");
		 }
	 }
	 
	 private static void opening(){
		 AudioPlayer.playm(5);
		 GUI.oppoIcon.setIcon(GUI.getImageIcon("oak"));
		 setStoryButtons(1);
		 GUI.console.setText("PROF. PINE:\tHello, my name is Professor Pine");
		 while(!buttonPressed){
		 }
		 buttonPressed=false;
		 GUI.oppoIcon.setIcon(GUI.getImageIcon("tree"));
		 GUI.console.setText("PROF. PINE:\tAnd no, I am not a tree. \nAll pokumon professors must have a tree as their last name.");
		 while(!buttonPressed){
		 }
		 buttonPressed=false;
		 GUI.oppoIcon.setIcon(GUI.getImageIcon("oak"));
		 GUI.console.setText("PROF. PINE:\tAnyway, Welcome to the world of pokumon!");
		 while(!buttonPressed){
		 }
		 buttonPressed=false;
		 GUI.playerIcon.setIcon(GUI.getImageIcon("clefairy"));
		 GUI.console.setText("PROF. PINE:\tThis is a pokumon. We use these creatures to battle.");
		 while(!buttonPressed){
		 }
		 buttonPressed=false;
		 GUI.playerIcon.setIcon(GUI.getImageIcon("blank"));
		 GUI.showNameDialog();
		 GUI.playerName.setText("Trainer "+playerName);
		 GUI.showSexDialog();
		 GUI.playerIcon.setIcon(GUI.getImageIcon(getPlayerPicture()));
		 GUI.console.setText("PROF. PINE:\t"+"Ah, so you're "+playerName+" moving into my town of LittleLeaf.");
		 while(!buttonPressed){
		 }
		 buttonPressed=false;
		 GUI.console.setText("PROF. PINE:\t"+"Come visit me in my lab later.");
		 while(!buttonPressed){
		 }
		 buttonPressed=false;
		 GUI.oppoIcon.setIcon(GUI.getImageIcon("blank"));
	 }
	 
}
