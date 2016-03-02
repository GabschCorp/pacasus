package de.berufsschule_freising.pacasus.model.game;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;

/**
 * Created by Jooly on 21.02.2016.
 */
public class Dot extends AbstractPoint {

	private Animation dotFrame;

	public Dot(Map map, Point mapPosition, AssetManager am){
		super(map, mapPosition, am);

		this.getPaint().setStyle(Paint.Style.STROKE);
		this.getPaint().setStrokeWidth(2);
		this.getPaint().setColor(Color.WHITE);

		this.dotFrame = new Animation(this.getSpritesheet());
		this.dotFrame.setColumns(8);
		this.dotFrame.setRows(8);
		this.dotFrame.setStartFrame(21);
		this.dotFrame.setEndFrame(22);
	}

	@Override
	public void render() {
		if (!this.isEaten()) {

			//PointF drawingPosition = this.getPositionByMapPosition();

			this.dotFrame.setScaleHeight(this.getMap().getGridUnitLength());
			this.dotFrame.setScaleWidth(this.getMap().getGridUnitLength());

			this.getCanvas().setMatrix(new Matrix());

			// TODO : vom Asset rendern
			this.getCanvas().drawBitmap(this.dotFrame.createBitmapFrame(),
					this.getPositionByMapPosition().x,
					this.getPositionByMapPosition().y + 200, null);

			//this.getCanvas().drawArc(drawingPosition.x - 7, drawingPosition.y - 7 + 200, drawingPosition.x + 7, drawingPosition.y + 7 + 200, 0, 360, true, this.getPaint());
		}
	}

	@Override
	public void clear() {

	}
}
