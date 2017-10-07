package fr.flavi1.gravitree.manager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import fr.flavi1.gravitree.Tools.Items;
import fr.flavi1.gravitree.Tools.Randomizer;
import fr.flavi1.gravitree.entity.Bomb;
import fr.flavi1.gravitree.entity.Newton;
import fr.flavi1.gravitree.menu.MenuButton;

/**
 * Created by Flavien on 24/07/2015.
 */
public class SpecialManager {

    public enum SpecialType {
        EARTHQUAKE, BOMB
    }

    public static float EARTHQUAKE_APPLE_GENERATION_FACTOR = 50;

    public static final int SPECIAL_GLASS_X = 391;
    public static final int SPECIAL_GLASS_Y = 0;
    public static final int SPECIAL_GLASS_W = 89;
    public static final int SPECIAL_GLASS_H = 5;

    public static final int EARTHQUAKE_INTENSITY = 20;

    public static final int FULL_DARKNESS_END = 2000;
    public static final int PARTIAL_DARKNESS_END = 4000;

    public static final SpecialManager manager = new SpecialManager();

    private long earthquakeStartTime;

    private Bomb bomb = null;
    private long explosionTime = 0;

    public SpecialManager() {
        earthquakeStartTime = TimeUtils.millis()-30000;
    }

    public void launchSpecial(SpecialType type, boolean direct) {

        ScoreManager.manager.setAndWriteLastSpecialTime(TimeUtils.millis());

        switch (type) {
            case EARTHQUAKE:
                earthquakeStartTime = TimeUtils.millis();
                if(direct && !SoundManager.isMusicMuted()) {
                    final long id = SoundManager.earthquakeSound.play();
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            SoundManager.earthquakeSound.stop(id);
                        }
                    }, getEarthquakeDuration()/1000);
                }
                break;
            case BOMB:
                bomb = new Bomb();
                if(!SoundManager.isMusicMuted())
                    SoundManager.bombFallSound.play();
                break;
            default:
                break;
        }
    }

    public void launchRandomSpecial() {
        int seed = Randomizer.random.nextInt(2);
        if(seed == 0)
            launchSpecial(SpecialType.BOMB, true);
        else
            launchSpecial(SpecialType.EARTHQUAKE, true);
    }

    public void launchMiniEarthquake(int duration) {
        launchSpecial(SpecialType.EARTHQUAKE, false);
        earthquakeStartTime -= getEarthquakeDuration() - duration;
    }

    public boolean isSpecialReady() {
        int lvl = getSpecialLevel();
        //System.out.println("Level : "+lvl);
        if(lvl < 0)
            return false;
        //System.out.println(TimeUtils.millis() - ScoreManager.manager.getLastSpecialTime());
        return TimeUtils.millis() - ScoreManager.manager.getLastSpecialTime() > Items.specialCooldown[lvl];
    }

    public int getEarthquakeDuration() {
        int lvl = getSpecialLevel();

        if(lvl < 0)
            return 2000;
        return Items.earthquakeDurations[lvl];
    }

    public void act(float delta) {
        if(bomb != null)
            bomb.act(delta);
    }

    public void draw(SpriteBatch batch, MenuButton specialButton) {
        int lvl = getSpecialLevel();

        if(lvl < 0)
            return;
        if(SpecialManager.manager.isSpecialReady())
            batch.draw(TextureManager.manager.specialReadyPoint, SPECIAL_GLASS_X, SPECIAL_GLASS_Y, SPECIAL_GLASS_W, SPECIAL_GLASS_H);
        else
            batch.draw(TextureManager.manager.specialWaitPoint, SPECIAL_GLASS_X, SPECIAL_GLASS_Y, SPECIAL_GLASS_W * (Items.specialCooldown[lvl] - TimeUtils.millis() + ScoreManager.manager.getLastSpecialTime()) / (Items.specialCooldown[lvl]), SPECIAL_GLASS_H);

        batch.draw(TextureManager.manager.specialGlass, SPECIAL_GLASS_X, SPECIAL_GLASS_Y, SPECIAL_GLASS_W, SPECIAL_GLASS_H);

        specialButton.draw(batch);
        if(!isSpecialReady())
            batch.draw(TextureManager.manager.specialButtonHider, 391, 5);
    }

    public void drawDarkness(SpriteBatch batch) {
        long t = TimeUtils.millis() - explosionTime;
        if (t <= FULL_DARKNESS_END) {
            batch.draw(TextureManager.manager.bombDarkness, 0, 0);
            batch.draw(TextureManager.manager.getTexture(1), Newton.NEWTON_X, Newton.NEWTON_Y);
        }
        else if (t <= PARTIAL_DARKNESS_END) {
            batch.setColor(1, 1, 1, ((float) (PARTIAL_DARKNESS_END - t)) / ((float) PARTIAL_DARKNESS_END - FULL_DARKNESS_END));
            batch.draw(TextureManager.manager.bombDarkness, 0, 0);
            batch.setColor(1, 1, 1, 1);
            batch.draw(TextureManager.manager.getTexture(1), Newton.NEWTON_X, Newton.NEWTON_Y);
            ScoreManager.manager.addScore(Items.bombValue[getSpecialLevel()]);
        }
        else if(bomb != null) {
            batch.setColor(1, 1, 1, ((820.f-bomb.getY()))/((float)(820- Newton.NEWTON_H-Newton.NEWTON_Y)));
            batch.draw(TextureManager.manager.bombDarkness, 0, 0);
            batch.setColor(1, 1, 1, 1);
            batch.draw(TextureManager.manager.getTexture(1), Newton.NEWTON_X, Newton.NEWTON_Y);
        }

        if(bomb != null) {
            if(bomb.explosionPending()) {
                ParticleManager.manager.explodeBomb();
                if(!SoundManager.isMusicMuted())
                    SoundManager.explosionSound.play();
                bomb = null;
                explosionTime = TimeUtils.millis();
                launchMiniEarthquake(500);
            }
            else
                bomb.draw(batch);
        }

    }

    /**
     *
     * @return -1 means not bought yet
     */
    public int getSpecialLevel() {
        if(ScoreManager.manager.isItemBought(1, 9))
            return 3;
        else if(ScoreManager.manager.isItemBought(1, 6))
            return 2;
        else if(ScoreManager.manager.isItemBought(1, 3))
            return 1;
        else if(ScoreManager.manager.isItemBought(1, 1))
            return 0;
        else
            return -1;
    }

    public boolean isEarthquakeRunning() {
        return TimeUtils.millis() - earthquakeStartTime <= getEarthquakeDuration();
    }
}
