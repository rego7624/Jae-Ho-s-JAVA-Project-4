package Environment;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import Object.WarningLog;

public class WarningEngine {
	public ArrayList<WarningLog> logs = new ArrayList<>();
	private final int maxLog = 3;
	public void draw(Graphics2D g) {
		int mins = (maxLog>logs.size())?logs.size():maxLog;
		for(int i=0;i<mins;i++) {
			String str = logs.get(i).Log;
			g.setFont(new Font(Constants.DefaultFontName, Font.BOLD, 30));
			g.setColor(Color.YELLOW);
			pe.drawCenteredString(g, str, new Rectangle(0,50+i*35,Constants.FrameWidth, 30));
		}
		update();
	}
	private void update() {
		for(int i=0;i<logs.size();i++) {
			if((System.currentTimeMillis() - logs.get(i).registerFlag)>2500) {
				this.logs.remove(i);
				i--;
			}
		}
		for(int i=0;i<logs.size();i++) {
			if(i>=maxLog) {
				this.logs.remove(i);
				i--;
			}
		}
	}
	
	public void register(String log) {
		WarningLog wl = new WarningLog(log);
		logs.add(0, wl);
	}
	
	PrintEngine pe = new PrintEngine();
}
