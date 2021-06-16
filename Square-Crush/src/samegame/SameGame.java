package samegame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import graphics.ColorDef;

public class SameGame {
	
	// the board variable needs to be public so that
	// it can be accessed by the JUnit test. Please
	// do not modify its visibility.
	
	public ArrayList<ArrayList<Tile>> board;
	
	private int ROW;
	private int COL;
	private int[][] moves = {{1,0},{-1,0},{0,1},{0,-1}};

	/**
	 * 5 marks (Pass level)
	 * 
	 * Constructor: create a board of size rows x columns
	 * containing Tiles of different colours using the 
	 * random number generator as discussed in the assignment 
	 * specification.
	 * 
	 * Both rows and columns are going to be an integer > 0 and <= 1000.
	 * You can assume that rgen is going to be a valid Random object 
	 * (rgen won't be null)
	 * 
	 * You can look at the constructor for SimpleSameGame to get
	 * some ideas. To construct a Tile with a random colour, you
	 * can use.
	 * 
	 * Tile random = new Tile(colors.get(rgen.nextInt(colors.size())),1);
	 * 
	 * Remember that row 0 is the bottom-most row. 
	 * 
	 * @param rgen	  - the random number generator (non null)
	 * @param rows    - the number of rows on the board, 0 < rows <= 1000
	 * @param columns - the number of columns on the board, 0 < columns <= 1000
	 */
	public SameGame(Random rgen, int rows, int columns) {
		
		// do not modify the code involving the ArrayList colors 
		ArrayList<Color> colors = new ArrayList<Color>();
		colors.add(Color.decode(ColorDef.BLUE));
		colors.add(Color.decode(ColorDef.PURPLE));
		colors.add(Color.decode(ColorDef.YELLOW));
		colors.add(Color.decode(ColorDef.GREEN));
		
		// start writing your code here
		
		this.ROW = rows;
		this.COL = columns;
		
		board = new ArrayList<ArrayList<Tile>>();
		for(int i=0; i<rows; ++i)
			board.add(new ArrayList<>());

//		for(int i=rows-1; i>=0; --i){
		for(int i=0; i<rows; ++i) {
			// generates a row of tiles
			for (int j = 0; j < columns; j++) {
//				board.get(i).add(new Tile(colors.get(rgen.nextInt(colors.size())),1));
				Tile random = new Tile(colors.get(rgen.nextInt(colors.size())), 1);
				this.board.get(i).add(j, random); // fill the board with random Tile object
			}

		}
	}
	
	/**
	 * 5 marks (Pass level)
	 * 
	 * Computes the score obtained by the user at the end of the
	 * game. The score for the game is the number of tiles that 
	 * the player manages to remove, so if the player removes 10
	 * tiles, then the player should receive a score of 10.
	 * 
	 * The ideal way to complete this method is by completing the 
	 * removeTile() method (e.g. by adding a point for each tile removed),
	 * but you should be able to obtain full marks even if you cannot
	 * implement removeTile() (see the JUnit test)
	 * 
	 * @return the score obtained by the user
	 */
	public int calculateScore() {

		// to be completed
		int res=ROW*COL;
		for(int r=board.size()-1; r>=0; --r) {
			for(int col=0; col<board.get(r).size(); ++col) {
				
				if(board.get(r).get(col) == null)
					continue;
					res -= 1;
			}
		}
		return res;
	}
	
	/**
	 * 5 marks (Pass level)
	 * 
	 * Returns an instance copy of the board. Do not return a reference
	 * copy since the calling method may modify the returned ArrayList.
	 * 
	 * @return an instance copy of the board
	 */
	public ArrayList<ArrayList<Tile>> getBoard() {
		// to be completed
//		 ArrayList returnBoard = new ArrayList(board);
		ArrayList<ArrayList<Tile>> returnBoard = new ArrayList<>();
//		return  (ArrayList<ArrayList<Tile>>) board.clone();
		for (ArrayList<Tile> list: board) {
			returnBoard.add(new ArrayList<>(list));
		}
		return returnBoard;
	}
	
