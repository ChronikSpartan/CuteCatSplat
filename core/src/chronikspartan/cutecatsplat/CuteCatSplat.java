package chronikspartan.cutecatsplat;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import chronikspartan.cutecatsplat.data.Assets;
import chronikspartan.cutecatsplat.services.PlayServices;
import chronikspartan.cutecatsplat.states.GameStateManager;
import chronikspartan.cutecatsplat.states.SplashState;

public class CuteCatSplat extends ApplicationAdapter{
    public static final int WIDTH = 1080;
    public static final int HEIGHT = 1820;
    public static final String TITLE = "Cute Cat Splat";

    private GameStateManager gsm;
    private SpriteBatch batch;

    private AdsController adsController;
    private static PlayServices playServices;

    public CuteCatSplat(AdsController adsController, PlayServices playServices) {
        if (adsController != null)
            this.adsController = adsController;
        else
            this.adsController = new DummyAdsController();
        this.playServices = playServices;
    }

    @Override
    public void create(){
        batch = new SpriteBatch();
        Assets assets = new Assets();
		assets.clear();
		assets.load();
        gsm = new GameStateManager();
        gsm.push(new SplashState(gsm, assets, adsController, playServices));
    }

    @Override
    public void render(){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(batch);
    }

    private class DummyAdsController implements AdsController {
        @Override
        public void showBannerAd() {

        }

        @Override
        public void hideBannerAd() {

        }

        @Override
        public boolean isNetworkConnected() {
            return false;
        }

        @Override
        public void showInterstitialAd(Runnable then) {

        }
    }
}
