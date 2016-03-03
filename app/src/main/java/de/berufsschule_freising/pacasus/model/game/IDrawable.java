package de.berufsschule_freising.pacasus.model.game;

import android.graphics.Canvas;
import android.graphics.PointF;

/**
 * Created by Julian on 21.10.2015.
 */
public interface IDrawable {

    void setCanvas(Canvas canvas);
    Canvas getCanvas();

    void render();
    void clear();
}
