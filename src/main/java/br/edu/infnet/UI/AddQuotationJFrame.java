/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.infnet.UI;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.mysql.cj.util.StringUtils;

import br.edu.infnet.appservices.ProductAppService;
import br.edu.infnet.appservices.QuotationAppService;
import br.edu.infnet.database.ProductRepository;
import br.edu.infnet.database.QuotationRepository;
import br.edu.infnet.models.Product;
import br.edu.infnet.models.Quotation;

/**
 *
 * @author 55229
 */
public class AddQuotationJFrame extends javax.swing.JFrame {

    /**
     * Creates new form AddQuotationJFrame
     */
    Quotation _quotation;
    private javax.swing.JButton jButton1;
    private java.awt.Choice _choice1;
    private javax.swing.JTextField _jTextField1;
    private ProductAppService _productAppService;
    private QuotationAppService _quotationAppService;
    private List<Product> _productList = new ArrayList<Product>();

    public AddQuotationJFrame() {

        try {
            ProductRepository productRepository = ProductRepository.getInstance();
            QuotationRepository quotationRepository = QuotationRepository.getInstance();
            productRepository.init();
            quotationRepository.init();
            ProductAppService productAppService = new ProductAppService(productRepository);
            _productAppService = productAppService;
            QuotationAppService quotationAppService = new QuotationAppService(quotationRepository);
            _quotationAppService = quotationAppService;
            _productList = _productAppService.GetAll();

            jButton1.addActionListener(x -> {

            });
        } catch (Exception e) {
            // TODO: handle exception
        }
        initComponents();

    }

    public AddQuotationJFrame(Quotation quotation) {

        try {
            ProductRepository productRepository = ProductRepository.getInstance();
            QuotationRepository quotationRepository = QuotationRepository.getInstance();
            productRepository.init();
            quotationRepository.init();
            ProductAppService productAppService = new ProductAppService(productRepository);
            _productAppService = productAppService;
            QuotationAppService quotationAppService = new QuotationAppService(quotationRepository);
            _quotationAppService = quotationAppService;
            _productList = _productAppService.GetAll();
            _quotation = quotation;

        } catch (Exception e) {
            // TODO: handle exception
        }
        initComponents();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        java.awt.Choice choice1 = new java.awt.Choice();
        _productList.forEach(x -> choice1.add(x.GetName()));
        _choice1 = choice1;

        javax.swing.JTextField jTextField1 = new javax.swing.JTextField("", 15);
        _jTextField1 = jTextField1;
        javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel3 = new javax.swing.JLabel();
        javax.swing.JButton jButton1 = new javax.swing.JButton();
        javax.swing.JButton jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Amount");

        jLabel3.setText("Product");

        jButton1.setText("Add");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cancel");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup().addGap(79, 79, 79).addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(
                                                choice1, javax.swing.GroupLayout.PREFERRED_SIZE, 142,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup().addGap(112, 112, 112).addGroup(layout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup().addComponent(jLabel1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup().addComponent(jButton1)
                                                .addGap(18, 18, 18).addComponent(jButton2)))))
                        .addContainerGap(124, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup().addGap(44, 44, 44)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(choice1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1))
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton1).addComponent(jButton2))
                        .addContainerGap(133, Short.MAX_VALUE)));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        var productName = _choice1.getItem(_choice1.getSelectedIndex());
        var productAmount = StringUtils.isNullOrEmpty(_jTextField1.getText()) ? 0
                : Integer.parseInt(_jTextField1.getText());
        Product selectedProduct = _productList.stream().filter(y -> y.GetName().equalsIgnoreCase(productName))
                .findFirst().orElse(null);

        var quotationPrice = productAmount * selectedProduct.GetPrice();

        if (productName == "" || productAmount == 0 || quotationPrice == 0.0)
            JOptionPane.showMessageDialog(new JFrame(), "All the fields are required", "Dialog",
                    JOptionPane.ERROR_MESSAGE);
        else if (_quotation == null)
            _quotationAppService.Add(new Quotation(selectedProduct, productAmount));
        else
        {
            _quotation.SetAmount(productAmount);
            _quotation.SetProduct(selectedProduct);
            _quotation.SetTotal();
            _quotationAppService.Edit(_quotation);
        }
            
        AddQuotationJFrame.super.dispose();
        new MenuJFrame().setVisible(true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AddQuotationJFrame.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddQuotationJFrame.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddQuotationJFrame.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddQuotationJFrame.class.getName()).log(java.util.logging.Level.SEVERE,
                    null, ex);
        }
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddQuotationJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
