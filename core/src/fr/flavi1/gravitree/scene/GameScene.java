package fr.flavi1.gravitree.scene;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import fr.flavi1.gravitree.Tools.FRect;
import fr.flavi1.gravitree.Tools.Items;
import fr.flavi1.gravitree.Tools.Randomizer;
import fr.flavi1.gravitree.entity.*;
import fr.flavi1.gravitree.manager.*;
import fr.flavi1.gravitree.menu.MenuButton;
import fr.flavi1.gravitree.menu.UpgradeTimer;

import java.util.ArrayList;

/**
 * Created by Flavien on 19/06/2015.
 */
public class GameScene {

    public Newton newton;

    public ArrayList<Apple> apples;
    public ArrayList<Bird> birds;
    public Tree[] trees;
    public ArrayList<BirdProjectile> birdProjectiles;
    public ArrayList<AppleFragment[] > appleFragments;
    public ArrayList<GainLabel> gainLabels;

    private MenuButton menuButtons[] = {
            new MenuButton(11, 702, 43),
            new MenuButton(391, 5, 44),
            new MenuButton(438, 758, 45)
    };

    public GameScene() {
        newton = new Newton();

        apples = new ArrayList<Apple>();
        birds = new ArrayList<Bird>();
        trees = new Tree[]{
                new Tree(0),
                new Tree(1),
                new Tree(2)
        };
        birdProjectiles = new ArrayList<BirdProjectile>();
        appleFragments = new ArrayList<AppleFragment[]>();
        gainLabels = new ArrayList<GainLabel>();
    }

