package Object;

import java.awt.Color;

import Environment.Constants;

public class Player extends Character{
	public Player() {
		this.setInitCurPos(200, 570);
		
		double stage = (double)Constants.STAGE;
		
		this.setSize(75);
		this.setMove_ang(0);
		this.setMultiAttack(1);
		
		
		this.setMaxHP(60+7*stage);
		this.setCurHP(60+7*stage);
		this.setDmg(15+1.15*stage);
		this.setMax_vel(4.5+0.02*stage);
		this.setAtt_vel(12+0.06*stage);
		this.setCooltime(1250+(52500/(stage+30)));
		this.setBulletSize(20+(1250/(stage+50)));
		
		
		this.setHpColor(new Color(55,175,55,255));
		this.setBodyColor(new Color(99,138,255));
		this.setIsUser(true);
	}
	
	public void updateOnStage() {
		double stage = (double)Constants.STAGE;
		
		this.setMaxHP(60+7*stage);
		this.setCurHP(60+7*stage);
		this.setDmg(15+1.15*stage);
		this.setMax_vel(3+0.0125*stage);
		this.setAtt_vel(10+0.07*stage);
		this.setCooltime(1250+(52500/(stage+30)));
		this.setBulletSize(20+(1250/(stage+50)));
	}
	
	public void stopMoving() {
		this.setDest(this.getCurx(), this.getCury());
	}
}
