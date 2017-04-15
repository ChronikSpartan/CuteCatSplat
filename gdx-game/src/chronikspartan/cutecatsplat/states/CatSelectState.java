package chronikspartan.cutecatsplat.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import chronikspartan.cutecatsplat.AdsController;
import chronikspartan.cutecatsplat.CuteCatSplat;
import chronikspartan.cutecatsplat.CreateButton;
import chronikspartan.cutecatsplat.data.Assets;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.*;

/**
 * Created by cube on 1/20/2017.
 */

public class CatSelectState extends State {
	private static final int PLAYSTATE = 1;
	private static final int MENUSTATE = 3;
	
    private Texture background, sparkyIcon, leroyIcon, tillyIcon, trampyIcon;
	private CreateButton buttonCreator = new CreateButton();
    private Button sparkyButton, leroyButton, tillyButton, trampyButton;
    private Stage stage;
	private String catSelected = "Sparky";
	
	public FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
	public static BitmapFont font;
	
	public Color gold = new Color(1f, 0.85f, 0f, 1);
	public Color silver = new Color(0.75f, 0.75f, 0.75f, 1);
	public Color bronze = new Color(0.8f, 0.5f, 0.2f, 1);

	private int stateToLoad = 0;

    public CatSelectState(GameStateManager gsm, Assets assets, AdsController adsController){
        super(gsm, assets, adsController);
		// Set up camera
		cam.setToOrtho(false, CuteCatSplat.WIDTH, CuteCatSplat.HEIGHT);

        background = (Texture) assets.manager.get(Assets.rankingsScreen);

		sparkyIcon = (Texture) assets.manager.get(Assets.catIconSparky);
		leroyIcon = (Texture) assets.manager.get(Assets.catIconLeroy);
		tillyIcon = (Texture) assets.manager.get(Assets.catIconTilly);
		trampyIcon = (Texture) assets.manager.get(Assets.catIconTrampy);
		
		// Create font
		parameter.size = 50;
		font = assets.generator.generateFont(parameter);
		
		// Create Sparky buttons and add listener
        sparkyButton = buttonCreator.NewButton(sparkyIcon, sparkyIcon);
      	sparkyButton.addListener(new InputListener(){
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
					Gdx.input.vibrate(5);
					return true;
				}
				public void touchUp(InputEvent event, float x, float y, int pointer, int button){
					// Set MenuState to load
					stateToLoad = PLAYSTATE;
					catSelected = "Sparky";
				}
			});
			
		// Create Leroy buttons and add listener
        leroyButton = buttonCreator.NewButton(leroyIcon, leroyIcon);
      	leroyButton.addListener(new InputListener(){
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
					Gdx.input.vibrate(5);
					return true;
				}
				public void touchUp(InputEvent event, float x, float y, int pointer, int button){
					// Set MenuState to load
					stateToLoad = PLAYSTATE;
					catSelected = "Leroy";
				}
			});
			
		// Create Tilly buttons and add listener
        tillyButton = buttonCreator.NewButton(tillyIcon, tillyIcon);
      	tillyButton.addListener(new InputListener(){
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
					Gdx.input.vibrate(5);
					return true;
				}
				public void touchUp(InputEvent event, float x, float y, int pointer, int button){
					// Set MenuState to load
					stateToLoad = PLAYSTATE;
					catSelected = "Tilly";
				}
			});
			
		// Create Trampy buttons and add listener
        trampyButton = buttonCreator.NewButton(trampyIcon, trampyIcon);
      	trampyButton.addListener(new InputListener(){
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
					Gdx.input.vibrate(5);
					return true;
				}
				public void touchUp(InputEvent event, float x, float y, int pointer, int button){
					// Set MenuState to load
					stateToLoad = PLAYSTATE;
					catSelected = "Trampy";
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
			
		Label highScore1 = new Label("1st Place: " + String.valueOf(Assets.getHighScore1()), 
			new Label.LabelStyle(font, gold));
		Label highScore2 = new Label("2nd Place: " + String.valueOf(Assets.getHighScore2()), 
									 new Label.LabelStyle(font, silver));
		Label highScore3 = new Label("3rd Place: " + String.valueOf(Assets.getHighScore3()), 
									 new Label.LabelStyle(font, bronze));
	
		// Create table to hold actors for stage
        Table menuTable = new Table();
		menuTable.add().height(sparkyButton.getHeight());
		menuTable.row();
		menuTable.add(sparkyButton).height(sparkyButton.getHeight()/2.5f).width(sparkyButton.getWidth()/2.5f);
        menuTable.row();
        menuTable.add().height(sparkyButton.getHeight()/3);
		menuTable.row();
		menuTable.add(leroyButton).height(sparkyButton.getHeight()/2.5f).width(sparkyButton.getWidth()/2.5f);
        menuTable.row();
        menuTable.add().height(sparkyButton.getHeight()/3);
		menuTable.row();
		menuTable.add(tillyButton).height(sparkyButton.getHeight()/2.5f).width(sparkyButton.getWidth()/2.5f);
        menuTable.row();
        menuTable.add().height(sparkyButton.getHeight()/3);
        menuTable.row();
        menuTable.add(trampyButton).height(sparkyButton.getHeight()/2.5f).width(sparkyButton.getWidth()/2.5f);
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
		// Load Stateselected
		if(stateToLoad == MENUSTATE)
			gsm.set(new MenuState(gsm, assets, adsController));
			
		if(stateToLoad == PLAYSTATE)
			gsm.set(new PlayState(gsm, assets, adsController, catSelected));
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
