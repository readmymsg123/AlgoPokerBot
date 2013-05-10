
public class FlopState extends State {
	
	private int bothand[]; // bot's 2 cards
	private int board[]; // board
	private int pot; // result from preflop only
	private int oppFlopAction;
	
	// later on: store previous actions of bot and opponent for more sophisticated strategies
	
	public FlopState() {
		bothand = new int[2];
		board = new int[3];
		// generate random cards from dealer?
		pot = 0;
	}
	
	// action is bot's preflop action
	public FlopState(PreflopState prev, int action, int[] flop, int oppFlopAction) {
		bothand = prev.getBothand();
		if (action == 1) // check
			pot = 0;
		else // bet and call
			pot = 2;
		this.board = flop;
		this.oppFlopAction = oppFlopAction;
	}
	
	public int getPot() {
		return pot;
	}
	
	public ResultState getTransition(int botAction, int index) {
		// return resulting state given an action (and which one of the random decisions)
	}
	
	public boolean validCards(int[] opphand) {
		// return false if any cards overlap with bothand or flop
	}

	public boolean validActions(int botAction) {
		// returns false if invalid set of opponent-bot actions
	}
}