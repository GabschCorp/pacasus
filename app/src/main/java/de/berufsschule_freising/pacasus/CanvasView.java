package de.berufsschule_freising.pacasus;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.io.IOException;

import de.berufsschule_freising.pacasus.model.game.DirectionType;
import de.berufsschule_freising.pacasus.model.game.Ghost;
import de.berufsschule_freising.pacasus.model.game.GhostFactory;
import de.berufsschule_freising.pacasus.model.game.Map;
import de.berufsschule_freising.pacasus.model.game.Pacman;

/**
 * Created by Gabriel on 22.10.2015.
 */
public class CanvasView extends View implements GestureDetector.OnGestureListener{

	private GestureDetector gestureDetector;
	private Pacman pac;
	private Ghost blinky;
	private Ghost inky;
	private Ghost clyde;
	private Ghost pinky;

	private Map map;

	public CanvasView(Activity context)
	{
		super(context);
		gestureDetector = new GestureDetector(context, this);
		//TODO constructor


		try {
			this.map = new Map(context.getAssets());
			this.map.parse();
		} catch (IOException ex){

		} catch (Exception e) {
			e.printStackTrace();
		}

		pac = new Pacman(new Point(1,1), context.getAssets(), this.map);

		this.blinky = GhostFactory.createBlinky(this.map, new Point(11, 16),context.getAssets());
		this.clyde = GhostFactory.createClyde(this.map, new Point(11, 16), context.getAssets());
		this.inky = GhostFactory.createInky(this.map, new Point(11, 16), context.getAssets());
		this.pinky = GhostFactory.createPinky(this.map, new Point(11, 16), context.getAssets());
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
		this.map.setCanvas(canvas);
		this.pac.setCanvas(canvas);
		this.blinky.setCanvas(canvas);
		this.clyde.setCanvas(canvas);
		this.inky.setCanvas(canvas);
		this.pinky.setCanvas(canvas);

		this.map.render();
		this.blinky.render();
		this.clyde.render();
		this.inky.render();
		this.pinky.render();
		this.pac.render();

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
				this.pac.addDirection(DirectionType.Right);
			} else {
				this.pac.addDirection(DirectionType.Left);
			}
		} else {
			if (distanceY < 0){
				this.pac.addDirection(DirectionType.Down);
			} else {
				this.pac.addDirection(DirectionType.Up);
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
