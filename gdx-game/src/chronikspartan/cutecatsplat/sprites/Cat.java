package chronikspartan.cutecatsplat.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import chronikspartan.cutecatsplat.CuteCatSplat;
import com.badlogic.gdx.*;
import com.badlogic.gdx.utils.*;
import android.view.*;

/**
 * Created by cube on 1/20/2017.
 */

public class Cat{
    private static final int RUN_SPEED = 15;
    private static final int FORWARD_MOVEMENT = 300;
	//private static final int SIDE_MOVEMENT = 50;
	//private int sideMovement = 1;
    private Vector3 position;
    private Vector3 velocity;
    private Rectangle bounds;
    private Animation catAnimation, splatAnimation;

    private Texture catTexture, splatTexture;
	
	private boolean catDead = false;

    public Cat(int x, int y){

		// Set original cat position
        position = new Vector3(x, y, 0);

        velocity = new Vector3(0, 0, 0);
		
        catTexture = new Texture("images/Cat_Sprite_Map.png");
		splatTexture = new Texture("images/Splat_Sprite_Map.png");
		
        catAnimation = new Animation(new TextureRegion(catTexture), 2, 0.35f);
		splatAnimation = new Animation(new TextureRegion(splatTexture), 4, 0.2f);
	
		// Set cat bounds
        bounds = new Rectangle(x + 3, y, (catTexture.getWidth() / 2)- 6, catTexture.getHeight());
    }

    public void update(float dt){
		// Run cat animation
        catAnimation.update(dt);
		
		// Move1 cat
        position.add(0, FORWARD_MOVEMENT * dt, 0);
		
		/* TOSH
		// Move2 cat
		position.add(sideMovement, FORWARD_MOVEMENT * dt, 0);
		velocity.scl(1/dt);
		*/
		
		// Move bounds with cat.
        bounds.setPosition(position.x + 3, position.y);
    }

    public void move(int x){
        position.x = x;
    }
	
	/*
	public void move2()
	{
		sideMovement = -sideMovement;
		velocity.x = sideMovement;
	}
	*/
	
	public void splat(float dt){
		// Run splat animation
        splatAnimation.update(dt);
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
		if(!catDead)
        	return catAnimation.getFrame();
		else
			if(splatAnimation.getFrameNumber() == 3)
				splatAnimation.callFinalFrame();
			return splatAnimation.getFrame();
    }

}
