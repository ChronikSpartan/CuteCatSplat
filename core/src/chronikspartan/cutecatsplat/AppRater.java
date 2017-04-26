package chronikspartan.cutecatsplat;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import chronikspartan.cutecatsplat.data.Assets;

public class AppRater extends ApplicationAdapter{
	private final static int DAYS_UNTIL_PROMPT = 0;
	private final static int LAUNCHES_UNTIL_PROMPT = 4;
	
	private Preferences prefs = Gdx.app.getPreferences("AppRater");

	private Skin skin  = new Skin(Gdx.files.internal("uiskin.json"));
	private TextButton
			btnRate = new TextButton("Fo sho!", skin),
			btnLate = new TextButton("Laters!", skin),
			btnNo = new TextButton("Nah!", skin);
	
	Stage stage = new Stage();

	
	public Dialog showRateDialog() {
		// Check if dialog should be displayed
		if(!checkRequirements())
			return null;
			
		Gdx.input.setInputProcessor(stage);
		
		Dialog dialog = new Dialog("Pesky popup!", skin);

		dialog.text("If you like cat nip and saving cats,\nor just want a free cat, please rate me.");

		dialog.padLeft(20f);
		dialog.padRight(20f);
		dialog.center();
		dialog.button(btnRate);
		dialog.button(btnLate);
		dialog.button(btnNo);
		dialog.show(stage);
		
		btnRate.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					if(prefs != null){
						Gdx.net.openURI("https://play.google.com/store/apps/details?id=chronikspartan.cutecatsplat");
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
	
	private Boolean checkRequirements() {
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
			if (System.currentTimeMillis() >= date_firstLaunch) {
				return true;
			}
		}

		return false;
	}   
	
	// Retrieves whether to show AppRater or not
	private Boolean dontShowAppRater(){
		return prefs.getBoolean("dontshowagain");
	}

	// Sets to not show AppRater 
	private void setDontShowAppRater(){
		prefs.putBoolean("dontshowagain", true);
		prefs.flush();
	}

	// Retrieves AppRater launch counter
	private int getAppRaterLaunchCount(){
		return  prefs.getInteger("launch_count");
	}

	// Sets AppRater launch counter
	private void setAppRaterLaunchCount(int launch_count){
		prefs.putInteger("launch_count", launch_count);
		prefs.flush();
	}

	// Retrieves AppRater launch counter
	private long getAppRaterDateFirstLaunch(){
		return  prefs.getLong("date_firstlaunch");
	}

	// Sets AppRater launch counter
	private void setAppRaterDateFirstLaunch(long date_firstLaunch){
		prefs.putLong("date_firstlaunch", date_firstLaunch);
		prefs.flush();
	}
	
	public void dispose()
	{
		stage.dispose();
	}
}
