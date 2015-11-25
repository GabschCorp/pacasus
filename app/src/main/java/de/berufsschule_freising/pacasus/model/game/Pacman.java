package de.berufsschule_freising.pacasus.model.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

/**
 * Created by Julian on 21.10.2015.
 */

// TODO:
public class Pacman extends Actor {

	private Paint paint;

	private int direction;
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

	public void setDirection(int dir){
		this.direction = dir;
	}

	@Override
	public void move() {
		switch (this.direction) {
			case DIRECTION_DOWN :
				this.getPosition().y += this.speed;
				break;
			case DIRECTION_UP :
				this.getPosition().y -= this.speed;
				break;
			case DIRECTION_RIGHT :
				this.getPosition().x += this.speed;
				break;
			case DIRECTION_LEFT :
				this.getPosition().x -= this.speed;
				break;
			case DIRECTION_NONE :
				break;

		}
	}

	@Override
	public void clear() {

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
