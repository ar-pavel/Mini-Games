package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

import samegame.*;

/*
 * The following class is a graphical interface for the simple version
 * of SameGame.
 * 
 * If you like, you can play around with this file, as long as it doesn't interfere
 * with the game logic in SameGame.java.
 * 
 * I did not comment the code extensively because you are not required to understand
 * how this class works. 
 */

public class SimpleSameGameBoard extends JPanel{
	
	// You can ignore the following variable (it is used for serialization)
	private static final long serialVersionUID = 985903368434308429L;

	
	// The board
	private ArrayList<Tile> board;
	
	
	// constructor
	public SimpleSameGameBoard(long seed, int size){
		this.setPreferredSize(new Dimension(1024,768));
		this.setBackground(Color.decode(ColorDef.LIGHTGRAY));

		// create a new game with a fixed size and a random seed
		game = new SimpleSameGame(new Random(seed),size);
	}
	
	// the method to draw the board
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		int startX = 34;
		// to draw the tiles, we get the state of the board (i.e. ArrayList<Tile> 
		// from the game and then draw the tiles in the ArrayList one by one
		board = game.getBoard();

		// remove everything on the panel (wipe the screen basically)
		this.removeAll();
		for(int i = 0; i < board.size(); i++) {
			if (board.get(i) != null) {
				// create a tile and add it to the panel
				SimpleSameGameTile temp = new SimpleSameGameTile(startX + i * 44, 200, 40, 40, 8, board.get(i).getColor(),this,i);
				this.add(temp);
			}
		}
	}
	
	// getter for the game 
	protected SimpleSameGame getGame() {
		return game;
	}
	
	// This is the application's entry point. You can change the call to the SameGameBoard constructor
	// to change the game settings (change seed, change row/column numbers)
	public static void main(String[] args) {
	
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame root = new JFrame("SimpleSameGame");
				root.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				SimpleSameGameBoard b = new SimpleSameGameBoard(10,22);
				b.repaint();
				root.add(b);
				root.setContentPane(b);
				root.pack();
				root.setVisible(true);
				
			}
		});
	}
}
