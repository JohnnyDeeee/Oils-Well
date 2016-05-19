package johndoe.game.desktop;

import johndoe.game.Config;
import johndoe.game.OilsWell;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher implements Config{
	public static void main (String[] arg) {
		LwjglApplicationConfiguration configuration = new LwjglApplicationConfiguration();
		
		configuration.width = Config.WIDTH;
		configuration.height = Config.HEIGHT;
		configuration.title = Config.TITLE + " " + Config.VERSION;
		configuration.resizable = Config.RESIZABLE;
		configuration.useGL30 = Config.USEGL30;
		configuration.vSyncEnabled = Config.VSYNC;
		
		new LwjglApplication(new OilsWell(), configuration);
	}
}
