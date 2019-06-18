import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class AnimatedSprite extends ActiveSprite {
	
	private int currentFrame = 0;
	private long elapsedTime = 0;
	private final static int FRAMES = 150;
	private double framesPerSecond = 30;
	private double milliSecondsPerFrame = 1000 / framesPerSecond;
	private static Image[] frames = new Image[FRAMES];
	private static boolean framesLoaded = false;
			
	public AnimatedSprite(double centerX, double centerY, double framesPerSecond) {

		super();
		this.setCenterX(centerX);
		this.setCenterY(centerY);		

		this.framesPerSecond = framesPerSecond;
		this.milliSecondsPerFrame = 1000 / framesPerSecond;
		long startTime = System.currentTimeMillis();
		
		if (framesLoaded == false) {
			for (int frame = 0; frame < FRAMES; frame++) {
				String filename = "res/animated-earth/frame_" + String.format("%03d", frame) + "_delay-0.04s.gif";
				try {
					frames[frame] = ImageIO.read(new File(filename));
				}
				catch (IOException e) {
					System.err.println(e.toString());
				}		
			}
			
			if (frames[0] != null) {
				setWidth(frames[0].getWidth(null));
				setHeight(frames[0].getHeight(null));
				framesLoaded = true;
			}
		}		
	}
		
	@Override
	public Image getImage() {
		return frames[currentFrame];
	}
		
	@Override
	public void update(Universe level, KeyboardInput keyboard, long actual_delta_time) {

		elapsedTime += actual_delta_time;
		long elapsedFrames = (long) (elapsedTime / milliSecondsPerFrame);
		currentFrame = (int) (elapsedFrames % FRAMES);
		
	}			

}
