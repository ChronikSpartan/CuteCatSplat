package chronikspartan.cutecatsplat;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureAdapter;
import com.badlogic.gdx.input.GestureDetector;

public class MyGestureDetector extends GestureDetector {
	public interface DirectionListener {
		void onLeft(float deltaX);

		void onRight(float deltaX);

		void onUp(float velocityY);

		void onDown(float velocityY);
	}

	public MyGestureDetector(DirectionListener directionListener) {
		super(new DirectionGestureListener(directionListener));
	}

	private static class DirectionGestureListener extends GestureAdapter{
		DirectionListener directionListener;

		public DirectionGestureListener(DirectionListener directionListener){
			this.directionListener = directionListener;
		}

		@Override
		public boolean pan(float x, float y, float deltaX, float deltaY)
		{
			if(deltaX>0){
				directionListener.onRight(deltaX);
			}else{
				directionListener.onLeft(deltaX);
			}
			return super.pan(x, y, deltaX, deltaY);
		}
/*
		@Override
		public boolean fling(float velocityX, float velocityY, int button) {
			if(Math.abs(velocityX)>Math.abs(velocityY)){
				if(velocityX>0){
                    directionListener.onRight(velocityX);
				}else{
                    directionListener.onLeft(velocityX);
				}
			}else{
				if(velocityY>0){
                    directionListener.onDown(velocityY);
				}else{                                  
                    directionListener.onUp(velocityY);
				}
			}
			return super.fling(velocityX, velocityY, button);
		}
		*/
	}
}
