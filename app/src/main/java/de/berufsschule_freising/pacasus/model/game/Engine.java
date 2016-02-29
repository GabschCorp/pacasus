package de.berufsschule_freising.pacasus.model.game;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Point;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Julian on 29.02.2016.
 */
public class Engine {

	private Pacman pacman;

	private List<Ghost> ghostList;

	private Map map;

	public Engine(AssetManager am){
		try {
			this.map = new Map(am);
			this.map.parse();
		} catch (IOException ex){

		} catch (Exception e) {
			e.printStackTrace();
		}

		pacman = new Pacman(new Point(1,1), am, this.map);

		this.ghostList = new ArrayList<>();
		this.ghostList.add(GhostFactory.createBlinky(this.map, new Point(12, 15), am));
		this.ghostList.add(GhostFactory.createClyde(this.map, new Point(13, 15), am));
		this.ghostList.add(GhostFactory.createInky(this.map, new Point(12, 15), am));
		this.ghostList.add(GhostFactory.createPinky(this.map, new Point(14, 15), am));
	}

	public void update() {
		this.pacman.move();
		for (Ghost ghost : this.ghostList) {
			ghost.move();
		}
	}

	public void render(Canvas canvas){
		// TODO: Bessere Lösung für Gameloop finden
		// Evtl mit GameTime
		this.map.setCanvas(canvas);
		this.pacman.setCanvas(canvas);

		this.map.render();

		for (Ghost ghost : this.ghostList){
			ghost.setCanvas(canvas);
			ghost.render();

			if (ghost.isIntersect(this.pacman)){
				if (GameState.getInstance().isEatable()){
					this.pacman.eatGhost(ghost);
				} else {
					this.pacman.die();
				}
			}
		}
		this.pacman.render();
	}

	public Pacman getPacman(){
		return this.pacman;
	}
}
