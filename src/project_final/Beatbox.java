package project_final;
import java.awt.*;

import javax.swing.*;
import javax.sound.midi.*;
import javax.xml.parsers.ParserConfigurationException;

import project_final.flickrfinal;

import org.xml.sax.SAXException;

import com.aetrion.flickr.FlickrException;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.*;
import java.io.*;

public class Beatbox {
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
	int NumOfInstrument = 13;
	int NumOfBeats = 40;
	String[] instrumentNames = {"Bright Acoustic Piano","French Horn","Music Box","Seashore", "Acoustic Guitar", "Bass Drum", "Violin", "Cello","Pan Flute", "Trumpet", "Tinkle Bell", "Applause", "Woodblock"};//, "Hand Clap", "Trumpet","Applause", "Maracas", "Whistle"};
	int [] instruments = {2,61, 11,123,32,35,41,43,76,57,113, 127, 116};//,39,57,127,70,72};
	//int [] pitch = {15,30,45,60,75,};

	static int channel = 1;//channel = 9
	
	
	public static void main(String[] args){
		new Beatbox().buildGUI();
	}
	    
	public void buildGUI(){
		String a;
		theFrame = new JFrame("CreateYourMusic");
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BorderLayout layout = new BorderLayout();
		JPanel background = new JPanel(layout);
		JPanel ConPL = new JPanel();
		background.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));//for aesthetic
		//ConPL.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
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
                				  flickrfinal display = new flickrfinal();
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
                      thread1.start();
                      thread2.start();
                }

          });
	
