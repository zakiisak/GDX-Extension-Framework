package game.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import game.graphics.Sprite;
import game.utils.FontUtils;
import game.utils.MathUtils;
import game.utils.Vector2;

public class TiledRenderer {
	
	public static void drawTang(SpriteBatch renderer, Sprite tang, float x, float y, float progress, float rotation, float scale)
	{
		tang.renderPart(renderer, x + (tang.getWidth() * scale) / 2, y + (tang.getHeight() * scale) / 2, tang.getWidth() * scale, tang.getHeight() * scale, 0, (int) (progress * 16f), 8, 16, rotation);
	}
	
	public static void drawVine(SpriteBatch batch, Sprite sprite, float x, float y, float renderWidth, float spriteScale, float rotation)
	{
		float fragmentSize = sprite.getWidth() * spriteScale;
		Vector2<Float> circle = MathUtils.getPointOnCircle((int) rotation);
		int renderCount = (int) (renderWidth / ((float) sprite.getWidth() * spriteScale));
		
		float tx = x;
		float ty = y;
		float extra = renderWidth / (sprite.getWidth() * spriteScale) - (float) renderCount;
		for(int i = 0; i < renderCount; i++)
		{
			sprite.render(batch, tx, ty, fragmentSize, fragmentSize, rotation);
			tx += circle.getX() * fragmentSize;
			ty += circle.getY() * fragmentSize;
		}
		
		final int partWidth = (int) ((float) sprite.getWidth() * extra); 
		sprite.renderPartWithCustomOrigin(batch, tx, ty, (float) partWidth * spriteScale, fragmentSize, 0, 0, partWidth, Math.round(sprite.getHeight()), false, rotation, fragmentSize / 2, fragmentSize / 2);
	}
	
	public static void drawVineFromPoints(SpriteBatch batch, Sprite sprite, float x, float y, float x2, float y2, float spriteScale)
	{
		float rotation = MathUtils.angleBetween(x, y, x2, y2);
		float length = MathUtils.getDistance(x, y, x2, y2);
		drawVine(batch, sprite, x, y, length, spriteScale, rotation);
	}

	public static void drawTiledBox(SpriteBatch batch, Sprite sprite, float x, float y, float width, float height, int scale, Color middleColor)
	{
		int size = 16 * scale;
		int border = 8 * scale;
		int corner = 4 * scale;
		
		sprite.renderPart(batch, x - corner, y - corner, corner, corner, 0, 0, corner, corner);
		sprite.renderPart(batch, x + width, y - corner, corner, corner, size - corner, 0, corner, corner);
		sprite.renderPart(batch, x + width, y + height, corner, corner, size - corner, size - corner, corner, corner);
		sprite.renderPart(batch, x - corner, y + height, corner, corner, 0, size - corner, corner, corner);
		
		//Top
		sprite.renderPart(batch, x, y - corner, width, corner, corner, 0, border, corner);
		
		//Right
		sprite.renderPart(batch, x + width, y, corner, height, size - corner, corner, corner, border);
		
		//Bottom
		sprite.renderPart(batch, x, y + height, width, corner, corner, size - corner, border, corner);
		
		//Left
		sprite.renderPart(batch, x - corner, y, corner, height, 0, corner, corner, border);

		batch.setColor(middleColor);
		sprite.renderPart(batch, x, y, width, height, corner, corner, border, border);
	}

	public static void drawTiledBox(SpriteBatch batch, Sprite sprite, float x, float y, float width, float height, int scale)
	{
		drawTiledBox(batch, sprite, x, y, width, height, scale, new Color(1, 1, 1, 1));
	}


	
	public static void drawTiledBorder(SpriteBatch batch, Sprite sprite, float x, float y, float width, float height, int scale)
	{
		int size = 16 * scale;
		int border = 8 * scale;
		int corner = 4 * scale;
		
		sprite.renderPart(batch, x - corner, y - corner, corner, corner, 0, 0, corner, corner);
		sprite.renderPart(batch, x + width, y - corner, corner, corner, size - corner, 0, corner, corner);
		sprite.renderPart(batch, x + width, y + height, corner, corner, size - corner, size - corner, corner, corner);
		sprite.renderPart(batch, x - corner, y + height, corner, corner, 0, size - corner, corner, corner);
		
		//Top
		sprite.renderPart(batch, x, y - corner, width, corner, corner, 0, border, corner);
		
		//Right
		sprite.renderPart(batch, x + width, y, corner, height, size - corner, corner, corner, border);
		
		//Bottom
		sprite.renderPart(batch, x, y + height, width, corner, corner, size - corner, border, corner);
		
		//Left
		sprite.renderPart(batch, x - corner, y, corner, height, 0, corner, corner, border);
	}
	
	public static void drawTiledBorderWithLabel(SpriteBatch batch, Sprite sprite, float x, float y, float width, float height, int scale, Label label)
	{
		int size = 16 * scale;
		int border = 8 * scale;
		int corner = 4 * scale;
		
		sprite.renderPart(batch, x - corner, y - corner, corner, corner, 0, 0, corner, corner);
		sprite.renderPart(batch, x + width, y - corner, corner, corner, size - corner, 0, corner, corner);
		sprite.renderPart(batch, x + width, y + height, corner, corner, size - corner, size - corner, corner, corner);
		sprite.renderPart(batch, x - corner, y + height, corner, corner, 0, size - corner, corner, corner);
		
		//Top
		sprite.renderPart(batch, x, y - corner, 4, corner, corner, 0, border, corner);
		label.render(batch, x + 5 + label.width, y - label.getHeight() * 0.75f, FontUtils.ALLIGN_RIGHT);
		sprite.renderPart(batch, x + 4 + label.width + 4, y - corner, width - 8 - label.width, corner, corner, 0, border, corner);
		
		//Right
		sprite.renderPart(batch, x + width, y, corner, height, size - corner, corner, corner, border);
		
		//Bottom
		sprite.renderPart(batch, x, y + height, width, corner, corner, size - corner, border, corner);
		
		//Left
		sprite.renderPart(batch, x - corner, y, corner, height, 0, corner, corner, border);
	}
	
	public static void drawStretched(SpriteBatch batch, Sprite sprite, float x, float y, float width, float height)
	{
		sprite.render(batch, x, y, width, height);
	}
	
}
