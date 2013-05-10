from FiveEval import FiveEval
from dealer import Dealer
from Player import Player

class PokerGame :

	def __init__(self):
		self.p1 = Player(1)
		self.p2 = Player(2)
		self.dealer = Dealer()
		self.pot = 0 
		self.flop_cards = [] 
		self.bet_flag = False
		self.check_flag = False
		self.cur_bet = 0 
		self.cur_round = 0 
		self.cur_player = 1
		self.evaluator = FiveEval()

	def pre_flop(self):
		hands = self.dealer.deal_cards()
		self.p1.hand = hands[0]
		self.p2.hand = hands[1]
		self.cur_bet = 0 
		
		self.handle_move(self.p1.your_move(), 1)

	def flop(self):
		self.check_flag = False
		self.bet_flag = False
		self.see_pot()
		print "-----------------------------"
		print "Dealing flop"
		self.flop_cards = self.dealer.deal_flop()
		visible_flop = []
		for card in self.flop_cards:
			visible_flop.append(str(self.evaluator.number_lookup_table[self.evaluator.deckcardsFace[card]])+str(self.evaluator.suit_lookup_table[self.evaluator.deckcardsSuit[card]]))
		print "Flop: " + str(visible_flop)
		self.cur_bet = 0
		self.handle_move(self.p1.your_move(), 1)

	def see_stack_sizes(self):
		print "------------------------------"
		print "Stack Sizes"
		print("Player 1 stack: " + str(self.p1.stack))
		print("Player 2 stack: " + str(self.p2.stack))

	def see_pot(self):
		print "------------------------------"
		print "Pot Size: $" + str(self.pot)

	def handle_move(self, move, id):
		if move[0] == 'F':
			self.bet_flag = False
			print "Player " + str(move[1]) + "folded"
			self.end_round(move[1])
		elif move[0] == 'B' and self.bet_flag == False:
			self.cur_bet = move[1]
			self.pot = self.pot + self.cur_bet
			self.bet_flag = True
			print "Player " + str(move[2]) + " bet $" + str(self.cur_bet) 
			self.change_turn(move[2])
		elif move[0] == 'CA' and self.bet_flag == True:
			self.pot = self.pot + self.cur_bet
			self.bet_flag = False
			print "Player " + str(move[2]) + " called the bet"
			self.next_round()
		elif move[0] == 'C' and self.bet_flag == False:
			if self.check_flag == False:
				print "Player " + str(move[1]) + " checked"
				self.check_flag = True
				self.change_turn(move[1])
			else:
				print "Player " + str(move[1]) + " checked"
				self.check_flag = False
				self.next_round()
		else:
			print "False Move"
			self.repeat_turn()

	def change_turn(self, cur_player):
		print "-----------------------------"
		if cur_player == 1:
			self.cur_player = cur_player
			self.handle_move(self.p2.your_move(),2)
		else:
			self.cur_player = cur_player
			self.handle_move(self.p1.your_move(),1)

	def next_round(self):
		self.cur_round = self.cur_round + 1
		if self.cur_round == 1:
			self.flop()
		if self.cur_round == 2:
			self.evaluate()

	def repeat_turn(self):
		if self.cur_round == 1:
			self.handle_move(self.p1.your_move(),1)
		else:
			self.handle_move(self.p2.your_move(),2)

	def end_round(self, folded_player):
		print "Player " + str(folded_player) + " folded."

	def evaluate(self):
		self.cur_round += 1 
		self.see_pot()
		p1_hand = self.p1.hand + self.flop_cards
		p2_hand = self.p2.hand + self.flop_cards
		score_p1 = self.evaluator.getRankOfFive(p1_hand[0],p1_hand[1],p1_hand[2],p1_hand[3],p1_hand[4])
		score_p2 = self.evaluator.getRankOfFive(p2_hand[0],p2_hand[1],p2_hand[2],p2_hand[3],p2_hand[4])
		if score_p1 > score_p2:
			print "Player 1 wins $" + str(self.pot) 
			self.p1.stack += self.pot
		elif score_p2 > score_p1:
			print "Player 2 wins $" + str(self.pot) 
			self.p2.stack += self.pot
		else:
			print "Split pot"
			self.p2.stack += (0.5*self.pot)
			self.p1.stack += (0.5*self.pot)
		self.see_stack_sizes()


def run():
	game = PokerGame()
	game.pre_flop()

run()