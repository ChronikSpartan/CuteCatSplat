package chronikspartan.cutecatsplat;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class ParallaxLayer{
    TextureRegion region ;
    Vector2 parallaxRatio;
    Vector2 startPosition;
    Vector2 padding ;
    public ParallaxLayer(TextureRegion region,Vector2 parallaxRatio,Vector2 padding){
        this(region, parallaxRatio, new Vector2(0,0),padding);
    }
    /**
     * @param region   the TextureRegion to draw , this can be any width/height
     * @param parallaxRatio   the relative speed of x,y {@link ParallaxBackground#ParallaxBackground(ParallaxLayer[], float, float, Vector2)}
     * @param startPosition the init position of x,y
     * @param padding  the padding of the region at x,y
     */
    private ParallaxLayer(TextureRegion region, Vector2 parallaxRatio, Vector2 startPosition, Vector2 padding){
        this.region  = region;
        this.parallaxRatio = parallaxRatio;
        this.startPosition = startPosition;
        this.padding = padding;
    }
}
