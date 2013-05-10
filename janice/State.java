
public class State {
	
	protected int bothand[]; // bot's 2 cards
	protected int pot;
	
	public State() {
		bothand = new int[2];
		pot = 0;
	}
	
	public int[] getBothand () {
		return bothand;
	}
	
}