package Engine.Managers;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    private final Map<String, Clip> soundClips = new HashMap<>();

    public SoundManager() {
        // Register sounds by ID and path
        registerSound("test", "/sounds/test.wav");
        registerSound("pause", "/sounds/pause.wav");
        registerSound("death", "/sounds/death.wav");
    }

    public void registerSound(String id, String path) {
        try {
            URL soundURL = getClass().getResource(path);

            if(soundURL == null) {
                System.err.println("Sound file not found: " + path);
                return;
            }

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            soundClips.put(id, clip);
        } catch(UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Failed to load the sound: " + id);
            e.printStackTrace();
        }
    }

    public void play(String id) {
        Clip clip = soundClips.get(id);
        if(clip == null) return;
        clip.start();
    }

    public void loop(String id) {
        Clip clip = soundClips.get(id);
        if (clip == null) return;
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop(String id) {
        Clip clip = soundClips.get(id);
        if (clip == null) return;
        clip.setFramePosition(0);                       // Set the clip back to the beginning ready for next play
        clip.stop();
    }

    public void reset(String id) {
        Clip clip = soundClips.get(id);

        if(clip == null) return;
        if(!clip.isRunning()) {
            clip.setFramePosition(0);
        }
    }

    public void closeAll(boolean releaseResources) {
        for(Clip clip : soundClips.values()) {
            if(clip != null && clip.isOpen()) {
                clip.setFramePosition(0);               // Unlikely to use the resources after unloading, but setting back to 0 in case we do
                clip.stop();                            // Stop any playing clips

                if(releaseResources) {
                    clip.close();                       // Close the clips to clear resources
                }
            }
        }

        if(releaseResources) {
            soundClips.clear();                         // Clearing up resources
        }
    }
}