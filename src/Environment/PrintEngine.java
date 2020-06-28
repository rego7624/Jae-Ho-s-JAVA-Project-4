package Environment;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

import Object.Coordinate;

public class PrintEngine {
	public void drawCenteredString(Graphics g, String text, Rectangle rect) {
		Font font = g.getFont();
	    // Get the FontMetrics
	    FontMetrics metrics = g.getFontMetrics(font);
	    // Determine the X coordinate for the text
	    int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
	    // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
	    int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
	    // Draw the String
	    g.drawString(text, x, y);
	}
	
	public void drawFlexibleCenteredString(Graphics g, String text, Coordinate coord) {
		Font font = g.getFont();
	    // Get the FontMetrics
	    FontMetrics metrics = g.getFontMetrics(font);
	    int w = metrics.stringWidth(text);
	    int h = metrics.getHeight();
	    // Determine the X coordinate for the text
	    int x = coord.getX()-w/2 + (w - metrics.stringWidth(text)) / 2;
	    // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
	    int y = coord.getY() + ((h - metrics.getHeight()) / 2) + metrics.getAscent();
	    // Draw the String
	    g.drawString(text, x, y);
	}
}
