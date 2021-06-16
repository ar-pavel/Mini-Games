# Reversi-Othello
Reversi Rules Reversi (also known as Othello) is a pretty simple game. It consists of a 8x8 square board, and pieces with one black and one white side. Each player has a color, and the aim of the game is to get more of your pieces on the board than the opponent's pieces.

#### Description
This game is played between two players, with one player using black pieces and the other player using white
pieces. The players take turns setting their pieces on an 8x8 game board. The goal of the game is to have the
most pieces on the board by the end. More detailed rules and instructions are in their own section below.

There are three classes: Point , Reversi , and Game .
- Point is a class that represents the location of a piece on the 2-dimensional game board.

- Reversi is a class that launches the game and handles the logistics of game delivery and management.

- Game is a class that handles the specific game mechanics and rules associated with Reversi. 



##### Game:
- displayBoard
    - Displays the current game board.
- gameResult
    - Calculate and return the result of the game.
    
    Cases:
    
            - Return 1 - There are more black pieces than white pieces.
            - Return 0 - There are an equal number of black pieces and white pieces.
            - Return -1 - There are more white pieces than black pieces.

- getPlaceableLocations
    - This method checks which points are valid moves for the current player. Then, it returns an array of
those positions.
    - There are only 64 positions in our 8x8 board, so 64 is the upper limit.
    - 8 cases need to be checked: top down, bottom up, left to right, right to left and the 4 diagonals.
- showPlaceableLocations
    - Places a '*' in the game board at every valid position the current player can place a piece.
    
- placeMove
    - Updates the board with the results of the current player making a move.
- updateScores
    - Updates the scores of both players after every move.
    - Scores refer to the number of pieces each player has on the board.
    
##### Reversi:

- *isEmpty*
    - Check whether any valid moves can be played. Returns true if the board is full (All 64 positions are
occupied), false if it is not.

- *contains*
    - This method should check if a given point/position in the board is within the given array of points.
    - This is used to check if the user’s input is among their valid set of positions (moves) or not.


- start
    - This method runs the game.
        - The player using black pieces will always start the game.
    - This method has to handle reading input from the user in order to make a move.
        - In this case, it will take a two digit integer (where the rst digit is the row, and the second is
the column) from the user to represent a position that they want to move to.
        - Then, it needs to check whether this position is within this player’s valid moves or not. If it is
not, you need to keep asking the user until you get a valid position - contains() method.
    
        - If the user inputs ‘exit’, the game terminates.
    - Moreover, this method needs to handle cases where one of the players might have no valid
moves, and has to skip his turn.
    - Finally, this method should report the winner at the end of the game.
    - The game terminates either when both players have no valid moves or when the board is full and
there are no empty positions. Hint: the isEmpty() method may be useful. It also terminates if the
players inputs ‘exit’.  

- main
    - Creates a Game object and calls the start() method.
    
    
    