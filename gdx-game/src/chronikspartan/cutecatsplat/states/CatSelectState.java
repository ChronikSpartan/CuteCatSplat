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
import com.badlogic.gdx.scenes.scene2d.utils.*;

/**
 * Created by cube on 1/20/2017.
 */

public class CatSelectState extends State {
	private static final int PLAYSTATE = 1;
	private static final int MENUSTATE = 3;
	
    private Texture background, shadowIcon, sparkyIcon, leroyIcon, tillyIcon, trampyIcon;
	private CreateButton buttonCreator = new CreateButton();
    private Button shadowButton1, shadowButton2, shadowButton3, sparkyButton, leroyButton, tillyButton, trampyButton;
    private Stage stage;
	private String catSelected = "Sparky";
	
	public FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
	public static BitmapFont font;
	
	private int stateToLoad = 0;

    public CatSelectState(GameStateManager gsm, Assets assets, AdsController adsController){
        super(gsm, assets, adsController);
		// Set up camera
		cam.setToOrtho(false, CuteCatSplat.WIDTH, CuteCatSplat.HEIGHT);

        background = (Texture) assets.manager.get(Assets.rankingsScreen);

		shadowIcon = (Texture) assets.manager.get(Assets.catIconShadow);
		sparkyIcon = (Texture) assets.manager.get(Assets.catIconSparky);
		leroyIcon = (Texture) assets.manager.get(Assets.catIconLeroy);
		tillyIcon = (Texture) assets.manager.get(Assets.catIconTilly);
		trampyIcon = (Texture) assets.manager.get(Assets.catIconTrampy);
		
		// Create font
		parameter.size = 15;
		font = assets.generator.generateFont(parameter);
		
		shadowButton1 = buttonCreator.NewButton(shadowIcon, shadowIcon);
		shadowButton2 = buttonCreator.NewButton(shadowIcon, shadowIcon);
		shadowButton3 = buttonCreator.NewButton(shadowIcon, shadowIcon);
		
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
			
		Label sparkyName = new Label("SPARKY", 
									new Label.LabelStyle(font, Color.BLACK));
		Label tillyName = new Label("TILLY", 
								new Label.LabelStyle(font, Color.BLACK));
		Label trampyName = new Label("TRAMPY", 
								 new Label.LabelStyle(font, Color.BLACK));
		Label leroyName = new Label("LEROY", 
								new Label.LabelStyle(font, Color.BLACK));
		
		Label tilly = new Label("Pass 10 gates\nto unlock.", 
			new Label.LabelStyle(font, Color.TEAL));
		Label trampy = new Label("Get 5 cat nips\nin one go\nto unlock.", 
			new Label.LabelStyle(font, Color.TEAL));
		Label leroy = new Label("Hidden achievement.", 
			new Label.LabelStyle(font, Color.TEAL));
			
		tilly.setAlignment(Align.center);
		trampy.setAlignment(Align.center);
		leroy.setAlignment(Align.center);
	
		// Create table to hold actors for stage
        Table menuTable = new Table();
		menuTable.add().height(sparkyButton.getHeight());
		menuTable.row();
		menuTable.add(sparkyName);
		menuTable.row();
		menuTable.add(sparkyButton).height(sparkyButton.getHeight()/2.5f).width(sparkyButton.getWidth()/2.5f);
        menuTable.row();
        menuTable.add().height(sparkyButton.getHeight()/3);
		menuTable.row();
		menuTable.add(tillyName);
		menuTable.row();
		if(Assets.checkTilly())
			menuTable.add(tillyButton).height(sparkyButton.getHeight()/2.5f).width(sparkyButton.getWidth()/2.5f);
		else
			menuTable.add(shadowButton1).height(sparkyButton.getHeight()/2.5f).width(sparkyButton.getWidth()/2.5f);
        menuTable.row();
        menuTable.add().height(sparkyButton.getHeight()/3);
		menuTable.row();
		menuTable.add(trampyName);
		menuTable.row();
		if(Assets.checkTrampy())
			menuTable.add(trampyButton).height(sparkyButton.getHeight()/2.5f).width(sparkyButton.getWidth()/2.5f);
		else
			menuTable.add(shadowButton2).height(sparkyButton.getHeight()/2.5f).width(sparkyButton.getWidth()/2.5f);
        menuTable.row();
        menuTable.add().height(sparkyButton.getHeight()/3);
        menuTable.row();
		menuTable.add(leroyName);
		menuTable.row();
        if(Assets.checkLeroy())
			menuTable.add(leroyButton).height(sparkyButton.getHeight()/2.5f).width(sparkyButton.getWidth()/2.5f);
		else
			menuTable.add(shadowButton3).height(sparkyButton.getHeight()/2.5f).width(sparkyButton.getWidth()/2.5f);
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
