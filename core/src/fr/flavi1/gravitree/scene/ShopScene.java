package fr.flavi1.gravitree.scene;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.flavi1.gravitree.Game;
import fr.flavi1.gravitree.Tools.Items;
import fr.flavi1.gravitree.manager.CollisionManager;
import fr.flavi1.gravitree.manager.InputManager;
import fr.flavi1.gravitree.manager.ScoreManager;
import fr.flavi1.gravitree.manager.TextureManager;
import fr.flavi1.gravitree.menu.MenuButton;
import fr.flavi1.gravitree.menu.UpgradeTimer;

/**
 * Created by Flavien on 17/06/2015.
 */
public class ShopScene {

    public static final float CURSOR_THICKNESS = 4;

    private int currentMenuId;

    private int selectedId;

    private MenuButton buyButton;

    private MenuButton[][] buttons = {
            {
                    new MenuButton(208, 176, 2),
                    new MenuButton(120, 266, 3),
                    new MenuButton(296, 266, 4),
                    new MenuButton(120, 356, 5),
                    new MenuButton(208, 356, 6),
                    new MenuButton(296, 356, 7),
                    new MenuButton(120, 446, 8),
                    new MenuButton(208, 446, 9),
                    new MenuButton(296, 446, 10),
                    new MenuButton(120, 536, 11),
                    new MenuButton(208, 536, 12),
                    new MenuButton(296, 536, 13),
                    new MenuButton(208, 630, 14),

                    new MenuButton(0, 205, 41),
                    new MenuButton(423, 205, 42)
            },
            {
                    new MenuButton(208, 176, 15),
                    new MenuButton(120, 266, 16),
                    new MenuButton(296, 266, 17),
                    new MenuButton(120, 356, 18),
                    new MenuButton(208, 356, 19),
                    new MenuButton(296, 356, 20),
                    new MenuButton(120, 446, 21),
                    new MenuButton(208, 446, 22),
                    new MenuButton(296, 446, 23),
                    new MenuButton(120, 536, 24),
                    new MenuButton(208, 536, 25),
                    new MenuButton(296, 536, 26),
                    new MenuButton(208, 630, 27),

                    new MenuButton(0, 205, 41),
                    new MenuButton(423, 205, 42)
            },
            {
                    new MenuButton(208, 176, 28),
                    new MenuButton(120, 266, 29),
                    new MenuButton(296, 266, 30),
                    new MenuButton(120, 356, 31),
                    new MenuButton(208, 356, 32),
                    new MenuButton(296, 356, 33),
                    new MenuButton(120, 446, 34),
                    new MenuButton(208, 446, 35),
                    new MenuButton(296, 446, 36),
                    new MenuButton(120, 536, 37),
                    new MenuButton(208, 536, 38),
                    new MenuButton(296, 536, 39),
                    new MenuButton(208, 630, 40),

                    new MenuButton(0, 205, 41),
                    new MenuButton(423, 205, 42)
            }
    };

    public ShopScene() {
        currentMenuId = 0;
        selectedId = 0;

        buyButton = new MenuButton(350, 10, 0);
    }

    public void setCurrentMenuId(int id) {
        if(currentMenuId == id)
            return;

        selectedId = 0;
        currentMenuId = id;
    }

    public int getCurrentMenuId() {
        return currentMenuId;
    }

    public void refresh() {
        // TODO
    }

    /*public MenuButton[] getButtons() {
        MenuButton[] boutons = new MenuButton[menuSelectButtons.length+singularities[currentMenuId].length];

        for(int i = 0; i < singularities[currentMenuId].length; i++) {
            boutons[i] = singularities[currentMenuId][i].getButton();
        }
        for(int i = singularities[currentMenuId].length; i < menuSelectButtons.length+singularities[currentMenuId].length; i++) {
            boutons[i] = menuSelectButtons[i-singularities[currentMenuId].length];
        }

        return boutons;
    }*/

    public void act(float delta) {
        if(InputManager.manager.popTouched()) {
            int[] coord = InputManager.manager.getCoord();

            for(int i = 0; i < buttons[currentMenuId].length; i++) {
                if(CollisionManager.isInRect(coord[0], coord[1], buttons[currentMenuId][i].getRect())) {
                    if(Items.arePrerequiesitesFilled(currentMenuId, i) || buttons[currentMenuId][i].getId() >= 41)
                        buttons[currentMenuId][i].touch(this);
                    return;
                }
            }
            if(CollisionManager.isInRect(coord[0], coord[1], buyButton.getRect()))
                buyButton.touch(this);
        }

        if(InputManager.manager.popBackPressed())
            Game.requestStateChange(Game.State.GAME);

        UpgradeTimer.timer.act(delta);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(TextureManager.manager.getShopBackground(currentMenuId), 0, 0);
        UpgradeTimer.timer.drawInShop(batch);

        for(int i = 0; i < buttons[currentMenuId].length; i++) {
            buttons[currentMenuId][i].draw(batch);

            if(!Items.arePrerequiesitesFilled(currentMenuId, i)) {
                batch.draw(TextureManager.manager.getTexture(7), buttons[currentMenuId][i].getX(), buttons[currentMenuId][i].getY());
            }
        }
        batch.draw(TextureManager.manager.getTexture(2), buttons[currentMenuId][selectedId].getX()-CURSOR_THICKNESS, buttons[currentMenuId][selectedId].getY()-CURSOR_THICKNESS);

        // Description

        //batch.draw(TextureManager.manager.getTexture(0), 0, 0);
        batch.draw(TextureManager.manager.getButtonTexture(2+13*currentMenuId+selectedId), 13, 74);
        TextureManager.manager.descrFont.draw(batch, Items.buttonDescription[13*currentMenuId+selectedId], 83, 129);
        TextureManager.manager.explFont.drawMultiLine(batch, Items.buttonExplanation[13*currentMenuId+selectedId], 83, 109);

        if(!ScoreManager.manager.isItemBought(currentMenuId, selectedId))
        {
            if(ScoreManager.manager.canBuy(currentMenuId, selectedId))
                buyButton.setId(0);
            else
                buyButton.setId(1);

            buyButton.draw(batch);
            TextureManager.manager.priceFont.draw(batch, Integer.toString(Items.prices[currentMenuId][selectedId])+" $", 17, 46);
        }

    }

    public void setSelectedItem(int id) {
        selectedId = id;
    }

    public int getSelectedId() {
        return selectedId;
    }
}
