package de.berufsschule_freising.pacasus;

import android.app.Activity;
import android.graphics.Canvas;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import de.berufsschule_freising.pacasus.model.game.DirectionType;
import de.berufsschule_freising.pacasus.model.game.Engine;

/**
 * Created by Gabriel on 22.10.2015
 */
public class CanvasView extends View implements GestureDetector.OnGestureListener{

	private GestureDetector gestureDetector;

	private Engine engine;

	public CanvasView(Activity context)
	{
		super(context);
		this.gestureDetector = new GestureDetector(context, this);
		this.engine = new Engine(context.getAssets());
	}


	public boolean onTouchEvent(MotionEvent ev){
		gestureDetector.onTouchEvent(ev);

		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		this.engine.update();
		this.engine.render(canvas);

		try {
			Thread.sleep(30);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.invalidate();
}

	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		if (Math.abs(distanceX) > Math.abs(distanceY)){
			if (distanceX < 0){
				this.engine.getPacman().addDirection(DirectionType.Right);
			} else {
				this.engine.getPacman().addDirection(DirectionType.Left);
			}
		} else {
			if (distanceY < 0){
				this.engine.getPacman().addDirection(DirectionType.Down);
			} else {
				this.engine.getPacman().addDirection(DirectionType.Up);
			}
		}

		return true;
	}

	@Override
	public void onLongPress(MotionEvent e) {

	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		return false;
	}
}
