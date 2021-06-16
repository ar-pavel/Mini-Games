#include "stdio.h"
#include "stdbool.h"
#include "stdlib.h"
#include "math.h"
#include "time.h"

// ---------------Static Variables------------------------ //

// life count, initial count is 3
int lifeCnt = 3;
// bomb count, initial count is 3
int bombCnt = 3;

// colom size and row size of the board
int col, row;

// track the exit and bomb cell
int exitX, exitY;

// board distributaion percentages
const int negPercentage = 40;
const int posPercentage = 40;
const int specialPercentage = 20;

// range of the board points
float left = 0, range = 15;

// game status checker
bool win = false;

// user name
const char *userName;

// initial range of the bomb
int boomPower = 1;

// flag a cell as if it is already unwraped, plus for positive cell and minus for negative
const float PLUS = 69.11;
const float MINUS = -69.11;
const float maskedBOMB = 33.22;
const float BOMB = -33.22;
const float maskedEXIT = 77.99;
const float EXIT = -77.99;

// track current score of the user
double currentScore = 0.0;

// file pointer
FILE *fptr;

// path to the file
char fname[] = "user.log";

// -------------------******-------------------------------- //

int convertToInt(const char a[])
{
	int i = 0;
	int num = 0;
	while (a[i] != 0)
	{
		num = (a[i] - '0') + (num * 10);
		i++;
	}
	return num;
	;
}

int min(const int a, const int b)
{
	return a < b ? a : b;
}

float randomNum(float n, float m, bool neg)
{
	float scale = rand() / (float)RAND_MAX;			 /* [0, 1.0] */
	return (neg ? -1 : 1) * ((n + scale * (m - n))); /* [min, max] */
}

void displayOriginal(float **board)
{
	int i, j;
	for (i = 0; i < row; ++i)
	{
		for (j = 0; j < col; ++j)
		{
			if (board[i][j] == EXIT)
				printf("*\t");
			else if (board[i][j] == BOMB)
				printf("$\t");
			else
				printf("%.2f\t", board[i][j]);
		}
		printf("\n");
	}
}

// display the masked game board
void display(float **board)
{
	int i, j;
	for (i = 0; i < row; ++i)
	{
		for (j = 0; j < col; ++j)
		{
			// if this cell is exit point
			if (board[i][j] == EXIT)
				printf("*");
			// if this cell contains a bomb
			else if (board[i][j] == BOMB)
				printf("$");
			// if this cell is already umsaked and positive
			else if (board[i][j] == PLUS)
				printf("+");
			// if this cell is already umsaked and negative
			else if (board[i][j] == MINUS)
				printf("-");
			// if this cell hasn't masked yet
			else
				printf("X");
		}
		printf("\n");
	}
}

void populateBoard(float **board)
{
	// tract the negative number count
	int negCnt = 0;

	// assign a random floating point number in each cell of the board
	for (int i = 0; i < row; ++i)
		for (int j = 0; j < col; ++j)
		{
			// check if the negative number count has already exced more than the desired percentage
			// if not, assign select randomly
			bool neg = ((negCnt + 1) * 1.0 / (row * col * 1.0) * 100.0 > negPercentage ? 0 : rand() % 2);
			negCnt += neg ? 1 : 0;
			board[i][j] = randomNum(left, range, neg);
		}

	// generate exit point randomly
	exitX = randomNum(0, row - 1, 0);
	exitY = randomNum(0, col - 1, 0);

	// mark exit point of the board

	// number of bombs in the board
	int cnt = randomNum(18, 20, 0);

	while (cnt > 0)
	{
		// generate a bomb cell randomly
		int bombX = randomNum(0, row - 1, 0);
		int bombY = randomNum(0, col - 1, 0);

		// make sure bomb and exit point are not in the same cell
		if (!(bombX == exitX && bombY == exitY))
		{
			board[bombX][bombY] = maskedBOMB;
			--cnt;
		}
	}

	board[exitX][exitY] = maskedEXIT;
	// printf("exit x = %d , y = %d\n", exitX, exitY);

	printf("Total Negative count percentage : %.2f%\n", negCnt * 1.0 / (row * col * 1.0) * 100.0);
}

void initializeGame(float **board)
{

	// assing random numbers to the board
	populateBoard(board);

	// reset life and bomb count
	lifeCnt = bombCnt = 3;

	// debug perpose print
	displayOriginal(board);

	printf("input -1 -1 to exit the game\n");
}

void gameLoger()
{
	fptr = fopen(fname, "a");
	if (fptr == NULL)
	{
		printf("File Writing Error!");
		exit(1);
	}
	fprintf(fptr, "%s,%.2f,%s\n", userName, currentScore, (win ? "win" : "lose"));
}

void exitGame()
{

	gameLoger();
	// fputs(userName, fptr);

	printf("Game exited!");
}

void displayTopNScore(int n)
{
	fptr = fopen(fname, "r");
	if (fptr == NULL)
	{
		printf("File Writing Error!");
		exit(1);
	}

	char c[100];
	int i, d;
	for (i = 1; i <= n; ++i)
	{
		fscanf(fptr, "%s", c);
		printf("%s\n", c);
	}
}

