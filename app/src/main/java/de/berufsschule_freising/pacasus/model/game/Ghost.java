package de.berufsschule_freising.pacasus.model.game;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.HashMap;

import de.berufsschule_freising.pacasus.model.game.*;

/**
 * Created by Julian on 21.10.2015.
 */
public class Ghost extends Actor {

	private HashMap<DirectionType, Animation> animationMap;

	private Animation rightAnimation;

	private Animation topAnimation;
	private Animation leftAnimation;
	private Animation downAnimation;

	public Ghost(){
//		this.paint = new Paint();
//		this.p
	}


//		Clyde Orange
//		Inky blau
//		Blinky Rot
//		Pinky Pinky
	public Ghost(String name, Point initialPosition, de.berufsschule_freising.pacasus.model.game.Map map, AssetManager am) {
		this();

		this.setName(name);
		this.setMap(map);
		this.setInitialPosition(initialPosition);
		this.setAssetManager(am);

		this.setDirection(DirectionType.Right);
		this.setNextDirection(DirectionType.Left);

		this.animationMap = new HashMap<>();
	}

	@Override
	public void render() {
		Canvas canvas = this.getCanvas();

		canvas.setMatrix(new Matrix());

		this.animationMap.get(this.getDirection()).setScaleHeight(this.getMap().getGridUnitLength());
		this.animationMap.get(this.getDirection()).setScaleWidth(this.getMap().getGridUnitLength());

		Bitmap frame = this.animationMap.get(this.getDirection()).createBitmapFrame();

		// Erstes zeichnen; Kartenposition setzen
		if (this.getPosition().x == 0){
			this.setMapPosition(this.getInitialPosition());
		}

		canvas.drawBitmap(frame, this.getPosition().x, this.getPosition().y + 200, null);

		this.move();
	}

	@Override
	public void clear() {
		this.getCanvas().drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
	}

	@Override
	public void move() {
		if (this.canWalk(this.getNextDirection())) {// Kann in die nächste, angegebene Richtung laufen?

			// Richtungen aktualisieren
			this.setDirection(this.getNextDirection());
			Random random = new Random();
			int randomNum = random.nextInt((4 - 1) + 1) + 1;
			this.setNextDirection(DirectionType.fromOrdinal(randomNum));

			this.modifyPosition();

		} else if(this.canWalk(this.getDirection())){ // Kann in die aktulle Richtung laufen?
			this.modifyPosition();
		} else {
			Random random = new Random();
			int randomNum = random.nextInt((4 - 1) + 1) + 1;
			this.setDirection(DirectionType.fromOrdinal(randomNum));
			randomNum = random.nextInt((4 - 1) + 1) + 1;
			this.setNextDirection(DirectionType.fromOrdinal(randomNum));
			//this.move();
		}
	}

	public void modifyPosition() {
		switch (this.getDirection()) {
			case Down:
				this.getPosition().y += this.getSpeed();
				break;
			case Up:
				this.getPosition().y -= this.getSpeed();
			break;
			case Right :
				this.getPosition().x += this.getSpeed();
				break;
			case Left :
				this.getPosition().x -= this.getSpeed();
				break;
			case None :
				break;
		}
	}

	public Animation getDownAnimation() {
		return downAnimation;
	}

	public void setDownAnimation(Animation downAnimation) {
		this.downAnimation = downAnimation;
	}

	public Animation getRightAnimation() {
		return rightAnimation;
	}

	public void setRightAnimation(Animation rightAnimation) {
		this.rightAnimation = rightAnimation;
	}

	public Animation getTopAnimation() {
		return topAnimation;
	}

	public void setTopAnimation(Animation topAnimation) {
		this.topAnimation = topAnimation;
	}

	public Animation getLeftAnimation() {
		return leftAnimation;
	}

	public void setLeftAnimation(Animation leftAnimation) {
		this.leftAnimation = leftAnimation;
	}

	public HashMap<DirectionType, Animation> getAnimationMap() {
		return animationMap;
	}

	public void setAnimationMap(HashMap<DirectionType, Animation> animationMap) {
		this.animationMap = animationMap;
	}
}
