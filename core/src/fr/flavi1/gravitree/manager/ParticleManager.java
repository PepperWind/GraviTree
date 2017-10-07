package fr.flavi1.gravitree.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.flavi1.gravitree.entity.Newton;

/**
 * Created by Flavien on 20/08/2015.
 */
public class ParticleManager {

    public static final ParticleManager manager = new ParticleManager();

    private ParticleEffect bombParticleEffect;

    private boolean bombToPlay;

    public ParticleManager() {
    }

    public void load() {
        bombToPlay = false;

        bombParticleEffect = new ParticleEffect();
        bombParticleEffect.load(Gdx.files.internal("particles/bomb.party"), Gdx.files.internal(""));
    }

    public void explodeBomb() {
        bombToPlay = true;

        bombParticleEffect.getEmitters().first().setPosition(Newton.NEWTON_X+Newton.NEWTON_W/2, Newton.NEWTON_Y+Newton.NEWTON_H+10);
        bombParticleEffect.start();
    }

    public void act(float delta) {
        if(bombToPlay) {
            if(bombParticleEffect.isComplete())
                bombToPlay = false;
            else
                bombParticleEffect.update(delta);
        }
    }

    public void draw(SpriteBatch batch) {
        if(bombToPlay)
            bombParticleEffect.draw(batch);
    }
}
