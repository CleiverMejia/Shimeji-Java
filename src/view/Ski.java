package view;

import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import enums.Action;
import util.Sprite;

public class Ski extends JLabel {

    private float x = 0;
    private float hSpeed = 0;
    private int hLim = 0;
    private float y = 0;
    private float vSpeed = 0;
    private float vLim = 10;
    private int width = 0;
    private int height = 0;
    private int direction = 1;
    private int randomXPosition = 0;

    private Action action = Action.FALLING;
    private Sprite sprite = new Sprite();

    public Ski() {
        //this.setBackground(new Color(0, 0, 0, 0));
        this.setBackground(Color.RED);
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

                action = Action.FALLING;
                setSprite();
            }
        }));

        new Timer(10, (ActionEvent e) -> { // Repeticion
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
                    }
                }
                case JUMP -> {
                    y -= 1000;
                    action = Action.FALLING;
                    setSprite();
                }
                case FALLING -> {
                    if (vSpeed < vLim) {
                        vSpeed += .8f;
                    }

                    for (int i = 0; i < vSpeed; i++) {
                        if (y < Main.HEIGHT - height) {
                            y++;
                            continue;
                        }

                        vSpeed = 0;
                        y = Main.HEIGHT - height;
                        action = Action.IDLE;
                        setSprite();
                    }
                }
                case DRAG -> {}
                default -> {
                    if (hSpeed >= 0) {
                        hSpeed -= .2f;
                    } else {
                        hSpeed = 0;
                    }

                    for (int i = 0; i < hSpeed; i++) {
                        x += direction;
                    }
                }
            }

            this.setBounds((int) x, (int) y, width, height);
        }).start();

        AtomicInteger timeRandom = new AtomicInteger(10000);
        new Timer(timeRandom.get(), (ActionEvent e) -> {
            int rNumber = (int) (Math.random() * 7);
            Action actionSelected = Action.values()[rNumber];

            if (action == Action.WALK || action == Action.RUN || action == Action.DRAG || action == Action.FALLING) {
                return;
            }

            action = actionSelected;

            switch (action) {
                case IDLE ->
                    setSprite();
                case WALK, RUN -> {
                    randomXPosition = (int) (Main.WIDTH * Math.random());
                    direction = (int) Math.signum(randomXPosition - x);
                    hLim = actionSelected == Action.WALK ? 3 : 7;

                    setSprite();
                }
                case SIT -> setSprite();
                case UP -> setSprite();
                case DOWN -> setSprite();
                default -> {
                }
            }

            timeRandom.set((int) (Math.random() * 10000));
            ((Timer) e.getSource()).setDelay(timeRandom.get());

            System.out.println(actionSelected);
        }).start();
    }

    private Icon flipHorizontal(Icon icon) {
        int w = icon.getIconWidth();
        int h = icon.getIconHeight();

        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        icon.paintIcon(null, img.getGraphics(), 0, 0);

        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-w, 0);
        //tx.rotate(Math.toRadians(rotate), w/2f, h/2f);

        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        BufferedImage flipped = op.filter(img, null);

        return new ImageIcon(flipped);
    }

    private void setSprite() {
        Icon icon = sprite.get(action);

        if (this.direction == -1) {
            icon = flipHorizontal(icon);
        }

        this.setIcon(icon);
        this.y += this.height - icon.getIconHeight();
        this.width = icon.getIconWidth();
        this.height = icon.getIconHeight();
    }
}
