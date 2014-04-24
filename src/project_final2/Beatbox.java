package project_final2;

import java.awt.*;

import javax.swing.*;
import javax.sound.midi.*;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import project_final2.flickrfinal;

import com.aetrion.flickr.FlickrException;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.*;
import java.io.*;

public class Beatbox {
    flickrfinal display;
    boolean fixOrNot = false;
    static int count = 0;
    JButton FixPitch;
    JPanel mainPanel;
    ArrayList<JCheckBox> checkboxList;//arraylist checkboxList
    Sequencer sequencer;
    Sequence sequence;
    Track track;
    JFrame theFrame;
    JCheckBox c;
    int r, bpm;
    int b;
    int NumOfInstrument = 13;
    int NumOfBeats = 40;
    String[] instrumentNames = {"Piano", "French Horn", "Music Box", "Seashore", "Guitar", "Bass Drum", "Violin", "Cello", "Pan Flute", "Trumpet", "Tinkle Bell", "Applause", "Woodblock"};//, "Hand Clap", "Trumpet","Applause", "Maracas", "Whistle"};
    int[] instruments = {2, 61, 11, 123, 25, 35, 41, 43, 76, 57, 113, 127, 116};//,39,57,127,70,72};
    //int [] pitch = {15,30,45,60,75,};

    static int channel = 1;//channel = 9

//ting chen's comment///////////////////////////////////////////////////////////////////////////////////////////////////        
    ImageIcon backgroundimg;
    JPanel imagePanel;
    JPanel background;

    public void JFrameBackground() {
        backgroundimg = new ImageIcon(getClass().getResource("/mypackage1/back1.png"));// background image
        JLabel label = new JLabel(backgroundimg);// put background in a label

// set the size of label to be the same size of the panel(2000, 500)
        label.setBounds(0, 0, 1250, 500);
      //  label.setPreferredSize(new Dimension(1700, 500));
// translate the frame into panel, we can only use the setQpaque() to make the window opaque
        imagePanel = (JPanel) theFrame.getContentPane();
        imagePanel.setOpaque(false);

// default layout style of panel is BorderLayout
        imagePanel.setLayout(new FlowLayout());
        imagePanel.setPreferredSize(new Dimension(1250, 500));
        theFrame.getLayeredPane().setLayout(null);

// put the background to the bottom of the layout layer to be the background
        theFrame.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));

     //   theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //    theFrame.setSize(1300, 500);
        theFrame.setResizable(true);
        theFrame.setVisible(false);
    }
///ting chen's comment over///////////////////////////////////////////////////////////////////////////////////////////////	
public Beatbox(int a){
                 b =a;
    	if (a == 1){//sad
                r = 30;
    		bpm = 30;
    	}
    	else if (a == 2){//clam
    		r = 60;
    		bpm = 60;
    	}
    	else if ( a == 3){//happy
    		r = 90;
    		bpm = 120;
    	}
    }
    public Beatbox(){
    	
    }
    public static void main(String[] args) {
        new Beatbox().buildGUI();
    }

    public void buildGUI() {
        String a;
        theFrame = new JFrame("Hear, Watch it");
        theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BorderLayout layout = new BorderLayout();
        background = new JPanel(layout);
        JPanel ConPL = new JPanel();
        background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));//for aesthetic
        //ConPL.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));



