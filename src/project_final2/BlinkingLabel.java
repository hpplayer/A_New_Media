package project_final2;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;


public class BlinkingLabel extends JLabel implements Runnable{

private boolean show=false,blink=true;
private JLabel iconImg; 
private JFrame parent;

public BlinkingLabel(JLabel icon, JFrame pane)
{

this.iconImg = icon;
this.parent = pane;

this.setVisible(true);
new Thread(this).start();

}

public void run() {
while (blink) {
show=!show;

if(show)
parent.setVisible(true);
else
parent.setVisible(false);

repaint();
try { Thread.sleep(100); } catch (Exception e) { }
}
}

public boolean isBlink() {
return blink;
}

public void setBlink(boolean blink) {
this.blink = blink;
}

}