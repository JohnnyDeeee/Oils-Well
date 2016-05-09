package johndoe.game.screens;

import johndoe.game.Config;
import johndoe.game.font.BmapFont;
import johndoe.game.listeners.LabelListener;
import johndoe.game.tween.ActorAccessor;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class MainMenu implements Screen{

	private Stage stage;
	private Skin skin;
	private Table table;
	private BmapFont bmapFont;
	private Label title_label, creator_label, version_label, play_label, highscores_label, exit_label;
	private LabelStyle labelStyle_1, labelStyle_2, labelStyle_3;
	private Tween tween;
	private TweenManager tm;
	private Timeline menuAnimation;
	private static Music song;
	private boolean init;
	private Texture background;
	
	public MainMenu(boolean init){
		this.init = init;
	}
	
	@Override
	public void show() {
		if (init) {
			setupSound();
			setupStage();
			setupTween();
		}else{
			setupStage();
			addLabelListeners();
		}
	}

	@Override
	public void render(float delta) {
		//Screen clearing
		Gdx.gl.glClearColor(0, 0, 0, 1); // Clear the application's background color
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear some other stuff?
		
		if (init) {
			//Update animations
			tm.update(delta); // Update tween manager
		}
		//Draw/update the scene
		stage.act(delta); // Updates the stage and everything inside it
		
		stage.getBatch().begin();
		stage.getBatch().draw(background, 0, 0);
		stage.getBatch().end();
		
		stage.draw(); // Draw the stage and everything inside it
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height); // Updates the viewport		
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {
//		song.stop(); // Stop playing this music file
	}

	@SuppressWarnings("static-access")
	@Override
	public void dispose() {
		stage.dispose(); // Dispose the stage
		skin.dispose(); // Dispose the skin
		bmapFont.dispose(); // Dispose the bitmapFonts (custom class)
		song.dispose();
	}

	private void setupSound(){
		//Music setup
		song = Gdx.audio.newMusic(Gdx.files.internal("music/bensound-littleidea.mp3")); // Main Menu background music
		song.setVolume(0.1f); // Set the volume
		song.setLooping(true); // Set it to loop
		song.play(); // Play the song
	}
	
	private void setupStage(){
		stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight())); // Creates a new stage (scene) where we can put items in and gives it a viewport

		background = new Texture(Gdx.files.internal("tilesets/main_menu.png"));
		
		Gdx.input.setInputProcessor(stage); // Adds input handling to the stage
		
		skin = new Skin(); // Creates a skin to use inside the table
		
		table = new Table(skin); // Table acts as a layout
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // Set the bounds of the layout to the application's width and height
		
		//Setup label styles
		labelStyle_1 = new LabelStyle(bmapFont.arial_white_32x32, Color.PURPLE); // Creates a style for the version_label
		labelStyle_2 = new LabelStyle(bmapFont.bubblegum_white_128x128, Color.PINK); // Creates a style for the title_label and creator_label
		labelStyle_3 = new LabelStyle(bmapFont.bubblegum_white_64x64, Color.PINK); // Creates a style for the play_label, options_label and the exit_label (these labels acts as buttons)
		
		//Setup title_label
		title_label = new Label(Config.TITLE, labelStyle_2); // Creates the title_label
		title_label.setFontScale(0.9f);
		
		//Setup creator_label
		creator_label = new Label("by Gerben Tesselaar", labelStyle_3); // Create the creator_label
		creator_label.setFontScale(0.5f); // Set the font scale
		
		//Setup play_label
		play_label = new Label("Play", labelStyle_3); // Create the play_label
		play_label.setName("Play"); //For use with MainMenuButtonListener
		play_label.setFontScale(0.8f);
		
		//Setup options_label
		highscores_label = new Label("Highscores", labelStyle_3); // Create the higscores_label
		highscores_label.setName("Highscores"); //For use with MainMenuButtonListener
		highscores_label.setFontScale(0.8f);
		
		//Setup exit_label
		exit_label = new Label("Exit", labelStyle_3); // Create the exit_label
		exit_label.setName("Exit"); //For use with MainMenuButtonListener
		exit_label.setFontScale(0.8f);
		
		//Setup version_label
		version_label = new Label("Version: " + Config.VERSION, labelStyle_1); // Create the version_label
		version_label.setFontScale(0.8f); // Set the font scale
		
		//Setup the stage
		table.center().top(); // Sets the table(layout) to the top-center of the stage(scene)
		table.add().spaceBottom(10); // Adds a empty cell with bottom space
		table.row(); // Adds a row(enter) in the table(layout)
		table.add(title_label); // Adds the title_label to the table (layout)
		table.row(); // Adds a row(enter) in the table(layout)
		table.add(creator_label); // Adds the creator_label to the table (layout)
		table.getCell(creator_label).spaceBottom(90); // Adds pixels to the bottom of creator_label
		table.row(); // Adds a row(enter) in the table(layout)
		table.add(play_label); // Adds the play_label to the table (layout)
		table.getCell(play_label).spaceBottom(20); // Adds pixels to the bottom of play_label
		table.row(); // Adds a row(enter) in the table(layout)
		table.add(highscores_label); // Adds the high_scores to the table (layout)
		table.getCell(highscores_label).spaceBottom(20); // Adds pixels to the bottom of options_label
		table.row(); // Adds a row(enter) in the table(layout)
		table.add(exit_label); // Adds the exit_label to the table (layout)
		table.getCell(exit_label).spaceBottom(20); // Adds pixels to the bottom of exit_label
		table.row(); // Adds a row(enter) in the table(layout)
		table.add(version_label).align(Align.bottomRight); // Adds the version_label to the table(layout) and aligns it to the bottomRight
