package util;

import enums.Action;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;

public class Sprite {

    private final String URL_BASE = "src/assets/Ski_";
    private final int SCALE = 4;

    private Map<Action, ImageIcon> spriteImages = new HashMap<>();

    public Sprite() {
        for (Action action : Action.values()) {
            String sprName = action.name().toLowerCase();

            ImageIcon icon = new ImageIcon(URL_BASE + sprName + ".png");
            Image iconBig = icon.getImage().getScaledInstance(
                icon.getIconWidth() * SCALE, icon.getIconHeight() * SCALE, Image.SCALE_SMOOTH
            );

            spriteImages.put(action, new ImageIcon(iconBig));
        }
    }

    public ImageIcon get(Action action) {
        return spriteImages.get(action);
    }
}
