package johndoe.game.screens;

import johndoe.game.tween.SpriteAccessor;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Splash implements Screen {

	private SpriteBatch batch;
	private Texture splashTexture;
	private Sprite splash;
	private TweenManager tm;
	
	@Override
	public void show() {
		//Initializations
		batch = new SpriteBatch(); // A batch is needed to draw stuff the the screen
		tm = new TweenManager(); // This manager is used to animate sprites
		Tween.registerAccessor(Sprite.class, new SpriteAccessor()); // Specify's the class we want to animate
		
		//Create the splash image
		splashTexture = new Texture("img/splash.png"); // Sets the splash texture
		splash = new Sprite(splashTexture); // Turns the splash texture into a sprite so we have more control over it (ex. setSize() )
		splash.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //Change the size of the sprite to the application width and height
		
		//Tween animation
		TweenCallback tc = new TweenCallback() { // Create the callback to open the main menu after tween animation
			
			@Override
			public void onEvent(int arg0, BaseTween<?> arg1) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu(true)); // Sets the screen to the main menu
			}
		};
		
		Tween.set(splash, SpriteAccessor.ALPHA).target(0).start(tm); // Start the animation by setting the sprite ALPHA to begin at 0
		Tween.to(splash, SpriteAccessor.ALPHA, 2).target(1).start(tm); // Increment the ALPHA value over 2 seconds untill it reaches 1
		Tween.to(splash, SpriteAccessor.ALPHA, 1.5f).target(0).delay(2).start(tm).setCallback(tc); // Decrement the ALPHA value over 2 seconds untill it reaches 0, then call the tc callback
	}

	@Override
	public void render(float delta) {
		//Screen clearing
		Gdx.gl.glClearColor(0, 0, 0, 1); // Clear the application's background color
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear some other stuff?
		
		//Update animations
		tm.update(delta); // Update the tweenManager
				
		//Batch drawing
		batch.begin(); // Begin drawing
		splash.draw(batch); // Draw the splash sprite to the screen
		batch.end(); // End drawing
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
		//Clear all resources
		batch.dispose(); // Dispose the sprite batch
		splash.getTexture().dispose(); // Dispose the texture of the splash sprite (sprite itself can't be disposed)
	}

}
