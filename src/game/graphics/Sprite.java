package game.graphics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Sprite {

	private static final boolean FlipY = true;
	private static Map<Integer, int[]> sheetSizes = new HashMap<Integer, int[]>();
	private static Map<Integer, Texture> textures = new HashMap<Integer, Texture>();
	
	public static Sprite white;
	public static Sprite spr_error;

	public static Sprite button_default;
	public static Sprite button_hovered;
	public static Sprite button_pressed;
	public static Sprite textbox;
	public static Sprite checkbox_default;
	public static Sprite checkbox_default_marked;
	public static Sprite checkbox_hovered;
	public static Sprite checkbox_hovered_marked;
	public static Sprite radiobutton_default;
	public static Sprite radiobutton_default_marked;
	public static Sprite radiobutton_hovered;
	public static Sprite radiobutton_hovered_marked;
	public static Sprite document;
	public static Sprite scrollbar_back;
	public static Sprite scrollbar_default;
	public static Sprite scrollbar_hovered;
	
	public static Sprite scrollbar_button_back;
	public static Sprite scrollbar_button_default;
	public static Sprite scrollbar_button_hovered;
	

	public static void load()
	{	
		FileHandle spriteDir = Gdx.files.local("res/sprites/");
		loadRecursively(spriteDir, spriteDir);
	}

	private static void loadRecursively(FileHandle dir, FileHandle rootDirectory)
	{
		for(FileHandle handle : dir.list())
		{
			if(handle.isDirectory())
			{
				loadRecursively(handle, rootDirectory);
			}
			else
			{
				SheetPacker.addImageWithDottedName(handle, rootDirectory).addWhiteOverlay = true;
			}
		}
	}

	public static void setSpriteReferences()
	{
		white = get("ui.white");
		button_default = get("ui.button_default");
		button_hovered = get("ui.button_hovered");
		button_pressed = get("ui.button_pressed");
		textbox = get("ui.textbox");
		checkbox_default = get("ui.checkbox_default");
		checkbox_default_marked = get("ui.checkbox_default_marked");
		checkbox_hovered = get("ui.checkbox_hovered");
		checkbox_hovered_marked = get("ui.checkbox_hovered_marked");
		radiobutton_default = get("ui.radiobutton_default");
		radiobutton_default_marked = get("ui.radiobutton_default_marked");
		radiobutton_hovered = get("ui.radiobutton_hovered");
		radiobutton_hovered_marked = get("ui.radiobutton_hovered_marked");
		document = get("ui.document");
		scrollbar_back = get("ui.scrollbar_back");
		scrollbar_default = get("ui.scrollbar_default");
		scrollbar_hovered = get("ui.scrollbar_hovered");
		scrollbar_button_back = get("ui.scrollbar_button_back");
		scrollbar_button_default = get("ui.scrollbar_button_default");
		scrollbar_button_hovered = get("ui.scrollbar_button_hovered");
		spr_error = get("spr_error");
		
	}
	
	public static void registerTextureSize(int texture, int width, int height)
	{
		sheetSizes.put(texture, new int[] {width, height});
	}
	
	public static void registerTexture(int textureNumber, Texture texture)
	{
		textures.put(textureNumber, texture);
	}
	
	public boolean equals(Sprite sprite)
	{
		if(sprite == null) return false;
		final Texture texture = getTexture();
		return u == sprite.u && v == sprite.v && u2 == sprite.u2 && v2 == sprite.v2 
				&& texture.getTextureObjectHandle() == sprite.getTexture().getTextureObjectHandle();
	}
	
	public int textureNumber;
	public float u, v, u2, v2;
	public String nameId;
	
	private int[] pixels;
	private int pixelWidth;
	private int pixelHeight;
	
	public void setPixelData(int[] pixels, int pixelWidth, int pixelHeight)
	{
		this.pixelWidth = pixelWidth;
		this.pixelHeight = pixelHeight;
		this.pixels = pixels;
	}
	
	public Sprite() {}
	
	public Sprite(int textureNumber, float u, float v, float u2, float v2)
	{
		this.textureNumber = textureNumber;
		this.u = u;
		this.v = v;
		this.u2 = u2;
		this.v2 = v2;
	}
	
	public Sprite(Sprite sprite)
	{
		this.textureNumber = sprite.textureNumber;
		this.u = sprite.u;
		this.v = sprite.v;
		this.u2 = sprite.u2;
		this.v2 = sprite.v2;
	}

	public Sprite getOverlay()
	{
		return Sprite.get(nameId + "_overlay");
	}
	
	public float getWidth()
	{
		return (u2 - u) * sheetSizes.get(getTexture().getTextureObjectHandle())[0];
	}
	
	public float getHeight()
	{
		return (v2 - v) * sheetSizes.get(getTexture().getTextureObjectHandle())[1];
	}
	
	public float getUniform()
	{
		return 1.0f / (float) sheetSizes.get(getTexture().getTextureObjectHandle())[0];
	}
	
	public Texture getTexture()
	{
		return textures.get(textureNumber);
	}
	
	public int[] getPixels()
	{
		return pixels;
	}
	
	public int getPixel(int x, int y)
	{
		return pixels[x + y * pixelWidth];
	}
	
	public boolean hasPixelData()
	{
		return pixels != null;
	}
	
	public int getPixelWidth()
	{
		return pixelWidth;
	}
	
	public int getPixelHeight()
	{
		return pixelHeight;
	}
	
	
	public Sprite cpy()
	{
		return new Sprite(this);
	}

	public SpriteSequence asSequence()
	{
		return new SpriteSequence(this);
	}
	
	public void render(SpriteBatch batch, float x, float y, float width, float height)
	{
		batch.draw(getTexture(), (int) x, (int) y, (int) width, (int) height, u, v, u2, v2);
	}

	public void render(SpriteBatch batch, float x, float y, float width, float height, boolean flipX)
	{
		render(batch, (int) x, (int) y, (int) width, (int) height, 0, flipX);
	}


	public void render(SpriteBatch batch, float x, float y, float width, float height, boolean flipX, boolean flipY)
	{
		render(batch, (int) x, (int) y, (int) width, (int) height, 0, flipX, flipY);
	}
	
	public void render(SpriteBatch batch, float x, float y, float width, float height, float rotation)
	{
		final Texture texture = getTexture();
		int srcX = (int) (u * texture.getWidth());
		int srcY = (int) (v * texture.getHeight());
		int srcWidth = (int) ((u2 - u) * texture.getWidth());
		int srcHeight = (int) ((v2 - v) * texture.getHeight());
		batch.draw(getTexture(), (int) x, (int) y, width / 2,  height / 2, (int) width, (int) height, 1, 1, rotation, srcX, srcY, srcWidth, srcHeight, false, FlipY);
	}
	
	public void renderWithCustomOrigin(SpriteBatch batch, float x, float y, float width, float height, float rotation, float localOriginX, float localOriginY)
	{
		final Texture texture = getTexture();
		int srcX = (int) (u * texture.getWidth());
		int srcY = (int) (v * texture.getHeight());
		int srcWidth = (int) ((u2 - u) * texture.getWidth());
		int srcHeight = (int) ((v2 - v) * texture.getHeight());
		batch.draw(getTexture(), (int) x, (int) y, localOriginX, localOriginY, (int) width, (int) height, 1, 1, rotation, srcX, srcY, srcWidth, srcHeight, false, FlipY);
	}

	public void render(SpriteBatch batch, float x, float y, float width, float height, float rotation, boolean flipX)
	{
		final Texture texture = getTexture();
		int srcX = (int) (u * texture.getWidth());
		int srcY = (int) (v * texture.getHeight());
		int srcWidth = (int) ((u2 - u) * texture.getWidth());
		int srcHeight = (int) ((v2 - v) * texture.getHeight());
		batch.draw(getTexture(), (int) x, (int) y, width / 2, height / 2, (int) width, (int) height, 1, 1, rotation, srcX, srcY, srcWidth, srcHeight, flipX, FlipY);
	}

	public void render(SpriteBatch batch, float x, float y, float width, float height, float rotation, boolean flipX, boolean flipY)
	{
		final Texture texture = getTexture();
		int srcX = (int) (u * texture.getWidth());
		int srcY = (int) (v * texture.getHeight());
		int srcWidth = (int) ((u2 - u) * texture.getWidth());
		int srcHeight = (int) ((v2 - v) * texture.getHeight());
		batch.draw(getTexture(), (int) x, (int) y, width / 2, height / 2, (int) width, (int) height, 1, 1, rotation, srcX, srcY, srcWidth, srcHeight, flipX, flipY);
	}
	
	public void renderWithOutline(SpriteBatch batch, float x, float y, float width, float height, float borderWidth, Color outlineColor)
	{
		com.badlogic.gdx.graphics.Color c = batch.getColor();
		batch.setColor(outlineColor.r, outlineColor.g, outlineColor.b, outlineColor.a);
		render(batch, x - borderWidth, y, width, height);
		render(batch, x + borderWidth, y, width, height);
		render(batch, x, y - borderWidth, width, height);
		render(batch, x, y + borderWidth, width, height);
		
		//Extra expensive, but extra good
		render(batch, x - borderWidth, y - borderWidth, width, height);
		render(batch, x + borderWidth, y - borderWidth, width, height);
		render(batch, x + borderWidth, y + borderWidth, width, height);
		render(batch, x - borderWidth, y + borderWidth, width, height);
		
		batch.setColor(c);
		render(batch, x, y, width, height);
	}
	
	/** The uv add parameters are in texture space rather than sprite space.
	 */
	public void render(SpriteBatch batch, float x, float y, float width, float height, float uOffset, float vOffset, float u2Width, float v2Height)
	{
		batch.draw(getTexture(), (int) x, (int) y, (int) width, (int) height, u + uOffset, v + vOffset, u + uOffset + u2Width, v + vOffset + v2Height);
	}
	
	public void renderPart(SpriteBatch batch, float x, float y, float width, float height, int partX, int partY, int partWidth, int partHeight, float rotation)
	{
		renderPart(batch, x, y, width, height, partX, partY, partWidth, partHeight, false, rotation);
	}
	
	public void renderPart(SpriteBatch batch, float x, float y, float width, float height, int partX, int partY, int partWidth, int partHeight, boolean flipX, float rotation)
	{
		final Texture texture = getTexture();
		int srcX = (int) (u * texture.getWidth());
		int srcY = (int) (v * texture.getHeight());
		batch.draw(getTexture(), (int) x, (int) y,  width / 2,  height / 2, (int) width, (int) height, 1, 1, rotation, srcX + partX, srcY + partY, partWidth, partHeight, flipX, FlipY);
	}
	
	public void renderPartWithCustomOrigin(SpriteBatch batch, float x, float y, float width, float height, int partX, int partY, int partWidth, int partHeight, boolean flipX, float rotation, float originX, float originY)
	{
		final Texture texture = getTexture();
		int srcX = (int) (u * texture.getWidth());
		int srcY = (int) (v * texture.getHeight());
		batch.draw(getTexture(), (int) x, (int) y,  originX, originY, (int) width, (int) height, 1, 1, rotation, srcX + partX, srcY + partY, partWidth, partHeight, flipX, FlipY);
	}

	public void renderPart(SpriteBatch batch, float x, float y, float width, float height, int partX, int partY, int partWidth, int partHeight)
	{
		renderPart(batch, x, y, width, height, partX, partY, partWidth, partHeight, 0);
	}

	public static Sprite[] get(String... names)
	{
		Sprite[] sprites = new Sprite[names.length];
		for(int i = 0; i < names.length; i++)
		{
			sprites[i] = get(names[i]);
		}
		return sprites;
	}
	
	public static List<Sprite> getAllStartingWith(String name, boolean includeOverlay) {
		name = name.toLowerCase();
		List<Sprite> sprites = new ArrayList<Sprite>();
		for(int i = 0; i < SheetPacker.sprites.size(); i++)
		{
			for(String key : SheetPacker.sprites.get(i).keySet()) {
				if(key.toLowerCase().startsWith(name))
				{
					boolean include = includeOverlay;
					
					if(includeOverlay == false)
					{
						include = !key.toLowerCase().endsWith("_overlay");
					}
					
					if(include)
						sprites.add(SheetPacker.sprites.get(i).get(key));
				}
			}
		}
		return sprites;
	}
	
	public static Sprite get(String name)
	{
		String lower = name.toLowerCase();
		for(int i = 0; i < SheetPacker.sprites.size(); i++)
		{
			Sprite sprite = SheetPacker.sprites.get(i).get(lower);
			if(sprite != null)
				return sprite;
		}
		return spr_error;
	}
	

	public static SpriteSequence getSequence(String... names)
	{
		if(names == null)
			return null;
		return new SpriteSequence(get(names));
	}
	
	public static Sprite[] getAtlasFromSprite(Sprite atlas, int gridWidth, int gridHeight, boolean flipY)
	{
		Sprite[] sprites = new Sprite[gridWidth * gridHeight];
		final float startU = atlas.u;
		final float startV = atlas.v;
		final float uWidth = atlas.u2 - atlas.u;
		final float vHeight = atlas.v2 - atlas.v;
		
		final float xUniform = uWidth / (float) gridWidth;
		final float yUniform = vHeight / (float) gridHeight;
		
		for(int x = 0; x < gridWidth; x++)
		{
			for(int y = 0; y < gridHeight; y++)
			{
				final float u = startU + x * xUniform;
				final float v = startV + y * yUniform;
				Sprite sprite = new Sprite(atlas.textureNumber, u, v, u + xUniform, v + yUniform);
				sprites[x + (flipY ? (gridHeight - 1 - y) : y) * gridWidth] = sprite;
			}
		}
		return sprites;
	}
	
	public static Sprite[] getAtlasFromSprite(Sprite atlas, int gridWidth, int gridHeight)
	{
		return getAtlasFromSprite(atlas, gridWidth, gridHeight, true);
	}
	
	public static Sprite getSpritePart(Sprite atlas, int gridWidth, int gridHeight, int spriteCountPerLine, int index)
	{
		final float startU = atlas.u;
		final float startV = atlas.v;
		final float uWidth = atlas.u2 - atlas.u;
		final float vHeight = atlas.v2 - atlas.v;
		
		final float xUniform = uWidth / (float) gridWidth;
		final float yUniform = vHeight / (float) gridHeight;
		final float u = startU + (index % spriteCountPerLine) * xUniform;
		final float v = startV + (index / spriteCountPerLine) * yUniform;
		return new Sprite(atlas.textureNumber, u, v, u + xUniform, v + yUniform);
	}
	
}
