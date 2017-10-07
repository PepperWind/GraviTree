package fr.flavi1.gravitree.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import fr.flavi1.gravitree.Tools.Randomizer;

/**
 * Created by Flavien on 07/07/2015.
 */
public class SoundManager {

    //public static Music[] ambientMusics;

    private static int currentMusicId;

    public static Sound explosionSound;
    public static Sound bombFallSound;
    public static Sound earthquakeSound;
    public static Sound appleSpawnSound;
    public static Sound buySound;

    public static Sound[] appleFallSounds;

    private static boolean musicMuted; /** Music and sounds alltogether*/

    public static final int[] ambientLengths = {
            1,
            190,
            420,
            220,
            150,
            190
    };

    public static boolean isMusicMuted() {
        return musicMuted;
    }

    public static void toggleMuteMusic() {
        musicMuted = !musicMuted;
/*        if(musicMuted)
            ambientMusics[currentMusicId].pause();
        else
            ambientMusics[currentMusicId].play();*/
    }

    public static void load() {
        /*currentMusicId = -1;
        ambientMusics = new Music[] {
                Gdx.audio.newMusic(Gdx.files.internal("music/ambient0.ogg")),
                Gdx.audio.newMusic(Gdx.files.internal("music/ambient1.mp3")),
                Gdx.audio.newMusic(Gdx.files.internal("music/ambient2.ogg")),
                Gdx.audio.newMusic(Gdx.files.internal("music/ambient3.mp3")),
                Gdx.audio.newMusic(Gdx.files.internal("music/ambient4.mp3")),
                Gdx.audio.newMusic(Gdx.files.internal("music/ambient3.mp3"))
        };*/

        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sound/explosion.mp3"));
        bombFallSound = Gdx.audio.newSound(Gdx.files.internal("sound/bombFall.mp3"));
        earthquakeSound = Gdx.audio.newSound(Gdx.files.internal("sound/earthquake.mp3"));
        appleSpawnSound = Gdx.audio.newSound(Gdx.files.internal("sound/appleSpawn.mp3"));
        buySound = Gdx.audio.newSound(Gdx.files.internal("sound/buy.ogg"));

        appleFallSounds = new Sound[] {
                Gdx.audio.newSound(Gdx.files.internal("sound/appleFall0.mp3")),
                Gdx.audio.newSound(Gdx.files.internal("sound/appleFall1.mp3"))
        };
    }

    /*public static void refreshMusic(int boughtItemsNb) {

        if(boughtItemsNb < 2) {
            if(currentMusicId == 0)
                return;
            if(currentMusicId >= 0)
                ambientMusics[currentMusicId].stop();
            currentMusicId = 0;
            ambientMusics[0].play();
            ambientMusics[0].setLooping(true);
            return;
        }
        if(boughtItemsNb < 5) {
            if(currentMusicId == 1)
                return;
            if(currentMusicId >= 0)
                ambientMusics[currentMusicId].stop();
            currentMusicId = 1;
            ambientMusics[1].setLooping(true);
            ambientMusics[1].setPosition(Randomizer.random.nextInt(ambientLengths[1]));
            ambientMusics[1].play();
            return;
        }
        if(boughtItemsNb < 10) {
            if(currentMusicId == 2)
                return;
            if(currentMusicId >= 0)
                ambientMusics[currentMusicId].stop();
            currentMusicId = 2;
            ambientMusics[2].setLooping(true);
            ambientMusics[2].setPosition(Randomizer.random.nextInt(ambientLengths[2]));
            ambientMusics[2].play();
            return;
        }
        if(boughtItemsNb < 16) {
            if(currentMusicId == 3)
                return;
            if(currentMusicId >= 0)
                ambientMusics[currentMusicId].stop();
            currentMusicId = 3;
            ambientMusics[3].setLooping(true);
            ambientMusics[3].setPosition(Randomizer.random.nextInt(ambientLengths[3]));
            ambientMusics[3].play();
            return;
        }
        if(boughtItemsNb < 24) {
            if(currentMusicId == 4)
                return;
            if(currentMusicId >= 0)
                ambientMusics[currentMusicId].stop();
            currentMusicId = 4;
            ambientMusics[4].setLooping(true);
            ambientMusics[4].setPosition(Randomizer.random.nextInt(ambientLengths[4]));
            ambientMusics[4].play();
            return;
        }

    }*/
}
