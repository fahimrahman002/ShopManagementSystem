/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shopmanagement;

import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Fahim
 */
public class addNewItemForm extends javax.swing.JDialog {

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String user;
    String currency;
    String initialProductId;
    DefaultTableModel dtm;
    int row;

    /** Creates new form addNewItemForm */
    public addNewItemForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        setUndecorated(false);
        initComponents();
        buttonAnimation();
        conn = JConnection.connectdb();
        dtm = (DefaultTableModel) productTable.getModel();
        this.user = "admin";
        setAllValues();
        setTableModel();
        showProducts();
    }

    public addNewItemForm(java.awt.Frame parent, boolean modal, String user) {
        super(parent, modal);
        setUndecorated(false);
        initComponents();
        buttonAnimation();
        conn = JConnection.connectdb();
        this.user = user;
        setAllValues();
        setTableModel();
        showProducts();
    }

    public void setTableModel() {
        dtm = (DefaultTableModel) productTable.getModel();
        productTable.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        productTable.setGridColor(Color.black);
        productTable.setShowHorizontalLines(true);
        productTable.setShowVerticalLines(true);
    }

    public void setAllValues() {
        setCurrencyValues();
        typeLabel1.setText(currency + "/item");
        typeLabel2.setText(currency + "/item");

    }

    public void buttonAnimation() {
        addItemButton.setVisible(true);
        addItemButton1.setVisible(false);
        removeButton.setVisible(true);
        removeButton1.setVisible(false);
        refreshButton.setVisible(true);
        refreshButton1.setVisible(false);
        updateButton.setVisible(true);
        updateButton1.setVisible(false);
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

    public void addProduct(String productId, String productName, String type, Double purchaseRate, Double salesRate, int vat, int stock, java.sql.Date lastUpdate, java.sql.Time time) {

        String sql = "INSERT INTO products(user, product_id, product_name, type, purchase_rate, sales_rate, vat, stock, last_update, time) VALUES(?,?,?,?,?,?,?,?,?,?)";
        try {

            pst = conn.prepareStatement(sql);
            pst.setString(1, user);
            pst.setString(2, productId);
            pst.setString(3, productName);
            pst.setString(4, type);
            pst.setDouble(5, purchaseRate);
            pst.setDouble(6, salesRate);
            pst.setInt(7, vat);
            pst.setInt(8, stock);
            pst.setDate(9, lastUpdate);
            pst.setTime(10, time);
            pst.execute();

            clearField();
            showProducts();

            JOptionPane.showMessageDialog(null, "New item added");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void clearField() {
        productIdField.setText("");
        productNameField.setText("");
        purchaseRateField.setText("");
        salesRateField.setText("");
        vatField.setText("0");
        quantityField.setText("1");
    }

    public void showProducts() {

        String sql = "select * from products where user=" + "\'" + user + "\'";

        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            dtm.setRowCount(0);
            while (rs.next()) {
                Object[] obj = {rs.getString(2), rs.getString(3), rs.getString(4), rs.getDouble(5), rs.getDouble(6), rs.getInt(7), rs.getInt(8), rs.getDate(9), rs.getTime(10)};
                dtm.addRow(obj);

            }

//            productTable.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
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
        purchaseRateField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        salesRateField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        quantityField = new javax.swing.JTextField();
        quantityLabel = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        vatField = new javax.swing.JTextField();
        addItemButton = new javax.swing.JLabel();
        productIdField = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        typeComboBox = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        typeLabel1 = new javax.swing.JLabel();
        removeButton = new javax.swing.JLabel();
        typeLabel2 = new javax.swing.JLabel();
        addItemButton1 = new javax.swing.JLabel();
        removeButton1 = new javax.swing.JLabel();
        updateButton = new javax.swing.JLabel();
        updateButton1 = new javax.swing.JLabel();
        refreshButton = new javax.swing.JLabel();
        refreshButton1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        productTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setText("Product Name :");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 80, 116, 30));

        productNameField.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jPanel1.add(productNameField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 80, 270, -1));

        jLabel2.setFont(new java.awt.Font("DialogInput", 1, 24)); // NOI18N
        jLabel2.setText("Add New Item");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 0, 197, 29));

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setText("Purchase Rate :");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 110, 116, 30));

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
        jPanel1.add(purchaseRateField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 110, 90, -1));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setText("Sales Rate :");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 140, 113, 30));

        salesRateField.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        salesRateField.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                salesRateFieldVetoableChange(evt);
            }
        });
        jPanel1.add(salesRateField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 140, 90, -1));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setText("Item Type :");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 50, 86, 30));

        jLabel6.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel6.setText("Quantity ");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 110, 86, -1));

        quantityField.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        quantityField.setText("1");
        jPanel1.add(quantityField, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 130, 60, -1));

        quantityLabel.setFont(new java.awt.Font("Arial", 1, 17)); // NOI18N
        quantityLabel.setText(" item");
        jPanel1.add(quantityLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 130, 50, 30));

        jLabel8.setFont(new java.awt.Font("Curlz MT", 1, 24)); // NOI18N
        jLabel8.setText("%");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 90, 24, 25));

        vatField.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        vatField.setText("0");
        jPanel1.add(vatField, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 90, 34, -1));

        addItemButton.setBackground(new java.awt.Color(255, 51, 51));
        addItemButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addItem.jpg"))); // NOI18N
        addItemButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addItemButtonMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                addItemButtonMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                addItemButtonMouseReleased(evt);
            }
        });
        jPanel1.add(addItemButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(522, 130, 110, 40));

        productIdField.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jPanel1.add(productIdField, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, 270, -1));

        jLabel10.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel10.setText("Product Id :");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 50, 110, 30));

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
        jPanel1.add(typeComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 50, -1, -1));

        jLabel11.setFont(new java.awt.Font("Arial", 1, 17)); // NOI18N
        jLabel11.setText("Vat : ");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 90, 50, 30));

        typeLabel1.setFont(new java.awt.Font("Arial", 1, 17)); // NOI18N
        typeLabel1.setText("/ item");
        jPanel1.add(typeLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 110, 90, 30));

        removeButton.setBackground(new java.awt.Color(255, 51, 51));
        removeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/remove.jpg"))); // NOI18N
        removeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                removeButtonMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                removeButtonMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                removeButtonMouseReleased(evt);
            }
        });
        jPanel1.add(removeButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 130, 110, 40));

        typeLabel2.setFont(new java.awt.Font("Arial", 1, 17)); // NOI18N
        typeLabel2.setText("/ item");
        jPanel1.add(typeLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 140, 90, 30));

        addItemButton1.setBackground(new java.awt.Color(255, 51, 51));
        addItemButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addItem.jpg"))); // NOI18N
        addItemButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addItemButton1MouseClicked(evt);
            }
        });
        jPanel1.add(addItemButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 140, 102, 30));

        removeButton1.setBackground(new java.awt.Color(255, 51, 51));
        removeButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/remove.jpg"))); // NOI18N
        removeButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                removeButton1MouseClicked(evt);
            }
        });
        jPanel1.add(removeButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(637, 134, 115, 40));

        updateButton.setBackground(new java.awt.Color(255, 51, 51));
        updateButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/update.jpg"))); // NOI18N
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
        jPanel1.add(updateButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 90, 102, 32));

        updateButton1.setBackground(new java.awt.Color(255, 51, 51));
        updateButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/update.jpg"))); // NOI18N
        updateButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                updateButton1MouseClicked(evt);
            }
        });
        jPanel1.add(updateButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(677, 93, 102, 30));

        refreshButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/refresh - Copy.jpg"))); // NOI18N
        refreshButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                refreshButtonMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                refreshButtonMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                refreshButtonMouseReleased(evt);
            }
        });
        jPanel1.add(refreshButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(685, 32, 140, 60));

        refreshButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/refresh3 - Copy.png"))); // NOI18N
        jPanel1.add(refreshButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(683, 55, 110, 40));

        productTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product Id", "Product Name", "Type", "Purchase Rate", "Sales Rate", "Vat", "Stock", "Last Update", "Time"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        productTable.setRowHeight(20);
        productTable.getTableHeader().setReorderingAllowed(false);
        productTable.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                productTableFocusGained(evt);
            }
        });
        productTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                productTableMouseClicked(evt);
            }
        });
        productTable.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                productTablePropertyChange(evt);
            }
        });
        jScrollPane1.setViewportView(productTable);
        if (productTable.getColumnModel().getColumnCount() > 0) {
            productTable.getColumnModel().getColumn(0).setResizable(false);
            productTable.getColumnModel().getColumn(0).setPreferredWidth(35);
            productTable.getColumnModel().getColumn(1).setResizable(false);
            productTable.getColumnModel().getColumn(2).setResizable(false);
            productTable.getColumnModel().getColumn(2).setPreferredWidth(50);
            productTable.getColumnModel().getColumn(3).setResizable(false);
            productTable.getColumnModel().getColumn(4).setResizable(false);
            productTable.getColumnModel().getColumn(5).setResizable(false);
            productTable.getColumnModel().getColumn(5).setPreferredWidth(30);
            productTable.getColumnModel().getColumn(6).setResizable(false);
            productTable.getColumnModel().getColumn(6).setPreferredWidth(35);
            productTable.getColumnModel().getColumn(7).setResizable(false);
            productTable.getColumnModel().getColumn(8).setResizable(false);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 836, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void addItemButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addItemButtonMouseClicked

        try {
            String productId = productIdField.getText();
            String productName = productNameField.getText();
            String type = (String) typeComboBox.getSelectedItem();
            Double purchaseRate = Double.parseDouble(purchaseRateField.getText());
            Double salesRate = Double.parseDouble(salesRateField.getText());
            int vat = Integer.parseInt(vatField.getText());
            int stock = Integer.parseInt(quantityField.getText());

            Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            java.sql.Time sqlTime = new java.sql.Time(date.getTime());
            String sql = "select product_id from products where user='" + user + "\' and product_id='"+productId+"\'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Product id should be unique", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                addProduct(productId, productName, type, purchaseRate, salesRate, vat, stock, sqlDate, sqlTime);
                clearField();
                showProducts();
            }
        } catch (Exception e) {
            final JPanel panel = new JPanel();
            JOptionPane.showMessageDialog(panel, "Wrong Input.\nPlease make sure you have given right text and right number in specific field.", "Warning",
                    JOptionPane.WARNING_MESSAGE);

        }
    }//GEN-LAST:event_addItemButtonMouseClicked

    private void purchaseRateFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_purchaseRateFieldKeyTyped


    }//GEN-LAST:event_purchaseRateFieldKeyTyped

    private void purchaseRateFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_purchaseRateFieldKeyPressed


    }//GEN-LAST:event_purchaseRateFieldKeyPressed

    private void purchaseRateFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_purchaseRateFieldMouseClicked

    }//GEN-LAST:event_purchaseRateFieldMouseClicked

    private void removeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_removeButtonMouseClicked
        if (productTable.getRowCount() == 0) {
            final JPanel panel = new JPanel();
            JOptionPane.showMessageDialog(panel, "There is no data to delete.", "Warning",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            row = productTable.getSelectedRow();
            if (row == -1) {
                final JPanel panel = new JPanel();
                JOptionPane.showMessageDialog(panel, "No row selected.", "No selection",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                String id = dtm.getValueAt(row, 0).toString();
                String name = dtm.getValueAt(row, 1).toString();
                String sql = "DELETE FROM products WHERE product_id=" + "\'" + id + "\' AND user=" + "\'" + user + "\'";
                String warningMassage = "Delete product from database-" + "\nProduct id : " + id + "\nProduct Name : " + name;
                int DialogResult = JOptionPane.showConfirmDialog(this, warningMassage, "Delete", JOptionPane.YES_NO_OPTION);
                if (DialogResult == 0) {
                    try {
                        pst = conn.prepareStatement(sql);
                        pst.execute();
                        clearField();
                        showProducts();
                    } catch (SQLException ex) {
                        final JPanel panel = new JPanel();
                        JOptionPane.showMessageDialog(panel, ex, "Warning",
                                JOptionPane.WARNING_MESSAGE);
                    }
//            System.out.println(productTable.getSelectedRow());

                }
            }
        }

    }//GEN-LAST:event_removeButtonMouseClicked

    private void typeComboBoxHierarchyChanged(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_typeComboBoxHierarchyChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_typeComboBoxHierarchyChanged

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

    private void addItemButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addItemButton1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_addItemButton1MouseClicked

    private void addItemButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addItemButtonMousePressed
        addItemButton.setVisible(false);
        addItemButton1.setVisible(true);
    }//GEN-LAST:event_addItemButtonMousePressed

    private void addItemButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addItemButtonMouseReleased
        addItemButton.setVisible(true);
        addItemButton1.setVisible(false);
    }//GEN-LAST:event_addItemButtonMouseReleased

    private void removeButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_removeButton1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_removeButton1MouseClicked

    private void removeButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_removeButtonMousePressed
        removeButton.setVisible(false);
        removeButton1.setVisible(true);
    }//GEN-LAST:event_removeButtonMousePressed

    private void removeButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_removeButtonMouseReleased
        removeButton.setVisible(true);
        removeButton1.setVisible(false);
    }//GEN-LAST:event_removeButtonMouseReleased

    private void typeComboBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_typeComboBoxMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_typeComboBoxMouseClicked

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

            if (productId.equals("") || productName.equals("") || stock <= 0) {
                throw new Exception();
            }
        } catch (Exception e) {
            final JPanel panel = new JPanel();
            JOptionPane.showMessageDialog(panel, "Wrong Input.\nPlease make sure you have given right text and right value in specific fields.", "Warning", JOptionPane.WARNING_MESSAGE);
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
                clearField();
                setSelectedValue(typeComboBox, "Products");
                JFrame frame = new JFrame();
                JOptionPane.showMessageDialog(frame, "Product details updated.");
                showProducts();

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

    private void updateButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateButton1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_updateButton1MouseClicked

    private void refreshButtonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refreshButtonMouseReleased
        refreshButton.setVisible(true);
        refreshButton1.setVisible(false);
    }//GEN-LAST:event_refreshButtonMouseReleased

    private void refreshButtonMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refreshButtonMousePressed
        refreshButton.setVisible(false);
        refreshButton1.setVisible(true);
    }//GEN-LAST:event_refreshButtonMousePressed

    private void productTableFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_productTableFocusGained

    }//GEN-LAST:event_productTableFocusGained

    private void productTablePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_productTablePropertyChange

    }//GEN-LAST:event_productTablePropertyChange

    private void productTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_productTableMouseClicked
        int row = productTable.getSelectedRow();
        initialProductId = (String) dtm.getValueAt(row, 0);
        productIdField.setText((String) dtm.getValueAt(row, 0));
        productNameField.setText((String) dtm.getValueAt(row, 1));
        setSelectedValue(typeComboBox, (String) dtm.getValueAt(row, 2));
        purchaseRateField.setText(dtm.getValueAt(row, 3).toString());
        salesRateField.setText(dtm.getValueAt(row, 4).toString());
        vatField.setText(dtm.getValueAt(row, 5).toString());
        quantityField.setText(dtm.getValueAt(row, 6).toString());

    }//GEN-LAST:event_productTableMouseClicked

    private void refreshButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refreshButtonMouseClicked
        clearField();
        showProducts();
    }//GEN-LAST:event_refreshButtonMouseClicked

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

    private void salesRateFieldVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_salesRateFieldVetoableChange

    }//GEN-LAST:event_salesRateFieldVetoableChange
    public void setSelectedValue(JComboBox comboBox, String match) {
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            Object item = comboBox.getItemAt(i);
            if (item.equals(match)) {
                comboBox.setSelectedIndex(i);
                break;
            }
        }
    }

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
            java.util.logging.Logger.getLogger(addNewItemForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(addNewItemForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(addNewItemForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(addNewItemForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                addNewItemForm dialog = new addNewItemForm(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel addItemButton;
    private javax.swing.JLabel addItemButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField productIdField;
    private javax.swing.JTextField productNameField;
    private javax.swing.JTable productTable;
    private javax.swing.JTextField purchaseRateField;
    private javax.swing.JTextField quantityField;
    private javax.swing.JLabel quantityLabel;
    private javax.swing.JLabel refreshButton;
    private javax.swing.JLabel refreshButton1;
    private javax.swing.JLabel removeButton;
    private javax.swing.JLabel removeButton1;
    private javax.swing.JTextField salesRateField;
    private javax.swing.JComboBox typeComboBox;
    private javax.swing.JLabel typeLabel1;
    private javax.swing.JLabel typeLabel2;
    private javax.swing.JLabel updateButton;
    private javax.swing.JLabel updateButton1;
    private javax.swing.JTextField vatField;
    // End of variables declaration//GEN-END:variables
}
