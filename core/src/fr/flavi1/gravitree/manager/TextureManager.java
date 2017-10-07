package fr.flavi1.gravitree.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Created by Flavien on 17/06/2015.
 */
public class TextureManager {

    public BitmapFont gainFont;
    public BitmapFont scoreFont;
    public BitmapFont timerFont;
    public BitmapFont descrFont;
    public BitmapFont explFont;
    public BitmapFont priceFont;

    public static TextureManager manager = new TextureManager();
    public AssetManager assetManager;

    private boolean generalLoaded;
    private boolean loadScreenLoaded;

    private Texture[] textures;
    private Texture[] appleTextures;
    private Texture[][] treeTextures;
    private Texture[] buttonTextures;
    private Texture[] shopBackgroundTextures;

    public Texture background;

    public Texture barPoint;
    public Texture timerGlass;

    public Texture specialButtonHider;
    public Texture specialWaitPoint;
    public Texture specialReadyPoint;
    public Texture specialGlass;

    public Texture hand;
    public Texture notMuted;

    public Texture bird0;
    public Texture bird1;
    public Texture birdProjectile;
    public Texture nest;

    public Texture bomb;
    public Texture bombDarkness;

    public Texture loadScreen;

    public TextureManager() {
        generalLoaded = false;
        loadScreenLoaded = false;

        assetManager = new AssetManager();
    }

    public void preload() {
        loadScreen = new Texture("loadScreen.png");
    }

    public void reload() {
        generalLoaded = false;
        loadScreenLoaded = false;

        loadLoadScreen();
        loadGeneral();
    }

    public void loadLoadScreen() {
        if (loadScreenLoaded)
            return;
        loadScreenLoaded = true;

        gainFont = new BitmapFont(Gdx.files.internal("font/gainFont.fnt"), false);
        scoreFont = new BitmapFont(Gdx.files.internal("font/scoreFont.fnt"), false);
        timerFont = new BitmapFont(Gdx.files.internal("font/timerFont.fnt"), false);
        descrFont = new BitmapFont(Gdx.files.internal("font/descrFont.fnt"), false);
        explFont = new BitmapFont(Gdx.files.internal("font/explFont.fnt"), false);
        priceFont = new BitmapFont(Gdx.files.internal("font/priceFont.fnt"), false);

        assetManager.finishLoading();
    }

    public void loadGeneral() {
        if (generalLoaded)
            return;
        generalLoaded = true;

        /*assetManager.load("icon0.png", Texture.class);
        assetManager.load("icon1.png", Texture.class);

        assetManager.load("button0.png", Texture.class);
        assetManager.load("button1.png", Texture.class);
        assetManager.load("button2.png", Texture.class);*/

        treeTextures = new Texture[][] {
                {
                        new Texture("tree0,0.png"),
                        new Texture("tree0,1.png"),
                        new Texture("tree0,2.png"),
                },
                {
                        new Texture("tree1,0.png"),
                        new Texture("tree1,1.png"),
                        new Texture("tree1,2.png"),
                },
                {
                        new Texture("tree2,0.png"),
                        new Texture("tree2,1.png"),
                        new Texture("tree2,2.png"),
                }
        };

        buttonTextures = new Texture[] {
                new Texture("button0.png"),
                new Texture("button1.png"),
                new Texture("button2.png"),
                new Texture("button3.png"),
                new Texture("button4.png"),
                new Texture("button5.png"),
                new Texture("button6.png"),
                new Texture("button7.png"),
                new Texture("button8.png"),
                new Texture("button9.png"),
                new Texture("button10.png"),
                new Texture("button11.png"),
                new Texture("button12.png"),
                new Texture("button13.png"),
                new Texture("button14.png"),
                new Texture("button15.png"),
                new Texture("button16.png"),
                new Texture("button17.png"),
                new Texture("button18.png"),
                new Texture("button19.png"),
                new Texture("button20.png"),
                new Texture("button21.png"),
                new Texture("button22.png"),
                new Texture("button23.png"),
                new Texture("button24.png"),
                new Texture("button25.png"),
                new Texture("button26.png"),
                new Texture("button27.png"),
                new Texture("button28.png"),
                new Texture("button29.png"),
                new Texture("button30.png"),
                new Texture("button31.png"),
                new Texture("button32.png"),
                new Texture("button33.png"),
                new Texture("button34.png"),
                new Texture("button35.png"),
                new Texture("button36.png"),
                new Texture("button37.png"),
                new Texture("button38.png"),
                new Texture("button39.png"),
                new Texture("button40.png"),
                new Texture("button41.png"),
                new Texture("button42.png"),
                new Texture("button43.png"),
                new Texture("button44.png"),
                new Texture("button45.png"),
        };
        textures = new Texture[] {
                new Texture("texture0.png"),
                new Texture("texture1.png"),
                new Texture("texture2.png"),
                new Texture("texture3.png"),
                new Texture("texture4.png"),
                new Texture("texture5.png"),
                new Texture("texture6.png"),
                new Texture("texture7.png")
        };
        appleTextures = new Texture[] {
                new Texture("apple0.png"),
                new Texture("apple1.png"),
                new Texture("apple2.png"),
                new Texture("apple3.png"),
                new Texture("apple4.png"),
        };
        shopBackgroundTextures = new Texture[] {
                new Texture("shopBackground0.png"),
                new Texture("shopBackground1.png"),
                new Texture("shopBackground2.png")
        };


        background = new Texture("background.png");

        barPoint = new Texture("barPoint.png");
        timerGlass = new Texture("timerGlass.png");

        specialButtonHider = new Texture("specialButtonHider.png");
        specialWaitPoint = new Texture("specialWaitPoint.png");
        specialReadyPoint = new Texture("specialReadyPoint.png");
        specialGlass = new Texture("specialGlass.png");

        hand = new Texture("hand.png");
        notMuted = new Texture("notMuted.png");

        bird0 = new Texture("bird0.png");
        bird1 = new Texture("bird1.png");
        birdProjectile = new Texture("birdProjectile.png");
        nest = new Texture("nest.png");

        bomb = new Texture("bomb.png");
        bombDarkness = new Texture("bombDarkness.png");

        /*for(int i = 0; i < 7; i++)
            assetManager.load("texture"+i+".png", Texture.class);

        assetManager.finishLoading();*/
    }

    public Texture getButtonTexture(int id) {
        return buttonTextures[id];
        //return assetManager.get("button"+id+".png", Texture.class);
    }

    public Texture getTreeTexture(int treeId, int treeLevel) {
        return treeTextures[treeId][Math.min(treeLevel, 2)];
        //return assetManager.get("icon"+id+".png", Texture.class);
    }

    public Texture getTexture(int id) {
        return textures[id];
        //return assetManager.get("texture"+id+".png", Texture.class);
    }

    public Texture getAppleTexture(int type) {
        return appleTextures[type];
        //return assetManager.get("texture"+id+".png", Texture.class);
    }

    public Texture getShopBackground(int menuId) {
        return shopBackgroundTextures[menuId];
    }
}
