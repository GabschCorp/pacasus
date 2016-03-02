package de.berufsschule_freising.pacasus.model.game;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Julian on 02.03.2016.
 */
public class ResourceRegistry {

	private static ResourceRegistry instance = null;
	private Bitmap spritesheet = null;

	private void ResourceRegistry(){

	}

	public static ResourceRegistry getInstance(){
		if (instance == null){
			instance = new ResourceRegistry();
		}

		return instance;
	}

	public Bitmap getSpritesheet(AssetManager am){

		if (this.spritesheet == null){

			InputStream spriteSheetInputStream;
			this.spritesheet = null;
			try {
				spriteSheetInputStream = am.open("pacman_characters.png");
				this.spritesheet = BitmapFactory.decodeStream(spriteSheetInputStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return this.spritesheet;
	}
}