//		table.debug(); // Shows the debug lines for all the objects in the table(layout)
		stage.addActor(table); // Adds the table(layout) to the stage(scene)
	}

	@SuppressWarnings("static-access")
	private void setupTween(){
		//Create tween animation
		tm = new TweenManager(); // Create the tween manager
		tween.registerAccessor(Actor.class, new ActorAccessor()); // Specify's the class we want to animate		
		
		// Main menu fading in
		menuAnimation = Timeline.createSequence()
			.beginSequence()
			.push(tween.set(title_label, ActorAccessor.ALPHA).target(0))
			.push(tween.set(creator_label, ActorAccessor.ALPHA).target(0))
			.push(tween.set(version_label, ActorAccessor.ALPHA).target(0))
			.push(tween.set(play_label, ActorAccessor.ALPHA).target(0))
			.push(tween.set(highscores_label, ActorAccessor.ALPHA).target(0))
			.push(tween.set(exit_label, ActorAccessor.ALPHA).target(0))
			.push(tween.to(title_label, ActorAccessor.ALPHA, 1).target(1))
				.beginParallel()
				.push(tween.to(creator_label, ActorAccessor.ALPHA, 0.8f).target(1))
				.push(tween.to(version_label, ActorAccessor.ALPHA, 0.8f).target(1))
				.end()
			.pushPause(0.4f)
				.beginParallel()
				.push(tween.to(play_label, ActorAccessor.ALPHA, 0.8f).target(1))
				.push(tween.to(highscores_label, ActorAccessor.ALPHA, 0.8f).target(1))
				.push(tween.to(exit_label, ActorAccessor.ALPHA, 0.8f).target(1))
				.end()
				.setCallback(new TweenCallback() {
					
					@Override
					public void onEvent(int type, BaseTween<?> source) {
						addLabelListeners();
					}
				})
			.end()
		.start(tm);
	}
	
	private void addLabelListeners(){
		//Add all listeners when the animation is done
		play_label.addListener(new LabelListener(play_label));
		highscores_label.addListener(new LabelListener(highscores_label));
		exit_label.addListener(new LabelListener(exit_label));
	}
	
	public static void stopMusic(){
		song.stop();
	}
}
