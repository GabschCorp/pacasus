package de.berufsschule_freising.pacasus.model.game;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.ArcShape;
import android.graphics.drawable.shapes.Shape;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Julian on 21.10.2015.
 */
public class Map implements IDrawable {

    private Canvas canvas;
    private String filePath;
    private AssetManager assetManager;

    private Bitmap bitmapMap;

    private int thickness;

    private Paint paint;
    private Color backgroundColor;
    private Color foregroundColor;

    private int width;
    private int height;

	private char[][] charMap = new char[999][999];

    // Wert, welcher angibt, wie lang eine Einheit des Rasters ist.
    // Hoffe, es ist verständlich was ich mein :D
    // TODO: Vllt kann man diesen Wert berechnen, evlt const setzen
    public static float GRID_UNIT_LENGTH = 10f;

    public Map(AssetManager am, int width, int height) throws IOException {
        this.assetManager = am;
        this.width = width;
        this.height = height;

        this.paint = new Paint();
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setColor(Color.BLUE);

        InputStream mapfile = this.assetManager.open("maps/proof");
        this.bitmapMap = parse(mapfile);
    }

    private Bitmap parse(InputStream io) throws IOException {
        Bitmap bmp = Bitmap.createBitmap(this.width , this.height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);

        byte[] contentBytes = new byte[io.available()];
        io.read(contentBytes);

        String content = new String(contentBytes);

        Log.w("Map", content);

        // Compute Grid Unit Length
        // CanvasWidth / AnzahlZeichenProZeile
        int i = 0;
        char[] contentChars = content.toCharArray();
        while (contentChars[i] != Character.LINE_SEPARATOR){
            i++;
        }
        this.GRID_UNIT_LENGTH = this.width / i;

        int row = 0;
        int column = -1;

        for (char c : content.toCharArray()){
            column++;

			this.charMap[row][column] = c;

            switch (c) {
                case '╔' :
                case '╗' :
                case '╚' :
                case '╝' :
                case '═' :
                    canvas.drawLine(column * GRID_UNIT_LENGTH + (GRID_UNIT_LENGTH / 2),
                            row * GRID_UNIT_LENGTH + (GRID_UNIT_LENGTH / 2),
                            (column + 1) * GRID_UNIT_LENGTH + (GRID_UNIT_LENGTH / 2),
                            (row) * GRID_UNIT_LENGTH  + (GRID_UNIT_LENGTH / 2), this.paint);
                    break;
                case '║' :
                    canvas.drawLine(column * GRID_UNIT_LENGTH ,
                            row * GRID_UNIT_LENGTH,
                            (column) * GRID_UNIT_LENGTH,
                            (row + 1) * GRID_UNIT_LENGTH, this.paint);
                    break;
				case Character.LINE_SEPARATOR :
					row++;
					column = -1;
					break;
            }
        }

        return bmp;
    }

    public Map(AssetManager am, Canvas canvas, int width, int height) throws IOException {
        this(am, width, height);
        this.setCanvas(canvas);
    }

    public Canvas getCanvas(){
        return this.canvas;
    }

    public void setCanvas(Canvas canvas){
        this.canvas = canvas;
    }

    public String getFilePath(){
        return this.filePath;
    }

    public void setFilePath(String filePath){
        this.filePath = filePath;
    }

    @Override
    public void render() {
        if (this.getCanvas() == null){
            // TODO: throw Exception
        }
        if (this.getFilePath() == null){
            // TODO: throw Exception
        }

        this.getCanvas().setMatrix(new Matrix());
        this.getCanvas().translate(0, 200);
        this.getCanvas().drawBitmap(this.bitmapMap, 0, 0, null);
    }

    @Override
    public void clear() {

    }

    public static float getGridUnitLength(){
        return Map.GRID_UNIT_LENGTH;
    }

	public char getCharAtPoint(Point pos){
		return 'a';
	}
}
