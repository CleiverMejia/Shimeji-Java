package view;

import enums.Action;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import util.FrameGroup;
import util.Sprite;
import view.ski.ActionSeleted;
import view.ski.Step;

public class Ski extends Rectangle {

    // Movement
    public final AtomicInteger direction = new AtomicInteger(1);
    public final AtomicInteger randomXPosition = new AtomicInteger(0);

    // Animation
    public final AtomicReference<Action> action = new AtomicReference<>(Action.JUMP);
    public int imageIndex = 0;
    public int spriteIndex = 0;
    public FrameGroup groupFrame = Sprite.get(action.get());
    public BufferedImage frame = groupFrame.getFrame(imageIndex);

    // Horizontal movement
    public float hSpeed = 0;
    public int hLim = 3;

    // Vertical movement
    public float vSpeed = 0;
    public final float VLIM = 10f;

    // Threads
    private final Step step = new Step(this);
    private final ActionSeleted actionSeleted = new ActionSeleted(this);

    // Actions
    public boolean dragged = false;
    public boolean isColliding = false;

    // Hitbox
    protected final int hitboxW = 13;
    protected final int hitboxH = 23;

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
        width = (int) (hitboxW * Config.scale);
        height = (int) (hitboxH * Config.scale);

        switch (randomStart) {
            case 0 -> {
                x = 50;
                y = 0;
                hSpeed = 0f;
                vSpeed = 0f;

                setAction(Action.FALLING);
            }
            default -> {
                x = -70;
                y = Main.SCREEN_HEIGHT - height;
                hSpeed = 3f;
                vSpeed = -8f;

                setAction(Action.JUMP);
            }
        }
    }

    public void setAction(Action action) {
        this.action.set(action);
        setSprite();
    }

    public void setSprite() {
        this.imageIndex = 0;
        this.spriteIndex = 0;
        this.groupFrame = Sprite.get(action.get());
        this.frame = groupFrame.getFrame(imageIndex);
    }
}
