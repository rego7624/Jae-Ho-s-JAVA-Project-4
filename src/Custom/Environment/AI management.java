package Environment;

import java.awt.Graphics2D;
import java.util.ArrayList;

import Object.AI;

public class AIManagement {
	public ArrayList<AI> ais = new ArrayList<>();
	public void draw(Graphics2D g) {
		for(int i=0;i<ais.size();i++)
			ais.get(i).draw(g);
		update();
	}
	
	public void update() {
		for(int i=0;i<ais.size();i++) {
			AI ai = ais.get(i);
			if(ai.getCurHP()==0) {
				ais.remove(i);
				i--;
			}
		}
		for(int i=0;i<ais.size();i++) {
			ais.get(i).update();
		}
		if(ais.size()==0) {
			Constants.user.setFullHP();
			Constants.STAGE++;
			
			this.startStage();
		}
	}
	
	public void startStage() {
		this.ais.clear();
		Constants.user.updateOnStage();
		int num = (int)Math.sqrt((double)Constants.STAGE);
		Constants.wne.register("½ºÅ×ÀÌÁö "+Constants.STAGE+" ½ÃÀÛ!");
		for(int i=0;i<num;i++) {
			Constants.lue.addMessage("Enemy created");
			ais.add(new AI());
		}
	}
}
