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
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

import events.Event;
import events.EventArgs;
import events.IEvent;

/**
 * Created by Julian on 21.10.2015.
 */

// TODO:
public class Pacman extends Actor {

	public final IEvent<PacmanEventArgs> PacmanEatsPill = new Event<>();

	// Game
	private int lives;
	private int points;

	// TODO: Zahlen eruieren
	private final static int POINTS_DOT =  10;
	private final static int POINTS_PILL = 100;
	private final static int POINTS_GHOST = 1000;


	private Paint paint;

	// initialMapPosition;

	private Animation stayAnimation;
	private Animation runAnimation;
	private Animation dieAnimation;

	public Pacman(Point initialPosition, AssetManager am, Map map){
		super(am);

		this.lives = 3;
		this.points = 0;

		this.setDirection(DirectionType.None);

		this.paint = new Paint();
		this.paint.setStyle(Paint.Style.FILL);
		this.paint.setColor(Color.YELLOW);

		this.setMap(map);

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

		this.dieAnimation = new Animation(spriteSheet);
		this.dieAnimation.setColumns(8);
		this.dieAnimation.setRows(8);
		this.dieAnimation.setEndFrame(24);
		this.dieAnimation.setStartFrame(20);

		int frameWidth = this.runAnimation.getFrameWidth();
		this.setScaleFactor(this.getMap().getGridUnitLength() / frameWidth);

		this.setInitialPosition(initialPosition);
	}

	public void eatGhost(Ghost ghost){
		ghost.setMapPosition(ghost.getInitialPosition());
		ghost.setIsEatable(false);
		this.points += Pacman.POINTS_GHOST;
	}

	public void eatDot(AbstractPoint dot){
		dot.eat();

		if (dot instanceof Pill){
			this.points += Pacman.POINTS_PILL;
		} else {
			this.points += Pacman.POINTS_DOT;
		}
	}

	public void die(){
		this.lives--;

		this.setCurrentAnimation(this.dieAnimation);
		this.setDirection(DirectionType.None);

		// stop game
		// run die animation
		// reset game
		//
	}

	@Override
	public void move() {
		// pillen essen
		AbstractPoint dot;
		if ((dot = this.getMap().getEatablePointByPosition(this.getMapPosition())) != null){
			if (dot instanceof Pill){

				PacmanEventArgs args = new PacmanEventArgs();
				this.PacmanEatsPill.fire(this, args);
			}
			dot.eat();
		}

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

	// TODO: EatPillEvent

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

		canvas.drawBitmap(frame, this.getPosition().x, this.getPosition().y, null);

		//this.move();
	}

}
