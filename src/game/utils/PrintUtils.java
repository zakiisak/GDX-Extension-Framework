package game.utils;

import java.lang.reflect.Field;

public class PrintUtils {

    public static <T> void print(T object)
    {
        System.out.println(object + ": ");
        Field[] fields = object.getClass().getFields();
        for(int i  = 0; i < fields.length; i++)
        {
            try {
                System.out.println(fields[i].getName() + "=" + fields[i].get(object));
            } catch (IllegalAccessException e) {
                System.err.println("could not access " + fields[i] + " - " + e.getLocalizedMessage());
            }
        }
    }
}
