import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BlinkySprite extends ActiveSprite {

	private static final double VELOCITY = 200;
	private static final int WIDTH = 50;
	private static final int HEIGHT = 50;

	private static Image normalImage;
//	private static Image ghostImage;
//	boolean isGhost = true;
	
	private long elapsedTime = 0;

	public BlinkySprite(double centerX, double centerY) {

		super();
		this.setCenterX(centerX);
		this.setCenterY(centerY);	
		this.setWidth(WIDTH);
		this.setHeight(HEIGHT);

		if (normalImage == null) {
			try {
				normalImage = ImageIO.read(new File("res/BearSprite.png"));
//				ghostImage = ImageIO.read(new File("res/ghost.png"));
			}
			catch (IOException e) {
				System.err.println(e.toString());
			}		
		}
		
	}

	public Image getImage() {
//		if (this.isGhost) {
//			return ghostImage;
//		} else {
			return normalImage;
//		}
	}

	@Override
	public void update(Universe universe, KeyboardInput keyboard, long actual_delta_time) {

		double velocityX = 0;
		double velocityY = 0;
		
		//LEFT	
		if (keyboard.keyDown(37)) {
			velocityX = -VELOCITY;
		}
		
		velocityY = +VELOCITY;
		//UP
//		if (keyboard.keyDown(38)) {
//			velocityY = -VELOCITY;			
//		}
		// RIGHT
		if (keyboard.keyDown(39)) {
			velocityX += VELOCITY;
		}
		// DOWN
//		if (keyboard.keyDown(40)) {
//			velocityY += VELOCITY;			
//		}
		
		
		double deltaX = actual_delta_time * 0.001 * velocityX;
		
		if (checkCollisionWithBarrier(universe, deltaX, 0) == true) {
			Animation.getNextUniverse();
		}
			
		if (checkCollisionWithBarrier(universe, deltaX, 0) == false) {
			this.addCenterX(deltaX);
		}
		
		double deltaY = actual_delta_time * 0.001 * velocityY;
		if (checkCollisionWithBarrier(universe, 0, deltaY) == false) {
			this.addCenterY(deltaY);
			
		}

	}
	private boolean checkCollisionWithBarrier(Universe sprites, double deltaX, double deltaY) {

		boolean colliding = false;

		for (StaticSprite staticSprite : sprites.getStaticSprites()) {
			if (staticSprite instanceof BarrierSprite) {
				if (CollisionDetection.overlaps(this.getMinX() + deltaX, this.getMinY() + deltaY, 
						this.getMaxX()  + deltaX, this.getMaxY() + deltaY, 
						staticSprite.getMinX(),staticSprite.getMinY(), 
						staticSprite.getMaxX(), staticSprite.getMaxY())) {
					colliding = true;
					break;					
				}
			}
		}		
		return colliding;		
	}

}
