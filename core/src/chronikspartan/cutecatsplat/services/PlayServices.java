package chronikspartan.cutecatsplat.services;

/**
 * Created by Chronik Spartan on 24/04/2017.
 */

public interface PlayServices
{
    public void signIn();
    public void signOut();
    public void rateGame();
    public void unlockAchievement();
    public void submitScore(int highScore);
    public void showAchievement();
    public void showScore();
    public boolean isSignedIn();
}
