package de.berufsschule_freising.pacasus.model.game;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;

/**
 * Created by Julian on 21.10.2015.
 */
public abstract class Actor extends Drawable implements IActor {

	private int id;
	private String name;
	private Canvas canvas;

	private Point initialPosition;
	private PointF position = new PointF();

	private DirectionType direction = DirectionType.None;
	private DirectionType nextDirection = DirectionType.None;

	private AssetManager assetManager;

	private float speed = 10;

	private Resources resources;

	private Animation currentAnimation;

	private Map map;
	private float scaleFactor;

	public Actor(AssetManager am) {
		super(am);
	}

	public boolean isIntersect(Actor actor){
		// Differenz(-Vector) kleiner/gleich einer Längeneinheit
		if (Math.abs(this.getPosition().x - actor.getPosition().x) <= this.getMap().getGridUnitLength() &&
				Math.abs(this.getPosition().y  - actor.getPosition().y) <= this.getMap().getGridUnitLength()){
			return true;
		}

		return false;
	}

	// public abstract boolean isIntersect(Actor actor);


	public boolean canWalk(DirectionType dir){
		Point mapPos = this.getMapPosition();

		if (DirectionType.Up == dir){
			mapPos.y--;
		}
		else if (DirectionType.Down == dir){
			mapPos.y++;
		}
		else if (DirectionType.Right == dir){
			mapPos.x++;
		}
		else if (DirectionType.Left == dir){
			mapPos.x--;
		}

		// TODO: Richtung plus/minus 1
		char tmp;
		tmp = this.getMap().getCharAtPoint(mapPos);
		if (Character.isWhitespace(tmp) ||
				tmp == '*' ||
				tmp == 'p'){


			return true;
		}
		return false;
	}
	
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

	public void setMapPosition(Point pos){
		this.setPosition(new PointF(pos.x * this.map.getGridUnitLength(), pos.y * this.map.getGridUnitLength()));
	}

	public Point getMapPosition(){
		float x = this.getPosition().x / this.getMap().getGridUnitLength();
		float y = this.getPosition().y / this.getMap().getGridUnitLength();

		return new Point((int)Math.floor(x), (int)Math.floor(y));
	}

	private void setPosition(PointF pos){
		this.position = pos;
	}

	public void setDirection(DirectionType dir){
		this.direction = dir;
	}

	public Point getInitialPosition() {
		return initialPosition;
	}

	public void setInitialPosition(Point initialPosition) {
		this.initialPosition = initialPosition;
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}

	public void setAssetManager(AssetManager assetManager) {
		this.assetManager = assetManager;
	}

	public void addDirection(DirectionType dir){
		if (this.getDirection() == DirectionType.None){
			this.setDirection(dir);
		} else {
			this.nextDirection = dir;
		}
	}

	public DirectionType getNextDirection(){
		return this.nextDirection;
	}

	public void setNextDirection(DirectionType dir){
		this.nextDirection = dir;
	}

}
