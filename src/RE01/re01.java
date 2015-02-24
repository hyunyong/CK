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
    
    int mpx=0;
    int mpy=0;
    int bin_num = 400;
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
            g.drawString("L0", (int)L0+100, 100);
            g.setColor(Color.red);
            g.fillRect(100,50,pL,30);
            g.drawString("L'", pL+100, 40);
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
            
            g.setColor(Color.BLACK);
            g.drawLine(50, 550, 750, 550);
            g.drawString("Energy [MeV]", 690, 570);
            g.drawLine(50, 550, 50, 300);
            g.drawString("beta",20, 330);
            g.setColor(Color.BLUE);
            int e = 0;
            double p_beta = 0.0;
            double b_beta = 0.0;
           
            while(true){
                calGamma(mass, e);
                calBeta();
                if(getBeta()>0.99){
                    break;
                }
                e += 1;
            }
            int log_t = 0;
            int gx0 = 50;
            int gy0 = 550;
            int xbin = 700;
            int ybin = 250;
            e = (int)(e*1.1);
            if(e<10){
                e = e*100;
                log_t = 1;
            }
 
            
            int[] gx_p = new int[e];
            int[] gy_p = new int[e];
            double[] gbeta = new double[e];
            double[] ge = new double[e];
            for(int x=0;x<e;x++){
                double ke = 0.0;
                double w_e = 0.0;
                if(log_t ==1){
                    ke = x/100.0;
                    w_e = e/100.0;
                }else{
                    ke = x;
                    w_e= e;
                }
                calGamma(mass,ke);
                calBeta();
                
                gx_p[x] = (int)(gx0+ke*xbin/w_e);
                gy_p[x] = (int)(gy0-getBeta()*ybin);
                ge[x] = ke;
                gbeta[x] = getBeta();
                
            }
            for(int x=1;x<e;x++){
                g.drawLine(gx_p[x-1], gy_p[x-1], gx_p[x], gy_p[x]);
            }
            if(mpy>100){
                double dr = 1000.0;
                int index = -1;
                for(int x=0;x<e;x++){
                    double dx = Math.abs(gx_p[x]-mpx);
                    double dy = Math.abs(gy_p[x]-mpy);
                    double tmp = Math.sqrt(dx*dx+dy*dy);
                    if(tmp<dr){
                        dr = tmp;
                        index = x;
                    }
                }
                g.setColor(Color.BLACK);
                if(dr<3){
                    String tmpgbeta = "";
                    tmpgbeta += "Beta : ";
                    tmpgbeta += String.valueOf(gbeta[index]);
                    g.drawString(tmpgbeta, gx_p[index]+10, gy_p[index]-30);
                    String tmpgke = "";
                    tmpgke += "Kinetic energy : ";
                    tmpgke += String.valueOf(ge[index]);
                    g.drawString(tmpgke, gx_p[index]+10, gy_p[index]-20);
                }
            }
           
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
        jButton2 = new javax.swing.JButton();

        plot.setPreferredSize(new java.awt.Dimension(700, 0));
        plot.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                plotMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout plotLayout = new javax.swing.GroupLayout(plot);
        plot.setLayout(plotLayout);
        plotLayout.setHorizontalGroup(
            plotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 810, Short.MAX_VALUE)
        );
        plotLayout.setVerticalGroup(
            plotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 603, Short.MAX_VALUE)
        );

        jButton1.setText("Calculation");
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

        jButton2.setText("Exit");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(plot, javax.swing.GroupLayout.DEFAULT_SIZE, 810, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField1)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(97, 97, 97)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(plot, javax.swing.GroupLayout.PREFERRED_SIZE, 603, javax.swing.GroupLayout.PREFERRED_SIZE)))
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

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void plotMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_plotMouseClicked
        // TODO add your handling code here:
        mpx = evt.getX();
        mpy = evt.getY();
        plot.repaint();
    }//GEN-LAST:event_plotMouseClicked
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JPanel plot;
    // End of variables declaration//GEN-END:variables
}
