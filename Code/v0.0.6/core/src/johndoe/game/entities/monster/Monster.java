package johndoe.game.entities.monster;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Monster extends Sprite{

	//Fields
	private AssetManager manager;
	private SpriteBatch batch;
	private TextureAtlas walk_left_atlas, walk_right_atlas;
	private Animation current_animation, walk_left, walk_right;
	private float timePassed = 0;
	private Vector2 pos;
	private float speed;
	private int facing;
	private Rectangle collisionBox;
	private TiledMap map;
	private int id;
	private boolean delete;
	
	//Properties
	public Rectangle getCollisionBox(){
		return this.collisionBox;
	}
	
	public void setCollisionBox(int x, int y, int width, int height){
		this.collisionBox = new Rectangle(x,y,width,height);
	}
	
	//Constructor
	public Monster(AssetManager manager, SpriteBatch batch, int id){
		this.manager = manager;
		this.batch = batch;
		this.map = manager.get("map/map.tmx");
		this.id = id;
		
		walk_left_atlas = manager.get("tilesets/monster_walk_left.pack");
		walk_left = new Animation(1/10f, walk_left_atlas.getRegions());
		
		walk_right_atlas = manager.get("tilesets/monster_walk_right.pack");
		walk_right = new Animation(1/10f, walk_right_atlas.getRegions());
		
		current_animation = walk_right;
		facing = 1;
		pos = new Vector2(100,100);
		speed = 3.0f; // Default 2.0f
		collisionBox = new Rectangle(pos.x, pos.y, 24, 30);
		delete = false;
		
		//Spawn pos
		try {
			for(MapObject object : map.getLayers().get("Collision").getObjects()){
				if(object.getProperties().containsKey("monsterSpawn1") && id == 1){
					RectangleMapObject rect = (RectangleMapObject) object;
					pos = new Vector2(rect.getRectangle().x, rect.getRectangle().y);
					Gdx.app.log("MONSTER", "Spawn1 point found!");
					facing = 1;
					current_animation = walk_right;
					break;
				}else if(object.getProperties().containsKey("monsterSpawn2") && id == 2){
					RectangleMapObject rect = (RectangleMapObject) object;
					pos = new Vector2(rect.getRectangle().x, rect.getRectangle().y);
					Gdx.app.log("MONSTER", "Spawn2 point found!");
					facing = 3;
					current_animation = walk_left;
					break;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Gdx.app.log("MONSTER", "No spawn point found!");
			e.printStackTrace();
		}
	}
	
	//Methods
	public void draw(){
		if (!delete) {
			update(Gdx.graphics.getDeltaTime());
			batch.begin();
			batch.draw(current_animation.getKeyFrame(timePassed, true), pos.x,pos.y);
			batch.end();
		}
	}
	
	public void update(float delta){
		collisionBox.set(pos.x, pos.y, collisionBox.getWidth(), collisionBox.getHeight());
		timePassed += delta * speed/2;
		
		switch (facing) {
		case 1:
			pos.x += 30 * speed * delta;
			break;
		case 3:
			pos.x -= 30 * speed * delta;
			break;
		default:
			break;
		}
		
		checkCollision();
	}
	
	public void dispose(){
		walk_left_atlas.dispose();
		walk_right_atlas.dispose();
	}
	
	public void delete(){
		delete = true;
		collisionBox = new Rectangle(0,0,0,0);
		Gdx.app.log("MONSTER", "Monster got killed!");
	}
	
	private void checkCollision(){
		//collision with collision layer
		MapObjects objects_collisionlayer = map.getLayers().get("Collision").getObjects();
		
		for (RectangleMapObject rectangleObject : objects_collisionlayer.getByType(RectangleMapObject.class)){
			
			if(!rectangleObject.getProperties().containsKey("monsterSpawn1")){ // Make sure we dont collide with the monster spawn rectangle
				Rectangle rectangle = rectangleObject.getRectangle();
				if (Intersector.overlaps(rectangle, collisionBox)){
					if(rectangleObject.getProperties().containsKey("monsterTurnLeft")){
						current_animation = walk_left;
						facing = 3;
					}else if(rectangleObject.getProperties().containsKey("monsterTurnRight")){
						current_animation = walk_right;
						facing = 1;
					}
				}
			}
		}
	}
}
