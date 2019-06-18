import java.awt.Rectangle;
import java.util.ArrayList;

public class SateliteSpriteUniverse extends Universe {

	public SateliteSpriteUniverse () {
		
		super();
		
		SateliteSprite jupiter = new SateliteSprite(0, 0, 0, 0, 1000000, 100,false, "res/satelites/jupiter.png");
		this.player1 = jupiter;		
		SateliteSprite io = new SateliteSprite(-100,0, 0, 350 ,  1000, 36, false, "res/satelites/io.png");
		SateliteSprite europa = new SateliteSprite(-150,0, 0, 325,  1000, 31, false, "res/satelites/europa.png");
		SateliteSprite ganymede  = new SateliteSprite(-250,0, 0, 300,  1000, 52,false, "res/satelites/ganymede.png");
		SateliteSprite callisto = new SateliteSprite(-350,0, 0, 275,  1000, 48,false, "res/satelites/callisto.png");

		activeSprites.add(io);
		activeSprites.add(europa);
		activeSprites.add(callisto);
		activeSprites.add(ganymede);
		activeSprites.add(jupiter);
		
	}
	
	public void update(KeyboardInput keyboard, long actual_delta_time) {

		if (keyboard.keyDownOnce(27)) {
			complete = true;
		}

		updateSprites(keyboard, actual_delta_time);
		disposeSprites();
	}

	@Override
	public boolean centerOnPlayer() {
		// TODO Auto-generated method stub
		return true;
	}
	
}
