/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RE01;

import java.awt.Color;
import java.awt.Graphics;
/**
 *
 * @author hyunyong
 */
public class re01 extends javax.swing.JApplet {
    double mass, kenergy,beta, gamma;
    int bin_num = 100;
    int[] x_val = new int[bin_num];
    int[] y_val = new int[bin_num];



   
    
    public void setMass(double mass_){
      mass = mass_;
    }
    public double getMass(){
      return mass;
    }
    public double getKenergy(){
      return kenergy;
    }
    public void setKenergy(double kenergy_){
      kenergy = kenergy_;
    }
    public void calGamma(double mass, double kenergy){
      gamma =  1.0 + kenergy/mass;
    }
    public void calBeta(){
       beta = Math.sqrt(1.0-1.0/(gamma*gamma));
    }
    public double getGamma(){
      return gamma;
    }
    public double getBeta(){
      return beta;
    }
    
    public void iniArr(){
      for(int x=0;x<bin_num;x++){
        x_val[x] = x;
        y_val[x] = 0;
      }
    }
    
    /**
     * Initializes the applet re01
     */
    
    public void init() {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(re01.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(re01.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(re01.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(re01.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the applet */
        try {
            java.awt.EventQueue.invokeAndWait(new Runnable() {
                public void run() {
                    initComponents();
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public class Plotbeta extends javax.swing.JPanel{
    }
    public class Plotgamma extends javax.swing.JPanel{
    }
    public class PlotPan extends javax.swing.JPanel{                
        private int radius;
        PlotPan(){
            super();
            radius = 0 ;
        }
        
        public void paintComponent(Graphics g)
        {   
            calGamma(getMass(), getKenergy());
            calBeta();
            
            double L0 = 500.0;
            double L = L0/getGamma();
            double hight = 10.0;
            int pL = (int)(L);
            super.paintComponent(g);
            g.setColor(Color.blue);
            //System.out.format(String.valueOf(pL));
            g.fillRect(100,50,(int)L0,30);
            g.setColor(Color.red);
            g.fillRect(100,50,pL,30);
            g.setColor(Color.black);
            String tmpm = "";
            tmpm += "The E0(mass): ";
            tmpm += String.valueOf(getMass());
            tmpm += " MeV";
            g.drawString(tmpm, 100, 110);
            String tmpk = "";
            tmpk += "The kinetic energy : ";
            tmpk += String.valueOf(getKenergy());
            tmpk += " MeV";
            g.drawString(tmpk, 100, 130);
            String tmps = "";
            tmps += "The Object speed : ";
            tmps += String.valueOf(getBeta());
            tmps += " C";
            g.drawString(tmps, 100, 150);
            String tmpl = "";
            tmpl += "Length contraction : L = ";
            tmpl += String.valueOf(1.0/getGamma());
            tmpl += " L0";
            g.drawString(tmpl, 100, 170);
            String tmpt = "";
            tmpt += "Time dilation : T = ";
            tmpt += String.valueOf(getGamma());
            tmpt += " T0";
            g.drawString(tmpt, 100, 190);
            
            
            
            
        
           
        }
    }
    /**
     * This method is called from within the init() method to initialize the
     * form. WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        plot = new PlotPan();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        betaPlot = new Plotgamma();
        gammaPlot = new Plotbeta();

        plot.setPreferredSize(new java.awt.Dimension(700, 0));

        javax.swing.GroupLayout plotLayout = new javax.swing.GroupLayout(plot);
        plot.setLayout(plotLayout);
        plotLayout.setHorizontalGroup(
            plotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        plotLayout.setVerticalGroup(
            plotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 235, Short.MAX_VALUE)
        );

        jButton1.setText("Cal.");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextField1.setText("Mass in MeV");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jTextField2.setText("Kenergy in MeV");
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout betaPlotLayout = new javax.swing.GroupLayout(betaPlot);
        betaPlot.setLayout(betaPlotLayout);
        betaPlotLayout.setHorizontalGroup(
            betaPlotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        betaPlotLayout.setVerticalGroup(
            betaPlotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout gammaPlotLayout = new javax.swing.GroupLayout(gammaPlot);
        gammaPlot.setLayout(gammaPlotLayout);
        gammaPlotLayout.setHorizontalGroup(
            gammaPlotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 401, Short.MAX_VALUE)
        );
        gammaPlotLayout.setVerticalGroup(
            gammaPlotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(betaPlot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(gammaPlot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(plot, javax.swing.GroupLayout.DEFAULT_SIZE, 813, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(plot, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(betaPlot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(gammaPlot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        plot.repaint();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
        double mass_ = Double.valueOf(evt.getActionCommand());
        
        setMass(mass_);
        System.out.format(String.valueOf(getMass()));
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
        double kenergy_ = Double.valueOf(evt.getActionCommand());
        
        setKenergy(kenergy_);
        System.out.format(String.valueOf(getKenergy()));
    }//GEN-LAST:event_jTextField2ActionPerformed
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel betaPlot;
    private javax.swing.JPanel gammaPlot;
    private javax.swing.JButton jButton1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JPanel plot;
    // End of variables declaration//GEN-END:variables
}
