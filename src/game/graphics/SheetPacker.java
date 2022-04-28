package game.graphics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.glutils.PixmapTextureData;
import com.badlogic.gdx.utils.GdxRuntimeException;

public final class SheetPacker {
	public static int SIZE = 4096;
	private static List<ImageData> images = new ArrayList<ImageData>();
	private static Map<String, SpriteOnInit> referencers = new HashMap<String, SpriteOnInit>();
	public static List<Map<String, Sprite>> sprites = new ArrayList<Map<String, Sprite>>();
	public static List<Integer> textureAtlasses = new ArrayList<Integer>();
	private static int sheetIdCounter = 0;
	
	/**
	 * Stores the sprites in the static sprites field in this class.
	 */
	public static void pack()
	{
		SIZE = GL11.glGetInteger(GL11.GL_MAX_TEXTURE_SIZE);
		if(SIZE > 4096)
			SIZE = 4096;
		if(images.size() < 1)
		{
			System.out.println("buffered images are empty. Returning....");
			return;
		}
		pack(images);
		images.clear();
		referencers.clear();
	}
	
	private static void pack(List<ImageData> images)
	{
		System.out.println("referencers size: " + referencers.size());
		int size = GL11.glGetInteger(GL11.GL_MAX_TEXTURE_SIZE);
		if(size > SIZE)
			size = SIZE;
		double uniform = 1.0 / (double) size;
		System.out.println("Creating Texture Atlas: " + size + "x" + size);
		
		Pixmap buffer = new Pixmap(size, size, Format.RGBA8888);
		Texture handle = new Texture(buffer, false);
		Map<String, Sprite> map = new HashMap<String, Sprite>();
		
		int xTex = 0;
		int yTex = 0;
		
		int maxHeight = 0;
		
		List<ImageData> temp = new ArrayList<ImageData>();
		
		for(ImageData image : images)
		{
			Pixmap data = image.image;
			int width = data.getWidth() + (image.addWhiteOverlay ? data.getWidth() : 0);
			int height = data.getHeight();
			if(height > maxHeight)
			{
				maxHeight = height;
			}
			
			if(xTex + width >= size)
			{
				yTex += maxHeight;
				xTex = 0;
			}
			if(yTex + height >= size)
			{
				temp.add(image);
				continue;
			}
			for(int x = 0; x < data.getWidth(); x++)
			{
				for(int y = 0; y < height; y++)
				{
					buffer.drawPixel(xTex + x, yTex + y, data.getPixel(x, y));
					//pixels[(xTex + x) + (yTex + y) * size] = data[x + y * width];
				}
			}
			double u = (float) ((double) xTex * uniform);
			double v = (float) ((double) yTex * uniform);
			Sprite sprite = new Sprite(sheetIdCounter, (float)u, (float)v, (float)  (u + data.getWidth() * uniform),  (float) ( v + height * uniform));
			if(image.addWhiteOverlay)
			{
				u += data.getWidth() * uniform;
				for(int x = 0; x < data.getWidth(); x++)
				{
					for(int y = 0; y < height; y++)
					{
						int pixel = data.getPixel(x, y);
						int Alpha = (pixel & 0x000000ff);
						if(Alpha > 0) //Make alpha max for nice outlines
							Alpha = 255;
						if(Alpha != 0)
							buffer.drawPixel(xTex + data.getWidth() + x, yTex + y, 
									((0xFF & 255) << 24) | ((0xFF & 255) << 16) |
						            ((0xFF & 255) << 8) | (0xFF & Alpha));
						else
							buffer.drawPixel(xTex + data.getWidth() + x, yTex + y, pixel);
					}
				}
				Sprite spriteOverlay = new Sprite(sheetIdCounter, (float)u, (float)v, (float)(u + data.getWidth() * uniform), (float)(v + height * uniform));
				spriteOverlay.nameId = image.name + "_overlay";
				map.put(image.name + "_overlay", spriteOverlay);
			}
			
			if(referencers.get(image.name.toLowerCase()) != null)
			{
				referencers.get(image.name).onInitialized(data, sprite);
			}
			else System.out.println("sprite " + image.name + " has no referencer");
			sprite.nameId = image.name;
			map.put(image.name, sprite);
			//System.out.println("adding sprite: " + image.name + " uv: " + sprite.u + ", " + sprite.v + ", " + sprite.u2 + ", " + sprite.v2);
			xTex += width + 1;
		}
		handle.load(new PixmapTextureData(buffer, null, true, false));
		handle.setWrap(TextureWrap.ClampToEdge, TextureWrap.ClampToEdge);
		handle.setFilter(TextureFilter.MipMapLinearNearest, TextureFilter.Nearest);
		//PixmapIO.writePNG(Gdx.files.local("res/sheet.png"), buffer);
		buffer.dispose();
		
		Sprite.registerTextureSize(handle.getTextureObjectHandle(), SIZE, SIZE);
		Sprite.registerTexture(sheetIdCounter, handle);
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		textureAtlasses.add(handle.getTextureObjectHandle());
		
		sprites.add(map);
		sheetIdCounter++;
		if(temp.size() > 0)
		{
			pack(temp);
		}
	}
	
