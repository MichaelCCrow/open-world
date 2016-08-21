package scenes;

import java.io.IOException;

import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;

//import game.AudioManager;

public abstract class Scene {
	private boolean doExit = false;

	// return false if the game should be quit
	public abstract boolean drawFrame(float delta) throws IOException;

	// null typically means Game should load menu
	public Scene nextScene() {
		return null;
	}

	protected void exit() {
		doExit = true;
	};

	// returns false when game should be exited
	public boolean go() {
		long lastloop = (Sys.getTime() * 1000 / Sys.getTimerResolution());

		boolean keepGoing = true;
		do {
			Display.sync(60); // 60 FPS
			long now = (Sys.getTime() * 1000 / Sys.getTimerResolution());
			long delta = now - lastloop;
			lastloop = now;

			try {
				keepGoing = drawFrame(delta);
			} catch (IOException e) {
				e.printStackTrace();
			}

			// UPDATE DISPLAY
			Display.update();
			// AudioManager.getInstance().update();

			if (Display.isCloseRequested() || doExit) {
				return false;
			}

		} while (keepGoing);

		return true;
	}

}
