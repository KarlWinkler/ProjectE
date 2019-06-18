import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class SpaceShipSprite extends ActiveSprite {

	private static Image[] rotatedImages = new Image[360];
	private AudioPlayer thrustSound = new AudioPlayer();
	private AudioPlayer bulletSound = new AudioPlayer();
	
	private double velocityX = 000;        	//PIXELS PER SECOND
	private double velocityY = 0;          	//PIXELS PER SECOND
	private double reloadTime = 0;
	private Image rotatedImage;

	private double ACCELERATION = 400;          			//PIXELS PER SECOND PER SECOND 
	private double ROTATION_SPEED = 180;	//degrees per second	
	private double currentAngle = 0;
	private int currentImageAngle = 0;

	public SpaceShipSprite(double centerX, double centerY) {

		super();
		this.setCenterX(centerX);
		this.setCenterY(centerY);		

		Image image = null;
		try {
			image = ImageIO.read(new File("res/spaceship.png"));
		}
		catch (IOException e) {
			System.out.print(e.toString());
		}

		if (image != null) {
			for (int i = 0; i < 360; i++) {
				rotatedImages[i] = ImageRotator.rotate(image, i);			
			}
			this.setHeight(image.getHeight(null));
			this.setWidth(image.getWidth(null));
		}
		
				
	}
	
	@Override
	public Image getImage() {
		return rotatedImages[(int)currentAngle];
	}
	
	public Image getImage(int angle) {
		return rotatedImages[angle];
	}
		
	@Override
	public void update(Universe universe, KeyboardInput keyboard, long actual_delta_time) {

		//LEFT	
		if (keyboard.keyDown(37)) {
			currentAngle -= (ROTATION_SPEED * (actual_delta_time * 0.001));
		}
		// RIGHT
		if (keyboard.keyDown(39)) {
			currentAngle += (ROTATION_SPEED * (actual_delta_time * 0.001));
		}
		//UP
		if (keyboard.keyDown(38)) {
			if (thrustSound.isPlayCompleted()) {
				thrustSound.playAsynchronous("res/thrust.wav");
			}
			double angleInRadians = Math.toRadians(currentAngle);
			velocityX += Math.cos(angleInRadians) * ACCELERATION * actual_delta_time * 0.001;
			velocityY += Math.sin(angleInRadians) * ACCELERATION * actual_delta_time * 0.001;
		}
		else {
			if (thrustSound.isPlayCompleted() == false) {
				thrustSound.setStop(true);
			}
		}
		// DOWN
		if (keyboard.keyDown(40)) {
		}
		//SPACE
		if (keyboard.keyDown(32)) {
			shoot(universe);	
		}

	    if (currentAngle >= 360) {
	    	currentAngle -= 360;
	    }
	    if (currentAngle < 0) {
	    	currentAngle += 360;
	    }	
	    
	    currentAngle %= 360;
		
	    this.setHeight(rotatedImages[(int)currentAngle].getHeight(null));
	    this.setWidth(rotatedImages[(int)currentAngle].getWidth(null));
	    
		//calculate new position assuming there are no changes in direction
	    double movement_x = (this.velocityX * actual_delta_time * 0.001);
	    double movement_y = (this.velocityY * actual_delta_time * 0.001);
	    this.setCenterX(this.getCenterX() + movement_x);
	    this.setCenterY(this.getCenterY()  + movement_y);
	    
		reloadTime -= actual_delta_time;
	}
	
	public void shoot(Universe universe) {
		
		if (reloadTime <= 0) {
			double currentVelocity = Math.sqrt((velocityX * velocityX) + (velocityY * velocityY));
			double bulletVelocity = 750; // + currentVelocity;
			double ratio = (bulletVelocity / currentVelocity);
//			 = ratio * velocityX + velocityX;
//			double bulletVelocityY = ratio * velocityY + velocityY;
			double angleInRadians = Math.toRadians(currentAngle);
			double bulletVelocityX = Math.cos(angleInRadians) * bulletVelocity + velocityX;
			double bulletVelocityY = Math.sin(angleInRadians) * bulletVelocity + velocityY;
			
			double bulletCurrentX = this.getCenterX();
			double bulletCurrentY = this.getCenterY();

			BulletSprite bullet = new BulletSprite(bulletCurrentX, bulletCurrentY, bulletVelocityX, bulletVelocityY);
			universe.getActiveSprites().add(bullet);
			if (bulletSound.isPlayCompleted()) {
				bulletSound.playAsynchronous("res/missile.wav");
			}
			
			reloadTime = 100;
			
		}
	}

	
}
