package fr.flavi1.gravitree;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import fr.flavi1.gravitree.Tools.Randomizer;
import fr.flavi1.gravitree.manager.*;
import fr.flavi1.gravitree.scene.GameScene;
import fr.flavi1.gravitree.scene.ShopScene;

public class Game extends ApplicationAdapter {

	public enum State {
		GAME, SPLASH, MENU, SHOP, CREDITS
	}

	public static Game game = new Game();

	private OrthographicCamera camera;
	private Viewport viewport;

	private State currentState;

	private static State newStateRequested;
	private static boolean isNewStateRequested;

	private ShopScene shopScene;
	private GameScene gameScene;

	private boolean toLoad;
    private boolean toLoad2;
	private boolean loaded;

	SpriteBatch batch;

	public static int w;
	public static int h;

	public Game() {
		w = 800;
		h = 480;
		toLoad = true;
        toLoad2 = false;
		loaded = false;
	}

	@Override
	public void create () {

        System.out.println("t-3");

        Gdx.input.setCatchBackKey(true);

		isNewStateRequested = false;
		
		batch = new SpriteBatch();

        TextureManager.manager.preload();

		camera = new OrthographicCamera();
		viewport = new StretchViewport(480, 800, camera);
		viewport.apply();
		camera.position.set(240, 400, 0);
    }

	public void load() {
        System.out.println("t-2");

        SoundManager.load();
		TextureManager.manager.loadLoadScreen();
		TextureManager.manager.loadGeneral();

		ScoreManager.manager.load();
		Gdx.input.setInputProcessor(InputManager.manager);
		//SoundManager.refreshMusic(ScoreManager.manager.getBoughtItemsNb());
		//SoundManager.refreshMusic(ScoreManager.manager.getBoughtItemsNb());

		System.out.println("t-1");

		DidactitialManager.manager.load();

		ParticleManager.manager.load();

		shopScene = new ShopScene();
		gameScene = new GameScene();

		currentState = State.SPLASH;

		setState(State.GAME);
		gameScene.onResume();

		toLoad = false;
		loaded = true;

		System.out.println("t0");
	}

	@Override
	public void render () {
		if(toLoad2) {
            load();

            toLoad2 = false;
            return;
        }

		if(toLoad) {
            camera.update();
            camera.position.set(240, 400, 0);

            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            batch.begin();
            batch.setProjectionMatrix(camera.combined);

            batch.draw(TextureManager.manager.loadScreen, 0, 0);
            System.out.println("Splash");
            batch.end();

            toLoad2 = true;

            return;
        }
		
		boolean earthquakeRunning = SpecialManager.manager.isEarthquakeRunning();
		if(currentState == State.GAME && earthquakeRunning)
			camera.position.set(240 - SpecialManager.EARTHQUAKE_INTENSITY/2 + Randomizer.random.nextInt(SpecialManager.EARTHQUAKE_INTENSITY), 400- SpecialManager.EARTHQUAKE_INTENSITY/2 + Randomizer.random.nextInt(SpecialManager.EARTHQUAKE_INTENSITY), 0);
		else
			camera.position.set(240, 400, 0);

		camera.update();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// If a state change is requested

		if(isNewStateRequested)
			setState(newStateRequested);

		// Splash screen management

		/*if(currentState == State.SPLASH && TextureManager.manager.assetManager.update()) {
			setState(State.GAME);
		}
		else if(!TextureManager.manager.assetManager.update())
			TextureManager.manager.assetManager.finishLoading();
		else if(currentState == State.SPLASH)
			return;*/

		ScoreManager.manager.act();

		batch.begin();
		batch.setProjectionMatrix(camera.combined);

		// Shop scene

		if(currentState == State.SHOP) {
			shopScene.act(Gdx.graphics.getDeltaTime());
			shopScene.draw(batch);
		}

		// Game scene

		else if(currentState == State.GAME) {
			gameScene.act(Gdx.graphics.getDeltaTime());
			gameScene.draw(batch);
			ParticleManager.manager.act(Gdx.graphics.getDeltaTime());
            SpecialManager.manager.act(Gdx.graphics.getDeltaTime());
		}
        DidactitialManager.manager.act(Gdx.graphics.getDeltaTime());
        DidactitialManager.manager.draw(batch);

		batch.end();

		if(earthquakeRunning)
			camera.position.set(240, 400, 0);

	}

	// State management

	public void setState(State newState) {
		isNewStateRequested = false;

		if(currentState == newState)
			return;

		if(newState == State.SHOP) {
			shopScene.refresh();
		}
		if(newState == State.GAME) {
			gameScene.onResume();
		}

		currentState = newState;
		DidactitialManager.manager.onStateChanged(newState);
	}

	public static void requestStateChange(State newState) {
		isNewStateRequested = true;
		newStateRequested = newState;
	}

	@Override
	public void resize(int width, int height) {
		w = width;
		h = height;
		TextureManager.manager.reload();
		try {
			viewport.update(width, height);
			camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	public ShopScene getShopScene() {
		return shopScene;
	}

	public GameScene getGameScene() {
		return gameScene;
	}

	/*@Override
	public void resume() {
		TextureManager.manager.loadGeneral();

		System.out.println("Resuming !");

		super.resume();
	}*/

	@Override
	public void dispose() {
		ScoreManager.manager.writeScore();
	}
}
