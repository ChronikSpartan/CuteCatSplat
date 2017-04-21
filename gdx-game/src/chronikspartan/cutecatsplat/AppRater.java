package chronikspartan.cutecatsplat;

import chronikspartan.cutecatsplat.data.Assets;

import android.content.Context;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.view.View.*;
import android.content.Intent;
import android.net.Uri;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.*;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class AppRater extends ApplicationAdapter{
	private final static String APP_PNAME = "chronikspartan.cutecatsplat";

	private final static int DAYS_UNTIL_PROMPT = 0;
	private final static int LAUNCHES_UNTIL_PROMPT = 2 ;
	
	private Preferences prefs = Gdx.app.getPreferences("AppRater");
	
	private TextButton btnRate, btnLate, btnNo;
	
	Stage stage = new Stage();
	Skin skin  = new Skin(Gdx.files.internal("uiskin.json")); 
	
	public Dialog showRateDialog() {
		// Check if dialog should be displayed
		if(!checkRequirements())
			return null;
			
		Gdx.input.setInputProcessor(stage);
		
		Dialog dialog = new Dialog("Pesky popup!", skin);
		
		dialog.padLeft(20f);
		dialog.padRight(20f);
		dialog.text("If you like cat nip and saving cats,\nor just want a free cat,\nplease rate me.");
					
		btnRate = new TextButton("Fo sho!", skin);
		btnLate = new TextButton("Laters!", skin);
		btnNo = new TextButton("Nah!", skin);
		dialog.button(btnRate);
		dialog.button(btnLate);
		dialog.button(btnNo);
		dialog.show(stage);
		
		btnRate.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					if(prefs != null){
						Gdx.net.openURI("https://play.google.com/store/apps/details?id=" + APP_PNAME);
						Assets.unlockLeroy();
						setDontShowAppRater();
					}
				}
			});
			
		btnLate.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					if(prefs != null){
						setAppRaterLaunchCount(0);
					}
				}
			});
			
		btnNo.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					if (prefs != null) {
						setDontShowAppRater();
					}
				}
			});
		
		return dialog;
    }
	
	public Boolean checkRequirements() {
		if (dontShowAppRater()) { return false; }

		// Increment launch counter
		int launch_count = getAppRaterLaunchCount() + 1;
		setAppRaterLaunchCount(launch_count);

		// Get date of first launch
		Long date_firstLaunch = getAppRaterDateFirstLaunch();
		if (date_firstLaunch == 0) {
			date_firstLaunch = System.currentTimeMillis();
			setAppRaterDateFirstLaunch(date_firstLaunch);
		}

		// Wait at least n days before opening dialog
		if (launch_count >= LAUNCHES_UNTIL_PROMPT) {
			if (System.currentTimeMillis() >= date_firstLaunch + 
                (DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000)) {
				return true;
			}
		}

		return false;
	}   
	
	// Retrieves whether to show AppRater or not
	public Boolean dontShowAppRater(){
		return prefs.getBoolean("dontshowagain");
	}

	// Sets to not show AppRater 
	public void setDontShowAppRater(){
		prefs.putBoolean("dontshowagain", true);
		prefs.flush();
	}

	// Retrieves AppRater launch counter
	public int getAppRaterLaunchCount(){
		return  prefs.getInteger("launch_count");
	}

	// Sets AppRater launch counter
	public void setAppRaterLaunchCount(int launch_count){
		prefs.putInteger("launch_count", launch_count);
		prefs.flush();
	}

	// Retrieves AppRater launch counter
	public long getAppRaterDateFirstLaunch(){
		return  prefs.getLong("date_firstlaunch");
	}

	// Sets AppRater launch counter
	public void setAppRaterDateFirstLaunch(long date_firstLaunch){
		prefs.putLong("date_firstlaunch", date_firstLaunch);
		prefs.flush();
	}
	
	public void dispose()
	{
		stage.dispose();
	}
}
