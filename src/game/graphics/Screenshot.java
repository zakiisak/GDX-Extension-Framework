package game.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.utils.ScreenUtils;

import java.nio.ByteBuffer;

public class Screenshot {

    public static void save()
    {
        int counter = 1;
        FileHandle handle;
        do {
            handle = Gdx.files.local("screenshot_" + (counter++) + ".png");
        } while(handle.exists());
        Pixmap map = getScreenshot(true);
        PixmapIO.writePNG(handle, map);
        map.dispose();
    }

    public static Pixmap getScreenshot(boolean yDown) {

        final int w = Gdx.graphics.getWidth();
        final int h = Gdx.graphics.getHeight();
        final Pixmap pixmap = ScreenUtils.getFrameBufferPixmap(0, 0, w, h);
        if (yDown) {
            // Flip the pixmap upside down
            ByteBuffer pixels = pixmap.getPixels();
            int numBytes = w * h * 4;
            byte[] lines = new byte[numBytes];
            int numBytesPerLine = w * 4;
            for (int i = 0; i < h; i++) {
                pixels.position((h - i - 1) * numBytesPerLine);
                pixels.get(lines, i * numBytesPerLine, numBytesPerLine);
            }
            pixels.clear();
            pixels.put(lines);
            pixels.clear();
        }


        return pixmap;
    }
}
