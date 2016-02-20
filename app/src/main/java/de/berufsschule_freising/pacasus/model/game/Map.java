package de.berufsschule_freising.pacasus.model.game;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.callback.CallbackHandler;

/**
 * Created by Julian on 21.10.2015.
 */
public class Map implements IDrawable {

    private Canvas canvas = null;
    private String filePath;
    private AssetManager assetManager;

    private Bitmap bitmapMap = null;

    private int thickness;

    private Paint paint;
    private Color backgroundColor;
    private Color foregroundColor;

    // TODO: Const Wand-offset; Wand abflachen
    private float wallOffsetPercentage = 0.25f;

    private int width;
    private int height;

    private InputStream io;

    /** Aufbau wie Matrix => char[row][column] */
    private List<List<Character>> charMap = new ArrayList<>();

    // Wert, welcher angibt, wie lang eine Einheit des Rasters ist.
    // Hoffe, es ist verständlich was ich mein :D
    // TODO: Vllt kann man diesen Wert berechnen, evlt const setzen
    public static float GridUnitLength = 0f;

    public Map(AssetManager am) throws IOException {
        this.assetManager = am;

        this.paint = new Paint();
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setColor(Color.BLUE);
    }

    public void parse() throws Exception {
//        if (this.width == 0 || this.height == 0){
//            throw new Exception("Width and Height must be set");
//        }
//        if (this.canvas == null){
//            throw new Exception("Canvas must be set");
//        }

        this.io = this.assetManager.open("maps/proof");

        byte[] contentBytes = new byte[this.io.available()];
        io.read(contentBytes);


        String content = new String(contentBytes);

        Log.w("Map", content);

        char[] contentChars = content.toCharArray();

        // set charmap
        ArrayList<Character> tmpList = new ArrayList<>();
        for (char c : contentChars){ // Get Columncount and rowCount
            Log.w("Map", String.valueOf(c));
            if (c == '\n'){
                continue;
            }

            if (c != '\r') { // zeilenende
                tmpList.add(tmpList.size(), c);
            } else {
                this.charMap.add(this.charMap.size(), tmpList);
                tmpList = new ArrayList<>();

            }
        }
        this.charMap.add(this.charMap.size(), tmpList); // Letzte Zeile hinzufügen

        for (int i = 0; i < this.charMap.size(); i++) { // row
            for (int j = 0; j < this.charMap.get(i).size(); j++) { // columns
                Log.w("Map" + i, String.valueOf(this.charMap.get(i).get(j)));
            }
        }

        Log.w("Map", "charmap set");
        Log.w("Map", String.valueOf(this.charMap.size()));
        Log.w("Map", String.valueOf(this.charMap.get(1).size()));


        // Compute Grid Unit Length
        // CanvasWidth / AnzahlZeichenProZeile
        //this.GridUnitLength = this.width / this.charMap.size();
        this.GridUnitLength = 30;
        Log.w("Map", "GridUnitLenght:" + this.getGridUnitLength());

    }

    public void setWidth(int width)  {
        this.width = width;
    }

    public void setHeight(int height){
        this.height = height;
    }

    public Canvas getCanvas(){
        return this.canvas;
    }

    public void setCanvas(Canvas canvas){
        this.canvas = canvas;
        Log.w("Map:width", String.valueOf(canvas.getWidth()));
        this.GridUnitLength = this.getCanvas().getWidth() / this.charMap.get(0).size();

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
            throw new RuntimeException("Canvas must be set");
        }

        Matrix matrix = new Matrix();
        matrix.postTranslate(0, 200);

        this.getCanvas().setMatrix(matrix);
        //this.getCanvas().translate(0, 200);
        //if (this.bitmapMap == null){
            this.renderMap();
        //}

