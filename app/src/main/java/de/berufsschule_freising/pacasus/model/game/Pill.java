package de.berufsschule_freising.pacasus.model.game;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Jooly on 21.02.2016.
 */
public class Pill extends AbstractPoint implements IDrawable{

	private AssetManager assetManager;
	private Animation pillFrame;

	public Pill(Map map, Point mapPosition, AssetManager am){
		super(map, mapPosition);

		this.setAssetManager(am);

		InputStream spriteSheetInputStream;
		Bitmap spritesheet = null;
		try {
			spriteSheetInputStream = this.getAssetManager().open("pacman_characters.png");
			spritesheet = BitmapFactory.decodeStream(spriteSheetInputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.pillFrame = new Animation(spritesheet);
		this.pillFrame.setColumns(8);
		this.pillFrame.setRows(8);
		this.pillFrame.setStartFrame(6);
		this.pillFrame.setEndFrame(7);

		this.getPaint().setStyle(Paint.Style.STROKE);
		this.getPaint().setStrokeWidth(5);
		this.getPaint().setColor(Color.WHITE);
	}

	@Override
	public void render() {
		if (!this.isEaten()) {

			PointF drawingPosition = this.getPositionByMapPosition();

			this.pillFrame.setScaleHeight(this.getMap().getGridUnitLength());
			this.pillFrame.setScaleWidth(this.getMap().getGridUnitLength());

			this.getCanvas().setMatrix(new Matrix());

			// TODO : vom Asset rendern
//		this.getCanvas().drawBitmap(this.pillFrame.createBitmapFrame(),
//				this.getPositionByMapPosition().x,
//				this.getPositionByMapPosition().y + 200, null);

			this.getCanvas().drawArc(drawingPosition.x - 7, drawingPosition.y - 7 + 200, drawingPosition.x + 7, drawingPosition.y + 7 + 200, 0, 360, true, this.getPaint());
		}
	}

	@Override
	public void clear() {

	}

	public AssetManager getAssetManager() {
		return assetManager;
	}

	public void setAssetManager(AssetManager assetManager) {
		this.assetManager = assetManager;
	}

	public Animation getPillFrame() {
		return pillFrame;
	}

	public void setPillFrame(Animation pillFrame) {
		this.pillFrame = pillFrame;
	}
}
