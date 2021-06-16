package Snake;

import javax.swing.*;

public class GameRunner {

    // Developer Info
    private static final String NAME = "John Doe";
    private static final String ID = "XZ362436";

    public static void main(String[] args) {

        // Create a new instance of the game
        SnakeView f1= new SnakeView();

        //Setting up the window settings
        f1.setTitle("Jumbo Snake");
        f1.setSize(700,700);
        f1.setVisible(true);
        f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}