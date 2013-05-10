
public class ResultState extends State {
	
	private int bothand[]; // bot's 2 cards
	private int board[]; // board
	private int opphand[];
	private int pot;
	
	public ResultState() {
		bothand = new int[2];
		board = new int[3];
		// generate random cards from dealer?
		pot = 0;
	}
	
	// action is bot's flop action
	public ResultState(FlopState prev, int action, int[] opphand) {
		bothand = prev.getBothand();
		if (action == 1) // check
			pot = prev.getPot();
		else // bet and call
			pot = prev.getPot() + 2;
		this.opphand = opphand;
	}
	
	public boolean validCards(int[] opphand) {
		// return false if any cards overlap with bothand or flop
	}

}