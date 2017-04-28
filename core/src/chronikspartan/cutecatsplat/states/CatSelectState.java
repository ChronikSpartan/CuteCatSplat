package chronikspartan.cutecatsplat.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import chronikspartan.cutecatsplat.AdsController;
import chronikspartan.cutecatsplat.CuteCatSplat;
import chronikspartan.cutecatsplat.CreateButton;
import chronikspartan.cutecatsplat.data.Assets;
import chronikspartan.cutecatsplat.services.PlayServices;

import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.*;

import static com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.*;

/**
 * Created by cube on 1/20/2017.
 */

class CatSelectState extends State {
	private static final int PLAYSTATE = 1;
	private static final int MENUSTATE = 3;
	
    private Texture background;
	private Stage stage;
	private int catSelected = 1; // 1 = Sparky, 2 = Leroy, 3 = Tilly, 4 = Trampy

	public static BitmapFont font;
	
	private int stateToLoad = 0;

    CatSelectState(GameStateManager gsm, Assets assets, AdsController adsController, PlayServices playServices){
        super(gsm, assets, adsController, playServices);
		// Set up camera
		cam.setToOrtho(false, CuteCatSplat.WIDTH, CuteCatSplat.HEIGHT);

        background = (Texture) assets.manager.get(Assets.rankingsScreen);

		Texture splatIcon = (Texture) assets.manager.get(Assets.splatIcon);
		Texture sparkyIcon = (Texture) assets.manager.get(Assets.catIconSparky);
		Texture leroyIcon = (Texture) assets.manager.get(Assets.catIconLeroy);
		Texture tillyIcon = (Texture) assets.manager.get(Assets.catIconTilly);
		Texture trampyIcon = (Texture) assets.manager.get(Assets.catIconTrampy);
		
		// Create font
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 15;
		font = assets.generator.generateFont(parameter);

		CreateButton buttonCreator = new CreateButton();
		Button splatButton1 = buttonCreator.NewButton(splatIcon, splatIcon);
		Button splatButton2 = buttonCreator.NewButton(splatIcon, splatIcon);
		Button splatButton3 = buttonCreator.NewButton(splatIcon, splatIcon);
		
		// Create Sparky buttons and add listener
		Button sparkyButton = buttonCreator.NewButton(sparkyIcon, sparkyIcon);
      	sparkyButton.addListener(new InputListener(){
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
					Gdx.input.vibrate(5);
					return true;
				}
				public void touchUp(InputEvent event, float x, float y, int pointer, int button){
					// Set MenuState to load
					stateToLoad = PLAYSTATE;
					catSelected = 1;
				}
			});
			
		// Create Leroy buttons and add listener
		Button leroyButton = buttonCreator.NewButton(leroyIcon, leroyIcon);
      	leroyButton.addListener(new InputListener(){
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
					Gdx.input.vibrate(5);
					return true;
				}
				public void touchUp(InputEvent event, float x, float y, int pointer, int button){
					// Set MenuState to load
					stateToLoad = PLAYSTATE;
					catSelected = 2;
				}
			});
			
		// Create Tilly buttons and add listener
		Button tillyButton = buttonCreator.NewButton(tillyIcon, tillyIcon);
      	tillyButton.addListener(new InputListener(){
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
					Gdx.input.vibrate(5);
					return true;
				}
				public void touchUp(InputEvent event, float x, float y, int pointer, int button){
					// Set MenuState to load
					stateToLoad = PLAYSTATE;
					catSelected = 3;
				}
			});
			
		// Create Trampy buttons and add listener
		Button trampyButton = buttonCreator.NewButton(trampyIcon, trampyIcon);
      	trampyButton.addListener(new InputListener(){
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
					Gdx.input.vibrate(5);
					return true;
				}
				public void touchUp(InputEvent event, float x, float y, int pointer, int button){
					// Set MenuState to load
					stateToLoad = PLAYSTATE;
					catSelected = 4;
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
			new Label.LabelStyle(font, Color.BLACK));
		Label trampy = new Label("Get 5 cat nips\nin one go\nto unlock.", 
			new Label.LabelStyle(font, Color.BLACK));
		Label leroy = new Label("Hidden achievement.", 
			new Label.LabelStyle(font, Color.BLACK));
			
		tilly.setAlignment(Align.center);
		trampy.setAlignment(Align.center);
		leroy.setAlignment(Align.center);

		Stack tillyStack = new Stack();
		tillyStack.add(splatButton1);
		tillyStack.add(tilly);

		Stack trampyStack = new Stack();
		trampyStack.add(splatButton2);
		trampyStack.add(trampy);

		Stack leroyStack = new Stack();
		leroyStack.add(splatButton3);
		leroyStack.add(leroy);
	
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
			menuTable.add(tillyStack).height(sparkyButton.getHeight() / 2.5f).width(sparkyButton.getHeight() / 2.5f);
        menuTable.row();
        menuTable.add().height(sparkyButton.getHeight()/3);
		menuTable.row();
		menuTable.add(trampyName);
		menuTable.row();
		if(Assets.checkTrampy())
			menuTable.add(trampyButton).height(sparkyButton.getHeight()/2.5f).width(sparkyButton.getWidth()/2.5f);
		else
			menuTable.add(trampyStack).height(sparkyButton.getHeight()/2.5f).width(sparkyButton.getHeight()/2.5f);
        menuTable.row();
        menuTable.add().height(sparkyButton.getHeight()/3);
        menuTable.row();
		menuTable.add(leroyName);
		menuTable.row();
        if(Assets.checkLeroy())
			menuTable.add(leroyButton).height(sparkyButton.getHeight()/2.5f).width(sparkyButton.getWidth()/2.5f);
		else
			menuTable.add(leroyStack).height(sparkyButton.getHeight()/2.5f).width(sparkyButton.getHeight()/2.5f);
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
			gsm.set(new MenuState(gsm, assets, adsController, playServices));
			
		if(stateToLoad == PLAYSTATE)
			gsm.set(new PlayState(gsm, assets, adsController, playServices, catSelected, 0));
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
		stage.dispose();
    }
}
