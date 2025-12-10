package util;

import enums.Action;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Iterator;
import java.util.Map;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Sprite {

    private static final int SCALE = 4;

    private static final Icon idle = loadImage("idle.png");
    private static final Icon walk = loadImage("walk.gif");
    private static final Icon run = loadImage("run.gif");
    private static final Icon jump = loadImage("jump.png");
    private static final Icon drag = loadImage("drag.png");
    private static final Icon falling = loadImage("jump.png");
    private static final Icon up = loadImage("up.png");
    private static final Icon down = loadImage("down.png");
    private static final Icon sit = loadImage("sit.png");

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

    private static ImageIcon loadImage(String name) {
        String path = "/assets/Ski_" + name;

        ImageIcon icon = new ImageIcon(Sprite.class.getResource(path));
        Image image = icon.getImage().getScaledInstance(
                icon.getIconWidth() * SCALE,
                icon.getIconHeight() * SCALE,
                Image.SCALE_DEFAULT
        );

        return new ImageIcon(image);
    }

    public static Icon get(Action action) {
        return sprImages.get(action);
    }
}
