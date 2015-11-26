package de.berufsschule_freising.pacasus;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;

import de.berufsschule_freising.pacasus.model.game.DirectionType;
import de.berufsschule_freising.pacasus.model.game.Pacman;

/**
 * Created by Gabriel on 22.10.2015.
 */
public class CanvasView extends View{

	private Pacman pac;

	private PointF startTouchPoint;

	public CanvasView(Context context)
	{
		super(context);
		//TODO constructor


		pac = new Pacman(new PointF(this.getWidth() / 2, this.getHeight() / 2));
	}

	public boolean onTouchEvent(MotionEvent ev){
		float x = ev.getX();
		float y = ev.getY();

		switch (ev.getAction()){
			case MotionEvent.ACTION_DOWN :
				this.startTouchPoint = new PointF(ev.getX(), ev.getY());
				break;
			case MotionEvent.ACTION_UP :
				// Right/Left
				if (Math.abs(x - this.startTouchPoint.x) > Math.abs(y - this.startTouchPoint.y)){
					// Right
					if (x > this.startTouchPoint.x){
						this.pac.setDirection(DirectionType.right);
					} else { // Left
						this.pac.setDirection(DirectionType.left);
					}
				} else { // Up/Down
					// Up
					if (y < this.startTouchPoint.y){
						this.pac.setDirection(DirectionType.up);
					} else { // Down
						this.pac.setDirection(DirectionType.down);
					}
				}
				break;
		}
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// TODO: Bessere Lösung für Gameloop finden
		pac.setCanvas(canvas);
		pac.render();


		try {
			Thread.sleep(100);
			this.invalidate();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
