package de.berufsschule_freising.pacasus;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import de.berufsschule_freising.pacasus.model.game.Pacman;

/**
 * Created by Gabriel on 22.10.2015.
 */
public class CanvasView extends View implements GestureDetector.OnGestureListener{

	private GestureDetector gestureDetector;
	private Pacman pac;

	public CanvasView(Context context)
	{
		super(context);
		gestureDetector = new GestureDetector(context, this);
		//TODO constructor

		pac = new Pacman(new PointF(this.getWidth() / 2, this.getHeight() / 2));
	}

	public boolean onTouchEvent(MotionEvent ev){
		gestureDetector.onTouchEvent(ev);

		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// TODO: Bessere Lösung für Gameloop finden
		// Evtl mit GameTime
		pac.setCanvas(canvas);
		pac.render();

		try {
			Thread.sleep(20);
			this.invalidate();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
				this.pac.setDirection(Pacman.DIRECTION_RIGHT);
			} else {
				this.pac.setDirection(Pacman.DIRECTION_LEFT);
			}
		} else {
			if (distanceY < 0){
				this.pac.setDirection(Pacman.DIRECTION_DOWN);
			} else {
				this.pac.setDirection(Pacman.DIRECTION_UP);
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
