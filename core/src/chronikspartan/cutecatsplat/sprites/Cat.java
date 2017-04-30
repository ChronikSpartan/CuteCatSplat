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
	private Rectangle bounds, sideBounds;
    private Animation catAnimation, splatAnimation, leftAnimation, rightAnimation;
	private Integer splatFrameCount = 0;

    private Texture catTexture;

	private boolean catDead = false;
	private boolean moveLeft = false;
	private boolean moveRight = false;

	public boolean allowMove = true;

    public Cat(int x, int y, Assets assets, int catNumber){

		// Set original cat position
        position = new Vector3(x, y, 0);

		Texture splatTexture, leftTexture, rightTexture;
		switch (catNumber){
			case 1:
				catTexture = (Texture) assets.manager.get(Assets.catSpriteMap);
				splatTexture = (Texture) assets.manager.get(Assets.splatTexture);
				leftTexture = (Texture) assets.manager.get(Assets.leftTexture);
				rightTexture = (Texture) assets.manager.get(Assets.rightTexture);
				break;
			case 2:
				catTexture = (Texture) assets.manager.get(Assets.leroySpriteMap);
				splatTexture = (Texture) assets.manager.get(Assets.splatTexture);
				leftTexture = (Texture) assets.manager.get(Assets.leroyLeftTexture);
				rightTexture = (Texture) assets.manager.get(Assets.leroyRightTexture);
				break;
			case 3:
				catTexture = (Texture) assets.manager.get(Assets.tillySpriteMap);
				splatTexture = (Texture) assets.manager.get(Assets.splatTexture);
				leftTexture = (Texture) assets.manager.get(Assets.tillyLeftTexture);
				rightTexture = (Texture) assets.manager.get(Assets.tillyRightTexture);
				break;
			case 4:
				catTexture = (Texture) assets.manager.get(Assets.trampySpriteMap);
				splatTexture = (Texture) assets.manager.get(Assets.splatTexture);
				leftTexture = (Texture) assets.manager.get(Assets.trampyLeftTexture);
				rightTexture = (Texture) assets.manager.get(Assets.trampyRightTexture);
				break;
			default:
				catTexture = (Texture) assets.manager.get(Assets.catSpriteMap);
				splatTexture = (Texture) assets.manager.get(Assets.splatTexture);
				leftTexture = (Texture) assets.manager.get(Assets.leftTexture);
				rightTexture = (Texture) assets.manager.get(Assets.rightTexture);
				break;
		}

        catAnimation = new Animation(new TextureRegion(catTexture), NUMBER_OF_CAT_SPRITE_IMAGES, 0.4f, true);
		splatAnimation = new Animation(new TextureRegion(splatTexture), NUMBER_OF_SPLAT_FRAMES, 0.2f, false);
		leftAnimation = new Animation(new TextureRegion(leftTexture), NUMBER_OF_DIRECTIONAL_FRAMES, 0.4f, true);
		rightAnimation = new Animation(new TextureRegion(rightTexture), NUMBER_OF_DIRECTIONAL_FRAMES, 0.4f, true);
		
		// Set cat bounds
        bounds = new Rectangle(x + 15, y + (catTexture.getHeight()/1.2f), (catTexture.getWidth() / NUMBER_OF_CAT_SPRITE_IMAGES)- 30, catTexture.getHeight()/10);
		sideBounds = new Rectangle(x + 12, y, (catTexture.getWidth() / NUMBER_OF_CAT_SPRITE_IMAGES)- 24, catTexture.getHeight());
		
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
        bounds.setPosition(position.x + 15, position.y + (catTexture.getHeight()/1.2f));
		sideBounds.setPosition(position.x + 12, position.y);
		
    }
	
	public void moveRight(float deltaX)
	{
		if(deltaX > 3 && allowMove)
		{
			moveRight = true;
			position.add(deltaX,0,0);
		}
	}
	
	public void moveLeft(float deltaX)
	{
		if(deltaX < -3 && allowMove)
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
	
	public Rectangle getSideBounds(){
        return sideBounds;
    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getTexture() {
		if(catDead)
		{
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
