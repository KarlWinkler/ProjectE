
public class AnimatedSpritesUniverse extends Universe {

	public AnimatedSpritesUniverse () {
		
		super();
		
		player1 = new AnimatedSprite(-300,0, 30);
		activeSprites.add(player1);
		
		activeSprites.add(new RotatingSprite(150,-30));
		
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