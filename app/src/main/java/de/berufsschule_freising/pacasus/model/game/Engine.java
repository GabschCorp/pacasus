package de.berufsschule_freising.pacasus.model.game;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;

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

	private Timer catchTimer = new Timer();
	private boolean isCatchTimerRunning = false;

	private static final int EATABLE_DELAY = 10 * 1000;

	public Engine(AssetManager am){
		try {
			this.map = new Map(am);
			this.map.parse();
		} catch (IOException ex){

		} catch (Exception e) {
			e.printStackTrace();
		}

		this.pacman = new Pacman(new Point(1,1), am, this.map);

		this.ghostList = new ArrayList<>();
		this.ghostList.add(GhostFactory.createBlinky(this.map, new Point(12, 15), am));
		this.ghostList.add(GhostFactory.createClyde(this.map, new Point(13, 15), am));
		this.ghostList.add(GhostFactory.createInky(this.map, new Point(14, 15), am));
		this.ghostList.add(GhostFactory.createPinky(this.map, new Point(12, 15), am));

		this.pacman.PacmanEatsPill.addHandler(new IEventHandler<PacmanEventArgs>() {
			@Override
			public void handle(Object sender, PacmanEventArgs args) {
				state = GameState.Catch;
				for (Ghost ghost : ghostList) {
					ghost.setIsEatable(true);
				}
				try {
					catchTimer.schedule(new TimerTask() {
						@Override
						public void run() {
							if(isCatchTimerRunning == false) {
								stopEatable();
								isCatchTimerRunning = true;
							}
							else {
								catchTimer.cancel();
								catchTimer.purge();
								stopEatable();
								isCatchTimerRunning = true;

							}
						}
					}, Engine.EATABLE_DELAY); // 10 sec
				} catch (IllegalStateException e){

				}
			}
		});

		for (Ghost ghost : this.ghostList) {

			// TODO : addevents
		}
	}

	public void stopEatable(){
		state = GameState.Run;
		for (Ghost ghost : ghostList) {
			ghost.setIsEatable(false);
		}
		isCatchTimerRunning = false;
	}

	public void update(){
		this.pacman.move();

		for (Ghost ghost : this.ghostList) {
			ghost.move();

			// Kollisionserkennung
			if (CollisionDetection.isIntersecting(this.pacman, ghost, this.map.getGridUnitLength())){
				// Pacman auf Pillen
				if (state == GameState.Catch && ghost.getIsEatable() == true){
					this.pacman.eatGhost(ghost);
				} else { // Pacman stirbt
					this.pacman.die();
				}

			}
			if (ghost.isIntersect(this.pacman)){
			}
		}
	}

	public void render(Canvas canvas){
		this.map.setCanvas(canvas);
		this.pacman.setCanvas(canvas);

		this.map.render();

		for (Ghost ghost : this.ghostList){
			ghost.setCanvas(canvas);
			ghost.render();
		}
		this.pacman.render();
	}

	public Pacman getPacman(){
		return this.pacman;
	}
}
