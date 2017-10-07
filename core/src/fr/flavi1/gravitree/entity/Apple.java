package fr.flavi1.gravitree.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.flavi1.gravitree.Tools.FRect;
import fr.flavi1.gravitree.Tools.Items;
import fr.flavi1.gravitree.Tools.Randomizer;
import fr.flavi1.gravitree.manager.PhysicsManager;
import fr.flavi1.gravitree.manager.ScoreManager;
import fr.flavi1.gravitree.manager.SoundManager;
import fr.flavi1.gravitree.manager.TextureManager;

/**
 * Created by Flavien on 19/06/2015.
 */
public class Apple {

    public enum State {
        GROWING, FALLING
    }

    public static final float GROWTH_SPEED = .4f;

    private float x;
    private float y;

    private float ySpeed;
    private float xSpeed;

    private int type;

    private State state;
    private float scale;

    private int bouncedTimes;

    public static final int[] apple_sizes = {
            32,
            32,
            32,
            40,
            32
    };

    public Apple(float mx, float my, boolean startBig) {
        type = generateAppleType();

        x = mx;
        y = my;
        ySpeed = -500;
        xSpeed = 0;

        bouncedTimes = 0;

        /*if(startBig) {
            scale = 1;
            state = State.FALLING;
        }
        else {*/
            scale = 0;
            state = State.GROWING;
        //}

        if(!SoundManager.isMusicMuted())
                SoundManager.appleSpawnSound.play(.01f);
    }

    public int generateAppleType() {
        int sup = 3;
        if(ScoreManager.manager.isItemBought(2, 12))
            sup = 10;
        else if(ScoreManager.manager.isItemBought(2, 10))
            sup = 9;
        else if(ScoreManager.manager.isItemBought(2, 7))
            sup = 7;
        else if(ScoreManager.manager.isItemBought(2, 4))
            sup = 5;

        int seed = Randomizer.random.nextInt(sup);

        if(seed < 3)
            return 0;
        if(seed < 5)
            return 1;
        if(seed < 7)
            return 2;
        if(seed < 9)
            return 3;
        else
            return 4;

    }

    public void draw(SpriteBatch batch) {
        batch.draw(TextureManager.manager.getAppleTexture(type), x, y, 16, 30, apple_sizes[type], apple_sizes[type], scale, scale, 0, 0, 0, apple_sizes[type], apple_sizes[type], false, false);
    }

    public void act(float delta) {
        if(state == State.GROWING) {
            scale += GROWTH_SPEED*delta;
            if(scale >= 1) {// || SpecialManager.manager.isEarthquakeRunning())
                state = State.FALLING;
                scale = 1;
            }
            return;
        }

        ySpeed -= delta * PhysicsManager.GRAVITY_INTENSITY;
        xSpeed *= (1-PhysicsManager.FRICTION_INTENSITY_X);
        ySpeed *= (1-(PhysicsManager.FRICTION_INTENSITY_Y));
        y += delta * ySpeed;
        x += delta * xSpeed;
    }

    public FRect getRect() {
        return new FRect(x, y, apple_sizes[type], apple_sizes[type]);
    }

