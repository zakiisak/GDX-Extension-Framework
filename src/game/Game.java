package game;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import game.graphics.FontBatch;
import game.graphics.SheetPacker;
import game.graphics.Sprite;
import game.net.Network;
import game.scene.Scene;
import game.scene.SceneSample;
import game.sound.AudioManager;

public class Game implements ApplicationListener {
	public static final String GAME_VERSION = "v0.1";
	public static final String TITLE = "GDX Extension Framework";
	
	
	public static boolean shuttingDown = false;

	public static Game instance;

	public static AudioManager audio()
	{
		return instance.audioManager;
	}
	
	public static Input input() {
		return instance.input;
	}

	public LwjglApplicationConfiguration config;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	public ShapeRenderer shapeRenderer;
	private AudioManager audioManager;
	private Input input;
	
	private List<GameRunnable> runOnGameThreadList = new ArrayList<GameRunnable>();
	
	//The scene that is currently being rendered
	private Scene activeScene;
	
	public Game() {}

	@Override
	public void create() {
		instance = this;
		Sprite.load();
		input = new Input();
		Gdx.input.setInputProcessor(input);
		camera = new OrthographicCamera();
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		audioManager = new AudioManager();
		audioManager.load();
		Fonts.load();
		Cursors.init();
		SheetPacker.pack();
		Sprite.setSpriteReferences();
		Display.setLocation(-1, -1);
		
		//Change the startup scene here:
		setScene(SceneSample.class);
	}
	
	@Override
	public void dispose() {
		if(this.activeScene != null)
			this.activeScene.destroy();
		shuttingDown = true;
		Network.dispose();
		SheetPacker.clearAtlasses();
		batch.dispose();
		audioManager.dispose();
		Fonts.dispose();
		Cursors.dispose();
	}
	
	public void runOnGameThread(GameRunnable runnable) {
		synchronized (this.runOnGameThreadList) {
			this.runOnGameThreadList.add(runnable);	
		}
	}

	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.setProjectionMatrix(camera.combined);
		shapeRenderer.setProjectionMatrix(camera.combined);
		if(activeScene != null)
			activeScene.resize(width, height);
	}
	
	@Override
	public void render() {
		
		synchronized (this.runOnGameThreadList) {
			for(int i = 0; i < this.runOnGameThreadList.size(); i++)
				this.runOnGameThreadList.get(i).run(this);
			this.runOnGameThreadList.clear();
		}
		
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		batch.begin();
		
		if(activeScene != null) {
			activeScene.tick();
			activeScene.render(batch);
		}
		
		FontBatch.flushAll(batch);
		batch.end();
		input.refresh();
		Cursors.resetToNormal();
		
		if(Network.isClientConnected())
			Network.flushClientPackets();
	}
	
	private Scene initializeScene(Class<? extends Scene> sceneClass) {
		try {
			Scene scene = (Scene) sceneClass.getDeclaredConstructors()[0].newInstance();
			scene.create();
			return scene;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void setScene(Class<? extends Scene> sceneClass) {
		Scene scene = initializeScene(sceneClass);
		if(scene != null) {
			if(this.activeScene != null)
				this.activeScene.destroy();
			this.activeScene = scene;			
		}
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	
	public AudioManager getAudio()
	{
		return audioManager;
	}
	
	public SpriteBatch getSpriteBatch()
	{
		return batch;
	}

	public Input getInput()
	{
		return input;
	}

}
