package game.main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;

import game.Game;

public class Main {
	
	public static void main(String[] args)
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.initialBackgroundColor = new Color(0.9f, 0.9f, 0.9f, 1);
		config.samples = 0;
		config.width = 640;
		config.height = 480;
		
		config.vSyncEnabled = true;
		config.title = Game.TITLE + Game.GAME_VERSION;
		//While loading everything, we put the window out of bounds of the visible spectrum
		config.x = -30000; 
		config.resizable = true;
		config.foregroundFPS = 60;
		Game game = new Game();
		game.config = config;
		
		new LwjglApplication(game, config);
	}
	
}
