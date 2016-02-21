package de.berufsschule_freising.pacasus.model.game;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;

import java.io.IOException;
import java.io.InputStream;

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

	public Pacman(Point initialPosition, AssetManager am, Map map){
		this();

		this.setMap(map);
		this.setAssetManager(am);

		this.setSpeed(map.getGridUnitLength() / 4);

		InputStream spriteSheetInputStream;
		Bitmap spriteSheet = null;
		try {
			spriteSheetInputStream = this.getAssetManager().open("pacman_characters.png");
			spriteSheet = BitmapFactory.decodeStream(spriteSheetInputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.stayAnimation = new Animation(spriteSheet);
		this.stayAnimation.setColumns(8);
		this.stayAnimation.setRows(8);
		this.stayAnimation.setEndFrame(0);
		this.stayAnimation.setStartFrame(0);

		this.setCurrentAnimation(stayAnimation);

		this.runAnimation = new Animation(spriteSheet);
		this.runAnimation.setColumns(8);
		this.runAnimation.setRows(8);
		this.runAnimation.setEndFrame(4);
		this.runAnimation.setStartFrame(0);

		int frameWidth = this.runAnimation.getFrameWidth();
		this.setScaleFactor(this.getMap().getGridUnitLength() / frameWidth);
				// 100 (pacframe ) = 302px
				// x 			 = gridunitlegnt

		//this.setResources(resources);

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
