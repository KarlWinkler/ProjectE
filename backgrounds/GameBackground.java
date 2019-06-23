import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GameBackground extends Background{

	
	private Image aberhart;
   
    private int backgroundWidth = 0;
    private int backgroundHeight = 0;

    public GameBackground() {
    	try {
    		this.aberhart = ImageIO.read(new File("res/Background.png"));
    		backgroundWidth = aberhart.getWidth(null);
    		backgroundHeight = aberhart.getHeight(null);
    		
    		
    	}
    	catch (IOException e) {
    		//System.out.println(e.toString());
    	}		
    }
	
	
		
		
	
	
	public int getCol(int x) {
		//which col is x sitting at?
		int col = 0;
		if (backgroundWidth != 0) {
			col = (x / backgroundWidth);
			if (x < 0) {
				return col - 1;
			}
			else {
				return col;
			}
		}
		else {
			return 0;
		}
	}
	
	public int getRow(int y) {
		//which row is y sitting at?
		int row = 0;
		
		if (backgroundHeight != 0) {
			row = (y / backgroundHeight);
			if (y < 0) {
				return row - 1;
			}
			else {
				return row;
			}
		}
		else {
			return 0;
		}
	}

}
