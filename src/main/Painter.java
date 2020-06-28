package main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import javax.print.attribute.standard.Media;

import Environment.Constants;
import Environment.PingDrawingEngine;
import Environment.ShowTraceEngine;
import Object.Coordinate;
import Object.Player;

public class Painter {
	private ShowTraceEngine ste = new ShowTraceEngine();
	final File StartThemeFile = new File("src\\resources\\Videos\\StartTheme.mp4");
	
	public void drawComponentsById(Graphics2D g) {
		switch(Constants.StoryPage) {
		case 0:
			g.setColor(new Color(100,100,100,255));
			g.drawImage(Constants.BG_IMAGE, null, 0, 0);
			break;
		case 1:
			g.drawImage(Constants.GM_IMAGE, null, 0, 0);
			ste.draw(g, Constants.MouseX, Constants.MouseY, Constants.SkillSet.Qs);
			ste.draw(g, Constants.MouseX, Constants.MouseY, Constants.SkillSet.Ws);
			ste.draw(g, Constants.MouseX, Constants.MouseY, Constants.SkillSet.Es);
			ste.draw(g, Constants.MouseX, Constants.MouseY, Constants.SkillSet.Rs);
			Constants.aim.draw(g);
			Constants.aim.update();
			Constants.user.draw(g);
			Constants.user.update();
			Constants.wne.draw(g);
			break;
		default:
			break;
		}
		
		//Messages
		Constants.SkillNotPreparedSignal.drawEpic(g, new Coordinate(Constants.FrameWidth/2, Constants.FrameHeight - 20),
				new Font(Constants.DefaultFontName, Font.PLAIN, 12));
		
		//Pings
		Constants.pde.draw(g);
		
		//Skill Window
		this.drawSkillWindow(g);
		
		//Cursor
		if(Constants.FocusCursorMode)
			g.drawImage(Constants.FocusCursorImage, null, Constants.MouseX - Constants.FocusCursorImage.getWidth()/2, Constants.MouseY - Constants.FocusCursorImage.getHeight()/2);
	}
	
	public void drawSkillWindow(Graphics2D g) {
		if(Constants.StoryPage != 1)return;
		g.setColor(Color.BLACK);
		for(int i=0;i<4;i++)
			g.fillRect(
					Constants.FrameWidth - (Constants.StandardSkillSetSize + Constants.StandardSkillSetMargin)*(i+1) - Constants.StandardSkillRightMarginAdjustment,
					Constants.FrameHeight - Constants.StandardSkillSetSize - Constants.StandardSkillSetMargin,
					Constants.StandardSkillSetSize,
					Constants.StandardSkillSetSize);
		g.drawImage(Constants.qSkillImage, null,
				Constants.FrameWidth - (Constants.StandardSkillSetSize + Constants.StandardSkillSetMargin)*4 - Constants.StandardSkillRightMarginAdjustment + Constants.StandardSkillImagePadding,
				Constants.FrameHeight - Constants.StandardSkillSetSize - Constants.StandardSkillSetMargin + Constants.StandardSkillImagePadding
				);
		g.drawImage(Constants.wSkillImage, null,
				Constants.FrameWidth - (Constants.StandardSkillSetSize + Constants.StandardSkillSetMargin)*3 - Constants.StandardSkillRightMarginAdjustment + Constants.StandardSkillImagePadding,
				Constants.FrameHeight - Constants.StandardSkillSetSize - Constants.StandardSkillSetMargin + Constants.StandardSkillImagePadding
				);
		g.drawImage(Constants.eSkillImage, null,
				Constants.FrameWidth - (Constants.StandardSkillSetSize + Constants.StandardSkillSetMargin)*2 - Constants.StandardSkillRightMarginAdjustment + Constants.StandardSkillImagePadding,
				Constants.FrameHeight - Constants.StandardSkillSetSize - Constants.StandardSkillSetMargin + Constants.StandardSkillImagePadding
				);
		g.drawImage(Constants.rSkillImage, null,
				Constants.FrameWidth - (Constants.StandardSkillSetSize + Constants.StandardSkillSetMargin)*1 - Constants.StandardSkillRightMarginAdjustment + Constants.StandardSkillImagePadding,
				Constants.FrameHeight - Constants.StandardSkillSetSize - Constants.StandardSkillSetMargin + Constants.StandardSkillImagePadding
				);
		
		g.setColor(new Color(30,30,30,200));
		for(int i=0;i<4;i++)
			Constants.sme[i].draw(g, new Coordinate(
					Constants.FrameWidth - (Constants.StandardSkillSetSize + Constants.StandardSkillSetMargin)*(i+1) - Constants.StandardSkillRightMarginAdjustment + Constants.StandardSkillImagePadding,
					Constants.FrameHeight - Constants.StandardSkillSetSize - Constants.StandardSkillSetMargin + Constants.StandardSkillImagePadding
					), Constants.StandardSkillSetSize - 2*Constants.StandardSkillImagePadding, i);
	}
	
	public void drawLog(Graphics2D g) {
		if(!Constants.isLogVisible)return;
		g.setColor(new Color(255,255,255,150));
		g.fillRect(0, 0, Constants.LogFrameWidth, Constants.LogFrameHeight);
		g.setColor(new Color(50,50,50,255));
		
		g.setFont(new Font(Constants.DefaultFontName, Font.PLAIN, 20));
		g.drawString("Cursor Position : ("+Constants.MouseX+","+Constants.MouseY+")", 15, 35);
		g.drawString("Frame Count : "+Constants.FrameCount, 15, 65);
		g.drawString("Elapsed Time : "+String.format("%.2fs", ((double)Constants.ElapsedTime)/1000), 15, 95);
		g.drawString("Player Position : ("+(int)Constants.user.getCurx()+","+(int)Constants.user.getCury()+")", 15, 125);
		g.drawLine(15, 155, Constants.LogFrameWidth - 15, 155);
		
		ArrayList<String> LocalLog = Constants.Logger;
		int maxn = 7;
		int ShowingLogNum = (LocalLog.size()<maxn)?LocalLog.size():maxn;
		for(int i=0;i<ShowingLogNum; i++)
			g.drawString("Log #"+(LocalLog.size()-i)+" : "+LocalLog.get(i), 15, 185+i*25);
		
		g.drawLine(15, 400, Constants.LogFrameWidth - 15, 400);
	}
}
