import java.awt.Image;
import java.util.Random;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BearSprite extends ActiveSprite {

	private static final double VELOCITY = 100;
	private static final int WIDTH = 50;
	private static final int HEIGHT = 50;

	private static Image normalImage;
	int speedScaling = 0;
	int gameScore = speedScaling;

	
	private int randomNumber() { 
		Random rand = new Random();


		int n = rand.nextInt(1200);
		
		n -= 600;
		n += 1;
		
		return n;
	}
	

	public BearSprite(double centerX, double centerY) {

		super();
		this.setCenterX(centerX);
		this.setCenterY(centerY);	
		this.setWidth(WIDTH);
		this.setHeight(HEIGHT);

		if (normalImage == null) {
			try {
				normalImage = ImageIO.read(new File("res/BearSprite.png"));
			}
			catch (IOException e) {
				System.err.println(e.toString());
			}		
		}
		
	}

	public Image getImage() {

			return normalImage;

	}

	@Override
	public void update(Universe universe, KeyboardInput keyboard, long actual_delta_time) {
		
		
		double velocityX = 0;
		double velocityY = 0;
		

		velocityY = (200 + speedScaling) ;

		
		double deltaX = actual_delta_time * 0.001 * velocityX;
		double deltaY = actual_delta_time * 0.001 * velocityY;
		
		
		if (checkCollisionWithBullets(universe, deltaX, deltaY)){
			gameScore += 5;
			speedScaling = speedScaling + 3;
			setMinX(randomNumber());
			setMinY(-450); 
			universe.setScore(gameScore);
		}
				
				
		if (checkCollisionWithBarrier(universe, deltaX, 0) == true || checkCollisionWithBarrier(universe, 0, deltaY) == true) {
//			gameOver
			universe.setEndGame(gameScore);
			universe.setComplete(true);

		}

			
		if (checkCollisionWithBarrier(universe, deltaX, 0) == false) {
			this.addCenterX(deltaX);
		}
		
		
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
	
	private boolean checkCollisionWithBullets(Universe sprites, double deltaX, double deltaY) {

		boolean colliding = false;

		for (ActiveSprite activeSprite : sprites.getActiveSprites()) {
			if (activeSprite instanceof BulletSprite) {
				if (CollisionDetection.overlaps(this.getMinX() + deltaX, this.getMinY() + deltaY, 
						this.getMaxX()  + deltaX, this.getMaxY() + deltaY, 
						activeSprite.getMinX(),activeSprite.getMinY(), 
						activeSprite.getMaxX(), activeSprite.getMaxY())) {
					activeSprite.setDispose();
					colliding = true;
					break;					
				}
			}
		}		
		return colliding;		
	}

}
