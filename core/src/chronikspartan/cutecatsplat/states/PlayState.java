package chronikspartan.cutecatsplat.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
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
import chronikspartan.cutecatsplat.services.PlayServices;
import chronikspartan.cutecatsplat.sprites.Cat;
import chronikspartan.cutecatsplat.sprites.Wall;

/**
 * Created by cube on 1/20/2017.
 */

class PlayState extends State {
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
	private boolean showStars = false;
	private Array<Wall> walls;
	
	private int stateToLoad = 0;
	private int starsCounter = 0;
	private int continueCounter= 0;
	private int starSelect = 0;
	private int catNipCounter = 0;
	private int catNipCollected = 0;
	private int catNipSetCounter = 1000;

	private Texture bush;
	private Texture swipe;
	private Texture splatScreenTexture;
	private Texture starsTexture;
	private TextureRegion imgTextureBushRegionLeft, imgTextureBushRegionRight, imgTextureGrassRegion, imgTextureGrassRegion2;
    private Vector2 rightBushPos1, rightBushPos2, leftBushPos1, leftBushPos2, backgroundPos, backgroundPos2;
	private InputProcessor backProcessor;
	
	private FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
	private static BitmapFont font;

	private Stage stage;
	private Sound splat, screech, miaow2, fenceSmash;
	private Music catNipTune = (Music) assets.manager.get(Assets.catNipTune);

	private MyGestureDetector gestureDetector;
	private boolean touchLeftWall = false;
	private boolean touchRightWall = false;
	
	private int catType;
	
	// Public members
	private int points = 0;
	private boolean catNipActivated = false;

