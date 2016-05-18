package johndoe.game;

import johndoe.game.font.BmapFont;
import johndoe.game.screens.Splash;

import com.badlogic.gdx.Game;

public class OilsWell extends Game {
	
	@Override
	public void create () {
		BmapFont.init(); // Initialize BitmapFonts
		setScreen(new Splash()); //This sets the screen to the Splash class
	}

	@Override
	public void render () {
		super.render();
	}
}
