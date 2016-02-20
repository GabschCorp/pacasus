package de.berufsschule_freising.pacasus.model.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.util.Log;

import de.berufsschule_freising.pacasus.R;

/**
 * Created by Julian on 21.10.2015.
 */

// TODO:
public class Pacman extends Actor {

	private Paint paint;

	// initialMapPosition;
	private Point initialPosition;

	//private PointF mapPosition; // Position in GridUnitLenghts innerhalb der Map; bin ich berechenbar??

	private DirectionType direction = DirectionType.None;
	private DirectionType nextDirection = DirectionType.None;
	private float speed = 10;

	private Resources resources;

	private Animation currentAnimation;
	private Animation stayAnimation;
	private Animation runAnimation;
	private Animation dieAnimation;

	private Map map;
	private float scaleFactor;

	public Pacman(){
		super();

		this.setDirection(DirectionType.None);

		this.paint = new Paint();
		this.paint.setStyle(Paint.Style.FILL);
		this.paint.setColor(Color.YELLOW);
	}



	public Pacman(Point initialPosition, Resources resources, Map map){
		this();

		this.map = map;

		this.stayAnimation = new Animation(resources, R.drawable.pacman_characters);
		this.stayAnimation.setColumns(8);
		this.stayAnimation.setRows(8);
		this.stayAnimation.setEndFrame(0);
		this.stayAnimation.setStartFrame(0);

		this.currentAnimation = stayAnimation;

		this.runAnimation = new Animation(resources, R.drawable.pacman_characters);
		this.runAnimation.setColumns(8);
		this.runAnimation.setRows(8);
		this.runAnimation.setEndFrame(4);
		this.runAnimation.setStartFrame(0);

		// Scalings for Animation
		this.stayAnimation.setScaleHeight(this.map.getGridUnitLength());
		this.stayAnimation.setScaleWidth(this.map.getGridUnitLength());
		this.runAnimation.setScaleHeight(this.map.getGridUnitLength());
		this.runAnimation.setScaleWidth(this.map.getGridUnitLength());

		int frameWidth = this.runAnimation.getFrameWidth();
		this.scaleFactor = this.map.getGridUnitLength() / frameWidth;
				// 100 (pacframe ) = 302px
				// x 			 = gridunitlegnt

		this.resources = resources;

		this.initialPosition = initialPosition;
	}


	@Override
	public void move() {

		if (this.canWalk(this.getNextDirection()) && this.getNextDirection() != DirectionType.None) {// Kann in die nächste, angegebene Richtung laufen?

			// Richtungen aktualisieren
			this.direction = this.getNextDirection();
			this.setDirection(DirectionType.None);

			this.modifyPosition();

		} else if(this.canWalk(this.getDirection())){ // Kann in die aktulle Richtung laufen?
			this.modifyPosition();
		} else { // Stop
			this.direction = DirectionType.None;
			this.currentAnimation = this.stayAnimation;
	}
	}

	private void modifyPosition(){
		switch (this.getDirection()) {
			case Down:
				this.currentAnimation = this.runAnimation;
				this.getPosition().y += this.speed;
				break;
			case Up:
				this.currentAnimation = this.runAnimation;
				this.getPosition().y -= this.speed;
				break;
			case Right:
				this.currentAnimation = this.runAnimation;
				this.getPosition().x += this.speed;
				break;
			case Left:
				this.currentAnimation = this.runAnimation;
				this.getPosition().x -= this.speed;
				break;
			case None:
				this.currentAnimation = this.stayAnimation;
				break;
		}
	}

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
		tmp = this.map.getCharAtPoint(mapPos);
		Log.w("Map", String.valueOf(tmp));
		if (Character.isWhitespace(tmp)){

			return true;
		}
		return false;
	}

	private Point getMapPosition(){
		Log.w("Pacman", String.valueOf(this.getPosition().x));
		Log.w("Pacman", String.valueOf(this.getPosition().y));

		float x = this.getPosition().x / (this.map.getGridUnitLength());
		float y = this.getPosition().y / (this.map.getGridUnitLength());

		return new Point((int)Math.ceil((double) x), (int)Math.ceil((double) y));
	}

	@Override
	public void clear() {
		this.getCanvas().drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
	}

	@Override
	public void render(){

		Canvas canvas = this.getCanvas();
		Matrix oMat = new Matrix();
		canvas.setMatrix(oMat);

		// Get Animationframe
		this.currentAnimation.setRotation((this.getDirection().ordinal() - 1) * 90);
		Bitmap frame = this.currentAnimation.createBitmapFrame();

		// Erstes zeichnen; Kartenposition setzen
		if (this.getPosition().x == 0){
			this.setMapPosition(this.initialPosition);
		}

		canvas.drawBitmap(frame, this.getPosition().x, this.getPosition().y +200, null);

		this.move();
	}

	public void setMapPosition(Point pos){
		this.setPosition(new PointF(pos.x * this.map.getGridUnitLength(), pos.y * this.map.getGridUnitLength()));
	}

	private void setPosition(PointF pos){
		this.position = pos;
	}

	public void setDirection(DirectionType dir){
		if (this.direction == DirectionType.None){
			this.direction = dir;
		} else {
			this.nextDirection = dir;
		}
	}

	public DirectionType getDirection(){
		return this.direction;
	}

	public DirectionType getNextDirection(){
		return this.nextDirection;
	}
}
