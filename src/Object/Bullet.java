package Object;

import java.awt.Color;
import java.awt.Graphics2D;

public class Bullet {
	private double curx, cury;		//현재 좌표
	private double angle;			//이동 방향
	private double velocity;		//속도
	private double size;
	
	public Bullet(double cx, double cy, double ang, double vel, double size) {
		this.curx = cx;
		this.cury = cy;
		this.angle = ang;
		this.velocity = vel;
		this.size = size;
	}
	
	public void draw(Graphics2D g) {
		double ssize = size*0.7;
		double sssize = ssize*0.7;
		g.setColor(Color.RED);
		g.fillOval((int)(curx-size/2), (int)(cury-size/2), (int)size, (int)size);
		g.setColor(Color.ORANGE);
		g.fillOval((int)(curx-ssize/2), (int)(cury-ssize/2), (int)ssize, (int)ssize);
		g.setColor(Color.YELLOW);
		g.fillOval((int)(curx-sssize/2), (int)(cury-sssize/2), (int)sssize, (int)sssize);
	}
	
	public void update() {
		curx += velocity*Math.cos(angle);
		cury += velocity*Math.sin(angle);
	}

	public double getCurx() {
		return curx;
	}

	public void setCurx(double curx) {
		this.curx = curx;
	}

	public double getCury() {
		return cury;
	}

	public void setCury(double cury) {
		this.cury = cury;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}
	
	public double getSize() {
		return size;
	}
}
