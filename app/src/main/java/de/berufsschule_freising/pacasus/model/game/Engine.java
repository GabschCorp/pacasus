package de.berufsschule_freising.pacasus.model.game;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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

	private GameState state = GameState.Run;
	private int actualScore = 0;
	private int actualLives = 3;

	private Pacman pacman;
	private static final int PACMAN_SPAWN_X = 1;
	private static final int PACMAN_SPAWN_Y = 1;

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

		this.pacman = new Pacman(new Point(PACMAN_SPAWN_X,PACMAN_SPAWN_Y), am, this.map);
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
							else{
								catchTimer.cancel();
								catchTimer.purge();
								stopEatable();
								isCatchTimerRunning = true;

							}
						}
					}, Engine.EATABLE_DELAY); // 10 sec
				} catch (IllegalStateException e){

				}
				if(args.EatenDot != null && args.EatenDot instanceof Dot) {
					removeDotFromList((Dot) args.EatenDot);
					if(map.getDotList().size() == 0){
						state = GameState.Win;
					}
				}

				//TODO:Wenn die Lezzte Pille gegessen wurde -> Win!
			}
		});

		this.pacman.PacmanEatsDot.addHandler(new IEventHandler<PacmanEventArgs>() {
			@Override
			public void handle(Object sender, PacmanEventArgs args) {
				actualScore = args.Points;
				actualLives = args.Lives;
			}
		});

		this.pacman.PacmanDies.addHandler(new IEventHandler<PacmanEventArgs>() {
			@Override
			public void handle(Object sender, PacmanEventArgs args) {
				actualScore = args.Points;
				actualLives = args.Lives;

				if(actualLives == 0)
				{
					state = GameState.Over;
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
		/* TODO:
		 * Move
		 * CollisionDetection ...
		 */

	}

	public void render(Canvas canvas){
		// TODO: Bessere Lösung für Gameloop finden
		// Evtl mit GameTime
		Paint textPaint = new Paint();
		textPaint.setColor(Color.YELLOW);
		textPaint.setStyle(Paint.Style.STROKE);
		textPaint.setTextSize(50);

		this.map.setCanvas(canvas);
		this.pacman.setCanvas(canvas);

		this.map.render();

		switch(state){
			case Run:
			case Catch:{
				for (Ghost ghost : this.ghostList) {
					ghost.setCanvas(canvas);
					ghost.render();

					if (ghost.isIntersect(this.pacman)) {
						if (state == GameState.Catch && ghost.getIsEatable() == true) {
							this.pacman.eatGhost(ghost);
						} else {
							this.pacman.die();
						}
					}
				}
				this.pacman.render();
				break;
			}
			case Over:{
				canvas.drawText(String.format("Game Over", actualScore, actualLives), 250, 525, textPaint);
				break;
			}
			case Win:{
				canvas.drawText(String.format("You Won", actualScore, actualLives), 250, 525, textPaint);
				break;
			}
			default:{
				break;
			}
		}

		canvas.drawText(String.format("Punkte: %d | Leben: %d", actualScore, actualLives), 25, 200, textPaint);
	}

	public void removeDotFromList(Dot dot){
		this.map.getDotList().remove(dot);
	}

	public Pacman getPacman(){
		return this.pacman;
	}
}
