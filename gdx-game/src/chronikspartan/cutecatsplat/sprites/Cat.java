package chronikspartan.cutecatsplat.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import chronikspartan.cutecatsplat.CuteCatSplat;

/**
 * Created by cube on 1/20/2017.
 */

public class Cat{
    private static final int RUN_SPEED = 15;
    private static final int MOVEMENT = 100;
    private Vector3 position;
    private Vector3 velocity;
    private Rectangle bounds;
    private Animation catAnimation;

    private Texture texture;

    public Cat(int x, int y){

        position = new Vector3(x, y, 0);

        velocity = new Vector3(0, 0, 0);
        texture = new Texture("images/Pixel_Cat_Sprite_Map.png");
        catAnimation = new Animation(new TextureRegion(texture), 2, 0.5f);

        bounds = new Rectangle(x + 3, y, (texture.getWidth() / 2)- 6, texture.getHeight());
    }

    public void update(float dt){
        catAnimation.update(dt);
        position.add(0, MOVEMENT * dt, 0);
        bounds.setPosition(position.x + 3, position.y);
    }

    public void move(int x){
        position.x = x;
    }

    public void dispose(){
        texture.dispose();
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getTexture() {
        return catAnimation.getFrame();
    }

}
