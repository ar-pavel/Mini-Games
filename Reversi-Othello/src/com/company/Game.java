package com.company;

public class Game {

    public Point point;
    private final char[][] board;
    public int WScore;
    public int BScore;
    public int remaining;
    private final char[] boardX = new char[]{'1', '2', '3', '4', '5', '6', '7', '8'};

    public Game() {
        board = new char[][]{
                {'_', '_', '_', '_', '_', '_', '_', '_',},
                {'_', '_', '_', '_', '_', '_', '_', '_',},
                {'_', '_', '_', '_', '_', '_', '_', '_',},
                {'_', '_', '_', 'W', 'B', '_', '_', '_',},
                {'_', '_', '_', 'B', 'W', '_', '_', '_',},
                {'_', '_', '_', '_', '_', '_', '_', '_',},
                {'_', '_', '_', '_', '_', '_', '_', '_',},
                {'_', '_', '_', '_', '_', '_', '_', '_',},};
    }

    public void displayBoard() {
        /**
         * TODO: Display the current board along with the current positions of the pieces
         * @param b The game board to be displayed
         */
        System.out.print("\n ");
        for ( char c: boardX ) {
            System.out.print(" " + c);
        }
        System.out.print(" \n");

        int i =  1;
        for ( char[] x: board) {
            System.out.print(i++ + " ");
            for ( char c: x) {
                System.out.print(c + " ");
            }
            System.out.println();
        }
    }

    public int gameResult(Point[] whitePiecePlaceableLocations, Point[] blackPiecePlaceableLocations) {
        /**
         * TODO: Update the score and determine the game result
         * @param whitePiecePlaceableLocations Array of possible moves for white pieces
         * @param blackPiecePlaceableLocations Array of possible moves for black pieces
         * @return The integer corresponding to the game result: 1 for black win, 0 for draw, -1 for white win
         */

        updateScores();

        if(remaining==0 || (isEmpty(whitePiecePlaceableLocations) && isEmpty(blackPiecePlaceableLocations))){
            if(this.WScore > this.BScore)
                return -1;
            if(this.BScore > this.WScore)
                return 1;
            return 0;
        }
        return 5;

    }

    public Point[] getPlaceableLocations(char player, char opponent) {
        /**
         * TODO: Check the board for and fill the placeablePositions array with valid moves that the player can make
         * @param player Current player
         * @param opponent player's opponent
         * @return placeablePositions array, with corresponding point for a valid location and (-1,-1) for an invalid location
         */

        Point[] placeablePositions = new Point[64];


        for (int i=0,k=0; i<8; ++i)
            for(int j=0; j<8; ++j, ++k){
                if(canPlace(player, i, j)){
//                    System.err.println("Can be placed at : " + i + " , " + j);
                    placeablePositions[k]= new Point(i,j);
                }else{
                    placeablePositions[k]= new Point(-1, -1);
                }
            }

        return placeablePositions;
    }

    public void showPlaceableLocations(Point[] locations, char player, char opponent) {
        /**
         * TODO: Display the board with valid moves for the player
         * @param locations Array containing placeable locations for the player
         * @param player Current player
         * @param opponent player's opponent
         */

        char[][] placeAbleBoard = new char[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                placeAbleBoard[i][j]=this.board[i][j];
            }
        }

        for (Point point : locations) {
            if(point.x != -1)
                placeAbleBoard[point.x][point.y]='*';
        }

        System.out.print("\n ");
        for ( char c: this.boardX ) {
            System.out.print(" " + c);
        }
        System.out.print(" \n");

        int i =  1;
        for ( char[] x: placeAbleBoard) {
            System.out.print(i++ + " ");
            for ( char c: x) {
                System.out.print(c + " ");
            }
            System.out.println();
        }

