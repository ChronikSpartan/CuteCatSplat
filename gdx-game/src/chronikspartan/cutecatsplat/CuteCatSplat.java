package chronikspartan.cutecatsplat;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import chronikspartan.cutecatsplat.states.GameStateManager;
import chronikspartan.cutecatsplat.states.SplashState;
import chronikspartan.cutecatsplat.data.Assets;

public class CuteCatSplat extends ApplicationAdapter{
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 1820;
    public static final String TITLE = "Cute Cat Splat";

    private GameStateManager gsm;
	private Assets assets;
    private SpriteBatch batch;
    Texture img;

    @Override
    public void create(){
        batch = new SpriteBatch();
		assets = new Assets();
		assets.load();
        gsm = new GameStateManager();
        gsm.push(new SplashState(gsm, assets));
    }

    @Override
    public void render(){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(batch);
    }
}