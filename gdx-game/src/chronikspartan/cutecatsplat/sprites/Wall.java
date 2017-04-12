package chronikspartan.cutecatsplat.sprites;

import chronikspartan.cutecatsplat.data.Assets;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;
import chronikspartan.cutecatsplat.states.*;

/**
 * Created by cube on 1/20/2017.
 */

public class Wall {
    private static final int FLUCTUATION = 900;
    private static final int WALL_GAP = 150;
    private static final int LEFT_OFFSET = 5;
    public static final int WALL_WIDTH = 52;

    private static Texture wall;
    private TextureRegion rightWall, leftWall, wallExplode, catNip;
    private Vector2 posRightWall, posLeftWall, posCatNip;
    private Rectangle boundsRight, boundsLeft, pointGate, catNipBounds;
	private Animation wallExploding;
    private Random rand;
	private String side;
	boolean pointRecorded;
	boolean explode = false;

    public Wall(float y, Assets assets){
		// Load wall texture and then create two Texure Regions
		// one for each sidevof wall
        wall = (Texture) assets.manager.get(Assets.wall);
        catNip = new TextureRegion((Texture) assets.manager.get(Assets.catNip), 0, 0, 96, 96);
        wallExplode = new TextureRegion((Texture) assets.manager.get(Assets.wallExplode));
        leftWall = new TextureRegion(wall);
        rightWall = new TextureRegion(wall);
        leftWall.flip(true, false);
		
		wallExploding = new Animation(wallExplode, 3, 0.2f, false);

		// Initialise
        rand = new Random();
		pointRecorded = false;

		// Set position of walls
        posRightWall = new Vector2(rand.nextInt(FLUCTUATION) + WALL_GAP + LEFT_OFFSET, y);
        posLeftWall = new Vector2(posRightWall.x - WALL_GAP - wall.getWidth(), y);

		// Create boundaries for wall collisions
        boundsRight = new Rectangle(posRightWall.x, posRightWall.y, rightWall.getRegionWidth(), rightWall.getRegionHeight());
        boundsLeft = new Rectangle(posLeftWall.x, posLeftWall.y, leftWall.getRegionWidth(), leftWall.getRegionHeight());

        posCatNip = new Vector2();
        catNipBounds = new Rectangle(0, 0, catNip.getRegionWidth(), catNip.getRegionHeight());
		
		// Create boundaries for points gate
		pointGate = new Rectangle(boundsLeft);
		pointGate.y = pointGate.y + boundsLeft.height;
		pointGate.x = 0;
	}
	
	public Rectangle getDebugPointGate()
	{
		return pointGate;
	}

    public TextureRegion getLeftWall() {
		if(explode && side =="RIGHT"){
			return wallExploding.getFrame();
		}
		return rightWall;
    }

    public TextureRegion getRightWall() {
		if(explode && side =="LEFT"){
			return wallExploding.getFrame();
		}
        return leftWall;
    }

    public TextureRegion getCatNip() { return catNip; }

    public Vector2 getPosLeftWall() {
        return posRightWall;
    }

    public Vector2 getPosRightWall() {
        return posLeftWall;
    }

    public Vector2 getPosCatNip() { return posCatNip; }
	
	public void explode(float dt, String side){

		this.side = side;
		// Wall explode animation
		wallExploding.update(dt);
		explode = true;
    }
	
	public void shift(){
		posRightWall.add(0, -wallExplode.getRegionHeight()/2);
	}

    public void reposition(float y){
		explode = false;
		wallExploding.reset();
		
        Random catNipGenerator = new Random();
        int cng = catNipGenerator.nextInt(5);
		// Reposition walls and boundaries
		posRightWall.set(rand.nextInt(FLUCTUATION) + WALL_GAP + LEFT_OFFSET, y);
        posLeftWall.set(posRightWall.x - WALL_GAP - wall.getWidth(), y);

        boundsRight.setPosition(posRightWall.x, posRightWall.y);
        boundsLeft.setPosition(posLeftWall.x, posLeftWall.y);
        pointGate.setPosition(0, boundsLeft.y + boundsLeft.getHeight());

        if(cng == 2) {
            posCatNip.set(posRightWall.x - WALL_GAP/1.5f, y);
            catNipBounds.setPosition(posCatNip.x, posCatNip.y);
        }
		
		// Reset point recorded flag ready to record a new point
		pointRecorded = false;
    }

    public boolean collidesWithRight(Rectangle player){
		return player.overlaps(boundsRight);
	}
		
	public boolean collidesWithLeft(Rectangle player){
		return player.overlaps(boundsLeft);
    }

    public boolean pointGained(Rectangle player){
		if (player.overlaps(pointGate) && pointRecorded == false){
			// Set point recorded flag to true so multiple points
			// are not gained while cat passes through gate
			pointRecorded = true;
			return true;
		}
		return false;
			
    }

    public boolean catNipGained(Rectangle player){
		if (player.overlaps(catNipBounds)){
			// Start cat nip
			posCatNip.set(0 , 0);
			catNipBounds.setPosition(0, 0);
			return true;
		}
        return false;

    }

    public void dispose(){
        wall.dispose();
    }
}
