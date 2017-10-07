package fr.flavi1.gravitree.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import fr.flavi1.gravitree.Game;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.hideStatusBar = true;
		config.useWakelock = true;
		initialize(Game.game, config);
		onTempStop();
		onTempRestart();
	}

	public void onTempStop(){
		super.onStart();
	}

	public void onTempRestart(){
		super.onRestart();
	}

	@Override
	public void onStop(){
		super.onStop();
	}
}
