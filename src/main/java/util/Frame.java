package util;

public class Frame {

    public final int x;
    public final int y;
    public final int w;
    public final int h;
    public final int duration;
    public final int leftMargin;

    public Frame(int x, int y, int w, int h, int duration, int leftMargin) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.duration = duration;
        this.leftMargin = leftMargin;
    }
}
