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

	private Animation eatableAnimation;

	private boolean isEatable;


	public Ghost(String name, Point initialPosition, Map map, AssetManager am) {
		super(am);

		this.setName(name);
		this.setMap(map);
		this.setInitialPosition(initialPosition);

		this.eatableAnimation = new Animation(this.getSpritesheet());
		this.eatableAnimation.setColumns(8);
		this.eatableAnimation.setRows(8);
		this.eatableAnimation.setStartFrame(30);
		this.eatableAnimation.setEndFrame(32);

		this.setDirection(DirectionType.Right);
		this.setNextDirection(DirectionType.Up);

		this.animationMap = new HashMap<>();
	}

	@Override
	public void render() {
		Canvas canvas = this.getCanvas();

		this.animationMap.get(this.getDirection()).setScaleHeight(this.getMap().getGridUnitLength());
		this.animationMap.get(this.getDirection()).setScaleWidth(this.getMap().getGridUnitLength());
		this.eatableAnimation.setScaleHeight(this.getMap().getGridUnitLength());
		this.eatableAnimation.setScaleWidth(this.getMap().getGridUnitLength());

		Bitmap frame;
		if (isEatable == true){
			frame = this.eatableAnimation.createBitmapFrame();
		} else {
			frame = this.animationMap.get(this.getDirection()).createBitmapFrame();
		}

		canvas.drawBitmap(frame, this.getPosition().x, this.getPosition().y, null);
	}

	@Override
	public void clear() {
		this.getCanvas().drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
	}

	@Override
	public void move() {
		Log.w("Ghost:" + this.getName(), this.getDirection().toString());

		// Erstes zeichnen; Kartenposition setzen
		if (this.getPosition().x == 0){
			this.setMapPosition(this.getInitialPosition());
		}

		if (this.canWalk(this.getNextDirection()) && this.getNextDirection() != DirectionType.None) {// Kann in die nächste, angegebene Richtung laufen?

			// Richtungen aktualisieren
			this.setDirection(this.getNextDirection());
			this.setNextDirection(DirectionType.None);

			this.modifyPosition();

		} else if(this.canWalk(this.getDirection()) && this.getDirection() != DirectionType.None){ // Kann in die aktulle Richtung laufen?
			this.modifyPosition();
		} else {
			Random random = new Random();
			int randomNum = random.nextInt((4 - 1) + 1) + 1;
			this.setDirection(DirectionType.fromOrdinal(randomNum));
			randomNum = random.nextInt((4 - 1) + 1) + 1;
			this.setNextDirection(DirectionType.fromOrdinal(randomNum));
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

	public boolean getIsEatable() {
		return isEatable;
	}

	public void setIsEatable(boolean isEatable) {
		this.isEatable = isEatable;
	}

	public HashMap<DirectionType, Animation> getAnimationMap() {
		return animationMap;
	}

	public void setAnimationMap(HashMap<DirectionType, Animation> animationMap) {
		this.animationMap = animationMap;
	}
}
