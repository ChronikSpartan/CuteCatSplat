package chronikspartan.cutecatsplat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class CreateButton
{
	public Button NewButton(Texture image, Texture imageDepressed){
		Button.ButtonStyle btnStyle = new Button.ButtonStyle();
        btnStyle.up = new TextureRegionDrawable(new TextureRegion(image));
        btnStyle.down = new TextureRegionDrawable(new TextureRegion(imageDepressed));
        return new Button(btnStyle);
	}
}
