package de.berufsschule_freising.pacasus.model.game;

import android.graphics.Canvas;

/**
 * Created by Julian on 21.10.2015.
 */
public interface IDrawable {

    public void setCanvas(Canvas canvas);
    public Canvas getCanvas();

    public void render();
}
