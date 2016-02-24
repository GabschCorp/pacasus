package de.berufsschule_freising.pacasus.model.game;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Julian on 22.02.2016.
 */
public class GameState {

	public enum State {
		Normal, // geister bunt
		Eatable, // geister blau
		Floating // Wechselzustand, geister blau/bunt blinkend
	}

	private State state = State.Normal;
	private static GameState instance = null;

	private GameState(){
	}

	public static GameState getInstance(){
		if (instance == null){
			instance = new GameState();
		}

		return instance;
	}

	public boolean isEatable(){
		return this.state == State.Eatable;
	}

	public void startEatable(){
		this.state = State.Eatable;

		Timer t = new Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				GameState.getInstance().stopEatable();
			}
		}, 10 * 1000); // 10 sec
	}

	public void stopEatable(){
		this.state = State.Normal;
	}

	public GameState.State getState(){
		return this.state;
	}

}
