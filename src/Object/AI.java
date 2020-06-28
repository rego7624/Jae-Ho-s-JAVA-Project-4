package Object;

import java.awt.Color;
import java.util.Random;

import Environment.Constants;

public class AI extends Character{
	public AI() {
		Random r = new Random();
		this.setInitCurPos(600+500*r.nextDouble(), 30+220*r.nextDouble());
		
		double stage = (double)Constants.STAGE;
		this.setSize(80-(60-(900/(stage+15))));
		
		this.setMaxHP(20+7*stage);
		this.setCurHP(20+7*stage);
		this.setDmg(15+1*stage);
		
		this.setMax_vel(0.75+0.01*stage);
		this.setMove_ang(0);
		this.setAtt_vel(5+0.02*stage);
		this.setCooltime(5000-(3500-(280000/(stage+80))));
		this.setMultiAttack(1+(int)Math.cbrt(stage));
		this.setBulletSize(25+(1000/(stage+50)));
		
		this.setHpColor(new Color(175,55,55,255));
		this.setIsUser(false);
		this.setBodyColor(new Color(255,50,132));
		this.setChangePeriod(0.6+(160/(stage+200)));
	}
}
