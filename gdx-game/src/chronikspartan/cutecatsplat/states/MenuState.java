package chronikspartan.cutecatsplat.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Input.Keys;
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
	
    private Texture background, splashScreen, play1, play2, rankings1, rankings2;
	private Image logoImage;
    private Button playButton, rankingsButton;
    private Stage stage;
	private CreateButton buttonCreator = new CreateButton();
	
	private int stateToLoad = 0;
	
    public MenuState(GameStateManager gsm, Assets assets){
        super(gsm, assets);
		splashScreen = new Texture("images/Splash_Screen.png");
		
		// Set up camera
		cam.setToOrtho(false, CuteCatSplat.WIDTH, CuteCatSplat.HEIGHT);
		
		background = assets.manager.get(Assets.menuScreen);
		play1 = assets.manager.get(Assets.play1);
		play2 = assets.manager.get(Assets.play2);
		rankings1 = assets.manager.get(Assets.rankings1);
		rankings2 = assets.manager.get(Assets.rankings2);
		
		// Create play button style and add listener
	    playButton = buttonCreator.NewButton(play1, play2);
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
        rankingsButton = buttonCreator.NewButton(rankings1, rankings2);
		rankingsButton.addListener(new InputListener(){
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
					return true;
				}
				public void touchUp(InputEvent event, float x, float y, int pointer, int button){
					// Set RankingsStaTe to load
					stateToLoad = RANKINGSSTATE;
				}
			});
			
		InputProcessor backProcessor = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {

                if ((keycode == Keys.ESCAPE) || (keycode == Keys.BACK) )
				{
					Assets.clear();
					Gdx.app.exit();
				}
                return false;
            }
        };
		
		// Create table to hold actors for stage
        Table menuTable = new Table();
        menuTable.add().height(playButton.getHeight()*1.5f);
        menuTable.row();
        menuTable.add(playButton).height(playButton.getHeight()/2).width(playButton.getWidth()/2);
        menuTable.row();
        menuTable.add().height(playButton.getHeight()/2);
        menuTable.row();
        menuTable.add(rankingsButton).height(playButton.getHeight()/2).width(playButton.getWidth()/2);
        menuTable.setFillParent(true);

		// Create stage and set for input processor
        stage = new Stage(new StretchViewport(CuteCatSplat.WIDTH/2.5f, CuteCatSplat.HEIGHT/2.5f));
		stage.addActor(menuTable);
		
		InputMultiplexer multiplexer = new InputMultiplexer(stage, backProcessor);
        Gdx.input.setInputProcessor(multiplexer);
		Gdx.input.setCatchBackKey(true);
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
        sb.draw(background, 0, 0);
        sb.end();
		stage.act();
        stage.draw();
    }
	
    @Override
    public void dispose(){
        assets.dispose();
    }
}
