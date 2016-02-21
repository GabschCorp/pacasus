package de.berufsschule_freising.pacasus.model.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;

/**
 * Created by Jooly on 21.02.2016.
 */
public class Dot extends AbstractPoint implements IDrawable{

	public Dot(Map map, Point mapPosition){
		super(map, mapPosition);

		this.getPaint().setStyle(Paint.Style.STROKE);
		this.getPaint().setStrokeWidth(2);
		this.getPaint().setColor(Color.WHITE);
	}

	@Override
	public void render() {

		PointF drawingPosition = this.getPositionByMapPosition();

		this.getCanvas().drawArc(drawingPosition.x - 2, drawingPosition.y - 2, drawingPosition.x + 2, drawingPosition.y + 2, 0, 360, true, this.getPaint());
	}

	@Override
	public void clear() {

	}
}
