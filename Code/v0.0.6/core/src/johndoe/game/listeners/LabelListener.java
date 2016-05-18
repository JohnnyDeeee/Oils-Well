package johndoe.game.listeners;

import johndoe.game.Config;
import johndoe.game.screens.EndGame;
import johndoe.game.screens.Highscores;
import johndoe.game.screens.Loading;
import johndoe.game.screens.MainMenu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class LabelListener extends ClickListener{

	private Actor actor;
	
	public LabelListener(Actor actor){
		this.actor = actor;
	}
	
	@Override
	public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
		actor.setColor(Color.PURPLE);
	}
	
	@Override
	public void exit(InputEvent event, float x, float y, int pointer, Actor toActor){
		actor.setColor(255, 51, 255, 255);
	}
	
	@Override
	public void clicked(InputEvent event, float x, float y){
		String screen = actor.getName();
		
		switch(screen){
			case "Play":
				MainMenu.stopMusic();
				((Game) Gdx.app.getApplicationListener()).setScreen(new Loading()); // Sets the screen to the Loading screen
				break;
			case "Highscores":
				((Game) Gdx.app.getApplicationListener()).setScreen(new Highscores()); // Sets the screen to the Highscores screen
				break;
			case "Exit":
				Gdx.app.exit(); // Closes the app
				break;
			case "BackToMenu":
				((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu(false));
				break;
			case "Save":
				EndGame.save();
				((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu(true));
			default:
				assert false;
		}
	}
}
