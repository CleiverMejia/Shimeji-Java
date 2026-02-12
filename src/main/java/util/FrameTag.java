package util;

public class FrameTag {

    public final Frame frame;
    public final SpriteSourceSize spriteSourceSize;
    public final SourceSize sourceSize;
    public final int duration;

    public FrameTag(Frame frame, SpriteSourceSize spriteSourceSize, SourceSize sourceSize, int duration) {
        this.frame = frame;
        this.spriteSourceSize = spriteSourceSize;
        this.sourceSize = sourceSize;
        this.duration = duration;
    }
}
