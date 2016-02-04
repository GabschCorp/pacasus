package de.berufsschule_freising.pacasus.model.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;

import java.util.*;
import java.util.HashMap;

import de.berufsschule_freising.pacasus.R;

/**
 * Created by Julian on 21.10.2015.
 */
public class Ghost extends Actor {

	private String name;

	private float speed = 10;

	private PointF position;

	private HashMap<DirectionType, Animation> animationMap;

	private Animation currentAnimation;
	private Animation rightAnimation;
	private Animation topAnimation;
	private Animation leftAnimation;
	private Animation downAnimation;

	public Ghost(){
//		this.paint = new Paint();
//		this.p
	}


//		Clyde Orange
//		Inky blau
//		Blinky Rot
//		Pinky Pinky
	public Ghost(String name, PointF initialPosition, Resources resouces) {
		this();

		this.name = name;
		this.position = initialPosition;
		this.setDirection(DirectionType.Down);

		this.animationMap = new HashMap<>();

		Animation anim = new Animation(resouces, R.drawable.pacman_characters);
		anim.setColumns(8);
		anim.setRows(8);
		anim.setStartFrame(36);
		anim.setEndFrame(38);

		this.animationMap.put(DirectionType.Down, anim);
//
//		anim = new Animation(resouces, R.drawable.pacman_characters);
//		anim.setColumns(8);
//		anim.setRows(8);
//		anim.setStartFrame(34);
//		anim.setEndFrame(36);
//
//		this.animationMap.put(DirectionType.Right, anim);
//
//
//		anim = new Animation(resouces, R.drawable.pacman_characters);
//		anim.setColumns(8);
//		anim.setRows(8);
//		anim.setStartFrame(32);
//		anim.setEndFrame(34);
//
//		this.animationMap.put(DirectionType.Up, anim);
//
//		anim = new Animation(resouces, R.drawable.pacman_characters);
//		anim.setColumns(8);
//		anim.setRows(8);
//		anim.setStartFrame(38);
//		anim.setEndFrame(40);
//
//		this.animationMap.put(DirectionType.Left, anim);
	}

	@Override
	public void render() {
		Canvas canvas = this.getCanvas();

		canvas.setMatrix(new Matrix());

		Bitmap frame = this.animationMap.get(this.getDirection()).createBitmapFrame();
		canvas.scale(0.2f, 0.2f);
		canvas.translate(this.getPosition().x, this.getPosition().y);

		canvas.drawBitmap(frame, 0, 0, null);

		this.move();
	}

	@Override
	public void clear() {
		this.getCanvas().drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
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
}
