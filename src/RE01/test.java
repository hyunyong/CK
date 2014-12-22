/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RE01;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author hyunyong
 */
public class test extends javax.swing.JApplet {
    javax.swing.Timer timer;
    boolean isTimerOn;
    
    double n, k, ke, nstep, T, t0, spread, pulse, ddx, dt,epsilon, sigma, eaf, kstart, freq_in;
    double eps0 = 8.85419e-12;
    double c = 299792458.0;
    double pi = Math.PI;
    double abs_l1,abs_l2,abs_l3,abs_h1,abs_h2,abs_h3;
    
    int bin_num = 800;
    int xoffset = 50;
    int yoffset = 200;
    int geoStart = 250;
    
    int kc;

    int m_num = 2;
    int numlay_;
    int lambda_;
    int lambda_tmp =300;
    double zoom = 30;
    double[] ex = new double[bin_num];
    double[] hy = new double[bin_num];
    double[] intensity = new double[bin_num];
    
    double[] cb = new double[bin_num];
    double[] ca = new double[bin_num];
    double[] dis_n = new double[bin_num];
    
    double[] n_l = new double[m_num];
    double[] k_l = new double[m_num];

    int[] w_l = new int[m_num];
    int[] geo_w = new int[m_num]; 
    
    int[] posi = new int[bin_num];

    public void initFDTD(){
        
        abs_l1=0.0;abs_l2=0.0;abs_l3=0.0;abs_h1=0.0;abs_h2=0.0;abs_h3=0.0;
        ddx = 10.e-9; //set the cell size to 1 cm
        dt = ddx/(2.0*c); //cla. the time step
        lambda_ = lambda_tmp;
        freq_in = lambdaTofreq(lambda_);
        

        kc = bin_num/2;
        t0 = 40.0;
        spread = 12;
        T = 0;
        nstep = 1;
        for(int x=0; x<bin_num; x++){
            ex[x] = 0.0;
            hy[x] = 0.0;
            posi[x] = x;
            
        }
        setGeo();
    
    }
    
    public double lambdaTofreq(double lambda_){
        return c/(double)lambda_/1.e-9;
    }
    public void setGeo(){
        geo_w[0]=w_l[0];
        geo_w[1]=w_l[1];

        
        for(int x=0;x<geoStart;x++){
            ca[x] = 0.5;
            cb[x] = 0.5;
            dis_n[x] = 1.0;
        }
        int x0 = geoStart;
       
        for(int nl=0;nl<numlay_;nl++){
            if((x0+geo_w[0])>bin_num){
                break;
            }
            for(int x=x0;x<x0+geo_w[0];x++){
                ca[x] = 0.5;
                cb[x] = 0.5/((n_l[0]*n_l[0]));
                dis_n[x] = n_l[0];
            }
            if((x0+geo_w[0]+geo_w[1])>bin_num){
                break;
            }
            for(int x=x0+geo_w[0];x<x0+geo_w[0]+geo_w[1];x++){
                ca[x] = 0.5;
                cb[x] = 0.5/((n_l[1]*n_l[1]));
                dis_n[x] = n_l[1];
            }
            x0 = x0 + geo_w[0]+geo_w[1];
           /* if(x0>bin_num){
                x0 = bin_num-(geo_w[0]+geo_w[1]+1);
            }*/
        }
        for (int x =x0;x<bin_num;x++){
            ca[x] = 0.5;
            cb[x] = 0.5;
            dis_n[x] =1.0;
                    
        }
   
        
        }


    
    