        //this.canvas.drawBitmap(this.bitmapMap, 0, 200, null);
    }

    private void renderMap(){
        canvas = this.getCanvas();

//        Bitmap bmp = Bitmap.createBitmap((int)this.getGridUnitLength() * this.charMap.get(0).size(),
//                (int)this.getGridUnitLength() * this.charMap.size(), Bitmap.Config.ARGB_8888);
//        canvas = new Canvas(bmp);

        for (int row = 0; row < this.charMap.size(); row++) { // row
            for (int column = 0; column < this.charMap.get(row).size(); column++) { // columns
                char c = this.charMap.get(row).get(column);
                switch (c) {
                    case '╔':
                        canvas.drawArc((float)(column) * this.GridUnitLength + this.getWallOffset(),
                                (float)(row) * this.GridUnitLength + this.getWallOffset(),
                                (float)(column + 2) * this.GridUnitLength + this.getWallOffset(),
                                (float)(row + 2) * this.GridUnitLength + this.getWallOffset(), 180f, 90f, false, this.paint);
                        break;
                    case '╗':
                        canvas.drawArc((float)(column - 1) * this.GridUnitLength - this.getWallOffset(),
                                (float)(row) * this.GridUnitLength + this.getWallOffset(),
                                (float)(column + 1) * this.GridUnitLength - this.getWallOffset(),
                                (float)(row + 2) * this.GridUnitLength + this.getWallOffset(), 270f, 90f, false, this.paint);
                        break;
                    case '╚':
                        canvas.drawArc((float)column * this.GridUnitLength + this.getWallOffset(),
                                (float)(row - 1) * this.GridUnitLength - this.getWallOffset(),
                                (float)(column + 2) * this.GridUnitLength,
                                (float)(row + 1) * this.GridUnitLength - this.getWallOffset(), 90f, 90f, false, this.paint);
                        break;
                    case '╝':
                        canvas.drawArc((float)(column - 1) * this.GridUnitLength - this.getWallOffset(),
                                (float)(row - 1) * this.GridUnitLength - this.getWallOffset(),
                                (float)(column + 1) * this.GridUnitLength - this.getWallOffset(),
                                (float)(row + 1) * this.GridUnitLength - this.getWallOffset(), 0, 90f, false, this.paint);
                        break;
                    case '═':
                        canvas.drawLine(column * this.GridUnitLength,
                                row * this.GridUnitLength + this.getWallOffset(),
                                (column + 1) * this.GridUnitLength,
                                (row) * this.GridUnitLength + this.getWallOffset(), this.paint);
                        canvas.drawLine(column * this.GridUnitLength,
                                (row + 1) * this.GridUnitLength - this.getWallOffset(),
                                (column + 1) * this.GridUnitLength,
                                (row + 1) * this.GridUnitLength - this.getWallOffset(), this.paint);
                        break;
                    case '║':
                        canvas.drawLine(column * this.GridUnitLength + this.getWallOffset(),
                                row * this.GridUnitLength,
                                (column) * this.GridUnitLength + this.getWallOffset(),
                                (row + 1) * this.GridUnitLength, this.paint);
                        canvas.drawLine((column + 1) * this.GridUnitLength - this.getWallOffset(),
                                row * this.GridUnitLength,
                                (column + 1) * this.GridUnitLength - this.getWallOffset(),
                                (row + 1) * this.GridUnitLength, this.paint);
                        break;
                }
            }
        }

        //this.bitmapMap = bmp;
    }

    @Override
    public void clear() {
        this.getCanvas().drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
    }

    public float getGridUnitLength(){
        return this.GridUnitLength;
    }

    public float getWallOffset(){
        return this.getGridUnitLength() * this.wallOffsetPercentage;
    }

	public char getCharAtPoint(Point pos){
        Log.w("map-x", String.valueOf(pos.x));
        Log.w("map-y", String.valueOf(pos.y));
        //Log.w("map-xl", String.valueOf(this.charMap.size()));
        //Log.w("map-yl", String.valueOf(this.charMap.get(pos.y).size()));

        if (this.charMap.get(0).size() >= pos.x && pos.x >= 0 &&
               this.charMap.size() >= pos.y && pos.y >= 0){
             return this.charMap.get(pos.y).get(pos.x);
        }

        return '*';
	}
}
