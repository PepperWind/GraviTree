package fr.flavi1.gravitree.menu;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.flavi1.gravitree.Tools.FRect;
import fr.flavi1.gravitree.Game;
import fr.flavi1.gravitree.manager.*;
import fr.flavi1.gravitree.scene.ShopScene;

/**
 * Created by Flavien on 17/06/2015.
 */
public class MenuButton {

    //private boolean pressed;
    //private boolean touchPending;

    private float x;
    private float y;
    private int id;

    public MenuButton(float mx, float my, int mid) {
        //touchPending = false;

        x = mx;
        y = my;
        id = mid;
    }

    public void touch(Object scene) {
        /*if(ScoreManager.manager.isButtonPressed(id))
            return;

        ScoreManager.manager.setButtonPressed(id, true);*/

        System.out.println(id);

        switch(id) {
            case 0:
                //System.out.println("Requesting buy : "+((ShopScene) scene).getCurrentMenuId()+", "+ ((ShopScene) scene).getSelectedId());
                if(ScoreManager.manager.isItemBought(((ShopScene) scene).getCurrentMenuId(), ((ShopScene) scene).getSelectedId()))
                    break;

                if(ScoreManager.FAST_SHOP)
                    ScoreManager.manager.buyItem(((ShopScene) scene).getCurrentMenuId(), ((ShopScene) scene).getSelectedId());
                else
                    ScoreManager.manager.initiateItemBuy(((ShopScene) scene).getCurrentMenuId(), ((ShopScene) scene).getSelectedId());
                if((((ShopScene) scene).getCurrentMenuId() == 0 || ((ShopScene) scene).getCurrentMenuId() == 2) && ((ShopScene) scene).getSelectedId() == 0)
                    DidactitialManager.manager.advanceStep();
                ((ShopScene) scene).refresh();
                break;
            case 41:
                if(ScoreManager.manager.getDidactitialStep() == 0 || ScoreManager.manager.getDidactitialStep() == 1)
                    break;

                ((ShopScene) scene).setCurrentMenuId((3+(((ShopScene) scene)).getCurrentMenuId()-1) % 3);
                if(((ShopScene) scene).getCurrentMenuId() == 2 && ScoreManager.manager.getDidactitialStep() == 2)
                    DidactitialManager.manager.advanceStep();
                else if(((ShopScene) scene).getCurrentMenuId() != 2 && ScoreManager.manager.getDidactitialStep() == 3)
                    DidactitialManager.manager.setStep(2);
                break;
            case 42:
                if(ScoreManager.manager.getDidactitialStep() == 0 || ScoreManager.manager.getDidactitialStep() == 1)
                    break;

                ((ShopScene) scene).setCurrentMenuId((3 + (((ShopScene) scene)).getCurrentMenuId() + 1) % 3);
                if(((ShopScene) scene).getCurrentMenuId() == 2 && ScoreManager.manager.getDidactitialStep() == 2)
                    DidactitialManager.manager.advanceStep();
                else if(((ShopScene) scene).getCurrentMenuId() != 2 && ScoreManager.manager.getDidactitialStep() == 3)
                    DidactitialManager.manager.setStep(2);
                break;
            case 43:
                Game.requestStateChange(Game.State.SHOP);
                if(ScoreManager.manager.getDidactitialStep() == 0)
                    DidactitialManager.manager.advanceStep();
                break;
            case 44:
                if(SpecialManager.manager.isSpecialReady()) {
                    SpecialManager.manager.launchRandomSpecial();
                }
                break;
            case 45:
                SoundManager.toggleMuteMusic();
                break;
            default:

                if(id >= 2 && id <= 40) {
                    ((ShopScene) scene).setSelectedItem((id - 2) % 13);
                    return;
                }

                System.out.println("Button id not treated : "+id);
        }
    }

    public void draw(SpriteBatch batch) {
        batch.draw(TextureManager.manager.getButtonTexture(id), x, y);
    }

    public FRect getRect() {
        return new FRect(x, y, TextureManager.manager.getButtonTexture(id).getWidth(), TextureManager.manager.getButtonTexture(id).getHeight());
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setId(int mid) {
        id = mid;
    }

    public int getId() {
        return id;
    }
}
