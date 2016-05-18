package johndoe.game.screens;

import johndoe.game.font.BmapFont;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class Loading implements Screen{

	private AssetManager manager = new AssetManager();
	private SpriteBatch batch;
	private Sprite loadingBar;
	private Texture loadingBar_texture;
	private Label progress_label;
	private boolean isDone;
	
	@Override
	public void show() {
		isDone = false;
		batch = new SpriteBatch(); // A batch is needed to draw stuff the the screen
		
		loadingBar_texture = new Texture("img/loadingBar.png"); // Creates the texture to use in the sprite
		loadingBar = new Sprite(loadingBar_texture); // Creates the sprite from the texture
		loadingBar.setSize(0, 30); // Sets the sprite size (width, height)
		loadingBar.setPosition((Gdx.graphics.getWidth()/2) - 300, (Gdx.graphics.getHeight()/2) + (loadingBar.getHeight()/2)); // Sets the sprite position so that it fits in the middle of the screen
		
		progress_label = new Label("Loading...", new LabelStyle(BmapFont.arial_white_32x32, Color.WHITE));
		progress_label.setPosition((Gdx.graphics.getWidth()/2), (Gdx.graphics.getHeight()/2) + (loadingBar.getHeight()*2));
		
		manager.load("font/MomsTypewriter_white_32x32.fnt", BitmapFont.class); // Load the custom font white 32x32
		manager.load("font/MomsTypewriter_black_32x32.fnt", BitmapFont.class); // Load the custom font black 32x32
		
		manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver())); // Sets the right loader for the class
		manager.load("map/map.tmx", TiledMap.class); // Loads the map
		
		manager.load("tilesets/walk_down.pack", TextureAtlas.class);
		manager.load("tilesets/walk_up.pack", TextureAtlas.class);
		manager.load("tilesets/walk_left.pack", TextureAtlas.class);
		manager.load("tilesets/walk_right.pack", TextureAtlas.class);
		manager.load("tilesets/idle_down.pack", TextureAtlas.class);
		manager.load("tilesets/idle_up.pack", TextureAtlas.class);
		manager.load("tilesets/idle_left.pack", TextureAtlas.class);
		manager.load("tilesets/idle_right.pack", TextureAtlas.class);
		manager.load("tilesets/fly_down.pack", TextureAtlas.class);
		manager.load("tilesets/fly_left.pack", TextureAtlas.class);
		manager.load("tilesets/fly_right.pack", TextureAtlas.class);
		
		manager.load("tilesets/monster_walk_left.pack", TextureAtlas.class);
		manager.load("tilesets/monster_walk_right.pack", TextureAtlas.class);
		
		manager.load("img/rope_horizontal.png", Texture.class);
		manager.load("img/rope_vertical.png", Texture.class);
	}

	@Override
	public void render(float delta) {
		//Screen clearing
		Gdx.gl.glClearColor(0, 0, 0, 1); // Clear the application's background color
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear some other stuff?
		
		float progress = manager.getProgress(); // Float value that is equal to the asset loading progress of the manager
		Gdx.app.log("LOADING", "Assets loading progres: " + (int)(Math.floor(progress*100)) + "%"); // Prints out the progress value
		
		loadingBar.setSize((progress * 100) * 6, loadingBar.getHeight()); // Updates the size of the loading bar according to the asset manager progress
		progress_label.setText((int)(Math.floor(progress*100)) + "%");
		
		//Batch drawing
		batch.begin(); // Begin drawing
		loadingBar.draw(batch); // Draw the loading bar to the screen
		progress_label.draw(batch, 1);
		batch.end(); // End drawing
		
		if(manager.update() && isDone){
			Gdx.app.log("LOADING", "Done loading assets!"); // Debug notification when the asset manager is done loading the assets
			
			//Simple sleep -- not really necessary.. can be deleted.. is just to see loading is at 100%
			try {
			    Thread.sleep(1000);
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
			
			((Game) Gdx.app.getApplicationListener()).setScreen(new Play(manager)); // Sets the screen to the Loading screen
		}
		
		if(progress >= 1.0f){
			isDone = true;
		}
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		manager.dispose(); //Disposes all assets inside the manager and also the manager itself
	}

}
