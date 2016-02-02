package de.berufsschule_freising.pacasus.model.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;

import de.berufsschule_freising.pacasus.R;

/**
 * Created by Julian on 21.10.2015.
 */

// TODO:
public class Pacman extends Actor {

	private Paint paint;

	private DirectionType direction;
	private float speed = 50;

	private Resources resources;

	private Animation runAnimation;

	public Pacman(){
		super();

		this.direction = DirectionType.None;

		this.paint = new Paint();
		this.paint.setStyle(Paint.Style.FILL);
		this.paint.setColor(Color.YELLOW);
	}

	public Pacman(PointF initialPosition, Resources resources){
		this();

		this.runAnimation = new Animation(resources, R.drawable.pacman_characters);
		this.runAnimation.setColumns(8);
		this.runAnimation.setRows(8);
		this.runAnimation.setEndFrame(4);
			this.runAnimation.setStartFrame(0);

		this.resources = resources;
		this.position = initialPosition;
	}

	public void setDirection(DirectionType dir){
		this.direction = dir;
	}

	@Override
	public void move() {
		switch (this.direction) {
			case Down:
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

		// Get Animationframe
		Bitmap frame = this.runAnimation.createBitmapFrame();

		// Set World Matrix
		c.scale(0.2f, 0.2f);
		c.translate(this.getPosition().x, this.getPosition().y);
		c.rotate((this.direction.ordinal() - 1) * 90, frame.getWidth() / 2, frame.getHeight() /2);

		c.drawBitmap(frame, 0,0, null);

		this.move();
	}
}
