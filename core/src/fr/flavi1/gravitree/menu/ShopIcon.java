package fr.flavi1.gravitree.menu;

import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Flavien on 17/06/2015.
 */
public class ShopIcon extends Actor {

    public static final float SHOP_ICON_X = 26;
    public static final float SHOP_ICON_Y = 23;

    private float x;
    private float y;
    private int id;
    private int subMenuId;

    public ShopIcon(int mid, int mSubMenuId) {
        x = SHOP_ICON_X;
        y = SHOP_ICON_Y;
        id = mid;
        subMenuId = mSubMenuId;
    }

    public void update(int psubMenuId, int pid) {
        id = pid;
        subMenuId = psubMenuId;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getX() {
        return x;
    }

}