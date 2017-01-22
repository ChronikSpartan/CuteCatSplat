package chronikspartan.cutecatsplat.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import chronikspartan.cutecatsplat.CuteCatSplat;

/**
 * Created by cube on 1/20/2017.
 */

public class MenuState extends State {
    private Texture background, logo, play, playDepressed, rankings, rankingsDepressed;
    private Button playButton, rankingsButton;
    private Button.ButtonStyle playBtnStyle, rankingsBtnStyle;
    private Stage stage;

    public MenuState(GameStateManager gsm){
        super(gsm);
        background = new Texture("images/Pixel_Block_Solid_Grass.png");
        logo = new Texture("images/Buttons_and_Logo/Cute_Cat_Logo.png");
        play = new Texture("images/Buttons_and_Logo/Play_1.png");
        playDepressed = new Texture("images/Buttons_and_Logo/Play_2.png");
        rankings = new Texture("images/Buttons_and_Logo/Rankings_1.png");
        rankingsDepressed = new Texture("images/Buttons_and_Logo/Rankings_2.png");

        playBtnStyle = new Button.ButtonStyle();
        playBtnStyle.up = new TextureRegionDrawable(new TextureRegion(play));
        playBtnStyle.down = new TextureRegionDrawable(new TextureRegion(playDepressed));
        playButton = new Button(playBtnStyle);
      // playButton.addListener(new ClickListener(Input.Buttons.LEFT)){

       // }

        rankingsBtnStyle = new Button.ButtonStyle();
        rankingsBtnStyle.up = new TextureRegionDrawable(new TextureRegion(rankings));
        rankingsBtnStyle.down = new TextureRegionDrawable(new TextureRegion(rankingsDepressed));
        rankingsButton = new Button(rankingsBtnStyle);

        Table menuTable = new Table();
        menuTable.row();
        menuTable.add().height(playButton.getHeight());
        menuTable.row();
        menuTable.add().height(playButton.getHeight());
        menuTable.row();
        menuTable.add(playButton);
        menuTable.row();
        menuTable.add().height(playButton.getHeight()/2);
        menuTable.row();
        menuTable.add(rankingsButton);
        menuTable.setFillParent(true);

        stage = new Stage();
        stage.addActor(menuTable);
    }
    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()) {
            gsm.set(new PlayState(gsm));
        }

    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0, CuteCatSplat.WIDTH, CuteCatSplat.HEIGHT);
        sb.draw(logo, (CuteCatSplat.WIDTH / 2) - (logo.getWidth() / 2), CuteCatSplat.HEIGHT / 1.5f);
       // sb.draw(play, (CuteCatSplat.WIDTH / 2) - (play.getWidth() / 2), CuteCatSplat.HEIGHT / 2.5f);
       // sb.draw(rankings, (CuteCatSplat.WIDTH / 2) - (rankings.getWidth() / 2), CuteCatSplat.HEIGHT / 4);
        sb.end();

        stage.draw();

    }

    @Override
    public void dispose(){
        background.dispose();
        play.dispose();
    }
}
