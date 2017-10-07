package fr.flavi1.gravitree.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.TimeUtils;
import fr.flavi1.gravitree.Tools.Items;
import fr.flavi1.gravitree.menu.UpgradeTimer;

/**
 * Created by Flavien on 18/06/2015.
 */
public class ScoreManager {

    public static final boolean FAST_SHOP = false;  // TODO

    public static final boolean TO_RESET = false; // TODO
    public static final long BEGINNING_SCORE = 200; // TODO 200

    public static final long SAVE_INTERVAL = 3000;

    private long last_save_time;

    private long score;
    private long subScore;
    private long akey = 3;
    private long bkey = 111;

    private long writeScoreRequestTime; /** = -1 if nothing requested*/

    public Preferences prefs;
    private long lastRemindedTime;

    private long upgradeInitiationTime;
    private int lastInitiatedSubMenu;
    private int lastInitiatedId;
    private long lastSpecialTime;
    
    public int didactitialStep;

    private int birdLevel;

    private boolean gameSceneRefreshRequested;

    private int boughtItemsNb;

    private boolean itemBought[][] = {
            {
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false
            },
            {
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false
            },
            {
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false
            },
            {
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false
            },
            {
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false
            },
            {
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false,
                    false
            }
    };

    public final int appleFallPeriods[][] = {
            {
                    1300, 700, 400, 150, 50
            },
            {
                    1300, 700, 400, 150, 50
            },
            {
                    1300, 700, 400, 150, 50
                    //2000, 1000, 500, 200, 100
            }
    };

    public int[] amountPerSec = {
        0,
        0,
        2,
        3,
        6,
        10,
        15,
        21,
        27,
        35,
        45,
        55,
        65,
        75,
        85,
        95,
        110,
        130,
        160,
        200,
        250,
        300,
        350,
        400,
        450,
        500,
        550,
        600,
        700,
        800,
        900,
        1000,
        1200,
        1350,
        1500,
        1750,
        2000,
        2500,
        3000,
        3500,
        4000,
        5000
    };

    public static ScoreManager manager = new ScoreManager();

    public  void load() {
        prefs = Gdx.app.getPreferences("score");

        if(TO_RESET) {
            reset();
        }

        boughtItemsNb = 0;

        for(int i = 0; i < itemBought.length; i++) {
            for(int j = 0; j < itemBought[i].length; j++) {
                itemBought[i][j] = readItemBought(i, j);
                if(itemBought[i][j])
                    boughtItemsNb++;
            }
        }
        score = 0;
        subScore = encrypt(0);
        addScore(readScore());

        if(readScore() == 0)
            addScore(BEGINNING_SCORE);

        writeScoreRequestTime = -1;
        lastRemindedTime = TimeUtils.millis();
        last_save_time = TimeUtils.millis();

        readLastRemindedTime();
        readDidactitialStep();
        readInitiateItemBuy();
        readBirdLevel();
        readLastSpecialTime();
    }

    public void reset() {
        lastInitiatedId = 0;

        score = BEGINNING_SCORE;
        subScore = encrypt(BEGINNING_SCORE);

        writeScore();

        writeScoreRequestTime = -1;
        lastRemindedTime = TimeUtils.millis();
        writeLastRemindedTime();

        setDidactitialStep(0);

        for(int s = 0; s < itemBought.length; s++) {
            for(int i = 0; i < itemBought[0].length; i++) {
                prefs.putBoolean("b" + s + "," + i, false);
                itemBought[s][i] = false;
            }
        }

        prefs.flush();

    }

    /*public  void displayScore(Batch batch) {
        if(subScore != encrypt(score)) {
            score = -1;
            subScore = encrypt(score);
        }

        //TextureManager.scoreFont.draw(batch, Integer.toString(score), 230, 700);
    }*/

    public  long length(long n) {
        int i = 2;
        int l = 0;

        while (n > 1) {
            if (n%i == 0) {
                n /= i;
                l += 1;
            }
            else {
                i += 1;
            }
        }
        return l;
    }

    public long encrypt(long n) {
        return n*(akey)+bkey;
    }

    public  long disencrypt(long n) {
        return (n - bkey)/(akey);
    }

    public  boolean corresponds(long n, long cryptedN, long cryptedLen) {
        return (n == disencrypt(cryptedN) && cryptedLen == length(cryptedN));
    }

    public long readScore() {
        long n = prefs.getLong("score", BEGINNING_SCORE);
        long cryptedN = prefs.getLong("score_secureKey", encrypt(BEGINNING_SCORE));
        long cryptedLen = prefs.getLong("UQH5GRsfUFfzef", length(encrypt(BEGINNING_SCORE)));

        if(corresponds(n, cryptedN, cryptedLen)) return n;
        return BEGINNING_SCORE;
    }

    public void addScore(long difference) {
        if(subScore != encrypt(score)) {
            score = 0;
            subScore = encrypt(score);
        }
        score += difference;
        if(score < 0) {
            System.out.println("WARNING : Negative score !  Score set to 0 consequently.");
            score = 0;
        }

        requestWriteScore();

        subScore = encrypt(score);
    }

    public long getScore() {
        if(subScore != encrypt(score)) {
            score = 0;
            subScore = encrypt(score);
            System.out.println("WARNING : Score has been tampered with !");
        }

        return score;
    }

