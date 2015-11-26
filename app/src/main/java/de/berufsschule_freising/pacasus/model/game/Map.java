package de.berufsschule_freising.pacasus.model.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import com.larvalabs.svgandroid.SVGParser;

/**
 * Created by Julian on 21.10.2015.
 */
public class Map implements IDrawable {

    private Canvas canvas;
    private String filePath;

    private int thickness;

    private Paint paint;
    private Color backgroundColor;
    private Color foregroundColor;

    // Wert, welcher angibt, wie lang eine Einheit des Rasters ist.
    // Hoffe, es ist verständlich was ich mein :D
    // TODO: Vllt kann man diesen Wert berechnen, evlt const setzen
    public static final float GRID_UNIT_LENGTH = 50f;

    public Map(){

    }

    public Map(Canvas canvas, String filePath){
        this();
        this.setCanvas(canvas);
        this.setFilePath(filePath);
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
    }

    @Override
    public void clear() {

    }

    public static float getGridUnitLength(){
        return Map.GRID_UNIT_LENGTH;
    }
}