	PlayState(GameStateManager gsm, Assets assets, final AdsController adsController, PlayServices playServices, int catType, int points){
        super(gsm, assets, adsController, playServices);
		
		this.catType = catType;
		this.points = points;

		Texture catSpriteMap = (Texture) assets.manager.get(Assets.catSpriteMap);
        cat = new Cat(CuteCatSplat.WIDTH/2 - catSpriteMap.getWidth()/6, 970, assets, catType);
		
		// Set camera size
        cam.setToOrtho(false, CuteCatSplat.WIDTH, CuteCatSplat.HEIGHT);
		
		// Load textures
		bush = (Texture) assets.manager.get(Assets.bush);
		swipe = (Texture) assets.manager.get(Assets.swipe);

		// Load sound
		miaow2 = (Sound) assets.manager.get(Assets.miaow2);
		splat = (Sound) assets.manager.get(Assets.splat);
		screech = (Sound) assets.manager.get(Assets.screech);
		fenceSmash = (Sound) assets.manager.get(Assets.fenceSmash);
		
		// Create font
		parameter.size = 150;
		font = assets.generator.generateFont(parameter);
		font.setColor(0, 0.5f, 0.5f, 1);

		Random rand = new Random();
		
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
		Texture texture_grass = new Texture(Gdx.files.internal("images/Grass.png"));
        texture_grass.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        imgTextureGrassRegion = new TextureRegion(texture_grass);
        imgTextureGrassRegion.setRegion(0, 0, texture_grass.getWidth()* 10,
										texture_grass.getHeight()* 10);
		imgTextureGrassRegion2 = new TextureRegion(imgTextureGrassRegion);


		backgroundPos = new Vector2(0, cam.position.y - cam.viewportHeight / 2);
		backgroundPos2 = new Vector2(0, cam.position.y - (cam.viewportHeight / 2) + imgTextureGrassRegion2.getRegionHeight());

		gestureDetector = new MyGestureDetector(new MyGestureDetector.DirectionListener() {
			@Override
			public void onRight(float deltaX) {
				if (!catDead && !touchRightWall)
					cat.moveRight(deltaX);
			}

			@Override
			public void onLeft(float deltaX) {
				if (!catDead && !touchLeftWall)
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

		// Initialise stage
		// Create stage and set for input processor
		stage = new Stage(new StretchViewport(CuteCatSplat.WIDTH/2.5f, CuteCatSplat.HEIGHT/2.5f));

		backProcessor = new InputAdapter() {
			@Override
			public boolean keyDown(int keycode) {

				if ((keycode == Keys.ESCAPE) || (keycode == Keys.BACK) ) {
					catNipTune.stop();
					stateToLoad = MENUSTATE;
				}
				return false;
			}
		};

		createGameOverScreen();
		
		InputMultiplexer multiplexer = new InputMultiplexer(gestureDetector, stage, backProcessor);
        Gdx.input.setInputProcessor(multiplexer);
		Gdx.input.setCatchBackKey(true);
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.isTouched()) {
			// Start game if paused
			if(!gameStarted)
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
		if(adsController.getReward())
		{
			continueCounter++;
			if(continueCounter>10) {
				continueCounter = 0;
				adsController.setReward(false);
				gsm.set(new PlayState(gsm, assets, adsController, playServices, catType, points));
			}
		}

		if(points == 1)
			playServices.unlockAchievementFirstGate();

		if(points == 5)
			playServices.unlockAchievementFifthGate();

		if(points == 9)
			playServices.unlockAchievementNinthGate();

		if(catNipCollected == 1)
			playServices.unlockAchievementFirstCatNip();

		if(catNipCollected == 3)
			playServices.unlockAchievementThreeCatNips();

		if(starsCounter<10)
		{
			starsCounter++;
		}
		else{
			starSelect++;
			if(starSelect > 2)
				starSelect = 0;
			switch(starSelect)
			{
				case 0:
					starsTexture = (Texture) assets.manager.get(Assets.textureStarsScreen1);
					break;
				case 1:
					starsTexture = (Texture) assets.manager.get(Assets.textureStarsScreen2);
					break;
				case 2:
					starsTexture = (Texture) assets.manager.get(Assets.textureStarsScreen3);
					break;
				default:
					starsTexture = (Texture) assets.manager.get(Assets.textureStarsScreen3);
					break;
			}
			starsCounter = 0;
		}
		// Load PlayState if button selected
		if(stateToLoad == PLAYSTATE) {
			adsController.showInterstitialAd(new Runnable() {
 					@Override

					public void run() {
					return;
				}
			});
			gsm.set(new PlayState(gsm, assets, adsController, playServices, catType, 0));
		}

		// Load MenuState if button selected
		if(stateToLoad == MENUSTATE) {
			gsm.set(new MenuState(gsm, assets, adsController, playServices));
		}

		if (catDead)
		{
			cat.splat(dt);
		}
		else 
		{
			touchLeftWall = touchRightWall = false;
			updateBush();
			catNipSetCounter++;
			
			// Update cat and camera position
			cat.update(dt);
			cam.position.y = cat.getPosition().y + CAT_LOCATION;

			cam.update();

			handleInput();
		
			if(gameStarted)
			{
        		for(Wall wall : walls){
					// Reposition walls if they move off screen
            		if(cam.position.y - (cam.viewportHeight / 2) > wall.getPosLeftWall().y + wall.wallHeight){
                		if(wall.reposition(wall.getPosLeftWall().y + ((Wall.WALL_WIDTH + WALL_SPACING) * WALL_COUNT), catNipSetCounter))
						{
							catNipSetCounter = 0;
						}
					}

					if(wall.collidesWithLeft(cat.getSideBounds()))
						touchLeftWall = true;
					if(wall.collidesWithRight(cat.getSideBounds()))
						touchRightWall = true;

					// End game of collision occurs
            		if(!catNipActivated && (wall.collidesWithLeft(cat.getBounds()) || wall.collidesWithRight(cat.getBounds()))){
						catDead = true;
						screech.play(0.6f);
						splat.play(0.8f);
						Gdx.input.vibrate(300);
                		setScores();
					}
					else if (wall.collidesWithLeft(cat.getBounds()))
					{
						wall.explode(dt, "LEFT");
						fenceSmash.play(0.1f);
					}
					else if (wall.collidesWithRight(cat.getBounds()))
					{
						wall.explode(dt, "RIGHT");
						fenceSmash.play(0.1f);
					}

					// Add points if fence passed
            		if(wall.pointGained(cat.getBounds()))
                		points ++;
						
					if(points > 10)
						Assets.unlockTilly();
						
					if(catNipCollected == 5)
						Assets.unlockTrampy();

					// Activate cat nip and stars overlay
					if(wall.catNipGained(cat.getBounds())){
						catNipActivated = showStars = true;
						catNipCollected++;
						catNipTune.play();
					}
        		}
				
				if(catNipActivated)
					catNipCounter++;
				
				switch(catNipCounter){
					case 400:
						showStars = false;
						catNipTune.stop();
						break;
					case 500:
						catNipActivated = false;
						catNipCounter = 0;
						break;
					default:
						break;
				}

				// End game if cat hits bushes
        		if(!catNipActivated && (cat.getPosition().x <= bush.getWidth() - 50 ||
                		cat.getPosition().x >= (cam.viewportWidth - bush.getWidth() - 25))){
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
            if(gameStarted)
			{
				for(Wall wall : walls) {
                	sb.draw(wall.getLeftWall(), wall.getPosLeftWall().x, wall.getPosLeftWall().y);
                	sb.draw(wall.getRightWall(), wall.getPosRightWall().x, wall.getPosRightWall().y);
					sb.draw(wall.getCatNip(), wall.getPosCatNip().x, wall.getPosCatNip().y);
            	}
			}
			else
			{
				sb.draw(swipe, (cam.viewportWidth / 2) - (swipe.getWidth() / 2), cat.getPosition().y - 300);
			}
			sb.draw(cat.getTexture(), cat.getPosition().x, cat.getPosition().y);
            sb.draw(imgTextureBushRegionRight, rightBushPos1.x, rightBushPos1.y);
            sb.draw(imgTextureBushRegionRight, rightBushPos2.x, rightBushPos2.y);
            sb.draw(imgTextureBushRegionLeft, leftBushPos1.x, leftBushPos1.y);
            sb.draw(imgTextureBushRegionLeft, leftBushPos2.x, leftBushPos2.y);
			
			font.draw(sb, String.valueOf(points), (cam.viewportWidth / 2) - parameter.size/4, cat.getPosition().y + 1000);
		
		if(catDead){
			catNipTune.stop();
			sb.draw(splatScreenTexture, cat.getPosition().x - 500, cat.getPosition().y - 500);
			sb.end();
			stage.act();
			stage.draw();
		}
		else 
		if(showStars){
			sb.draw(starsTexture, CuteCatSplat.WIDTH/2 - starsTexture.getWidth()/2, cat.getPosition().y - 500);
			sb.end();
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

    private void createGameOverScreen(){
		Texture watchAd1 = (Texture) assets.manager.get(Assets.play1);
		Texture watchAd2 = (Texture) assets.manager.get(Assets.play2);
		Texture restart1 = (Texture) assets.manager.get(Assets.restart1);
		Texture restart2 = (Texture) assets.manager.get(Assets.restart2);
		Texture back1 = (Texture) assets.manager.get(Assets.back1);
		Texture back2 = (Texture) assets.manager.get(Assets.back2);

		FreeTypeFontGenerator.FreeTypeFontParameter wafParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		// Create font
		wafParameter.size = 25;
		BitmapFont watchAdfont = assets.generator.generateFont(wafParameter);
		watchAdfont.setColor(0, 0.5f, 0.5f, 1);

		Label watchAdLabel = new Label("Press button above to\nwatch video and continue\nscore with new life",
				new Label.LabelStyle(watchAdfont, Color.BLACK));
		watchAdLabel.setAlignment(Align.center);

		// Create restart button style and add listener
		CreateButton buttonCreator = new CreateButton();

		Button watchAdButton = buttonCreator.NewButton(watchAd1, watchAd2);
		watchAdButton.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				Gdx.input.vibrate(5);
				return true;
			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button){
				adsController.showRewardAd();
			}
		});

		Button restartButton = buttonCreator.NewButton(restart1, restart2);
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
		Button returnButton = buttonCreator.NewButton(back1, back2);
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

		Random randContinue = new Random();

		// Create end table to hold actors for stage
		Table endTable = new Table();
		endTable.row();
		endTable.add().height(restartButton.getHeight());
		endTable.row();
		if(randContinue.nextInt(3) == 1) {
			endTable.add(watchAdButton).height(restartButton.getHeight() / 2).width(restartButton.getWidth() / 2);
			endTable.row();
			endTable.add(watchAdLabel).height(restartButton.getHeight() / 2).width(restartButton.getWidth() / 2);
		}
		else {
			endTable.add().height(restartButton.getHeight() / 2);
			endTable.row();
			endTable.add().height(restartButton.getHeight() / 2);
		}
		endTable.row();
		endTable.add(restartButton).height(restartButton.getHeight()/2).width(restartButton.getWidth()/2);
		endTable.row();
		endTable.add().height(restartButton.getHeight()/2);
		endTable.row();
		endTable.add(returnButton).height(returnButton.getHeight()/2).width(returnButton.getWidth()/2);
		endTable.setFillParent(true);

		;
		// Create stage and set for input processor
		stage = new Stage(new StretchViewport(CuteCatSplat.WIDTH/2.5f, CuteCatSplat.HEIGHT/2.5f));
		stage.addActor(endTable);
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
			Assets.setHighScore1(points);
		}
	}
}
