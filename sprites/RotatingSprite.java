import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class RotatingSprite extends ActiveSprite {
	
	private double currentAngle = 90;
	private double ROTATION_SPEED = 72;	//degrees per second
	private final static int FRAMES = 360;
	private static Image[] rotatedImages = new Image[FRAMES];
	private static boolean framesLoaded = false;
			
	public RotatingSprite(double centerX, double centerY) {

		super();
		this.setCenterX(centerX);
		this.setCenterY(centerY);				

		Image defaultImage = null;

		if (framesLoaded == false) {
			try {
				defaultImage = ImageIO.read(new File("res/earth-polar-view.png"));
				
				setWidth(defaultImage.getWidth(null));
				setHeight(defaultImage.getHeight(null));
				
				for (int i = 0; i < FRAMES; i++) {
					rotatedImages[i] = ImageRotator.rotate(defaultImage, i);
				}

			}
			catch (IOException e) {
			}
			framesLoaded = true;
		}		
	}
			
	public Image getImage() {
		return rotatedImages[(int)currentAngle];
//		return rotatedImage;
	}
		
	@Override
	public void update(Universe level, KeyboardInput keyboard, long actual_delta_time) {
				
		currentAngle -= (ROTATION_SPEED * (actual_delta_time * 0.001));
	    if (currentAngle >= 360) {
	    	currentAngle -= 360;
	    }
	    if (currentAngle < 0) {
	    	currentAngle += 360;
	    }
	    
	    int frame = (int)currentAngle;
	    if (rotatedImages[frame] != null) {
		    this.setHeight(rotatedImages[frame].getHeight(null));
		    this.setWidth(rotatedImages[frame].getWidth(null));
	    }
	}			

	public boolean checkCollisionWithPlayers(double x, double y) {
		boolean isColliding = false;
		return isColliding;		
	}

}
