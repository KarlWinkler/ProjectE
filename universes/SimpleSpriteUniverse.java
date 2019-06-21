import java.util.ArrayList;

public class SimpleSpriteUniverse extends Universe {

	public SimpleSpriteUniverse () {
		
		super();

		background = new GameBackground();
		//ArrayList<StaticSprite> barriers = ((MappedBackground)background).getBarriers();
		
		//((GameBackground) background).getBarriers();
		
		ActiveSprite bear = new BlinkySprite(0, - 425);
		this.setXCenter(0);
		this.setYCenter(0);
		player1 = new SimpleSprite(0,350);
		activeSprites.add(player1);
		activeSprites.add(bear);
		
		//top
		staticSprites.add(new BarrierSprite(-750,-384, 750, -434, true));
		//bottom
		staticSprites.add(new BarrierSprite(-750,400, 750, 384, true));
		//left
		staticSprites.add(new BarrierSprite(-750,-450, -734, 450, true));
		//right
		staticSprites.add(new BarrierSprite(734,-450, 750, 450, true));
		
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
