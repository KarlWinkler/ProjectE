import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BlinkySprite extends ActiveSprite {

	private static final double VELOCITY = 200;
	private static final int WIDTH = 50;
	private static final int HEIGHT = 50;

	private static Image normalImage;
	private static Image ghostImage;
	boolean isGhost = false;
	
	private long elapsedTime = 0;

	public BlinkySprite(double centerX, double centerY) {

		super();
		this.setCenterX(centerX);
		this.setCenterY(centerY);	
		this.setWidth(WIDTH);
		this.setHeight(HEIGHT);

		if (normalImage == null) {
			try {
				normalImage = ImageIO.read(new File("res/blinky.png"));
				ghostImage = ImageIO.read(new File("res/ghost.png"));
			}
			catch (IOException e) {
				System.err.println(e.toString());
			}		
		}
		
	}

	public Image getImage() {
		if (this.isGhost) {
			return ghostImage;
		} else {
			return normalImage;
		}
	}

	@Override
	public void update(Universe level, KeyboardInput keyboard, long actual_delta_time) {

		elapsedTime += actual_delta_time;
		
		//how many 2-second periods have elapsed?
		long periods = (elapsedTime / 2000);
		//every odd period, this sprite becomes a ghost
		isGhost = ((periods % 2) == 1);
		
		
		double newX = getCenterX();
		double newY = getCenterY();

		if (isGhost) {
			newX += actual_delta_time * 0.001 * VELOCITY;
		} else {
			newX -= actual_delta_time * 0.001 * VELOCITY;
		}

		setCenterX(newX);
		setCenterY(newY);

	}

}
