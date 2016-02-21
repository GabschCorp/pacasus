package de.berufsschule_freising.pacasus.model.game;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;

/**
 * Created by Julian on 21.10.2015.
 */
public abstract class Actor implements IActor {

	private int id;
	private String name;
	private Canvas canvas;

	private Point initialPosition;
	private PointF position = new PointF();

	private DirectionType direction = DirectionType.None;
	private DirectionType nextDirection = DirectionType.None;

	private float speed = 10;

	private Resources resources;

	private Animation currentAnimation;

	private Map map;
	private float scaleFactor;

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

	// public abstract boolean intersects(Actor actor);

	
	@Override
	public abstract void clear();

	public abstract void move();

	public abstract void render();

	public Resources getResources() {
		return resources;
	}

	public void setResources(Resources resources) {
		this.resources = resources;
	}

	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public Animation getCurrentAnimation() {
		return currentAnimation;
	}

	public void setCurrentAnimation(Animation currentAnimation) {
		this.currentAnimation = currentAnimation;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getScaleFactor() {
		return scaleFactor;
	}

	public void setScaleFactor(float scaleFactor) {
		this.scaleFactor = scaleFactor;
	}

	public Canvas getCanvas(){
		return this.canvas;
	}

	public void setCanvas(Canvas canvas){
		this.canvas = canvas;
	}

	public PointF getPosition(){
		return this.position;
	}

	public DirectionType getDirection(){
		return this.direction;
	}

	public DirectionType getNextDirection(){
		return this.nextDirection;
	}

	public void setNextDirection(DirectionType dir){
		this.nextDirection = dir;
	}

	public void setMapPosition(Point pos){
		this.setPosition(new PointF(pos.x * this.map.getGridUnitLength(), pos.y * this.map.getGridUnitLength()));
	}

	private void setPosition(PointF pos){
		this.position = pos;
	}

	public void setDirection(DirectionType dir){
		this.direction = dir;
	}

	public void addDirection(DirectionType dir){
		if (this.direction == DirectionType.None){
			this.direction = dir;
		} else {
			this.nextDirection = dir;
		}
	}

	public Point getInitialPosition() {
		return initialPosition;
	}

	public void setInitialPosition(Point initialPosition) {
		this.initialPosition = initialPosition;
	}

}