bool makeMove(float **board, int x, int y)
{

	bool status = false;

	// possible 8 moves
	int dx[] = {0, 0, 0, +1, +1, +1, -1, -1, -1};
	int dy[] = {0, +1, -1, -1, 0, +1, -1, 0, +1};

	// possible 16 moves
	int dx16[] = {-2, -2, -2, -2, -2, -1, -1, -1, -1, -1, 0, 0, 0, 0, 0, +1, +1, +1, +1, +1, +2, +2, +2, +2, +2};
	int dy16[] = {0, +1, +2, -1, -2, 0, +1, +2, -1, -2, 0, +1, +2, -1, -2, 0, +1, +2, -1, -2, 0, +1, +2, -1, -2};

	double score = 0.0;

	int i;
	if (boomPower == 2)
	{
		boomPower = 1;
		for (i = 0; i <= 25; i++)
		{
			int tx = dx16[i] + x;
			int ty = dy16[i] + y;

			// check if this cell is valid
			if (tx >= 0 && tx < row && ty >= 0 && ty < col)
			{
				// if the discovered cell contains no special value
				if (abs(board[tx][ty]) <= range)
				{
					score += board[tx][ty];
					// printf("%.2f \n", board[tx][ty]);
					board[tx][ty] = (board[tx][ty] < 0 ? MINUS : PLUS);
				}
				// if the exit cell revealed
				else if (board[tx][ty] == maskedEXIT)
				{
					// marks the status as true
					status = true;
					// mark the cell as disoceverd Exit point
					board[tx][ty] = EXIT;
				}
				else if (board[tx][ty] == maskedBOMB)
				{
					// mark the cell as disoceverd bomb
					board[tx][ty] = BOMB;

					if (boomPower == 1)
						printf("Bang!! Your bomb range is doubled\n");
					// double the bomb power
					boomPower = 2;
				}
			}
		}
	}
	else
	{
		for (i = 0; i <= 8; i++)
		{
			int tx = dx[i] + x;
			int ty = dy[i] + y;

			// check if this cell is valid
			if (tx >= 0 && tx < row && ty >= 0 && ty < col)
			{
				// if the discovered cell contains no special value
				if (abs(board[tx][ty]) <= range)
				{
					score += board[tx][ty];
					// printf("%.2f \n", board[tx][ty]);
					board[tx][ty] = (board[tx][ty] < 0 ? MINUS : PLUS);
				}
				// if the exit cell revealed
				else if (board[tx][ty] == maskedEXIT)
				{
					// marks the status as true
					status = true;
					// mark the cell as disoceverd Exit point
					board[tx][ty] = EXIT;
				}
				else if (board[tx][ty] == maskedBOMB)
				{
					// mark the cell as disoceverd bomb
					board[tx][ty] = BOMB;

					if (boomPower == 1)
						printf("Bang!! Your bomb range is doubled\n");
					// double the bomb power
					boomPower = 2;
				}
			}
		}
	}

	currentScore += score;

	// printf("score for %d %d is : %.2f", x, y, score);
	printf("Total uncovered score of %.2f points\n", score);

	// return status as true if an exit point is discovered
	return status;
}

// game controller
void playGame(float **board)
{

	// initialize the board and the variables
	initializeGame(board);

	/*
	The game ends in one of three cases: 
		- when the score becomes less than or equal to zero and the lives are zeroed, 
		- the player uncovers the exit tile on the board, 
		- or the total number of bombs is exhausted before uncovering the exit tile.
	*/

	bool gameOver = false;
	// // display the masked game board
	// display(board);

	while (!gameOver && bombCnt > 0 && lifeCnt > 0)
	{

		// display the updated board
		display(board);

		printf("Lives: %d\nScore: %0.2f\nBombs: %d\n", lifeCnt, currentScore, bombCnt);

		// play moves
		printf("Enter bomb position(x, y): \n");
		int x, y;
		// take input the moves
		scanf("%d %d", &x, &y);

		if (x == -1 && y == -1)
		{
			// exit the game
			// exitGame();
			return;
		}

		// make move
		gameOver = makeMove(board, x, y);

		if (currentScore < 0)
		{
			--lifeCnt;
			currentScore = 0;
		}

		// gameOver = true;
		bombCnt--;
	}

	win = board[exitX][exitY] == EXIT;

	printf(gameOver ? "Game Own!\n" : "Game Lost!\n");

	// log the game
	exitGame();
}

// process the variables passed as program argument
void readArgumentVariables(const char *argv[])
{

	// for(int i=0; argv[i]!=NULL; ++i)
	// 		printf("%s\n", argv[i]);

	userName = (argv[1] == NULL ? "Undefined" : argv[1]);

	const char *tmp = "10";
	row = convertToInt((argv[2] == NULL ? tmp : argv[2]));
	col = convertToInt((argv[3] == NULL ? tmp : argv[3]));

	// debug print
	printf("userName : %s\n", userName);
	printf("row : %d\n", row);
	printf("col : %d\n", col);
}

int main(int argc, char const *argv[])
{
	// #ifndef ONLINE_JUDGE
	// freopen("in.txt", "r", stdin);
	// freopen("xc.txt", "w", stdout);
	// #endif

	fptr = fopen(fname, "a");
	if (fptr == NULL)
	{
		printf("File Writing Error!");
		exit(1);
	}

	displayTopNScore(2);

	srand(time(0));

	float **board;

	readArgumentVariables(argv);

	// allocating momories  according to prefered size
	board = malloc(row * sizeof *board);
	// for each row allocating the columns of size param col
	int i;
	for (i = 0; i < row; i++)
		board[i] = malloc(col * sizeof *board[i]);

	int x;
	// print the available options
	printf("Enter : \n");
	printf("\t1 : play game\n");
	printf("\t2 : display top scores \n");
	printf("\t3 : Exit\n");
	while (scanf("%d", &x))
	{
		if (x == 1)
			playGame(board);
		else if (x == 2)
		{
			printf("How many : ");
			scanf("%d", &x);
			displayTopNScore(x);
		}
		else
			goto done;

		printf("Enter : \n");
		printf("\t1 : play game\n");
		printf("\t2 : display top scores \n");
		printf("\t3 : Exit\n");
	}

// printf("_____no error so far!\n");
done:
	fclose(fptr);

	return 0;
}