package de.berufsschule_freising.pacasus.model.game;

import android.graphics.Canvas;
import android.graphics.PointF;

/**
 * Created by Julian on 21.10.2015.
 */
public abstract class Actor implements IActor {
	private int id;
	private String name;
	private Canvas canvas;

	protected PointF position;
	private DirectionType direction;

	public Actor()
	{
		this.id = 0;
		this.name = "";

		this.position = new PointF(0, 0);
	}

	public Actor(String name, Canvas canvas)
	{
		this.name = name;
		this.canvas = canvas;
	}

	public Actor(int id, String name, Canvas canvas)
	{
		this(name, canvas);
		this.id = id;
	}

	// public abstract boolean intersects(Actor actor);

	public Canvas getCanvas(){
		return this.canvas;
	}

	public void setCanvas(Canvas canvas){
		this.canvas = canvas;
	}

	public PointF getPosition(){
		return this.position;
	}

	public void setDirection(DirectionType dir){
		this.direction = dir;
	}

	public DirectionType getDirection(){
		return this.direction;
	}
	
	@Override
	public abstract void clear();

	public abstract void move();

	public abstract void render();
}
