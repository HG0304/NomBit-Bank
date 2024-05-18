/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import controller.ControllerSaldo;
import model.Investidor;

/**
 *
 * @author unifhnomura
 */
public class MenuFrame extends javax.swing.JFrame {
    private ControllerSaldo control;
    private Investidor investidor;
    /**
     * Creates new form MenuFrame
     */
    public MenuFrame(Investidor investidor) {
        initComponents();
        this.investidor = investidor;
    }

    public MenuFrame() {
        initComponents();
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btConsultarExtrato = new javax.swing.JButton();
        btConsoltarSaldo = new javax.swing.JButton();
        btDepositar = new javax.swing.JButton();
        btBuyCrip = new javax.swing.JButton();
        btSacar = new javax.swing.JButton();
        btSellCrip = new javax.swing.JButton();
        btAttCrip = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btConsultarExtrato.setText("Consultar extrato");
        btConsultarExtrato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btConsultarExtratoActionPerformed(evt);
            }
        });

        btConsoltarSaldo.setText("Consultar saldo");
        btConsoltarSaldo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btConsoltarSaldoActionPerformed(evt);
            }
        });

        btDepositar.setText("Depositar");
        btDepositar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDepositarActionPerformed(evt);
            }
        });

        btBuyCrip.setText("Comprar criptos");
        btBuyCrip.setActionCommand("");
        btBuyCrip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btBuyCripActionPerformed(evt);
            }
        });

        btSacar.setText("Sacar");
        btSacar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSacarActionPerformed(evt);
            }
        });

        btSellCrip.setText("Vender crptos");
        btSellCrip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSellCripActionPerformed(evt);
            }
        });

        btAttCrip.setText("Atualizar cotação");
        btAttCrip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAttCripActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("O que deseja fazer?");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(40, Short.MAX_VALUE)
                        .addComponent(btBuyCrip, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btConsoltarSaldo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btConsultarExtrato, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE))
                            .addComponent(btDepositar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btSacar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btSellCrip, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btAttCrip, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE))))
                .addGap(41, 41, 41))
            .addGroup(layout.createSequentialGroup()
                .addGap(121, 121, 121)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btConsoltarSaldo)
                    .addComponent(btSacar))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btConsultarExtrato)
                    .addComponent(btAttCrip))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btDepositar)
                    .addComponent(btSellCrip))
                .addGap(29, 29, 29)
                .addComponent(btBuyCrip)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btConsultarExtratoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btConsultarExtratoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btConsultarExtratoActionPerformed

    private void btConsoltarSaldoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btConsoltarSaldoActionPerformed
        ExibirSaldoFrame sf = new ExibirSaldoFrame(investidor);
        sf.setVisible(true);
        //this.dispose();
    }//GEN-LAST:event_btConsoltarSaldoActionPerformed

    private void btDepositarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDepositarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btDepositarActionPerformed

    private void btBuyCripActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btBuyCripActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btBuyCripActionPerformed

    private void btSacarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSacarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btSacarActionPerformed

    private void btSellCripActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSellCripActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btSellCripActionPerformed

    private void btAttCripActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAttCripActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btAttCripActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(MenuFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(MenuFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(MenuFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(MenuFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new MenuFrame().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAttCrip;
    private javax.swing.JButton btBuyCrip;
    private javax.swing.JButton btConsoltarSaldo;
    private javax.swing.JButton btConsultarExtrato;
    private javax.swing.JButton btDepositar;
    private javax.swing.JButton btSacar;
    private javax.swing.JButton btSellCrip;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
