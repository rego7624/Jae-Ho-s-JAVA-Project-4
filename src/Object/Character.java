package Object;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import Environment.Constants;
import Environment.ImageControlEngine;
import Environment.PrintEngine;
import Object.Effects.qEffect;
import Object.Effects.rEffect;
import Object.Effects.wEffect;
import Environment.Constants.SkillSet;
import main.mainframe;

public abstract class Character {
	private double curx, cury;		//현재 좌표
	private double dx, dy;			//이동 목표 좌표
	private double CurHP;			//현재 체력
	private double MaxHP;			//최대 체력
	private double dmg;				//데미지 계수
	private double max_vel;			//최대 이속
	private double move_ang;		//이동 방향
	private double att_vel;			//투사체 속도
	private double cooltime;		//쿨타임
	private int MultiAttack;		//멀티 어택
	
	private double original_vel;
	
	private double size;			//캐릭터 크기(직경)
	
	private Color HpColor = Color.BLACK;	//체력바 색깔
	private Color BodyColor;
	
	private boolean isUser;			//플레이어인지 확인
	
	private BufferedImage playerImage = null;
	
	
	private ArrayList<Bullet> bullets = new ArrayList<>();
	private ArrayList<qEffect> qEffects = new ArrayList<>();
	private ArrayList<wEffect> wEffects = new ArrayList<>();
	private ArrayList<rEffect> rEffects = new ArrayList<>();
	private double bulletSize;
	
	private double cooltimeflag = 0;
	private double changePeriod;
	
	PrintEngine pe = new PrintEngine();
	
	public void draw(Graphics2D g) {
		final int HpBarWidth = 110;
		final int HpBarHeight = 23;
		final int Border = 4;
		
		if(this.isUser) {
			this.playerImage = ImageControlEngine.CallImage(Constants.PlayerIMAGE, (int)this.size, (int)this.size);
		}else {
			this.playerImage = ImageControlEngine.CallImage(Constants.EnemyIMAGE, (int)this.size, (int)this.size);
		}
		AffineTransform transform = new AffineTransform();
		transform.rotate(Math.toRadians(Math.toDegrees(this.move_ang)+90), size / 2, size / 2);
		AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BICUBIC);
		g.drawImage(op.filter(this.playerImage, null), null, (int)(this.curx-size/2), (int)(this.cury-size/2));
		
		String hphint = (int)CurHP+"/"+(int)MaxHP;
		int strlen = g.getFontMetrics().stringWidth(hphint);
		double rate = CurHP/MaxHP;
		
		g.setColor(Color.BLACK);
		g.fillRect((int)(curx-HpBarWidth/2)-Border/2, (int)(cury-size/2-5-HpBarHeight)-Border/2,
				(int)HpBarWidth+Border, (int)HpBarHeight+Border);
		
