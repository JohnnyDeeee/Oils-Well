package johndoe.game.entities.player;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.Animation;

public class PlayerInput extends InputAdapter{

	private Player player;
	@SuppressWarnings("unused")
	private Animation move_up, move_down, move_left, move_right, idle_up, idle_down, idle_left, idle_right, fly_left, fly_right, fly_down;
	
	public PlayerInput(Player player){
		this.player = player;
		this.move_up = player.getAnimation("move_up");
		this.move_down = player.getAnimation("move_down");
		this.move_left = player.getAnimation("move_left");
		this.move_right = player.getAnimation("move_right");
		this.idle_up = player.getAnimation("idle_up");
		this.idle_down = player.getAnimation("idle_down");
		this.idle_left = player.getAnimation("idle_left");
		this.idle_right = player.getAnimation("idle_right");
		this.fly_left = player.getAnimation("fly_left");
		this.fly_right = player.getAnimation("fly_right");
		this.fly_down = player.getAnimation("fly_down");
	}
	
	public void checkInput(int keycode){
		
	}
	
	@Override
	public boolean keyDown(int keycode) {
		switch(keycode){
		case Keys.W:
			if (!player.isMovingDown() && !player.isMovingLeft() && !player.isMovingRight()) {
				player.setLoop(true);
				player.setCurrent_animation(move_up);
				player.setUpMove(true);
				player.setFacing(0);
			}
			break;
		case Keys.S:
			if (!player.isMovingUp() && !player.isMovingLeft() && !player.isMovingRight()) {
				player.setLoop(true);
				
				switch (player.getFacing()) {
				case 0:
					player.setCurrent_animation(fly_down);
					break;
				case 1:
					player.setCurrent_animation(fly_left);
					break;
				case 3:
					player.setCurrent_animation(fly_right);
					break;
				default:
					break;
				}
				
				player.setDownMove(true);
			}
			break;
		case Keys.A:
			if (!player.isMovingDown() && !player.isMovingRight() && !player.isMovingUp() && player.getFacing() != 1) {
				player.setLoop(true);
				player.setCurrent_animation(move_left);
				player.setLeftMove(true);
				player.setFacing(3);
			}
			break;
		case Keys.D:
			if (!player.isMovingDown() && !player.isMovingLeft() && !player.isMovingUp() && player.getFacing() != 3) {
				player.setLoop(true);
				player.setCurrent_animation(move_right);
				player.setRightMove(true);
				player.setFacing(1);
			}
			break;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch(keycode){
		case Keys.W:
			if (!player.isMovingDown() && !player.isMovingLeft() && !player.isMovingRight()) {
				player.setLoop(false);
				player.setCurrent_animation(idle_up);
				player.setUpMove(false);
			}
			break;
		case Keys.S:
			if (!player.isMovingUp() && !player.isMovingLeft() && !player.isMovingRight()) {
				player.setLoop(true);
				
				switch (player.getFacing()) {
				case 0:
					player.setCurrent_animation(idle_up);
					break;
				case 1:
					player.setCurrent_animation(idle_right);
					break;
				case 3:
					player.setCurrent_animation(idle_left);
					break;
				default:
					break;
				}
				
				player.setDownMove(false);
			}
			break;
		case Keys.A:
			if (!player.isMovingDown() && !player.isMovingRight() && !player.isMovingUp()) {
				player.setLoop(false);
				player.setCurrent_animation(idle_left);
				player.setLeftMove(false);
			}
			break;
		case Keys.D:
			if (!player.isMovingDown() && !player.isMovingLeft() && !player.isMovingUp()) {
				player.setLoop(false);
				player.setCurrent_animation(idle_right);
				player.setRightMove(false);
			}
			break;
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

}
