package chronikspartan.cutecatsplat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
    public static Texture texture_grass;
    public static TextureRegion imgTextureGrassRegion;

    public static Texture texture_bush;
    public static TextureRegion imgTextureBushRegion_right;
    public static TextureRegion imgTextureBushRegion_left;
    public static Sprite sprite_back;

    public static Texture texture_cat_run_1;
    public static Texture texture_cat_run_2;
    public static Sprite spriteCat1;
    public static Sprite spriteCat2;

    public static int width = Gdx.graphics.getWidth();
    public static int height = Gdx.graphics.getHeight();
    public static int bush_width;

    public static void load(){

        // Create grass background texture region
        texture_grass = new Texture(Gdx.files.internal("images/Block_Solid_Grass.png"));
        texture_grass.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        imgTextureGrassRegion = new TextureRegion(texture_grass);
        imgTextureGrassRegion.setRegion(0, 0, texture_grass.getWidth(),
                texture_grass.getHeight());

        // Create basic bush texture
        texture_bush = new Texture(Gdx.files.internal("images/Bush.png"));
        bush_width = texture_bush.getWidth();

        // Create bush textures and then flip so that there is a left and right version
        imgTextureBushRegion_right = new TextureRegion(texture_bush);
        imgTextureBushRegion_right.setRegion(0, 0, texture_bush.getWidth(),
                texture_bush.getHeight());

        imgTextureBushRegion_left = new TextureRegion(imgTextureBushRegion_right);
        imgTextureBushRegion_left.flip(true, false);

        // Create Cat Sprites
        texture_cat_run_1 = new Texture(Gdx.files.internal("images/Cat_Run_1.png"));
        texture_cat_run_2 = new Texture(Gdx.files.internal("images/Cat_Run_2.png"));
        sprite_back = new Sprite(texture_grass);
        spriteCat1 = new Sprite(texture_cat_run_1);
        spriteCat1.flip(false,true);
        spriteCat2 = new Sprite(texture_cat_run_2);
        spriteCat2.flip(false,true);



        /*
        sprite_back.flip(true, true);
        sprite_back.scale(sprite_back.getWidth()/width);
        sprite_back.setPosition(0, 0);
        */
    }
}
