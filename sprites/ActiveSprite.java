import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public abstract class ActiveSprite extends Sprite {

	//TODO - can this be made private?
	private double centerX = 0;
	private double centerY = 0;
	private double width = 50;
	private double height = 50;
	private boolean dispose = false;

	public abstract void update(Universe level, KeyboardInput keyboard, long actual_delta_time);
	public abstract Image getImage();
	
	public ActiveSprite() {
	}
	
	public final double getMinX() {
		return centerX - (width / 2);
	}

	public final double getMaxX() {
		return centerX + (width / 2);
	}

	public final double getMinY() {
		return centerY - (height / 2);
	}

	public final double getMaxY() {
		return centerY + (height / 2);
	}

	public final double getHeight() {
		// TODO Auto-generated method stub
		return height;
	}

	public final double getWidth() {
		// TODO Auto-generated method stub
		return width;
	}

	public double getCenterX() {
		return centerX;
	};

	public double getCenterY() {
		return centerY;
	};
	
	public final void setCenterX(double centerX) {
		this.centerX = centerX;
	};
	
	public final void setCenterY(double centerY) {
		this.centerY= centerY;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public void setHeight(double height) {
		this.height = height;
	}
	
	public final void addCenterX(double delta) {
		this.centerX += delta;		
	}

	public final void addCenterY(double delta) {
		this.centerY += delta;		
	}
	
	public final void setMinX(double minX) {
		this.centerX =  minX + (width / 2);
	};

	public final void setMinY(double minY) {
		this.centerY = minY + (height / 2);;
	}
				
	public boolean getDispose() {
		return dispose;
	}

	public void setDispose() {
		this.dispose = true;
	}
	
}
