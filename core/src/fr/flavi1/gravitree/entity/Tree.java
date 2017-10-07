package fr.flavi1.gravitree.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import fr.flavi1.gravitree.Tools.Items;
import fr.flavi1.gravitree.Tools.Randomizer;
import fr.flavi1.gravitree.manager.ScoreManager;
import fr.flavi1.gravitree.manager.SpecialManager;
import fr.flavi1.gravitree.manager.TextureManager;

/**
 * Created by Flavien on 19/06/2015.
 */
public class Tree {

    //public static final float TREE_Y = 0; /** Obsolete */

    private float x;
    private float y;
    private int id;

    private int birdSpawnPeriod;
    private long lastBird0SpawnTime;
    private long lastBird1SpawnTime;
    private long lastBird2SpawnTime;

    private long lastAppleTime;

    public Tree(int mid) {
        id = mid;

        if(ScoreManager.manager.getTreeLvl(id) < 0)
            return;

        x = getXById(id);
        y = getYById(id);
        lastAppleTime = TimeUtils.millis()- Randomizer.random.nextInt(ScoreManager.manager.appleFallPeriods[id][ScoreManager.manager.getTreeLvl(id)]);
        
        birdSpawnPeriod = Items.birdSpawnPeriod[ScoreManager.manager.getTreeLvl(id)];
        lastBird0SpawnTime = TimeUtils.millis()-Randomizer.random.nextInt(birdSpawnPeriod);
        lastBird1SpawnTime = TimeUtils.millis()-Randomizer.random.nextInt(birdSpawnPeriod);
        lastBird2SpawnTime = TimeUtils.millis()-Randomizer.random.nextInt(birdSpawnPeriod);
    }

    /*public FRect getRect() { /** OBSOLETE AND DEPRECATED
        switch (id) {
            case 0:
                return new FRect(148, 426, 210, 199);
            case 1:
                return new FRect(258, 580, 182, 190);
            default:
                return new FRect(0, 400, 213, 222);
        }
    }*/

    public float[] getRandomPoint() {
        float[] centerCoord = getCircleCenter();
        float r = Randomizer.random.nextFloat()*getCircleRadius();
        float angle = Randomizer.random.nextFloat()*2*3.14159f;
        return new float[]{centerCoord[0]+r*(float)Math.cos(angle), centerCoord[1]+r*(float)Math.sin(angle)};
    }

    public float[] getCircleCenter() {
        switch(id) {
            case 0:
                switch(ScoreManager.manager.getTreeLvl(id)) {
                    case 0:
                        return new float[]{getXById(id)+173, getYById(id) + 349};
                    case 1:
                        return new float[]{getXById(id)+176, getYById(id) + 406};
                    case 2:
                        return new float[]{getXById(id)+165, getYById(id) + 442};
                }

            case 1:
                switch(ScoreManager.manager.getTreeLvl(id)) {
                    case 0:
                        return new float[]{getXById(id)+175, getYById(id) + 181};
                    case 1:
                        return new float[]{getXById(id)+170, getYById(id) + 236};
                    case 2:
                        return new float[]{getXById(id)+176, getYById(id) + 274};
                }

            case 2:
                switch(ScoreManager.manager.getTreeLvl(id)) {
                    case 0:
                        return new float[]{getXById(id)+173, getYById(id) + 445};
                    case 1:
                        return new float[]{getXById(id)+174, getYById(id) + 506};
                    case 2:
                        return new float[]{getXById(id)+177, getYById(id) + 538};
                }

            default:
                System.out.println("Error fond in : getCircleCenter()");
                return new float[]{-1, -1};
        }
    }

    public float getCircleRadius() {
        switch(id) {
            case 0:
                switch(ScoreManager.manager.getTreeLvl(id)) {
                    case 0:
                        return 80;
                    case 1:
                        return 120;
                    case 2:
                        return 140;
                }

            case 1:
                switch(ScoreManager.manager.getTreeLvl(id)) {
                    case 0:
                        return 73;
                    case 1:
                        return 125;
                    case 2:
                        return 140;
                }

            case 2:
                switch(ScoreManager.manager.getTreeLvl(id)) {
                    case 0:
                        return 80;
                    case 1:
                        return 105;
                    case 2:
                        return 110;
                }

            default:
                //System.out.println("Error fond in : getCircleRadius()");
                return 0;
        }

    }

    /**
     * @return vect : {appleHasToBeSpawned, bird0HasToBeSpawned, bird1HasToBeSpawned, bird2HasToBeSpawned}
     */

    public boolean[] act() {
        if(ScoreManager.manager.getTreeLvl(id) == -1 || !ScoreManager.manager.isItemBought(2, 0))
            return new boolean[]{false, false, false, false};

        long time = TimeUtils.millis();
        if(!SpecialManager.manager.isEarthquakeRunning())
            return new boolean[]{time - lastAppleTime >= ScoreManager.manager.appleFallPeriods[id][ScoreManager.manager.getTreeLvl(id)], time - lastBird0SpawnTime >= birdSpawnPeriod, time - lastBird1SpawnTime >= birdSpawnPeriod, time - lastBird2SpawnTime >= birdSpawnPeriod};
        else
            return new boolean[]{time - lastAppleTime >= ScoreManager.manager.appleFallPeriods[id][ScoreManager.manager.getTreeLvl(id)]/SpecialManager.EARTHQUAKE_APPLE_GENERATION_FACTOR, time - lastBird0SpawnTime >= birdSpawnPeriod, time - lastBird1SpawnTime >= birdSpawnPeriod, time - lastBird2SpawnTime >= birdSpawnPeriod};

    }

    public void anAppleHasSpawned() {
        lastAppleTime = TimeUtils.millis();
    }

    public void aBirdHasSpawned(int id) {
        switch(id) {
            case 0:
                lastBird0SpawnTime = TimeUtils.millis();
            case 1:
                lastBird1SpawnTime = TimeUtils.millis();
            default:
                lastBird2SpawnTime = TimeUtils.millis();
        }
    }

    public void draw(SpriteBatch batch) {
        if(ScoreManager.manager.getTreeLvl(id) < 0)
            return;

        batch.draw(TextureManager.manager.getTreeTexture(id, ScoreManager.manager.getTreeLvl(id)), x, y);
    }

    public static float getXById(int pid) {
        switch(pid) {
            case 0:
                return -67;
            case 1:
                return 63;
            default:
                return 182;
        }
    }

    public static float getYById(int pid) {
        switch(pid) {
            case 0:
                return 30;
            case 1:
                return 0;
            default:
                return 15;
        }
    }

    public static int permutation(int i) {
        if (i == 0)
            return 0;
        if (i == 1)
            return 2;
        else
            return 1;
    }
}
