package view;

import java.awt.Color;
import javax.swing.JPanel;

public class Config extends JPanel {
    private final int width = 100;
    private final int height = 200;

    public Config() {
        this.setVisible(false);
        this.setBounds(Main.SCREEN_WIDTH/2 - width/2, Main.SCREEN_HEIGHT/2 - height/2, width, height);
        this.setBackground(Color.BLUE);
    }
}
