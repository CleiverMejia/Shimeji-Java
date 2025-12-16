package util;

import java.awt.image.BufferedImage;

public class FrameGroup {

    private final BufferedImage[] frames;
    private final int[] duration;
    private final boolean repeat;

    public FrameGroup(Frame[] frames, boolean repeat) {
        this.frames = new BufferedImage[frames.length];
        this.duration = new int[frames.length];
        this.repeat = repeat;

        BufferedImage sheet = Sprite.getSheet();

        for (int i = 0; i < frames.length; i++) {
            this.frames[i] = sheet.getSubimage(
                    frames[i].x,
                    frames[i].y,
                    frames[i].w,
                    frames[i].h
            );

            this.duration[i] = frames[i].duration;
        }
    }

    public BufferedImage getFrame(int ind) {
        return frames[ind];
    }

    public int getDuration(int ind) {
        return duration[ind];
    }

    public int size() {
        return frames.length;
    }

    public boolean isRepeat() {
        return repeat;
    }
}