//----------------------------------------------------------------------------------------------------------------//                
        checkboxList = new ArrayList<JCheckBox>();
        //Box buttonBox = new Box(BoxLayout.Y_AXIS); //Box buttonBox
        Box buttonBox = new Box(BoxLayout.X_AXIS);

        FixPitch = new JButton(" Fix? ");
        FixPitch.addActionListener(new FixPitchListener());
        buttonBox.add(FixPitch);

        JButton SelectAll = new JButton("RandPick");
        SelectAll.addActionListener(new SelectAllListener());
        buttonBox.add(SelectAll);//Box buttonBox

        JButton start = new JButton("Start");
        start.addActionListener(new MyStarListener());
        buttonBox.add(start);

        JButton stop = new JButton("Stop");
        stop.addActionListener(new MyStopListener());
        buttonBox.add(stop);

        JButton upTempo = new JButton("Tempo up");
        upTempo.addActionListener(new MyUpTempoListener());
        buttonBox.add(upTempo);

        JButton downTempo = new JButton("Tempo down");
        downTempo.addActionListener(new MyDownTempoListener());
        buttonBox.add(downTempo);

        JButton CancelSelectAll = new JButton("Deselect All");
        CancelSelectAll.addActionListener(new CancelSelectAll());
        buttonBox.add(CancelSelectAll);

        JButton SaveIt = new JButton("Save");
        SaveIt.addActionListener(new SaveItlistener());
        buttonBox.add(SaveIt);

        JButton LoadIt = new JButton("Load");
        LoadIt.addActionListener(new LoadItlistener());
        buttonBox.add(LoadIt);

        JButton FinalResult = new JButton("Hear, Watch it");
                FinalResult.addActionListener(new ActionListener(){
                  public void actionPerformed(ActionEvent e) {
                	  Thread thread1 = new Thread(){
                		  public void run(){
                			  try{
                				 display = new flickrfinal(b);
                				  display.testimg("happy");
                				  display.frame.setVisible(false);
                				  theFrame.setVisible(true);
             			  
                                          
                                          }catch(Exception e){
                				  //donothing
                			  }
                		  }
                	  };
                	  Thread thread2 = new Thread(){
                		  public void run(){
                			  
                			  buildTrackAndStart();
                			  theFrame.setVisible(false);
     
                			  
                		  }
                	  };
                             	  Thread thread3 = new Thread(){
                		  public void run(){
                			  try{
                				  ControlButton controltest = new ControlButton();
                                          controltest.setVisible(true); 
             			  
                                          
                                          }catch(Exception e){
                				  //donothing
                			  }
                		  }
                	  };
                      thread1.start();
                      thread2.start();
                       thread3.start();
                }

          });

/*---------------------------------------------------------------------------------------------------------------------
        FinalResult.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

//ther is to add display music and picture part                            
                buildTrackAndStart();
                flickrfinal test = new flickrfinal();
                try {
                    test.getimg("sad");
                } catch (ParserConfigurationException ex) {
                    Logger.getLogger(Beatbox.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Beatbox.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SAXException ex) {
                    Logger.getLogger(Beatbox.class.getName()).log(Level.SEVERE, null, ex);
                } catch (FlickrException ex) {
                    Logger.getLogger(Beatbox.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Beatbox.class.getName()).log(Level.SEVERE, null, ex);
                }
                theFrame.setVisible(false);
            }
        });
        
 -------------------------------------------------------------------------------------------------------------------*/       
        buttonBox.add(FinalResult);

        
        Box nameBox = new Box(BoxLayout.Y_AXIS);
		//Box numBox = new Box(BoxLayout.X_AXIS);
        //for ( int i = 0; i<10; i++){

