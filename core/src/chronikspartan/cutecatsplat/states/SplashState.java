package chronikspartan.cutecatsplat.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

import chronikspartan.cutecatsplat.AdsController;
import chronikspartan.cutecatsplat.data.Assets;
import chronikspartan.cutecatsplat.services.PlayServices;

public class SplashState extends State {
	private static final int MENUSTATE = 3;
	private static final int WIDTH = 480;
	private static final int HEIGHT = 800;
	
	private long startTime = TimeUtils.millis();
	
	private Texture splashScreen;
	private int stateToLoad = 0;

    public SplashState(GameStateManager gsm, Assets assets, AdsController adsController, PlayServices playServices){
        super(gsm, assets, adsController, playServices);
		splashScreen = new Texture("images/Splash_Screen.png");
		
		// Set up camera
		cam.setToOrtho(false, WIDTH, HEIGHT);

        
	}

    @Override
    public void handleInput() {
    }

    @Override
    public void update(float dt) {
		// Load PlayState if button selected
		if(stateToLoad == MENUSTATE)
			gsm.set(new MenuState(gsm, assets, adsController, playServices));
		
    }

    @Override
    public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(cam.combined);
		if(assets.manager.update() && TimeUtils.millis() - startTime > 4000){
			stateToLoad = MENUSTATE;
		}
		else
		{
			sb.begin();
			sb.draw(splashScreen, WIDTH/2 - 200, HEIGHT/2 - 300);
			sb.end();
		}

    }

    @Override
    public void dispose(){
		assets.dispose();
    }
}