	/**
	 * 5 marks (Pass level)
	 * 
	 * Checks if row i column j is a valid index, that is, if there is a
	 * tile on that location. Return false if i and j does not
	 * correspond to a valid index, or if a null value is found at
	 * that location.
	 * 
	 * @param i - the row index
	 * @param j - the column index
	 * @return true if row i column j is a valid location that contains a Tile, 
	 * false otherwise
	 */
	public boolean isValidIndex(int i, int j) {
		// to be completed
		return ((i<board.size() && i>=0) && (j<board.get(0).size() && j>=0));
	}

	
	/**
	 * 10 marks (Pass level)
	 * 
	 * Checks if it is legal to remove a tile on row i column j. A tile
	 * can be removed if there is an adjacent tile with the same colour
	 * (please see the assignment specification for more information). 
	 * Return false if the location given is invalid. 
	 * 
	 * @param i - the row index
	 * @param j - the column index
	 * @return true if it is legal to remove the tile at row i column j, 
	 * false otherwise
	 */
	public boolean isValidMove(int i, int j) {
		
		// to be completed
		if(isValidIndex(i, j) && board.get(i).get(j) != null){
			for (int k = 0; k < 4; k++) {
				int x = i+moves[k][0];
				int y = j+moves[k][1];

				if(isValidIndex(x, y) &&
					board.get(x).get(y).getColor().equals(board.get(i).get(j).getColor())) {
					return true;
				}
			}
		}
		return false;
		
	}
	
	/**
	 * 5 marks (Pass level)
	 * 
	 * Checks if the player has run out of moves, that is, if it is not possible
	 * to remove any more tiles from the board. Return true if there are no more
	 * tiles on the board.
	 * 
	 * @return true if there are no more valid moves, false otherwise
	 */
	public boolean noMoreValidMoves() {
		
		// to be completed
		for (int r = board.size()-1; r >=0; r--) {
			for (int c=board.get(r).size()-1; c>=0; --c){
				if(isValidMove(r, c))
					return false;
			}
		}
		return true;
	}
	
	/**
	 * 5 marks (Credit Level)
	 * 
	 * Imagine two rows of Tiles, 'bottom' and 'top', with 'top' placed on top of 'bottom'.
	 * 
	 * This method rearranges the two rows such that if there is an empty (i.e. null)
	 * slot in the 'bottom' row and there is a tile at the same index in the 'top' row, 
	 * then it will move the tile down to 'bottom' row (effectively you are copying
	 * the tile into 'bottom', and set the index to null in 'top').
	 * You are basically moving tiles downward, but only between two rows.
	 * 
	 * Don't do anything if the sizes of the two ArrayLists are not the same,
	 * or if one of them is null
	 * 
	 * @param bottom - the bottom row of Tiles
	 * @param top    - the top row of Tiles
	 */
	public void trickleDown(ArrayList<Tile> bottom, ArrayList<Tile> top) {
		
		// to be completed
		// if the size of the two array are not same or one of them is null
		
		if(bottom == null) {
			//System.out.println("Bottom is null");
			return;
		}
		if(top == null) {
			//System.out.println("Top is null");
			return;
		}
		
		if(bottom.size() == top.size()) {

			// if a cell is empty replace it by the one top of it
			for(int col = 0; col < bottom.size(); ++col) {
				if(bottom.get(col)==null){
					bottom.set(col, top.get(col));
					top.set(col, null);
				}
			}

		}

	}

	private void printBoard(){
		System.out.println();
		for(int i=0; i<board.size(); ++i) {
			for(int j=0; j<board.get(i).size(); ++j)
				System.out.print(board.get(i).get(j)+ "\t");
			System.out.println();
		}
		System.out.println();
	}


