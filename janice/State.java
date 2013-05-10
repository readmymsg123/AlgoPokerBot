
public class State {
	
	protected int bothand[]; // bot's 2 cards
	protected int pot;
	protected float value[];
	
	public State() {
		bothand = new int[2];
		pot = 0;
	}
	
	public int[] getBothand () {
		return bothand;
	}
	
	public void setValue(int index, float value) {
		this.value[index] = value;
	}
	
	public float getValue(int index) {
		return value[index];
	}
	
}