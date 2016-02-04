package de.berufsschule_freising.pacasus.model.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
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
	private float speed = 10;

	private Resources resources;

	private Animation runAnimation;

	public Pacman(){
		super();

		this.setDirection(DirectionType.None);

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


	@Override
	public void move() {
		switch (this.getDirection()) {
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
		this.getCanvas().drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
	}

	@Override
	public void render(){

		float length = Map.getGridUnitLength();
		Canvas canvas = this.getCanvas();
		canvas.setMatrix(new Matrix());

		// Get Animationframe
		Bitmap frame = this.runAnimation.createBitmapFrame();

		// Set World Matrix
		canvas.scale(0.2f, 0.2f);
		canvas.translate(this.getPosition().x, this.getPosition().y);
		canvas.rotate((this.getDirection().ordinal() - 1) * 90, frame.getWidth() / 2, frame.getHeight() /2);

		canvas.drawBitmap(frame, 0,0, null);

		this.move();
	}
}
