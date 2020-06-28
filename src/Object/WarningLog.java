package Object;

public class WarningLog {
	public long registerFlag = 0;
	public String Log;
	
	public WarningLog(String str) {
		this.Log = str;
		this.registerFlag = System.currentTimeMillis();
	}
	
}
