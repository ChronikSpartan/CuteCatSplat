package chronikspartan.cutecatsplat;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.*;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.*;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class AndroidLauncher extends AndroidApplication implements AdsController{
	private static final String INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-6491016065744731/9152240807";
	AdView bannerAd;
	InterstitialAd interstitialAd;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		String str = com.badlogic.gdx.Version.VERSION;
		String str2 = str;
		
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		View gameView = initializeForView(new CuteCatSplat(this), config);
		setupAds();

		RelativeLayout layout = new RelativeLayout(this);
		layout.addView(gameView, ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);

		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		layout.addView(bannerAd, params);

		setContentView(layout);
	}

	public void setupAds() {
		bannerAd = new AdView(this);
		bannerAd.setVisibility(View.INVISIBLE);
		bannerAd.setBackgroundColor(0xff000000); // black
		bannerAd.setAdUnitId(INTERSTITIAL_AD_UNIT_ID);
		bannerAd.setAdSize(AdSize.SMART_BANNER);

		interstitialAd = new InterstitialAd(this);
		interstitialAd.setAdUnitId(INTERSTITIAL_AD_UNIT_ID);

		AdRequest.Builder builder = new AdRequest.Builder();
		AdRequest ad = builder.build();
		interstitialAd.loadAd(ad);
	}

	@Override
	public void showBannerAd() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				bannerAd.setVisibility(View.VISIBLE);
				AdRequest.Builder builder = new AdRequest.Builder();
				AdRequest ad = builder.build();
				bannerAd.loadAd(ad);
			}
		});
	}

	@Override
	public void hideBannerAd() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				bannerAd.setVisibility(View.INVISIBLE);
			}
		});
	}

	@Override
	public boolean isNetworkConnected(){
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo niWifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		NetworkInfo niMob = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

		return ((niWifi != null && niWifi.isConnected()) || (niMob != null && niMob.isConnected()));
	}

	@Override
	public void showInterstitialAd(final Runnable then) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (then != null) {
					interstitialAd.setAdListener(new AdListener() {
						@Override
						public void onAdClosed() {
							Gdx.app.postRunnable(then);
						}
					});
				}
				interstitialAd.show();
				AdRequest.Builder builder = new AdRequest.Builder();
				AdRequest ad = builder.build();
				interstitialAd.loadAd(ad);
			}
		});
	}
}
