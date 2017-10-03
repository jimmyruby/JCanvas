//JCanvas is a graphics package for Java developers that aims to simplify the game creating process.
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.MouseInfo;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
public class JCanvas{
	public static JPanel panel;
	public static JFrame frame;
	public static TreeMap<String, Boolean> keys = new TreeMap<String, Boolean>();;
	public static int width, height, mouseX, mouseY, waitPeriod, framerate, fontSize, textHeight;
	public static long duration, startTime;
	public static boolean mousePressed, keyPressed, mouseClicked, firstMouseClicked, keyClicked, firstKeyClicked;
	public static String fontStyle;
	public static Font font;
	public JCanvas(){
		width = frame.getSize().width;
		height = frame.getSize().height;
		mouseX = (int)MouseInfo.getPointerInfo().getLocation().getX();
		mouseY = (int)MouseInfo.getPointerInfo().getLocation().getY();
		keyPressed = false;
		firstMouseClicked = true;
        for(int i = 0; i < 65536; i++){
        	keys.put(((char)i)+"", false);
        }
        framerate = 60;
        fontStyle = "Arial";
        fontSize = 36;
        font = new Font(fontStyle, Font.PLAIN, fontSize);
        panel.setFont(font);
        waitPeriod = (int)Math.round(1.0/framerate*1000);
	}

//	Returns the file the current program is running on	

	public static File getGlobalFile(String path){
		String longPath = path;
		try{
			longPath = JCanvas.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			for(int i = longPath.length()-2; i > 0; i--){
				if(longPath.substring(i, i+1).equals("/")){
					longPath = longPath.substring(0, i+1);
					break;
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		longPath += path;
		return new File(longPath);
	}
	public static void framerate(int rate){
		framerate = rate;
		waitPeriod = (int)Math.round(1.0/framerate*1000);
	}
	public static void background(int r, int g, int b){
		Color c = new Color(r, g, b);
		panel.setBackground(c);
	}
	public static void updateMouse(){
		mouseX = (int)MouseInfo.getPointerInfo().getLocation().getX();
		mouseY = (int)MouseInfo.getPointerInfo().getLocation().getY();
		mouseClicked = mouseClicked();
		keyClicked = keyClicked();
	}
	public static void addKeys(){
		frame.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				keyPressed = true;
				keys.put((e.getKeyChar()+"").toLowerCase(), true);
			}
			public void keyReleased(KeyEvent e){
				keyPressed = false;
				keys.put((e.getKeyChar()+"").toLowerCase(), false);
			}
		});
	}
	public static void addMouse(){
        frame.addMouseListener(new MouseAdapter(){
        	public void mousePressed(MouseEvent e){
        		mousePressed = true;
        	}
        	public void mouseReleased(MouseEvent e){
        		mousePressed = false;
        	}
        });
	}
	public static int r = 0; 
	public static int g = 0; 
	public static int b = 0;
	public static void setColor(int red, int green, int blue){
		r = red;
		g = green;
		b = blue;
	}
	protected static void image(JImage image, int x, int y){
		JLabel c = new JLabel(image.image);
		c.setLocation(x, y);
		c.setSize(image.image.getIconWidth(), image.image.getIconHeight());
		panel.add(c);
	}
	public static void setFontStyle(String fontType){
		fontStyle = fontType;
		font = new Font(fontStyle, Font.PLAIN, fontSize);
	}
	public static void setFontSize(int fontsize){
		fontsize = fontSize;
		font = new Font(fontStyle, Font.PLAIN, fontSize);
	}
	public static void text(String text, int x, int y){
		FontMetrics metric = panel.getGraphics().getFontMetrics(font);
		int textWidth = metric.stringWidth(text);
		textHeight = metric.getHeight();
		JLabel c = new JLabel(text);
		c.setForeground(new Color(r, g, b));
		c.setLocation(x, y);
		c.setSize(textWidth, textHeight);
		panel.add(c);
	}
	public static int textWidth(String text){
		return panel.getGraphics().getFontMetrics(font).stringWidth(text);
	}
	public static void rect(int x, int y, int w, int h){
		JFrame rect = new JFrame();
		rect.setBackground(new Color(r, g, b));
		rect.setLocation(x, y);
		rect.setSize(w, h);
		panel.add(rect);
	}
	public static boolean mouseClicked(){
		if(!mousePressed){
			mouseClicked = false;
		}
		if(mousePressed && mouseClicked){
			mouseClicked = false;
			firstMouseClicked = false;
		}
		if(mousePressed){
    		if(firstMouseClicked){
    			mouseClicked = true;
    		}
		}
		else{
			firstMouseClicked = true;
		}
		if(mouseClicked){
			return true;
		}
		return false;
	}
	public static boolean keyClicked(){
		if(!keyPressed){
			keyClicked = false;
		}
		if(keyPressed && keyClicked){
			keyClicked = false;
			firstKeyClicked = false;
		}
		if(keyPressed){
    		if(firstKeyClicked){
    			keyClicked = true;
    		}
		}
		else{
			firstKeyClicked = true;
		}
		if(keyClicked){
			return true;
		}
		return false;
	}
	public static void refresh(){
		try{
			frame.setContentPane(panel);
			frame.setVisible(true);
			duration = System.currentTimeMillis() - startTime;
			if(duration < waitPeriod){
				TimeUnit.MILLISECONDS.sleep(waitPeriod - duration);
			}
			startTime = System.currentTimeMillis();
			panel.removeAll();
			addMouse();
			addKeys();
			updateMouse();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
    protected static JCanvas size(int x, int y){
    	try{
	        frame = new JFrame();
	        panel = new JPanel();
	        
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setSize(x, y);
	        frame.setResizable(false);
	        
			panel.setOpaque(true);
			panel.setBackground(Color.WHITE);
			panel.setLayout(null);
			
	        JCanvas jc = new JCanvas();
	        refresh();
	        return jc;
    	}
    	catch(Exception e){
    		e.printStackTrace();
	        frame = new JFrame();
	        panel = new JPanel();
	        JCanvas jc = new JCanvas();
	        return jc;
    	}
    }	

}

