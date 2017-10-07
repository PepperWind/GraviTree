package fr.flavi1.gravitree.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.flavi1.gravitree.Tools.FRect;
import fr.flavi1.gravitree.manager.TextureManager;

/**
 * Created by Flavien on 19/06/2015.
 */
public class Newton {

    public static final float MAGNET_CENTERX = 149;
    public static final float MAGNET_CENTERY = 0;

    public static final int NEWTON_H = 109;
    public static final int NEWTON_W = 90;
    public static final int NEWTON_X = 150;
    public static final int NEWTON_Y = 0;

    private float x;
    private float y;

    public Newton() {
        x = NEWTON_X;
        y = NEWTON_Y;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(TextureManager.manager.getTexture(1), x, y);
    }

    public void act(float delta) {

    }


    public FRect[] getRects() {
        return new FRect[]{new FRect(x+2, y+22, 18, 15), new FRect(x+20, y+46, 14, 39), new FRect(x+34, y, 45, 108)};
    }
}
