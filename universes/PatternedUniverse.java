import java.awt.Rectangle;
import java.util.ArrayList;

public class PatternedUniverse extends Universe {

	public PatternedUniverse () {
		
		super();
		
		background = new AberhartBackground();

		player1 = new SimpleSprite(0,0);
		activeSprites.add(player1);
		
	}
	
	public boolean centerOnPlayer() {
		return true;
	}		

	public void update(KeyboardInput keyboard, long actual_delta_time) {

		if (keyboard.keyDownOnce(27)) {
			complete = true;
		}
		
		updateSprites(keyboard, actual_delta_time);
		disposeSprites();
	}
	
}
