package Environment;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import Environment.Constants.SkillSet;
import Object.Player;

public class ShowTraceEngine {
	public void draw(Graphics2D g, int mx, int my, SkillSet skillID) {
		double msx = (double)mx;
		double msy = (double)my;
		
		Player me = Constants.user;
		double ang = Math.atan2(my-me.getCury(), mx-me.getCurx());
		
		double length = 2000;
		
		SkillManagement sm = null;
		
		g.setColor(new Color(45,205,205,155));
		
		switch(skillID) {
		case Qs:
			sm = Constants.sme[3];
			if(!sm.show)return;
			
			
			
			
			g.setStroke(new BasicStroke(35));
			if(sm.isFireable()) {
				g.drawLine(
					(int)me.getCurx(), 
					(int)me.getCury(),
					(int)(me.getCurx()+length*Math.cos(ang)), 
					(int)(me.getCury()+length*Math.sin(ang))
				);
			}
			break;
		case Ws:
			sm = Constants.sme[2];
			if(!sm.show)return;
			
			g.setStroke(new BasicStroke(45));
			if(sm.isFireable()) {
				g.drawLine(
					(int)me.getCurx(), 
					(int)me.getCury(),
					(int)(me.getCurx()+length*Math.cos(ang)), 
					(int)(me.getCury()+length*Math.sin(ang))
				);
			}
			break;
		case Es:
			sm = Constants.sme[1];
			final int Range = (int)Constants.eSkillRange*2;
			if(!sm.show)return;
			
			g.setStroke(new BasicStroke(45));
			if(sm.isFireable()) {
				g.fillOval(
						(int)me.getCurx() - Range/2, 
						(int)me.getCury() - Range/2, 
						(int)Range, 
						(int)Range
						);
			}
			break;
		case Rs:
			sm = Constants.sme[0];
			if(!sm.show)return;
			
			g.setStroke(new BasicStroke(45));
			if(sm.isFireable()) {
				g.drawLine(
					(int)me.getCurx(), 
					(int)me.getCury(),
					(int)(me.getCurx()+length*Math.cos(ang)), 
					(int)(me.getCury()+length*Math.sin(ang))
				);
			}
			break;
		}
		
		g.setStroke(new BasicStroke(2));
	}
}
