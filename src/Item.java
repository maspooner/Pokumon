
public class Item {
	
	public static final int HEAL=20;
	String name;
	int cost;
	boolean pokuball;
	int numberOwned;
	
	public Item(boolean pokuball){
		if(pokuball){
			name="Pokuball";
			cost=25;
		}
		else{
			name="Potion";
			cost=20;
		}
		this.pokuball=pokuball;
		numberOwned=0;
	}
}
