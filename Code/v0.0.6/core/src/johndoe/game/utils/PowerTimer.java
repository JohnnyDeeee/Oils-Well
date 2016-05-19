package johndoe.game.utils;

import johndoe.game.entities.player.Player;

import com.badlogic.gdx.utils.Timer.Task;

public class PowerTimer extends Task{

	private Player player;
	
	public PowerTimer(Player player) {
		this.player = player;
	}
	
	@Override
	public void run() {
		player.takePower();
		player.setSpeed(2.0f);
	}

}
