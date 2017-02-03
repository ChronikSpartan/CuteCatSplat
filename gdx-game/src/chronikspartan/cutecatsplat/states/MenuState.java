package chronikspartan.cutecatsplat.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import chronikspartan.cutecatsplat.CuteCatSplat;
import chronikspartan.cutecatsplat.CreateButton;
import com.badlogic.gdx.scenes.scene2d.*;
import chronikspartan.cutecatsplat.*;
import com.badlogic.gdx.utils.*;
import chronikspartan.cutecatsplat.data.*;

/**
 * Created by cube on 1/20/2017.
 */

public class MenuState extends State {
	private static final int PLAYSTATE = 1;
	private static final int RANKINGSSTATE = 2;
	
    private Texture background, logo, splashScreen;
	private TextureRegion imgTextureBackgroundRegion;
	private Image logoImage;
    private Button playButton, rankingsButton;
    private Stage stage;
	private CreateButton buttonCreator = new CreateButton();
	
	private int stateToLoad = 0;
	
    public MenuState(GameStateManager gsm, Assets assets){
        super(gsm, assets);
		splashScreen = new Texture("images/Splash_Screen.png");
		
		// Set up camera
		cam.setToOrtho(false, CuteCatSplat.WIDTH/4, CuteCatSplat.HEIGHT/4);
		
        background = new Texture("images/Block_Solid_Grass.png");

        // Create grass background texture region
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        imgTextureBackgroundRegion = new TextureRegion(background);
        imgTextureBackgroundRegion.setRegion(0, 0, background.getWidth()*20,
										background.getHeight()*20);
		
        logo = new Texture("images/Buttons_and_Logo/Cute_Cat_Logo.png");
		logoImage = new Image(logo);

		// Create play button style and add listener
	    playButton = buttonCreator.NewButton(new Texture("images/Buttons_and_Logo/Play_1.png"), 
			new Texture("images/Buttons_and_Logo/Play_2.png"));
      	playButton.addListener(new InputListener(){
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				return true;
			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button){
				// Set PlayState to load
				stateToLoad = PLAYSTATE;
			}
		});
			

		// Same for rankings button
        rankingsButton = buttonCreator.NewButton(new Texture("images/Buttons_and_Logo/Rankings_1.png"), 
			new Texture("images/Buttons_and_Logo/Rankings_2.png"));
		rankingsButton.addListener(new InputListener(){
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
					return true;
				}
				public void touchUp(InputEvent event, float x, float y, int pointer, int button){
					// Set RankingsStaTe to load
					stateToLoad = RANKINGSSTATE;
				}
			});

		// Create table to hold actors for stage
        Table menuTable = new Table();
		menuTable.add(logoImage);
        menuTable.row();
        menuTable.row();
        menuTable.add().height(playButton.getHeight());
        menuTable.row();
        menuTable.add(playButton);
        menuTable.row();
        menuTable.add().height(playButton.getHeight()/2);
        menuTable.row();
        menuTable.add(rankingsButton);
        menuTable.setFillParent(true);

		// Create stage and set for input processor
        stage = new Stage(new StretchViewport(CuteCatSplat.WIDTH, CuteCatSplat.HEIGHT));
        Gdx.input.setInputProcessor(stage);
		stage.addActor(menuTable);
	}
	
    @Override
    public void handleInput() {
    }

    @Override
    public void update(float dt) {
		// Load PlayState if button selected
		if(stateToLoad == PLAYSTATE)
			gsm.set(new PlayState(gsm, assets));
			
		// Load MenuState if button selected
		if(stateToLoad == RANKINGSSTATE)
			gsm.set(new RankingsState(gsm, assets));
    }

    @Override
    public void render(SpriteBatch sb) {
		// Set camera for displaying at correct zoom
		sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(imgTextureBackgroundRegion, 0, 0);
        sb.end();
		stage.act();
        stage.draw();
    }
	
    @Override
    public void dispose(){
        background.dispose();
    }
}
