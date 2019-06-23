import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class BulletSprite extends ActiveSprite {

	private static Image image;
	private static final int WIDTH = 20;
	private static final int HEIGHT = 20;

	private AudioPlayer bulletSound = null;
	
	private double velocityX = 000;        	//PIXELS PER SECOND
	private double velocityY = 0;          	//PIXELS PER SECOND
	private double accelerationX = 0;          			//PIXELS PER SECOND PER SECOND 
	private double accelerationY = 0;          		//PIXELS PER SECOND PER SECOND 
	private long lifeTime = 1000;
	
	public BulletSprite(double centerX, double centerY, double velocityX, double velocityY) {

		super();
		this.setCenterX(centerX);
		this.setCenterY(centerY);
		this.setWidth(WIDTH);
		this.setHeight(HEIGHT);

		this.velocityX = velocityX;
		this.velocityY = velocityY;
		
		if (image == null) {
			try {
				image = ImageIO.read(new File("res/bullet.png"));
			}
			catch (IOException e) {
				System.err.println(e.toString());
			}
		}
		
		if (bulletSound == null) {
			bulletSound = new AudioPlayer();
		}
		
		bulletSound.playAsynchronous("res/Gun2.wav");
		
	}
	
	@Override
	public Image getImage() {
		return image;
	}
		
	@Override
	public void update(Universe level, KeyboardInput keyboard, long actual_delta_time) {

	    double movement_x = (this.velocityX * actual_delta_time * 0.001);
	    double movement_y = (this.velocityY * actual_delta_time * 0.001);
	    
	    this.addCenterX(movement_x);
	    this.addCenterY(movement_y);
	    
		

	    lifeTime -= actual_delta_time;
	    if (lifeTime < 0) {
	    	this.setDispose();
	    }	    			
	}		
	

	
}