        System.out.println();

    }

    public void placeMove(Point point, char player, char opponent) {
        /**
         * TODO: Place a piece on the board at the location by the point p and update the board accordingly
         * @param p Point corresponding to the location of the piece to be placed
         * @param player Current player
         * @param opponent player's opponent
         */
        this.board[point.x][point.y]=player;
        makeInverse(point.x, point.y, player, opponent);

    }

    private void makeInverse(int i, int j, char player, char oplayer) {

        int mi , mj , c;

        //move up
        mi = i - 1;
        mj = j;
        c = 0;
        while(mi>0 && board[mi][mj] == oplayer){
            mi--;
            c++;
        }
        if(mi>=0 && board[mi][mj] == player && c>0) {
            mi = i - 1;
            mj = j;
            c = 0;
            while(mi>0 && board[mi][mj] == oplayer){
                this.board[mi][mj] = player;
                mi--;
            }
        }

        //move down
        mi = i + 1;
        mj = j;
        c = 0;
        while(mi<7 && board[mi][mj] == oplayer){
            mi++;
            c++;
        }
        if(mi<=7 && board[mi][mj] == player && c>0) {
            mi = i + 1;
            mj = j;
            c = 0;
            while(mi<7 && board[mi][mj] == oplayer){
                this.board[mi][mj] = player;
                mi++;
            }
        }

        //move left
        mi = i;
        mj = j - 1;
        c = 0;
        while(mj>0 && board[mi][mj] == oplayer){
            mj--;
            c++;
        }
        if(mj>=0 && board[mi][mj] == player && c>0) {
            mi = i;
            mj = j - 1;
            c = 0;
            while(mj>0 && board[mi][mj] == oplayer){
                this.board[mi][mj] = player;
                mj--;
            }
        }

        //move right
        mi = i;
        mj = j + 1;
        c = 0;
        while(mj<7 && board[mi][mj] == oplayer){
            mj++;
            c++;
        }
        if(mj<=7 && board[mi][mj] == player && c>0) {
            mi = i;
            mj = j + 1;
            c = 0;
            while(mj<7 && board[mi][mj] == oplayer){
                this.board[mi][mj] = player;
                mj++;
            }
        }

        //move up left
        mi = i - 1;
        mj = j - 1;
        c = 0;
        while(mi>0 && mj>0 && board[mi][mj] == oplayer){
            mi--;
            mj--;
            c++;
        }
        if(mi>=0 && mj>=0 && board[mi][mj] == player && c>0) {
            mi = i - 1;
            mj = j - 1;
            c = 0;
            while(mi>0 && mj>0 && board[mi][mj] == oplayer){
                this.board[mi][mj] = player;
                mi--;
                mj--;
            }
        }

        //move up right
        mi = i - 1;
        mj = j + 1;
        c = 0;
        while(mi>0 && mj<7 && board[mi][mj] == oplayer){
            mi--;
            mj++;
            c++;
        }
        if(mi>=0 && mj<=7 && board[mi][mj] == player && c>0) {
            mi = i - 1;
            mj = j + 1;
            c = 0;
            while(mi>0 && mj<7 && board[mi][mj] == oplayer){
                this.board[mi][mj] = player;
                mi--;
                mj++;
            }
        }

        //move down left
        mi = i + 1;
        mj = j - 1;
        c = 0;
        while(mi<7 && mj>0 && board[mi][mj] == oplayer){
            mi++;
            mj--;
            c++;
        }
        if(mi<=7 && mj>=0 && board[mi][mj] == player && c>0) {
            mi = i + 1;
            mj = j - 1;
            c = 0;
            while(mi<7 && mj>0 && board[mi][mj] == oplayer){
                this.board[mi][mj] = player;
                mi++;
                mj--;
            }
        }

        //move down right
        mi = i + 1;
        mj = j + 1;
        c = 0;
        while(mi<7 && mj<7 && board[mi][mj] == oplayer){
            mi++;
            mj++;
            c++;
        }
        if(mi<=7 && mj<=7 && board[mi][mj] == player && c>0) {
            mi = i + 1;
            mj = j + 1;
            c = 0;
            while(mi<7 && mj<7 && board[mi][mj] == oplayer){
                this.board[mi][mj] = player;
                mi++;
                mj++;

            }
        }
    }

    public void updateScores() {
        /**
         * TODO: Update the scores (number of pieces of a player's color) of each player
         */
        this.WScore=0;
        this.BScore=0;

        for ( char[] line: board ) {
            for ( char c : line) {
                if (c == 'W') {
                    this.WScore++;
                } else {
                    if (c == 'B') {
                        this.BScore++;
                    }
                }
            }
        }
        this.remaining = 64-this.WScore-this.BScore;

    }

    public boolean canPlace(char player,int i,int j){

        if(this.board[i][j] != '_') return false;

        int mi , mj , c;
        char oplayer = ((player == 'B') ? 'W' : 'B');

        //move up
        mi = i - 1;
        mj = j;
        c = 0;
        while(mi>0 && board[mi][mj] == oplayer){
            mi--;
            c++;
        }
        if(mi>=0 && board[mi][mj] == player && c>0) return true;


        //move down
        mi = i + 1;
        mj = j;
        c = 0;
        while(mi<7 && board[mi][mj] == oplayer){
            mi++;
            c++;
        }
        if(mi<=7 && board[mi][mj] == player && c>0) return true;

        //move left
        mi = i;
        mj = j - 1;
        c = 0;
        while(mj>0 && board[mi][mj] == oplayer){
            mj--;
            c++;
        }
        if(mj>=0 && board[mi][mj] == player && c>0) return true;

        //move right
        mi = i;
        mj = j + 1;
        c = 0;
        while(mj<7 && board[mi][mj] == oplayer){
            mj++;
            c++;
        }
        if(mj<=7 && board[mi][mj] == player && c>0) return true;

        //move up left
        mi = i - 1;
        mj = j - 1;
        c = 0;
        while(mi>0 && mj>0 && board[mi][mj] == oplayer){
            mi--;
            mj--;
            c++;
        }
        if(mi>=0 && mj>=0 && board[mi][mj] == player && c>0) return true;

        //move up right
        mi = i - 1;
        mj = j + 1;
        c = 0;
        while(mi>0 && mj<7 && board[mi][mj] == oplayer){
            mi--;
            mj++;
            c++;
        }
        if(mi>=0 && mj<=7 && board[mi][mj] == player && c>0) return true;

        //move down left
        mi = i + 1;
        mj = j - 1;
        c = 0;
        while(mi<7 && mj>0 && board[mi][mj] == oplayer){
            mi++;
            mj--;
            c++;
        }
        if(mi<=7 && mj>=0 && board[mi][mj] == player && c>0) return true;

        //move down right
        mi = i + 1;
        mj = j + 1;
        c = 0;
        while(mi<7 && mj<7 && board[mi][mj] == oplayer){
            mi++;
            mj++;
            c++;
        }
        if(mi<=7 && mj<=7 && board[mi][mj] == player && c>0) return true;

        //when all hopes fade away
        return false;
    }

    public static boolean isEmpty(Point[] board) {
        /**
         * TODO: Check whether any valid moves can be played
         * @param board The game board to be checked
         * @return true if there are valid moves; false if there are no valid moves
         */

        for (int i = 0; i < 64; i++) {
            if(board[i].x !=-1 && board[i].y!=-1)
                return false;
        }

        return true;
    }
}
