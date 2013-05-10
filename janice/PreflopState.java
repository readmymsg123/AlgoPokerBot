
public class PreflopState extends State {
	
	private int oppAction; // opponent's action
	
	public PreflopState() { // this should never be called
		bothand = new int[2];
		// generate random cards from dealer?
		oppAction = 0;
	}
	
	public PreflopState(int card1, int card2, int oppAction) {
		bothand = new int[2];
		bothand[0] = card1;
		bothand[1] = card2;
		this.oppAction = oppAction;
	}
	
	public int[] getBothand() {
		return bothand;
	}
	
	public int getOppAction() {
		return oppAction;
	}
	
	public boolean validCards(int[] flop) {
		// return false if any cards overlap with bothand
	}
	
	public boolean validAction(int action) {
		// returns false if invalid action, ie. opp raises and bot checks
	}
	
}