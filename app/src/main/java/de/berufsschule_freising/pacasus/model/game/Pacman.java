package de.berufsschule_freising.pacasus.model.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Julian on 21.10.2015.
 */
public class Pacman extends Actor {

	private Paint paint;

	public Pacman(){
		super();

		this.paint = new Paint();
		this.paint.setStyle(Paint.Style.FILL);
		this.paint.setColor(Color.YELLOW);
	}

	@Override
	public void move() {
		this.getPosition().x += 3;
		this.getPosition().y += 3;
	}

	public void render(){
		float length = Map.getGridUnitLength();
		Canvas c = this.getCanvas();

		// draw
		c.drawCircle(this.getPosition().x, this.getPosition().y, length, this.paint);

		//
		this.move();
	}
}
