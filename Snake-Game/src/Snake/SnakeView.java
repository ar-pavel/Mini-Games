package Snake;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class SnakeView extends JFrame {
    public static ArrayList<ArrayList<Square>> board;
    public static int width = 35;
    public static int height = 35;
    public static int noOfSquare = 35;
    private final int boardColor = 2;

    public SnakeView(){
        
        // Initialize the arraylist that'll contain the threads
        board = new ArrayList<>();

        // Creates Threads and it's data and adds it to the arrayList
        for(int i=0;i<width;i++){
            ArrayList<Square> data= new ArrayList<>();
            for(int j=0;j<height;j++){
                Square c = new Square(boardColor);
                data.add(c);
            }
            board.add(data);
        }

        // panel holds the game board
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(noOfSquare,noOfSquare,0,0));

        // Setting up the layout of the panel
//        getContentPane().setLayout(new GridLayout(noOfSquare,noOfSquare,0,0));

        // Start & pauses all threads, then adds every square of each thread to the panel
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
//                getContentPane().add(board.get(i).get(j).square);
                panel.add(board.get(i).get(j).square);
            }
        }

        // initial position of the snake to ~middle
        BoardPoint position = new BoardPoint(20,15);

        // passing this value to the controller
        SnakeApp c = new SnakeApp(position);

        // Add game board to the the frame
        getContentPane().add(panel);

        // Add a status bar that contains the current score of the player
        c.statusBar = new StatusBar();
        getContentPane().add(c.statusBar, java.awt.BorderLayout.SOUTH);

        // start the game
        c.start();

        // Links the window to the keyboard
        this.addKeyListener(new MoveReader());

    }
}