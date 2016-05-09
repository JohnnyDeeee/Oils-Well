package johndoe.game.font;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class BmapFont{

	public static BitmapFont momsTypewriter_black_32x32,
							 momsTypewriter_white_32x32,
							 momsTypewriter_black_64x64,
							 momsTypewriter_white_64x64,
							 arial_black_32x32,
							 arial_white_32x32,
							 bubblegum_white_64x64,
							 bubblegum_white_128x128;
	
	public static void init(){
		momsTypewriter_black_32x32 = new BitmapFont(Gdx.files.internal("font/MomsTypewriter_black_32x32.fnt"), false); // Create the font
		momsTypewriter_white_32x32 = new BitmapFont(Gdx.files.internal("font/MomsTypewriter_white_32x32.fnt"), false); // Create the font
		momsTypewriter_black_64x64 = new BitmapFont(Gdx.files.internal("font/MomsTypewriter_black_64x64.fnt"), false); // Create the font
		momsTypewriter_white_64x64 = new BitmapFont(Gdx.files.internal("font/MomsTypewriter_white_64x64.fnt"), false); // Create the font
		arial_black_32x32 = new BitmapFont(Gdx.files.internal("font/Arial_white_32x32.fnt"), false); // Create the font
		arial_white_32x32 = new BitmapFont(Gdx.files.internal("font/Arial_white_32x32.fnt"), false); // Create the font
		bubblegum_white_64x64 = new BitmapFont(Gdx.files.internal("font/Bubblegum_white_64x64.fnt"));
		bubblegum_white_128x128 = new BitmapFont(Gdx.files.internal("font/Bubblegum_white_128x128.fnt"));
	}
	
	public static void dispose(){
		momsTypewriter_black_32x32.dispose();
		momsTypewriter_white_32x32.dispose();
		momsTypewriter_black_64x64.dispose();
		momsTypewriter_white_64x64.dispose();
		arial_black_32x32.dispose();
		arial_white_32x32.dispose();
		bubblegum_white_64x64.dispose();
		bubblegum_white_128x128.dispose();
	}
	
}
