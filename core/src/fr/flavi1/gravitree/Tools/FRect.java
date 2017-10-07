package fr.flavi1.gravitree.Tools;

/**
 * Created by Flavien on 18/06/2015.
 */
public class FRect {

    private float x;
    private float y;
    private float width;
    private float height;

    public FRect(float mx, float my, float mw, float mh) {
        x = mx;
        y = my;
        width = mw;
        height = mh;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
