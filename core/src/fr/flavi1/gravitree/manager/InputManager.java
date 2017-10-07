package fr.flavi1.gravitree.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import fr.flavi1.gravitree.Game;

/**
 * Created by Flavien on 18/06/2015.
 */
public class InputManager implements InputProcessor {

    public static InputManager manager = new InputManager();

    public int[] coord = {0, 0};

    private boolean touchToRead;
    private boolean backPressed;

    public InputManager() {
        touchToRead = false;
        backPressed = false;

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.BACK || keycode == Input.Keys.BACKSPACE) {
            backPressed = true;
            return true;
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        //System.out.println(character);

        if(character == Input.Keys.BACK || character == Input.Keys.BACKSPACE) {
            backPressed = true;
            return true;
        }

        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(button == Input.Keys.BACK) {
            backPressed = true;
            return true;
        }

        coord[0] = screenX;
        coord[1] = Gdx.graphics.getHeight()-screenY;
        touchToRead = true;

        //SpecialManager.manager.launchSpecial(SpecialManager.SpecialType.EARTHQUAKE);

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public boolean popTouched() {
        if(!touchToRead)
            return false;
        touchToRead = false;
        return true;
    }

    public boolean popBackPressed() {
        if(!backPressed)
            return false;
        backPressed = false;
        return true;
    }

    public int[] getCoord() {
        return new int[]{480*coord[0] / Game.w, 800*coord[1]/ Game.h};
    }

}
