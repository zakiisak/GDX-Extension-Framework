package game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import game.graphics.FontLoader;
import game.gui.GuiDefaults;

public class Fonts {
	
	public static BitmapFont standardFont_24;
	public static BitmapFont standardFont_24_withOutline;
	
	public static void load() {
		standardFont_24 = FontLoader.loadFont(FileLoader.get("res/fonts/GentiumBookBasic.ttf"), 24);
		standardFont_24_withOutline = FontLoader.loadFontWithOutline(FileLoader.get("res/fonts/GentiumBookBasic.ttf"), 24, Color.BLACK, 1, true);
		//Add more fonts here
		
		
		GuiDefaults.DefaultFont = standardFont_24;
	}
	
	public static void dispose() {
		standardFont_24.dispose();
		standardFont_24_withOutline.dispose();
		//Dispose more fonts here
	}
	
}