		g.setColor(this.HpColor);
		g.fillRect((int)(curx-HpBarWidth/2), (int)(cury-size/2-5-HpBarHeight),
				(int)(HpBarWidth*rate), (int)HpBarHeight);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font(Constants.DefaultFontName, Font.BOLD, 17));
		pe.drawCenteredString(g, hphint, new Rectangle(
				(int)(curx-HpBarWidth/2)-Border/2, (int)(cury-size/2-5-HpBarHeight)-Border/2,(int)HpBarWidth+Border, (int)HpBarHeight+Border
				));
		
		
		g.setColor(Color.ORANGE);
		
		if(this.isUser) {
			for(int i=0;i<qEffects.size();i++)
				qEffects.get(i).draw(g,true);
			for(int i=0;i<wEffects.size();i++)
				wEffects.get(i).draw(g,false);
			for(int i=0;i<rEffects.size();i++)
				rEffects.get(i).draw(g,true);
		}else {
			for(int i=0;i<bullets.size();i++)
				bullets.get(i).draw(g);
		}
	}
	
	
	
	public void update() {
		this.cooltimeflag -= 1000/Constants.FPS;
		if(this.cooltimeflag<0)
			this.cooltimeflag = 0;
		
		this.updateQ();
		this.updateW();
		this.updateR();
		for(int i=0;i<bullets.size();i++) {
			Bullet b = bullets.get(i);
			double dist = Constants.distance(curx, cury, b.getCurx(), b.getCury());
			if(dist>Constants.maxDeleteBulletLen) {
				bullets.remove(i);
				continue;
			}
			bullets.get(i).update();
		}
		
		if(this.isUser) {		//내 스킬이 적에게 닿음
			//Q스킬
			for(int i=0;i<this.qEffects.size();i++) {
				qEffect q = qEffects.get(i);
				for(int j=0;j<Constants.aim.ais.size();j++) {
					AI ai = Constants.aim.ais.get(j);
					double dist = Constants.distance(
							q.getCurx(),
							q.getCury(),
							ai.getCurx(),
							ai.getCury());
					if(dist<(q.EffectImage.getHeight()/2+ai.getSize()/2)) {
						Constants.aim.ais.get(j).getDamage(this.dmg);
						qEffects.remove(i);
						i--;
						break;
					}
				}
			}
			//W스킬
			for(int i=0;i<this.wEffects.size();i++) {
				wEffect w = wEffects.get(i);
				for(int j=0;j<Constants.aim.ais.size();j++) {
					AI ai = Constants.aim.ais.get(j);
					double dist = Constants.distance(
							w.getCurx(),
							w.getCury(),
							ai.getCurx(),
							ai.getCury());
					if(dist<((w.EffectImage.getHeight()+w.EffectImage.getWidth())/4+ai.getSize()/2)) {
						Constants.aim.ais.get(j).getDamage(this.dmg);
						wEffects.remove(i);
						i--;
						break;
					}
				}
			}
			//R스킬
			for(int i=0;i<this.rEffects.size();i++) {
				rEffect r = rEffects.get(i);
				for(int j=0;j<Constants.aim.ais.size();j++) {
					AI ai = Constants.aim.ais.get(j);
					double dist = Constants.distance(
							r.getCurx(),
							r.getCury(),
							ai.getCurx(),
							ai.getCury());
					if(dist<(r.EffectImage.getHeight()/2+ai.getSize()/2)) {
						Constants.aim.ais.get(j).getDamage(this.dmg);
						break;
					}
				}
			}
		}else {					//적 총알이 나에게 닿음
			for(int i=0;i<this.bullets.size();i++) {
				Bullet b = bullets.get(i);
				Player player = Constants.user;
				double dist = Constants.distance(
						b.getCurx(),
						b.getCury(),
						player.getCurx(),
						player.getCury());
				if(dist<(b.getSize()/2+player.getSize()/2)) {
					if(!Constants.UnDeadMode)
						Constants.user.getDamage(this.dmg);
					bullets.remove(i);
					i--;
				}
			}
		}
		
		//이동
		if(this.isUser) {
			if(!((dy==cury)&&(dx==curx))) {
				move_ang = Math.atan2(dy-cury, dx-curx);
				if(Constants.distance(curx, cury, dx, dy)<max_vel) {
					curx = dx;
					cury = dy;
					if(Constants.sme[1].isProcessing()) {
						Constants.sme[1].setProcessing(false);
						this.max_vel = this.original_vel;
					}
				}else {
					curx += max_vel*Math.cos(move_ang);
					cury += max_vel*Math.sin(move_ang);
				}
			}
		}else {
			if(this.cooltimeflag == 0) {
				Random r = new Random();
				
				double nx_min, nx_max, ny_min, ny_max;
				Player player = Constants.user;
				double range = 70;
				nx_min = player.getCurx()-range;
				ny_min = player.getCury()-range*2/3;
				nx_max = player.getCurx()+range;
				ny_max = player.getCury()+range*2/3;
				double nx = (nx_max-nx_min)*r.nextDouble()+nx_min;
				double ny = (ny_max-ny_min)*r.nextDouble()+ny_min;
				
				this.fire(nx, ny);
			}
			
			if((dy==cury)&&(dx==curx)) {
				double nx = 0, ny = 0, ann = 0;
				Random r = new Random();
				while(true) {
					ann = Math.PI*2*r.nextDouble()-Math.PI;
					nx = curx + Math.cos(ann)*this.max_vel*Constants.FPS*this.changePeriod
							*(1.25-0.5*r.nextDouble());
					ny = cury + Math.sin(ann)*this.max_vel*Constants.FPS*this.changePeriod
							*(1.25-0.5*r.nextDouble());
					if(nx > Constants.FrameWidth - this.size/2)
						continue;
					if(ny < this.size/2) {
						continue;
					}
					if(ny<=(0.6549*(nx-73)))
						break;
				}
				this.setDest(nx, ny);
			}else {
				move_ang = Math.atan2(dy-cury, dx-curx);
				if(Constants.distance(curx, cury, dx, dy)<max_vel) {
					curx = dx;
					cury = dy;
				}else {
					curx += max_vel*Math.cos(move_ang);
					cury += max_vel*Math.sin(move_ang);
				}
			}
		}
	}
	
	public void setFullHP() {
		this.CurHP = this.MaxHP;
	}
	
	public void fire(double ax, double ay) {
		double rs = size/2;
		double ang = Math.atan2(ay-getCury(),
				ax-getCurx());
		move_ang = ang;
		Bullet b = new Bullet(
				curx+rs*Math.cos(ang),
				cury+rs*Math.sin(ang),
				ang,
				att_vel, 
				this.bulletSize);
		bullets.add(b);
		this.setHotTime();
	}
	
	public void fire(double ax, double ay, int ID) {
		double rs = size/2;
		double ang = Math.atan2(ay-getCury(), ax-getCurx());
		move_ang = ang;
		switch(ID) {
		case 3:
			qEffect q = new qEffect();
			q.setInstance(
					curx+rs*Math.cos(ang),
					cury+rs*Math.sin(ang),
					ang,
					att_vel
					);
			qEffects.add(q);
			break;
		case 2:
			wEffect w = new wEffect();
			w.setInstance(
					curx+rs*Math.cos(ang),
					cury+rs*Math.sin(ang),
					ang,
					att_vel
					);
			wEffects.add(w);
			break;
		case 1:
			Constants.sme[1].setProcessing(true);
			this.original_vel = this.max_vel;
			this.setMax_vel(3000);
			double dist = Constants.distance(curx, cury, ax, ay);
			if(dist>Constants.eSkillRange)
				dist = Constants.eSkillRange;
			
			mainframe.moveObject((int)(curx+Math.cos(ang)*dist), (int)(cury+Math.sin(ang)*dist));
			//mainframe.moveObject();
			break;
		case 0:
			rEffect r = new rEffect();
			r.setInstance(
					curx+rs*Math.cos(ang),
					cury+rs*Math.sin(ang),
					ang,
					att_vel
					);
			rEffects.add(r);
			break;
		}
		
		this.setHotTime(ID);
	}
	
	private void updateQ() {
		for(int i=0;i<qEffects.size();i++) {
			qEffect q = qEffects.get(i);
			double dist = Constants.distance(curx, cury, q.getCurx(), q.getCury());
			if(dist>Constants.maxDeleteBulletLen) {
				qEffects.remove(i);
				continue;
			}
			qEffects.get(i).update();
		}
	}
	
	private void updateW() {
		for(int i=0;i<wEffects.size();i++) {
			wEffect w = wEffects.get(i);
			double dist = Constants.distance(curx, cury, w.getCurx(), w.getCury());
			if(dist>Constants.maxDeleteBulletLen) {
				wEffects.remove(i);
				continue;
			}
			wEffects.get(i).update();
		}
	}
	
	private void updateR() {
		for(int i=0;i<rEffects.size();i++) {
			rEffect r = rEffects.get(i);
			double dist = Constants.distance(curx, cury, r.getCurx(), r.getCury());
			if(dist>Constants.maxDeleteBulletLen) {
				rEffects.remove(i);
				continue;
			}
			rEffects.get(i).update();
		}
	}
	
	public void getDamage(double damage) {
		this.CurHP -= damage;
		if(this.CurHP<0)
			this.CurHP = 0;
	}
	
	public void setInitCurPos(double curx, double cury) {
		this.setPos(curx, cury);
		this.setDest(curx, cury);
	}
	
	public void setPos(double cx, double cy) {
		this.curx = cx;
		this.cury = cy;
	}
	
	public void setHotTime() {
		Random r = new Random();
		double rate = r.nextDouble()*0.2+0.9;
		this.cooltimeflag = this.cooltime*rate;
	}
	
	public void setHotTime(int ID) {
		Constants.sme[ID].makeHot();
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

	public double getCurHP() {
		return CurHP;
	}

	public void setCurHP(double hP) {
		CurHP = hP;
	}

	public double getDmg() {
		return dmg;
	}

	public void setDmg(double dmg) {
		this.dmg = dmg;
	}

	public double getMax_vel() {
		return max_vel;
	}

	public void setMax_vel(double max_vel) {
		this.max_vel = max_vel;
	}

	public double getMove_ang() {
		return move_ang;
	}

	public void setMove_ang(double move_ang) {
		this.move_ang = move_ang;
	}

	public double getAtt_vel() {
		return att_vel;
	}

	public void setAtt_vel(double att_vel) {
		this.att_vel = att_vel;
	}

	public double getCooltime() {
		return cooltime;
	}

	public void setCooltime(double cooltime) {
		this.cooltime = cooltime;
	}
	
	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public double getDestx() {
		return dx;
	}

	public double getDesty() {
		return dy;
	}
	
	public void setDest(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
		if(this.isUser) {System.out.println("REQUEST");
			if(this.dx<this.size/2)
				this.dx = this.size/2;
			if(this.dy > Constants.FrameHeight - this.size/2)
				this.dy = Constants.FrameHeight - this.size/2;
		}
	}

	public Color getHpColor() {
		return HpColor;
	}

	public void setHpColor(Color hpColor) {
		HpColor = hpColor;
	}

	public double getMaxHP() {
		return MaxHP;
	}

	public void setMaxHP(double maxHP) {
		MaxHP = maxHP;
	}
	
	public boolean canFire() {
		return this.cooltimeflag == 0;
	}
	
	public double getCooltimeFlag() {
		return this.cooltimeflag;
	}
	
	public boolean getIsUser() {
		return this.isUser;
	}
	
	public void setIsUser(boolean isUser) {
		this.isUser = isUser;
	}

	public double getChangePeriod() {
		return changePeriod;
	}

	public void setChangePeriod(double changePeriod) {
		this.changePeriod = changePeriod;
	}

	public int getMultiAttack() {
		return MultiAttack;
	}

	public void setMultiAttack(int multiAttack) {
		MultiAttack = multiAttack;
	}

	public Color getBodyColor() {
		return BodyColor;
	}

	public void setBodyColor(Color bodyColor) {
		BodyColor = bodyColor;
	}

	public double getBulletSize() {
		return bulletSize;
	}

	public void setBulletSize(double bulletSize) {
		this.bulletSize = bulletSize;
	}
}
