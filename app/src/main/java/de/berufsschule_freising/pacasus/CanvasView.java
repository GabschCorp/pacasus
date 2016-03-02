package de.berufsschule_freising.pacasus;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.berufsschule_freising.pacasus.model.game.DirectionType;
import de.berufsschule_freising.pacasus.model.game.Engine;
import de.berufsschule_freising.pacasus.model.game.GameState;
import de.berufsschule_freising.pacasus.model.game.Ghost;
import de.berufsschule_freising.pacasus.model.game.GhostFactory;
import de.berufsschule_freising.pacasus.model.game.Map;
import de.berufsschule_freising.pacasus.model.game.Pacman;
import de.berufsschule_freising.pacasus.model.game.RenderingThread;

/**
 * Created by Gabriel on 22.10.2015
 */
// GestureDetector.OnGestureListener,
public class CanvasView extends SurfaceView implements GestureDetector.OnGestureListener, SurfaceHolder.Callback {

	private GestureDetector gestureDetector;
	private Engine engine;
	private RenderingThread renderingThread;
	private SurfaceHolder surfaceHolder;

	private Paint paint;

	public CanvasView(Activity context)
	{
		super(context);
		this.gestureDetector = new GestureDetector(context, this);
		this.engine = new Engine(context.getAssets());
		gestureDetector = new GestureDetector(context, this);

		this.initView();
		this.paint = new Paint();
		this.paint.setColor(Color.BLACK);
		this.paint.setStyle(Paint.Style.FILL_AND_STROKE);
	}

	//initialization code
	void initView(){
		//initialize our screen holder
		this.surfaceHolder = getHolder();
		this.surfaceHolder.addCallback(this);

		//initialize our game engine
		this.engine = new Engine(getContext().getAssets());

		//initialize our Thread class. A call will be made to start it later
		this.renderingThread = new RenderingThread(this.surfaceHolder, this.engine, this);
		this.setFocusable(true);
	}


	public void surfaceCreated(SurfaceHolder surfaceHolder){
		Log.w("CanvasView", "surfaceCreated");
		//this.renderingThread = new RenderingThread(this.getHolder(), this.engine, this);
		this.renderingThread.setIsRunning(true);
		this.renderingThread.start();
	}

	public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height){
		Log.w("CanvasView", "surfaceChanged");

	}

	public void surfaceDestroyed(SurfaceHolder surfaceHolder){
		Log.w("CanvasView", "surfaceDestroyed");

		boolean retry = true;
		while (retry){
			try {
				this.renderingThread.join();
				retry = false;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


	public boolean onTouchEvent(MotionEvent ev){
		gestureDetector.onTouchEvent(ev);

		return true;
	}

//
//		try {
//			Thread.sleep(30);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		this.invalidate();
//	}

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
