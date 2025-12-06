package view;

import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JFrame {

    private static final Rectangle screenSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
    public static final int SCREEN_WIDTH = screenSize.width;
    public static final int SCREEN_HEIGHT = screenSize.height;

    public void run() {
        JPanel containerPanel = new JPanel();

        containerPanel.setLayout(null);
        containerPanel.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        containerPanel.setBackground(Color.WHITE);
        containerPanel.setOpaque(false);

        containerPanel.add(new Ski());
        containerPanel.add(new Ski());
        containerPanel.add(new Ski());

        this.add(containerPanel);

        this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setUndecorated(true);
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        this.setLayout(null);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setVisible(true);
    }
}
