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

	private Animation runAnimation;

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

		this.runAnimation = new Animation(resources, R.drawable.pacman_characters);
		this.runAnimation.setColumns(8);
		this.runAnimation.setRows(8);
		this.runAnimation.setEndFrame(4);
		this.runAnimation.setStartFrame(0);

		int frameWidth = this.runAnimation.getFrameWidth();
		this.scaleFactor = Map.getGridUnitLength() / frameWidth;
				// 100 (pacframe ) = 302px
				// x 			 = gridunitlegnt

		this.resources = resources;
		this.setMapPosition(initialPosition.x, initialPosition.y); // TsDO: prüfen
	}


	@Override
	public void move() {
		// TODO:
		if (this.canWalk(this.direction)) {

			switch (this.getDirection()) {
				case Down:
					this.getPosition().y += this.speed;
					break;
				case Up:
					this.getPosition().y -= this.speed;
					break;
				case Right:
					this.getPosition().x += this.speed;
					break;
				case Left:
					this.getPosition().x -= this.speed;
					break;
				case None:
					break;
			}
		}
	}

	public boolean canWalk(DirectionType dir){
		Point mapPos = this.computeMapPosition();

		if (DirectionType.Up == dir){
			mapPos.y--;
		}

		if (DirectionType.Down == dir){
			mapPos.y++;
		}
		if (DirectionType.Right == dir){
			mapPos.x++;
		}
		if (DirectionType.Left == dir){
			mapPos.y--;
		}

		// TODO: Richtung plus/minus 1
		if (Character.isWhitespace(this.map.getCharAtPoint(mapPos))){
			 return false;
		}

		return true;
	}

	private Point computeMapPosition(){
		float x = this.getPosition().x / this.map.getGridUnitLength();
		float y = this.getPosition().y / this.map.getGridUnitLength();

		return new Point((int) x, (int) y);
	}

	@Override
	public void clear() {
		this.getCanvas().drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
	}

	@Override
	public void render(){

		float length = Map.getGridUnitLength();
		Canvas canvas = this.getCanvas();
		Matrix oMat = new Matrix();
		oMat.setTranslate(0, 200);
		canvas.setMatrix(oMat);

		// Get Animationframe
		Bitmap frame = this.runAnimation.createBitmapFrame();

		// Set World Matrix
		canvas.scale(this.scaleFactor, this.scaleFactor);
		//canvas.translate(0, 200); // TODO:
		canvas.translate(this.getPosition().x, this.getPosition().y);
		canvas.rotate((this.getDirection().ordinal() - 1) * 90, frame.getWidth() / 2, frame.getHeight() /2);

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
