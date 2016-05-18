package johndoe.game.renderers;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class TextureMapObjectAndTileRenderer extends OrthogonalTiledMapRenderer {

    public TextureMapObjectAndTileRenderer(TiledMap map) {
        super(map);
    }

    public TextureMapObjectAndTileRenderer(TiledMap map, Batch batch) {
        super(map, batch);
    }

    public TextureMapObjectAndTileRenderer(TiledMap map, float unitScale) {
        super(map, unitScale);
    }

    public TextureMapObjectAndTileRenderer(TiledMap map, float unitScale, Batch batch) {
        super(map, unitScale, batch);
    }

    @Override
    public void renderObject(MapObject object) {
        if (object instanceof TextureMapObject) {
            TextureMapObject textureObject = (TextureMapObject) object;
//            batch.draw(textureObject.getTextureRegion(), textureObject.getX(), textureObject.getY());
            batch.draw(textureObject.getTextureRegion(), textureObject.getX(), textureObject.getY(), textureObject.getOriginX(), textureObject.getOriginY(), textureObject.getTextureRegion().getRegionWidth(), textureObject.getTextureRegion().getRegionHeight(), textureObject.getScaleX(), textureObject.getScaleY(), textureObject.getRotation());
        }
    }
    
    @Override
	public void renderObjects(MapLayer layer) {
		for (MapObject object : layer.getObjects()) {
			renderObject(object);
		}
	}
}