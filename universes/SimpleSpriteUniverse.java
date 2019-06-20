import java.util.ArrayList;

public class SimpleSpriteUniverse extends Universe {

	public SimpleSpriteUniverse () {
		
		super();

		background = new GameBackground();
		//ArrayList<StaticSprite> barriers = ((MappedBackground)background).getBarriers();
		
		//((GameBackground) background).getBarriers();
		
		this.setXCenter(0);
		this.setYCenter(0);
		player1 = new SimpleSprite(0,350);
		activeSprites.add(player1);
		
		//top
		staticSprites.add(new BarrierSprite(-450,-450, 450, -434, true));
		//bottom
		staticSprites.add(new BarrierSprite(-450,434, 450, 459, true));
		//left
		staticSprites.add(new BarrierSprite(-450,-450, -434, 450, true));
		//right
		staticSprites.add(new BarrierSprite(434,-450, 450, 450, true));
		
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
