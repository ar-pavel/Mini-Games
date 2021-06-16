# Bomberman

_Console based single player Bomberman game_

### Overview

This game is inspired by the `retro Bomberman game`, but it is an oversimplified version. The game consists of a 2D board of hidden rewards and scores. The score sare represented as floating-point numbers, positive and negative. The board tiles are randomly populated by these numbers. In addition, the board has an exit tile and reward tiles that double the range of a bomb. When the user uncovers the exit tile, the game is won.

**Dependencies**

    GNU compiler

**Get to the game.**

    Clone this repository and run the file named `bomberman.c`.

```shell
git clone url_of_this_repository
cd Bomberman
gcc bomberman.c
./a.out PlayerName row column   # ex: ./a.out   pavel    10     10
```

#### Game Details

The player starts with three lives and a score of zero and a specific number of bombs. S/He chooses a cell to place a bomb. The bomb explodes uncovering the immediate bordering tiles to the bomb. The user’s score is updated as the sum of all the values of the uncovered tiles. If the score becomes negative life is lost and the number of lives is decremented. If a reward tile is uncovered, the bomb range is doubled: it will be able to uncover the immediately surrounding tiles and their immediately surrounding tiles. Unlike the original game, the bomb never kills the Bomberman. The game ends in one of three cases: when the score becomes less than or equal to zero and the lives are zeroed, when the player uncovers the exit tile on the board or the total number of bombs is exhausted before uncovering the exit tile. When the user starts a new life, the total number of bombs is maintained from the previous life and the score is zeroed.
