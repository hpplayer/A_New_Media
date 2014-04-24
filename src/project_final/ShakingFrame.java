package project_final;
import java.awt.Point;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
 
public class ShakingFrame 
{
    //variables
    private JFrame frame;
   /// public static final int UPDATE_TIME = 50;
    public static final int UPDATE_TIME = 75;
    public static final int DURATION = 1000;
 
    private Point primaryLocation;
    private long startTime;
    private Timer time;
 
    public ShakingFrame(JFrame f)
    {
        this.frame = f;
    }//constructor
     
    //used on a 'ShakingFrame' object to shake the jframe
    public void startShake()
    {
        primaryLocation = frame.getLocation();
        startTime = System.currentTimeMillis();
        time= new Timer(UPDATE_TIME,timeListener);
        time.start();
    }
     
    //stops shake/puts back in original place
    public void stopShake()
    {
        //code to stop the screen shaking
        time.stop();
        frame.setLocation(primaryLocation);
        frame.setVisible(true);
        //frame.repaint();
    }
     
    private class ActionTime implements ActionListener
    {
         private int xOffset, yOffset;
         //every interval the timer ticks, this is performed
        @Override
         public void actionPerformed(ActionEvent e)
         {
             //get elapsed time(running time)
             long elapsedTime = System.currentTimeMillis() - startTime;
             Random r = new Random();
             int op = r.nextInt(5);
              
             if ( op > 0)
             {
                //change x and y offset then reallocate frame
                xOffset = primaryLocation.x + (r.nextInt(20));
                yOffset = primaryLocation.y + (r.nextInt(20));
                frame.setLocation(xOffset,yOffset);
               // frame.setVisible(false);                
                //frame.repaint();
             }
             else
             {
                //change x and y offset then reallocate frame
                xOffset = primaryLocation.x - (r.nextInt(20));
                yOffset = primaryLocation.y - (r.nextInt(20));
                frame.setLocation(xOffset,yOffset);
              //  frame.setVisible(true);
                //frame.repaint();
             }
             //elapsedTime exceed  DURATION, so stop now
             if(elapsedTime > DURATION)
             {   
                stopShake();
             }
         }
    }
    //listener/instance of ActionTime
    private ActionTime timeListener = new ActionTime();
}