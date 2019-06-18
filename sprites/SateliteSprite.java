import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class SateliteSprite extends ActiveSprite {

	public static double GRAVITATIONAL_CONSTANT = 1E-1;
	
	private Image image;
	
	private double velocityX = 000;        	//PIXELS PER SECOND
	private double velocityY = 0;          	//PIXELS PER SECOND
	private double mass = 0;
	private double diameter = 0;			//100 KM PER PIXEL
	private boolean anchored = false;
	private double accelerationX = 0;          			//PIXELS PER SECOND PER SECOND 
	private double accelerationY = 0;          		//PIXELS PER SECOND PER SECOND 
	
	public SateliteSprite(double centerX, double centerY, double velocityX, double velocityY, double mass, double diameter, boolean anchored, String imageFile) {

		super();
		this.setCenterX(centerX);
		this.setCenterY(centerY);		

		this.velocityX = velocityX;
		this.velocityY = velocityY;
		this.mass = mass;
		this.diameter = diameter;
		this.anchored = anchored;

		if (image == null) {
			try {
				image = ImageIO.read(new File(imageFile));
			}
			catch (IOException e) {
				System.err.println(e.toString());
			}		
		}
		this.setHeight((int) diameter);
		this.setWidth((int) diameter);
		
	}
		
	public double getVelocityX() {
		return velocityX;
	}

	public void setVelocityX(double velocityX) {
		this.velocityX = velocityX;
	}

	public double getVelocityY() {
		return velocityY;
	}

	public void setVelocityY(double velocityY) {
		this.velocityY = velocityY;
	}

	public double getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}

	public boolean isAnchored() {
		return anchored;
	}

	@Override
	public Image getImage() {
		return image;
	}
		
	@Override
	public void update(Universe level, KeyboardInput keyboard, long actual_delta_time) {

		//calculate new position assuming there are no changes in direction
	    double movement_x = (this.velocityX * actual_delta_time * 0.001);
	    double movement_y = (this.velocityY * actual_delta_time * 0.001);
	    this.addCenterX(movement_x);
	    this.addCenterY(movement_y);
		
		for (ActiveSprite other : level.getActiveSprites()) {
			if (other instanceof SateliteSprite && other != this) {
				SateliteSprite satelite = (SateliteSprite)other;
				if (satelite.isAnchored() == false) {
					//calculate the attraction vector
					//first, calculate distance.... leave it in squared form
					double deltaX = satelite.getCenterX() - this.getCenterX();
					double deltaY = satelite.getCenterY() - this.getCenterY();
					double distanceSquared = deltaX * deltaX + deltaY * deltaY;
					double force = GRAVITATIONAL_CONSTANT * ((this.mass * satelite.mass) / distanceSquared);
					//force == mass * acceleration; thus, acceleration = force / mass
					double acceleration = force / satelite.mass;
					double vector = acceleration * actual_delta_time * 0.001;
					double tangent = deltaY / deltaX;
					//too lazy to do the math here... vector should equal sqrt(attractionX ^ 2 + attractionY ^ 2);
					double attractionX = deltaX * vector * -1;
					double attractionY = deltaY * vector * -1;
					satelite.velocityX += attractionX;
					satelite.velocityY += attractionY;
					//System.out.println(String.format("satelite.velocityX %.10f;  satelite.velocityY %.10f; delta %d", satelite.velocityX, satelite.velocityY, actual_delta_time));
				}
			}
		}				
	}				
}
