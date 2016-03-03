package de.berufsschule_freising.pacasus.model.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.VelocityTracker;
import android.view.View;

import java.util.Collection;

import de.berufsschule_freising.pacasus.CanvasView;

/**
 * Created by Julian on 29.02.2016.
 */
public class RenderingThread extends Thread {

	private boolean isRunning = true;
	private SurfaceHolder surfaceHolder;
	private Engine engine;
	private CanvasView view;

	private static final int DELAY = 10;

	public RenderingThread(SurfaceHolder surfaceHolder, Engine engine, CanvasView view){
		super();

		this.surfaceHolder = surfaceHolder;
		this.engine = engine;
		this.view = view;
	}

	@Override
	public void run(){
		Paint p = new Paint();
		p.setColor(Color.RED);
		p.setStyle(Paint.Style.FILL_AND_STROKE);

		while (this.isRunning){
			long begin = System.currentTimeMillis() / 1000;

			if (!this.surfaceHolder.getSurface().isValid()){
				continue;
			}

			this.engine.update();

			Canvas canvas = this.surfaceHolder.lockCanvas();

			if (canvas != null){
				synchronized (this.surfaceHolder){
					this.engine.render(canvas);
				}
				this.surfaceHolder.unlockCanvasAndPost(canvas);
			}

			try {
				long end = System.currentTimeMillis() / 1000;
				this.sleep(this.DELAY - (end - begin));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


	public boolean isRunning() {
		return isRunning;
	}

	public void setIsRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
}
