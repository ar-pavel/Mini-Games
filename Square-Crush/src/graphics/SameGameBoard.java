package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import samegame.*;

/*
 * The following class is a graphical interface for the SameGame board
 * 
 * If you like, you can play around with this file, as long as it doesn't interfere
 * with the game logic in SameGame.java.
 * 
 * I did not comment the code extensively because you are not required to understand
 * how this class works. 
 */

public class SameGameBoard extends JPanel{
	
	private static final long serialVersionUID = -8522390763634730320L;
	
	// the game we are displaying
	protected SameGame game;
	// the board for our game
	private ArrayList<ArrayList<Tile>> board;
	
	// variable used to check if the game has finished 
	protected boolean gameFinished;
	// variable to keep track of the score (displayed when the game is finished
	protected int score;
	
	
	// constructor
	public SameGameBoard(long seed, int rows, int columns){
		this.setPreferredSize(new Dimension(1024,768));
		this.setBackground(Color.decode(ColorDef.LIGHTGRAY));

		game = new SameGame(new Random(seed),rows,columns);
		gameFinished = false;
	}
	
	// the method called to draw the board
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int startX = 34;
		int startY = 708;
		
		// to draw the tiles, we get the state of the board (i.e. ArrayList<Tile> 
		// from the game and then draw the tiles in the ArrayList one by one

		// remove everything on the panel (wipe the screen basically)
		this.removeAll();
		board = game.getBoard();
		for(int i = 0; i < board.size(); i++) {
			for(int j = 0; j < board.get(i).size(); j++) {

				if (board.get(i).get(j) != null) {
					SameGameTile temp = new SameGameTile(startX + j * 44, startY - i * 44, 40, 40, 8, board.get(i).get(j).getColor(),this,i,j);
					this.add(temp);
					
				}
			}
		}
		// if the game is finished, display the final score
		if (gameFinished) {
			JLabel score = new JLabel("Final Score: " + this.score);
			score.setBounds(20,20,400,100);
			score.setForeground(Color.decode(ColorDef.DARKGRAY));
			score.setFont(new Font("SansSerif", Font.BOLD, 24));
			this.add(score);
		}
	}
	
	
	// This is the application's entry point. You can change the call to the SameGameBoard constructor
	// to change the game settings (change seed, change row/column numbers)
	public static void main(String[] args) {
	
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame root = new JFrame("SameGame");
				root.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				// use this if you want a completely random game
				//SameGameBoard b = new SameGameBoard(System.currentTimeMillis(),16,22);

				// here are a few other options to start the game
				SameGameBoard b = new SameGameBoard(10,16,22);
				//SameGameBoard b = new SameGameBoard(10,6,6);
				//SameGameBoard b = new SameGameBoard(20,10,10);

				b.repaint();
				root.add(b);
				root.setContentPane(b);
				root.pack();
				root.setVisible(true);
				
			}
		});
	}
}
