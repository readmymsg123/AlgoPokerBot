import FiveEval
import numpy as np 
from random import choice


class Dealer : 

    def __init__(self):
        self.deck = []
    #deals hole cards
    def deal_cards(self):
        del self.deck[:]
        for i in range(0, 51):
            self.deck.append(i)

        p1_hand = []
        p2_hand = []
        for i in range(2):
            card = choice(self.deck)
            p1_hand.append(card)
            self.deck.remove(card)
            card = choice(self.deck)
            p2_hand.append(card)
            self.deck.remove(card)
        return (p1_hand, p2_hand)
    #deals three community cards
    def deal_flop(self):
        flop = [] 
        for i in range(3):
            card = choice(self.deck)
            self.deck.remove(card)
            flop.append(card) 
        return flop



