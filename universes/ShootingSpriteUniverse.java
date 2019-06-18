import java.awt.Rectangle;
import java.util.ArrayList;

public class ShootingSpriteUniverse extends Universe {

	AudioPlayer player = new AudioPlayer();
	
	public ShootingSpriteUniverse () {
		
		super();
	
		player1 = new SpaceShipSprite(0,0);
		background = new StarfieldBackground();
		
		this.activeSprites.add(player1);
	}
	
	public boolean centerOnPlayer() {
		return true;
	}

	public void update(KeyboardInput keyboard, long actual_delta_time) {

//		if (player.isPlayCompleted()) {
//			player.playAsynchronous("res/Cold-Moon.wav");
//		}
		
		if (keyboard.keyDownOnce(27)) {
			complete = true;
		}
		
		updateSprites(keyboard, actual_delta_time);
		disposeSprites();
	}
	
}
