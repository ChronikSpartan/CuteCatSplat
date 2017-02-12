package chronikspartan.cutecatsplat.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
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

import chronikspartan.cutecatsplat.CuteCatSplat;
import chronikspartan.cutecatsplat.CreateButton;
import chronikspartan.cutecatsplat.data.Assets;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.*;
//import android.media.*;

/**
 * Created by cube on 1/20/2017.
 */

public class RankingsState extends State {
	private static final int MENUSTATE = 3;
	
    private Texture background, back1, back2;
	private CreateButton buttonCreator = new CreateButton();
    private Button backButton;
    private Stage stage;

	private int stateToLoad = 0;

    public RankingsState(GameStateManager gsm, Assets assets){
        super(gsm, assets);
		// Set up camera
		cam.setToOrtho(false, 1080, 1920);

        background = assets.manager.get(Assets.rankingsScreen);

		back1 = assets.manager.get(Assets.back1);
		back2 = assets.manager.get(Assets.back2);
		
		// Create Back button and add listener
        backButton = buttonCreator.NewButton(back1, back2);
      	backButton.addListener(new InputListener(){
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
					return true;
				}
				public void touchUp(InputEvent event, float x, float y, int pointer, int button){
					// Set MenuState to load
					stateToLoad = MENUSTATE;
				}
			});
			
		//Assets.blockedFont.draw(sb, String.valueOf(points), (cam.viewportWidth / 2), cat.getPosition().y + 200);
		Label highScore1 = new Label("1st Place: " + String.valueOf(Assets.getHighScore1()), 
			new Label.LabelStyle(Assets.blockedFont, Color.RED));
		Label highScore2 = new Label("2nd Place: " + String.valueOf(Assets.getHighScore2()), 
									 new Label.LabelStyle(Assets.blockedFont, Color.RED));
		Label highScore3 = new Label("3rd Place: " + String.valueOf(Assets.getHighScore3()), 
									 new Label.LabelStyle(Assets.blockedFont, Color.RED));
	
		// Create table to hold actors for stage
        Table menuTable = new Table();
		menuTable.add().height(backButton.getHeight());
		menuTable.row();
		menuTable.add(highScore1);
        menuTable.row();
        menuTable.add().height(backButton.getHeight()/2);
		menuTable.row();
		menuTable.add(highScore2);
        menuTable.row();
        menuTable.add().height(backButton.getHeight()/2);
		menuTable.row();
		menuTable.add(highScore3);
        menuTable.row();
        menuTable.add().height(backButton.getHeight()/2);
        menuTable.row();
        menuTable.add(backButton).height(backButton.getHeight()/2).width(backButton.getWidth()/2);
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
		if(stateToLoad == MENUSTATE)
			gsm.set(new MenuState(gsm, assets));
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
