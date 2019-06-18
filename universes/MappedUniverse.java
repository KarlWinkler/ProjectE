import java.awt.Rectangle;
import java.util.ArrayList;

public class MappedUniverse extends Universe {

	public MappedUniverse () {
		
		super();
		
		background = new MappedBackground();
		ArrayList<StaticSprite> barriers = ((MappedBackground)background).getBarriers();
		
		((MappedBackground) background).getBarriers();

		player1 = new SimpleSprite(MappedBackground.TILE_HEIGHT * 2, MappedBackground.TILE_WIDTH * 2);
		player1.setHeight(MappedBackground.TILE_HEIGHT * 0.9);
		player1.setWidth(MappedBackground.TILE_WIDTH * 0.9);
		
		activeSprites.add(player1);
		staticSprites.addAll(barriers);
		
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
