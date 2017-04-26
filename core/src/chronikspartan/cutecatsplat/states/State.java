package chronikspartan.cutecatsplat.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import chronikspartan.cutecatsplat.AdsController;
import chronikspartan.cutecatsplat.data.*;
import chronikspartan.cutecatsplat.services.PlayServices;

/**
 * Created by cube on 1/20/2017.
 */

abstract class State {
    OrthographicCamera cam;
    GameStateManager gsm;
	protected Assets assets;
    AdsController adsController;
    PlayServices playServices;

    State(GameStateManager gsm, Assets assets, AdsController adsController, PlayServices playServices){
        this.gsm = gsm;
		this.assets = assets;
        this.adsController = adsController;
        this.playServices = playServices;
        cam = new OrthographicCamera();
    }

    protected abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render(SpriteBatch sb);
    public abstract void dispose();
}