    public void requestWriteScore() {
        writeScoreRequestTime = TimeUtils.millis();
    }

    public void writeScore() {
        writeScoreRequestTime = -1;

        prefs.putLong("score", score);
        prefs.putLong("score_secureKey", encrypt(score));
        prefs.putLong("UQH5GRsfUFfzef", length(encrypt(score)));
        prefs.flush();
    }

    public void save() {
        writeScore();
        last_save_time = TimeUtils.millis();
    }

    public int getTreeLvl(int treeId) {
        if(isItemBought(0, 6+treeId))
            return 2;
        if(isItemBought(0, 3+treeId))
            return 1;
        if(treeId == 0 && isItemBought(0, 1) || treeId == 1 && isItemBought(0, 0) || treeId == 2 && isItemBought(0, 2))
            return 0;
        return -1;
    }

    public boolean readItemBought(int subMenuId, int itemId) {
        return prefs.getBoolean("b" + subMenuId + "," + itemId, false);
    }

    public boolean buyItem(int subMenuId, int itemId) {
        if(getScore() < Items.prices[subMenuId][itemId])
            return false;

        prefs.putBoolean("b" + subMenuId + "," + itemId, true);
        prefs.flush();

        System.out.println(Items.durations[subMenuId][itemId]);

        boughtItemsNb++;
        //SoundManager.refreshMusic(boughtItemsNb);

        itemBought[subMenuId][itemId] = true;
        addScore(-Items.prices[subMenuId][itemId]);
        gameSceneRefreshRequested = true;
        save();
        return true;
    }

    public boolean isItemBought(int subMenuId, int itemId) {
        return itemBought[subMenuId][itemId];
    }

    public void writeLastRemindedTime() {
        long time = TimeUtils.millis();
        if(time-lastRemindedTime > 5000) {
            prefs.putLong("lastT", TimeUtils.millis());
            prefs.flush();
            lastRemindedTime = time;
        }
    }

    public long readLastRemindedTime() {
        return prefs.getLong("lastT", TimeUtils.millis());
    }

    public int getMagnetLevel() {
        if(isItemBought(1, 11))
            return 3;
        if(isItemBought(1, 8))
            return 2;
        if(isItemBought(1, 5))
            return 1;
        if(isItemBought(1, 2))
            return 0;
        return -1;
    }

    public void initiateItemBuy(int subMenu, int id) {
        if(!SoundManager.isMusicMuted())
            SoundManager.buySound.play();
        upgradeInitiationTime = TimeUtils.millis();
        UpgradeTimer.timer.runUpgrade();
        lastInitiatedSubMenu = subMenu;
        lastInitiatedId = id;
        prefs.putLong("iniTm", upgradeInitiationTime);
        prefs.putInteger("subMenuInit", subMenu);
        prefs.putInteger("idInit", id);
        prefs.flush();
    }

    public void readDidactitialStep() {
        didactitialStep = prefs.getInteger("didactitialStep", 0);
    }

    public void readInitiateItemBuy() {
        upgradeInitiationTime = prefs.getLong("iniTm");
        lastInitiatedSubMenu = prefs.getInteger("subMenuInit", -1);
        lastInitiatedId = prefs.getInteger("idInit", -1);
    }

    public void readLastSpecialTime() {
        lastSpecialTime = prefs.getLong("lastSpecialTime", TimeUtils.millis() - 20000);
    }

    public void setAndWriteLastSpecialTime(long time) {
        lastSpecialTime = time;
        prefs.putLong("lastSpecialTime", lastSpecialTime);
        prefs.flush();
    }

    public int getDidactitialStep() {
        return didactitialStep;
    }

    public long getLastSpecialTime() {
        return lastSpecialTime;
    }

    public int getLastInitiatedSubMenu() {
        return lastInitiatedSubMenu;
    }

    public int getLastInitiatedId() {
        return lastInitiatedId;
    }

    public boolean canBuy(int subMenuId, int id) {
        return score >= Items.prices[subMenuId][id] && !UpgradeTimer.timer.isUpgradeActuallyRunning();
    }

    public void setDidactitialStep(int step) {
        prefs.putInteger("didactitialStep", step);
        prefs.flush();
        didactitialStep = step;
    }

    public long getUpgradeInitiationTime() {
        return upgradeInitiationTime;
    }

    public int getBirdLevel() {
        return birdLevel;
    }

    public void readBirdLevel() {
        birdLevel = prefs.getInteger("blv", 0);
    }

    public void act() {
        long time = TimeUtils.millis();
        if(writeScoreRequestTime > 0 && writeScoreRequestTime + SAVE_INTERVAL < time || time - last_save_time >= SAVE_INTERVAL) {
            writeScore();
            last_save_time = time;
        }
    }

    public boolean popIsGameSceneRefreshRequested() {
        if(!gameSceneRefreshRequested)
            return false;
        gameSceneRefreshRequested = false;
        return true;
    }

    public int getBoughtItemsNb() {
        return boughtItemsNb;
    }

    public long getBucketDuration() {
        if(itemBought[1][12])
            return 43200;
        else if(itemBought[1][10])
            return 21600;
        else if(itemBought[1][7])
            return 10800;
        else if(itemBought[1][4])
            return 5400;
        else if(itemBought[1][0])
            return 1800;
        else
            return 0;
    }
}
