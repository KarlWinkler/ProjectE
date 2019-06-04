import java.awt.Rectangle;
import java.util.ArrayList;

public class Sprite {

	final int SPRITE_WIDTH = 50; // sprite.get_width()
	final int SPRITE_HEIGHT =50; //sprite.get_height()
	private Movement movement = new Movement();

	protected long height = 50;
	protected long width = 50;
	
	public Sprite(long height, long width, double currentX, double currentY, double velocityX,
			double velocityY, double accelerationX, double accelerationY) {
		this.height = height;
		this.width = width;
		this.currentX = currentX;
		this.currentY = currentY;
		this.velocityX = velocityX;
		this.velocityY = velocityY;
		this.accelerationX = accelerationX;
		this.accelerationY = accelerationY;
	}

	protected double currentX = 20;
	protected double currentY = 20;
	protected double velocityX = 200;        	//PIXELS PER SECOND
	protected double velocityY = 0;          	//PIXELS PER SECOND
	protected double accelerationX = 0;          			//PIXELS PER SECOND PER SECOND 
	protected double accelerationY = 0;          		//PIXELS PER SECOND PER SECOND 
	
	public double getCurrentX() {
		return currentX;
	}

	public void setCurrentX(double currentX) {
		this.currentX = currentX;
	}

	public double getCurrentY() {
		return currentY;
	}

	public void setCurrentY(double currentY) {
		this.currentY = currentY;
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

	public long getHeight() {
		return height;
	}

	public long getWidth() {
		return width;
	}

	public double getAccelerationX() {
		return accelerationX;
	}

	public void setAccelerationX(double accelerationX) {
		this.accelerationX = accelerationX;
	}

	public double getAccelerationY() {
		return accelerationY;
	}

	public void setAccelerationY(double accelerationY) {
		this.accelerationY = accelerationY;
	}

	public void update(ArrayList<Rectangle> barriers, long actual_delta_time ) {
		updateVelocity(actual_delta_time);
		updatePosition(barriers, actual_delta_time);
	}

    private void updateVelocity(long actual_delta_time) {
    	this.velocityY += (accelerationY * actual_delta_time * 0.001);
    	//System.out.printf("currentX: %f; currentY: %f; velocityX: %f; velocityY: %f\n",sprite.currentX, sprite.currentY, sprite.velocityX, sprite.velocityY );
    }

	private void updatePosition(ArrayList<Rectangle> barriers, long actual_delta_time ) {
		
		//calculate new position assuming there are no changes in direction
	    double movement_x = (this.velocityX * actual_delta_time * 0.001);
	    double movement_y = (this.velocityY * actual_delta_time * 0.001);
	    double new_x = this.currentX + movement_x;
	    double new_y = this.currentY + movement_y;

	    long this_top = Math.round(this.currentY);
	    long this_bottom = this_top + SPRITE_HEIGHT;
	    long this_left = Math.round(this.currentX);
	    long this_right = this_left + SPRITE_WIDTH;
		 
	    for (Rectangle barrier : barriers) {
			//colliding with top edge of barrier?
	        //?moving down (can only collide if sprite is moving down)
	        if (movement_y > 0) {
	            //?is the this to the left || right of the barrier? (can only collide if this is not the case) 
	            if (!( (this_left > barrier.getMaxX()) || (this_right < barrier.getMinX()))) {
	                this.calculateBounce(this_bottom, movement_y, barrier.getMinY(), movement);
	                if (movement.didCollide) {
	                	new_y = movement.newLocaton -SPRITE_HEIGHT;
	                	this.velocityY = (movement.newVelocity * 1000) / actual_delta_time;
	                }
	            }
	        }

	        //colliding with bottom edge of barrier?
	        //?moving up (can only collide if sprite is moving up)
	        if (movement_y < 0) {
	            //is the this to the left || right of the barrier? (can only collide if this is not the case) 
	            if (! ((this_left > barrier.getMaxX()) || (this_right < barrier.getMinX()))) {
	                this.calculateBounce(this_top, movement_y, barrier.getMaxY(), movement);
	                if (movement.didCollide) {
	                	new_y = movement.newLocaton;
	                	this.velocityY = (movement.newVelocity * 1000) / actual_delta_time;
	                }
	            }
	        }

	        //colliding with left edge of barrier?
	        //?moving right (can only collide if sprite is moving to right)
	        if (movement_x > 0) {
	            //?is the this above || below the barrier? (can only collide if this is not the case) 
	            if (!( (this_top > barrier.getMaxY()) || (this_bottom < barrier.getMinY()))) {
	                this.calculateBounce(this_right, movement_x, barrier.getMinX(), movement);
	                if (movement.didCollide) {
	                	new_x = movement.newLocaton - SPRITE_WIDTH;
	                	this.velocityX = (movement.newVelocity * 1000) / actual_delta_time;
	                }
	            }
	        }

	        //colliding with right edge of barrier?
	        //?moving left (can only collide if sprite is moving to left)
	        if (movement_x < 0) {
	            //?is the this above || below the barrier? (can only collide if this is not the case) 
	            if (!( (this_top > barrier.getMaxY()) || (this_bottom < barrier.getMinY()))) {
	                this.calculateBounce(this_left, movement_x, barrier.getMaxX(), movement);
	                if (movement.didCollide) {
	                	this.velocityX = (movement.newVelocity * 1000) / actual_delta_time;
	                	new_x = movement.newLocaton;
	                }
	            }
	        }
	    }
	    
	    this.setCurrentX(new_x);
	    this.setCurrentY(new_y);
                    
	}
	    
	protected void calculateBounce(double location, double velocity, double boundary, Movement movement) {

		double distanceToBoundary = 0;
			
	    if ( (velocity > 0) && (location <= boundary) && ((location + velocity) >= boundary)) {
	        distanceToBoundary = (boundary - location);
	        movement.newLocaton = boundary - (velocity - distanceToBoundary);
	        movement.newVelocity = - velocity;
	        movement.didCollide = true;
	    }
	    else if (velocity < 0 && (location >= boundary) && ((location + velocity) <= boundary)) {
	        distanceToBoundary = (location - boundary);
	        movement.newLocaton = boundary - (velocity + distanceToBoundary);
	        movement.newVelocity = - velocity;
	        movement.didCollide = true;
	    }
	    else {
	    	movement.newLocaton = location + velocity;
	    	movement.newVelocity = velocity;
	    	movement.didCollide = false;
	    }
	    
	}
	
	
}
