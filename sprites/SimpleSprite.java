import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SimpleSprite extends ActiveSprite {

	private final double VELOCITY = 200;

	private static Image image;	

	public SimpleSprite(double centerX, double centerY) {
		super();

		this.setCenterX(centerX);
		this.setCenterY(centerY);

		if (image == null) {
			try {
				image = ImageIO.read(new File("res/simple-sprite.png"));
				this.setHeight(this.image.getHeight(null));
				this.setWidth(this.image.getWidth(null));
			}
			catch (IOException e) {
				System.out.println(e.toString());
			}		
		}		
	}

	public Image getImage() {
		return image;
	}

	@Override
	public void update(Universe universe, KeyboardInput keyboard, long actual_delta_time) {

		double velocityX = 0;
		double velocityY = 0;
		
		//LEFT	
		if (keyboard.keyDown(37)) {
			velocityX = -VELOCITY;
		}
		//UP
		if (keyboard.keyDown(38)) {
			velocityY = -VELOCITY;			
		}
		// RIGHT
		if (keyboard.keyDown(39)) {
			velocityX += VELOCITY;
		}
		// DOWN
		if (keyboard.keyDown(40)) {
			velocityY += VELOCITY;			
		}

		double deltaX = actual_delta_time * 0.001 * velocityX;
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
