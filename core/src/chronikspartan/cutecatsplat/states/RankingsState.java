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
import com.badlogic.gdx.scenes.scene2d.*;
//import android.media.*;

/**
 * Created by cube on 1/20/2017.
 */

public class RankingsState extends State {
	private static final int MENUSTATE = 3;

    private Texture background, rankings, back, backDepressed;
	private TextureRegion imgTextureBackgroundRegion;
	private Image rankingsImage;
    private Button backButton;
    private Button.ButtonStyle backBtnStyle;
    private Stage stage;

	private int stateToLoad = 0;

    public RankingsState(GameStateManager gsm){
        super(gsm);
		// Set up camera
		cam.setToOrtho(false, CuteCatSplat.WIDTH/4, CuteCatSplat.HEIGHT/4);

        background = new Texture("images/Block_Solid_Grass.png");

        // Create grass background texture region
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        imgTextureBackgroundRegion = new TextureRegion(background);
        imgTextureBackgroundRegion.setRegion(0, 0, background.getWidth()*20,
											 background.getHeight()*20);

        rankings = new Texture("images/Buttons_and_Logo/Rankings_1.png");
		rankingsImage = new Image(rankings);

		// Load Back button images
        back = new Texture("images/Buttons_and_Logo/Back_1.png");
        backDepressed = new Texture("images/Buttons_and_Logo/Back_2.png");
        
		// Set Back button style and add listener
        backBtnStyle = new Button.ButtonStyle();
        backBtnStyle.up = new TextureRegionDrawable(new TextureRegion(back));
        backBtnStyle.down = new TextureRegionDrawable(new TextureRegion(backDepressed));
        backButton = new Button(backBtnStyle);
      	backButton.addListener(new InputListener(){
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
					return true;
				}
				public void touchUp(InputEvent event, float x, float y, int pointer, int button){
					// Set MenuState to load
					stateToLoad = MENUSTATE;
				}
			});

		// Create table to hold actors for stage
        Table menuTable = new Table();
		menuTable.add(rankingsImage);
        menuTable.row();
        menuTable.row();
        menuTable.add().height(backButton.getHeight());
        menuTable.row();
        menuTable.add(backButton);
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
			gsm.set(new MenuState(gsm));
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
        back.dispose();
    }
}
