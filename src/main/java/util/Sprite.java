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
        new Frame(29, 1, 27, 33, 300),
        new Frame(1, 36, 27, 34, 300)
    };

    private static final Map<Action, FrameGroup> sprImages = new HashMap<>();

    static {
        try {
            sheet = ImageIO.read(Sprite.class.getResource("/assets/ski.png"));

            final FrameGroup idle = new FrameGroup(idleCoord, true);

            sprImages.put(Action.IDLE, idle);
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static BufferedImage getSheet() {
        return sheet;
    }

    public static FrameGroup get(Action action) {
        return sprImages.get(Action.IDLE);
    }
}
