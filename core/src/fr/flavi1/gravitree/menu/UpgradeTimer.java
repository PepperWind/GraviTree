package fr.flavi1.gravitree.menu;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import fr.flavi1.gravitree.Tools.Items;
import fr.flavi1.gravitree.manager.ScoreManager;
import fr.flavi1.gravitree.manager.TextureManager;

/**
 * Created by Flavien on 10/07/2015.
 */
public class UpgradeTimer {

    public static final float UPGRADE_BAR_X = 9;
    public static final float UPGRADE_BAR_Y = 676;
    public static final float UPGRADE_BAR_W = 462;
    public static final float UPGRADE_BAR_H = 16;

    public static final float UPGRADE_BAR_X_SHOP = 0;
    public static final float UPGRADE_BAR_Y_SHOP = 704;
    public static final float UPGRADE_BAR_W_SHOP = 480;
    public static final float UPGRADE_BAR_H_SHOP = 6;

    public static final float TIMER_TEXT_X = 180;
    public static final float TIMER_TEXT_Y = 715;

    public static final UpgradeTimer timer = new UpgradeTimer();

    private boolean isUpgradeRunning;

    public UpgradeTimer() {
        isUpgradeRunning = isUpgradeActuallyRunning();
    }

    public void runUpgrade() {
        isUpgradeRunning = true;
    }

    public boolean isUpgradeActuallyRunning() {
        if(ScoreManager.manager.getLastInitiatedId() < 0 || ScoreManager.manager.getLastInitiatedSubMenu() < 0)
            return false;

        return TimeUtils.millis() < ScoreManager.manager.getUpgradeInitiationTime() + Items.durations[ScoreManager.manager.getLastInitiatedSubMenu()][ScoreManager.manager.getLastInitiatedId()];
    }

    /*public void launchUpgrade(int subMenuId, int id) {
        ScoreManager.manager.
    }*/

    public void act(float delta) {
        //System.out.println(isUpgradeRunning+" , "+isUpgradeActuallyRunning());
        if(isUpgradeRunning && !isUpgradeActuallyRunning()) {
            //System.out.println(ScoreManager.manager.getLastInitiatedSubMenu()+" - "+ScoreManager.manager.getLastInitiatedId());
            ScoreManager.manager.buyItem(ScoreManager.manager.getLastInitiatedSubMenu(), ScoreManager.manager.getLastInitiatedId());
            isUpgradeRunning = false;
        }
    }

    public void draw(SpriteBatch batch) {
        long currentTime = TimeUtils.millis();

        if(ScoreManager.manager.getLastInitiatedId() < 0 || ScoreManager.manager.getLastInitiatedSubMenu() < 0 || currentTime > ScoreManager.manager.getUpgradeInitiationTime() + Items.durations[ScoreManager.manager.getLastInitiatedSubMenu()][ScoreManager.manager.getLastInitiatedId()]){
            TextureManager.manager.timerFont.draw(batch, "Complete", TIMER_TEXT_X, TIMER_TEXT_Y);
        }

        else {
            long[] temps = decoupe(Items.durations[ScoreManager.manager.getLastInitiatedSubMenu()][ScoreManager.manager.getLastInitiatedId()]-currentTime+ScoreManager.manager.getUpgradeInitiationTime());

            batch.draw(TextureManager.manager.barPoint, UPGRADE_BAR_X, UPGRADE_BAR_Y, UPGRADE_BAR_W*(Items.durations[ScoreManager.manager.getLastInitiatedSubMenu()][ScoreManager.manager.getLastInitiatedId()]-currentTime+ScoreManager.manager.getUpgradeInitiationTime())/(Items.durations[ScoreManager.manager.getLastInitiatedSubMenu()][ScoreManager.manager.getLastInitiatedId()]), UPGRADE_BAR_H);
            TextureManager.manager.timerFont.draw(batch, temps[0]+"d "+temps[1]+"h "+temps[2]+"m "+temps[3]+"s", TIMER_TEXT_X, TIMER_TEXT_Y);
        }

        //batch.draw(TextureManager.manager.timerGlass, UPGRADE_BAR_X, UPGRADE_BAR_Y);
    }

    public void drawInShop(SpriteBatch batch) {
        long currentTime = TimeUtils.millis();

        if(ScoreManager.manager.getLastInitiatedId() < 0 || ScoreManager.manager.getLastInitiatedSubMenu() < 0 || currentTime > ScoreManager.manager.getUpgradeInitiationTime() + Items.durations[ScoreManager.manager.getLastInitiatedSubMenu()][ScoreManager.manager.getLastInitiatedId()]){
            batch.draw(TextureManager.manager.specialReadyPoint, UPGRADE_BAR_X_SHOP, UPGRADE_BAR_Y_SHOP, UPGRADE_BAR_W_SHOP, UPGRADE_BAR_H_SHOP);
        }

        else {
            batch.draw(TextureManager.manager.barPoint, UPGRADE_BAR_X_SHOP, UPGRADE_BAR_Y_SHOP, UPGRADE_BAR_W_SHOP*(Items.durations[ScoreManager.manager.getLastInitiatedSubMenu()][ScoreManager.manager.getLastInitiatedId()]-currentTime+ScoreManager.manager.getUpgradeInitiationTime())/(Items.durations[ScoreManager.manager.getLastInitiatedSubMenu()][ScoreManager.manager.getLastInitiatedId()]), UPGRADE_BAR_H_SHOP);
        }

        batch.draw(TextureManager.manager.timerGlass, UPGRADE_BAR_X_SHOP, UPGRADE_BAR_Y_SHOP, UPGRADE_BAR_W_SHOP, UPGRADE_BAR_H_SHOP);
    }

    /**
     *
     * @param ms
     * @return int[] : days, hours, minutes, seconds
     */
    public long[] decoupe(long ms) {
        long secs = ms/1000;

        long[] r = new long[]{0, 0, 0, 0};

        r[0] = secs/86400;
        r[1] = (secs-r[0]*86400)/3600;
        r[2] = (secs - r[0]*86400 - r[1]*3600)/60;
        r[3] = secs - r[0]*86400 - r[1]*3600 - r[2]*60;

        return r;
    }
}
