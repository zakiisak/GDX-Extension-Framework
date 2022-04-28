package game.graphics;

public class SpriteSequence {

    private Sprite[] sprites;
    private int index;

    private int animationDelay = 30;
    private int animationTick = 0;

    private boolean disableAnimation;
    private boolean updateReverse = false;
    private int direction = 1;

    public SpriteSequence(Sprite... sprites)
    {
        this.sprites = sprites;
    }

    public SpriteSequence setAnimationDelay(int animationDelay)
    {
        this.animationDelay = animationDelay;
        return this;
    }

    public boolean isAnimationDisabled()
    {
        return disableAnimation;
    }

    public SpriteSequence disableAnimation()
    {
        this.disableAnimation = true;
        return this;
    }

    public int getAnimationDelay()
    {
        return animationDelay;
    }

    public SpriteSequence updateReverse()
    {
        updateReverse = true;
        return this;
    }

    public boolean isUpdatingReverse()
    {
        return updateReverse;
    }

    public void updateSequence()
    {
        if(disableAnimation == false) {
            if (animationTick >= animationDelay) {
                animationTick = 0;

                if (direction > 0) {
                    if (index + 1 >= sprites.length) {
                        if (updateReverse) {
                            direction *= -1;
                            index--;
                        } else index = 0;
                    } else {
                        index++;
                    }
                } else if (direction < 0) {
                    if (index - 1 < 0) {
                        if (updateReverse) {
                            direction *= -1;
                            index++;
                        } else index = sprites.length - 1;
                    } else {
                        index--;
                    }
                }
            } else animationTick++;
        }
    }

    public int getIndex()
    {
        return index;
    }

    public Sprite getSprite(int index)
    {
        return sprites[index];
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public Sprite getSprite()
    {
        return sprites[index];
    }
    public Sprite[] getSpriteCollection() {return sprites; }

    public int getSpriteCount()
    {
        return sprites.length;
    }

    public int getWidth()
    {
        return (int) Math.round(sprites[index].getWidth());
    }
    public int getHeight()
    {
        return (int) Math.round(sprites[index].getHeight());
    }

}
