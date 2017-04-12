package chronikspartan.cutecatsplat.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import chronikspartan.cutecatsplat.AdsController;
import chronikspartan.cutecatsplat.data.*;

/**
 * Created by cube on 1/20/2017.
 */

public abstract class State {
    protected OrthographicCamera cam;
    protected Vector3 mouse;
    protected GameStateManager gsm;
	protected Assets assets;
    protected AdsController adsController;

    protected State(GameStateManager gsm, Assets assets, AdsController adsController){
        this.gsm = gsm;
		this.assets = assets;
        this.adsController = adsController;
        cam = new OrthographicCamera();
        mouse = new Vector3();
    }

    protected abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render(SpriteBatch sb);
    public abstract void dispose();
}
