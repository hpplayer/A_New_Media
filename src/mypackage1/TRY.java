/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mypackage1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author tingchen
 */
public class TRY {
  
    public TRY(){}
    
     private void initComponents(JFrame thisFrame) {

     
        
        JButton Stop = new JButton();
        JButton Start = new JButton();
        JButton TempoUp = new JButton();
        JButton TempoDown = new JButton();
        

        thisFrame.setDefaultCloseOperation(thisFrame.EXIT_ON_CLOSE);
        thisFrame.setLocation(new java.awt.Point(2000, 1000));

        Stop.setText("Stop");
        Stop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                StopMouseClicked(evt);
            }
        });

        Start.setText("Start");
        Start.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                StartMouseClicked(evt);
            }
        });

        TempoUp.setText("Up");
        TempoUp.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                TempoUpMouseClicked(evt);
            }
        });

        TempoDown.setText("Down");
        TempoDown.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                TempoDownMouseClicked(evt);
            }
        });
        TempoDown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                TempoDownActionPerformed(evt);
            }
        });

        GroupLayout layout = new GroupLayout(thisFrame.getContentPane());
        thisFrame.getContentPane().setLayout(layout);
        
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Start, GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        thisFrame.pack();
        thisFrame.setVisible(true);
    }// </editor-fold>                        

    private static void TempoDownActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
        
        
    }                                         

    private static void StartMouseClicked(java.awt.event.MouseEvent evt) {                                   
        // TODO add your handling code here:
         //buildTrackAndStart();
        //sequencer.start();
    }                                  

    private void StopMouseClicked(java.awt.event.MouseEvent evt) {                                  
        // TODO add your handling code here:
        //sequencer.stop();
        //display.frame.setVisible(false);
        //this.setVisible(false);
        //theFrame.setVisible(true);
       
    }                                 

    private void TempoUpMouseClicked(java.awt.event.MouseEvent evt) {                                     
        // TODO add your handling code here:
      //  float tempoFactor = sequencer.getTempoFactor();
      //  sequencer.setTempoFactor((float) (tempoFactor * 2));
    }                                    

    private void TempoDownMouseClicked(java.awt.event.MouseEvent evt) {                                       
        // TODO add your handling code here:
      //  float tempoFactor = sequencer.getTempoFactor();
      //  sequencer.setTempoFactor((float) (tempoFactor * 0.5));
    }     
    
    public static void main(String[] args) {
        TRY test = new TRY();
        JFrame testF = new JFrame();
        test.initComponents(testF);
    }
}
