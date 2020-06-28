package Environment;

import java.awt.Image;
import java.awt.image.BufferedImage;

public class ImageControlEngine {
	public static BufferedImage CallImage(BufferedImage bi, int x, int y) {	//transformed size
		BufferedImage resized = new BufferedImage(x,y,BufferedImage.TYPE_INT_ARGB);
		resized.getGraphics().drawImage(bi.getScaledInstance(x, y, Image.SCALE_AREA_AVERAGING),0,0,x,y,null);
		return resized;
	}
}
