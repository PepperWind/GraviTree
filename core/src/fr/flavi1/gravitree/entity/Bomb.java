package fr.flavi1.gravitree.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.flavi1.gravitree.manager.TextureManager;

/**
 * Created by Flavien on 20/08/2015.
 */

public class Bomb {

    public static final int BOMB_W = 49;
    public static final int BOMB_H = 160;

    public static final float BOMB_X = Newton.NEWTON_X+Newton.NEWTON_W/2-BOMB_W/2;
    public static final float BOMB_START_Y = 800;
    public static final float BOMB_START_VELOCITY = -300;
    public static final float BOMB_ACCELERATION = -40;

    public static final float BOMB_EXPLOSION_Y = Newton.NEWTON_Y+Newton.NEWTON_H;

    private float y;
    private float speed;

    public Bomb() {
        speed = BOMB_START_VELOCITY;
        y = BOMB_START_Y;
    }

    public void act(float delta) {
        y += delta*speed;
        speed += delta*BOMB_ACCELERATION;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(TextureManager.manager.bomb, BOMB_X, y);
    }

    public boolean explosionPending() {
        return y <= BOMB_EXPLOSION_Y;
    }

    public float getY() {
        return y;
    }
}
