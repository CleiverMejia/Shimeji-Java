package view;

import enums.Action;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import util.Sprite;

public class Ski extends JLabel {

    private float x = 0;
    private float hSpeed = 0;
    private int hLim = 0;
    private float y = 0;
    private float vSpeed = 0;
    private final float vLim = 10;
    private int width = 0;
    private int height = 0;
    private int direction = 1;
    private int randomXPosition = 0;

    private Action action = Action.FALLING;

    public Ski() {
        this.setBackground(new Color(0, 0, 0, 0));
        //this.setBackground(Color.RED);
        this.setOpaque(true);
        setSprite();

        this.addMouseMotionListener(new MouseMotionAdapter() { // Arrastrar
            @Override
            public void mouseDragged(MouseEvent e) {
                Point point = MouseInfo.getPointerInfo().getLocation();
                SwingUtilities.convertPointFromScreen(point, Ski.this.getTopLevelAncestor());

                x = point.x - width / 2;
                y = point.y;
                action = Action.DRAG;
                setSprite();
            }
        });

        this.addMouseListener((new MouseAdapter() { // Soltar
            @Override
            public void mouseReleased(MouseEvent e) {
                Point point = MouseInfo.getPointerInfo().getLocation();
                SwingUtilities.convertPointFromScreen(point, Ski.this.getTopLevelAncestor());

                vSpeed = 0;
                action = Action.FALLING;
                setSprite();
            }
        }));

        initThreads();
    }

    private void initThreads() {
        new Thread(new Step()).start();
        new Thread(new ActionSelect()).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Icon icon = Sprite.get(action);
        Image img = ((ImageIcon) icon).getImage();

        Graphics2D g2 = (Graphics2D) g.create();

        if (direction == -1) {
            g2.scale(-1, 1);
            g2.drawImage(img, -icon.getIconWidth(), 0, null);
        } else {
            g2.drawImage(img, 0, 0, null);
        }

        g2.dispose();
    }

    private void setSprite() {
        Icon icon = Sprite.get(action);

        this.y += this.height - icon.getIconHeight();
        this.width = icon.getIconWidth();
        this.height = icon.getIconHeight();
    }

    private class Step extends Thread {

        @Override
        public void run() {
            new Timer(10, (ActionEvent e) -> {
                switch (action) {
                    case WALK, RUN -> {
                        if (hSpeed < hLim) {
                            hSpeed += .5f;
                        }

                        for (int i = 0; i < hSpeed; i++) {
                            if (Math.signum(randomXPosition - x + direction) != 0) {
                                x += direction;
                                continue;
                            }

                            action = Action.IDLE;
                            setSprite();
                            break;
                        }
                    }
                    case JUMP -> {
                        if (vSpeed < 0) {
                            y += vSpeed;
                            vSpeed += .2f;

                            break;
                        }

                        action = Action.FALLING;
                        setSprite();
                    }
                    case FALLING -> {
                        if (vSpeed < vLim) {
                            vSpeed += .2f;
                        }

                        for (int i = 0; i < vSpeed; i++) {
                            if (y < Main.SCREEN_HEIGHT - height) {
                                y++;
                                continue;
                            }

                            vSpeed = 0;
                            y = Main.SCREEN_HEIGHT - height;
                            action = Action.IDLE;
                            setSprite();
                        }
                    }
                    default -> {
                        if (hSpeed < 0) {
                            hSpeed = 0;
                            break;
                        }

                        hSpeed -= .2f;
                        x += (int) (direction * hSpeed);
                    }
                }

                setBounds((int) x, (int) y, width, height);
            }).start();
        }
    }

    private class ActionSelect extends Thread {

        @Override
        public void run() {
            AtomicInteger timeRandom = new AtomicInteger(10000);
            new Timer(timeRandom.get(), (ActionEvent e) -> {
                int rNumber = (int) (Math.random() * 7);
                Action actionSelected = Action.values()[rNumber];

                if (action == Action.WALK
                        || action == Action.RUN
                        || action == Action.DRAG
                        || action == Action.FALLING
                        || action == Action.JUMP) {
                    return;
                }

                action = actionSelected;

                switch (action) {
                    case WALK, RUN -> {
                        randomXPosition = (int) (Main.SCREEN_WIDTH * Math.random());
                        direction = (int) Math.signum(randomXPosition - x);
                        hLim = actionSelected == Action.WALK ? 4 : 7;
                    }
                    case JUMP ->
                        vSpeed = -8;
                    default -> {
                    }
                }

                setSprite();

                timeRandom.set((int) (Math.random() * 10000));
                ((Timer) e.getSource()).setDelay(timeRandom.get());

                System.out.println(actionSelected);
            }).start();
        }
    }
}
