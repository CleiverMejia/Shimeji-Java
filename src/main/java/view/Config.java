package view;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class Config extends JPanel {
    private final int width = 200;
    private final int height = 500;
    public static float scale = 3;
    public static int opacity = 100;

    public Config() {
        JLabel scaleLabel = new JLabel("Escalado:");
        JSlider scaleSlider = new JSlider(1, 500, 300);
        JLabel opacityLabel = new JLabel("Opacidad:");
        JSlider opacitySlider = new JSlider(0, 100, 100);
        JButton close = new JButton("X");

        close.addActionListener(l -> {
            Config.this.setVisible(false);
        });

        scaleSlider.setMajorTickSpacing(1);
        scaleSlider.addChangeListener(e -> {
            Config.scale = scaleSlider.getValue()/100f;
        });

        scaleSlider.setMajorTickSpacing(5);
        scaleSlider.addChangeListener(e -> {
            Config.opacity = opacitySlider.getValue();
        });

        this.add(scaleLabel);
        this.add(scaleSlider);
        this.add(opacityLabel);
        this.add(opacitySlider);
        this.add(close);

        this.setVisible(false);
        this.setBounds(Main.SCREEN_WIDTH/2 - width/2, Main.SCREEN_HEIGHT/2 - height/2, width, height);
        this.setBackground(Color.GRAY);
    }
}
