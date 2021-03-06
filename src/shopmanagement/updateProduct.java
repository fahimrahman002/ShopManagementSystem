/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shopmanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Fahim
 */
public class updateProduct extends javax.swing.JDialog {

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String currency;
    String user;
    String productId = null;
    String initialProductId;

    /** Creates new form updateProduct */
    public updateProduct(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        conn = JConnection.connectdb();
        this.user = "admin";
        setCurrencyValues();
        setInitialValues();

    }

    public updateProduct(java.awt.Frame parent, boolean modal, String user, String productId) {
        super(parent, modal);
        conn = JConnection.connectdb();
        initComponents();
        updateButton.setVisible(true);
        updateButton1.setVisible(false);
        this.user = user;
        this.productId = productId;
        productNameField.setFocusable(true);
        productIdField.setFocusable(false);
        setCurrencyValues();
        setInitialValues();
    }

    public void setInitialValues() {
        String sql = "select * from  products where user=" + "\'" + user + "\'" + " and product_id=" + "\'" + productId + "\'";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                initialProductId = rs.getString(2);
                productIdField.setText(initialProductId);
                productNameField.setText(rs.getString(3));
                setSelectedValue(typeComboBox, rs.getString(4));
                purchaseRateField.setText(Double.toString(rs.getDouble(5)));
                salesRateField.setText(Double.toString(rs.getDouble(6)));
                vatField.setText(Integer.toString(rs.getInt(7)));
                quantityField.setText(Integer.toString(rs.getInt(8)));

            }

        } catch (SQLException e) {
            final JPanel panel = new JPanel();
            JOptionPane.showMessageDialog(panel, "Can't fetch initial data from database.", "Warning",
                    JOptionPane.WARNING_MESSAGE);

        }

    }

    public void setSelectedValue(JComboBox comboBox, String match) {
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            Object item = comboBox.getItemAt(i);
            if (item.equals(match)) {
                comboBox.setSelectedIndex(i);
                break;
            }
        }
    }

    public void setCurrencyValues() {
        this.currency = getCurrency();
        if (currency.equals("$")) {
            currency = "\u09F3";
        } else if (currency.equals("৳")) {
            currency = "tk ";
        } else if (currency.equals("INR")) {
            currency = "\u20B9";
        } else if (currency.equals("¥")) {
            currency = "\u00A5";
        } else if (currency.equals("€")) {
            currency = "\u20AC";
        } else if (currency.equals("£")) {
            currency = "\uFFE1";
        } else if (currency.equals("Rs")) {
            currency = "\u20A8";
        }
    }

    public String getCurrency() {
        String sql = "select currency from users where user=" + "\'" + user + "\'";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            if (rs.next()) {
                return rs.getString("currency");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        productNameField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        productIdField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        salesRateField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        quantityField = new javax.swing.JTextField();
        quantityLabel = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        vatField = new javax.swing.JTextField();
        typeComboBox = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        typeLabel1 = new javax.swing.JLabel();
        typeLabel2 = new javax.swing.JLabel();
        updateButton = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        purchaseRateField = new javax.swing.JTextField();
        updateButton1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setText("Product Name :");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(146, 110, 150, 30));

        productNameField.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jPanel1.add(productNameField, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 110, 270, 30));

        jLabel2.setFont(new java.awt.Font("DialogInput", 1, 30)); // NOI18N
        jLabel2.setText("Update Product");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 10, 280, 40));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel3.setText("Purchase Rate :");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(146, 210, 160, 30));

        productIdField.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        productIdField.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        productIdField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                productIdFieldMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                productIdFieldMouseEntered(evt);
            }
        });
        productIdField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                productIdFieldKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                productIdFieldKeyTyped(evt);
            }
        });
        jPanel1.add(productIdField, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 70, 100, 30));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel4.setText("Sales Rate :");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(183, 260, 140, 30));

        salesRateField.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jPanel1.add(salesRateField, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 255, 100, 30));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel5.setText("Item Type :");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(186, 160, 120, 30));

        jLabel6.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel6.setText("Quantity :");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 332, 90, 40));

        quantityField.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        quantityField.setText("1");
        jPanel1.add(quantityField, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 340, 60, -1));

        quantityLabel.setFont(new java.awt.Font("Arial", 1, 17)); // NOI18N
        quantityLabel.setText(" item");
        jPanel1.add(quantityLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 340, 50, 30));

        jLabel8.setFont(new java.awt.Font("Curlz MT", 1, 24)); // NOI18N
        jLabel8.setText("%");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 300, 24, 25));

        vatField.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        vatField.setText("0");
        jPanel1.add(vatField, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 300, 34, -1));

        typeComboBox.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        typeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Product", "Liquid", "Grocery", "Meat", "Fish" }));
        typeComboBox.addHierarchyListener(new java.awt.event.HierarchyListener() {
            public void hierarchyChanged(java.awt.event.HierarchyEvent evt) {
                typeComboBoxHierarchyChanged(evt);
            }
        });
        typeComboBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                typeComboBoxMouseClicked(evt);
            }
        });
        typeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeComboBoxActionPerformed(evt);
            }
        });
        typeComboBox.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                typeComboBoxPropertyChange(evt);
            }
        });
        jPanel1.add(typeComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 160, 100, -1));

        jLabel11.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel11.setText("Vat : ");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 300, 60, 30));

        typeLabel1.setFont(new java.awt.Font("Arial", 1, 17)); // NOI18N
        typeLabel1.setText("/ item");
        jPanel1.add(typeLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 210, 90, 30));

        typeLabel2.setFont(new java.awt.Font("Arial", 1, 17)); // NOI18N
        typeLabel2.setText("/ item");
        jPanel1.add(typeLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 260, 90, 30));

        updateButton.setBackground(new java.awt.Color(51, 255, 0));
        updateButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/update2.jpg"))); // NOI18N
        updateButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                updateButtonMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                updateButtonMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                updateButtonMouseReleased(evt);
            }
        });
        jPanel1.add(updateButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(252, 418, 200, 60));

        jLabel12.setFont(new java.awt.Font("Arial", 1, 19)); // NOI18N
        jLabel12.setText("Product Id :");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 70, 110, 30));

        purchaseRateField.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        purchaseRateField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                purchaseRateFieldMouseClicked(evt);
            }
        });
        purchaseRateField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                purchaseRateFieldKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                purchaseRateFieldKeyTyped(evt);
            }
        });
        jPanel1.add(purchaseRateField, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 210, 100, 30));

        updateButton1.setBackground(new java.awt.Color(51, 255, 0));
        updateButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/update2.jpg"))); // NOI18N
        updateButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                updateButton1MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                updateButton1MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                updateButton1MouseReleased(evt);
            }
        });
        jPanel1.add(updateButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 420, 200, 60));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 723, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 723, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 542, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void productIdFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_productIdFieldMouseClicked

    }//GEN-LAST:event_productIdFieldMouseClicked

    private void productIdFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_productIdFieldKeyPressed

    }//GEN-LAST:event_productIdFieldKeyPressed

    private void productIdFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_productIdFieldKeyTyped

    }//GEN-LAST:event_productIdFieldKeyTyped

    private void typeComboBoxHierarchyChanged(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_typeComboBoxHierarchyChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_typeComboBoxHierarchyChanged

    private void typeComboBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_typeComboBoxMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_typeComboBoxMouseClicked

    private void typeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeComboBoxActionPerformed

        if (typeComboBox.getSelectedItem() == "Liquid") {
            quantityLabel.setText("Liter");
            typeLabel1.setText(currency + "/liter");
            typeLabel2.setText(currency + "/liter");
        } else if (typeComboBox.getSelectedItem() == "Grocery" || typeComboBox.getSelectedItem() == "Meat" || typeComboBox.getSelectedItem() == "Fish") {
            quantityLabel.setText("KG");
            typeLabel1.setText(currency + "/kg");
            typeLabel2.setText(currency + "/kg");
        } else {
            quantityLabel.setText("Item");
            typeLabel1.setText(currency + "/item");
            typeLabel2.setText(currency + "/item");
        }
    }//GEN-LAST:event_typeComboBoxActionPerformed

    private void updateButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateButtonMouseClicked
        String productId = null;
        String productName = null;
        String type = null;
        Double purchaseRate = null;
        Double salesRate = null;
        int vat = 0;
        int stock = 0;
        boolean error = false;

        try {
            productId = productIdField.getText();
            productName = productNameField.getText();
            type = (String) typeComboBox.getSelectedItem();
            purchaseRate = Double.parseDouble(purchaseRateField.getText());
            salesRate = Double.parseDouble(salesRateField.getText());
            vat = Integer.parseInt(vatField.getText());
            stock = Integer.parseInt(quantityField.getText());
            if (productId.equals("") || productName.equals("")) {
                throw new Exception();
            }
        } catch (Exception e) {
            final JPanel panel = new JPanel();
            JOptionPane.showMessageDialog(panel, "Wrong Input.\nPlease make sure you have given right text and right number in specific field.", "Warning", JOptionPane.WARNING_MESSAGE);
            error = true;
        }

        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        java.sql.Time sqlTime = new java.sql.Time(date.getTime());

        String sql = "UPDATE products SET product_id=" + "\'" + productId + "\'" + ",product_name=" + "\'" + productName + "\'" + ",type="
                + "\'" + type + "\'" + ",purchase_rate=" + "\'" + purchaseRate + "\'" + ",sales_rate=" + "\'" + salesRate + "\'" + ",vat="
                + "\'" + vat + "\'" + ",stock=" + "\'" + stock + "\'" + ",last_update=" + "\'" + sqlDate + "\'"
                + ",time=" + "\'" + sqlTime + "\'" + "where user=" + "\'" + user + "\' and product_id=" + "\'" + initialProductId + "\'";
        if (!error) {
            final JPanel panel = new JPanel();
            try {
                pst = conn.prepareStatement(sql);
                pst.execute();
                JFrame frame = new JFrame();
                JOptionPane.showMessageDialog(frame, "Product details updated.");
                this.dispose();

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(panel, "Database error.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }


    }//GEN-LAST:event_updateButtonMouseClicked

    private void updateButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateButtonMousePressed
        updateButton.setVisible(false);
        updateButton1.setVisible(true);
    }//GEN-LAST:event_updateButtonMousePressed

    private void updateButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateButtonMouseReleased
        updateButton.setVisible(true);
        updateButton1.setVisible(false);
    }//GEN-LAST:event_updateButtonMouseReleased

    private void purchaseRateFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_purchaseRateFieldMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_purchaseRateFieldMouseClicked

    private void purchaseRateFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_purchaseRateFieldKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_purchaseRateFieldKeyPressed

    private void purchaseRateFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_purchaseRateFieldKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_purchaseRateFieldKeyTyped

    private void productIdFieldMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_productIdFieldMouseEntered
