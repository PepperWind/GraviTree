package fr.flavi1.gravitree.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import fr.flavi1.gravitree.Tools.FRect;
import fr.flavi1.gravitree.manager.PhysicsManager;
import fr.flavi1.gravitree.manager.TextureManager;

/**
 * Created by Flavien on 20/06/2015.
 */
public class AppleFragment {

    public static final long APPLE_FRAGMENT_DURATION = 1500;

    public static final int APPLE_FRAGMENT_VALUE = 1;

    private float x;
    private float y;

    private float ySpeed;
    private float xSpeed;

    private long creationTime;

    private boolean enabled;

    public AppleFragment(float mx, float my, float speed, float angle) {
        x = mx;
        y = my;

        ySpeed = speed*(float)Math.sin(angle);
        xSpeed = speed*(float)Math.cos(angle);

        enabled = true;

        creationTime = TimeUtils.millis();
    }

    public void draw(SpriteBatch batch) {
        batch.draw(TextureManager.manager.getTexture(4), x, y);
    }

    public void act(float delta) {
        ySpeed -= delta * PhysicsManager.GRAVITY_INTENSITY/3;
        y += delta * ySpeed;
        x += delta * xSpeed;
    }

    public FRect getRect() {
        return new FRect(x, y, 8, 8);
    }

    public boolean isCollapseRequested() {
        return TimeUtils.millis() >= creationTime+APPLE_FRAGMENT_DURATION;
    }

    public void disable() {
        enabled = false;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
