package models;

import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Player {
    private final Icon idle;
    private final Icon jump;
    private final Icon walk;
    private final Icon sit;
    private final Icon drag;
    private final Icon up;
    private final Icon down;
    private final Icon run;
    private final int SCALE = 4;

    public Player() {
        ImageIcon idleIcon = new ImageIcon("src/assets/Ski_idle.png");
        Image idleIconBig = idleIcon.getImage().getScaledInstance(idleIcon.getIconWidth() * SCALE, idleIcon.getIconHeight() * SCALE, Image.SCALE_SMOOTH);

        ImageIcon jumpIcon = new ImageIcon("src/assets/Ski_jump.png");
        Image jumpIconBig = jumpIcon.getImage().getScaledInstance(jumpIcon.getIconWidth() * SCALE, jumpIcon.getIconHeight() * SCALE, Image.SCALE_SMOOTH);

        ImageIcon walkIcon = new ImageIcon("src/assets/Ski_walk.gif");
        Image walkIconBig = walkIcon.getImage().getScaledInstance(walkIcon.getIconWidth() * SCALE, walkIcon.getIconHeight() * SCALE, Image.SCALE_DEFAULT);

        ImageIcon sitIcon = new ImageIcon("src/assets/Ski_sit.png");
        Image sitIconBig = sitIcon.getImage().getScaledInstance(sitIcon.getIconWidth() * SCALE, sitIcon.getIconHeight() * SCALE, Image.SCALE_SMOOTH);

        ImageIcon dragIcon = new ImageIcon("src/assets/Ski_drag.png");
        Image dragIconBig = dragIcon.getImage().getScaledInstance(dragIcon.getIconWidth() * SCALE, dragIcon.getIconHeight() * SCALE, Image.SCALE_SMOOTH);

        ImageIcon upIcon = new ImageIcon("src/assets/Ski_up.png");
        Image upIconBig = upIcon.getImage().getScaledInstance(upIcon.getIconWidth() * SCALE, upIcon.getIconHeight() * SCALE, Image.SCALE_SMOOTH);

        ImageIcon downIcon = new ImageIcon("src/assets/Ski_down.png");
        Image downIconBig = downIcon.getImage().getScaledInstance(downIcon.getIconWidth() * SCALE, downIcon.getIconHeight() * SCALE, Image.SCALE_SMOOTH);

        ImageIcon runIcon = new ImageIcon("src/assets/Ski_run.gif");
        Image runIconBig = runIcon.getImage().getScaledInstance(runIcon.getIconWidth() * SCALE, runIcon.getIconHeight() * SCALE, Image.SCALE_DEFAULT);

        idle = new ImageIcon(idleIconBig);
        jump = new ImageIcon(jumpIconBig);
        walk = new ImageIcon(walkIconBig);
        sit = new ImageIcon(sitIconBig);
        drag = new ImageIcon(dragIconBig);
        up = new ImageIcon(upIconBig);
        down = new ImageIcon(downIconBig);
        run = new ImageIcon(runIconBig);
    }

    public Icon getIdle() {
        return idle;
    }

    public Icon getJump() {
        return jump;
    }

    public Icon getWalk() {
        return walk;
    }

    public Icon getSit() {
        return sit;
    }

    public Icon getDrag() {
        return drag;
    }

    public Icon getUp() {
        return up;
    }

    public Icon getDown() {
        return down;
    }

    public Icon getRun() {
        return run;
    }
}