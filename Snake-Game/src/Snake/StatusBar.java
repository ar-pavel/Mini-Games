package Snake;

import javax.swing.*;
import java.awt.*;

public class StatusBar extends JLabel {

    public StatusBar() {
        super();
        super.setPreferredSize(new Dimension(700, 20));
        setText("Game is Running!");
    }

    public void setMessage(String message) {
        setText("Current Score : "+message);
    }
}