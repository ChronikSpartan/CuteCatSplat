package chronikspartan.cutecatsplat.sprites;

import chronikspartan.cutecatsplat.data.Assets;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

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
    private Random rand;
	boolean pointRecorded;

    public Wall(float y, Assets assets){
		// Load wall texture and then create two Texure Regions
		// one for each sidevof wall
        wall = (Texture) assets.manager.get(Assets.wall);
        catNip = new TextureRegion((Texture) assets.manager.get(Assets.catNip), 0, 0, 96, 96);
        wallExplode = new TextureRegion((Texture) assets.manager.get(Assets.wallExplode));
        leftWall = new TextureRegion(wall);
        rightWall = new TextureRegion(wall);
        leftWall.flip(true, false);

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
        catNipBounds = new Rectangle();
		
		// Create boundaries for points gate
		pointGate = new Rectangle(boundsLeft);
		pointGate.y = pointGate.y + boundsLeft.height;
		pointGate.x = pointGate.x + WALL_GAP;
	}

    public TextureRegion getLeftWall() {
        return rightWall;
    }

    public TextureRegion getRightWall() {
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

    public void reposition(float y){
        Random catNipGenerator = new Random();
        int cng = catNipGenerator.nextInt(5);
		// Reposition walls and boundaries
        posRightWall.set(rand.nextInt(FLUCTUATION) + WALL_GAP + LEFT_OFFSET, y);
        posLeftWall.set(posRightWall.x - WALL_GAP - wall.getWidth(), y);

        boundsRight.setPosition(posRightWall.x, posRightWall.y);
        boundsLeft.setPosition(posLeftWall.x, posLeftWall.y);
        pointGate.setPosition(boundsLeft.x + WALL_GAP, boundsLeft.y + boundsLeft.getHeight());

        if(cng == 3) {
            posCatNip.set(posRightWall.x - WALL_GAP, y);
            catNipBounds.setPosition(posCatNip.x, posCatNip.y);
        }
		
		// Reset point recorded flag ready to record a new point
		pointRecorded = false;
    }

    public boolean collides(Rectangle player){
        return player.overlaps(boundsRight) || player.overlaps(boundsLeft);
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
        return false;

    }

    public void dispose(){
        wall.dispose();
    }
}
