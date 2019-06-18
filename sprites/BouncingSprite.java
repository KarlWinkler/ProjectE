import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BouncingSprite extends ActiveSprite {

	private static final double VELOCITY = 200; //50 pixels per second
	private static final int WIDTH = 50;
	private static final int HEIGHT = 50;

	private static Image image = null;
	private double velocityX = 300; //per second
	private double velocityY = 300; //per second
	private CollisionDetection collisionDetection;
	TwoDimensionBounce bounce;

	public BouncingSprite(double centerX, double centerY, double velocityX, double velocityY) {

		super();
		this.setCenterX(centerX);
		this.setCenterY(centerY);	

		collisionDetection = new CollisionDetection();
		bounce = new TwoDimensionBounce();
		collisionDetection.setBounceFactorX(1);
		collisionDetection.setBounceFactorY(1);

		this.velocityX = velocityX;
		this.velocityY = velocityY;

		if (image == null) {
			try {
				image = ImageIO.read(new File("res/star.png"));
			}
			catch (IOException e) {
				System.err.println(e.toString());
			}
		}

		this.setHeight(HEIGHT);
		this.setWidth(WIDTH);

	}

	@Override
	public void update(Universe level, KeyboardInput keyboard, long actual_delta_time) {

		collisionDetection.calculate2DBounce(bounce, this, level.getStaticSprites(), velocityX, velocityY, actual_delta_time);
		this.setMinX(bounce.newX);
		this.setMinY(bounce.newY);
		this.velocityX = bounce.newVelocityX;
		this.velocityY = bounce.newVelocityY;			

		this.velocityX = this.velocityX + level.getAccelarationX() * 0.001 * actual_delta_time;
		this.velocityY = this.velocityY + level.getAccelarationY() * 0.001 * actual_delta_time;

	}

	@Override
	public Image getImage() {
		return image;
	}

}