    public void mainFDTD(double T){
        
        for(int x=1; x<bin_num; x++){
            ex[x] = ex[x] + ca[x]*(hy[x-1] - hy[x]);
        }
        
        //pulse = Math.exp(-0.5*(Math.pow((t0-T)/spread, 2.0)));
        pulse = Math.sin(2*pi*freq_in*dt*T);

         ex[5] =  ex[5]+pulse;
        
        //Absorbing boundary
        ex[0] = abs_l2;
        abs_l2 = abs_l1;
        abs_l1 = ex[1];
        
        ex[bin_num-1] = abs_h2;
        abs_h2 = abs_h1;
        abs_h1 = ex[bin_num-2];
       
        for(int x=0; x <bin_num-1;x++){
            hy[x] = hy[x] + cb[x]*(ex[x]-ex[x+1]);
        }
        
        for(int x=0; x<bin_num;x++){
            //intensity[x] = Math.sqrt(ex[x]*ex[x]+hy[x]*hy[x]);
            intensity[x] =   ex[x];
        }
      
    }
    /**
     * Initializes the applet test
     */
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
            java.util.logging.Logger.getLogger(test.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(test.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(test.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(test.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the applet */
        try {
            java.awt.EventQueue.invokeAndWait(new Runnable() {
                public void run() {
                    initComponents();
                    n_l[0] = 1.0;
                    n_l[1] = 1.0;
                    initFDTD();
                    
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void timerStart(){
        if(timer == null){
            timer = new javax.swing.Timer(10, new aListener());
       
        }
        timer.start();
        isTimerOn =true;
    }
    public void timerStop(){
        timer.stop();
        isTimerOn = false;
    }
    public class aListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            T=T+1;
            mainFDTD(T);
            testplot.repaint();
          
        }
    };
    
    public void setN(int m, double n_){
        n_l[m] = n_;
    }
    public void setK(int m, double k_){
        k_l[m] = k_;
    }
    public void setW(int m, int w_){
        w_l[m] = w_;
    }
    
    public class TestPlot extends javax.swing.JPanel{
        TestPlot(){
            super();
        }
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.setColor(Color.getHSBColor(45, 84, 100));
            g.fillRect(20, 20, 700, 80);
            g.setColor(Color.BLACK);
            String tmpl = "Wave length = ";
            tmpl += String.valueOf(lambda_);
            tmpl += " nm";
            g.drawString(tmpl, 50, 50);
            String tmplay = "Number of pair layer = ";
            tmplay += String.valueOf(numlay_);
            g.drawString(tmplay, 50, 80);
            
            String tmpm1n = "Marterial 1 n: ";
            tmpm1n += String.valueOf((double)((int)(n_l[0]*10.0))/10.0);
            g.drawString(tmpm1n, 300, 50);
            String tmpm1w = "Marterial 1 thinkness : ";
            tmpm1w += String.valueOf(geo_w[0]*10);
            tmpm1w += " nm";
            g.drawString(tmpm1w, 300, 80);
            
            String tmpm2n = "Marterial 2 n: ";
            tmpm2n += String.valueOf((double)((int)(n_l[1]*10.0))/10.0);
            g.drawString(tmpm2n, 500, 50);
            String tmpm2w = "Marterial 2 thinkness : ";
            tmpm2w += String.valueOf(geo_w[1]*10);
            tmpm2w += " nm";
            g.drawString(tmpm2w, 500, 80);
            String tmpo = "Optical index n";
            g.setColor(Color.orange);
            g.drawString(tmpo, 50, 300);
            g.setColor(Color.RED);
            String tmpex = "Electric filed";
            g.drawString(tmpex, 50, yoffset-70);
                  
            int x0 = geoStart+xoffset;
           
            for(int x=0;x<numlay_;x++){
                
                g.setColor(Color.GRAY);
                g.fillRect(x0, yoffset-50, geo_w[0],100);
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(x0+geo_w[0], yoffset-50, geo_w[1], 100);
                
                x0 = x0+geo_w[0]+geo_w[1];
                
            }
            g.setColor(Color.RED);
            for(int x=1; x<bin_num ; x++){
                //g.drawLine(posi[x-1]+xoffset,(int)(ex[x-1]*zoom)+yoffset, posi[x]+xoffset, (int)(ex[x]*zoom)+yoffset);
                g.drawLine(posi[x-1]+xoffset,(int)(intensity[x-1]*zoom)+yoffset, posi[x]+xoffset, (int)(intensity[x]*zoom)+yoffset);
            }
            g.setColor(Color.orange);
            for(int x=1; x<bin_num ; x++){
                g.drawLine(posi[x-1]+xoffset,(int)(-dis_n[x-1]*10.0)+330, posi[x]+xoffset, (int)(-dis_n[x]*10.0)+330);
                
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
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        testplot = new TestPlot();
        start_b = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        m1_n = new javax.swing.JSpinner();
        m2_n = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        numlay_s = new javax.swing.JSlider();
        numlay = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        lamba_s = new javax.swing.JSlider();
        lambda_spinor = new javax.swing.JSpinner();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        set_b = new javax.swing.JButton();
        exit_b = new javax.swing.JButton();
        m1_w = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        m2_w = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(617, 518));
        setMinimumSize(new java.awt.Dimension(617, 518));

        testplot.setBackground(new java.awt.Color(255, 255, 255));
        testplot.setMaximumSize(new java.awt.Dimension(593, 330));
        testplot.setMinimumSize(new java.awt.Dimension(593, 330));
        testplot.setPreferredSize(new java.awt.Dimension(593, 330));

        javax.swing.GroupLayout testplotLayout = new javax.swing.GroupLayout(testplot);
        testplot.setLayout(testplotLayout);
        testplotLayout.setHorizontalGroup(
            testplotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        testplotLayout.setVerticalGroup(
            testplotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 330, Short.MAX_VALUE)
        );

        start_b.setText("Start");
        start_b.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                start_bActionPerformed(evt);
            }
        });

        jLabel1.setText("Material 1 n");

        m1_n.setModel(new javax.swing.SpinnerNumberModel(1.0d, 1.0d, 5.0d, 0.09999999999999998d));
        m1_n.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                m1_nStateChanged(evt);
            }
        });

        m2_n.setModel(new javax.swing.SpinnerNumberModel(1.0d, 1.0d, 5.0d, 0.09999999999999998d));
        m2_n.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                m2_nStateChanged(evt);
            }
        });

        jLabel3.setText("Material 2 n");

        jLabel5.setText("Number of layers");

        numlay_s.setMaximum(10);

        numlay.setModel(new javax.swing.SpinnerNumberModel(0, 0, 10, 1));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, numlay_s, org.jdesktop.beansbinding.ELProperty.create("${value}"), numlay, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        numlay.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                numlayStateChanged(evt);
            }
        });

        jLabel6.setText("Wave length [nm]");

        lamba_s.setMaximum(800);
        lamba_s.setMinimum(300);

        lambda_spinor.setModel(new javax.swing.SpinnerNumberModel(300, 300, 800, 1));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, lamba_s, org.jdesktop.beansbinding.ELProperty.create("${value}"), lambda_spinor, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        lambda_spinor.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                lambda_spinorStateChanged(evt);
            }
        });

        set_b.setText("Set");
        set_b.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                set_bActionPerformed(evt);
            }
        });

        exit_b.setText("Exit");
        exit_b.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exit_bActionPerformed(evt);
            }
        });

        m1_w.setModel(new javax.swing.SpinnerNumberModel(0, 0, 1000, 10));
        m1_w.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                m1_wStateChanged(evt);
            }
        });

        jLabel7.setText("Material 1 thikness [nm]");

        m2_w.setModel(new javax.swing.SpinnerNumberModel(0, 0, 1000, 10));
        m2_w.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                m2_wStateChanged(evt);
            }
        });

        jLabel8.setText("Matiral 2 thickness [nm]");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(testplot, javax.swing.GroupLayout.DEFAULT_SIZE, 746, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(m2_w)
                            .addComponent(m1_w)
                            .addComponent(m1_n)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(m2_n)))
                        .addGap(47, 47, 47)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lamba_s, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lambda_spinor, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel5)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(numlay_s, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(numlay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel6))
                        .addGap(80, 80, 80)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(set_b, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(start_b, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(exit_b, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(161, 161, 161))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(testplot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(m1_n, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(m1_w, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(m2_n, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(m2_w, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(numlay_s, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(numlay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lamba_s, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lambda_spinor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 62, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(set_b)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(start_b)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(exit_b)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void start_bActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_start_bActionPerformed
        // TODO add your handling code here:
        
        initFDTD();
        timerStart();
    }//GEN-LAST:event_start_bActionPerformed

    private void m1_nStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_m1_nStateChanged
        // TODO add your handling code here:
        double n_ = (double) m1_n.getValue();
        setN(0,n_);
        
    }//GEN-LAST:event_m1_nStateChanged

    private void exit_bActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exit_bActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_exit_bActionPerformed

    private void m1_wStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_m1_wStateChanged
        // TODO add your handling code here:
        int w_ = (int) m1_w.getValue();
        setW(0, w_/10);
    }//GEN-LAST:event_m1_wStateChanged

    private void m2_nStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_m2_nStateChanged
        // TODO add your handling code here:
        double n_ = (double) m2_n.getValue();
        setN(1,n_);
    }//GEN-LAST:event_m2_nStateChanged

    private void m2_wStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_m2_wStateChanged
        // TODO add your handling code here:
        int w_ = (int) m2_w.getValue();
        setW(1, w_/10);
    }//GEN-LAST:event_m2_wStateChanged

    private void numlayStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_numlayStateChanged
        // TODO add your handling code here:
        numlay_ = (int) numlay.getValue();
    }//GEN-LAST:event_numlayStateChanged

    private void lambda_spinorStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_lambda_spinorStateChanged
        // TODO add your handling code here:
        lambda_tmp = (int)lambda_spinor.getValue();
    }//GEN-LAST:event_lambda_spinorStateChanged

    private void set_bActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_set_bActionPerformed
        // TODO add your handling code here:
        setGeo();
        testplot.repaint();
    }//GEN-LAST:event_set_bActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton exit_b;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JSlider lamba_s;
    private javax.swing.JSpinner lambda_spinor;
    private javax.swing.JSpinner m1_n;
    private javax.swing.JSpinner m1_w;
    private javax.swing.JSpinner m2_n;
    private javax.swing.JSpinner m2_w;
    private javax.swing.JSpinner numlay;
    private javax.swing.JSlider numlay_s;
    private javax.swing.JButton set_b;
    private javax.swing.JButton start_b;
    private javax.swing.JPanel testplot;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
