package graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.event.MouseInputAdapter;

public class SameGameTile extends JComponent {

	// various attributes needed to draw the tile
	// should be self-explanatory
	private static final long serialVersionUID = 2516851011912978902L;
	private int height;
	private int width;
	private int arc;
	private SameGameBoard board;
	private Color color;
	
	private int row;
	private int col;
	
	public SameGameTile(int x, int y, int width, int height, int arc, Color color, SameGameBoard board, int r, int c) {
		
		
		this.width = width;
		this.height = height;
		this.arc = arc;
		this.color = color;
		this.board = board;

		// this determines where the tile is drawn
		this.setBounds(x,y,width,height);
		
		this.row = r;
		this.col = c;

		// add a behaviour to the tile when clicked
		addMouseListeners();
	}
	
	public void paintComponent(final Graphics g) {
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		super.paintComponent(g);
		g.setColor(color);
		g.fillRoundRect(0,0,width,height,arc,arc);
	}
	
	// add behaviour to each tile when clicked (we use mouseReleased since this is actually
	// better at detecting clicks
	public void addMouseListeners() {
		
		this.addMouseListener(new MouseInputAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// check if the clicked tile is a valid tile to remove, and remove it if it is
				if (board.game.isValidIndex(row, col) && board.game.isValidMove(row,col)){
					board.game.removeTile(row, col);
					board.repaint();
				}
				// if there are no more valid moves, then calculate the score and display it
				if (board.game.noMoreValidMoves()) {
					board.score = board.game.calculateScore();
					board.gameFinished = true;
				}

			}
		});
	}
	
}
