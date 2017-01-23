package chronikspartan.cutecatsplat.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import chronikspartan.cutecatsplat.Assets;
import chronikspartan.cutecatsplat.CuteCatSplat;
import chronikspartan.cutecatsplat.ParallaxBackground;
import chronikspartan.cutecatsplat.ParallaxLayer;
import chronikspartan.cutecatsplat.sprites.Cat;
import chronikspartan.cutecatsplat.sprites.Wall;

/**
 * Created by cube on 1/20/2017.
 */

public class PlayState extends State {
    private static final int WALL_SPACING = 40;
    private static final int WALL_COUNT = 4;

    private FileHandle blockedFontFile;
    private BitmapFont blockedFont;


    private Cat cat;
    ParallaxBackground parallax_background;
    Vector3 touch;
    Texture bush, texture_grass;
    TextureRegion imgTextureBushRegionLeft, imgTextureBushRegionRight, imgTextureGrassRegion;

    Vector2 rightBushPos1, rightBushPos2, leftBushPos1, leftBushPos2;

    public int points = 0;

    private Array<Wall> walls;

    protected PlayState(GameStateManager gsm){
        super(gsm);
        cat = new Cat(50, 100);
        touch = new Vector3();

        blockedFontFile = new FileHandle("images/Font/blocked/dosapp.fon");
       // blockedFont = new BitmapFont(blockedFontFile);

        cam.setToOrtho(false, CuteCatSplat.WIDTH / 2.5f, CuteCatSplat.HEIGHT / 2.5f);
        bush = new Texture("images/Pixel_Bush.png");

        imgTextureBushRegionRight = new TextureRegion(bush);
        imgTextureBushRegionLeft = new TextureRegion(bush);
        imgTextureBushRegionLeft.flip(true,false);

        rightBushPos1 = new Vector2(cam.viewportWidth - bush.getWidth(),
                cam.position.y - cam.viewportHeight / 2);
        rightBushPos2 = new Vector2(cam.viewportWidth - bush.getWidth(),
                cam.position.y - cam.viewportHeight / 2 + bush.getHeight());
        leftBushPos1 = new Vector2(0, cam.position.y - cam.viewportHeight / 2);
        leftBushPos2 = new Vector2(0, cam.position.y - cam.viewportHeight / 2 + bush.getHeight());

        walls = new Array<Wall>();

        for(int i = 1; i <= WALL_COUNT; i++)
            walls.add(new Wall(i * (WALL_SPACING + Wall.WALL_WIDTH)));


        // Create grass background texture region
        texture_grass = new Texture(Gdx.files.internal("images/Pixel_Block_Solid_Grass_Large.png"));
        texture_grass.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        imgTextureGrassRegion = new TextureRegion(texture_grass);
        imgTextureGrassRegion.setRegion(0, 0, texture_grass.getWidth(),
										texture_grass.getHeight());
		
        // Set up scrolling parallax background
        parallax_background = new ParallaxBackground(new ParallaxLayer[]{
                new ParallaxLayer(imgTextureGrassRegion, new Vector2(0, 20), new Vector2(0, 0)),
        }, Assets.width, Assets.height, new Vector2(0, 30));
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.isTouched()) {
            touch.set(Gdx.input.getX(), 0, 0);
            cam.unproject(touch);
            cat.move((int)(touch.x - Assets.spriteCat1.getWidth()/2));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        updateBush();
        cat.update(dt);
        cam.position.y = cat.getPosition().y + 80;

        for(Wall wall : walls){
            if(cam.position.y - (cam.viewportHeight / 2) > wall.getPosLeftWall().y + wall.getLeftWall().getRegionHeight())
                wall.reposition(wall.getPosLeftWall().y + ((Wall.WALL_WIDTH + WALL_SPACING) * WALL_COUNT));

            if(wall.collides(cat.getBounds()))
                    gsm.set(new MenuState(gsm));

            if(wall.pointGained(cat.getBounds()))
                points++;

        }

        if(cat.getPosition().x <= bush.getWidth() - 10 ||
                cat.getPosition().x >= (cam.viewportWidth - bush.getWidth() - 5))
            gsm.set(new MenuState(gsm));

        cam.update();

        // Render parallax background
        parallax_background.render(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
            sb.draw(cat.getTexture(), cat.getPosition().x, cat.getPosition().y);
            for(Wall wall : walls) {
                sb.draw(wall.getLeftWall(), wall.getPosLeftWall().x, wall.getPosLeftWall().y);
                sb.draw(wall.getRightWall(), wall.getPosRightWall().x, wall.getPosRightWall().y);
            }
            sb.draw(imgTextureBushRegionRight, rightBushPos1.x, rightBushPos1.y);
            sb.draw(imgTextureBushRegionRight, rightBushPos2.x, rightBushPos2.y);
            sb.draw(imgTextureBushRegionLeft, leftBushPos1.x, leftBushPos1.y);
            sb.draw(imgTextureBushRegionLeft, leftBushPos2.x, leftBushPos2.y);
        sb.end();

        //blockedFont.draw(sb, "test", cam.viewportHeight - 10, cam.viewportWidth / 2);

    }

    @Override
    public void dispose() {
        cat.dispose();
        for (Wall wall : walls)
            wall.dispose();
        bush.dispose();
		texture_grass.dispose();
    }

    private void updateBush(){
        if((cam.position.y - cam.viewportHeight / 2) > (rightBushPos1.y + bush.getHeight()))
            rightBushPos1.add(0, bush.getHeight() * 2);

        if((cam.position.y - cam.viewportHeight / 2) > (rightBushPos2.y + bush.getHeight()))
            rightBushPos2.add(0, bush.getHeight() * 2);

        if((cam.position.y - cam.viewportHeight / 2) > (leftBushPos1.y + bush.getHeight()))
            leftBushPos1.add(0, bush.getHeight() * 2);

        if((cam.position.y - cam.viewportHeight / 2) > (leftBushPos2.y + bush.getHeight()))
            leftBushPos2.add(0, bush.getHeight() * 2);
    }
}
