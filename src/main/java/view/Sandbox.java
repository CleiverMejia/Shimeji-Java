package view;

import enums.Action;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Sandbox extends JPanel {

    private final List<Ski> creatures = new ArrayList<>(
            Arrays.asList(
                    new Ski(),
                    new Ski(),
                    new Ski()
            )
    );

    private boolean oneDragged = false;
    private boolean dragging = false;
    private boolean drop = false;
    private Point mousePos = MouseInfo.getPointerInfo().getLocation();

    private final boolean DEBUG = false;

    public Sandbox() {
        setLayout(null);
        setBounds(0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
        setBackground(Color.WHITE);
        setOpaque(false);

        addMouseMotionListener(new MouseMotionAdapter() { // Arrastrar
            @Override
            public void mouseDragged(MouseEvent e) {
                mousePos = MouseInfo.getPointerInfo().getLocation();
                dragging = true;
                SwingUtilities.convertPointFromScreen(mousePos, Sandbox.this.getTopLevelAncestor());
            }
        });

        addMouseListener(new MouseAdapter() { // Soltar
            @Override
            public void mouseReleased(MouseEvent e) {
                drop = true;
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        AffineTransform at = g2.getTransform();

        for (Ski ski : creatures) {
            AffineTransform at2 = new AffineTransform(at);
            at2.translate(
                    ski.x - ((ski.frame.getWidth() * Config.scale) / 4),
                    ski.y - (ski.frame.getHeight() * Config.scale - ski.height)
            );
            g2.setComposite(AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, Config.opacity / 100f
            ));
            g2.setTransform(at2);

            if (ski.direction == -1) {
                g2.scale(-Config.scale, Config.scale);
                g2.drawImage(ski.frame, -ski.frame.getWidth(), 0, null);
            } else {
                g2.scale(Config.scale, Config.scale);
                g2.drawImage(ski.frame, 0, 0, null);
            }

            if (DEBUG) {
                g2.setTransform(at);
                g2.setColor(new Color(255, 0, 0, 128));
                g2.fillRect(ski.x, ski.y, ski.width, ski.height);

                g2.setColor(new Color(0, 0, 255, 128));
                g2.fillRect(mousePos.x - 10, mousePos.y - 10, 20, 20);
            }

            /* Drag and Drop */
            if (dragging && ski.intersects(mousePos.x - 10, mousePos.y - 10, 20, 20) && !oneDragged) {
                ski.dragged = true;
                oneDragged = true;
            }

            if (ski.dragged) {
                ski.hSpeed = 0;
                ski.x = mousePos.x - ski.width / 2;
                ski.y = mousePos.y;
                ski.setAction(Action.DRAG);
            }

            if (drop && ski.intersects(mousePos.x - 5, mousePos.y - 5, 50, 50)) {
                ski.vSpeed = 0;
                ski.setAction(Action.FALLING);

                dragging = false;
                oneDragged = false;
                ski.dragged = false;
            }
        }

        drop = false;

        repaint();
        g2.dispose();
    }
}
