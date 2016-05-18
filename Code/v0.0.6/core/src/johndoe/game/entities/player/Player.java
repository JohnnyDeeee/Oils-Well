package johndoe.game.entities.player;


import java.util.ArrayList;
import java.util.Random;

import johndoe.game.screens.EndGame;
import johndoe.game.screens.Loading;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class Player extends Sprite {

	
	//Fields
	private AssetManager manager;
	private SpriteBatch batch;
	private TextureAtlas move_right_atlas, move_left_atlas, move_up_atlas, move_down_atlas, idle_right_atlas, idle_left_atlas, idle_up_atlas, idle_down_atlas, fly_left_atlas, fly_down_atlas, fly_right_atlas;
	private Animation current_animation, move_right, move_left, move_up, move_down, idle_right, idle_left, idle_up, idle_down, fly_left, fly_right, fly_down;
	private float timePassed = 0;
	private PlayerInput playerInput;
	private Vector2 pos;
	private int facing, score, life;
	private boolean loop, leftMove, rightMove, upMove, downMove;
	private float speed;
	private TiledMap map;
	private boolean collideTop, collideBottom, collideLeft, collideRight, hasPower;
	private Rectangle collisionBox;
	private ArrayList<float[]> oldPositions;
	private Texture tex;
	private Sprite rope_horizontal, rope_vertical;
	private ShapeRenderer sr;
	private Random random;
	private ArrayList<Sprite> ropes = new ArrayList<Sprite>();
	private float meters;
	
	//Properties
	public Animation getCurrent_animation() {
		return current_animation;
	}

	public void setCurrent_animation(Animation current_animation) {
		this.current_animation = current_animation;
	}

	public Animation getAnimation(String animation){
		try {
			switch(animation){
			case "current_animation":
				return this.current_animation;
			case "move_right":
				return this.move_right;
			case "move_left":
				return this.move_left;
			case "move_up":
				return this.move_up;
			case "move_down":
				return this.move_down;
			case "idle_right":
				return this.idle_right;
			case "idle_left":
				return this.idle_left;
			case "idle_up":
				return this.idle_up;
			case "idle_down":
				return this.idle_down;
			case "fly_left":
				return this.fly_left;
			case "fly_right":
				return this.fly_right;
			case "fly_down":
				return this.fly_down;
			default:
				return null;
			}
		} catch (Exception e) {
			Gdx.app.log("PLAYER", "No animation found by the name: " + animation);
			return null;
		}
	}
	
	public Vector2 getPos() {
		return pos;
	}

	public void setPos(Vector2 position) {
		this.pos = position;
	}

	public int getFacing(){
		return this.facing;
	}
	
	public void setFacing(int facing){
		this.facing = facing;
	}

	public float getHeight(){
		return current_animation.getKeyFrame(timePassed).getRegionHeight();
	}
	
	public float getWidth(){
		return current_animation.getKeyFrame(timePassed).getRegionWidth();
	}
	
	public boolean isMovingLeft(){
		return this.leftMove;
	}
	
	public boolean isMovingRight(){
		return this.rightMove;
	}
	
	public boolean isMovingUp(){
		return this.upMove;
	}
	
	public boolean isMovingDown(){
		return this.downMove;
	}
	
	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public void setLoop(boolean bool){
		this.loop = bool;
	}
	
	public Rectangle getCollisionBox(){
		return this.collisionBox;
	}
	
	public ArrayList<Sprite> getRopes(){
//		ArrayList<Sprite> ropeList = new ArrayList<Sprite>();
//		ropeList.add(rope_horizontal);
//		ropeList.add(rope_vertical);
		if(ropes.size() < 0){
			
		}
		
		return ropes;
	}
	
	public boolean hasPower(){
		return this.hasPower;
	}
	
	public void takePower(){
		this.hasPower = false;
	}
	
	public void givePower(){
		this.hasPower = true;
	}
	
	public int getScore(){
		return this.score;
	}
	
	public void setScore(int score){
		this.score = score;
	}
	
	public int getLife(){
		return this.life;
	}
	
	public void setLife(int life){
		this.life = life;
	}
	
	public float getMeters(){
		return this.meters;
	}
	
	//Constructor
	public Player(AssetManager manager, SpriteBatch batch){
		this.manager = manager;
		this.batch = batch;
		this.map = manager.get("map/map.tmx");
		
		//Move right
		move_right_atlas = manager.get("tilesets/walk_right.pack");
		move_right = new Animation(1/10f, move_right_atlas.getRegions());
		
		//Move left
		move_left_atlas = manager.get("tilesets/walk_left.pack");
		move_left = new Animation(1/10f, move_left_atlas.getRegions());
		
		//Move down
		move_down_atlas = manager.get("tilesets/walk_down.pack");
		move_down = new Animation(1/10f, move_down_atlas.getRegions());
		
		//Move up
		move_up_atlas = manager.get("tilesets/walk_up.pack");
		move_up = new Animation(1/10f, move_up_atlas.getRegions());
		
		//Idle right
		idle_right_atlas = manager.get("tilesets/idle_right.pack");
		idle_right = new Animation(1/10f, idle_right_atlas.getRegions());
		
		//Idle left
		idle_left_atlas = manager.get("tilesets/idle_left.pack");
		idle_left = new Animation(1/10f, idle_left_atlas.getRegions());
		
		//Idle down
		idle_down_atlas = manager.get("tilesets/idle_down.pack");
		idle_down = new Animation(1/10f, idle_down_atlas.getRegions());
		
		//Idle up
		idle_up_atlas = manager.get("tilesets/idle_up.pack");
		idle_up = new Animation(1/10f, idle_up_atlas.getRegions());
		
		//Fly left
		fly_left_atlas = manager.get("tilesets/fly_left.pack");
		fly_left = new Animation(1/10f, fly_left_atlas.getRegions());
		
		//Fly right
		fly_right_atlas = manager.get("tilesets/fly_right.pack");
		fly_right = new Animation(1/10f, fly_right_atlas.getRegions());
		
		//Fly down
		fly_down_atlas = manager.get("tilesets/fly_down.pack");
		fly_down = new Animation(1/10f, fly_down_atlas.getRegions());
		
		tex = manager.get("img/rope_horizontal.png");
		rope_horizontal = new Sprite(tex);
		rope_horizontal.setBounds(0, 0, 0, 0);
		tex = manager.get("img/rope_vertical.png");
		rope_vertical = new Sprite(tex);
		rope_vertical.setBounds(0, 0, 0, 0);
		
		//Current Animation
		current_animation = move_up;
		loop = true;
		facing = 1; // 0 = NORTH, 1 = EAST, 2 = SOUTH, 3 = WEST
		
		
		//Setup input (has to be after loading the animations)
		InputProcessor playerInput = new PlayerInput(this);
		Gdx.input.setInputProcessor(playerInput);
		
		//Setup variables
		pos = new Vector2(100,100);
		speed = 2.0f; // Default 2.0f
		collisionBox = new Rectangle(pos.x, pos.y, 24, 30);
		oldPositions = new ArrayList<float[]>();
		sr = new ShapeRenderer();
		hasPower = false;
		score = 0;
		life = 3;
		
		random = new Random();
		
		respawn();
		saveOldPosition();
	}

	
	//Methods
	public void draw(){
		update(Gdx.graphics.getDeltaTime());
		
		batch.begin();
		batch.draw(current_animation.getKeyFrame(timePassed, loop), pos.x, pos.y);
		batch.end();
		
		if(hasPower){
			float r = random.nextFloat();
			float g = random.nextFloat();
			float b = random.nextFloat();
			
			batch.setColor(new Color(r,g,b,1.0f));
		}else{
			batch.setColor(Color.WHITE);
		}
		
		drawOldPosition();
		
		//DEBUG - draw the box where collision kicks in on the character
//		sr.setProjectionMatrix(batch.getProjectionMatrix());
//		sr.begin(ShapeType.Line);
//		sr.setColor(Color.RED);
//		sr.rect(collisionBox.x, collisionBox.y, collisionBox.getWidth(), collisionBox.getHeight()); // Draw collision rectangle
//		sr.end();
	}
	
	public void update(float delta){
		collisionBox.set(pos.x, pos.y, collisionBox.getWidth(), collisionBox.getHeight()); // update collisionBox
		timePassed += delta * speed/2; // make this scale with the speed so you only have to edit the player.speed
		checkCollision();
		updatePosition(delta);
		
		if(life < 0){
			((Game) Gdx.app.getApplicationListener()).setScreen(new EndGame(score, (int)meters));
		}
	}
	
	public void dispose(){
		move_right_atlas.dispose();
		move_left_atlas.dispose();
		move_up_atlas.dispose();
		move_down_atlas.dispose();
	}
	
	public void respawn(){
		try {
			for(MapObject object : map.getLayers().get("Collision").getObjects()){
				if(object.getProperties().containsKey("playerSpawn")){
					RectangleMapObject rect = (RectangleMapObject) object;
					pos = new Vector2(rect.getRectangle().x, rect.getRectangle().y);
					Gdx.app.log("PLAYER", "Spawn point found!");
					oldPositions.clear();
					meters = 0;
					break;
				}
			}
		} catch (Exception e) {
			Gdx.app.log("PLAYER", "No spawn point found!");
			e.printStackTrace();
		}
	}
	
	private void saveOldPosition(){
		float[] coords = {(float) Math.floor(pos.x), (float) Math.floor(pos.y), facing};
		
		if (oldPositions.size() > 0) {
			float[] oldCoords = oldPositions.get(oldPositions.size() - 1);
			
			if (oldCoords[0] != coords[0] && oldCoords[1] != coords[1]) {
				oldPositions.add(coords);
				Gdx.app.log("PLAYER", "Next pos added! (" + coords + ")");
				Gdx.app.log("", "===================ALL POSITIONS==================");
				for(int i = 0; i < oldPositions.size(); i++){
					float[] oldPos = oldPositions.get(i);
					Gdx.app.log("", i + ": X = " + oldPos[0] + ", Y = " + oldPos[1] + ", Facing = " + oldPos[2]);
				}
				Gdx.app.log("", "==================================================");
				
				switch(facing){
				case 0:
					ropes.add(rope_vertical);
					break;
				case 1:
					ropes.add(rope_horizontal);
					break;
				case 3:
					ropes.add(rope_horizontal);
					break;
				}
			}
		}else{
			oldPositions.add(coords);
			Gdx.app.log("PLAYER", "First pos added!");
		}
	}
	
	private void drawOldPosition(){
		for(int i = 0; i < oldPositions.size(); i++){
			float[] oldPos = oldPositions.get(i);
			float x = oldPos[0];
			float y = oldPos[1];
			
			if(i >= 1){
				float[] nextOldPos = oldPositions.get(i-1);
				float x2 = nextOldPos[0];
				float y2 = nextOldPos[1];
				int face = (int)nextOldPos[2];
				
				batch.begin();
				
				//Draw line between corners
				switch (face) {
				case 0:
					rope_vertical.setBounds(x2+10, y2+5, 5, y-y2);
					rope_vertical.draw(batch);
					break;
				case 1:
					rope_horizontal.setBounds(x2+10, y2+5, x-x2+5, 5);
					rope_horizontal.draw(batch);
					break;
				case 3:
					rope_horizontal.setBounds(x2+15, y2+5, x-x2, 5);
					rope_horizontal.draw(batch);
					break;
				default:
					break;
				}
				
				batch.end();
			}
			
			if(i == oldPositions.size()-1){
				float x2 = pos.x;
				float y2 = pos.y;
				int face = (int)oldPos[2];
				
				batch.begin();
				
				//Draw line between player and last corner
				switch (face) {
				case 0:
					rope_vertical.setBounds(x2+10, y2+5, 5, y-y2);
					rope_vertical.draw(batch);
					break;
				case 1:
					rope_horizontal.setBounds(x2+10, y2+5, x-x2, 5);
					rope_horizontal.draw(batch);
					break;
				case 3:
					rope_horizontal.setBounds(x2+25, y2+5, x-x2-10, 5);
					rope_horizontal.draw(batch);
					break;
				default:
					break;
				}
				
				batch.end();
			}
		}
	}
	
	private void updatePosition(float delta){		
		if(leftMove){
			saveOldPosition();
			pos.x -= 30 * speed * delta;
			meters += 0.05f;
		}
		if(rightMove){
			saveOldPosition();
			pos.x += 30 * speed * delta;
			meters += 0.05f;
		}
		if (upMove) {
			saveOldPosition();
			pos.y += 30 * speed * delta;
			meters += 0.05f;
		}
		if (downMove) {
			if (oldPositions.size() > 0) {
				
				float[] oldCoords = oldPositions.get(oldPositions.size() - 1);
				
				if (pos.x != oldCoords[0] || pos.y != oldCoords[1]) {
					if (oldCoords[0] > pos.x) {
						if ((oldCoords[0] - pos.x) >= 30*(speed*4)*delta) {
							pos.x += 30 * (speed*4) * delta;
							meters -= 0.05f * 4;
						}else{
							pos.x = oldCoords[0];
						}
						
						setCurrent_animation(fly_right);
					} else if (oldCoords[0] < pos.x) {
						if ((pos.x - oldCoords[0]) >= 30*(speed*4)*delta) {
							pos.x -= 30 * (speed*4) * delta;
							meters -= 0.05f * 4;
						}else{
							pos.x = oldCoords[0];
						}
						
						setCurrent_animation(fly_left);
					}
					if (oldCoords[1] > pos.y) {
						if ((oldCoords[1] - pos.y) >= 30*(speed*4)*delta) {
							pos.y += 30 * (speed*4) * delta;
							meters -= 0.05f * 4;
						}else{
							pos.y = oldCoords[1];
						}
					} else if (oldCoords[1] < pos.y) {
						if ((pos.y - oldCoords[1]) >= 30*(speed*4)*delta) {
							pos.y -= 30 * (speed*4) * delta;
							meters -= 0.05f * 4;
						}else{
							pos.y = oldCoords[1];
						}
						
						setCurrent_animation(fly_down);
					}
				}else{
					oldPositions.remove(oldPositions.size() - 1);
					if (ropes.size() > 0) {
						ropes.remove(ropes.size() - 1);
					}
					Gdx.app.log("PLAYER", "Pos removed!");
				}
			}
		}
		
		Gdx.app.log("PLAYER", "meters: " + Math.ceil(meters));
	}
	
	public void setLeftMove(boolean bool){
		if (!collideLeft) {
			if (rightMove && bool) {
				rightMove = false;
			} else {
				leftMove = bool;
			}
		}else{
			leftMove = false;
		}
	}
	
	public void setRightMove(boolean bool){
		if (!collideRight) {
			if (leftMove && bool) {
				leftMove = false;
			} else {
				rightMove = bool;
			}
		} else {
			rightMove = false;
		}
	}
	
	public void setUpMove(boolean bool){
		if (!collideTop) {
			if (downMove && bool) {
				downMove = false;
			} else {
				upMove = bool;
			}
		} else {
			upMove = false;
		}
	}
	
	public void setDownMove(boolean bool){
		if (!collideBottom) {
			if (upMove && bool) {
				upMove = false;
			} else {
				downMove = bool;
			}
		}else{
			downMove = false;
		}
	}
	
	private void checkCollision(){
		//collision with objects layer
		MapObjects objects_objectslayer = map.getLayers().get("Objects").getObjects();
		
		for(RectangleMapObject rectangleObject : objects_objectslayer.getByType(RectangleMapObject.class)){
			
			Rectangle rectangle = rectangleObject.getRectangle();
			if(Intersector.overlaps(rectangle, collisionBox)){
				try {
					switch (rectangleObject.getProperties().get("action").toString()) {
					case "givePower":
						ObjectActions.destroyPowerCandy(map, rectangleObject, this);
						break;
					case "givePoint_1":
						ObjectActions.destroyCandy1(map, rectangleObject, this);
						break;
					case "givePoint_2":
						ObjectActions.destroyCandy2(map, rectangleObject, this);
						break;
					case "givePoint_3":
						ObjectActions.destroyCandy3(map, rectangleObject, this);
						break;
					default:
						break;
					}
				} catch (NullPointerException e) {
					Gdx.app.log("PLAYER", "There is no action for this object");
					e.printStackTrace();
				}
			}
		}
		
		//collision with collision layer
		MapObjects objects_collisionlayer = map.getLayers().get("Collision").getObjects();
		
		for (RectangleMapObject rectangleObject : objects_collisionlayer.getByType(RectangleMapObject.class)){
			if(!rectangleObject.getProperties().containsKey("playerSpawn") && !rectangleObject.getProperties().containsKey("endGame")){ // Make sure we dont collide with the player spawn rectangle
				Rectangle rectangle = rectangleObject.getRectangle();
				if (Intersector.overlaps(rectangle, collisionBox)){
					Gdx.app.log("PLAYER", "Player is intersecting!");
					switch (facing) {
					case 0:
						setUpMove(false);
						pos.y -= 1.0f;
						break;
					case 1:
						setRightMove(false);
						pos.x -= 1.0f;
						break;
					case 2:
						setDownMove(false);
						pos.y += 1.0f;
						break;
					case 3:
						setLeftMove(false);
						pos.x += 1.0f;
						break;
					default:
						break;
					}
				}
			}else if(rectangleObject.getProperties().containsKey("endGame")){
				Rectangle rectangle = rectangleObject.getRectangle();
				if (Intersector.overlaps(rectangle, collisionBox)){
					((Game) Gdx.app.getApplicationListener()).setScreen(new EndGame(score, (int)meters));
				}
			}
		}
	}
}
