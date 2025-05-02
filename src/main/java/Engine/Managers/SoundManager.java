package Engine.Managers;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class SoundManager {
    Clip clip;
    URL[] sounds = new URL[30];

    public SoundManager() {
        // Loading some sounds hard-coded for now
        sounds[0] = getClass().getResource("/sounds/test.wav");
    }

    public void setFile(int i) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(sounds[i]);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch(Exception ignored) {

        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }
}