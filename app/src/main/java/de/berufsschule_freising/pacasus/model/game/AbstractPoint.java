package de.berufsschule_freising.pacasus.model.game;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.Log;

/**
 * Created by Jooly on 21.02.2016.
 */
public abstract class AbstractPoint extends Drawable{

	private Paint paint = new Paint();

	private Point mapPosition;
	private Canvas canvas;

	private boolean isEaten = false;

	private Map map;

	public AbstractPoint(Map map, Point mapPosition, AssetManager am){
		super(am);

		this.setMap(map);
		this.setMapPosition(mapPosition);
	}

	public PointF getPositionByMapPosition(){
		PointF position = new PointF();
		position.x = this.getMap().getGridUnitLength() * this.getMapPosition().x;
		position.y = this.getMap().getGridUnitLength() * this.getMapPosition().y;

		position.x -= this.getMap().getGridUnitLength() / 2;
		position.y -= this.getMap().getGridUnitLength() / 2;

		return position;
	}

	public void eat(){
		this.isEaten = true;

		this.getMap().removeDotFromList(this);
	}

	public Point getMapPosition() {
		return mapPosition;
	}

	public void setMapPosition(Point mapPosition) {
		this.mapPosition = mapPosition;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	public Canvas getCanvas() {
		return this.canvas;
	}

	public Paint getPaint() {
		return paint;
	}

	public boolean isEaten() {
		return isEaten;
	}
}
