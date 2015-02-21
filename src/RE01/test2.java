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
public class test2 extends javax.swing.JApplet {
   
    /**
     * Initializes the applet test2
     */
    int p_bin = 270;
    double r, theta, e, phase, m, beta, gamma,v,b_f;
    double w_ =0.0;
    double m0 = 1.672621777E-27;
    double q = 1.602176565e-19;
    double evtoj = 1./1.602176565e-19;
    double b0 = 1.0;
    double w0 = q/m0;
    double c = 299792458.0;
    double mev = 1.602176565e-13;
    double[] iso = new double[p_bin];
    double[] r_x = new double[p_bin];
    double[] iso_w = new double[p_bin];
    double[] p_err = new double[p_bin];
    double[] e_r = new double[p_bin];
    double p_out = 4.0;
    
    public void set_iso(){
        for(int ix=0; ix<p_bin; ix++){
            double rx = ix/100.0;
            iso[ix] =  b0/(Math.sqrt(1-(rx*w0/c)*(rx*w0/c)));
            iso_w[ix] = b0/(Math.sqrt(1-(rx*w_*w0/c)*(rx*w_*w0/c)));
            if(ix ==0){
                p_err[ix] = iso[ix]*q*m0/(Math.sqrt(1-(rx*w0/c)*(rx*w0/c))) -iso_w[ix]*q*m0/(Math.sqrt(1-(rx*w0/c)*(rx*w0/c)));
            }
            else{
                p_err[ix] = iso[ix]*q*m0/(Math.sqrt(1-(rx*w0/c)*(rx*w0/c))) -iso_w[ix]*q*m0/(Math.sqrt(1-(rx*w0/c)*(rx*w0/c)))+p_err[ix-1];
            }
            if(Math.abs(p_err[ix]) <0.5E-46){
                p_out = rx;
            }
            r_x[ix] = ix;
            
        }
    }
    public double cal_e(double r){
        return m*w0*w0*r*r*evtoj/1E-6; 
    }
    public double cal_b(double r){
        double b_r = b0/(Math.sqrt(1-(r*w0/c)*(r*w0/c)));
        return b_r*b_f*w_;
    }
    public double cal_w(double r){
        return cal_b(r)*q*m0/(Math.sqrt(1-(r*w0/c)*(r*w0/c)));
    }
    public double del_p(double r){
        return w0-cal_w(r);
    }
    public double cal_r(double e){
        
        return Math.sqrt(e*mev/(w0*w0*m0));
        
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
            java.util.logging.Logger.getLogger(test2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(test2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(test2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(test2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the applet */
        try {
            java.awt.EventQueue.invokeAndWait(new Runnable() {
                public void run() {
                    initComponents();
                    mplot.repaint();
                    plot.repaint();
                    set_iso();
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public class Pplot extends javax.swing.JPanel{
        Pplot(){
            super();
        }
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.setColor(Color.BLACK);
            g.drawLine(50, 147, 350, 147);
            g.drawLine(50, 147, 50, 10);
            g.drawString("r", 345, 157);
            g.drawString("Bz", 35, 18);
            
            g.drawLine(50, 290, 350, 290);
            g.drawLine(50, 190, 50, 290);
            g.drawString("r", 345, 300);
     
            g.setColor(Color.RED);
            for(int x=1; x<p_bin ; x++){
                //g.drawLine(posi[x-1]+xoffset,(int)(ex[x-1]*zoom)+yoffset, posi[x]+xoffset, (int)(ex[x]*zoom)+yoffset);
                g.drawLine((int)(r_x[x-1]+50.0),(int)(-iso[x-1]*70.+180.0), (int)(r_x[x]+50.0), (int)(-iso[x]*70.0+180.0));
                //g.drawLine((int)(r_x[x-1]+50.0),(int)(iso[x-1]-157.0), (int)(r_x[x]+50.0), (int)(iso[x]-157.0));
            }
            g.setColor(Color.BLUE);
            for(int x=1; x<p_bin ; x++){
                //g.drawLine(posi[x-1]+xoffset,(int)(ex[x-1]*zoom)+yoffset, posi[x]+xoffset, (int)(ex[x]*zoom)+yoffset);
                g.drawLine((int)(r_x[x-1]+50.0),(int)(-iso_w[x-1]*70.+180.0), (int)(r_x[x]+50.0), (int)(-iso_w[x]*70.0+180.0));
                //g.drawLine((int)(r_x[x-1]+50.0),(int)(iso[x-1]-157.0), (int)(r_x[x]+50.0), (int)(iso[x]-157.0));
            }
            g.setColor(Color.RED);
            for(int x=1;x<p_bin;x++){
                g.drawLine((int)(r_x[x-1]+50.0),(int)(-p_err[x-1]*3E45+230.0), (int)(r_x[x]+50.0), (int)(-p_err[x]*3E45+230.0));
            }
            
        }
    }
            
    public class MPlot extends javax.swing.JPanel{
        MPlot(){
            super();
        }
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.setColor(Color.darkGray); 
            g.fillArc(50, 50, 300, 300, 0, 360);
            g.setColor(Color.black);
            for(int x=1;x<60;x++){
                r = cal_r(x*10)*100.0;
                g.drawArc(200-(int)r/2, 200-(int)r/2, (int)r, (int)r, 0, 360);    
            }
            g.setColor(Color.red);
            for(int x=1;x<60;x++){
                r = cal_r(x*10)*100.0;
                if (r>p_out*100.0){
                    break;
                }
                g.drawArc(200-(int)r/2, 200-(int)r/2, (int)r, (int)r, 0, 360);           
            }
            g.setColor(Color.blue);
            for(int x=1;x<60;x+=6){
                r= cal_r(x*10)*100.0;
                for (int th=0;th<30;th++){
                    theta = 2.0*Math.PI/30.0*th+ p_err[(int)(r/100.0)]*3E50;
                    
                    double xp = r*Math.cos(theta);
                    double yp = r*Math.sin(theta);
                    g.fillArc(199-(int)xp/2, 199-(int)yp/2, 4, 4, 0, 360);
                    
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        mplot = new MPlot();
        plot = new Pplot();
        bfw = new javax.swing.JSpinner();
        jButton2 = new javax.swing.JButton();

        javax.swing.GroupLayout mplotLayout = new javax.swing.GroupLayout(mplot);
        mplot.setLayout(mplotLayout);
        mplotLayout.setHorizontalGroup(
            mplotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        mplotLayout.setVerticalGroup(
            mplotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );

        plot.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout plotLayout = new javax.swing.GroupLayout(plot);
        plot.setLayout(plotLayout);
        plotLayout.setHorizontalGroup(
            plotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 457, Short.MAX_VALUE)
        );
        plotLayout.setVerticalGroup(
            plotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        bfw.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, 1.1d, 0.01d));
        bfw.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        bfw.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                bfwStateChanged(evt);
            }
        });
        bfw.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                bfwPropertyChange(evt);
            }
        });

        jButton2.setText("Exit");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(mplot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(plot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(bfw, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(59, 59, 59))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(mplot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(plot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bfw, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addContainerGap(29, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void bfwStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_bfwStateChanged
        // TODO add your handling code here:
        w_ = (double) bfw.getValue();
        set_iso();
        plot.repaint();
        mplot.repaint();
       
    }//GEN-LAST:event_bfwStateChanged

    private void bfwPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_bfwPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_bfwPropertyChange

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner bfw;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel mplot;
    private javax.swing.JPanel plot;
    // End of variables declaration//GEN-END:variables
}
