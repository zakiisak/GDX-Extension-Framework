package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;

/**
 * Here you can change the cursors to whatever you want!
 */
public class Cursors {
	
	private static Pixmap normal, attack, click, loot, settings, shoot;
	
	private static Pixmap currentCursor;
	private static boolean wasCursorChangedThisFrame = false;
	
	public static void init() {
		normal = new Pixmap(FileLoader.get("res/cursors/Cursor_Basic.png"));
		attack = new Pixmap(FileLoader.get("res/cursors/Cursor_Attack.png"));
		click = new Pixmap(FileLoader.get("res/cursors/Cursor_Hand.png"));
		loot = new Pixmap(FileLoader.get("res/cursors/Cursor_Loot.png"));
		settings = new Pixmap(FileLoader.get("res/cursors/Cursor_Settings.png"));
		shoot = new Pixmap(FileLoader.get("res/cursors/Cursor_shoot.png"));
		changeToNormal();
	}
	
	public static void resetToNormal() {
		if(wasCursorChangedThisFrame == false)
		{
			changeToNormal();			
		}
		wasCursorChangedThisFrame = false;
	}
	
	public static void dispose() {
		normal.dispose();
		attack.dispose();
		click.dispose();
		loot.dispose();
		settings.dispose();
		shoot.dispose();
	}
	
	public static void changeToNormal() {
		if(currentCursor != normal) {
			Gdx.input.setCursorImage(normal, 19, 5);
			currentCursor = normal;
		}
		wasCursorChangedThisFrame = true;
	}
	
	public static void changeToAttack() {
		if(currentCursor != attack) {
			Gdx.input.setCursorImage(attack, 14, 2);
			currentCursor = attack;			
		}
		wasCursorChangedThisFrame = true;
	}
	
	public static void changeToClick() {
		if(currentCursor != click) {
			Gdx.input.setCursorImage(click, 0, 0);
			currentCursor = click;			
		}
		wasCursorChangedThisFrame = true;
	}
	
	public static void changeToLoot() {
		if(currentCursor != loot) {
			Gdx.input.setCursorImage(loot, 0, 0);
			currentCursor = loot;
		}
		wasCursorChangedThisFrame = true;
	}
	
	public static void changeToSettings() {
		if(currentCursor != settings) {
			Gdx.input.setCursorImage(settings, 0, 0);
			currentCursor = settings;
		}
		wasCursorChangedThisFrame = true;
	}
	
	public static void changeToShoot() {
		if(currentCursor != shoot) {
			Gdx.input.setCursorImage(shoot, 0, 0);
			currentCursor = shoot;			
		}
		wasCursorChangedThisFrame = true;
	}

}
