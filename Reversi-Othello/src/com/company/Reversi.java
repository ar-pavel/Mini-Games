package com.company;

import java.util.Scanner;

public class Reversi {

    /**
     All the Print Statements needed are given here:
     String Draw = "It is a draw.";
     String WhiteWins ="White wins: " + g.WScore + ":" + g.BScore;
     String BlackWins = "Black wins: " + g.BScore + ":" + g.WScore;
     String Exit = "Exiting!";

     String BlackMove = "Place move (Black): row then column: ";
     String BlackSkipping = "Black needs to skip... Passing to white";
     String InvalidBlackMove = "Invalid move!\nPlace move (Black): row then column: ";
     String BlackScoreShow = "Black: " + g.BScore + " White: " + g.WScore;

     String WhiteSkipping = "White needs to skip... Passing to Black";
     String WhiteMove = "Place move (White): row then column: ";
     String InvalidWhiteMove = "Invalid move!\nPlace move (White): row then column: ";
     String WhiteScoreShow = "White: " + g.WScore + " Black: " + g.BScore;

     **/
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

    public static boolean contains(Point[] board, Point point) {
        /**
         * TODO: Check whether the board already contains a point corresponding to a previous move
         * @param board The game board to be checked
         * @param p The point to be checked for validity
         * @return true if the board contains the point; false if the board does not contain the point
         */

        for (int i = 0; i < 64; i++) {
            if(board[i].x == point.x && board[i].y==point.y)
                return true;
        }
        return  false;
//        return Arrays.asList(board).contains(point);

    }

    public static void start(Game game) {
        /**
         * TODO: Handle input corresponding to the sequence of moves entered by the user
         * TODO: Check the validity of the move, update the board, and print out the updated board after each move
         * TODO: Determine and print out the result after the game is completed
         * @param g The Reversi game currently in play
         */
        Scanner sc = new Scanner(System.in);

        String input;

        char player = 'B';
        char opponent = 'W';
        int x;
        int y;
        while (true) {

            int res = game.gameResult(game.getPlaceableLocations('B', 'W'), game.getPlaceableLocations('W', 'B'));
            if (res != 5) {
                if(res==1){
                    System.out.println("Black wins: " + game.BScore + ":" + game.WScore);
                }else if(res==-1){
                    System.out.println("White wins: " + game.WScore + ":" + game.BScore);
                }else{
                    System.out.println("It is a draw.");
                }
//                System.exit(0);
                return;
            }

            Point[] placeableLocations = game.getPlaceableLocations(player, opponent);

            if (!isEmpty(placeableLocations)) {
                game.showPlaceableLocations(placeableLocations, player, opponent);

                System.out.println("Place move (" + (player=='B'?"Black":"White") + "): row then column: ");
//                x = sc.nextInt();
//                y = sc.nextInt();
                input = sc.next();
                if(input.equals("exit")){
                    System.out.println("Exiting!");
                    return;
                }
//                System.out.println("Debug!");
                x = Integer.parseInt(String.valueOf(input.charAt(0)));
                y = Integer.parseInt(String.valueOf(input.charAt(1)));
                --x; --y;
//                System.out.println(" " + x + " " + y);
                while (!contains(placeableLocations, new Point(x, y))) {
                    System.out.println("Invalid move!\nPlace move (" +  (player=='B'?"Black":"White") + "): row then column: ");
//                    x = sc.nextInt();
//                    y = sc.nextInt();
                    input = sc.next();
                    if(input.equals("exit")){
                        System.out.println("Exiting!");
                       return;
                    }
                    x = Integer.parseInt(String.valueOf(input.charAt(0)));
                    y = Integer.parseInt(String.valueOf(input.charAt(1)));

                    --x; --y;
                }

                game.placeMove(new Point(x, y), player, opponent);
//                game.displayBoard();
                game.updateScores();

                if(player=='B'){
                    System.out.println("Black: " + game.BScore + " White: " + game.WScore);
                }else{
                    System.out.println("White: " + game.WScore + " Black: " + game.BScore );
                }

            } else {
                System.out.println( (player=='B'?"Black":"White") + " needs to skip... Passing to " +  (opponent=='B'?"Black":"White"));
            }
            player = player == 'B' ? 'W' : 'B';
            opponent = player == 'B' ? 'W' : 'B';

        }

    }

    public static void main(String[] args) {
        Game game = new Game();
        start(game);
    }
}