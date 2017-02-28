package chronikspartan.cutecatsplat.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import chronikspartan.cutecatsplat.AdsController;
import chronikspartan.cutecatsplat.data.Assets;
import chronikspartan.cutecatsplat.CuteCatSplat;
import chronikspartan.cutecatsplat.CreateButton;
import chronikspartan.cutecatsplat.MyGestureDetector;
import chronikspartan.cutecatsplat.sprites.Cat;
import chronikspartan.cutecatsplat.sprites.Wall;

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

    private Vector3 touch;
    private Texture bush, texture_grass, swipe, splatScreenTexture, catSpriteMap, restart1, restart2, back1, back2;
    private TextureRegion imgTextureBushRegionLeft, imgTextureBushRegionRight, imgTextureGrassRegion, imgTextureGrassRegion2;
    private Vector2 rightBushPos1, rightBushPos2, leftBushPos1, leftBushPos2, backgroundPos, backgroundPos2;
	
	private FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
	private static BitmapFont font;

	private CreateButton buttonCreator = new CreateButton();
	private Button restartButton, returnButton;
    private Stage stage;
	private MyGestureDetector gestureDetector;
	private Sound splat, screech, miaow2;
	
	private Random rand;

	// Public members
    public int points = 0;
	
    protected PlayState(GameStateManager gsm, Assets assets, AdsController adsController){
        super(gsm, assets, adsController);
		catSpriteMap = (Texture) assets.manager.get(Assets.catSpriteMap);
        cat = new Cat(CuteCatSplat.WIDTH/2 - catSpriteMap.getWidth()/6, 970, assets);
        touch = new Vector3();
		
		// Set camera size
        cam.setToOrtho(false, CuteCatSplat.WIDTH, CuteCatSplat.HEIGHT);
		
		// Load textures
		bush = (Texture) assets.manager.get(Assets.bush);
		swipe = (Texture) assets.manager.get(Assets.swipe);
		
		// Load sound
		miaow2 = (Sound) assets.manager.get(Assets.miaow2);
		splat = (Sound) assets.manager.get(Assets.splat);
		screech = (Sound) assets.manager.get(Assets.screech);
		
		// Create font
		parameter.size = 150;
		font = assets.generator.generateFont(parameter);
		font.setColor(0, 0.5f, 0.5f, 1);
		
		rand = new Random();
		
		switch(rand.nextInt(5))
		{
			case 0: splatScreenTexture = (Texture) assets.manager.get(Assets.textureSplatScreen1);
					break;
			case 1: splatScreenTexture = (Texture) assets.manager.get(Assets.textureSplatScreen2);
					break;
			case 2: splatScreenTexture = (Texture) assets.manager.get(Assets.textureSplatScreen3);
					break;
			case 3: splatScreenTexture = (Texture) assets.manager.get(Assets.textureSplatScreen4);
					break;
			case 4: splatScreenTexture = (Texture) assets.manager.get(Assets.textureSplatScreen5);
					break;
			default: splatScreenTexture = (Texture) assets.manager.get(Assets.textureSplatScreen1);
					break;
		}
		
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
        texture_grass = new Texture(Gdx.files.internal("images/Grass.png"));
        texture_grass.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        imgTextureGrassRegion = new TextureRegion(texture_grass);
        imgTextureGrassRegion.setRegion(0, 0, texture_grass.getWidth()* 10,
										texture_grass.getHeight()* 10);
		imgTextureGrassRegion2 = new TextureRegion(imgTextureGrassRegion);


		backgroundPos = new Vector2(0, cam.position.y - cam.viewportHeight / 2);
		backgroundPos2 = new Vector2(0, cam.position.y - (cam.viewportHeight / 2) + imgTextureGrassRegion2.getRegionHeight());
		
		restart1 = (Texture) assets.manager.get(Assets.restart1);
		restart2 = (Texture) assets.manager.get(Assets.restart2);
		back1 = (Texture) assets.manager.get(Assets.back1);
		back2 = (Texture) assets.manager.get(Assets.back2);
		
		// Create restart button style and add listener
	    restartButton = buttonCreator.NewButton(restart1, restart2);
      	restartButton.addListener(new InputListener(){
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
					Gdx.input.vibrate(5);
					return true;
				}
				public void touchUp(InputEvent event, float x, float y, int pointer, int button){
					// Set PlayState to load
					miaow2.play();
					stateToLoad = PLAYSTATE;
				}
			});
			
		// Create play button style and add listener
	    returnButton = buttonCreator.NewButton(back1, back2);
      	returnButton.addListener(new InputListener(){
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
					if(catDead)
					{
						Gdx.input.vibrate(5);
						return true;
					}
					return false;
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
			
		// Create end table to hold actors for stage
        Table endTable = new Table();
		endTable.add().height(restartButton.getHeight());
        endTable.row();
        endTable.row();
        endTable.add().height(restartButton.getHeight());
        endTable.row();
        endTable.add(restartButton).height(restartButton.getHeight()/2).width(restartButton.getWidth()/2);
        endTable.row();
        endTable.add().height(restartButton.getHeight()/2);
        endTable.row();
        endTable.add(returnButton).height(returnButton.getHeight()/2).width(returnButton.getWidth()/2);
        endTable.setFillParent(true);
		

		// Create stage and set for input processor
        stage = new Stage(new StretchViewport(CuteCatSplat.WIDTH/2.5f, CuteCatSplat.HEIGHT/2.5f));
		stage.addActor(endTable);
		
		gestureDetector = 
			new MyGestureDetector(new MyGestureDetector.DirectionListener() 
			{
				@Override
				public void onRight(float deltaX) {
					if(!catDead)
						cat.moveRight(deltaX);
				}

				@Override
				public void onLeft(float deltaX) {
					if(!catDead)
						cat.moveLeft(deltaX);
				}

				@Override
				public void onDown(float deltaY) {
					// Unused
				}
				
				@Override
				public void onUp(float deltaY) {
					// Unused
				}
				
			});
		
		InputMultiplexer multiplexer = new InputMultiplexer(stage, backProcessor, gestureDetector);
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
				walls.add(new Wall(START_DISTANCE + cat.getPosition().y, assets));
				for(int i = 1; i < WALL_COUNT; i++)
					walls.add(new Wall(i*(WALL_SPACING + Wall.WALL_WIDTH) + cat.getPosition().y + START_DISTANCE, assets));
				
				gameStarted = true;
			}
        }
    }

    @Override
    public void update(float dt) {
		// Load PlayState if button selected
		if(stateToLoad == PLAYSTATE) {
			adsController.showInterstitialAd(new Runnable() {
					@Override
					public void run() {
						return;
					}
				});
			gsm.set(new PlayState(gsm, assets, adsController));
		}

		// Load MenuState if button selected
		if(stateToLoad == MENUSTATE) {
			adsController.showInterstitialAd(new Runnable() {
				@Override
				public void run() {
					return;
				}
			});
			gsm.set(new MenuState(gsm, assets, adsController));
		}

		if (catDead)
		{
			cat.splat(dt);
		}
		else 
		{
			updateBush();
			
			// Update cat and camera position
			cat.update(dt);
			cam.position.y = cat.getPosition().y + CAT_LOCATION;

			cam.update();

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
						screech.play(0.6f);
						splat.play(0.8f);
						Gdx.input.vibrate(300);
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
					screech.play(0.6f);
					splat.play(0.8f);
					Gdx.input.vibrate(300);
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
			sb.draw(imgTextureGrassRegion, backgroundPos.x, backgroundPos.y);
			sb.draw(imgTextureGrassRegion2, backgroundPos2.x, backgroundPos2.y);
			sb.draw(cat.getTexture(), cat.getPosition().x, cat.getPosition().y);
            if(gameStarted)
			{
				for(Wall wall : walls) {
                sb.draw(wall.getLeftWall(), wall.getPosLeftWall().x, wall.getPosLeftWall().y);
                sb.draw(wall.getRightWall(), wall.getPosRightWall().x, wall.getPosRightWall().y);
            	}
			}
			else
			{
				sb.draw(swipe, (cam.viewportWidth / 2) - (swipe.getWidth() / 2), cat.getPosition().y - 300);
			}
            sb.draw(imgTextureBushRegionRight, rightBushPos1.x, rightBushPos1.y);
            sb.draw(imgTextureBushRegionRight, rightBushPos2.x, rightBushPos2.y);
            sb.draw(imgTextureBushRegionLeft, leftBushPos1.x, leftBushPos1.y);
            sb.draw(imgTextureBushRegionLeft, leftBushPos2.x, leftBushPos2.y);
			
			font.draw(sb, String.valueOf(points), (cam.viewportWidth / 2) - parameter.size/4, cat.getPosition().y + 1000);
		
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

		if((cam.position.y - cam.viewportHeight / 2) > (backgroundPos.y + imgTextureGrassRegion.getRegionHeight()))
			backgroundPos.add(0, imgTextureGrassRegion.getRegionHeight() * 2);

		if((cam.position.y - cam.viewportHeight / 2) > (backgroundPos2.y + imgTextureGrassRegion2.getRegionHeight()))
			backgroundPos2.add(0, imgTextureGrassRegion2.getRegionHeight() * 2);

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
