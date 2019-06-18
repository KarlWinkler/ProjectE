import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class JumpingSprite extends ActiveSprite {

	private static Image image;
	
	private double velocityX = 000;        	//PIXELS PER SECOND
	private double velocityY = 0;          	//PIXELS PER SECOND
	private double ACCCELERATION_X = 5;		//PIXELS PER SECOND PER MILLISECOND
	private double MAX_VELOCITY_X = 300;	//PIXELS PER SECOND
	private double FRICTION_FACTOR_X = 0.95; 

	private boolean isJumping = false;
	private final double INITIAL_JUMP_VELOCITY = 600; //pixels / second
	
	private CollisionDetection collisionDetection;
	TwoDimensionBounce bounce;
	
	public JumpingSprite(double centerX, double centerY) {

		super();
		this.setCenterX(centerX);
		this.setCenterY(centerY);		

		collisionDetection = new CollisionDetection();
		collisionDetection.setBounceFactorX(0.5);
		collisionDetection.setBounceFactorY(0);
		bounce = new TwoDimensionBounce();
		
		if (image == null) {
			try {
				image = ImageIO.read(new File("res/simple-sprite.png"));
			}
			catch (IOException e) {
				System.err.println(e.toString());
			}		
		}
		this.setHeight(image.getHeight(null));
		this.setWidth(image.getWidth(null));
		
	}
		
	public Image getImage() {
		return image;
	}
		
	@Override
	public void update(Universe level, KeyboardInput keyboard, long actual_delta_time) {

		boolean onGround = isOnGround(level);
		
		if (onGround) {

			if (keyboard.keyDown(32)) {
				isJumping = true;
				this.velocityY -= INITIAL_JUMP_VELOCITY;
				onGround = false;
			}
			// RIGHT
			if (keyboard.keyDown(39)) {
				velocityX += + ACCCELERATION_X;
				if (velocityX > MAX_VELOCITY_X) {
					velocityX = MAX_VELOCITY_X;
				}
			}
			// LEFT
			else if (keyboard.keyDown(37)) {
				velocityX -= ACCCELERATION_X;
				if (velocityX < - MAX_VELOCITY_X) {
					velocityX = - MAX_VELOCITY_X;
				}
			}
			else {
				this.velocityX = this.velocityX * FRICTION_FACTOR_X;
			}
		}
		else {
			
		}
		
		collisionDetection.calculate2DBounce(bounce, this, level.getStaticSprites(), velocityX, velocityY, actual_delta_time);
		this.setMinX(bounce.newX);
		this.setMinY(bounce.newY);
		this.velocityX = bounce.newVelocityX;
		this.velocityY = bounce.newVelocityY;

		if (onGround == true) {
			this.velocityY = 0;
		} else {
			this.velocityY = this.velocityY + level.getAccelarationY() * 0.001 * actual_delta_time;
		}

	}
	
	private boolean isOnGround(Universe level) {
		boolean onGround = false;
		for (StaticSprite sprite: level.getStaticSprites()) {
			boolean bottomColiding = this.getMaxY() >= (sprite.getMinY()) && this.getMaxY() <= sprite.getMinY();
			boolean withinRange = this.getMaxX() > sprite.getMinX() && this.getMinX() < sprite.getMaxX();
			if (bottomColiding && withinRange) {
				onGround = true;
				break;
			}
		}
		return onGround;
	}
		
}
