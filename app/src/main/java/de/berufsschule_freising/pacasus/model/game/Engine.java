package de.berufsschule_freising.pacasus.model.game;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Point;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import events.IEventHandler;

/**
 * Created by Julian on 29.02.2016.
 */
public class Engine {

	private GameState state;

	private Pacman pacman;
	private List<Ghost> ghostList;
	private Map map;

	private static final int EATABLE_DELAY = 10 * 1000;

	public Engine(AssetManager am){
		try {
			this.map = new Map(am);
			this.map.parse();
		} catch (IOException ex){

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			this.map = new Map(am);
			this.map.parse();
		} catch (IOException ex){

		} catch (Exception e) {
			e.printStackTrace();
		}

		this.ghostList = new ArrayList<>();
		this.ghostList.add(GhostFactory.createBlinky(this.map, new Point(11, 16), am));
		this.ghostList.add(GhostFactory.createClyde(this.map, new Point(11, 16), am));
		this.ghostList.add(GhostFactory.createInky(this.map, new Point(11, 16), am));
		this.ghostList.add(GhostFactory.createPinky(this.map, new Point(11, 16), am));

		this.pacman = new Pacman(new Point(1,1), am, this.map);
		this.pacman.PacmanEatsPill.addHandler(new IEventHandler<PacmanEventArgs>() {
			@Override
			public void handle(Object sender, PacmanEventArgs args) {
				state = GameState.Catch;
				for (Ghost ghost : ghostList) {
					ghost.setIsEatable(true);
				}
				Timer t = new Timer();
				t.schedule(new TimerTask() {
					@Override
					public void run() {
						stopEatable();
					}
				}, Engine.EATABLE_DELAY); // 10 sec
			}
		});

		for (Ghost ghost : this.ghostList) {

			// TODO : addevents
		}
	}

	public void stopEatable(){
		state = GameState.Run;
	}

	public void update(){
		/* TODO:
		 * Move
		 * CollisionDetection ...
		 */

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
				if (state == GameState.Catch && ghost.getIsEatable() == true){
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
