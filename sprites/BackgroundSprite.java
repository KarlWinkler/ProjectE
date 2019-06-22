import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BackgroundSprite extends StaticSprite{

	private static Image image = null;

	public BackgroundSprite(double minX, double minY, double maxX, double maxY, boolean showImage) {

		if (image == null && showImage) {
			try {
				image = ImageIO.read(new File("res/GameOver.jpg"));
			}
			catch (IOException e) {
				e.printStackTrace();
				//				System.out.println(e.toString());
			}		
		}

		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
		this.showImage = showImage;



	}

	@Override
	public Image getImage() {
		return image;
	}


}