	/** 5 marks (Credit Level)
	 * 
     * Rearranges the board so that all tiles are moved downwards and all empty columns are
     * removed. 
     * 
     * You do not need to complete the deleteEmptyColumns() method to receive full
     * marks for this method as it will not be tested in the JUnit test for this method,
     * that is, the JUnit test for rearrangeBoard() will NOT test if your implementation
     * of rearrangeBoard() removes the empty columns.
     * 
     * However, a proper implementation of rearrangeBoard() SHOULD call deleteEmptyColumns()
     * (after you move the tiles downwards), so if you manage to implement 
     * deleteEmptyColumns(), you need to call that method in rearrangeBoard().
     * 
     * Hint: use the trickleDown() method (although you can also do it without
     * calling trickleDown(), but calling trickleDown() will make the code simpler,
     * albeit less efficient).
	 * 
	 */
	public void rearrangeBoard() {

		// to be completed
//		printBoard();

		for(int k=0; k<board.size(); ++k)
			for(int r = board.size()-1; r>0; --r) {
				trickleDown(board.get(r-1), board.get(r));
			}
		
		// delete empty columns
		deleteEmptyColumns();
//		System.out.println("after rearranging : ");
//		printBoard();
	}
	
	/** 10 marks (Distinction Level)
	 * 
     * The following method removes all empty columns in the board. 
     * You MUST PASS testRearrangeBoard() before attempting deleteEmptyColumns, 
     * because the JUnit test will NOT directly test deleteEmptyColumns().
     * The JUnit test will call rearrangeBoard(), assuming that you have
     * implemented deleteEmptyColumns() properly and call it from rearrangeBoard().
	 * 
	 */
	public void deleteEmptyColumns() {

		// to be completed
		
		if(board.isEmpty())
			return;
		
	//	printBoard();

			for(int col=0; col<board.get(0).size(); ++col){
				boolean isNull = true;
			//	System.out.println(board.size());
				for (int row = 0; row < board.size(); row++) {

				//	System.out.println("checking : " + row + " , " + col + ", status : " + board.get(row).get(col));

					if(!isValidIndex(row, col))
						continue;

					if( board.get(row).get(col)== (null))
						continue;

					isNull = false;
					break;

				}

				// if current column is empty shift the columns left by one cell
				if(isNull){
					for(int r=board.size()-1; r>=0; --r){
						board.get(r).remove(col);
					}
					--col;
				}
			}

		//printBoard();

	}
	
	/** 16 marks (High Distinction Level)
	 * 
	 * The following method removes a tile at row i column j from the board
	 * and all adjacent tiles of the same colour, as per the game rules
	 * (as discussed in the assignment specification).
	 * 
	 * The JUnit test suite will include one test that tries a large board
	 * (1000 x 1000) --- see testRemoveTileLarge. The removeTile method
	 * must finish within 10 seconds for you to pass this test (it is
	 * worth 5 marks)
	 * 
	 * Do nothing if the given row and column index is not valid, or if it
	 * is not legal to move the tile. 
	 * 
	 * @param i  -  the row index of the tile to be removed
	 * @param j  -  the column index of the tile to be removed
	 */
	public void removeTile(int i, int j) {

		// to be completed

//		i = board.size()-1-i;
	//	System.out.println("Call for : x : " + i + ", y : " + j);
		if(isValidIndex(i,j) && board.get(i).get(j) != null &&  isValidMove(i,j)){
	//		System.out.println("DFS for : x : " + i + ", y : " + j);
			dfs(i,j, board.get(i).get(j));
//			board.get(i).set(j, null);

			rearrangeBoard();
		}

	}

	void dfs(int i, int j, Tile tile){
		if(isValidIndex(i, j)){

			//	System.out.println("i : "  + i + ", j : " + j + ", color : " + tile.getColor().equals(board.get(i).get(j).getColor()));

			// check for all valid moves
			for (int k = 0; k < 4; k++) {
//				board.get(i).set(j, null);
				int x = i+moves[k][0];
				int y = j+moves[k][1];

//				System.out.println("x : "  + x + ", y " + y);
				if(!isValidIndex(x,y)) {
					continue;
				}
				if( board.get(x).get(y) == null) {
					continue;
				}
//				System.out.println("x : "  + x + ", y " + y + ", color : " + board.get(x).get(y));

				if(tile.equals(board.get(x).get(y))){
					board.get(x).set(y, null);
					dfs(x,y, tile);
				}
			}

		}

	}
}
