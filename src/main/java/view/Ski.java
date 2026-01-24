package view;

import enums.Action;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import util.FrameGroup;
import util.Sprite;
import view.ski.ActionSeleted;
import view.ski.Step;

public class Ski extends Rectangle {

    // Movement
    public byte direction = 1;
    public short randomXPosition = 0;

    // Animation
    public Action action = Action.FALLING;
    public int imageIndex = 0;
    public int spriteIndex = 0;
    public FrameGroup groupFrame = Sprite.get(action);
    public BufferedImage frame = groupFrame.getFrame(imageIndex);

    // Horizontal movement
    public float hSpeed = 3f;
    public byte hLim = 3;

    // Vertical movement
    public float vSpeed = -8f;
    public final float VLIM = 10f;

    // Threads
    private final Step step = new Step(this);
    private final ActionSeleted actionSeleted = new ActionSeleted(this);

    public boolean dragged = false;

    public Ski() {
        initThreads();
    }

    private void initThreads() {
        init();
        step.start();
        actionSeleted.start();
    }

    private void init() {
        int randomStart = (int) (Math.random() * 2);
        width = (int) (13 * Config.scale);
        height = (int) (23 * Config.scale);

        setSprite();
        switch (randomStart) {
            case 0 -> {
                x = 50;
                y = 0;
                hSpeed = 0f;
                vSpeed = 0f;

                action = Action.FALLING;
            }
            default -> {
                x = -70;
                y = Main.SCREEN_HEIGHT - height;
            }
        }
    }

    public void setAction(Action action) {
        this.action = action;
        setSprite();
    }

    public void setSprite() {
        this.imageIndex = 0;
        this.spriteIndex = 0;
        this.groupFrame = Sprite.get(action);
        this.frame = groupFrame.getFrame(imageIndex);
    }
}
