package de.berufsschule_freising.pacasus.model.game;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by Julian on 21.10.2015.
 */
public class Map implements IDrawable {

    private Canvas canvas = null;
    private String filePath;
    private AssetManager assetManager;

    private Bitmap bitmapMap = null;

    private List<Dot> dotList = new ArrayList<>();
    private List<Pill> pillList = new ArrayList<>();

    private int thickness;

    private Paint paint;
    private Color backgroundColor;
    private Color foregroundColor;

    // TODO: Const Wand-offset; Wand abflachen
    private float wallOffsetPercentage = 0.5f;

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
        this.paint.setStrokeWidth(2);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setColor(Color.BLUE);
    }

    public void parse() throws Exception {
        this.io = this.assetManager.open("maps/proof");

        byte[] contentBytes = new byte[this.io.available()];
        io.read(contentBytes);


        String content = new String(contentBytes);

        char[] contentChars = content.toCharArray();

        // set charmap
        ArrayList<Character> tmpList = new ArrayList<>();
        for (char c : contentChars){ // Get Columncount and rowCount
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
                if (this.charMap.get(i).get(j) == '*') {
                    this.dotList.add(new Dot(this, new Point(j+1, i+1)));
                }
                if (this.charMap.get(i).get(j) == 'p') {
                    this.pillList.add(new Pill(this, new Point(j+1, i+1), this.assetManager));
                }
            }
        }

        this.GridUnitLength = 30;

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

        this.renderMap();


        // RenderDots
        for (Dot d : this.dotList){
            d.setCanvas(this.getCanvas());
            d.render();
        }

        // RenderPills
        for (Pill p : this.pillList){
            p.setCanvas(this.getCanvas());
            p.render();
        }
    }

    private void renderMap(){
        canvas = this.getCanvas();
        canvas.drawColor(Color.BLACK);

        for (int rowIter = 0; rowIter < this.charMap.size(); rowIter++) { // row
            for (int columnIter = 0; columnIter < this.charMap.get(rowIter).size(); columnIter++) { // columns
                char c = this.charMap.get(rowIter).get(columnIter);

				int column = columnIter + 1;
				int row  = rowIter + 1;

                switch (c) {
                    case '╔':
                        canvas.drawArc(getLeftMapPoint(column) + this.getWallOffset(),
                                getLeftMapPoint(row) + this.getWallOffset(),
                                getRightMapPoint(column) + this.getWallOffset(),
                                getRightMapPoint(row) + this.getWallOffset(), 180f, 90f, false, this.paint);
                        break;
                    case '╗':
                        canvas.drawArc(getLeftMapPoint(column) - this.getWallOffset(),
                                getLeftMapPoint(row) + this.getWallOffset(),
                                getRightMapPoint(column) - this.getWallOffset(),
                                getRightMapPoint(row) + this.getWallOffset(), 270f, 90f, false, this.paint);
                        break;
                    case '╚':
                        canvas.drawArc(getLeftMapPoint(column) + this.getWallOffset(),
                                getLeftMapPoint(row) - this.getWallOffset(),
                                getRightMapPoint(column),
                                getRightMapPoint(row) - this.getWallOffset(), 90f, 90f, false, this.paint);
                        break;
                    case '╝':
                        canvas.drawArc(getLeftMapPoint(column) - this.getWallOffset(),
                                getLeftMapPoint(row) - this.getWallOffset(),
                                getRightMapPoint(column) - this.getWallOffset(),
                                getRightMapPoint(row) - this.getWallOffset(), 0, 90f, false, this.paint);
                        break;
                    case '═':
                        canvas.drawLine(getLeftMapPoint(column),
                                getLeftMapPoint(row) + this.getWallOffset(),
                                getRightMapPoint(column),
                                getLeftMapPoint(row) + this.getWallOffset(), this.paint);

                        canvas.drawLine(getLeftMapPoint(column),
                                getRightMapPoint(row) - this.getWallOffset(),
                                getRightMapPoint(column),
                                getRightMapPoint(row) - this.getWallOffset(), this.paint);
                        break;
                    case '║':
                        canvas.drawLine(getLeftMapPoint(column) + this.getWallOffset(),
                                getLeftMapPoint(row),
                                getLeftMapPoint(column) + this.getWallOffset(),
                                getRightMapPoint(row), this.paint);

                        canvas.drawLine(getRightMapPoint(column) - this.getWallOffset(),
                                getLeftMapPoint(row),
                                getRightMapPoint(column) - this.getWallOffset(),
                                getRightMapPoint(row), this.paint);
                        break;
                }
            }
        }
    }

    private float getRightMapPoint(int rowOrColumn) {
        return rowOrColumn * this.GridUnitLength + this.GridUnitLength / 2;
    }

    private float getLeftMapPoint(int rowOrColumn) {
        return rowOrColumn * this.GridUnitLength - this.GridUnitLength / 2;
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

        if (this.charMap.get(0).size() >= pos.x && pos.x >= 0 &&
               this.charMap.size() >= pos.y && pos.y >= 0){
             return this.charMap.get(pos.y).get(pos.x);
        }

        return '*';
	}

    public AbstractPoint getEatablePointByPosition(Point pos){

        for (Dot d : this.dotList) {
            if (d.getMapPosition().x -1 == pos.x &&
                    d.getMapPosition().y -1 == pos.y){
                return d;
            }
        }
        for (Pill p : this.pillList){
            if (p.getMapPosition().x -1 == pos.x &&
                    p.getMapPosition().y -1 == pos.y){
                return p;
            }
        }

        return null;
    }

    public void removeDotFromList(AbstractPoint dot){
        if (dot instanceof Pill){
            this.pillList.remove(this.pillList.indexOf(dot));
        } else {
            this.dotList.remove(this.dotList.indexOf(dot));
        }
    }

    public List<Dot> getDotList(){
        return this.dotList;
    }
}
