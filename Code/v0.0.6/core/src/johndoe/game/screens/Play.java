package johndoe.game.screens;


import java.util.ArrayList;

import johndoe.game.entities.monster.Monster;
import johndoe.game.entities.player.Player;
import johndoe.game.font.BmapFont;
import johndoe.game.renderers.TextureMapObjectAndTileRenderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class Play implements Screen {
	
	private SpriteBatch batch;
	private OrthogonalTiledMapRenderer renderer;
	public OrthographicCamera camera;
	private AssetManager manager;
	private TiledMap map;
	private Player player;
	private Monster monster1, monster2;
	private ArrayList<Monster> monsterList;
	private int mapWidth, mapHeight, tilePixelWidth, tilePixelHeight, mapPixelWidth, mapPixelHeight;
	private ShapeRenderer sr;
	private LabelStyle labelStyle_1;
	private Label score_label, life_label;
	
	public Play(AssetManager manager){
		this.manager = manager;
	}
	
	@Override
	public void show() {
		map = manager.get("map/map.tmx");
		
		renderer = new TextureMapObjectAndTileRenderer(map);
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // Sets the y-axis to start in the bottom-left and views only the specified height and width
		camera.position.x = Gdx.graphics.getWidth()/2 - (32*3);
		camera.position.y = Gdx.graphics.getHeight()/2;
		camera.update();
		
		batch = new SpriteBatch();
		
		player = new Player(manager, batch);
		monster1 = new Monster(manager, batch, 1);
		monster2 = new Monster(manager, batch, 2);
		monsterList = new ArrayList<Monster>();
		monsterList.add(monster1);
		monsterList.add(monster2);
		
		mapWidth = map.getProperties().get("width", Integer.class);
		mapHeight = map.getProperties().get("height", Integer.class);
		tilePixelWidth = map.getProperties().get("tilewidth", Integer.class);
		tilePixelHeight = map.getProperties().get("tileheight", Integer.class);

		mapPixelWidth = mapWidth * tilePixelWidth;
		mapPixelHeight = mapHeight * tilePixelHeight;
		
		sr = new ShapeRenderer();
		
		labelStyle_1 = new LabelStyle(BmapFont.bubblegum_white_64x64, Color.PINK);
		score_label = new Label("Score: 0 Meter: 0", labelStyle_1);
		score_label.setPosition(10, 250);
		score_label.setFontScale(0.4f);
		life_label = new Label("Life: 0", labelStyle_1);
		life_label.setPosition(450, 250);
		life_label.setFontScale(0.4f);
	}

	@Override
	public void render(float delta) {
		//Screen clearing
		Gdx.gl.glClearColor(0, 0, 0, 1); // Clear the application's background color
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA); // Set blending mode for transparent images?
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear some other stuff?
		//End Screen clearing
		
		//Render the renderer
		renderer.setView(camera);
		renderer.render(); // Render all layers
//		renderer.render(ground_layers); // render layer 0
//		renderer.render(environment_layers); // render layer 1
		//End Render the renderer
		
		//Setup the batch
		batch.setProjectionMatrix(camera.combined);
		//End Setup the batch
		
		//Render the player
		player.draw();
		//End Render the player
		
		//Render the monster and check if player intersects with them
		for (Monster monster : monsterList) {
			monster.draw();
			
			if (Intersector.overlaps(monster.getCollisionBox(), player.getCollisionBox())) {
				if (player.hasPower()) {
					monster.delete();
					player.setScore(player.getScore() + 5);
				}else{
					player.respawn();
					if (player.getLife() > 0) {
						player.setLife(player.getLife() - 1);
					}else{
						player.setLife(-1);
					}
				}
			}else{
				for (Sprite rope : player.getRopes()) {
					if (Intersector.overlaps(monster.getCollisionBox(),rope.getBoundingRectangle())) {
						for (Sprite rope2 : player.getRopes()) {
							rope2.setBounds(0, 0, 0, 0);
						}
						Gdx.app.log("PLAY", "Monster hit rope!");
						player.respawn();
						if (player.getScore() > 1) {
							player.setScore(player.getScore() - 2);
						}
						if (player.getLife() > 0) {
							player.setLife(player.getLife() - 1);
						}else{
							player.setLife(-1);
						}
						break;
					}
				}
			}
		}
		//End Render monsters
		
		batch.begin();
		score_label.setText("Score: " + Integer.toString(player.getScore()) + " Meter: " + Integer.toString((int)player.getMeters()));
		score_label.draw(batch, 1.0f);
		life_label.setText("Life: " + Integer.toString(player.getLife()));
		life_label.draw(batch, 1.0f);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width;
		camera.viewportWidth = height;
		camera.update();
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
		manager.dispose();
		renderer.dispose();
		player.dispose();
		monster1.dispose();
		monster2.dispose();
	}

}
