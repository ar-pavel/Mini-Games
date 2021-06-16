package Snake;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static java.awt.Color.*;
import static java.awt.Color.cyan;

public class Square {

    //Array that contains the colors
    static Color[] C = {green, cyan,  blue, red, yellow, magenta, pink, black};

    int color;

    SquarePanel square;
    public Square(int col){
        color=col;
        square = new SquarePanel(C[col]);
    }
    public void lightMeUp(int c){
        square.ChangeColor(C[c]);
    }
}

class MoveReader extends KeyAdapter {
    public void keyPressed(KeyEvent event){
        switch(event.getKeyCode()){
            case 39:	                            // -> move to  Right
                //if it's not the opposite direction
                if(SnakeApp.snakeCurDirection!=2)
                    SnakeApp.snakeCurDirection=1;
                break;
            case 38:	                            // -> move to  Top
                if(SnakeApp.snakeCurDirection!=4)
                    SnakeApp.snakeCurDirection=3;
                break;

            case 37: 	                            // -> move to  Left
                if(SnakeApp.snakeCurDirection!=1)
                    SnakeApp.snakeCurDirection=2;
                break;

            case 40:	                            // -> move to Bottom
                if(SnakeApp.snakeCurDirection!=3)
                    SnakeApp.snakeCurDirection=4;
                break;

            default: 	break;
        }
    }
}