    public void act(float delta) {
        if (ScoreManager.manager.popIsGameSceneRefreshRequested())
            refresh();

        //ScoreManager.manager.writeLastRemindedTime();

        // Apple and bird spawning
        for (int i = 0; i < trees.length; i++) {
            boolean[] spawnRequests = trees[i].act();

            if (spawnRequests[0] && ScoreManager.manager.getTreeLvl(i) >= 0) {
                float[] coord = trees[i].getRandomPoint();
                apples.add(new Apple(coord[0], coord[1]-36, SpecialManager.manager.isEarthquakeRunning()));
                trees[i].anAppleHasSpawned();
            }
            if (spawnRequests[1] && ScoreManager.manager.isItemBought(0, 9+i)) {
                birds.add(new Bird(i, 0));
                trees[i].aBirdHasSpawned(0);
            }
            if (spawnRequests[2] && ScoreManager.manager.isItemBought(0, 12)) {
                birds.add(new Bird(i, 1));
                trees[i].aBirdHasSpawned(1);
            }
            if (spawnRequests[3] && ScoreManager.manager.isItemBought(0, 12)) {
                birds.add(new Bird(i, 2));
                trees[i].aBirdHasSpawned(2);
            }

        }

        // Bird management
        for (int i = 0; i < birds.size(); i++)
            birds.get(i).act();

        // Bird projectile spawning management
        for (int i = 0; i < birds.size(); i++) {
            if(birds.get(i).popProjectileToBeSpawned()) {
                float[] spawnCoords = birds.get(i).coord(TimeUtils.millis());
                birdProjectiles.add(new BirdProjectile(spawnCoords[0]+10, spawnCoords[1]+10, 300));
            }
        }

        // Apple movement management
        for (int i = 0; i < apples.size(); i++)
            apples.get(i).act(delta);

        // Bird projectile management
        for (int i = 0; i < birdProjectiles.size(); i++)
            birdProjectiles.get(i).act(delta);

        // Magnet management
        int magnetLevel = ScoreManager.manager.getMagnetLevel();
        if(magnetLevel >= 0) {
            for (int i = 0; i < apples.size(); i++) {
                if(apples.get(i).getX() < Newton.MAGNET_CENTERX)
                    apples.get(i).addXSpeed(1.8f* Items.magnetForce[magnetLevel] * (float)Math.pow((Newton.MAGNET_CENTERX - apples.get(i).getX())/480, 2f));
                else
                    apples.get(i).addXSpeed(-Items.magnetForce[magnetLevel] * (float)Math.pow((Newton.MAGNET_CENTERX - apples.get(i).getX())/480, 2f));


                //apples.get(i).addYSpeed(Items.magnetForce[magnetLevel]*( - apples.get(i).getX())/10.f);
            }

            for (int i = 0; i < birdProjectiles.size(); i++) {
                if(birdProjectiles.get(i).getX() < Newton.MAGNET_CENTERX)
                    birdProjectiles.get(i).addXSpeed(15*1.8f*Items.magnetForce[magnetLevel] * (float)Math.pow((Newton.MAGNET_CENTERX - birdProjectiles.get(i).getX())/480, 2f));
                else
                    birdProjectiles.get(i).addXSpeed(15*-Items.magnetForce[magnetLevel] * (float)Math.pow((Newton.MAGNET_CENTERX - birdProjectiles.get(i).getX())/480, 2f));

                //birdProjectiles.get(i).addYSpeed(Items.magnetForce[magnetLevel]*( - birdProjectiles.get(i).getX())/10.f);
            }

        }

        // AppleFragment management
        for (int i = 0; i < appleFragments.size(); i++) {
            if (appleFragments.get(i)[0].isCollapseRequested())
                appleFragments.remove(i);
        }
        for (int i = 0; i < appleFragments.size(); i++) {
            for (int j = 0; j < appleFragments.get(i).length; j++) {
                appleFragments.get(i)[j].act(delta);
            }
        }
        // GainLabel management
        for (int i = 0; i < gainLabels.size(); i++) {
            if (gainLabels.get(i).isCollapseRequested())
                gainLabels.remove(i);
        }
        for (int i = 0; i < gainLabels.size(); i++) {
            gainLabels.get(i).act(delta);
        }

        // Collision management
        for (int i = 0; i < apples.size(); i++) {
            for(FRect r : newton.getRects()) {
                if (CollisionManager.isCollision(r, apples.get(i).getRect())) {
                    if (apples.get(i).canStillBounce()) {
                        addScore(apples.get(i).getX() + apples.get(i).getRect().getWidth() / 2, 100, apples.get(i).getValue());
                        apples.get(i).bounce();
                    }
                    else {
                        appleFragments.add(apples.get(i).explode());
                        addScore(apples.get(i).getX() + apples.get(i).getRect().getWidth() / 2, 100, apples.get(i).getValue());
                        apples.remove(i);
                        break;
                    }
                }
            }

            try {

                if (apples.get(i).getY() <= 0) {
                    appleFragments.add(apples.get(i).explode());
                    apples.remove(i);
                }
            } catch (IndexOutOfBoundsException e) {

            }
        }

        for (int i = 0; i < birdProjectiles.size(); i++) {
            for(FRect r : newton.getRects()) {
                if (CollisionManager.isCollision(r, birdProjectiles.get(i).getRect())) {
                    addScore(birdProjectiles.get(i).getX() + birdProjectiles.get(i).getRect().getWidth() / 2, 100, birdProjectiles.get(i).getValue());
                    birdProjectiles.remove(i);
                    break;
                }
            }

            if(birdProjectiles.isEmpty())
                break;

            try {
                if (birdProjectiles.get(i).getY() < 0)
                    birdProjectiles.remove(i);
            } catch (IndexOutOfBoundsException e) {

            }
        }

        for (int i = 0; i < appleFragments.size(); i++) {
            for(FRect r : newton.getRects()) {
                for(int j = 0; j < appleFragments.get(i).length; j++) {
                    if (appleFragments.get(i)[j].isEnabled() && CollisionManager.isCollision(r, appleFragments.get(i)[j].getRect())) {
                        addScore(appleFragments.get(i)[j].getRect().getX() + appleFragments.get(i)[j].getRect().getWidth() / 2, appleFragments.get(i)[j].getRect().getY(), AppleFragment.APPLE_FRAGMENT_VALUE);
                        appleFragments.get(i)[j].disable();

                        break;
                    }
                }
            }
        }


        // Bird removal management
        for (int i = 0; i < birds.size(); i++) {
            if (birds.get(i).isCollapseRequested()) {
                birds.remove(i);
            }
        }


        // Button management

        if (InputManager.manager.popTouched()) {
            int[] coord = InputManager.manager.getCoord();

            //System.out.println(coord[0]+", "+coord[1]);

            for(int i = 0; i < menuButtons.length; i++) {
                if(CollisionManager.isInRect(coord[0], coord[1], menuButtons[i].getRect())) {
                    menuButtons[i].touch(null);
                    break;
                }
            }

            if(ScoreManager.manager.getBoughtItemsNb() < 2)
                return;

            for(int i = 0 ; i < 3; i++) {
                if (CollisionManager.isInDisk(trees[i].getCircleCenter(), trees[i].getCircleRadius(), coord)) {
                    apples.add(new Apple(coord[0]-16, coord[1]-16, SpecialManager.manager.isEarthquakeRunning()));
                    break;
                }
            }


        }

        //System.out.println("act");
        UpgradeTimer.timer.act(delta);

        // Back button management

        if(InputManager.manager.popBackPressed())
            System.exit(0);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(TextureManager.manager.background, 0, 0);

        for(int i = 0; i < trees.length; i++) {
            if(ScoreManager.manager.getTreeLvl(Tree.permutation(i)) >= 0)
                trees[Tree.permutation(i)].draw(batch);
        }

        newton.draw(batch);

        for(int i = 0; i < apples.size(); i++)
            apples.get(i).draw(batch);
        for(int i = 0; i < appleFragments.size(); i++) {
            for(int j = 0; j < appleFragments.get(i).length; j++)
                appleFragments.get(i)[j].draw(batch);
        }

        for(int i = 0; i < gainLabels.size(); i++)
            gainLabels.get(i).draw(batch);
        batch.setColor(1, 1, 1, 1);

        for(int i = 0; i < 3; i++) {
            if (ScoreManager.manager.isItemBought(0, 9+i)) {
                batch.draw(TextureManager.manager.nest, Items.nestCoords[i][0][0], Items.nestCoords[i][0][1]);
            }
            if (ScoreManager.manager.isItemBought(0, 12)){
                batch.draw(TextureManager.manager.nest, Items.nestCoords[i][1][0], Items.nestCoords[i][1][1]);
                batch.draw(TextureManager.manager.nest, Items.nestCoords[i][2][0], Items.nestCoords[i][2][1]);
            }
        }

        for(int i = 0; i < birds.size(); i++)
            birds.get(i).draw(batch);
        for(int i = 0; i < birdProjectiles.size(); i++)
            birdProjectiles.get(i).draw(batch);

        // ---------------------------    HUD    ---------------------------

        UpgradeTimer.timer.draw(batch);

        batch.draw(TextureManager.manager.getTexture(6), 0, 0); // The bar on the top

        batch.setColor(1, 0, 0, 1);
        TextureManager.manager.priceFont.draw(batch, "Score : " + ScoreManager.manager.getScore(), 130, 780);
        batch.setColor(1, 1, 1, 1);

        menuButtons[0].draw(batch);
        menuButtons[2].draw(batch);

        if(!SoundManager.isMusicMuted())
            batch.draw(TextureManager.manager.notMuted, 438, 758);

        SpecialManager.manager.draw(batch, menuButtons[1]);

        SpecialManager.manager.drawDarkness(batch);
        ParticleManager.manager.draw(batch);
    }

    public void addScore(float x, float y, int difference) {
        gainLabels.add(new GainLabel(x, y-10+ Randomizer.random.nextInt(20), difference));
        ScoreManager.manager.addScore(difference);
        //System.out.println(ScoreManager.manager.getScore());
    }

    public void refresh() {
        trees[0] = new Tree(0);
        trees[1] = new Tree(1);
        trees[2] = new Tree(2);
    }

    public void onResume() {
        trees[0] = new Tree(0);
        trees[1] = new Tree(1);
        trees[2] = new Tree(2);

        if(ScoreManager.manager.getBoughtItemsNb() >= 2)
            ScoreManager.manager.addScore((int) ( ScoreManager.manager.amountPerSec[ScoreManager.manager.getBoughtItemsNb()]*Math.min(ScoreManager.manager.getBucketDuration(), (TimeUtils.millis() - ScoreManager.manager.readLastRemindedTime())/1000)));

        System.out.println("Time elapsed : "+(TimeUtils.millis()-ScoreManager.manager.readLastRemindedTime())/1000+" s");
        ScoreManager.manager.writeLastRemindedTime();
    }

}
