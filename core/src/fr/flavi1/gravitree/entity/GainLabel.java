package fr.flavi1.gravitree.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import fr.flavi1.gravitree.manager.TextureManager;

/**
 * Created by Flavien on 20/06/2015.
 */
public class GainLabel {

    public static final float GAINLABEL_SPEED = 200;
    public static final long GAINLABEL_DURATION = 2000;
    public static final float GAINLABEL_STARTY = 100;

    private long creationTime;

    private float x;
    private float y;

    private int value;

    public GainLabel(float mx, float my, int mvalue) {
        x = mx;
        y = my;
        value = mvalue;
        creationTime = TimeUtils.millis();
    }

    public void draw(SpriteBatch batch) {
        long time = TimeUtils.millis();
        TextureManager.manager.gainFont.setColor(1, 1, 1, 1-Math.min(1, ((float)(time-creationTime-2*GAINLABEL_DURATION))/((float)GAINLABEL_DURATION)));
        TextureManager.manager.gainFont.draw(batch, "+ " + value, x,y);
        batch.setColor(1, 1, 1, 1-Math.min(1, ((float)(time-creationTime-2*GAINLABEL_DURATION))/((float)GAINLABEL_DURATION)));
        batch.draw(TextureManager.manager.getTexture(5), x-16, y-24);
    }

    public void act(float delta) {
        y += GAINLABEL_SPEED*delta;
    }

    public boolean isCollapseRequested() {
        return TimeUtils.millis() >= creationTime+GAINLABEL_DURATION-100;
    }
}
