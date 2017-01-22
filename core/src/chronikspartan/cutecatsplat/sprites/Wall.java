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
    private static final int FLUCTUATION = 90;
    private static final int WALL_GAP = 20;
    private static final int LEFT_OFFSET = 5;
    public static final int WALL_WIDTH = 52;

    private static Texture wall;
    private TextureRegion leftWall, rightWall;
    private Vector2 posLeftWall, posRightWall;
    private Rectangle boundsLeft, boundsRight, pointGate;
    private Random rand;

    public Wall(float y){
        wall = new Texture("images/Pixel_Wall.png");
        rightWall = new TextureRegion(wall);
        leftWall = new TextureRegion(wall);
        leftWall.flip(true, false);

        rand = new Random();

        posLeftWall = new Vector2(rand.nextInt(FLUCTUATION) + WALL_GAP + LEFT_OFFSET, y);
        posRightWall = new Vector2(posLeftWall.x - WALL_GAP - wall.getWidth(), y);

        boundsLeft = new Rectangle(posLeftWall.x, posLeftWall.y, leftWall.getRegionWidth(), leftWall.getRegionHeight());
        boundsRight = new Rectangle(posRightWall.x, posRightWall.y, rightWall.getRegionWidth(), rightWall.getRegionHeight());
        pointGate = new Rectangle(posLeftWall.x, posLeftWall.y + getLeftWall().getRegionHeight(), leftWall.getRegionWidth() * 3, 1);
    }



    public TextureRegion getLeftWall() {
        return leftWall;
    }

    public TextureRegion getRightWall() {
        return rightWall;
    }

    public Vector2 getPosLeftWall() {
        return posLeftWall;
    }

    public Vector2 getPosRightWall() {
        return posRightWall;
    }

    public void reposition(float y){
        posLeftWall.set(rand.nextInt(FLUCTUATION) + WALL_GAP + LEFT_OFFSET, y);
        posRightWall.set(posLeftWall.x - WALL_GAP - wall.getWidth(), y);

        boundsLeft.setPosition(posLeftWall.x, posLeftWall.y);
        boundsRight.setPosition(posRightWall.x, posRightWall.y);
        pointGate.setPosition(posLeftWall.x, posRightWall.y + leftWall.getRegionHeight());
    }

    public boolean collides(Rectangle player){
        return player.overlaps(boundsLeft) || player.overlaps(boundsRight);
    }

    public boolean pointGained(Rectangle player){
        return player.overlaps(pointGate);
    }

    public void dispose(){
        wall.dispose();
    }
}
