package chronikspartan.cutecatsplat;

import android.os.*;
import com.badlogic.gdx.backends.android.*;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new CuteCatSplat(), config);
	}
}
