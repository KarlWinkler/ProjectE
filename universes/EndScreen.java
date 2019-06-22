
public class EndScreen extends Universe {

	
	int score;
	
	public EndScreen () {
	
		super();
		BackgroundSprite image = new BackgroundSprite(-750,-450,750,450,true);
		staticSprites.add(image);
//		player1 = new BlinkySprite(0,0);
//		activeSprites.add(player1);
		
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
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public int getScore() {
		return score;
	}
	
	protected void setEndGame(int score){
		System.out.println(AnimationFrame.score);
	}

}