package fr.flavi1.gravitree.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.TimeUtils;
import fr.flavi1.gravitree.Tools.Items;
import fr.flavi1.gravitree.Tools.Randomizer;
import fr.flavi1.gravitree.manager.ScoreManager;
import fr.flavi1.gravitree.manager.TextureManager;

/**
 * Created by Flavien on 14/07/2015.
 */
public class Bird {

    private enum State {
        COMING, WAITING, LEAVING, COLLAPSABLE
    }

    public static final int BIRD_WIDTH = 40;
    public static final int BIRD_HEIGHT = 28;
    public static final float BIRD_HALFWIDTH = BIRD_WIDTH/2;
    public static final float BIRD_HALFHEIGHT = BIRD_HEIGHT/2;

    public static final float BIRD_SPEED_CORRECTION = .01f;

    private float nestX;  // The coordinates of the CENTER of the nest
    private float nestY;

    private float r;
    private long tOrig;
    private long leavingTime;
    private long lastAttackTime;

    private int attackPeriod;

    /**     Must NOT be changed without method setState(State pstate)    */
    private State state;

    public Bird(int treeId, int nestId) {
        state = State.COMING;

        nestX = Items.nestCoords[treeId][nestId][0]+21;
        nestY = Items.nestCoords[treeId][nestId][1]+18;

        tOrig = TimeUtils.millis();

        attackPeriod = Items.birdAttackPeriods[ScoreManager.manager.getBirdLevel()];
        lastAttackTime = TimeUtils.millis()- Randomizer.random.nextInt(attackPeriod);

        r = Randomizer.random.nextFloat()*5f+17f;
    }

    public void update(float pnestX, float pnestY) {
        attackPeriod = Items.birdAttackPeriods[ScoreManager.manager.getBirdLevel()];
        nestX = pnestX+21;
        nestY = pnestY+18;
    }

    public float[] coord(long t) {
        if(state == State.COMING)
            return new float[]{(float)(r*(BIRD_SPEED_CORRECTION*(t-tOrig) - Math.sin(BIRD_SPEED_CORRECTION*(t-tOrig))) - 2*Math.PI*r + nestX%(2*Math.PI*r)), (float)(r*(1-Math.cos(BIRD_SPEED_CORRECTION*(t-tOrig)))+nestY)};

        else if(state == State.WAITING) {
            return new float[]{nestX, nestY};
        }

        else {
            return new float[]{(float)(r*(BIRD_SPEED_CORRECTION*(t-tOrig) - Math.sin(BIRD_SPEED_CORRECTION*(t-tOrig)))) + nestX, (float)(r*(1-Math.cos((BIRD_SPEED_CORRECTION*(t-tOrig))))+nestY)};
        }
    }

    public void act() {
        switch (state) {
            case COMING:
                if (coord(TimeUtils.millis())[0] >= nestX - BIRD_HALFWIDTH) {
                    setState(State.WAITING);
                }
                break;
            case WAITING:
                if(TimeUtils.millis() - tOrig > leavingTime) {
                    setState(State.LEAVING);
                }
                break;
            case LEAVING:
                if(coord(TimeUtils.millis())[0] >= 480) {
                    setState(State.COLLAPSABLE);
                }
            break;
            default:
                break;
        }
    }

    public void draw(Batch batch) {
        float[] coords = coord(TimeUtils.millis());

        switch (state) {
            case COMING:
            case LEAVING:
                if(coords[1] - nestY < r / 5)
                    batch.draw(TextureManager.manager.bird1, coords[0], coords[1], BIRD_HALFWIDTH, BIRD_HALFHEIGHT, BIRD_WIDTH, BIRD_HEIGHT, 1, 1, getRotation(TimeUtils.millis()), 0, 0, BIRD_WIDTH, BIRD_HEIGHT, false, false);
                else
                    batch.draw(TextureManager.manager.bird0, coords[0], coords[1], BIRD_HALFWIDTH, BIRD_HALFHEIGHT, BIRD_WIDTH, BIRD_HEIGHT, 1, 1, getRotation(TimeUtils.millis()), 0, 0, BIRD_WIDTH, BIRD_HEIGHT, false, false);
                break;
            case WAITING:
                batch.draw(TextureManager.manager.bird0, coords[0], coords[1]);
            default:
                break;
        }

    }

    /**
     * @param t : The RAW TIME
     * @return rotation in radians
     */
    public float getRotation(long t) {

        /*switch (state) {
            case COMING:
            case LEAVING:
                try {
                    return (float) (180* Math.atan(Math.sin(BIRD_SPEED_CORRECTION*(t-tOrig)) / (1 - Math.cos(BIRD_SPEED_CORRECTION*(t-tOrig)))) / Math.PI);
                } catch (Exception e) {
                    return (float) (90);
                }
            case WAITING:
                return 0;
            default:
                System.out.print("WARNING : A collapsable bird must NOT be displayed !");
                return 0;
        }*/
        return 0;

    }

    public void setState(State pstate) {
        if(pstate == state)
            return;

        state = pstate;
        tOrig = TimeUtils.millis();

        if(state == State.WAITING)
            leavingTime = 1000 + Randomizer.random.nextInt(1000);
    }

    public boolean isCollapseRequested() {
        return state == State.COLLAPSABLE;
    }

    public boolean popProjectileToBeSpawned() {
        if(TimeUtils.millis()-lastAttackTime >= attackPeriod) {
            lastAttackTime = TimeUtils.millis();
            return true;
        }
        return false;
    }

}
