
public class EndScreen extends Universe {

	
	int score = 5;
	
	public EndScreen () {
	
		super();
		BackgroundSprite image = new BackgroundSprite(-750,-450,750,450,true);
		staticSprites.add(image);

		
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