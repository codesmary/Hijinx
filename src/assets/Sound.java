package assets;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

//plays and stops sound clips, once, multiple times, or continuously
public class Sound {
	private Clip clip;

	public Sound(String path) {
		try {
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(this.getClass().getResourceAsStream(path));
			this.clip = AudioSystem.getClip();
			this.clip.open(audioStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void play() {
		this.clip.start();
	}

	public void stop() {
		this.clip.stop();
	}

	public void playLoop(int repeat) {
		this.clip.loop(repeat);
	}

	public void playLoop() {
		this.playLoop(Clip.LOOP_CONTINUOUSLY);
	}

}