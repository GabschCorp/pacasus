package de.berufsschule_freising.pacasus.model.game;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Jooly on 21.02.2016.
 */
public class GhostFactory {

	// Blink - rot
	// Inky - türkis
	// Clyde - organge
	// Pinky pink

	public static Ghost createBlinky(Map map, Point initialPosition, AssetManager am){
		Ghost blinky = new Ghost("blinky", initialPosition, map, am);

		InputStream spriteSheetInputStream;
		Bitmap spriteSheet = null;
		try {
			spriteSheetInputStream = blinky.getAssetManager().open("pacman_characters.png");
			spriteSheet = BitmapFactory.decodeStream(spriteSheetInputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Animation anim = new Animation(spriteSheet);
		anim.setColumns(8);
		anim.setRows(8);
		anim.setStartFrame(36);
		anim.setEndFrame(38);

		blinky.getAnimationMap().put(DirectionType.Down, anim);

		anim = new Animation(spriteSheet);
		anim.setColumns(8);
		anim.setRows(8);
		anim.setStartFrame(34);
		anim.setEndFrame(36);

		blinky.getAnimationMap().put(DirectionType.Right, anim);


		anim = new Animation(spriteSheet);
		anim.setColumns(8);
		anim.setRows(8);
		anim.setStartFrame(32);
		anim.setEndFrame(34);

		blinky.getAnimationMap().put(DirectionType.Up, anim);

		anim = new Animation(spriteSheet);
		anim.setColumns(8);
		anim.setRows(8);
		anim.setStartFrame(38);
		anim.setEndFrame(40);

		blinky.getAnimationMap().put(DirectionType.Left, anim);

		anim = new Animation(spriteSheet);
		anim.setColumns(8);
		anim.setRows(8);
		anim.setStartFrame(38);
		anim.setEndFrame(40);

		blinky.getAnimationMap().put(DirectionType.None, anim);

		return blinky;
	}

	public static Ghost createInky(Map map, Point initialPosition, AssetManager am){
		Ghost inky = new Ghost("inky", initialPosition, map, am);

		InputStream spriteSheetInputStream;
		Bitmap spriteSheet = null;
		try {
			spriteSheetInputStream = inky.getAssetManager().open("pacman_characters.png");
			spriteSheet = BitmapFactory.decodeStream(spriteSheetInputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Animation anim = new Animation(spriteSheet);
		anim.setColumns(8);
		anim.setRows(8);
		anim.setStartFrame(52);
		anim.setEndFrame(54);

		inky.getAnimationMap().put(DirectionType.Down, anim);

		anim = new Animation(spriteSheet);
		anim.setColumns(8);
		anim.setRows(8);
		anim.setStartFrame(50);
		anim.setEndFrame(52);

		inky.getAnimationMap().put(DirectionType.Right, anim);


		anim = new Animation(spriteSheet);
		anim.setColumns(8);
		anim.setRows(8);
		anim.setStartFrame(48);
		anim.setEndFrame(50);

		inky.getAnimationMap().put(DirectionType.Up, anim);

		anim = new Animation(spriteSheet);
		anim.setColumns(8);
		anim.setRows(8);
		anim.setStartFrame(54);
		anim.setEndFrame(56);

		inky.getAnimationMap().put(DirectionType.Left, anim);

		anim = new Animation(spriteSheet);
		anim.setColumns(8);
		anim.setRows(8);
		anim.setStartFrame(38);
		anim.setEndFrame(40);

		inky.getAnimationMap().put(DirectionType.None, anim);

		return inky;
	}

	public static Ghost createClyde(Map map, Point initialPosition, AssetManager am){
		Ghost clyde = new Ghost("clyde", initialPosition, map, am);

		InputStream spriteSheetInputStream;
		Bitmap spriteSheet = null;
		try {
			spriteSheetInputStream = clyde.getAssetManager().open("pacman_characters.png");
			spriteSheet = BitmapFactory.decodeStream(spriteSheetInputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Animation anim = new Animation(spriteSheet);
		anim.setColumns(8);
		anim.setRows(8);
		anim.setStartFrame(44);
		anim.setEndFrame(46);

		clyde.getAnimationMap().put(DirectionType.Down, anim);

		anim = new Animation(spriteSheet);
		anim.setColumns(8);
		anim.setRows(8);
		anim.setStartFrame(42);
		anim.setEndFrame(44);

		clyde.getAnimationMap().put(DirectionType.Right, anim);


		anim = new Animation(spriteSheet);
		anim.setColumns(8);
		anim.setRows(8);
		anim.setStartFrame(40);
		anim.setEndFrame(42);

		clyde.getAnimationMap().put(DirectionType.Up, anim);

		anim = new Animation(spriteSheet);
		anim.setColumns(8);
		anim.setRows(8);
		anim.setStartFrame(46);
		anim.setEndFrame(48);

		clyde.getAnimationMap().put(DirectionType.Left, anim);

		anim = new Animation(spriteSheet);
		anim.setColumns(8);
		anim.setRows(8);
		anim.setStartFrame(38);
		anim.setEndFrame(40);

		clyde.getAnimationMap().put(DirectionType.None, anim);

		return clyde;
	}

	public static Ghost createPinky(Map map, Point initialPosition, AssetManager am){
		Ghost pinky = new Ghost("blinky", initialPosition, map, am);

		InputStream spriteSheetInputStream;
		Bitmap spriteSheet = null;
		try {
			spriteSheetInputStream = pinky.getAssetManager().open("pacman_characters.png");
			spriteSheet = BitmapFactory.decodeStream(spriteSheetInputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Animation anim = new Animation(spriteSheet);
		anim.setColumns(8);
		anim.setRows(8);
		anim.setStartFrame(60);
		anim.setEndFrame(62);

		pinky.getAnimationMap().put(DirectionType.Down, anim);

		anim = new Animation(spriteSheet);
		anim.setColumns(8);
		anim.setRows(8);
		anim.setStartFrame(58);
		anim.setEndFrame(60);

		pinky.getAnimationMap().put(DirectionType.Right, anim);


		anim = new Animation(spriteSheet);
		anim.setColumns(8);
		anim.setRows(8);
		anim.setStartFrame(56);
		anim.setEndFrame(58);

		pinky.getAnimationMap().put(DirectionType.Up, anim);

		anim = new Animation(spriteSheet);
		anim.setColumns(8);
		anim.setRows(8);
		anim.setStartFrame(62);
		anim.setEndFrame(64);

		pinky.getAnimationMap().put(DirectionType.Left, anim);

		anim = new Animation(spriteSheet);
		anim.setColumns(8);
		anim.setRows(8);
		anim.setStartFrame(38);
		anim.setEndFrame(40);

		pinky.getAnimationMap().put(DirectionType.None, anim);

		return pinky;
	}
}
