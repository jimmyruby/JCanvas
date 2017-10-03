import java.awt.*;
import javax.swing.*;

public class JImage{
	public String path;
	public ImageIcon image;
	public int width, height;
	public JImage(String path){
		this.path = path;
		try{
			String longPath = JImage.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			for(int i = longPath.length()-2; i > 0; i--){
				if(longPath.substring(i, i+1).equals("/")){
					longPath = longPath.substring(0, i+1);
					break;
				}
			}
			longPath = longPath + path;
			this.image = new ImageIcon(longPath);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		this.width = image.getIconWidth();
		this.height = image.getIconHeight();
	}
	public JImage resize(int x, int y){
		Image n = image.getImage();
		image = new ImageIcon(n.getScaledInstance(x, y, Image.SCALE_FAST));
		width = image.getIconWidth();
		height = image.getIconHeight();
		return this;
	}
}
