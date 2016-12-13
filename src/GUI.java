import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;


public class GUI implements ActionListener {
	
	public static JFrame frame=new JFrame("Pokumon: Aqua Blue Version");
	private static JDialog poke=new JDialog(frame,"Pokumon", true);
	private static JDialog itemDialog=new JDialog(frame,"Items", true);
	private static JDialog switchDialog=new JDialog(frame,"Items", true);
	
	public static JTextArea console=new JTextArea();
	public static JLabel playerIcon=new JLabel();
	public static JLabel oppoIcon=new JLabel();
	public static JProgressBar playerHealth=new JProgressBar();
	public static JProgressBar oppoHealth=new JProgressBar();
	
	public static JButton upperLeft=new JButton("-");
	public static JButton upperRight=new JButton("-");
	public static JButton lowerLeft=new JButton("-");
	public static JButton lowerRight=new JButton("-");
	
	public static JLabel playerName=new JLabel("");
	public static JLabel oppoName=new JLabel("");
	public static JLabel playerPoke=new JLabel("");
	public static JLabel oppoPoke=new JLabel("");
	public static JLabel playerMoney=new JLabel("");
	public static JLabel oppoLeft=new JLabel("");
	
	public static int moveNumber;
	private static final Dimension FRAME_SIZE=new Dimension(1020,600);
	
