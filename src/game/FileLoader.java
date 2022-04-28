package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class FileLoader {

    public static FileHandle get(String path)
    {
        return Gdx.files.local(path);
    }
}
