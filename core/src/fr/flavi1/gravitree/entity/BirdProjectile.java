package fr.flavi1.gravitree.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.flavi1.gravitree.Tools.FRect;
import fr.flavi1.gravitree.manager.PhysicsManager;
import fr.flavi1.gravitree.manager.TextureManager;

/**
 * Created by Flavien on 15/07/2015.
 */
public class BirdProjectile {

    private float x;
    private float y;

    private float ySpeed;
    private float xSpeed;

    private int value;

    public BirdProjectile(float mx, float my, int mvalue) {
        x = mx;
        y = my;
        value = mvalue;
        ySpeed = -500;
        xSpeed = 0;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(TextureManager.manager.birdProjectile, x, y);
    }

    public void act(float delta) {
        ySpeed -= delta * PhysicsManager.GRAVITY_INTENSITY;
        xSpeed *= (1-PhysicsManager.FRICTION_INTENSITY_X);
        ySpeed *= (1-(PhysicsManager.FRICTION_INTENSITY_Y));
        y += delta * ySpeed;
        x += delta * xSpeed;
    }

    public FRect getRect() {
        return new FRect(x, y, 8, 8);
    }

    public int getValue() {
        return value;
    }

    public void addXSpeed(float pSpeed) {
        xSpeed += pSpeed;
        System.out.println(xSpeed);
    }
    public void addYSpeed(float pSpeed) {
        ySpeed += pSpeed;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

}
