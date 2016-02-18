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

	//private PointF mapPosition; // Position in GridUnitLenghts innerhalb der Map; bin ich berechenbar??

	private DirectionType direction;
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

		int frameWidth = this.runAnimation.getFrameWidth();
		this.scaleFactor = this.map.getGridUnitLength() / frameWidth;
				// 100 (pacframe ) = 302px
				// x 			 = gridunitlegnt

		this.resources = resources;
		this.setMapPosition(initialPosition.x, initialPosition.y); // TsDO: prüfen
	}


	@Override
	public void move() {
		// TODO:
		if (this.canWalk(this.getDirection())) {
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
		} else {
			this.direction = DirectionType.None;
			this.currentAnimation = this.stayAnimation;
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

		return new Point((int)Math.ceil((double) x) -1, (int)Math.ceil((double) y) -1);
	}

	@Override
	public void clear() {
		this.getCanvas().drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
	}

	@Override
	public void render(){

		Canvas canvas = this.getCanvas();
		Matrix oMat = new Matrix();
		// oMat.setTranslate(0, 200);

		// Get Animationframe
		Bitmap frame = this.currentAnimation.createBitmapFrame();

		// Calc World Matrix
		oMat.setScale(this.scaleFactor, this.scaleFactor);
		oMat.setRotate((this.getDirection().ordinal() - 1) * 90, frame.getWidth() / 2, frame.getHeight() / 2);
		oMat.setTranslate(this.getPosition().x, this.getPosition().y);

		canvas.setMatrix(oMat);
		canvas.drawBitmap(frame, 0,0, null);

		this.move();
	}

	public void setMapPosition(int x, int y){
		this.setPosition(new PointF(x * this.map.getGridUnitLength(), y * this.map.getGridUnitLength()));
	}

	private void setPosition(PointF pos){
		this.position = pos;
	}
}
