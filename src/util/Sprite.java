package util;

import java.awt.Image;
import java.util.Map;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import enums.Action;

public class Sprite {

    private static final String URL_BASE = "src/assets/Ski_";
    private static final int SCALE = 4;

    private static final ImageIcon idleIcon = new ImageIcon(URL_BASE + "idle.png");
    private static final ImageIcon walkIcon = new ImageIcon(URL_BASE + "walk.gif");
    private static final ImageIcon runIcon = new ImageIcon(URL_BASE + "run.gif");
    private static final ImageIcon jumpIcon = new ImageIcon(URL_BASE + "jump.png");
    private static final ImageIcon dragIcon = new ImageIcon(URL_BASE + "drag.png");
    private static final ImageIcon fallingIcon = new ImageIcon(URL_BASE + "jump.png");
    private static final ImageIcon upIcon = new ImageIcon(URL_BASE + "up.png");
    private static final ImageIcon downIcon = new ImageIcon(URL_BASE + "down.png");
    private static final ImageIcon sitIcon = new ImageIcon(URL_BASE + "sit.png");

    private static final Image idleImage = idleIcon.getImage().getScaledInstance(idleIcon.getIconWidth() * SCALE, idleIcon.getIconHeight() * SCALE, Image.SCALE_SMOOTH);
    private static final Image walkImage = walkIcon.getImage().getScaledInstance(walkIcon.getIconWidth() * SCALE, walkIcon.getIconHeight() * SCALE, Image.SCALE_DEFAULT);
    private static final Image runImage = runIcon.getImage().getScaledInstance(runIcon.getIconWidth() * SCALE, runIcon.getIconHeight() * SCALE, Image.SCALE_DEFAULT);
    private static final Image jumpImage = jumpIcon.getImage().getScaledInstance(jumpIcon.getIconWidth() * SCALE, jumpIcon.getIconHeight() * SCALE, Image.SCALE_SMOOTH);
    private static final Image dragImage = dragIcon.getImage().getScaledInstance(dragIcon.getIconWidth() * SCALE, dragIcon.getIconHeight() * SCALE, Image.SCALE_SMOOTH);
    private static final Image fallingImage = fallingIcon.getImage().getScaledInstance(fallingIcon.getIconWidth() * SCALE, fallingIcon.getIconHeight() * SCALE, Image.SCALE_SMOOTH);
    private static final Image upImage = upIcon.getImage().getScaledInstance(upIcon.getIconWidth() * SCALE, upIcon.getIconHeight() * SCALE, Image.SCALE_SMOOTH);
    private static final Image downImage = downIcon.getImage().getScaledInstance(downIcon.getIconWidth() * SCALE, downIcon.getIconHeight() * SCALE, Image.SCALE_SMOOTH);
    private static final Image sitImage = sitIcon.getImage().getScaledInstance(sitIcon.getIconWidth() * SCALE, sitIcon.getIconHeight() * SCALE, Image.SCALE_SMOOTH);

    private static final Icon idle = new ImageIcon(idleImage);
    private static final Icon walk = new ImageIcon(walkImage);
    private static final Icon run = new ImageIcon(runImage);
    private static final Icon jump = new ImageIcon(jumpImage);
    private static final Icon drag = new ImageIcon(dragImage);
    private static final Icon falling = new ImageIcon(fallingImage);
    private static final Icon up = new ImageIcon(upImage);
    private static final Icon down = new ImageIcon(downImage);
    private static final Icon sit = new ImageIcon(sitImage);

    private static final Map<Action, Icon> sprImages = Map.of(
            Action.IDLE, idle,
            Action.WALK, walk,
            Action.RUN, run,
            Action.JUMP, jump,
            Action.DRAG, drag,
            Action.FALLING, falling,
            Action.DOWN, down,
            Action.UP, up,
            Action.SIT, sit
    );

    public static Icon get(Action action) {
        return sprImages.get(action);
    }
}