//ting chen's ------------------------------------------------------------------------------------------------//                
        for (int i = 0; i < NumOfInstrument; i++) {

            JLabel cur = new JLabel(instrumentNames[i]);
            cur.setOpaque(false);
            if(i%3==0){
                cur.setForeground(Color.red);
            }
            else if(i%3==1){
                cur.setForeground(Color.ORANGE);
            }
            else{
                cur.setForeground(Color.green);
            }
            cur.setFont(new Font("Marlett", Font.BOLD, 14));
            nameBox.add(cur);

            Component verticalStrut = Box.createVerticalStrut(16);
            nameBox.add(verticalStrut);
//ting chen's ------------------------------------------------------------------------------------------------//

                  //  nameBox.add(new Label(instrumentNames[i]));
        }//end for loop
        ConPL.add(BorderLayout.CENTER, buttonBox);
		//background.add(BorderLayout.SOUTH, ConPL);
        //background.add(BorderLayout.SOUTH, buttonBox);
        background.add(BorderLayout.WEST, nameBox);

        theFrame.getContentPane().add(background, BorderLayout.CENTER);
        theFrame.getContentPane().add(ConPL, BorderLayout.SOUTH);
		//
        //GridLayout grid = new GridLayout(17,16);
        GridLayout grid = new GridLayout(NumOfInstrument, NumOfBeats);

        //grid.setVgap(1);
        grid.setVgap(0);
        grid.setHgap(0);
        mainPanel = new JPanel(grid);
        background.add(BorderLayout.CENTER, mainPanel);
        mainPanel.setOpaque(false);
		//for (int i = 0; i<16; i++){
        //mainPanel.add(new Label(instrumentNum[i]));
        //}

        for (int i = 0; i < NumOfBeats * NumOfInstrument; i++) {
            c = new JCheckBox();
            c.setSelected(false);
            checkboxList.add(c);//arraylist checkboxList
            mainPanel.add(c);//add JCheckBox into mainPanel
        }//end loop

        setUpMidi();
        
        
//here is ting chen's comment	///////////////////////////////////////////////////////////////////////////////////
        background.setOpaque(false);
        ConPL.setOpaque(false);
        JFrameBackground();
		//theFrame.setBounds(300,300,300,300);//set JFrame
        //theFrame.setBounds(0,0,1700,500);
        //make frame fit its contents
        theFrame.setSize(1250, 500);
        //theFrame.pack();
        theFrame.setVisible(true);

    }//close buildGUI() method

