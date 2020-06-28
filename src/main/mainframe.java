package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Custom.JGradientButton;
import Environment.AnimationByTimeEngine;
import Environment.Constants;
import Environment.ImageControlEngine;
import Environment.LogUpdateEngine;
import Environment.SkillManagement;
import Environment.WarningEngine;
import Object.Message;
import Object.Player;


public class mainframe extends JPanel{
	private static JFrame MainCustomFrame = new JFrame("Shooting Game");
	
	private static JButton GameStartBtn = new JButton("Game Start");
	private static JButton GameEndBtn = new JButton("Game Exit");
	private static boolean switch_ = true;
	private static ImageControlEngine ice = new ImageControlEngine();
	
	private static Thread mainthread = null;
	Painter painter = new Painter();
	
	public void paintComponent(Graphics gd) {
		Graphics2D g = (Graphics2D)gd;
		painter.drawComponentsById(g);
		painter.drawLog(g);
	}
	
	private static void initial(mainframe m) {
		m.setLayout(null);
		
		for(int i=0;i<4;i++) {
			Constants.sme[i] = new SkillManagement();
			if(Constants.NoCoolDownMode)
				Constants.sme[i].setCoolTime(0);
			else
				Constants.sme[i].setCoolTime(Constants.SkillCoolTime[3-i]);
		}
		
		try {
			Constants.BG_IMAGE = ImageControlEngine.CallImage(
					ImageIO.read(new File("src\\resources\\Images\\temp.png")),
					Constants.FrameWidth,
					Constants.FrameHeight);
			Constants.GM_IMAGE = ImageControlEngine.CallImage(
					ImageIO.read(new File("src\\resources\\Images\\game_temp.png")),
					Constants.FrameWidth,
					Constants.FrameHeight);
			Constants.CursorImage = ImageControlEngine.CallImage(
					ImageIO.read(new File("src\\resources\\Images\\cursor.png")),
					32,
					32);
			Constants.MovePingImage = ImageControlEngine.CallImage(
					ImageIO.read(new File("src\\resources\\Images\\move_ping2.png")),
					60,
					80);
			Constants.PlayerIMAGE= ImageControlEngine.CallImage(
					ImageIO.read(new File("src\\resources\\Images\\PlayerImage.png")),
					117,
					117);
			Constants.EnemyIMAGE= ImageControlEngine.CallImage(
					ImageIO.read(new File("src\\resources\\Images\\EnemyImage.png")),
					117,
					117);
			Constants.qSkillImage = ImageControlEngine.CallImage(
					ImageIO.read(new File("src\\resources\\Images\\qSkill.png")),
					Constants.StandardSkillSetSize - 2*Constants.StandardSkillImagePadding,
					Constants.StandardSkillSetSize - 2*Constants.StandardSkillImagePadding);
			Constants.wSkillImage = ImageControlEngine.CallImage(
					ImageIO.read(new File("src\\resources\\Images\\wSkill.png")),
					Constants.StandardSkillSetSize - 2*Constants.StandardSkillImagePadding,
					Constants.StandardSkillSetSize - 2*Constants.StandardSkillImagePadding);
			Constants.eSkillImage = ImageControlEngine.CallImage(
					ImageIO.read(new File("src\\resources\\Images\\eSkill.png")),
					Constants.StandardSkillSetSize - 2*Constants.StandardSkillImagePadding,
					Constants.StandardSkillSetSize - 2*Constants.StandardSkillImagePadding);
			Constants.rSkillImage = ImageControlEngine.CallImage(
					ImageIO.read(new File("src\\resources\\Images\\rSkill.png")),
					Constants.StandardSkillSetSize - 2*Constants.StandardSkillImagePadding,
					Constants.StandardSkillSetSize - 2*Constants.StandardSkillImagePadding);
			Constants.qSkillEffectImage = ImageControlEngine.CallImage(
					ImageIO.read(new File("src\\resources\\Effects\\qImage.png")),
					190,
					45);
			Constants.wSkillEffectImage = ImageControlEngine.CallImage(
					ImageIO.read(new File("src\\resources\\Effects\\wImage.png")),
					136,
					114);
			Constants.eSkillEffectImage = ImageControlEngine.CallImage(
					ImageIO.read(new File("src\\resources\\Effects\\eImage.png")),
					150,
					150);
			Constants.rSkillEffectImage = ImageControlEngine.CallImage(
					ImageIO.read(new File("src\\resources\\Effects\\rImage.png")),
					180,
					180);
			Constants.FocusCursorImage = ImageControlEngine.CallImage(
					ImageIO.read(new File("src\\resources\\Images\\FocusCursor.png")),
					26,26);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Constants.StartTimeFlag = System.currentTimeMillis();
		//GameStartButtonSetting
		GameStartBtn.setBounds(
				50, 
				500, Constants.StandardButtonWidth, Constants.StandardButtonHeight);
		GameStartBtn.setFont(new Font(Constants.DefaultFontName, Font.PLAIN, 45));
		GameStartBtn.setForeground(Color.WHITE);
		GameStartBtn.setBorderPainted(false);
		GameStartBtn.setContentAreaFilled(false);
		GameStartBtn.setFocusPainted(false);
		GameStartBtn.setHorizontalAlignment(SwingConstants.LEFT);
		GameStartBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Constants.GoToSpecificPage(1);
				Constants.lue.addMessage("Go to Page 1");
				GameStartBtn.setVisible(false);
				GameStartBtn.setFocusable(false);
				GameEndBtn.setVisible(false);
				Constants.aim.startStage();
				for(int i=0;i<4;i++)
					Constants.sme[i].makeCool();
			}
		});
		GameStartBtn.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				Constants.MouseX = e.getX();
				Constants.MouseY = e.getY();
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				Constants.MouseX = e.getXOnScreen();
				Constants.MouseY = e.getYOnScreen();
			}
			
		});
		GameStartBtn.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				GameStartBtn.setForeground(new Color(150,150,150,255));
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				GameStartBtn.setForeground(Color.WHITE);
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		m.add(GameStartBtn);
		
		GameEndBtn.setBounds(
				50, 
				600, Constants.StandardButtonWidth, Constants.StandardButtonHeight);
		GameEndBtn.setFont(new Font(Constants.DefaultFontName, Font.PLAIN, 45));
		GameEndBtn.setForeground(Color.WHITE);
		GameEndBtn.setBorderPainted(false);
		GameEndBtn.setContentAreaFilled(false);
		GameEndBtn.setFocusPainted(false);
		GameEndBtn.setHorizontalAlignment(SwingConstants.LEFT);
		GameEndBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		GameEndBtn.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				Constants.MouseX = e.getX();
				Constants.MouseY = e.getY();
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				Constants.MouseX = e.getXOnScreen();
				Constants.MouseY = e.getYOnScreen();
			}
			
		});
		GameEndBtn.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				GameEndBtn.setForeground(new Color(150,150,150,255));
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				GameEndBtn.setForeground(Color.WHITE);
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		m.add(GameEndBtn);
	}
	
	private static void update() {
		Constants.FrameCount++;
		Constants.pde.update();
		for(int i=0;i<4;i++)
			Constants.sme[i].update();
		Constants.SkillNotPreparedSignal.update();
		if(Constants.user.getCurHP() == 0) {
			Constants.GoToSpecificPage(0);
			Constants.lue.addMessage("Go to Page 0");
			GameStartBtn.setVisible(true);
			GameStartBtn.setFocusable(true);
			GameEndBtn.setVisible(true);
			Constants.STAGE = 1;
		}
	}
	
	public static void main(String[] args) {
		Dimension dm = new Dimension(Constants.FrameWidth, Constants.FrameHeight);
		MainCustomFrame.setVisible(true);
		MainCustomFrame.setSize(Constants.FrameWidth, Constants.FrameHeight+40);
		MainCustomFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		MainCustomFrame.setFocusable(true);
		
		mainframe mf = new mainframe();
		mf.setVisible(true);
		mf.setSize(Constants.FrameWidth, Constants.FrameHeight);
		MainCustomFrame.add(mf);
		initial(mf);
		
		// Create a new blank cursor.
		Cursor custom = Toolkit.getDefaultToolkit().createCustomCursor(Constants.CursorImage,new Point(0, 0), "");
		Cursor blank = Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB),new Point(0, 0), "");
		MainCustomFrame.getContentPane().setCursor(custom);
		
		mainthread = new Thread(new Runnable() {
			@Override
			public void run() {
				for(;;) {
					try {
						Thread.sleep(1000/Constants.FPS);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					MainCustomFrame.repaint();
					mf.update();
					Constants.ElapsedTime = System.currentTimeMillis() - Constants.StartTimeFlag;
				}
			}
		});
		
		mainthread.start();
		
		//Mouse Action Listen
		mf.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				Constants.MouseX = e.getX();
				Constants.MouseY = e.getY();
			}

			@Override
			public void mouseMoved(MouseEvent arg0) {
				// TODO Auto-generated method stub
				Constants.MouseX = arg0.getX();
				Constants.MouseY = arg0.getY();
			}
		});
		
		mf.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				Constants.MouseX = e.getX();
				Constants.MouseY = e.getY();
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				Constants.MouseX = e.getX();
				Constants.MouseY = e.getY();
				switch(e.getButton()) {
				case MouseEvent.BUTTON3:
					//Move
					moveObject(e.getX(), e.getY());
					for(int i=0;i<4;i++)
						Constants.sme[i].show = false;
					Constants.FocusCursorMode = false;
					MainCustomFrame.getContentPane().setCursor(custom);
					break;
				case MouseEvent.BUTTON1:
					for(int i=0;i<4;i++) {
						if(Constants.sme[i].isFireable()&&Constants.sme[i].show) {
							Constants.user.stopMoving();
							Constants.user.fire(Constants.MouseX, Constants.MouseY, i);
							Constants.sme[i].showSwitch();
							Constants.FocusCursorMode = Constants.sme[i].show;
							if(Constants.sme[i].show)
								MainCustomFrame.getContentPane().setCursor(blank);
							else
								MainCustomFrame.getContentPane().setCursor(custom);
						}
					}
				}
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}
			
		});
		
		MainCustomFrame.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				switch(e.getKeyCode()) {
				case KeyEvent.VK_F3:
					Constants.isLogVisible = !Constants.isLogVisible;
					break;
				case KeyEvent.VK_S:
					Constants.user.stopMoving();
					break;
				case KeyEvent.VK_Q:
					for(int i=0;i<4;i++)
						Constants.sme[i].show = false;
					if(Constants.sme[3].isFireable()) {
						Constants.sme[3].showSwitch();
						Constants.FocusCursorMode = Constants.sme[3].show;
						if(Constants.sme[3].show)
							MainCustomFrame.getContentPane().setCursor(blank);
						else
							MainCustomFrame.getContentPane().setCursor(custom);
					}else {
						Constants.SkillNotPreparedSignal.touch();
					}
					break;
				case KeyEvent.VK_W:
					for(int i=0;i<4;i++)
						Constants.sme[i].show = false;
					if(Constants.sme[2].isFireable()) {
						Constants.sme[2].showSwitch();
						Constants.FocusCursorMode = Constants.sme[2].show;
						if(Constants.sme[2].show)
							MainCustomFrame.getContentPane().setCursor(blank);
						else
							MainCustomFrame.getContentPane().setCursor(custom);
					}else {
						Constants.SkillNotPreparedSignal.touch();
					}
					break;
				case KeyEvent.VK_E:
					for(int i=0;i<4;i++)
						Constants.sme[i].show = false;
					if(Constants.sme[1].isFireable()) {
						Constants.sme[1].showSwitch();
						Constants.FocusCursorMode = Constants.sme[1].show;
						if(Constants.sme[1].show)
							MainCustomFrame.getContentPane().setCursor(blank);
						else
							MainCustomFrame.getContentPane().setCursor(custom);
					}else {
						Constants.SkillNotPreparedSignal.touch();
					}
					break;
				case KeyEvent.VK_R:
					for(int i=0;i<4;i++)
						Constants.sme[i].show = false;
					if(Constants.sme[0].isFireable()) {
						Constants.sme[0].showSwitch();
						Constants.FocusCursorMode = Constants.sme[0].show;
						if(Constants.sme[0].show)
							MainCustomFrame.getContentPane().setCursor(blank);
						else
							MainCustomFrame.getContentPane().setCursor(custom);
					}else {
						Constants.SkillNotPreparedSignal.touch();
					}
					break;
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
	private void GeneralSetting(Graphics2D g) {
		g.setFont(new Font(Constants.DefaultFontName, Font.PLAIN, 15));
	}
	
	public static void moveObject(int tx, int ty) {

		Constants.pde.addPing(Constants.PingSet.MOVE_PING);
		
		double t1 = Math.atan2(
				50-Constants.user.getCury(),
				0-Constants.user.getCurx()
				);
		double t2 = Math.atan2(
				800-Constants.user.getCury(),
				0-Constants.user.getCurx()
				);
		double t3 = Math.atan2(
				800-Constants.user.getCury(),
				1126-Constants.user.getCurx()
				);
		Player me = Constants.user;
		double ang = Math.atan2(ty-me.getCury(),
				tx-me.getCurx());
		
		if((ang>t1)&&(ang<t3)) {		//t1~t3
			double ra = Constants.distance(
					Constants.user.getCurx(),
					Constants.user.getCury(),
					tx,
					ty
					);
			double hora = tx - Constants.user.getCurx();
			double vera = - ty + Constants.user.getCury();
			
			double rb = Constants.distance(
					0,50,1126,80);
			double verb = 750;
			double horb = 1126;
			
			double sinalp = vera/ra;
			double cosalp = hora/ra;
			
			double sinbet = verb/rb;
			double cosbet = horb/rb;
			
			double rate = (Constants.user.getCury() - 50)/750;
			double hors = 1126*rate;
			double xx = hors - Constants.user.getCurx();
			double length = xx*sinbet/(cosalp*sinbet+sinalp*cosbet);
			
			length -= Constants.user.getSize()/2;
			if(length<ra) {
				
				Constants.user.setDest(
						Constants.user.getCurx()+(length/ra)*hora,
						Constants.user.getCury()-(length/ra)*vera
						);
			}else {
				Constants.user.setDest(
						tx,
						ty
						);
			}
		}else {
			Constants.user.setDest(
					tx,
					ty
					);
		}
	}
}
