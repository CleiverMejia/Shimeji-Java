package view;

import enums.Action;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import util.FrameGroup;
import util.Sprite;

public class Ski extends JLabel {

    // Movement
    private int direction = 1;
    private int randomXPosition = 0;

    // Sprite
    private int width = 0;
    private int height = 0;

    // Animation
    private Action action = Action.JUMP;
    private int imageIndex = 0;
    private int spriteIndex = 0;
    private FrameGroup groupFrame = Sprite.get(action);
    private BufferedImage frame = groupFrame.getFrame(imageIndex);
    private final int SCALE = 3;

    // Horizontal movement
    private float x = -70;
    private float hSpeed = 3;
    private int hLim = 3;

    // Vertical movement
    private float y = Main.SCREEN_HEIGHT - height;
    private float vSpeed = -8;
    private final float vLim = 10;

    // Time
    AtomicInteger timeRandom = new AtomicInteger(10000);

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

                hSpeed = 0;
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
        init();
        new Step().start();
        new ActionSelect().start();
    }

    private void init() {
        int randomStart = (int) (Math.random() * 2);

        switch (randomStart) {
            case 0 -> {
                x = 0;
                y = -height;
                hSpeed = 0;
                vSpeed = 0;

                action = Action.FALLING;
            }
            default -> {
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        if (direction == -1) {
            g2.scale(-SCALE, SCALE);
            g2.drawImage(frame, -frame.getWidth(), 0, null);
        } else {
            g2.scale(SCALE, SCALE);
            g2.drawImage(frame, 0, 0, null);
        }

        if (spriteIndex > groupFrame.getDuration(imageIndex)) {
            spriteIndex %= groupFrame.getDuration(imageIndex);

            imageIndex++;
            if (groupFrame.isRepeat()) {
                imageIndex %= groupFrame.size();
            } else {
                imageIndex = Math.min(imageIndex, groupFrame.size() - 1);
            }

            frame = groupFrame.getFrame(imageIndex);

            y += height - frame.getHeight() * SCALE;
            width = frame.getWidth() * SCALE;
            height = frame.getHeight() * SCALE;
        }

        g2.dispose();
    }

    private void setSprite() {
        this.imageIndex = 0;
        this.spriteIndex = 0;
        this.groupFrame = Sprite.get(action);
        this.frame = groupFrame.getFrame(imageIndex);

        this.y += this.height - frame.getHeight() * SCALE;
        this.width = frame.getWidth() * SCALE;
        this.height = frame.getHeight() * SCALE;
    }

    private class Step extends Thread {

        @Override
        public void run() {
            new Timer(10, (ActionEvent e) -> {
                boolean isColliding = collision();
                spriteIndex += 10;

                switch (action) {
                    case WALK, RUN -> {
                        if (hSpeed < hLim) {
                            hSpeed += .5f;
                        }

                        for (int i = 0; i < hSpeed; i++) {
                            if (Math.signum(randomXPosition - (x + direction)) != 0) {
                                x += direction;
                                continue;
                            }

                            action = Action.IDLE;
                            setSprite();
                            break;
                        }

                        if (((int) (Math.random() * 50)) == 0) {
                            vSpeed = -6;
                            action = Action.JUMP;
                            setSprite();
                        }
                    }
                    case JUMP -> {
                        if (vSpeed < 0) {
                            x += hSpeed * direction;
                            y += vSpeed;
                            vSpeed += .2f;
                            break;
                        }

                        action = Action.FALLING;
                        setSprite();
                    }
                    case FALLING -> {
                        if (isColliding) {
                            vSpeed = -6;
                            action = Action.JUMP;
                            setSprite();
                            break;
                        }

                        if (vSpeed < vLim) {
                            x += hSpeed * direction;
                            vSpeed += .2f;
                        }

                        for (int i = 0; i < vSpeed; i++) {
                            if (y < Main.SCREEN_HEIGHT - height) {
                                y++;
                                continue;
                            }

                            y = Main.SCREEN_HEIGHT - height;

                            int randomNumber = (int) (Math.random() * 20);
                            action = randomNumber == 0 ? Action.HELPY : Action.IDLE;
                            setSprite();
                        }
                    }
                    default -> {
                        hSpeed -= .2f;
                        if (hSpeed < 0) {
                            hSpeed = 0;
                            break;
                        }

                        x += direction * hSpeed;
                    }
                }

                setBounds((int) x, (int) y, width, height);
                repaint();
            }).start();
        }

        private boolean collision() {
            if (getParent() == null) {
                return false;
            }

            Component[] childrens = getParent().getComponents();

            for (Component component : childrens) {
                if (component instanceof Ski ski && ski != null) {
                    if (ski.getX() == getX() && ski.getY() == getY()) {
                        continue;
                    }

                    if (getBounds().intersects(ski.getBounds())) {
                        int dist = (getY() + getHeight()) - ski.getY();
                        return dist <= 10;
                    }
                }
            }

            return false;
        }
    }

    private class ActionSelect extends Thread {

        @Override
        public void run() {
            new Timer(timeRandom.get(), (ActionEvent e) -> {
                int rNumber = (int) (Math.random() * 7);
                Action actionSelected = Action.values()[rNumber];

                if (action == Action.HELPY
                        && !(actionSelected == Action.JUMP || actionSelected == Action.SIT)) {
                    return;
                }

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
                        hLim = actionSelected == Action.WALK ? 3 : 7;
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
