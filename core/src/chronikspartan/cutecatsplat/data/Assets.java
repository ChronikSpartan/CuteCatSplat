package chronikspartan.cutecatsplat.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.assets.*;

public class Assets {
	public AssetManager manager = new AssetManager();
	
	public static Preferences prefs = Gdx.app.getPreferences("CuteCatSplat");
	
	public static AssetDescriptor splatIcon = new AssetDescriptor<Texture>("images/Splat.png", Texture.class);
	public static AssetDescriptor catIconSparky = new AssetDescriptor<Texture>("images/Cat_Icon_Sparky.png", Texture.class);
	public static AssetDescriptor catIconLeroy = new AssetDescriptor<Texture>("images/Cat_Icon_Leroy.png", Texture.class);
	public static AssetDescriptor catIconTilly = new AssetDescriptor<Texture>("images/Cat_Icon_Tilly.png", Texture.class);
	public static AssetDescriptor catIconTrampy = new AssetDescriptor<Texture>("images/Cat_Icon_Trampy.png", Texture.class);
	public static AssetDescriptor catSpriteMap = new AssetDescriptor<Texture>("images/Cat_Sprite_Map.png", Texture.class);
	public static AssetDescriptor leroySpriteMap = new AssetDescriptor<Texture>("images/Cat_Sprite_Map_Leroy.png", Texture.class);
	public static AssetDescriptor tillySpriteMap = new AssetDescriptor<Texture>("images/Cat_Sprite_Map_Tilly.png", Texture.class);
	public static AssetDescriptor trampySpriteMap = new AssetDescriptor<Texture>("images/Cat_Sprite_Map_Trampy.png", Texture.class);
	public static AssetDescriptor wallExplode = new AssetDescriptor<Texture>("images/Wall_Explode.png", Texture.class);
	public static AssetDescriptor catNip = new AssetDescriptor<Texture>("images/Cat_Nip.png", Texture.class);
	public static AssetDescriptor splatTexture = new AssetDescriptor<Texture>("images/Splat_Sprite_Map.png", Texture.class);
	public static AssetDescriptor leftTexture = new AssetDescriptor<Texture>("images/Cat_Sprite_Map_Left.png", Texture.class);
	public static AssetDescriptor rightTexture = new AssetDescriptor<Texture>("images/Cat_Sprite_Map_Right.png", Texture.class);
	public static AssetDescriptor leroyLeftTexture = new AssetDescriptor<Texture>("images/Cat_Sprite_Map_Left_Leroy.png", Texture.class);
	public static AssetDescriptor leroyRightTexture = new AssetDescriptor<Texture>("images/Cat_Sprite_Map_Right_Leroy.png", Texture.class);
	public static AssetDescriptor tillyLeftTexture = new AssetDescriptor<Texture>("images/Cat_Sprite_Map_Left_Tilly.png", Texture.class);
	public static AssetDescriptor tillyRightTexture = new AssetDescriptor<Texture>("images/Cat_Sprite_Map_Right_Tilly.png", Texture.class);
	public static AssetDescriptor trampyLeftTexture = new AssetDescriptor<Texture>("images/Cat_Sprite_Map_Left_Trampy.png", Texture.class);
	public static AssetDescriptor trampyRightTexture = new AssetDescriptor<Texture>("images/Cat_Sprite_Map_Right_Trampy.png", Texture.class);
	public static AssetDescriptor textureStarsScreen1 = new AssetDescriptor<Texture>("images/Cat_Nip_Stars_1.png", Texture.class);
	public static AssetDescriptor textureStarsScreen2 = new AssetDescriptor<Texture>("images/Cat_Nip_Stars_2.png", Texture.class);
	public static AssetDescriptor textureStarsScreen3 = new AssetDescriptor<Texture>("images/Cat_Nip_Stars_3.png", Texture.class);
	public static AssetDescriptor textureSplatScreen1 = new AssetDescriptor<Texture>("images/Splat_Screen_1.jpg", Texture.class);
	public static AssetDescriptor textureSplatScreen2 = new AssetDescriptor<Texture>("images/Splat_Screen_2.jpg", Texture.class);
	public static AssetDescriptor textureSplatScreen3 = new AssetDescriptor<Texture>("images/Splat_Screen_3.jpg", Texture.class);
	public static AssetDescriptor textureSplatScreen4 = new AssetDescriptor<Texture>("images/Splat_Screen_4.jpg", Texture.class);
	public static AssetDescriptor textureSplatScreen5 = new AssetDescriptor<Texture>("images/Splat_Screen_5.jpg", Texture.class);
	public static AssetDescriptor swipe = new AssetDescriptor<Texture>("images/Swipe.jpg", Texture.class);
	public static AssetDescriptor wall = new AssetDescriptor<Texture>("images/Wall.jpg", Texture.class);
	public static AssetDescriptor bush = new AssetDescriptor<Texture>("images/Bush.png", Texture.class);
	public static AssetDescriptor trophy1 = new AssetDescriptor<Texture>("images/Buttons_and_Screens/Trophy_1.png", Texture.class);
	public static AssetDescriptor trophy2 = new AssetDescriptor<Texture>("images/Buttons_and_Screens/Trophy_2.png", Texture.class);
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
	public static AssetDescriptor theme = new AssetDescriptor<Music>("audio/Theme.mp3", Music.class);
	public static AssetDescriptor catNipTune = new AssetDescriptor<Music>("audio/Cat_Nip_Time.mp3", Music.class);
	public static AssetDescriptor miaow = new AssetDescriptor<Sound>("audio/Miaow.mp3", Sound.class);
	public static AssetDescriptor miaow2 = new AssetDescriptor<Sound>("audio/Miaow_2.mp3", Sound.class);
	public static AssetDescriptor purr = new AssetDescriptor<Sound>("audio/Purr.mp3", Sound.class);
	public static AssetDescriptor screech = new AssetDescriptor<Sound>("audio/Screech.mp3", Sound.class);
	public static AssetDescriptor splat = new AssetDescriptor<Sound>("audio/Splat.mp3", Sound.class);
	public static AssetDescriptor fenceSmash = new AssetDescriptor<Sound>("audio/Fence_Smash.wav", Sound.class);
	
