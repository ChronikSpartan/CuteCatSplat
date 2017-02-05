package chronikspartan.cutecatsplat.states;

import chronikspartan.cutecatsplat.CuteCatSplat;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.*;
import chronikspartan.cutecatsplat.data.*;
import com.badlogic.gdx.*;

public class SplashState extends State {
	private static final int MENUSTATE = 3;
	private long startTime = TimeUtils.millis();
	
	private Texture splashScreen;
	private int stateToLoad = 0;

    public SplashState(GameStateManager gsm, Assets assets){
        super(gsm, assets);
		splashScreen = new Texture("images/Splash_Screen.png");

		// Set up camera
		cam.setToOrtho(false, 400, 600);

        
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
		sb.setProjectionMatrix(cam.combined);
		if(assets.manager.update() && TimeUtils.millis() - startTime > 1500){
			stateToLoad = MENUSTATE;
		}
		else
		{
			sb.begin();
			sb.draw(splashScreen, 200 - splashScreen.getWidth()/2, 
				300 - splashScreen.getHeight()/2);
			sb.end();
		}

    }

    @Override
    public void dispose(){
    }
}
