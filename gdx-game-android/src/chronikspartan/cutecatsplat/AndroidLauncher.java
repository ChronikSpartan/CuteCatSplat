package chronikspartan.cutecatsplat;

import android.os.*;
import com.badlogic.gdx.backends.android.*;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		/* DEBUG
		String str = com.badlogic.gdx.Version.VERSION;
		System.out.println(str);
		*/
		
		super.onCreate(savedInstanceState);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new CuteCatSplat(), config);
	}
}
