package Environment;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Object.Message;
import Object.Player;

public class Constants {
	//Environment
	public static final boolean DEBUG = true;
	
	public static final int FrameWidth = 1200;
	public static final int FrameHeight = 800;
	public static final int LogFrameWidth = 500;
	public static final int LogFrameHeight = 500;
	public static final Rectangle FullRect = new Rectangle(0,0,1200,800);
	
	public static BufferedImage BG_IMAGE = null;
	public static BufferedImage GM_IMAGE = null;
	public static BufferedImage PlayerIMAGE = null;
	public static BufferedImage EnemyIMAGE = null;
	public static BufferedImage CursorImage = null;
	public static BufferedImage MovePingImage = null;
	public static BufferedImage qSkillImage = null;
	public static BufferedImage wSkillImage = null;
	public static BufferedImage eSkillImage = null;
	public static BufferedImage rSkillImage = null;
	public static BufferedImage qSkillEffectImage = null;
	public static BufferedImage wSkillEffectImage = null;
	public static BufferedImage eSkillEffectImage = null;
	public static BufferedImage rSkillEffectImage = null;
	public static BufferedImage FocusCursorImage = null;
	
	public static final int FPS = 60;
	public static int FrameCount = 0;
	public static long ElapsedTime = 0;
	
	
	public static WarningEngine wne = new WarningEngine();
	public static LogUpdateEngine lue = new LogUpdateEngine();
	public static PingDrawingEngine pde = new PingDrawingEngine();
	
	public static SkillManagement[] sme = new SkillManagement[4];
	
	
	public static Message SkillNotPreparedSignal = new Message("½ºÅ³ÀÌ ¾ÆÁ÷ ÁØºñµÇÁö ¾Ê¾Ò½À´Ï´Ù!", 2500);
	
	public static int MouseX = 0;
	public static int MouseY = 0;
	
	public static final String DefaultFontName= "¸¼Àº °íµñ";

	public enum PingSet {MOVE_PING, };
	public enum SkillSet {Qs,Ws,Es,Rs};
	
	public static long StartTimeFlag = 0;
	
	
	//View Log
	public static boolean isLogVisible = false;
	public static ArrayList<String> Logger = new ArrayList<>();
	
	//Customize
	public static final int StandardButtonWidth = 300;
	public static final int StandardButtonHeight = 50;
	
	public static final int StandardSkillSetSize = 55;
	public static final int StandardSkillSetMargin = 20;
	public static final int StandardSkillImagePadding = 3;
	public static final int StandardSkillRightMarginAdjustment = 446;
	
	//Story
	public static int StoryPage = 0;
	
	public static void GoToSpecificPage(int dest) {
		StoryPage = dest;
	}
	
	//In Game
	public static int LEVEL = 0;
	public static Player user = new Player();
	public static AIManagement aim = new AIManagement();
	public static double maxDeleteBulletLen = 3000;
	public static int SCORE = 0;
	
	public static long SkillCoolTime[] = {1500, 17000, 10000, 143000};
	public static double eSkillRange = 250;
	
	public static int STAGE = 1;
	
	public static boolean FocusCursorMode = false;
	public static boolean UnDeadMode = false;
	public static boolean NoCoolDownMode = false;
	
	//Functions
	public static double distance(double x1,double y1, double x2, double y2) {
		return Math.sqrt((x1-x2)*(x1-x2)+((y1-y2)*(y1-y2)));
	}
	public static void print(String str) {
		System.out.println(str);
	}
	public static void restoreClip(Graphics2D g) {
		g.setClip(0, 0, FrameWidth, FrameHeight);
	}
}
