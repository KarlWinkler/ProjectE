
public class BlinkySpriteUniverse extends Universe {

	public BlinkySpriteUniverse () {
		
		super();
		
		player1 = new BlinkySprite(0,0);
		activeSprites.add(player1);
		
	}
	
	public boolean centerOnPlayer() {
		return false;
	}		
	
	public void update(KeyboardInput keyboard, long actual_delta_time) {

		if (keyboard.keyDownOnce(27)) {
			complete = true;
		}
		
		updateSprites(keyboard, actual_delta_time);
		disposeSprites();
	}

}