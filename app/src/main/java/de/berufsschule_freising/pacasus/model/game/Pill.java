package de.berufsschule_freising.pacasus.model.game;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;

/**
 * Created by Jooly on 21.02.2016.
 */
public class Pill extends AbstractPoint implements IDrawable{

	public Pill(Map map, Point mapPosition){
		super(map, mapPosition);

		this.getPaint().setStyle(Paint.Style.STROKE);
		this.getPaint().setStrokeWidth(5);
		this.getPaint().setColor(Color.WHITE);
	}

	@Override
	public void render() {

		PointF drawingPosition = this.getPositionByMapPosition();

		this.getCanvas().drawArc(drawingPosition.x - 7, drawingPosition.y - 7, drawingPosition.x + 7, drawingPosition.y + 7, 0, 360, true, this.getPaint());
	}

	@Override
	public void clear() {

	}
}
