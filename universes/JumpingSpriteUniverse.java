import java.awt.Rectangle;
import java.util.ArrayList;

public class JumpingSpriteUniverse extends Universe {

	AudioPlayer audio = new AudioPlayer();
	
	public JumpingSpriteUniverse () {
		
		super();
	
		player1 = new JumpingSprite(0,-450);
		activeSprites.add(player1);
		
		activeSprites.add(new BouncingSprite(-100 , -400 , 200, 0));
		activeSprites.add(new BouncingSprite(0 , -400 , 200, 0));
		activeSprites.add(new BouncingSprite(100 , -400 , 200, 0));
		
		//top platform
		staticSprites.add(new BarrierSprite(-50,34, 50, 50, true));
		//bottom platform
		staticSprites.add(new BarrierSprite(-150,234, 150, 250, true));
		//bottom
		staticSprites.add(new BarrierSprite(-450,436, 450, 450, true));
		//left
		staticSprites.add(new BarrierSprite(-450,-450, -434, 450, true));
		//right
		staticSprites.add(new BarrierSprite(436,-450, 450, 450, true));
    	
	}
	
	public boolean centerOnPlayer() {
		return false;
	}

	public void update(KeyboardInput keyboard, long actual_delta_time) {

		if (audio.isPlayCompleted()) {
			audio.playAsynchronous("res/Cold-Moon.wav");
		}
		
		if (keyboard.keyDownOnce(27)) {
			audio.setStop(true);
			complete = true;
		}
		
		updateSprites(keyboard, actual_delta_time);
		disposeSprites();
	}
	
}
