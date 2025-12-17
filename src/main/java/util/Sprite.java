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
        new Frame(27, 34, 27, 33, 300),
        new Frame(0, 0, 27, 34, 300)
    };

    private static final Frame[] walkCoord = {
        new Frame(54, 34, 27, 33, 200),
        new Frame(0, 67, 27, 32, 200),
        new Frame(27, 0, 27, 34, 200),
        new Frame(54, 0, 27, 34, 200)
    };

    private static final Map<Action, FrameGroup> sprImages = new HashMap<>();

    static {
        try {
            sheet = ImageIO.read(Sprite.class.getResource("/assets/Ski.png"));

            final FrameGroup idle = new FrameGroup(idleCoord, true);
            final FrameGroup walk = new FrameGroup(walkCoord, true);

            sprImages.put(Action.IDLE, idle);
            sprImages.put(Action.WALK, walk);
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
