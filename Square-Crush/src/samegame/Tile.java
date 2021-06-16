package samegame;

import java.awt.Color;

import graphics.ColorDef;

/*
 *  Class used to represent a Tile in a game. 
 *  
 *  The only method of importance here is 'equals', which you should
 *  use in comparing two different Tiles (it checks if they have
 *  the same colour).
 *  
 *  
 *  The score attribute is not needed for the assignment, although
 *  you can use it if you want (e.g. for calculateScore()). 
 *  
 *  I also implemented the toString method, and that may make it easier
 *  for you to debug your code (basically, if you print out a Tile, 
 *  you'll get its colour).
 *  
 */

public class Tile {

	// the colour of the tile
	private Color color;
	
	// the score for the tile
	private int score;
	
	// constructor
	public Tile(Color color, int score) {
		this.color = color;
		this.score = score;
	}
	
	public boolean equals(Tile other) {
		if (other == null)
			return false;
		return this.color.equals(other.color);
	}
	
	// getters and setters 
	public Color getColor() {
		return color;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public String toString() {
		if (this.color.equals(Color.decode(ColorDef.BLUE)))
			return "blue";
		if (this.color.equals(Color.decode(ColorDef.PURPLE)))
			return "purple";
		if (this.color.equals(Color.decode(ColorDef.YELLOW)))
			return "yellow";
		if (this.color.equals(Color.decode(ColorDef.GREEN)))
			return "green";
		return color.toString();
	}
}
