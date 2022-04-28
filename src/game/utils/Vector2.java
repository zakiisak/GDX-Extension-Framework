package game.utils;

public class Vector2<T> {
    private T x, y;

    public Vector2() {}
    public Vector2(T x, T y) {
        this.x = x;
        this.y = y;
    }

    public void set(Vector2<T> vec)
    {
        this.x = vec.x;
        this.y = vec.y;
    }

    public T getX()
    {
        return x;
    }

    public T getY()
    {
        return y;
    }

    public void setX(T x)
    {
        this.x = x;
    }

    public void setY(T y)
    {
        this.y = y;
    }
    
    public boolean equals(Vector2<T> vector) {
    	return vector.getX() == x && vector.getY() == y;
    }


}
