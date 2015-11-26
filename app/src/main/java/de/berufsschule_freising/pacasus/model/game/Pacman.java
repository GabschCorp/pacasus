package de.berufsschule_freising.pacasus.model.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;

/**
 * Created by Julian on 21.10.2015.
 */

// TODO:
public class Pacman extends Actor {

	private Paint paint;

	private DirectionType direction;
	private float speed = 5;

	public static final int DIRECTION_NONE = 0;
	public static final int DIRECTION_UP = 1;
	public static final int DIRECTION_RIGHT = 2;
	public static final int DIRECTION_DOWN = 3;
	public static final int DIRECTION_LEFT = 4;


	public Pacman(){
		super();

		this.paint = new Paint();
		this.paint.setStyle(Paint.Style.FILL);
		this.paint.setColor(Color.YELLOW);
	}

	public Pacman(PointF initialPosition){
		this();

		this.position = initialPosition;
	}

	public void setDirection(DirectionType dir){
		this.direction = dir;
	}

	@Override
	public void move() {
		switch (this.direction) {
			case Down :
				this.getPosition().y += this.speed;
				break;
			case Up:
				this.getPosition().y -= this.speed;
				break;
			case Right :
				this.getPosition().x += this.speed;
				break;
			case Left :
				this.getPosition().x -= this.speed;
				break;
			case None :
				break;
		}
	}

	@Override
	public void clear() {
		getCanvas().drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
	}
	
	@Override
	public void render(){
		float length = Map.getGridUnitLength();
		Canvas c = this.getCanvas();

		// draw
		c.drawCircle(this.getPosition().x, this.getPosition().y, length, this.paint);
			
		this.move();
	}
}
