package chronikspartan.cutecatsplat;

/**
 * Created by Chronik Spartan on 26/02/2017.
 */


public interface AdsController {
    public void showBannerAd();
    public void hideBannerAd();
    public boolean isWifiConnected();
    public void showInterstitialAd (Runnable then);
}
