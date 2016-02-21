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

	private Animation stayAnimation;
	private Animation runAnimation;
	private Animation dieAnimation;

	public Pacman(){
		super();

		this.setDirection(DirectionType.None);

		this.paint = new Paint();
		this.paint.setStyle(Paint.Style.FILL);
		this.paint.setColor(Color.YELLOW);
	}

	public Pacman(Point initialPosition, Resources resources, Map map){
		this();

		this.setMap(map);

		this.setSpeed(map.getGridUnitLength() / 4);

		this.stayAnimation = new Animation(resources, R.drawable.pacman_characters);
		this.stayAnimation.setColumns(8);
		this.stayAnimation.setRows(8);
		this.stayAnimation.setEndFrame(0);
		this.stayAnimation.setStartFrame(0);

		this.setCurrentAnimation(stayAnimation);

		this.runAnimation = new Animation(resources, R.drawable.pacman_characters);
		this.runAnimation.setColumns(8);
		this.runAnimation.setRows(8);
		this.runAnimation.setEndFrame(4);
		this.runAnimation.setStartFrame(0);

		int frameWidth = this.runAnimation.getFrameWidth();
		this.setScaleFactor(this.getMap().getGridUnitLength() / frameWidth);
				// 100 (pacframe ) = 302px
				// x 			 = gridunitlegnt

		this.setResources(resources);

		this.setInitialPosition(initialPosition);
	}


	@Override
	public void move() {

		if (this.canWalk(this.getNextDirection()) && this.getNextDirection() != DirectionType.None) {// Kann in die nächste, angegebene Richtung laufen?

			// Richtungen aktualisieren
			this.setDirection(this.getNextDirection());
			this.setNextDirection(DirectionType.None);

			this.modifyPosition();

		} else if(this.canWalk(this.getDirection())){ // Kann in die aktulle Richtung laufen?
			this.modifyPosition();
		} else { // Stop
			this.setDirection(DirectionType.None);
			this.setCurrentAnimation(this.stayAnimation);
	}
	}

	private void modifyPosition(){
		switch (this.getDirection()) {
			case Down:
				this.setCurrentAnimation(this.runAnimation);
				this.getPosition().y += this.getSpeed();
				break;
			case Up:
				this.setCurrentAnimation(this.runAnimation);
				this.getPosition().y -= this.getSpeed();
				break;
			case Right:
				this.setCurrentAnimation(this.runAnimation);
				this.getPosition().x += this.getSpeed();
				break;
			case Left:
				this.setCurrentAnimation(this.runAnimation);
				this.getPosition().x -= this.getSpeed();
				break;
			case None:
				this.setCurrentAnimation(this.stayAnimation);
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
		tmp = this.getMap().getCharAtPoint(mapPos);
		Log.w("Map", String.valueOf(tmp));
		if (Character.isWhitespace(tmp) ||
				tmp == '*' ||
				tmp == 'p'){


			return true;
		}
		return false;
	}

	private Point getMapPosition(){
		Log.w("Pacman", String.valueOf(this.getPosition().x));
		Log.w("Pacman", String.valueOf(this.getPosition().y));

		float x = this.getPosition().x / (this.getMap().getGridUnitLength());
		float y = this.getPosition().y / (this.getMap().getGridUnitLength());

		return new Point((int)x, (int)y);
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

		this.getCurrentAnimation().setScaleHeight(this.getMap().getGridUnitLength());
		this.getCurrentAnimation().setScaleWidth(this.getMap().getGridUnitLength());
		this.getCurrentAnimation().setRotation((this.getDirection().ordinal() - 1) * 90);

		// Get Animationframe
		Bitmap frame = this.getCurrentAnimation().createBitmapFrame();

		// Erstes zeichnen; Kartenposition setzen
		if (this.getPosition().x == 0){
			this.setMapPosition(this.getInitialPosition());
		}

		canvas.drawBitmap(frame, this.getPosition().x, this.getPosition().y + 200, null);

		this.move();
	}

}
