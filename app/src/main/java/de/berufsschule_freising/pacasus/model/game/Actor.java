package de.berufsschule_freising.pacasus.model.game;

import android.graphics.Canvas;

import java.sql.Date;

/**
 * Created by Julian on 21.10.2015.
 */
public abstract class Actor implements IDrawable{
	private int id;
	private String name;
	private Canvas canvas;

	public Actor()
	{
		this.id = 0;
		this.name = "";
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

	@Override
	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	@Override
	public Canvas getCanvas() {
		return this.canvas;
	}

	@Override
	public abstract void render();

	@Override
	public abstract void clear();
}
