package chronikspartan.cutecatsplat.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import chronikspartan.cutecatsplat.data.Assets;

import chronikspartan.cutecatsplat.CuteCatSplat;
import com.badlogic.gdx.*;
import com.badlogic.gdx.utils.*;

/**
 * Created by cube on 1/20/2017.
 */

public class Cat{
    private static final int FORWARD_MOVEMENT = 600;
	private static final int NUMBER_OF_CAT_SPRITE_IMAGES = 3;
	private static final int NUMBER_OF_SPLAT_FRAMES = 4;
	private static final int NUMBER_OF_DIRECTIONAL_FRAMES = 3;
    private Vector3 position;
    private Vector3 velocity;
    private Rectangle bounds;
    private Animation catAnimation, splatAnimation, leftAnimation, rightAnimation;
	private Integer splatFrameCount = 0;

    private Texture catTexture, splatTexture, leftTexture, rightTexture;
	
	private boolean catDead = false;
	private boolean moveLeft = false;
	private boolean moveRight = false;

    public Cat(int x, int y, Assets assets){

		// Set original cat position
        position = new Vector3(x, y, 0);

        velocity = new Vector3(0, 0, 0);
		
        catTexture = assets.manager.get(Assets.catSpriteMap);
		splatTexture = assets.manager.get(Assets.splatTexture);
		leftTexture = assets.manager.get(Assets.leftTexture);
		rightTexture = assets.manager.get(Assets.rightTexture);
		
        catAnimation = new Animation(new TextureRegion(catTexture), NUMBER_OF_CAT_SPRITE_IMAGES, 0.4f);
		splatAnimation = new Animation(new TextureRegion(splatTexture), NUMBER_OF_SPLAT_FRAMES, 0.2f);
		leftAnimation = new Animation(new TextureRegion(leftTexture), NUMBER_OF_DIRECTIONAL_FRAMES, 0.4f);
		rightAnimation = new Animation(new TextureRegion(rightTexture), NUMBER_OF_DIRECTIONAL_FRAMES, 0.4f);
		
		// Set cat bounds
        bounds = new Rectangle(x + 3, y, (catTexture.getWidth() / NUMBER_OF_CAT_SPRITE_IMAGES)- 6, catTexture.getHeight());
    }

    public void update(float dt){
		
		// Run cat animation
		if(moveLeft){
			leftAnimation.update(dt);
		}
		else if(moveRight){
			rightAnimation.update(dt);
		}
		else
			catAnimation.update(dt);
		
		// Move cat
        position.add(0, FORWARD_MOVEMENT * dt, 0);
		
		// Move bounds with cat.
        bounds.setPosition(position.x + 3, position.y);
    }
	
	public void moveRight(float deltaX)
	{
		if(deltaX > 3)
		{
			moveRight = true;
			position.add(deltaX,0,0);
		}
	}
	
	public void moveLeft(float deltaX)
	{
		if(deltaX < -3)
		{
			moveLeft = true;
			position.add(deltaX,0,0);
		}
	}
	
	
	public void splat(float dt){
		// Run splat animation
        splatAnimation.update(dt);
		
		// Move cat a few frames ahead
		if(splatFrameCount < 1 )
		{
			position.y = position.y + 20;
			splatFrameCount++;
		}
		
		catDead = true;
	}

    public void dispose(){
        catTexture.dispose();
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getTexture() {
		if(catDead)
		{
			if(splatAnimation.getFrameNumber() == NUMBER_OF_SPLAT_FRAMES - 1)
				splatAnimation.callFinalFrame();
			return splatAnimation.getFrame();
		}
		else if(moveLeft)
		{
			moveLeft = false;
			return leftAnimation.getFrame();
		}
		else if(moveRight)
		{
			moveRight = false;
			return rightAnimation.getFrame();
		}
		else
			return catAnimation.getFrame();
    }

}
