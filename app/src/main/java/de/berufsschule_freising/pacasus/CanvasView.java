package de.berufsschule_freising.pacasus;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by Gabriel on 22.10.2015.
 */
public class CanvasView extends View{

	public CanvasView(Context context)
	{
		super(context);
		//TODO constructor
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int height = getHeight();
		int width = getWidth();
		int radius = 100;
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.BLACK);
		canvas.drawPaint(paint);
		paint.setColor(Color.BLUE);
		canvas.drawCircle(height / 2, width / 2, radius, paint);
	}
}