//ther is to add display music and picture part                            
                      
                	  /*
                      buildTrackAndStart();
                      flickrfinal test = new flickrfinal();
                      try {
                          test.testimg("happy");
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
                       */

        buttonBox.add(FinalResult);
		
		Box nameBox = new Box(BoxLayout.Y_AXIS);
		//Box numBox = new Box(BoxLayout.X_AXIS);
		//for ( int i = 0; i<10; i++){
		for ( int i = 0; i<NumOfInstrument; i++){
			nameBox.add(new Label(instrumentNames[i]));

			
		}//end for loop
		ConPL.add(BorderLayout.CENTER,buttonBox);
		//background.add(BorderLayout.SOUTH, ConPL);
		//background.add(BorderLayout.SOUTH, buttonBox);
		background.add(BorderLayout.WEST, nameBox);
	
		
		theFrame.getContentPane().add(background,BorderLayout.CENTER);
		theFrame.getContentPane().add(ConPL, BorderLayout.SOUTH);
		//
		//GridLayout grid = new GridLayout(17,16);
		GridLayout grid = new GridLayout(NumOfInstrument,NumOfBeats);

		//grid.setVgap(1);
		grid.setVgap(10);
		grid.setHgap(10);
		mainPanel = new JPanel(grid);
		background.add(BorderLayout.CENTER, mainPanel);
		//for (int i = 0; i<16; i++){
		//mainPanel.add(new Label(instrumentNum[i]));
		//}
		
		for (int i = 0; i<NumOfBeats*NumOfInstrument; i++){
			c = new JCheckBox();
			c.setSelected(false);
			checkboxList.add(c);//arraylist checkboxList
			mainPanel.add(c);//add JCheckBox into mainPanel
		}//end loop
		
		setUpMidi();
		
		//theFrame.setBounds(300,300,300,300);//set JFrame
		theFrame.setBounds(100,100,300,300);
		theFrame.pack();//make frame fit its contents
		theFrame.setVisible(true);
		
	}//close buildGUI() method
	
	
	public void setUpMidi(){
		try{
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequence = new Sequence(Sequence.PPQ, 4);
			track = sequence.createTrack();
			sequencer.setTempoInBPM(120);
		
			
		}catch(Exception e){
			e.printStackTrace();
		}//end catch
		}//end setUpMidi() method
	
	
	public void buildTrackAndStart(){
		ArrayList<Integer> trackList =null;
		sequence.deleteTrack(track);
		track = sequence.createTrack();
	//	int[] trackList = null;
	//	int[] PitchList = null;
		
		for(int i = 0; i < NumOfInstrument; i++){
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
		
			for (int j = 0; j < NumOfBeats; j++){
			//j column
	
				JCheckBox jc = checkboxList.get(j+NumOfBeats*i);
				if(jc.isSelected()){
					//PitchList[j] = pitch[j];
					//trackList[j] = instrumentNum;
					 int key = instruments[i];
					 trackList.add(new Integer(key));
					 //trackList.add(1);
				//at J beat, i instrument is selected
				}else{
					 trackList.add(null);
				//trackList[j] = 0;
				//PitchList[j] = 0;
				}//end elseif	
				
			}//close inner loop
		
	
			makeTracks(trackList);
			//track.add(makeEvent(192,channel,instruments[i],0,3*NumOfBeats+1));
			}//close outter loop
		//track.add(makeEvent(192,channel,1,0,4));
		
		try{
			sequencer.setSequence(sequence);
			sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);
			sequencer.start();
			//sequencer.setTempoInBPM(120);
			sequencer.setTempoInBPM(60);
		}catch(Exception e){e.printStackTrace();}
	}//close buildTrackAndStart() method;
	
	public void makeTracks(ArrayList list){
		//List is instrumentNum, PitchList is PitchNum
		Iterator it = list.iterator();
		int r = 0;
		int y = 0;
		for (int i = 0; i < NumOfBeats; i++){	
			Integer num = (Integer) it.next();
			
			if (num != null){
    			int numKey = num.intValue();
    		//	track.add(makeEvent(144,channel,numKey, 100, i));
    			//track.add(makeEvent(128,channel,numKey, 100, i+5));
	    		//System.out.println("THE INSTRUMENT NOW IS " + numKey);
	    		
	    		if (fixOrNot == false){
    			r = (int) ((Math.random()*127 + 1));
    			Random ran = new Random();
    			y = ran.nextInt(i+2)+10;
    			//y= i +2;
	    		}else{
	    		//r = 60;
	    		r = 70;
	    		y= i +2;
	    		}
	    			
    			/* the following code generates random pitch
				track.add(makeEvent(144,channel,r,100,i));
				track.add(makeEvent(176,channel,127,0,i));
				track.add(makeEvent(128,channel,r,100,i+2));
    			 */
    			

    			track.add(makeEvent(192,channel,numKey,0,i));
    			track.add(makeEvent(144,channel,r,100,i+1));
				track.add(makeEvent(176,channel,127,0,y));
				track.add(makeEvent(128,channel,r,100,y+1));
			
	    
    		}
			//track.add(makeEvent(144,channel,List[i],100,i));//144 means note on, instrument 9 means drum, 1 piano. key: (note:0-127), velocity 100 and note 5
			//track.add(makeEvent(176,channel,127,0,i));
			//track.add(makeEvent(128,channel,List[i],100,i+10));//128 means note off
		}

		
	}//close makeTracks() method
	
	
	public MidiEvent makeEvent(int msgType, int Channel, int Note, int Velocity, int BeatOrder){
		MidiEvent event = null;
		try{
			ShortMessage a = new ShortMessage();
			a.setMessage(msgType, Channel, Note, Velocity);
			event = new MidiEvent(a, BeatOrder);
		}catch(Exception e){
			e.printStackTrace();
		}//end catch
	return event;
	}//close makeEvent() method;
	
	
	public class MyStarListener implements ActionListener{
			public void actionPerformed(ActionEvent a){
				buildTrackAndStart();
			}
	}//close inner class
	
	public class MyStopListener implements ActionListener{
		public void actionPerformed(ActionEvent a){
			sequencer.stop();
			}
		}//close inner class
		
		
	public class MyUpTempoListener implements ActionListener{
			public void actionPerformed(ActionEvent a){
				float tempoFactor = sequencer.getTempoFactor();
				//sequencer.setTempoFactor((float)(tempoFactor * 1.03));
				sequencer.setTempoFactor((float)(tempoFactor * 2));
				}
			}//close inner class
	public class MyDownTempoListener implements ActionListener{
		public void actionPerformed(ActionEvent a){
			float tempoFactor = sequencer.getTempoFactor();
			//sequencer.setTempoFactor((float)(tempoFactor * 0.97));
			sequencer.setTempoFactor((float)(tempoFactor * 0.5));
			}
		}//close inner class
	
	
	public class FixPitchListener implements ActionListener{
		public void actionPerformed(ActionEvent a){
				if (count == 1){
				fixOrNot = true;
				FixPitch.setText("Fixed");
				count =0;
				}
				else if (count == 0 ){
				fixOrNot = false;
				FixPitch.setText("Rando");
				count = 1;
			}
		}//close inner class
	}
	
	public class CancelSelectAll implements ActionListener{
		public void actionPerformed(ActionEvent a){
			for (int i = 0; i<NumOfBeats*NumOfInstrument; i++){
				c = checkboxList.get(i);
				c.setSelected(false);
				checkboxList.set(i, c);
			}	
			}
		}
			
	
	public class SelectAllListener implements ActionListener{
		public void actionPerformed(ActionEvent a){
			int d = (int)( Math.random()*NumOfBeats*NumOfInstrument);
			for (int i = 0; i<d; i++){
				int b = (int)( Math.random()*NumOfBeats*NumOfInstrument);
				//System.out.println(b);
				c = checkboxList.get(b);
				c.setSelected(true);
				checkboxList.set(b, c);
				
			}
		}
				}//close inner class
	
	
	public class SaveItlistener implements ActionListener{
		boolean[] checkBoxState = new boolean[NumOfBeats*NumOfInstrument];
		public void actionPerformed(ActionEvent e){
	
			
			for (int i =0; i < NumOfBeats*NumOfInstrument; i++){
	
				JCheckBox check=(JCheckBox) checkboxList.get(i);
				
				if (check.isSelected()){
					checkBoxState[i] = true;
				}else{
					checkBoxState[i] = false;
				}
	
			}//end for loop
			
			try{
				JFileChooser fileSave = new JFileChooser();
				fileSave.showSaveDialog(theFrame);
				saveFile(fileSave.getSelectedFile());
			}catch(Exception ex){
				ex.printStackTrace();
			}
			
	
		
		}//close method
		public void saveFile(File file){
			try{
			FileOutputStream fileoutput = new FileOutputStream(file);
			ObjectOutputStream os = new ObjectOutputStream(fileoutput);
			os.writeObject(checkBoxState);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}

	}//close SaveItlistener class
	
	public class LoadItlistener implements ActionListener{
		boolean[] checkBoxstate = null;
		public void actionPerformed(ActionEvent e){
			
			try{
				JFileChooser fileOpen = new JFileChooser();
				fileOpen.showOpenDialog(theFrame);
				loadFile(fileOpen.getSelectedFile());
				/*
				FileInputStream  fileinput = new FileInputStream(new File("CheckBox.ser"));
				ObjectInputStream is = new ObjectInputStream(fileinput);
				checkBoxstate = (boolean[]) is.readObject();
				*/
				//load directly
				
			}catch(Exception ex){
				ex.printStackTrace();
			}
			for (int i =0; i < NumOfBeats*NumOfInstrument; i++){
		
				JCheckBox check=(JCheckBox) checkboxList.get(i);
				
				if (checkBoxstate[i]){
					check.setSelected(true);
					checkboxList.set(i, check);
				}else{
					check.setSelected(false);
					checkboxList.set(i, check);
				}
			
			}//end for loop
			sequencer.stop();
			//buildTrackAndStart();
		
		}
		private void loadFile(File file){
			try{
				FileInputStream  fileinput = new FileInputStream(file);
				ObjectInputStream is = new ObjectInputStream(fileinput);
				checkBoxstate = (boolean[]) is.readObject();

			}catch(Exception ex){
				System.out.print("test");
				//ex.printStackTrace();
			}
	
		}//end loadFIle
	}//end LoadItlistener class
	
	
	//public void makeTracks(int[] List, int[] PitchList){
	
	
	
}//close class
