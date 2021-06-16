package graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.event.MouseInputAdapter;

/*
 * The following class is a graphical interface for the SimpleSameGame board
 * 
 * If you like, you can play around with this file, as long as it doesn't interfere
 * with the game logic in SameGame.java. 
 */

public class SimpleSameGameTile extends JComponent {

	// various attributes needed to draw the tile
	// should be self-explanatory
	private static final long serialVersionUID = 2516851011912978902L;
	private int height;
	private int width;
	private int arc;
	private int index;
	private SimpleSameGameBoard board;
	private Color color;
	
	public SimpleSameGameTile(int x, int y, int width, int height, int arc, Color color, SimpleSameGameBoard board, int index) {
		this.width = width;
		this.height = height;
		this.arc = arc;
		this.color = color;
		this.board = board;

		// this determines where the tile is drawn
		this.setBounds(x,y,width,height);
		this.index = index;
		
		// add a behaviour to the tile when clicked
		addMouseListeners();
	}
	
	public void paintComponent(final Graphics g) {
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		super.paintComponent(g);
		g.setColor(color);
		g.fillRoundRect(0,0,width,height,arc,arc);
	}
	
	// adds a behaviour to the tile when an event occurs
	public void addMouseListeners() {
		
		// add a listener for when a mouse is clicked, which listens for an event to
		// happen before executing some code
		this.addMouseListener(new MouseInputAdapter() {
			@Override
			// we use mouseReleased event because it is more accurate in detecting mouse clicks
			public void mouseReleased(MouseEvent e) {
				// if the clicked tile is a valid move, then remove the tile and redraw the baord
				if (board.game.isValidIndex(index) && board.game.isValidMove(index)){
					board.game.removeTile(index);
					board.repaint();
				}
			}
		});
	}
	
}
