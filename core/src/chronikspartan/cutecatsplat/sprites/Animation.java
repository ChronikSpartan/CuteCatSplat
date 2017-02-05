package chronikspartan.cutecatsplat.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Created by cube on 1/21/2017.
 */

public class Animation {
    private Array<TextureRegion> frames;
    private float maxFrameTime;
    private float currentFrameTime;
    private int frameCount;
    private int frame;
	private boolean finalFrame = false;

    public Animation(TextureRegion region, int frameCount, float cycleTime){
        frames = new Array<TextureRegion>();
        int frameWidth = region.getRegionWidth() / frameCount;
        for (int i = 0; i < frameCount; i++)
            frames.add(new TextureRegion(region, i * frameWidth, 0, frameWidth, region.getRegionHeight()));
        this.frameCount = frameCount;
        maxFrameTime = cycleTime / frameCount;
        frame = 0;
    }

    public void update(float dt){
        currentFrameTime += dt;
        if(currentFrameTime > maxFrameTime) {
            frame++;
            currentFrameTime = 0;
        }

        if((frame >= frameCount) && !finalFrame)
            frame = 0;
		else if(finalFrame)
			frame = frameCount - 1;
    }

    public TextureRegion getFrame(){
        return frames.get(frame);
    }
	
	public int getFrameNumber(){
		return frame;
	}
	
	public void callFinalFrame(){
		finalFrame = true;
	}

}
