package chronikspartan.cutecatsplat.sprites;

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
    private TextureRegion rightWall, leftWall;
    private Vector2 posRightWall, posLeftWall;
    private Rectangle boundsRight, boundsLeft, pointGate;
    private Random rand;
	boolean pointRecorded;

    public Wall(float y){
		// Load wall texture and then create two Texure Regions
		// one for each sidevof wall
        wall = new Texture("images/Wall.jpg");
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

    public Vector2 getPosLeftWall() {
        return posRightWall;
    }

    public Vector2 getPosRightWall() {
        return posLeftWall;
    }

    public void reposition(float y){
		// Reposition walls and boundaries
        posRightWall.set(rand.nextInt(FLUCTUATION) + WALL_GAP + LEFT_OFFSET, y);
        posLeftWall.set(posRightWall.x - WALL_GAP - wall.getWidth(), y);

        boundsRight.setPosition(posRightWall.x, posRightWall.y);
        boundsLeft.setPosition(posLeftWall.x, posLeftWall.y);
        pointGate.setPosition(boundsLeft.x + WALL_GAP, boundsLeft.y + boundsLeft.getHeight());
		
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

    public void dispose(){
        wall.dispose();
    }
}
