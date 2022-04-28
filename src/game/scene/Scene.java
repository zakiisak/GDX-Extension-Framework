package game.scene;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.Game;
import game.entity.Entity;

public abstract class Scene {
	
	protected List<Entity> entities = new ArrayList<Entity>();
	
	//Called when the scene is about to be entered
	public abstract void create();
	//Called when the scene is about to exit
	public abstract void destroy();
	
	//Called each time the window is resized
	public void resize(int width, int height) {
		
	}
	
	public void tick() {
		for(int i = 0; i < entities.size(); i++)
		{
			Entity e = entities.get(i);
			e.tick();
			if(e.isDead())
			{
				e.dispose();
				entities.remove(i--);
			}
		}
	}
		
	public void render(SpriteBatch batch) {
		for(int i = 0; i < entities.size(); i++)
		{
			Entity e = entities.get(i);
			e.render(batch);
		}
		
		for(int i = 0; i < entities.size(); i++)
		{
			Entity e = entities.get(i);
			e.renderPost(batch);
		}
	}
	
	public void addEntity(Entity e) {
		this.entities.add(e);
	}
	
	
	public void changeScene(Class<? extends Scene> scene) {
		Game.instance.setScene(scene);
	}
}
