from FiveEval import FiveEval

class Player :
    def __init__(self, id):
        self.stack = 1000000.00
        self.hand = []
        self.id = id 
        self.evaluator = FiveEval()

    def bet(self, amount):
        if self.stack > amount:
            self.stack = self.stack - amount
        return ('B', amount, self.id)

    def call(self, amount):
        if self.stack > amount:
            self.stack = self.stack - amount
        return ('CA', amount,  self.id)

    def check(self):
        return ('C', self.id)

    def fold(self):
        return ('F', self.id)    

    def show_cards(self):
        visible_hand = []
        for card in self.hand:
            visible_hand.append(str(self.evaluator.number_lookup_table[self.evaluator.deckcardsFace[card]])+str(self.evaluator.suit_lookup_table[self.evaluator.deckcardsSuit[card]]))
        print "Player " + str(self.id) + " Hand: " + str(visible_hand)
    
    #takes in user command for action
    #calls bet(), call(), check(), or fold() 
    #b 10 = bet 10
    #c = check
    #ca = call
    #f = fold 
    def your_move(self):
        self.show_cards()
        while True:
            move = raw_input("Player " + str(self.id) + " move: ").upper()
            if move[0] == 'F':
                return self.fold()
            elif move[:2] == 'CA':
                return self.call(int(move.split(' ')[1]))
            elif move[0] == 'C':
                return self.check()
            elif move[0] == 'B':
                return self.bet(int(move.split(' ')[1]))
            elif move[0] == 'S':
                print("Player " + str(self.id) + " stack: " + str(self.stack))


