import FiveEval
import numpy as np 

#initialize the deck
deck = []
player_1 = 1000000
player_2 = 1000000

def deal_cards():	
	for i in range(0, 51): #make deck
		deck.append(i)

