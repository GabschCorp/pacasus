package de.berufsschule_freising.pacasus;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import de.berufsschule_freising.pacasus.model.game.Pacman;

/**
 * Created by Gabriel on 22.10.2015.
 */
public class CanvasView extends View{

	private Pacman pac;

	public CanvasView(Context context)
	{
		super(context);
		//TODO constructor


		pac = new Pacman();
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
