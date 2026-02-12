package util;

import enums.Action;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class Sprite {

    private static final BufferedImage sheet;

    private static final Frame[] idleCoord = {
        new Frame(111, 51, 26, 25, 300, 6),
        new Frame(111, 0, 26, 26, 300, 6)
    };

    private static final Frame[] walkCoord = {
        new Frame(52, 52, 26, 25, 200, 6),
        new Frame(58, 26, 26, 26, 200, 6),
        new Frame(78, 52, 26, 25, 200, 6)
    };

    private static final Frame[] runCoord = {
        new Frame(84, 27, 27, 25, 100, 6),
        new Frame(58, 0, 27, 26, 100, 6),
        new Frame(0, 29, 27, 25, 100, 6)
    };

    private static final Frame[] jumpCoord = {
        new Frame(78, 77, 25, 25, 100, 6)
    };

    private static final Frame[] downCoord = {
        new Frame(0, 79, 26, 23, 100, 6)
    };

    private static final Frame[] upCoord = {
        new Frame(0, 54, 26, 25, 100, 7)
    };

    private static final Frame[] sitCoord = {
        new Frame(26, 81, 26, 22, 300, 6)
    };

    private static final Frame[] dragCoord = {
        new Frame(85, 0, 26, 27, 300, 5)
    };

    private static final Frame[] fallingCoord = {
        new Frame(27, 29, 25, 27, 300, 6)
    };

    private static final Frame[] helpyCoord = {
        new Frame(103, 101, 22, 7, 300, 7)
    };

    private static final Frame[] trumpetCoord = {
        new Frame(26, 56, 26, 25, 300, 6),
        new Frame(104, 76, 26, 25, 300, 5),
        new Frame(26, 56, 26, 25, 300, 6),
        new Frame(52, 77, 26, 25, 300, 7)
    };

    private static final Map<Action, FrameGroup> sprImages = new HashMap<>();

    static {
        try {
            sheet = ImageIO.read(Sprite.class.getResource("/assets/Ski.png"));

            final FrameGroup idle = new FrameGroup(idleCoord, true);
            final FrameGroup walk = new FrameGroup(walkCoord, true);
            final FrameGroup run = new FrameGroup(runCoord, true);
            final FrameGroup jump = new FrameGroup(jumpCoord, false);
            final FrameGroup down = new FrameGroup(downCoord, false);
            final FrameGroup up = new FrameGroup(upCoord, false);
            final FrameGroup sit = new FrameGroup(sitCoord, false);
            final FrameGroup drag = new FrameGroup(dragCoord, false);
            final FrameGroup falling = new FrameGroup(fallingCoord, false);
            final FrameGroup helpy = new FrameGroup(helpyCoord, false);
            final FrameGroup trumpet = new FrameGroup(trumpetCoord, true);

            sprImages.put(Action.IDLE, idle);
            sprImages.put(Action.WALK, walk);
            sprImages.put(Action.RUN, run);
            sprImages.put(Action.JUMP, jump);
            sprImages.put(Action.DOWN, down);
            sprImages.put(Action.UP, up);
            sprImages.put(Action.SIT, sit);
            sprImages.put(Action.DRAG, drag);
            sprImages.put(Action.FALLING, falling);
            sprImages.put(Action.HELPY, helpy);
            sprImages.put(Action.TRUMPET, trumpet);
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static BufferedImage getSheet() {
        return sheet;
    }

    public static FrameGroup get(Action action) {
        if (sprImages.get(action) == null) {
            return sprImages.get(Action.IDLE);
        }

        return sprImages.get(action);
    }
}
