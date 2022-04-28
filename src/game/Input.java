package game;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.InputAdapter;

import game.gui.TextAdapter;

public class Input extends InputAdapter {
	
	private static TextAdapter activeTextAdapter;

	static Map<Integer, Boolean> mouseButtons = new HashMap<Integer, Boolean>();

	private void resetJustPressed()
	{
		for(Integer i : mouseButtons.keySet())
		{
			mouseButtons.put(i, false);
		}
	}

	public void refresh()
	{
		resetJustPressed();
	}

	public boolean isButtonJustPressed(int button)
	{
		if(mouseButtons.containsKey(button))
		{
			return mouseButtons.get(button);
		}
		else return false;
	}

	public boolean touchDown (int screenX, int screenY, int pointer, int button) {
		mouseButtons.put(button, true);
		return false;
	}
	
	public static void bindTextAdapter(TextAdapter adapter)
	{
		activeTextAdapter = adapter;
	}
	
	public static void unbindTextAdapter()
	{
		activeTextAdapter = null;
	}
	
	public static TextAdapter getBoundTextWriter()
	{
		return activeTextAdapter;
	}
	
	
	public boolean keyTyped(char character) {
		if(activeTextAdapter != null) activeTextAdapter.input(character);
		return false;

	}


	
}
