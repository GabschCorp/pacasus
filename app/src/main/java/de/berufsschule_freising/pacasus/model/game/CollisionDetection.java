package de.berufsschule_freising.pacasus.model.game;

import android.graphics.Point;
import android.graphics.PointF;
import android.util.Log;

/**
 * Created by Julian on 03.03.2016.
 */
public class CollisionDetection {

	/**
	 * Prüft, ob sich die übergebenen Drawables anhand des übergebenen BoundingSpheres
	 * schneiden
	 *
	 * @param a	Linkes Objekt
	 * @param b	Rechtes Objekt
	 * @param boundingSphere Radius
	 * @return boolean
	 */
	public static boolean isIntersecting(Drawable a, Drawable b, float boundingSphere){
		// Differenz(-Vector) kleiner/gleich einer Längeneinheit
		if (Math.abs(a.getPosition().x - b.getPosition().x) <= boundingSphere &&
				Math.abs(a.getPosition().y  - b.getPosition().y) <= boundingSphere){
			return true;
		}

		return false;
	}

	/**
	 * Prüft, ob sie der übergebene Actor mit der angegebenen Map überschneiden
	 *
	 * @param actor
	 * @param map
	 * @return
	 */
	public static boolean isIntersecting(Actor actor, Map map){
		Point mapPos = actor.getMapPosition();

		switch (actor.getDirection()){
			case Up:
				mapPos.y--;
				break;
			case Down:
				mapPos.y++;
				break;
			case Right:
				mapPos.x++;
				break;
			case Left:
				mapPos.x--;
				break;
		}

		// Grobe Prüfung - Ist Weg
		char tmp;
		tmp = actor.getMap().getCharAtPoint(mapPos);
		if (Character.isWhitespace(tmp) ||
				tmp == '*' ||
				tmp == 'p'){
			return false;
		} else {
//			// Genaue Prüfung - Entfernung zum Stein
//			PointF charPos = actor.getPositionByMapPosition(mapPos);
//			if (Math.abs(charPos.x - actor.getPosition().x) >= actor.getMap().getGridUnitLength() ||
//					Math.abs(charPos.y - actor.getPosition().y) >= actor.getMap().getGridUnitLength()){
//				return false;
//			}
		}

		return true;
	}
}
