package de.berufsschule_freising.pacasus.model.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;


/**
 * Created by Julian on 26.11.2015.
 */
public class Animation {

	private Rect rectFrame;

	private int frameHeight;
	private int frameWidth;

	private int startFrame;
	private int endFrame;

	// TODO: beim setter berechnen
	private int columns;
	private int rows;

	private Resources resources;
	private Bitmap spriteSheet;

	private Point currentFrame;
	private int currentFrameNr = 0;

	// Scales
	private float scaleWidth = 0;
	private float scaleHeight = 0;

	private float rotation = 0;

	public Animation(Bitmap spritesheet) {
		this.spriteSheet = spritesheet;

		//this.spriteSheet = Bitmap.createBitmap(bmpFile);
		currentFrame = new Point();
	}

	private Point getFrameByNr(int nr){
		int row = (int)Math.floor(nr / this.columns);
		int column = nr - row * this.columns;



		if (nr >= startFrame && nr <= this.endFrame){
			return new Point(column * this.getFrameWidth(), row  * this.getFrameHeight());
			//return new PointF(column * 70, row * 70);
		} else {
			return this.getFrameByNr(currentFrameNr = startFrame);
		}
	}

	// TODO: Datentyp?
	// Auf Start und Endframe beschränken (getCurrentFrameNumber)
	public Point getFrame(){

		if (!(this.getSpritesheetWidth() - this.frameWidth <= this.currentFrame.x + this.frameWidth)){

			this.currentFrame.x += this.frameWidth;
		} else {
			this.currentFrame.x = 0;

			if (!(this.getSpritesheetHeight() <= this.currentFrame.y + this.frameHeight)){

				this.currentFrame.y += this.frameHeight;
			} else {

				this.currentFrame.y = 0;
			}
		}

		this.currentFrameNr++;


		return this.currentFrame;
	}

	public Bitmap createBitmapFrame(){
		if (this.currentFrameNr >= this.endFrame){
			this.currentFrameNr = this.startFrame;
		}
		Point frame = this.getFrameByNr(this.currentFrameNr);

//		if (frame.x > this.getFrameWidth() || frame.y > this.getFrameHeight()){
//			frame = this.getFrameByNr(this.currentFrameNr = startFrame);
//		}

		Matrix matrix = new Matrix();
		matrix.postRotate(this.getRotation());

		Bitmap bmp = Bitmap.createBitmap(this.spriteSheet, frame.x, frame.y, this.getFrameWidth(), this.getFrameHeight(), matrix, true);

		// Scale Bitmap
		if (this.getScaleWidth() != 0 && this.getScaleHeight() != 0){
			bmp = Bitmap.createScaledBitmap(bmp, (int)this.getScaleWidth(), (int)this.getScaleHeight(), false);
		}



		this.currentFrameNr++;
		// TODO: incrementcurrenframenumber();
		return bmp;
	}

	public void setColumns(int columns){
		this.frameWidth = (int)this.spriteSheet.getWidth() / columns;
		this.columns = columns;
	}

	public void setRows(int rows){
		this.frameHeight = (int)this.spriteSheet.getHeight() / rows;
		this.rows = rows;
	}

	public int getFrameHeight(){
		return this.frameHeight;
	}

	public void setFrameHeight(int height){
		this.frameHeight = height;
	}

	public void setFrameWidth(int width){
		this.frameWidth = width;
	}

	public int getFrameWidth(){
		return this.frameWidth;
	}

	public void setStartFrame(int start){
		this.startFrame = start;
	}

	public void setEndFrame(int end){
		this.endFrame = end;
	}

	public void setSpriteSheet(Bitmap bmp){
		//this.spriteSheet = bmp;
	}

	public float getSpritesheetWidth(){
		return (float)this.spriteSheet.getWidth();
	}

	public float getSpritesheetHeight(){
		return (float)this.spriteSheet.getHeight();
	}

	public float getScaleWidth() {
		return this.scaleWidth;
	}

	public void setScaleWidth(float scaleWidth) {
		this.scaleWidth = scaleWidth;
	}

	public float getScaleHeight() {
		return this.scaleHeight;
	}

	public void setScaleHeight(float scaleHeight) {
		this.scaleHeight = scaleHeight;
	}

	public float getRotation() {
		return this.rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
}
