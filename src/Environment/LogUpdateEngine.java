package Environment;

public class LogUpdateEngine {
	public void addMessage(String str) {
		Constants.Logger.add(0, str);
	}
}
