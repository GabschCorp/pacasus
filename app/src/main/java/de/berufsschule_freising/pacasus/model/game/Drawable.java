package de.berufsschule_freising.pacasus.model.game;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;


/**
 * Created by Julian on 02.03.2016.
 */
abstract public class Drawable implements IDrawable {

	private Canvas canvas;

	private AssetManager assetManager;
	private Bitmap spritesheet;

	public Drawable(AssetManager am){
		this.setAssetManager(am);

		this.spritesheet = ResourceRegistry.getInstance().getSpritesheet(am);
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}

	public void setAssetManager(AssetManager assetManager) {
		this.assetManager = assetManager;
	}

	@Override
	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	@Override
	public Canvas getCanvas() {
		return this.canvas;
	}

	public Bitmap getSpritesheet(){
		return this.spritesheet;
	}
}
