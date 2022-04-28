package game.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.scene.Scene;

public abstract class Entity {
	protected Long id;
	protected boolean dead;
	
	public void copyFrom(Entity e) {
		this.id = e.id;
		this.dead = e.dead;
	}
	
	//Runs 60 times a second or however the refresh rate is defined in the config
	public abstract void tick();
	//public void clientTick() {}
	public void renderPre(SpriteBatch batch) {}
	public abstract void render(SpriteBatch batch);
	public void renderPost(SpriteBatch batch) {}
	
	//Called when an entity is added to the scene
	public void create(Scene scene) {}
	
	public boolean equals(Entity entity) {
		if(entity == null)
			return false;
		return entity.id == this.id;
	}
	
	public Entity constructForNet() {
		return this;
	}
	
	public void dispose() {}
	
	public boolean isDead() {
		return dead;
	}
	
	public void kill() {
		this.dead = true;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setDead(boolean dead) {
		this.dead = dead;
	}
	
	
	
}
