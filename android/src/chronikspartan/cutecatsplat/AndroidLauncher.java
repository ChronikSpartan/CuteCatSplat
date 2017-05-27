package chronikspartan.cutecatsplat;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.*;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.*;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;
import com.inmobi.sdk.InMobiSdk;

import chronikspartan.cutecatsplat.services.PlayServices;

public class AndroidLauncher extends AndroidApplication implements AdsController, PlayServices, RewardedVideoAdListener
{
	private static final String INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-6491016065744731/9152240807";
	private static final String REWARDED_AD_UNIT_ID = "ca-app-pub-6491016065744731/1782126409";
	private AdView bannerAd;
	private InterstitialAd interstitialAd;
	private RewardedVideoAd rewardedVideoAd;
	private GameHelper gameHelper;
	private final static int requestCode = 1;

	private AdRequest.Builder builder;
	private AdRequest ad;

	private Boolean gameContinue = false;
	private Boolean rewarded = false;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		String str = com.badlogic.gdx.Version.VERSION;
		String str2 = str;
		
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		View gameView = initializeForView(new CuteCatSplat(this, this), config);

		InMobiSdk.init(this, "7cd708a91a1848c8a36f4f10d2de3ee6");
		builder = new AdRequest.Builder();
		ad = builder.build();
		setupAds();

		gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
		gameHelper.enableDebugLog(false);

		GameHelper.GameHelperListener gameHelperListener = new GameHelper.GameHelperListener()
		{
			@Override
			public void onSignInFailed(){ }

			@Override
			public void onSignInSucceeded(){ }
		};

		gameHelper.setup(gameHelperListener);

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

		rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
		rewardedVideoAd.setRewardedVideoAdListener(this);

		interstitialAd.loadAd(ad);
		loadRewardAd();
	}

	private void loadRewardAd(){
		if(!rewardedVideoAd.isLoaded()){
			rewardedVideoAd.loadAd(REWARDED_AD_UNIT_ID, ad);
		}
	}

	@Override
	public boolean getReward(){
		return gameContinue;
	}

	@Override
	public void setReward(Boolean state){
		gameContinue = state;
		rewarded = state;
	}

	@Override
	public void showRewardAd(){
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if(rewardedVideoAd.isLoaded())
					rewardedVideoAd.show();
					}
		});
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

	@Override
	protected void onStart()
	{
		super.onStart();
		gameHelper.onStart(this);
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		gameHelper.onStop();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		gameHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void signIn()
	{
		try
		{
			runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					gameHelper.beginUserInitiatedSignIn();
				}
			});
		}
		catch (Exception e)
		{
			Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
		}
	}

	@Override
	public void signOut()
	{
		try
		{
			runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					gameHelper.signOut();
				}
			});
		}
		catch (Exception e)
		{
			Gdx.app.log("MainActivity", "Log out failed: " + e.getMessage() + ".");
		}
	}

	@Override
	public void rateGame()
	{
		String str = "https://play.google.com/store/apps/details?id=chronikspartan.cutecatsplat";
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
	}

	@Override
	public void unlockAchievementFirstGate()
	{
		if (isSignedIn() == true)
		{
			Games.Achievements.unlock(gameHelper.getApiClient(),
					getString(R.string.achievement_first_gate_baby));
		}
		else
		{
			signIn();
		}
	}

	@Override
	public void unlockAchievementFifthGate() {
		if (isSignedIn() == true)
		{
			Games.Achievements.unlock(gameHelper.getApiClient(),
					getString(R.string.achievement_getting_the_hang_of_it));
		}
		else
		{
			signIn();
		}
	}

	@Override
	public void unlockAchievementNinthGate() {
		if (isSignedIn() == true)
		{
			Games.Achievements.unlock(gameHelper.getApiClient(),
					getString(R.string.achievement_the_ninth_gate));
		}
		else
		{
			signIn();
		}
	}

	@Override
	public void unlockAchievementFirstCatNip() {
		if (isSignedIn() == true)
		{
			Games.Achievements.unlock(gameHelper.getApiClient(),
					getString(R.string.achievement_what_is_this));
		}
		else
		{
			signIn();
		}
	}

	@Override
	public void unlockAchievementThreeCatNips() {
		if (isSignedIn() == true)
		{
			Games.Achievements.unlock(gameHelper.getApiClient(),
					getString(R.string.achievement_420_cat));
		}
		else
		{
			signIn();
		}
	}

	@Override
	public void submitScore(int highScore)
	{
		if (isSignedIn() == true)
		{
			Games.Leaderboards.submitScore(gameHelper.getApiClient(),
					getString(R.string.leaderboard_world_championship), highScore);
		}
	}

	@Override
	public void showAchievement()
	{
		if (isSignedIn() == true)
		{
			startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), requestCode);
		}
		else
		{
			signIn();
		}
	}

	@Override
	public void showScore()
	{
		if (isSignedIn() == true)
		{
			startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(),
					getString(R.string.leaderboard_world_championship)), requestCode);
		}
		else
		{
			signIn();
		}
	}

	@Override
	public boolean isSignedIn()
	{
		return gameHelper.isSignedIn();
	}

	@Override
	public void onRewardedVideoAdLoaded() {
	}

	@Override
	public void onRewardedVideoAdOpened() {

	}

	@Override
	public void onRewardedVideoStarted() {

	}

	@Override
	public void onRewardedVideoAdClosed() {
		if(rewarded)
			gameContinue = true;
		loadRewardAd();
	}

	@Override
	public void onRewarded(RewardItem rewardItem) {
		rewarded = true;
	}

	@Override
	public void onRewardedVideoAdLeftApplication() {

	}

	@Override
	public void onRewardedVideoAdFailedToLoad(int i) {

	}
}
