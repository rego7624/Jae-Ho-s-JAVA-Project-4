package Environment;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import Object.Coordinate;

public abstract class AnimationByTimeEngine {
	private long startFlag, elapsed, maintain;
	private String str;
	
	public AnimationByTimeEngine(String str, long maint) {
		this.str = str;
		this.maintain = maint;
	}
	
	public void update() {
		this.elapsed = System.currentTimeMillis() - this.startFlag;
	}
	public boolean isCompleted() {
		return elapsed > maintain;
	}
	
	public void touch() {
		this.startFlag = System.currentTimeMillis();
	}
	
	public double getProcessRate() {
		double rate = (double)this.elapsed/(double)this.maintain;
		if(rate>1)rate = 1;
		return rate;
	}
	
	public void draw(Graphics2D g, Coordinate center, Font font) {
		if(this.isCompleted()) return;
		Font ori = g.getFont();
		Color orc = g.getColor();
		g.setFont(font);
		g.setColor(Color.WHITE);
		pe.drawFlexibleCenteredString(g, str, center);
		g.setFont(ori);
		g.setColor(orc);
	}
	
	public void drawEpic(Graphics2D g, Coordinate center, Font font) {
		if(this.isCompleted()) return;
		Font ori = g.getFont();
		Color orc = g.getColor();
		g.setFont(font);
		g.setColor(new Color(255,255,255,(int)(255-255*this.getProcessRate())));
		pe.drawFlexibleCenteredString(g, str, center);
		g.setFont(ori);
		g.setColor(orc);
	}
	
	PrintEngine pe = new PrintEngine();
}
