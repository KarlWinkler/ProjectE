import java.awt.Image;
import java.util.Random;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BlinkySprite extends ActiveSprite {

	private static final double VELOCITY = 100;
	private static final int WIDTH = 50;
	private static final int HEIGHT = 50;

	private static Image normalImage;
	int speedScaling = 0;
	int gameScore = speedScaling;

//	private static Image ghostImage;
//	boolean isGhost = true;
	
	private int randomNumber() { 
		Random rand = new Random();

		// Obtain a number between [0 - 49].
		int n = rand.nextInt(1200);
		
		n -= 600;
		// Add 1 to the result to get a number from the required range
		// (i.e., [1 - 50]).
		n += 1;
		
		return n;
	}
	
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
//		if (keyboard.keyDown(37)) {
//			velocityX = -VELOCITY;
		
//		}
		
//		velocityY = +VELOCITY * scoreMultiplyer ;
		velocityY = (200 + speedScaling) ;
//		System.out.println(speedScaling);
		//UP
//		if (keyboard.keyDown(38)) {
//			velocityY = -VELOCITY;			
//		}
		// RIGHT
//		if (keyboard.keyDown(39)) {
//			velocityX += VELOCITY;
//		}
		// DOWN
//		if (keyboard.keyDown(40)) {
//			velocityY += VELOCITY;			
//		}
		
		
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
			
//			TESTING
//			BackgroundSprite image = new BackgroundSprite(-750,-450,750,450,true);
//			universe.staticSprites.add(image);
			
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
					colliding = true;
					break;					
				}
			}
		}		
		return colliding;		
	}

}
