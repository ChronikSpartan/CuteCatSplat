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
	
	public static AssetDescriptor textureCat = new AssetDescriptor<Texture>("images/Cat_Run_1.png", Texture.class);
	public static AssetDescriptor textureSplatScreen1 = new AssetDescriptor<Texture>("images/Splat_Screen_1.jpg", Texture.class);
	public static AssetDescriptor textureSplatScreen2 = new AssetDescriptor<Texture>("images/Splat_Screen_2.jpg", Texture.class);
	public static AssetDescriptor textureSplatScreen3 = new AssetDescriptor<Texture>("images/Splat_Screen_3.jpg", Texture.class);
	public static AssetDescriptor textureSplatScreen4 = new AssetDescriptor<Texture>("images/Splat_Screen_4.jpg", Texture.class);
	public static AssetDescriptor textureSplatScreen5 = new AssetDescriptor<Texture>("images/Splat_Screen_5.jpg", Texture.class);
	public static AssetDescriptor play1 = new AssetDescriptor<Texture>("images/Buttons_and_Screens/Play_1.png", Texture.class);
	public static AssetDescriptor play2 = new AssetDescriptor<Texture>("images/Buttons_and_Screens/Play_2.png", Texture.class);
	public static AssetDescriptor rankings1 = new AssetDescriptor<Texture>("images/Buttons_and_Screens/Rankings_1.png", Texture.class);
	public static AssetDescriptor rankings2 = new AssetDescriptor<Texture>("images/Buttons_and_Screens/Rankings_2.png", Texture.class);
	public static AssetDescriptor back1 = new AssetDescriptor<Texture>("images/Buttons_and_Screens/Back_1.png", Texture.class);
	public static AssetDescriptor back2 = new AssetDescriptor<Texture>("images/Buttons_and_Screens/Back_2.png", Texture.class);
	public static AssetDescriptor restart1 = new AssetDescriptor<Texture>("images/Buttons_and_Screens/Restart_1.png", Texture.class);
	public static AssetDescriptor restart2 = new AssetDescriptor<Texture>("images/Buttons_and_Screens/Restart_2.png", Texture.class);
	public static AssetDescriptor menuScreen = new AssetDescriptor<Texture>("images/Buttons_and_Screens/Menu_Screen.png", Texture.class);
	public static AssetDescriptor rankingsScreen = new AssetDescriptor<Texture>("images/Buttons_and_Screens/Rankings_Screen.png", Texture.class);
	
	public static BitmapFont blockedFont;
	
    public static int width = Gdx.graphics.getWidth();
    public static int height = Gdx.graphics.getHeight();
	
    public static void load(){
		blockedFont = new BitmapFont(Gdx.files.internal("images/Font/cutecatfont.fnt"), 
			Gdx.files.internal("images/Font/cutecatfont.png"), false);
	
		manager.load(textureCat);
		manager.load(play1);
		manager.load(play2);
		manager.load(rankings1);
		manager.load(rankings2);
		manager.load(back1);
		manager.load(back2);
		manager.load(restart1);
		manager.load(restart2);
		manager.load(menuScreen);
		manager.load(rankingsScreen);
		manager.load(textureSplatScreen1);
		manager.load(textureSplatScreen2);
		manager.load(textureSplatScreen3);
		manager.load(textureSplatScreen4);
		manager.load(textureSplatScreen5);
		
		// Provide default high scores of 0
		if (!prefs.contains("highScore1"))
			prefs.putInteger("highScore1", 0);
		if (!prefs.contains("highScore2")) 
			prefs.putInteger("highScore2", 0);
		if (!prefs.contains("highScore3")) 
			prefs.putInteger("highScore3", 0);
	 
		blockedFont.setUseIntegerPositions(false);
    }
	
	public static void clear(){
		manager.clear();
		blockedFont.dispose();
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
		blockedFont.dispose();
	}
}
