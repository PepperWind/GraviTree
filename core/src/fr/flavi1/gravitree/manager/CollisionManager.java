package fr.flavi1.gravitree.manager;

import fr.flavi1.gravitree.Tools.FRect;

/**
 * Created by Flavien on 17/06/2015.
 */
public class CollisionManager {

    public static final float GROUND_HEIGHT = 0;

    public static boolean isInRect(float x, float y, FRect r) {
        return x <= r.getX()+r.getWidth()
                && x >= r.getX()
                && y <= r.getY()+r.getHeight()
                && y >= r.getY();
    }

    public static boolean isCollision(FRect r1, FRect r2) {
        return !(r1.getX()+r1.getWidth() < r2.getX()
                || r2.getX()+r2.getWidth() < r1.getX()
                || r1.getY()+r1.getHeight() < r2.getY()
                || r2.getY()+r2.getHeight() < r1.getY());
    }

    public static boolean isVerticalCollision(FRect r1, FRect r2) {
        return !(r1.getY()+r1.getHeight() < r2.getY()
                || r2.getY()+r2.getHeight() < r1.getY());
    }

    public static boolean isCollisionWithGround(FRect rect) {
        if(rect.getY() <= GROUND_HEIGHT)
            return true;
        return false;
    }

    public static boolean isInDisk(float[] center, float radius, int[] coord) {
        return Math.sqrt((center[0]-coord[0])*(center[0]-coord[0]) + (center[1]-coord[1])*(center[1]-coord[1])) <= radius;
    }

}
