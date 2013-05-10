/* Assumptions and simplifications

heads up, opponent always goes first
bets are of constant amount
no blinds
game ends after flop
bot never folds when it can check

Notes:
cards are represented as integers. deck[0-3] = ace, 4-7 = king, 8-11 = queen, etc.
deck[0], [1], [2], [3] = spade, heart, diamond, club respectively

actions are also integers. 0 = fold, 1 = check, 2 = bet/raise, 3 = call


simple todos:
use enums for actions
use constants like numFlopPerPreflop to avoid multiplying at every for loop check

less simple todo: figure out how to store transitions
maybe each class can store a map. 
map action -> arraylist of resulting states

*/


public class Bot {
	public static void main(String [] args) {
				
		// example - you have pocket aces and opponent bets
		State preflop = new PreflopState(0, 1, 2);
		
		// there are only 2 ways to get into the flop:
		// both players check, or opp bets and bot calls.
		// but for now i'm just going to loop through all possible actions
		State flops[] = new FlopState[50*49*48*4*4]; 
		int index = 0;
		for (int action = 0; action < 4; action++) // bots preflop action
			for (int i = 0; i < 52; i++)
				for (int j = 0; j < 52; j++)
					for (int k = 0; k < 52; k++) {
						int flopcards[] = {i, j, k};
						if (preflop.validCards(flopcards) && preflop.validAction(action)) {
							// loop through ofa (opponent flop actions)
							for (int ofa = 0; ofa < 4; ofa++) {
								flops[index] = new FlopState((PreflopState)preflop, action, flopcards, ofa);
								preflop.addTransition(action, index); // used for links between states
								index++;
							}
						}
				}
		
		// now we have 1 preflop state linked to all possible flop states
		
		//time to create all result states
		int numFlopStates = index; // this should be 50*49*48*4*4
		index = 0;
		State results[] = new ResultState[numFlopStates*47*46*4];
		for (int pf = 0; pf < numFlopStates; pf++) {
			FlopState curflop = (FlopState) flops[pf];
			for (int action = 0; action < 4; action++) // bots flop action
				for (int i = 0; i < 52; i++) // showing opponent's hand now
					for (int j = 0; j < 52; j++) {
							int opphand[] = {i, j};
							if (curflop.validCards(opphand)) {
								results[index] = new ResultState((FlopState)curflop, action, opphand);
								curflop.addTransition(action, index); // used for links between states
								index++;
							}
					}
		}
		int numResultStates = index;
		
		// okay now we have all the states set up, we just need to value iterate.
		// im gonna store all the values inside the states themselves, v[3]
		// where v_i(s) is basically state s.v[i]
		
		preflop.setValue(0, 0); // parameters are setValue(i, value)
		for (int i = 0; i < numFlopStates; i++)
			flops[i].setValue(0, 0);
		for (int i = 0; i < numResultStates; i++)
			results[i].setValue(0, calculateWinnings(results[i]));
		// calculateWinnings determines who wins the hand
		// winnings = pot or 0
		
		// in this system, the only transitions that have rewards are the results
		// also, this loop can actually be simplified because all actions go in
		// one direction. but we might need this later when our model becomes
		// more complicated. 
		for (int h = 1; h < 3; h++) {
			
			float maxvalue = 0;
			for (int a = 0; a < 4; a++) { // loop through all actions to find the best one
				float value = 0;
				for (int i = 0; i < 50*49*48*4; i++) {
					State next = preflop.getTransition(a, i);
					value += next.getValue(h-1); // get v_{h-1}(State next)
				}
				value /= (50*49*48*4);
				if (value > maxvalue)
					maxvalue = value;
			}
			preflop.setValue(h, maxvalue);
			
			for (int s = 0; s < numFlopStates; s++) {
				maxvalue = 0;
				for (int a = 0; a < 4; a++) { // loop through all actions to find the best one
					float value = 0;
					for (int i = 0; i < 47*46*4; i++) {
						State next = flops[s].getTransition(a, i);
						value += next.getValue(h-1); // get v_{h-1}(State next)
					}
					value /= (47*46*4);
					if (value > maxvalue)
						maxvalue = value;
				}
				flops[s].setValue(h, maxvalue);
			}
			
			// we dont need to recalculate v(resulting states)
			for (int s = 0; s < numResultStates; s++) {
				results[s].setValue(h, results[s].getValue(h-1));
			}
			
		}
			
		// TODO: return policy, given the values
		
	}
}