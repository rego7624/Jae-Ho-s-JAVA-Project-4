package Environment;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;

import Object.Coordinate;

public class SkillManagement {
	private long SkillTimeFlag = 0;
	private long CurCoolTime = 0;
	private long CoolTime = 0;
	private boolean isProcessing = false;
	
	public boolean show = false;
	
	
	public void setCoolTime(long cool) {
		this.CoolTime = cool;
	}
	
	public void showSwitch() {
		this.show = !this.show;
	}
	
	public void makeHot() {
		this.SkillTimeFlag = System.currentTimeMillis();
	}
	
	public void makeCool() {
		this.SkillTimeFlag = 0;
	}
	
	public void update() {
		this.CurCoolTime = System.currentTimeMillis() - this.SkillTimeFlag;
	}
	
	public long getLeftTime() {
		return this.CoolTime - this.CurCoolTime;
	}
	
	public double getProcessRate() {
		double rate = (double)this.CurCoolTime/(double)this.CoolTime;
		if(rate>1)rate = 1;
		return rate;
	}
	
	public boolean isFireable() {
		return this.getProcessRate() == 1;
	}
	
	public void draw(Graphics2D g, Coordinate startPos, int size, int ind) {
		double extraMargin = 100;
		Arc2D.Double arc = new Arc2D.Double(Arc2D.PIE);
		arc.setFrame(startPos.getX()-extraMargin/2, startPos.getY()-extraMargin/2, size+extraMargin, size+extraMargin);
		arc.setAngleStart(90);
		arc.setAngleExtent(360-(double)(this.getProcessRate()*360));
		
		GeneralPath restrict = new GeneralPath();
		restrict.append(new Rectangle(startPos.getX(),startPos.getY(),size,size), false);
		
		g.clip(restrict);

		if(!Constants.sme[ind].isFireable()) {
			g.setColor(new Color(0,0,0,150));
			g.fill(Constants.FullRect);
		}
		
		g.setColor(new Color(20,110,215,190));
		g.fill(arc);
		
		Constants.restoreClip(g);
		
		g.setColor(Color.WHITE);
		
		g.setFont(new Font(Constants.DefaultFontName, Font.PLAIN, 20));
		
		String coolStr = "";
		double cooldown = (double)this.CoolTime*(1-this.getProcessRate())/1000;
		
		if(cooldown<1)
			coolStr = String.format("%.1f", cooldown);
		else
			coolStr = String.format("%d", (int)cooldown);
		
		if(cooldown > 0)
			pe.drawCenteredString(g, coolStr, new Rectangle(startPos.getX(),startPos.getY(),size,size));
	}
	
	public boolean isProcessing() {
		return isProcessing;
	}

	public void setProcessing(boolean isProcessing) {
		this.isProcessing = isProcessing;
	}

	PrintEngine pe = new PrintEngine();
}
