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

public class RankingsState extends State {
	private static final int MENUSTATE = 3;
	
    private Texture background, back1, back2;
	private CreateButton buttonCreator = new CreateButton();
    private Button backButton;
    private Stage stage;
	
	public FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
	public static BitmapFont font;
	
	public Color gold = new Color(1f, 0.85f, 0f, 1);
	public Color silver = new Color(0.75f, 0.75f, 0.75f, 1);
	public Color bronze = new Color(0.8f, 0.5f, 0.2f, 1);

	private int stateToLoad = 0;

    public RankingsState(GameStateManager gsm, Assets assets, AdsController adsController){
        super(gsm, assets, adsController);
		// Set up camera
		cam.setToOrtho(false, CuteCatSplat.WIDTH, CuteCatSplat.HEIGHT);

        background = (Texture) assets.manager.get(Assets.rankingsScreen);

		back1 = (Texture) assets.manager.get(Assets.back1);
		back2 = (Texture) assets.manager.get(Assets.back2);
		
		// Create font
		parameter.size = 50;
		font = assets.generator.generateFont(parameter);
		
		// Create Back button and add listener
        backButton = buttonCreator.NewButton(back1, back2);
      	backButton.addListener(new InputListener(){
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
					Gdx.input.vibrate(5);
					return true;
				}
				public void touchUp(InputEvent event, float x, float y, int pointer, int button){
					// Set MenuState to load
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
			
		Label highScore1 = new Label("1st Place: " + String.valueOf(Assets.getHighScore1()), 
			new Label.LabelStyle(font, gold));
		Label highScore2 = new Label("2nd Place: " + String.valueOf(Assets.getHighScore2()), 
									 new Label.LabelStyle(font, silver));
		Label highScore3 = new Label("3rd Place: " + String.valueOf(Assets.getHighScore3()), 
									 new Label.LabelStyle(font, bronze));
	
		// Create table to hold actors for stage
        Table menuTable = new Table();
		menuTable.add().height(backButton.getHeight());
		menuTable.row();
		menuTable.add(highScore1);
        menuTable.row();
        menuTable.add().height(backButton.getHeight()/3);
		menuTable.row();
		menuTable.add(highScore2);
        menuTable.row();
        menuTable.add().height(backButton.getHeight()/3);
		menuTable.row();
		menuTable.add(highScore3);
        menuTable.row();
        menuTable.add().height(backButton.getHeight()/3);
        menuTable.row();
        menuTable.add(backButton).height(backButton.getHeight()/2).width(backButton.getWidth()/2);
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
		if(stateToLoad == MENUSTATE)
			gsm.set(new MenuState(gsm, assets, adsController));
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
