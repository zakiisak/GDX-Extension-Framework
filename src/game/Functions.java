package game;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Functions {
	
	public static void listPhysicalFilesRecursively(List<FileHandle> files, String directory)
	{
		FileHandle[] listedFiles = Gdx.files.local(directory).list();
		for(int i = 0; i < listedFiles.length; i++)
		{
			FileHandle handle = listedFiles[i];
			if(handle.isDirectory())
			{
				listPhysicalFilesRecursively(files, handle.path());
			}
			else
			{
				files.add(handle);
			}
		}
	}
	
	
}