//        productIdField.setFocusable(true);
    }//GEN-LAST:event_productIdFieldMouseEntered

    private void updateButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateButton1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_updateButton1MouseClicked

    private void updateButton1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateButton1MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_updateButton1MousePressed

    private void updateButton1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateButton1MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_updateButton1MouseReleased

    private void typeComboBoxPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_typeComboBoxPropertyChange
        if (typeComboBox.getSelectedItem() == "Liquid") {
            quantityLabel.setText("Liter");
            typeLabel1.setText(currency + "/liter");
            typeLabel2.setText(currency + "/liter");
        } else if (typeComboBox.getSelectedItem() == "Grocery" || typeComboBox.getSelectedItem() == "Meat" || typeComboBox.getSelectedItem() == "Fish") {
            quantityLabel.setText("KG");
            typeLabel1.setText(currency + "/kg");
            typeLabel2.setText(currency + "/kg");
        } else {
            quantityLabel.setText("Item");
            typeLabel1.setText(currency + "/item");
            typeLabel2.setText(currency + "/item");
        }
    }//GEN-LAST:event_typeComboBoxPropertyChange

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
            java.util.logging.Logger.getLogger(updateProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(updateProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(updateProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(updateProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                updateProduct dialog = new updateProduct(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField productIdField;
    private javax.swing.JTextField productNameField;
    private javax.swing.JTextField purchaseRateField;
    private javax.swing.JTextField quantityField;
    private javax.swing.JLabel quantityLabel;
    private javax.swing.JTextField salesRateField;
    private javax.swing.JComboBox typeComboBox;
    private javax.swing.JLabel typeLabel1;
    private javax.swing.JLabel typeLabel2;
    private javax.swing.JLabel updateButton;
    private javax.swing.JLabel updateButton1;
    private javax.swing.JTextField vatField;
    // End of variables declaration//GEN-END:variables
}
