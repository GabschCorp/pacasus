package de.berufsschule_freising.pacasus.model.game;

import android.graphics.Canvas;
import android.graphics.Color;
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

	private static final int DELAY = 30;

	public RenderingThread(SurfaceHolder surfaceHolder, Engine engine, CanvasView view){
		super();

		this.surfaceHolder = surfaceHolder;
		this.engine = engine;
		this.view = view;
	}

	@Override
	public void run(){
		while (this.isRunning){
			if (!this.surfaceHolder.getSurface().isValid()){
				continue;
			}

			//Log.w("RenderingThread", "isRunning");
			this.engine.update();

			Canvas canvas = this.surfaceHolder.lockCanvas(null);

			if (canvas != null){
				synchronized (this.surfaceHolder){
					Log.w("RenderingThread", "isRendering");
					canvas.drawColor(Color.CYAN);
				}
				this.surfaceHolder.unlockCanvasAndPost(canvas);
			}

			try {
				this.sleep(this.DELAY);
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