	public GUI(){
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				startup();				
			}
		});
	}
	
	private void startup(){
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setSize(FRAME_SIZE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setIconImage(loadImage("pikachu"));
		
		setupJMenuBar();
		setupFrameContents();
	}

	private void openPokeFrame(){
		poke.setSize(new Dimension(900,900));
		poke.setResizable(false);
		poke.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		poke.setIconImage(loadImage("pikachu"));
		loadPoke();
	}
	private void openItemFrame() {
		itemDialog.getContentPane().removeAll();
		itemDialog.setSize(new Dimension(300,200));
		itemDialog.setResizable(false);
		itemDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		itemDialog.setIconImage(loadImage("pikachu"));

		JTextArea text=new JTextArea();
		text.setFont(new Font("sans serif", Font.BOLD, 22));
		text.setText("Potions: "+Main.holding[1].numberOwned+"\nPokuballs: "+Main.holding[0].numberOwned);
		text.setEditable(false);
		itemDialog.getContentPane().add(text);
		itemDialog.setVisible(true);
	}
	
	public void loadPoke(){
		poke.getContentPane().removeAll();
		Pokumon current;
		JLabel icon, name;
		JPanel sections;
		JProgressBar health;
		Container icons=new Container();
		icons.setLayout(new BoxLayout(icons, BoxLayout.Y_AXIS));
		
		Container statuses=new Container();
		statuses.setLayout(new BoxLayout(statuses, BoxLayout.Y_AXIS));
		for(int i=0;i<3;i++){
			if(Pokumon.playerPoke[i]!=null){
				current=Pokumon.playerPoke[i];
				
				icon=new JLabel();
				icon.setIcon(getImageIcon(current.pictureName));
				icons.add(icon);
				
				sections=new JPanel();
				sections.setLayout(new BoxLayout(sections, BoxLayout.Y_AXIS));
				name=new JLabel(current.name+"                 lvl. "+current.level);
				name.setFont(new Font("sans serif", Font.BOLD, 50));
				health=new JProgressBar();
				health.setMaximum(current.maxHP);
				health.setValue(current.currentHP);
				health.setSize(new Dimension(600,30));
				sections.add(name);
				sections.add(health);
				statuses.add(sections);
			}
		}
		JButton swap=new JButton("Swap");
		swap.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				swap();
			}
		});
		statuses.add(swap);
		JSplitPane all=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, icons, statuses);
		editProperties(all);
		poke.getContentPane().add(all);
		poke.setVisible(true);
	}

	
	private void swap(){
		Object[] numbers=new Object[]{1,2,3};
		int first, second;
		try{
			first=(int) JOptionPane.showInputDialog(poke, "Select first Pokumon to swap", "Pokumon 1", JOptionPane.PLAIN_MESSAGE, null, numbers, null);
			second=(int) JOptionPane.showInputDialog(poke, "Select second Pokumon to swap", "Pokumon 2", JOptionPane.PLAIN_MESSAGE, null, numbers, null);
		}
		catch(Exception e){
			return;
		}
		Pokumon temp;
		if(!Pokumon.playerPoke[first-1].equals(Pokumon.playerPoke[second-1])){
			temp=Pokumon.playerPoke[first-1];
			Pokumon.playerPoke[first-1]=Pokumon.playerPoke[second-1];
			Pokumon.playerPoke[second-1]=temp;
		}
		loadPoke();
	}
	private void setupJMenuBar(){
		JMenuBar jmb=new JMenuBar();
		JMenu file=new JMenu("File");
		jmb.add(file);
		
		JMenuItem poke=new JMenuItem("Pokumon");
		poke.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(Main.ablePokeMenu && Main.menusEnabled)
					openPokeFrame();
				else
					AudioPlayer.plays(3);
			}
		});
		file.add(poke);
		
		JMenuItem item=new JMenuItem("Items");
		item.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(Main.menusEnabled)
					openItemFrame();
				else
					AudioPlayer.plays(3);
			}
		});
		file.add(item);
		
		JMenuItem about=new JMenuItem("About");
		about.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, "Made by Matt Spooner in 2013\nMade for Emily's 13th birthday\nProject Timeline: "+Main.timeLine+"\nHours spent: "+Main.hoursWorked);
			}
		});
		file.add(about);
		
		jmb.setPreferredSize(new Dimension(1020,20));
		frame.setJMenuBar(jmb);
	}
	
	private void setupFrameContents(){
		
		JSplitPane healthBars=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, playerHealth, oppoHealth);
		healthBars.setDividerLocation(510);
		editProperties(healthBars);
		
		JSplitPane healthConsole=new JSplitPane(JSplitPane.VERTICAL_SPLIT, console, healthBars);
		healthConsole.setDividerLocation(110);
		editProperties(healthConsole);
		
		Container playerLabels=new Container();
		playerLabels.setLayout(new BoxLayout(playerLabels, BoxLayout.Y_AXIS));
		playerLabels.add(playerName);
		playerLabels.add(playerPoke);
		playerLabels.add(playerMoney);
		JSplitPane playerIconLabels=new JSplitPane(JSplitPane.VERTICAL_SPLIT, playerIcon, playerLabels);
		playerIconLabels.setDividerLocation(300);
		editProperties(playerIconLabels);
		
		Container buttons=new Container();
		buttons.setLayout(new GridLayout(2,2));
		buttons.add(upperLeft);
		upperLeft.addActionListener(this);
		buttons.add(upperRight);
		upperRight.addActionListener(this);
		buttons.add(lowerLeft);
		lowerLeft.addActionListener(this);
		buttons.add(lowerRight);
		lowerRight.addActionListener(this);
		buttons.setMinimumSize(new Dimension(420,420));
		
		JSplitPane leftMiddle=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, playerIconLabels, buttons);
		leftMiddle.setDividerLocation(300);
		editProperties(leftMiddle);
		
		Container oppoLabels=new Container();
		oppoLabels.setLayout(new BoxLayout(oppoLabels, BoxLayout.Y_AXIS));
		oppoLabels.add(oppoName);
		oppoLabels.add(oppoPoke);
		oppoLabels.add(oppoLeft);
		JSplitPane oppoIconLabels=new JSplitPane(JSplitPane.VERTICAL_SPLIT, oppoIcon, oppoLabels);
		oppoIconLabels.setDividerLocation(300);
		editProperties(oppoIconLabels);
		
		JSplitPane leftMiddleRight =new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftMiddle, oppoIconLabels);
		leftMiddleRight.setDividerLocation(720);
		editProperties(leftMiddleRight);
		
		JSplitPane topBottom=new JSplitPane(JSplitPane.VERTICAL_SPLIT, healthConsole, leftMiddleRight);
		topBottom.setDividerLocation(180);
		editProperties(topBottom);
		
		frame.getContentPane().setLayout(new CardLayout());
		
		JButton title=new JButton("<html>Matt proudly presents <br>a new bestselling game entitled...");
		title.addActionListener(this);
		title.setFont(new Font("sans serif", Font.BOLD, 40));
		frame.getContentPane().add(title);
		
		JButton picture=new JButton();
		picture.addActionListener(this);
		picture.setActionCommand("picture");
		Image i = loadImage("titlepicture");
		i = i.getScaledInstance(1020, 600, 0);
		picture.setIcon(new ImageIcon(i));
		frame.getContentPane().add(picture);
		frame.getContentPane().add(topBottom);
		
		setFontSizes();
		setDefaults();
	}
	
	private static Image loadImage(String fileName){
		String path = "images/" + fileName + ".png";
		try {
			if(Main.IS_TEST){
				return ImageIO.read(new File(path));
			}
			else{
				return ImageIO.read(GUI.class.getResource("/" + path));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void setFontSizes() {
		Font standard=new Font("sans serif", Font.BOLD, 22);
		Font consoleF=new Font("sans serif", Font.BOLD, 28);
		
		console.setFont(consoleF);
		playerName.setFont(standard);
		playerPoke.setFont(standard);
		playerMoney.setFont(standard);
		oppoName.setFont(standard);
		oppoPoke.setFont(standard);
		oppoLeft.setFont(standard);
		upperLeft.setFont(standard);
		upperRight.setFont(standard);
		lowerLeft.setFont(standard);
		lowerRight.setFont(standard);
	}
	
	private void setDefaults(){
		playerIcon.setIcon(getImageIcon("blank"));
		oppoIcon.setIcon(getImageIcon("blank"));
		console.setEditable(false);
		playerHealth.setBackground(new Color(168,156,156));
		playerHealth.setForeground(new Color(168,0,0));
		oppoHealth.setBackground(new Color(168,156,156));
		oppoHealth.setForeground(new Color(73,120,168));
	}
	
	private void editProperties(JSplitPane jsp){
		jsp.setEnabled(false);
		jsp.setDividerSize(0);
	}
	
	public static ImageIcon getImageIcon(String fileName){
		Image i = loadImage(fileName).getScaledInstance(300, 300, 0);
		return new ImageIcon(i);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton j=(JButton) e.getSource();
		String ac=e.getActionCommand();
		
		if(ac.equals("Fight")){
			console.setText("What attack?");
			Battle.setBattleButtons(2);
		}
		else if(ac.equals("Pokumon")){
			Battle.changedPoke=true;
			Battle.madeMove=true;
		}
		else if(ac.equals("Bag")){
			Battle.usedItem=true;
			Battle.madeMove=true;
		}
		else if(ac.equals("Run")){
			Battle.running=true;
			Battle.madeMove=true;
		}
		else if(ac.equals("Search grass")){
			Main.choseGrass=true;
			Main.buttonPressed=true;
		}
		else if(ac.equals("Back to town")){
			Main.buttonPressed=true;
		}
		else if(ac.equals("<html>Matt proudly presents <br>a new bestselling game entitled...")){
			CardLayout cl=(CardLayout) frame.getContentPane().getLayout();
			cl.next(frame.getContentPane());
			AudioPlayer.playm(4);
		}
		else if(ac.equals("picture")){
			CardLayout cl=(CardLayout) frame.getContentPane().getLayout();
			cl.next(frame.getContentPane());
			Main.titleClosed=true;
		}
		else if(ac.equals("Next")){
			Main.buttonPressed=true;
		}
		else if(ac.equals("Adventure")){
			Main.placeChosen=1;
			Main.buttonPressed=true;
		}
		else if(ac.equals("Mart")){
			Main.placeChosen=2;
			Main.buttonPressed=true;
		}
		else if(ac.equals("Potion")){
			Main.chosePotion=true;
			Main.buttonPressed=true;
		}
		else if(ac.equals("Pokuball")){
			Main.chosePotion=false;
			Main.buttonPressed=true;
		}
		else if(ac.equals("Back")){
			Main.choseBack=true;
			Main.buttonPressed=true;
		}
		else if(ac.equals("Pokumon Center")){
			Main.placeChosen=3;
			Main.buttonPressed=true;
		}
		else if(ac.equals("Lab")){
			Main.placeChosen=4;
			Main.buttonPressed=true;
		}
		else if(j.equals(upperLeft) && Battle.buttonState==2){
			if(Battle.currentPlayerPoke.attacks[0].currentPP==0){
				console.setText("Out of PP!");
				return;
			}
			moveNumber=1;
			Battle.madeMove=true;
		}
		else if(j.equals(upperRight) && Battle.buttonState==2){
			if(Battle.currentPlayerPoke.attacks[1].currentPP==0){
				console.setText("Out of PP!");
				return;
			}
			moveNumber=2;
			Battle.madeMove=true;
		}
		else if(j.equals(lowerLeft) && Battle.buttonState==2){
			if(Battle.currentPlayerPoke.attacks[2].currentPP==0){
				console.setText("Out of PP!");
				return;
			}
			moveNumber=3;
			Battle.madeMove=true;
		}
		else if(j.equals(lowerRight) && Battle.buttonState==2){
			if(Battle.currentPlayerPoke.attacks[3].currentPP==0){
				console.setText("Out of PP!");
				return;
			}
			moveNumber=4;
			Battle.madeMove=true;
		}
	}
	
	public static void showSexDialog(){
		Object[] options = {"Boy", "Girl"};
		String s=(String)JOptionPane.showInputDialog(frame, "Prof. Pine:\t Are you a boy, or a girl?", "Boy/Girl", JOptionPane.PLAIN_MESSAGE, null, options, "Girl");
		if(s.equals("Girl"))
			Main.male=false;
		else
			Main.male=true;
	}
	public static void showNameDialog(){
		Main.playerName=(String)JOptionPane.showInputDialog(frame, "Prof. Pine:\t Now, What is your name?", "Name", JOptionPane.PLAIN_MESSAGE, null, null, "Emily");
	}
	public static Object showPokeDialog(){
		String message="";
		int num=0;
		for(int i=0;i<3;i++){
			if(Pokumon.playerPoke[i]!=null){
				message+=(i+1)+": "+Pokumon.playerPoke[i].name+"\n";
				num++;
			}
		}
		Object[] nums=null;
		if(num==1)
			nums=new Object[]{1};
		if(num==2)
			nums=new Object[]{1,2};
		if(num==3)
			nums=new Object[]{1,2,3};
		return JOptionPane.showInputDialog(frame, message, "Switch", JOptionPane.PLAIN_MESSAGE, null, nums, null);
	}
	public static Object showItemDialog(){
		String message ="1: Pokuballs     x"+Main.holding[0].numberOwned+"\n2: Potions     x"+Main.holding[1].numberOwned;
		Object[] nums=new Object[]{1,2};
		return JOptionPane.showInputDialog(frame, message, "Use Item", JOptionPane.PLAIN_MESSAGE, null, nums, null);
	}
	public static Object showSwitchDialog(){
		return JOptionPane.showInputDialog(switchDialog, "Which would you like to withdraw?", "Swap", JOptionPane.PLAIN_MESSAGE, null, null, null);
	}
}