	public FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("images/Font/BubblegumSans-Regular.ttf"));
	
    public static int width = Gdx.graphics.getWidth();
    public static int height = Gdx.graphics.getHeight();
	
    public void load(){
		manager.load(splatIcon);
		manager.load(catIconSparky);
		manager.load(catIconLeroy);
		manager.load(catIconTilly);
		manager.load(catIconTrampy);
		manager.load(catSpriteMap);
		manager.load(leroySpriteMap);
		manager.load(tillySpriteMap);
		manager.load(trampySpriteMap);
		manager.load(wallExplode);
		manager.load(catNip);
		manager.load(splatTexture);
		manager.load(leftTexture);
		manager.load(rightTexture);
		manager.load(leroyLeftTexture);
		manager.load(leroyRightTexture);
		manager.load(tillyLeftTexture);
		manager.load(tillyRightTexture);
		manager.load(trampyLeftTexture);
		manager.load(trampyRightTexture);
		manager.load(swipe);
		manager.load(wall);
		manager.load(bush);
		manager.load(trophy1);
		manager.load(trophy2);
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
		manager.load(catNipTune);
		manager.load(theme);
		manager.load(miaow);
		manager.load(miaow2);
		manager.load(purr);
		manager.load(screech);
		manager.load(splat);
		manager.load(fenceSmash);
		manager.load(textureSplatScreen1);
		manager.load(textureSplatScreen2);
		manager.load(textureSplatScreen3);
		manager.load(textureSplatScreen4);
		manager.load(textureSplatScreen5);
		manager.load(textureStarsScreen1);
		manager.load(textureStarsScreen2);
		manager.load(textureStarsScreen3);
		
		// Provide default high scores of 0
		if (!prefs.contains("highScore1"))
			prefs.putInteger("highScore1", 0);
    }
	
	public void clear(){
		manager.clear();
	}
	
	// Receives an integer and maps it to the String highScore1 in prefs
	public static void setHighScore1(int val) {
		prefs.putInteger("highScore1", val);
		prefs.flush();
	}

	// Retrieves the current 1st place high score
	public static int getHighScore1() {
		return prefs.getInteger("highScore1");
	}

	// Unlocks Leroy
	public static void unlockLeroy() {
		prefs.putBoolean("LeroyUnlocked", true);
		prefs.flush();
	}

	// Retrieves if Leroy is unlocked
	public static Boolean checkLeroy() {
		return prefs.getBoolean("LeroyUnlocked");
	}
	
	// Unlocks Leroy
	public static void unlockTilly() {
		prefs.putBoolean("TillyUnlocked", true);
		prefs.flush();
	}

	// Retrieves if Leroy is unlocked
	public static Boolean checkTilly() {
		return prefs.getBoolean("TillyUnlocked");
	}
	
	// Unlocks Leroy
	public static void unlockTrampy() {
		prefs.putBoolean("TrampyUnlocked", true);
		prefs.flush();
	}

	// Retrieves if Leroy is unlocked
	public static Boolean checkTrampy() {
		return prefs.getBoolean("TrampyUnlocked");
	}
	
	public void dispose()
	{
		manager.dispose();
	}
}
