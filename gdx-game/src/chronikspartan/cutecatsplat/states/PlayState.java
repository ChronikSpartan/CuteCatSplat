package chronikspartan.cutecatsplat.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.Random;

import chronikspartan.cutecatsplat.data.Assets;
import chronikspartan.cutecatsplat.CuteCatSplat;
import chronikspartan.cutecatsplat.ParallaxBackground;
import chronikspartan.cutecatsplat.ParallaxLayer;
import chronikspartan.cutecatsplat.CreateButton;
import chronikspartan.cutecatsplat.sprites.Cat;
import chronikspartan.cutecatsplat.sprites.Wall;
import chronikspartan.cutecatsplat.data.*;

/**
 * Created by cube on 1/20/2017.
 */

public class PlayState extends State {
	// Private constants
	private static final int START_DISTANCE = 1500;
	private static final int CAT_LOCATION = 400;
    private static final int WALL_SPACING = 450;
    private static final int WALL_COUNT = 4;
	private static final int PLAYSTATE = 1;
	private static final int MENUSTATE = 3;

	// Private members
    private Cat cat;
	private boolean catDead = false;
	private boolean gameStarted = false;
	private Array<Wall> walls;
	
	private int stateToLoad = 0;
	
	private float viewportScaling = 2.5f;
    private ParallaxBackground parallax_background;
    private Vector3 touch;
    private Texture bush, texture_grass, splatScreenTexture, catTexture, restart1, restart2, back1, back2;
    private TextureRegion imgTextureBushRegionLeft, imgTextureBushRegionRight, imgTextureGrassRegion;
    private Vector2 rightBushPos1, rightBushPos2, leftBushPos1, leftBushPos2;

	private CreateButton buttonCreator = new CreateButton();
	private Button restartButton, returnButton;
    private Stage stage;
	
	private Random rand;
	
	// Public members
    public int points = 0;
	
    protected PlayState(GameStateManager gsm, Assets assets){
        super(gsm, assets);
        cat = new Cat(540, 970);
        touch = new Vector3();

		// Set camera size
        cam.setToOrtho(false, CuteCatSplat.WIDTH, CuteCatSplat.HEIGHT);
		
		// Load bush texture
		bush = new Texture("images/Bush.png");
		
		rand = new Random();
		
		switch(rand.nextInt(5))
		{
			case 0: splatScreenTexture = assets.manager.get(Assets.textureSplatScreen1);
					break;
			case 1: splatScreenTexture = assets.manager.get(Assets.textureSplatScreen2);
					break;
			case 2: splatScreenTexture = assets.manager.get(Assets.textureSplatScreen3);
					break;
			case 3: splatScreenTexture = assets.manager.get(Assets.textureSplatScreen4);
					break;
			case 4: splatScreenTexture = assets.manager.get(Assets.textureSplatScreen5);
					break;
			default: splatScreenTexture = assets.manager.get(Assets.textureSplatScreen1);
					break;
		}
		
		// Create splat screen
	

		// Create texture regions for bushes and flip for both sides
        imgTextureBushRegionRight = new TextureRegion(bush);
        imgTextureBushRegionLeft = new TextureRegion(bush);
        imgTextureBushRegionLeft.flip(true,false);

		// Set bush positions
        rightBushPos1 = new Vector2(cam.viewportWidth - bush.getWidth(),
                cam.position.y - cam.viewportHeight / 2);
        rightBushPos2 = new Vector2(cam.viewportWidth - bush.getWidth(),
                cam.position.y - cam.viewportHeight / 2 + bush.getHeight());
        leftBushPos1 = new Vector2(0, cam.position.y - cam.viewportHeight / 2);
        leftBushPos2 = new Vector2(0, cam.position.y - cam.viewportHeight / 2 + bush.getHeight());

        // Create grass background texture region
        texture_grass = new Texture(Gdx.files.internal("images/Grass-orig.png"));
        texture_grass.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        imgTextureGrassRegion = new TextureRegion(texture_grass);
        imgTextureGrassRegion.setRegion(0, 0, texture_grass.getWidth(),
										texture_grass.getHeight());
		
        // Set up scrolling parallax background
        parallax_background = new ParallaxBackground(new ParallaxLayer[]{
                new ParallaxLayer(imgTextureGrassRegion, new Vector2(0, 20), new Vector2(0, 0)),
        }, Assets.width, Assets.height, new Vector2(0, 40));
		
		restart1 = assets.manager.get(Assets.restart1);
		restart2 = assets.manager.get(Assets.restart2);
		back1 = assets.manager.get(Assets.back1);
		back2 = assets.manager.get(Assets.back2);
		
		// Create restart button style and add listener
	    restartButton = buttonCreator.NewButton(restart1, restart2);
      	restartButton.addListener(new InputListener(){
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
					return true;
				}
				public void touchUp(InputEvent event, float x, float y, int pointer, int button){
					// Set PlayState to load
					stateToLoad = PLAYSTATE;
				}
			});
			
