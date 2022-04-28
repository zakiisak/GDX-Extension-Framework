package game.utils;

import com.badlogic.gdx.graphics.Color;

public class ColorUtils {

    public static Color hsv(float hue, float saturation, float value){

        saturation = com.badlogic.gdx.math.MathUtils.clamp(saturation, 0.0f, 1.0f);
        while (hue < 0) hue++;
        while (hue >= 1) hue--;
        value = com.badlogic.gdx.math.MathUtils.clamp(value, 0.0f, 1.0f);

        float red = 0.0f;
        float green = 0.0f;
        float blue = 0.0f;

        final float hf = (hue - (int) hue) * 6.0f;
        final int ihf = (int) hf;
        final float f = hf - ihf;
        final float pv = value * (1.0f - saturation);
        final float qv = value * (1.0f - saturation * f);
        final float tv = value * (1.0f - saturation * (1.0f - f));

        switch (ihf) {
            case 0:         // Red is the dominant color
                red = value;
                green = tv;
                blue = pv;
                break;
            case 1:         // Green is the dominant color
                red = qv;
                green = value;
                blue = pv;
                break;
            case 2:
                red = pv;
                green = value;
                blue = tv;
                break;
            case 3:         // Blue is the dominant color
                red = pv;
                green = qv;
                blue = value;
                break;
            case 4:
                red = tv;
                green = pv;
                blue = value;
                break;
            case 5:         // Red is the dominant color
                red = value;
                green = pv;
                blue = qv;
                break;
        }

        return new Color(red, green, blue, 1);
    }
}
