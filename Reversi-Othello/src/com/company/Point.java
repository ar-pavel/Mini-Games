package com.company;

/**
 * A class that represents a point on the game board.
 *
 * <p>Purdue University -- CS18000 -- Summer 2020</p>
 *
 * @author Purdue CS
 * @version June 15, 2020
 */
public class Point {

    public int x;
    public int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}