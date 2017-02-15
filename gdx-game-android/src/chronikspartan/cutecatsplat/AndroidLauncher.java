package chronikspartan.cutecatsplat;

import android.os.*;
import com.badlogic.gdx.backends.android.*;
import junit.runner.*;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		String str = com.badlogic.gdx.Version.VERSION;
		String str2 = str;
		
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new CuteCatSplat(), config);
	}
}