    public AppleFragment[] explode() {
        if(!SoundManager.isMusicMuted())
            SoundManager.appleFallSounds[Randomizer.random.nextInt(SoundManager.appleFallSounds.length)].play(.01f);
        if(ScoreManager.manager.isItemBought(2, 11))
            return new AppleFragment[]{
                    new AppleFragment(x+16, y, 200+100*Randomizer.random.nextFloat(), .2f+(float)(Math.PI-.2)*Randomizer.random.nextFloat()),
                    new AppleFragment(x+16, y, 200+100*Randomizer.random.nextFloat(), .2f+(float)(Math.PI-.2)*Randomizer.random.nextFloat()),
                    new AppleFragment(x+16, y, 200+100*Randomizer.random.nextFloat(), .2f+(float)(Math.PI-.2)*Randomizer.random.nextFloat()),
                    new AppleFragment(x+16, y, 200+100*Randomizer.random.nextFloat(), .2f+(float)(Math.PI-.2)*Randomizer.random.nextFloat()),
                    new AppleFragment(x+16, y, 200+100*Randomizer.random.nextFloat(), .2f+(float)(Math.PI-.2)*Randomizer.random.nextFloat()),
                    new AppleFragment(x+16, y, 200+100*Randomizer.random.nextFloat(), .2f+(float)(Math.PI-.2)*Randomizer.random.nextFloat()),
            };
        if(ScoreManager.manager.isItemBought(2, 8))
            return new AppleFragment[]{
                    new AppleFragment(x+16, y, 200+100*Randomizer.random.nextFloat(), .2f+(float)(Math.PI-.2)*Randomizer.random.nextFloat()),
                    new AppleFragment(x+16, y, 200+100*Randomizer.random.nextFloat(), .2f+(float)(Math.PI-.2)*Randomizer.random.nextFloat()),
                    new AppleFragment(x+16, y, 200+100*Randomizer.random.nextFloat(), .2f+(float)(Math.PI-.2)*Randomizer.random.nextFloat()),
                    new AppleFragment(x+16, y, 200+100*Randomizer.random.nextFloat(), .2f+(float)(Math.PI-.2)*Randomizer.random.nextFloat()),
                    new AppleFragment(x+16, y, 200+100*Randomizer.random.nextFloat(), .2f+(float)(Math.PI-.2)*Randomizer.random.nextFloat()),
            };
        if(ScoreManager.manager.isItemBought(2, 5))
            return new AppleFragment[]{
                    new AppleFragment(x+16, y, 200+100*Randomizer.random.nextFloat(), .2f+(float)(Math.PI-.2)*Randomizer.random.nextFloat()),
                    new AppleFragment(x+16, y, 200+100*Randomizer.random.nextFloat(), .2f+(float)(Math.PI-.2)*Randomizer.random.nextFloat()),
                    new AppleFragment(x+16, y, 200+100*Randomizer.random.nextFloat(), .2f+(float)(Math.PI-.2)*Randomizer.random.nextFloat()),
                    new AppleFragment(x+16, y, 200+100*Randomizer.random.nextFloat(), .2f+(float)(Math.PI-.2)*Randomizer.random.nextFloat()),
            };
        if(ScoreManager.manager.isItemBought(2, 2))
            return new AppleFragment[]{
                    new AppleFragment(x+16, y, 200+100*Randomizer.random.nextFloat(), .2f+(float)(Math.PI-.2)*Randomizer.random.nextFloat()),
                    new AppleFragment(x+16, y, 200+100*Randomizer.random.nextFloat(), .2f+(float)(Math.PI-.2)*Randomizer.random.nextFloat()),
                    new AppleFragment(x+16, y, 200+100*Randomizer.random.nextFloat(), .2f+(float)(Math.PI-.2)*Randomizer.random.nextFloat()),
            };
        return new AppleFragment[]{
                new AppleFragment(x+16, y, 200+100*Randomizer.random.nextFloat(), .2f+(float)(Math.PI-.2)*Randomizer.random.nextFloat()),
                new AppleFragment(x+16, y, 200+100*Randomizer.random.nextFloat(), .2f+(float)(Math.PI-.2)*Randomizer.random.nextFloat()),
        };
    }

    public int getValue() {
        return Items.appleDamage[type];
    }

    public void addXSpeed(float pSpeed) {
        xSpeed += pSpeed;
    }
    public void addYSpeed(float pSpeed) {
        ySpeed += pSpeed;
    }

    public float getX() {
        return x;
    }

    public int getType() {
        return type;
    }

    public float getY() {
        return y;
    }

    public int bouncesStillAvailable() {
        if(ScoreManager.manager.isItemBought(2, 9))
            return 4-bouncedTimes;
        if(ScoreManager.manager.isItemBought(2, 6))
            return 3-bouncedTimes;
        if(ScoreManager.manager.isItemBought(2, 3))
            return 2-bouncedTimes;
        if(ScoreManager.manager.isItemBought(2, 1))
            return 1-bouncedTimes;
        return 0;
    }

    public boolean canStillBounce() {
        return bouncesStillAvailable() > 0;
    }

    public void bounce() {
        bouncedTimes++;

        xSpeed = 0;
        ySpeed = -ySpeed*.9f;
    }
}