public  class ControlButton extends javax.swing.JFrame {

/**
* Creates new form ControlButton
*/
public ControlButton() {
   initComponents();
}

/**
* This method is called from within the constructor to initialize the form.
* WARNING: Do NOT modify this code. The content of this method is always
* regenerated by the Form Editor.
*/
@SuppressWarnings("unchecked")
// <editor-fold defaultstate="collapsed" desc="Generated Code">                          
private void initComponents() {

   Stop = new javax.swing.JButton();
   Start = new javax.swing.JButton();
   TempoUp = new javax.swing.JButton();
   TempoDown = new javax.swing.JButton();

   setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
   setLocation(new java.awt.Point(2000, 1000));

   Stop.setText("Stop");
   Stop.addMouseListener(new java.awt.event.MouseAdapter() {
       public void mouseClicked(java.awt.event.MouseEvent evt) {
           StopMouseClicked(evt);
       }
   });

   Start.setText("Start");
   Start.addMouseListener(new java.awt.event.MouseAdapter() {
       public void mouseClicked(java.awt.event.MouseEvent evt) {
           StartMouseClicked(evt);
       }
   });

   TempoUp.setText("Up");
   TempoUp.addMouseListener(new java.awt.event.MouseAdapter() {
       public void mouseClicked(java.awt.event.MouseEvent evt) {
           TempoUpMouseClicked(evt);
       }
   });

   TempoDown.setText("Down");
   TempoDown.addMouseListener(new java.awt.event.MouseAdapter() {
       public void mouseClicked(java.awt.event.MouseEvent evt) {
           TempoDownMouseClicked(evt);
       }
   });
   TempoDown.addActionListener(new java.awt.event.ActionListener() {
       public void actionPerformed(java.awt.event.ActionEvent evt) {
           TempoDownActionPerformed(evt);
       }
   });

   javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
   getContentPane().setLayout(layout);
   layout.setHorizontalGroup(
       layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
       .addGroup(layout.createSequentialGroup()
           .addContainerGap()
           .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
               .addComponent(Start, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
               .addComponent(TempoUp, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addComponent(Stop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
               .addComponent(TempoDown, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
           .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
   );
   layout.setVerticalGroup(
       layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
       .addGroup(layout.createSequentialGroup()
           .addContainerGap()
           .addComponent(Start, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
           .addGap(4, 4, 4)
           .addComponent(Stop, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
           .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
           .addComponent(TempoUp, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
           .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
           .addComponent(TempoDown, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
           .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
   );

   pack();
}// </editor-fold>                        

private void TempoDownActionPerformed(java.awt.event.ActionEvent evt) {                                          
   // TODO add your handling code here:
   
   
}                                         

private void StartMouseClicked(java.awt.event.MouseEvent evt) {                                   
   // TODO add your handling code here:
    //buildTrackAndStart();
   sequencer.start();
}                                  

private void StopMouseClicked(java.awt.event.MouseEvent evt) {                                  
   // TODO add your handling code here:
   sequencer.stop();
   display.frame.setVisible(false);
   //this.setVisible(false);
   //theFrame.setVisible(true);
  
}                                 

private void TempoUpMouseClicked(java.awt.event.MouseEvent evt) {                                     
   // TODO add your handling code here:
   float tempoFactor = sequencer.getTempoFactor();
   sequencer.setTempoFactor((float) (tempoFactor * 2));
}                                    

private void TempoDownMouseClicked(java.awt.event.MouseEvent evt) {                                       
   // TODO add your handling code here:
   float tempoFactor = sequencer.getTempoFactor();
   sequencer.setTempoFactor((float) (tempoFactor * 0.5));
}                                      

/**
* @param args the command line arguments
*/


/*--------------------------------------------------------------------------------------------------------------------------------
public static void main(String args[]) {
   /* Set the Nimbus look and feel */
   //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
   /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
    * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html */
/*         
   try {
       for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
           if ("Nimbus".equals(info.getName())) {
               javax.swing.UIManager.setLookAndFeel(info.getClassName());
               break;
           }
       }
   } catch (ClassNotFoundException ex) {
       java.util.logging.Logger.getLogger(ControlButton.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
   } catch (InstantiationException ex) {
       java.util.logging.Logger.getLogger(ControlButton.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
   } catch (IllegalAccessException ex) {
       java.util.logging.Logger.getLogger(ControlButton.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
   } catch (javax.swing.UnsupportedLookAndFeelException ex) {
       java.util.logging.Logger.getLogger(ControlButton.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
   }
   //</editor-fold>

   /* Create and display the form 
   java.awt.EventQueue.invokeLater(new Runnable() {
       public void run() {
           new ControlButton().setVisible(true);
       }
   });
}
----------------------------------------------------------------------------------------------------------------------*/

// Variables declaration - do not modify                     
private javax.swing.JButton Start;
private javax.swing.JButton Stop;
private javax.swing.JButton TempoDown;
private javax.swing.JButton TempoUp;
// End of variables declaration                   
}
    public void setUpMidi() {
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequence = new Sequence(Sequence.PPQ, 4);
            track = sequence.createTrack();
            sequencer.setTempoInBPM(120);

        } catch (Exception e) {
            e.printStackTrace();
        }//end catch
    }//end setUpMidi() method

    public void buildTrackAndStart() {
        ArrayList<Integer> trackList = null;
        sequence.deleteTrack(track);
        track = sequence.createTrack();
	//	int[] trackList = null;
        //	int[] PitchList = null;

        for (int i = 0; i < NumOfInstrument; i++) {
			//i row

            trackList = new ArrayList<Integer>();
			//PitchList = new int[5];
            //int instrumentNum = instruments[i];	
            //check each instrument

            /* following code will change instruments
             try{
             ShortMessage instrumentChange = new ShortMessage();
             instrumentChange.setMessage(ShortMessage.PROGRAM_CHANGE, channel, instruments[i],0);
             track.add(new MidiEvent(instrumentChange, 0));
             //System.out.println("I changed the instrument to " +  instruments[i]);
             }catch(Exception e){
             //do nothing
             }
             */
            for (int j = 0; j < NumOfBeats; j++) {
			//j column

                JCheckBox jc = checkboxList.get(j + NumOfBeats * i);
                if (jc.isSelected()) {
					//PitchList[j] = pitch[j];
                    //trackList[j] = instrumentNum;
                    int key = instruments[i];
                    trackList.add(new Integer(key));
					 //trackList.add(1);
                    //at J beat, i instrument is selected
                } else {
                    trackList.add(null);
				//trackList[j] = 0;
                    //PitchList[j] = 0;
                }//end elseif	

            }//close inner loop

            makeTracks(trackList);
            //track.add(makeEvent(192,channel,instruments[i],0,3*NumOfBeats+1));
        }//close outter loop
        //track.add(makeEvent(192,channel,1,0,4));

        try {
            sequencer.setSequence(sequence);
            sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);
            sequencer.start();
            //sequencer.setTempoInBPM(120);
            sequencer.setTempoInBPM(bpm);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//close buildTrackAndStart() method;

    public void makeTracks(ArrayList list) {
        //List is instrumentNum, PitchList is PitchNum
        Iterator it = list.iterator();
        int y = 0;
        for (int i = 0; i < NumOfBeats; i++) {
            Integer num = (Integer) it.next();

            if (num != null) {
                int numKey = num.intValue();
    		//	track.add(makeEvent(144,channel,numKey, 100, i));
                //track.add(makeEvent(128,channel,numKey, 100, i+5));
                System.out.println("THE INSTRUMENT NOW IS " + numKey);

                if (fixOrNot == false) {
                    r = (int) ((Math.random() * 127 + 1));
                    Random ran = new Random();
                    y = ran.nextInt(i + 2) + 10;
                    //y= i +2;
                } else {
                    y = i + 2;
                }

                /* the following code generates random pitch
                 track.add(makeEvent(144,channel,r,100,i));
                 track.add(makeEvent(176,channel,127,0,i));
                 track.add(makeEvent(128,channel,r,100,i+2));
                 */
                track.add(makeEvent(192, channel, numKey, 0, i));
                track.add(makeEvent(144, channel, r, 100, i + 1));
                track.add(makeEvent(176, channel, 127, 0, y));
                track.add(makeEvent(128, channel, r, 100, y + 1));

            }
			//track.add(makeEvent(144,channel,List[i],100,i));//144 means note on, instrument 9 means drum, 1 piano. key: (note:0-127), velocity 100 and note 5
            //track.add(makeEvent(176,channel,127,0,i));
            //track.add(makeEvent(128,channel,List[i],100,i+10));//128 means note off
        }

    }//close makeTracks() method

    public MidiEvent makeEvent(int msgType, int Channel, int Note, int Velocity, int BeatOrder) {
        MidiEvent event = null;
        try {
            ShortMessage a = new ShortMessage();
            a.setMessage(msgType, Channel, Note, Velocity);
            event = new MidiEvent(a, BeatOrder);
        } catch (Exception e) {
            e.printStackTrace();
        }//end catch
        return event;
    }//close makeEvent() method;

    public class MyStarListener implements ActionListener {

        public void actionPerformed(ActionEvent a) {
            buildTrackAndStart();
            
        }
    }//close inner class

    public class MyStopListener implements ActionListener {

        public void actionPerformed(ActionEvent a) {
            sequencer.stop();
        }
    }//close inner class

    public class MyUpTempoListener implements ActionListener {

        public void actionPerformed(ActionEvent a) {
            float tempoFactor = sequencer.getTempoFactor();
            //sequencer.setTempoFactor((float)(tempoFactor * 1.03));
            sequencer.setTempoFactor((float) (tempoFactor * 2));
        }
    }//close inner class

    public class MyDownTempoListener implements ActionListener {

        public void actionPerformed(ActionEvent a) {
            float tempoFactor = sequencer.getTempoFactor();
            //sequencer.setTempoFactor((float)(tempoFactor * 0.97));
            sequencer.setTempoFactor((float) (tempoFactor * 0.5));
        }
    }//close inner class

    public class FixPitchListener implements ActionListener {

        public void actionPerformed(ActionEvent a) {
            if (count == 1) {
                fixOrNot = true;
                FixPitch.setText("Fixed");
                count = 0;
            } else if (count == 0) {
                fixOrNot = false;
                FixPitch.setText("Rando");
                count = 1;
            }
        }//close inner class
    }

    public class CancelSelectAll implements ActionListener {

        public void actionPerformed(ActionEvent a) {
            for (int i = 0; i < NumOfBeats * NumOfInstrument; i++) {
                c = checkboxList.get(i);
                c.setSelected(false);
                checkboxList.set(i, c);
            }
        }
    }

    public class SelectAllListener implements ActionListener {

        public void actionPerformed(ActionEvent a) {
            int d = (int) (Math.random() * NumOfBeats * NumOfInstrument);
            for (int i = 0; i < d; i++) {
                int b = (int) (Math.random() * NumOfBeats * NumOfInstrument);
                System.out.println(b);
                c = checkboxList.get(b);
                c.setSelected(true);
                checkboxList.set(b, c);

            }
        }
    }//close inner class

    public class SaveItlistener implements ActionListener {

        boolean[] checkBoxState = new boolean[NumOfBeats * NumOfInstrument];

        public void actionPerformed(ActionEvent e) {

            for (int i = 0; i < NumOfBeats * NumOfInstrument; i++) {

                JCheckBox check = (JCheckBox) checkboxList.get(i);

                if (check.isSelected()) {
                    checkBoxState[i] = true;
                } else {
                    checkBoxState[i] = false;
                }

            }//end for loop

            try {
                JFileChooser fileSave = new JFileChooser();
                fileSave.showSaveDialog(theFrame);
                saveFile(fileSave.getSelectedFile());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }//close method

        public void saveFile(File file) {
            try {
                FileOutputStream fileoutput = new FileOutputStream(file);
                ObjectOutputStream os = new ObjectOutputStream(fileoutput);
                os.writeObject(checkBoxState);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }//close SaveItlistener class

    public class LoadItlistener implements ActionListener {

        boolean[] checkBoxstate = null;

        public void actionPerformed(ActionEvent e) {

            try {
                JFileChooser fileOpen = new JFileChooser();
                fileOpen.showOpenDialog(theFrame);
                loadFile(fileOpen.getSelectedFile());
                /*
                 FileInputStream  fileinput = new FileInputStream(new File("CheckBox.ser"));
                 ObjectInputStream is = new ObjectInputStream(fileinput);
                 checkBoxstate = (boolean[]) is.readObject();
                 */
				//load directly

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            for (int i = 0; i < NumOfBeats * NumOfInstrument; i++) {

                JCheckBox check = (JCheckBox) checkboxList.get(i);

                if (checkBoxstate[i]) {
                    check.setSelected(true);
                    checkboxList.set(i, check);
                } else {
                    check.setSelected(false);
                    checkboxList.set(i, check);
                }

            }//end for loop
            sequencer.stop();
			//buildTrackAndStart();

        }
        
//----------------------ting chen's -------------------------------------------------------------------------------------------//
        
        /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tingchen
 */
        //ControlButton2 test = new ControlButton2();
        
       

        
//----------------------ting chen's -------------------------------------------------------------------------------------------//
        

        private void loadFile(File file) {
            try {
                FileInputStream fileinput = new FileInputStream(file);
                ObjectInputStream is = new ObjectInputStream(fileinput);
                checkBoxstate = (boolean[]) is.readObject();

            } catch (Exception ex) {
                System.out.print("test");
                //ex.printStackTrace();
            }

        }//end loadFIle
    }//end LoadItlistener class

	//public void makeTracks(int[] List, int[] PitchList){
}//close class

