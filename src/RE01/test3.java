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
 * @author hyun
 */
public class test3 extends javax.swing.JApplet {

    /**
     * Initializes the applet test3
     */
    int xbin = 570;
    int ybin = 570;    
    int mx = 0;
    int my = 0;
    int xcen = xbin/2;
    int ycen = ybin/2;
    int mpx = 0;
    int mpy = 0;
    double k = 0.5;
    double r0 = 50.0;
    
    double[] bx = new double[xbin*ybin];
    double[] by = new double[xbin*ybin];
    int[] pole_tip_xp = new int[32]; 
    int[] pole_tip_yp = new int[32];
    
    public void fill_b(){ 
        for(int x=0;x<xbin*ybin;x++){
          int xp = x%xbin - xcen;
          int yp = x/xbin - ycen;
          bx[x] = k*yp;
          by[x] = k*xp;
      }
    }
    
    public void pole_tip_geo(){
        double dx = 3.0;
        double ini_x = 0.0;
        double ini_y = 0.0;
        double r0 = 100.0;
        //pole_tip_xp[0] = (int)ini_x;
        //#pole_tip_yp[0] = (int)ini_y;
        for(int x=0;x<31;x++){
            double pxp = r0/Math.sqrt(2.0) + x*dx;
            double pyp = r0*r0/2./pxp;
            pole_tip_xp[x] = (int)(pxp + ini_x);
            pole_tip_yp[x] = (int)(pyp + ini_y);
            
        }
        pole_tip_xp[31] = pole_tip_xp[30];
        pole_tip_yp[31] = pole_tip_yp[0];
    }
  
    
    @Override
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
            java.util.logging.Logger.getLogger(test3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(test3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(test3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(test3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the applet */
        try {
            java.awt.EventQueue.invokeAndWait(new Runnable() {
                public void run() {
                    initComponents();
                    fill_b();
                    pole_tip_geo();
                    plot.repaint();
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public class MPlot extends javax.swing.JPanel{
        MPlot(){
            super();
        }
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.setColor(Color.GRAY);
            g.drawLine(xcen, 0, xcen, ybin);
            g.drawLine(0, ycen, xbin, ycen);
            for(int rp=50;rp<500;rp+=15){
                for(int th=0;th<4;th++){
                    for(int x=1;x<xbin;x+=1){
                    double hxp = x;
                    double hxpn = x+1;
                    double hyp = rp*rp/(x);
                    double hypn = rp*rp/(x+1);
                    double hxpp = hxp*Math.cos(Math.PI*th/2.0) - hyp*Math.sin(Math.PI*th/2.0);
                    double hypp = hxp*Math.sin(Math.PI*th/2.0) + hyp*Math.cos(Math.PI*th/2.);
                    double hxpnp = hxpn*Math.cos(Math.PI*th/2.0) - hypn*Math.sin(Math.PI*th/2.0);
                    double hypnp = hxpn*Math.sin(Math.PI*th/2.0) + hypn*Math.cos(Math.PI*th/2.0);
                    //g.drawLine((int)hxp, (int)hyp, (int)hxpn, (int)hypn);
                    g.drawLine((int)hxpp+xcen, (int)hypp+ycen, (int)hxpnp+xcen, (int)hypnp+ycen);
                    }
                    
                }
            }
            
            g.setColor(Color.blue);
            //g.fillPolygon(pole_tip_xp, pole_tip_yp, 32);
            g.drawArc(mpx-2, mpy-2, 4, 4, 0, 360);
            int ap = mpx+mpy*xbin;
            g.drawLine(mpx-1, mpy-1, mpx-1+(int)bx[ap], mpy-1+(int)by[ap]);
            g.setColor(Color.red);
            double xpp = bx[ap]*Math.cos(Math.PI/2.0) - by[ap]*Math.sin(Math.PI/2.0);
            double ypp = bx[ap]*Math.sin(Math.PI/2.0) + by[ap]*Math.cos(Math.PI/2.0);
            g.drawLine(mpx-1, mpy-1, mpx-1+(int)xpp, mpy-1+ (int)ypp);
            g.setColor(Color.WHITE);
            g.fillRect(400, 10, 550, 70);
            g.setColor(Color.gray);
            g.drawLine(410, 20, 450, 20);
            g.drawString("Scalar equipotentials", 460, 25);
            g.setColor(Color.BLUE);
            g.drawLine(410, 40, 450, 40);
            g.drawString("Magnetic flux vector", 460, 45);
            g.setColor(Color.red);
            g.drawLine(410,60,450,60);
            g.drawString("Magnetic force vetcor", 460, 65);
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

        plot = new MPlot();
        jButton1 = new javax.swing.JButton();

        plot.setBackground(new java.awt.Color(255, 255, 255));
        plot.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                plotMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout plotLayout = new javax.swing.GroupLayout(plot);
        plot.setLayout(plotLayout);
        plotLayout.setHorizontalGroup(
            plotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 582, Short.MAX_VALUE)
        );
        plotLayout.setVerticalGroup(
            plotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 570, Short.MAX_VALUE)
        );

        jButton1.setText("Exit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(plot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(30, 30, 30))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(plot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void plotMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_plotMouseClicked
        // TODO add your handling code here:
        mpx = evt.getX();
        mpy = evt.getY();
        mx = mpx - xcen;
        my = mpy + ybin - ycen;
        plot.repaint();
        //System.out.println(mx-xcen);
        //System.out.println(ybin-my-ycen);
    }//GEN-LAST:event_plotMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel plot;
    // End of variables declaration//GEN-END:variables
}
