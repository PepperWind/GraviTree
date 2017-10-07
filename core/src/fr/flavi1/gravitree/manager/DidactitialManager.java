package fr.flavi1.gravitree.manager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.flavi1.gravitree.Game;

/**
 * Created by Flavien on 11/08/2015.
 */
public class DidactitialManager {

    public static final int HAND_HEIGHT = 94;
    public static final float HAND_SPEED = 500;

    public static final DidactitialManager manager = new DidactitialManager();

    private boolean rising;

    private boolean toShop;

    public float x;
    public float y;

    public static final float[][] targetCoords = {
            {30, 750},
            {220, 200},
            {420, 300},
            {220, 200}
    };

    public DidactitialManager() {
        if(ScoreManager.manager.getDidactitialStep() < 0)
            return;
        rising = true;
        y = 0;
        toShop = true;

        //ScoreManager.manager.setDidactitialStep(0);
    }

    public void load() {
        if(ScoreManager.manager.getDidactitialStep() >= 0)
            x = targetCoords[ScoreManager.manager.getDidactitialStep()][0];
    }

    public void advanceStep() {
        if(ScoreManager.manager.getDidactitialStep()+1 >= targetCoords.length) {
            ScoreManager.manager.setDidactitialStep(-1);
            return;
        }

        ScoreManager.manager.setDidactitialStep(ScoreManager.manager.getDidactitialStep()+1);
        x = targetCoords[ScoreManager.manager.getDidactitialStep()][0];
        y = 0;
        rising = true;
    }

    public void setStep(int id) {
        ScoreManager.manager.setDidactitialStep(id);
        x = targetCoords[ScoreManager.manager.getDidactitialStep()][0];
        y = 0;
        rising = true;
    }

    public void onStateChanged(Game.State state) {
        if(state == Game.State.GAME)
            toShop = true;
        else
            toShop = false;

        if(state == Game.State.SHOP && ScoreManager.manager.getDidactitialStep() == 3)
            setStep(2);

        y = 0;
        rising = true;
    }

    public void act(float delta) {
        if(ScoreManager.manager.getDidactitialStep() < 0 || !rising)
            return;

        if(!toShop) {
            x = targetCoords[ScoreManager.manager.getDidactitialStep()][0];

            y += HAND_SPEED*(1+(targetCoords[ScoreManager.manager.getDidactitialStep()][1]-y)/targetCoords[ScoreManager.manager.getDidactitialStep()][1]) *delta;

            if (y + HAND_HEIGHT >= targetCoords[ScoreManager.manager.getDidactitialStep()][1]) {
                y = -HAND_HEIGHT + targetCoords[ScoreManager.manager.getDidactitialStep()][1];
                rising = false;
            }
        }
        else {
            x = targetCoords[0][0];

            y += HAND_SPEED*(1+(targetCoords[0][1]-y)/targetCoords[0][1]) *delta;

            if (y + HAND_HEIGHT >= targetCoords[0][1]) {
                y = -HAND_HEIGHT + targetCoords[0][1];
                rising = false;
            }
        }
    }

    public void draw(SpriteBatch batch) {
        if(ScoreManager.manager.getDidactitialStep() < 0)
            return;

        //System.out.println(ScoreManager.manager.getDidactitialStep());


        batch.draw(TextureManager.manager.hand, x, y);
    }

}
