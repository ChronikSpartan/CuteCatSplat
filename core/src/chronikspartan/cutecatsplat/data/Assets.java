package chronikspartan.cutecatsplat.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.*;

public class Assets {
	public static AssetManager manager = new AssetManager();
	
	public static Preferences prefs = Gdx.app.getPreferences("CuteCatSplat");
	public static BitmapFont blockedFont = new BitmapFont(Gdx.files.internal("images/Font/cutecatfont.fnt"), 
		Gdx.files.internal("images/Font/cutecatfont.png"), false);

	public static AssetDescriptor texture_cat = new AssetDescriptor<Texture>("images/Cat_Run_1.png", Texture.class);

    public static int width = Gdx.graphics.getWidth();
    public static int height = Gdx.graphics.getHeight();
	
    public static void load(){
		//manager.load(prefs);
		//manager.load(blockedFont);
		manager.load(texture_cat);
		
		// Provide default high scores of 0
		if (!prefs.contains("highScore1"))
			prefs.putInteger("highScore1", 0);
		if (!prefs.contains("highScore2")) 
			prefs.putInteger("highScore2", 0);
		if (!prefs.contains("highScore3")) 
			prefs.putInteger("highScore3", 0);
	 
		blockedFont.setUseIntegerPositions(false);
    }
	
	// Receives an integer and maps it to the String highScore1 in prefs
	public static void setHighScore1(int val) {
		prefs.putInteger("highScore1", val);
		prefs.flush();
	}
	
	// Receives an integer and maps it to the String highScore2 in prefs
	public static void setHighScore2(int val) {
		prefs.putInteger("highScore2", val);
		prefs.flush();
	}
	
	// Receives an integer and maps it to the String highScore3 in prefs
	public static void setHighScore3(int val) {
		prefs.putInteger("highScore3", val);
		prefs.flush();
	}

	// Retrieves the current 1st place high score
	public static int getHighScore1() {
		return prefs.getInteger("highScore1");
	}
	
	// Retrieves the current 2nd place high score
	public static int getHighScore2() {
		return prefs.getInteger("highScore2");
	}
	
	// Retrieves the current 3rd place high score
	public static int getHighScore3() {
		return prefs.getInteger("highScore3");
	}
	
	public void dispose()
	{
		manager.dispose();
	}
}
