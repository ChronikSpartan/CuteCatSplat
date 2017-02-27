package chronikspartan.cutecatsplat.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import chronikspartan.cutecatsplat.CuteCatSplat;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		// System.setProperty("org.lwjgl.opengl.Display.allowSoftwareOpenGL", "true");
		config.width = CuteCatSplat.WIDTH;
		config.height = CuteCatSplat.HEIGHT;
		config.title = CuteCatSplat.TITLE;
		new LwjglApplication(new CuteCatSplat(null), config);
	}
}
