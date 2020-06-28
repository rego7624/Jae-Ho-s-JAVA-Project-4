package Environment;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public abstract class SkillEffect {
	public BufferedImage EffectImage = null;
	private double curx, cury;		//현재 좌표
	private double angle;			//이동 방향
	private double velocity;		//속도
	
	private double angleAdjust;		//각도 조정
	
	private double adjX, adjY;		//타점 조절
	
	public void draw(Graphics2D g, boolean rotate) {
		AffineTransform transform = new AffineTransform();
		System.out.println(this.EffectImage.getWidth()+", "+ this.EffectImage.getHeight());
		transform.rotate(Math.toRadians(Math.toDegrees(this.angle)+this.angleAdjust), this.EffectImage.getWidth()/2, this.EffectImage.getHeight()/2);
		//transform.translate(0, 15);
		AffineTransform translationTransform = findTranslation(transform, this.EffectImage);
		//transform.preConcatenate(translationTransform);
		
		AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
		if(rotate) {
			g.drawImage(
					op.filter(this.EffectImage,null),
					null,
					(int)(curx-this.EffectImage.getWidth()/2+adjX),
					(int)(cury-this.EffectImage.getHeight()/2+adjY)
					);
		}else {
			g.drawImage(
					this.EffectImage,
					null,
					(int)(curx-this.EffectImage.getWidth()/2+adjX),
					(int)(cury-this.EffectImage.getHeight()/2+adjY)
					);
		}
		//g.setColor(Color.RED);
		//g.fillOval((int)(curx-5), (int)(cury-5), 10, 10);
	}
	
	public void setInstance(double cx, double cy, double ang, double vel) {
		this.curx = cx;
		this.cury = cy;
		this.angle = ang;
		this.velocity = vel;
	}
	
	public void update() {
		curx += velocity*Math.cos(angle);
		cury += velocity*Math.sin(angle);
	}
	
	public SkillEffect(BufferedImage img) {
		this.EffectImage = img;
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
	public double getVelocity() {
		return velocity;
	}
	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}
	public double getAngleAdjust() {
		return angleAdjust;
	}
	public void setAngleAdjust(double angleAdjust) {
		this.angleAdjust = angleAdjust;
	}

	public double getAdjX() {
		return adjX;
	}

	public void setAdjX(double adjX) {
		this.adjX = adjX;
	}

	public double getAdjY() {
		return adjY;
	}

	public void setAdjY(double adjY) {
		this.adjY = adjY;
	}
	
	public void setAdj(double xx, double yy) {
		this.setAdjX(xx);
		this.setAdjY(yy);
	}
	
	private AffineTransform findTranslation(AffineTransform at, BufferedImage bi) {
	    Point2D p2din, p2dout;

	    p2din = new Point2D.Double(0.0, 0.0);
	    p2dout = at.transform(p2din, null);
	    double ytrans = p2dout.getY();

	    p2din = new Point2D.Double(0, bi.getHeight());
	    p2dout = at.transform(p2din, null);
	    double xtrans = p2dout.getX();

	    AffineTransform tat = new AffineTransform();
	    tat.translate(-xtrans, -ytrans);
	    return tat;
	  }
}