	public static ImageData addImage(FileHandle file, String spriteName, SpriteOnInit referencer)
	{
		ImageData data = new ImageData();
		try
		{
			data.image = new Pixmap(file);
		}
		catch(GdxRuntimeException e)
		{
			e.printStackTrace();
			return null;
		}
		if(data.image.getWidth() > SIZE || data.image.getHeight() > SIZE)
		{
			System.err.println("Texture " + file.path() + " is too large to be loaded!");
			System.err.println("Textures can't be larger than " + SIZE + "x" + SIZE);
			return null;
		}
		data.name = spriteName.toLowerCase();
		if(referencer != null)
			referencers.put(spriteName.toLowerCase(), referencer);
		images.add(data);
		return data;
	}
	
	public static ImageData addImage(String file, String spriteName, SpriteOnInit referencer)
	{
		return addImage(Gdx.files.local(file), spriteName, referencer);
	}
	
	public static ImageData addImage(FileHandle file, String spriteName)
	{
		return addImage(file, spriteName, null);
	}
	
	public static ImageData addImageWithDottedName(FileHandle file, FileHandle rootDirectory) {

		String name = file.nameWithoutExtension().toLowerCase();
		FileHandle parent = file;
		while((parent = parent.parent()).equals(rootDirectory) == false)
		{
			name = parent.nameWithoutExtension().toLowerCase() + "." + name;
		}

		return addImage(file, name);
	}
	
	
	public static ImageData addImage(FileHandle file, SpriteOnInit referencer)
	{
		return addImage(file, file.nameWithoutExtension(), referencer);
	}
	
	public static ImageData addImage(FileHandle file)
	{
		return addImage(file, (SpriteOnInit) null);
	}
	
	public static ImageData addImage(String file)
	{
		return addImage(Gdx.files.local(file), (SpriteOnInit) null);
	}
	
	public static ImageData addImage(String file, SpriteOnInit referencer)
	{
		return addImage(Gdx.files.local(file), referencer);
	}
	
	public static void addReferencer(String spriteName, SpriteOnInit referencer)
	{
		System.out.println("adding referencer " + spriteName);
		referencers.put(spriteName.toLowerCase(), referencer);
	}
	
	public static void clearAtlasses()
	{
		for(int texture : textureAtlasses)
		{
			GL11.glDeleteTextures(texture);
		}
	}
	
	protected static class ImageData
	{
		public String name;
		public Pixmap image;
		public boolean flipY = false;
		public boolean addWhiteOverlay = false;
	}
	
	public static interface SpriteOnInit
	{
		public void onInitialized(Pixmap image, Sprite sprite);
	}
	
	public static SpriteOnInit assignPixelData = new SpriteOnInit() {
		
		@Override
		public void onInitialized(Pixmap image, Sprite sprite) {
			System.out.println("running this");
			int[] array = new int[image.getWidth() * image.getHeight()];
			
			for(int x = 0; x < image.getWidth(); x++)
			{
				for(int y = 0; y < image.getHeight(); y++)
				{
					array[x + y * image.getWidth()] = image.getPixel(x,  y);
				}
			}
			sprite.setPixelData(array, image.getWidth(), image.getHeight());
		}
	};
	
}
