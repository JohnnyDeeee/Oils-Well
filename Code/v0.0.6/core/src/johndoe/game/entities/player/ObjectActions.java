package johndoe.game.entities.player;

import johndoe.game.utils.PowerTimer;

import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class ObjectActions {

	private static void removeTiles(String used_by, TiledMap map, RectangleMapObject rectangleObject){
		MapObjects objects = map.getLayers().get("Objects").getObjects();
		objects.remove(rectangleObject); // Removes the interaction region
		
		//Removes object tiles
		for(TextureMapObject object_tile : objects.getByType(TextureMapObject.class)){
			if (object_tile.getProperties().containsKey("used_by")) {
				if (object_tile.getProperties().get("used_by").equals(used_by)) {
					objects.remove(object_tile);
				}
			}
		}
	}
	
	public static void destroyPowerCandy(TiledMap map, RectangleMapObject rectangleObject, Player player){
		removeTiles("givePower", map, rectangleObject);
		
		player.givePower();
		player.setSpeed(3.0f);
		Timer.schedule(new PowerTimer(player), 10); // 20 second delay until run() is started
	}
	
	public static void destroyCandy1(TiledMap map, RectangleMapObject rectangleObject, Player player){
		removeTiles("givePoint_1", map, rectangleObject);
		
		player.setScore(player.getScore() + 10);
	}
	
	public static void destroyCandy2(TiledMap map, RectangleMapObject rectangleObject, Player player){
		removeTiles("givePoint_2", map, rectangleObject);
		
		player.setScore(player.getScore() + 10);
	}
	
	public static void destroyCandy3(TiledMap map, RectangleMapObject rectangleObject, Player player){
		removeTiles("givePoint_3", map, rectangleObject);
		
		player.setScore(player.getScore() + 10);
	}
	
}
