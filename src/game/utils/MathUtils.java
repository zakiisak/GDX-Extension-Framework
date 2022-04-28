package game.utils;

import java.util.Random;

public class MathUtils {

    private static Random random = new Random();

    public static float abs(float val)
    {
        if(val < 0)
            return val *= -1;
        return val;
    }
    
    public static float angleBetween(float x1, float y1, float x2, float y2)
    {
    	float degrees = (float) Math.toDegrees(Math.atan2(y2 - y1, x2 - x1));
    	while(degrees < 0)
    		degrees += 360;
    	while(degrees >= 360)
    		degrees -= 360;
    	return degrees;
    }
    
    public static float clamp(float value, float min, float max)
    {
    	if(value < min)
    		return min;
    	if(value > max)
    		return max;
    	return value;
    }

    public static int randSign()
    {
        return random.nextInt(1) * 2 - 1;
    }

    public static int random(int excludingThisNumber)
    {
        if(excludingThisNumber <= 1)
            return 0;
        return random.nextInt(excludingThisNumber);
    }

    public static Vector2<Float> getPointOnCircle(float degrees)
    {
        final double rad = Math.toRadians(degrees);
        return new Vector2<Float>((float) Math.cos(rad), (float) Math.sin(rad));
    }
    
    public static Vector2<Float> getPointOnCircle(float degrees, float scale)
    {
        final double rad = Math.toRadians(degrees);
        return new Vector2<Float>((float) Math.cos(rad) * scale, (float) Math.sin(rad) * scale);
    }
    

    public static float random()
    {
        return random.nextFloat();
    }

    public static long variance(long number, double percentVariance)
    {
        long percent = (long) ((double) number * percentVariance);
        return (long) ((double) random() * (percent * 2)) + (number - percent);
    }

    public static float getDistance(float x1, float y1, float x2, float y2)
    {
        final float xdiff = x2 - x1;
        final float ydiff = y2 - y1;
        return (float) Math.sqrt(xdiff * xdiff + ydiff * ydiff);
    }

    public static float getVectorLength(float x, float y)
    {
        return (float) Math.sqrt(x * x + y * y);
    }

    public static int clampDegrees(int value)
    {
        while(value < 0)
            value += 360;
        while(value > 360)
            value -= 360;
        return value;
    }

    public static int max(int... values)
    {
        int maxValue = 0;
        for(int i = 0; i < values.length; i++)
        {
            if(values[i] > maxValue)
                maxValue = values[i];
        }
        return maxValue;
    }

    public static float max(float... values)
    {
        float maxValue = 0;
        for(int i = 0; i < values.length; i++)
        {
            if(values[i] > maxValue)
                maxValue = values[i];
        }
        return maxValue;
    }

    /*
    public static void main(String[] args)
    {
        long[] numbers = randomNumbersThatAddUpTo(1000000000000L, 1000000, 0.25);
        for(int i = 0; i < numbers.length; i++)
            System.out.println(numbers[i]);
        long sum = 0;
        for(int i = 0; i < numbers.length; i++)
            sum += numbers[i];
        System.out.println("sum: " + sum);
    }
    */
    
    public static boolean chance(double chance)
    {
    	return Math.random() < chance;
    }

    public static long[] randomNumbersThatAddUpTo(long n, int count, double variancePercent)
    {
        long[] numbers = new long[count];
        long averageNumPerIndex = n / count;
        long variance = (long) ((double) averageNumPerIndex * variancePercent);

        long averageMin = averageNumPerIndex - variance;
        long range = variance * 2;

        long diffFromOrigAverage = 0;

        for(int i = 0; i < count; i++)
        {
            long randomBit = 0;
            if(range != 0)
                randomBit = Math.abs(random.nextLong()) % range;
           long generatedNumber = Math.min((averageMin - diffFromOrigAverage) + randomBit, n);
           //last index
           if(i == count - 1)
           {
               if(n < averageMin + range)
                   generatedNumber = n;
           }
           n -= generatedNumber;
           numbers[i] = generatedNumber;
           diffFromOrigAverage += generatedNumber - averageNumPerIndex;
        }
        return numbers;
    }
}