		// Create play button style and add listener
	    returnButton = buttonCreator.NewButton(back1, back2);
      	returnButton.addListener(new InputListener(){
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
					return true;
				}
				public void touchUp(InputEvent event, float x, float y, int pointer, int button){
					// Set PlayState to load
					stateToLoad = MENUSTATE;
				}
			});
			
		InputProcessor backProcessor = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {

                if ((keycode == Keys.ESCAPE) || (keycode == Keys.BACK) )
					stateToLoad = MENUSTATE;
                return false;
            }
        };
			
		// Create table to hold actors for stage
        Table menuTable = new Table();
		menuTable.add().height(restartButton.getHeight());
        menuTable.row();
        menuTable.row();
        menuTable.add().height(restartButton.getHeight());
        menuTable.row();
        menuTable.add(restartButton).height(restartButton.getHeight()/2).width(restartButton.getWidth()/2);
        menuTable.row();
        menuTable.add().height(restartButton.getHeight()/2);
        menuTable.row();
        menuTable.add(returnButton).height(returnButton.getHeight()/2).width(returnButton.getWidth()/2);
        menuTable.setFillParent(true);

		// Create stage and set for input processor
        stage = new Stage(new StretchViewport(CuteCatSplat.WIDTH/2.5f, CuteCatSplat.HEIGHT/2.5f));
		stage.addActor(menuTable);
		
		InputMultiplexer multiplexer = new InputMultiplexer(stage, backProcessor);
        Gdx.input.setInputProcessor(multiplexer);
		Gdx.input.setCatchBackKey(true);
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.isTouched()) {
			// Start game if paused
			if(gameStarted == false)
			{
				// Create and load array to hold the walls
				walls = new Array<Wall>();
				walls.add(new Wall(START_DISTANCE + cat.getPosition().y));
				for(int i = 1; i < WALL_COUNT; i++)
					walls.add(new Wall(i*(WALL_SPACING + Wall.WALL_WIDTH) + cat.getPosition().y + START_DISTANCE));
				
				gameStarted = true;
			}
			// Get x movement of touch and set to touch vector
            touch.set(Gdx.input.getX(), 0, 0);
            cam.unproject(touch);
			catTexture = assets.manager.get(Assets.textureCat);
			// Move cat to where finger is (minus half cat width to ensure its centered)
            cat.move((int)(touch.x - catTexture.getWidth()/2));
			
			//cat.move2();
        }
    }

    @Override
    public void update(float dt) {
		// Load PlayState if button selected
		if(stateToLoad == PLAYSTATE)
			gsm.set(new PlayState(gsm, assets));

		// Load MenuState if button selected
		if(stateToLoad == MENUSTATE)
			gsm.set(new MenuState(gsm, assets));

		if (catDead)
		{
			cat.splat(dt);
			parallax_background.render(0f);
		}
		else 
		{
			updateBush();
			
			// Update cat and camera position
			cat.update(dt);
			cam.position.y = cat.getPosition().y + CAT_LOCATION;

			cam.update();

			// Render parallax background
			parallax_background.render(dt);
			
			handleInput();
		
			if(gameStarted)
			{
        		for(Wall wall : walls){
					// Reposition walls if they move off screen
            		if(cam.position.y - (cam.viewportHeight / 2) > wall.getPosLeftWall().y + wall.getLeftWall().getRegionHeight())
                		wall.reposition(wall.getPosLeftWall().y + ((Wall.WALL_WIDTH + WALL_SPACING) * WALL_COUNT));

					// End game of collision occurs
            		if(wall.collides(cat.getBounds())){
						catDead = true;
                		setScores();
					}

					// Add points if fence passed
            		if(wall.pointGained(cat.getBounds()))
                		points ++;
        		}

				// End game if cat hits bushes
        		if(cat.getPosition().x <= bush.getWidth() - 50 ||
                		cat.getPosition().x >= (cam.viewportWidth - bush.getWidth() - 25)){
            		catDead = true;
					setScores();
				}
			}
		}
    }

    @Override
    public void render(SpriteBatch sb) {
		// Draw everything to sprite batch
		sb.begin();
			sb.setProjectionMatrix(cam.combined);
            sb.draw(cat.getTexture(), cat.getPosition().x, cat.getPosition().y);
            if(gameStarted)
			{
				for(Wall wall : walls) {
                sb.draw(wall.getLeftWall(), wall.getPosLeftWall().x, wall.getPosLeftWall().y);
                sb.draw(wall.getRightWall(), wall.getPosRightWall().x, wall.getPosRightWall().y);
            	}
			}
            sb.draw(imgTextureBushRegionRight, rightBushPos1.x, rightBushPos1.y);
            sb.draw(imgTextureBushRegionRight, rightBushPos2.x, rightBushPos2.y);
            sb.draw(imgTextureBushRegionLeft, leftBushPos1.x, leftBushPos1.y);
            sb.draw(imgTextureBushRegionLeft, leftBushPos2.x, leftBushPos2.y);
			
			assets.font.draw(sb, String.valueOf(points), (cam.viewportWidth / 2), cat.getPosition().y + 1000);
		
		if(!gameStarted)
		{
			
		}
		
		if(catDead){
			sb.draw(splatScreenTexture, cat.getPosition().x - 500, cat.getPosition().y - 500);
			sb.end();
			stage.act();
			stage.draw();
		}
		else
			sb.end();
    }

    @Override
    public void dispose() {
        cat.dispose();
        for (Wall wall : walls)
            wall.dispose();
        bush.dispose();
		assets.dispose();
    }

    private void updateBush(){
		// Scroll and reposition bushes
        if((cam.position.y - cam.viewportHeight / 2) > (rightBushPos1.y + bush.getHeight()))
            rightBushPos1.add(0, bush.getHeight() * 2);

        if((cam.position.y - cam.viewportHeight / 2) > (rightBushPos2.y + bush.getHeight()))
            rightBushPos2.add(0, bush.getHeight() * 2);

        if((cam.position.y - cam.viewportHeight / 2) > (leftBushPos1.y + bush.getHeight()))
            leftBushPos1.add(0, bush.getHeight() * 2);

        if((cam.position.y - cam.viewportHeight / 2) > (leftBushPos2.y + bush.getHeight()))
            leftBushPos2.add(0, bush.getHeight() * 2);
    }
	
	private void setScores(){
		if (points > Assets.getHighScore1()){
			Assets.setHighScore3(Assets.getHighScore2());
			Assets.setHighScore2(Assets.getHighScore1());
			Assets.setHighScore1(points);
		}
		else if(points > Assets.getHighScore2()){
			Assets.setHighScore3(Assets.getHighScore2());
			Assets.setHighScore2(points);
		}
		else if(points > Assets.getHighScore3())
			Assets.setHighScore3(points);
	}
}