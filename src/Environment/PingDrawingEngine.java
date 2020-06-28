package Environment;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Environment.Constants.PingSet;
import Object.Coordinate;

public class PingDrawingEngine {
	public class Ping{
		public Coordinate pingCoord = new Coordinate(0,0);
		public long PingMaintain = 0;
		public long PingFadeOut = 0;
		private BufferedImage image;
		private long PingTimeFlag;
		private long PingCurTime;
		
		public Ping(PingSet pingID) {
			switch(pingID) {
			case MOVE_PING:
				this.image = Constants.MovePingImage;
				this.pingCoord.setPos(Constants.MouseX-30, Constants.MouseY-50);
				this.PingMaintain = 150;
				this.PingFadeOut = 250;
				break;
			default:
				this.image = null;
			}
			this.PingTimeFlag = System.currentTimeMillis();
		}
		public void update() {
			this.PingCurTime = System.currentTimeMillis() - this.PingTimeFlag;
		}
		public void draw(Graphics2D g) {
			if(this.PingCurTime<this.PingMaintain)
				g.drawImage(
						this.image,
						null, this.pingCoord.getX(), this.pingCoord.getY()
						);
			else if(this.PingCurTime<(this.PingMaintain+this.PingFadeOut)){
				g.setComposite(AlphaComposite.getInstance(
						AlphaComposite.SRC_OVER, 
						(float)(1-(float)(this.PingCurTime - this.PingMaintain)/(float)this.PingFadeOut)
						));
				g.drawImage(
						this.image,
						null, this.pingCoord.getX(), this.pingCoord.getY()
						);
			}
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		}
	}
	private ArrayList<Ping> pings = new ArrayList<>();
	
	public void update() {
		for(int i=0;i<pings.size();i++) {
			Ping ping = pings.get(i);
			if(ping.PingCurTime > (ping.PingMaintain+ping.PingFadeOut)) {
				pings.remove(i);
				i--;
			}
		}
		for(int i=0;i<pings.size();i++)
			pings.get(i).update();
	}
	
	public void draw(Graphics2D g) {
		for(int i=0;i<pings.size();i++)
			pings.get(i).draw(g);
	}
	
	public void addPing(PingSet pingID) {
		this.pings.add(new Ping(pingID));
	}
}
