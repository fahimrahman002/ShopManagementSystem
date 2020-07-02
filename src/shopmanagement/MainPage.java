/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shopmanagement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author Lenovo
 */
public class MainPage extends javax.swing.JFrame {

    public String user;
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String currency;
    int[] salesInMonths = new int[12];

    /** Creates new form MainPage */
    public MainPage() {
        initComponents();
        this.user = "admin";
        conn = JConnection.connectdb();
        showUserDetails();
        setAllValues();
        getDataFromServer();
        showChart();

    }

    public MainPage(String user) {
        initComponents();
        this.user = user;
        conn = JConnection.connectdb();
        showUserDetails();
        setAllValues();
        getDataFromServer();
        showChart();

    }

    public void getDataFromServer() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy");
        for (int i = 1; i <= 12; i++) {
            String start = format.format(date) + "-" + Integer.toString(i) + "-01";
            String end = format.format(date) + "-" + Integer.toString(i) + "-31";

            String sql1 = "SELECT sum(quantity) FROM sales WHERE user=\'" + user + "\' AND date  BETWEEN \'" + start + "\' AND \'" + end + "\'";
            try {
                pst = conn.prepareStatement(sql1);
                rs = pst.executeQuery();
                if (rs.next()) {
                    salesInMonths[i - 1] = rs.getInt(1);
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
    }

    public void setAllValues() {
        int salesRate = 0, total = 0, purchaseRate = 0, profit = 0;
        setCurrencyValues();
        currencyLabel1.setText(currency);
        currencyLabel2.setText(currency);
        String sql;

        sql = "SELECT COUNT(product_id) FROM products where user=" + "\'" + user + "\'";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                newItemLabel.setText("" + rs.getInt(1));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        sql = "SELECT SUM(stock) FROM products where user=" + "\'" + user + "\'";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                purchasedItemLabel.setText("" + rs.getInt(1));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        Double Price, Total = 0.0;
        int Quantity;
        String productID, sql2;
        sql = "SELECT product_id,price,quantity FROM sales where user=" + "\'" + user + "\'";
        PreparedStatement pst2 = null;
        ResultSet rs2 = null;
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                productID = (rs.getString(1));
                Price = rs.getDouble(2);
                Quantity = rs.getInt(3);
                Total += Price * Quantity;
                try {
                    sql2 = "SELECT purchase_rate FROM products where user=" + "\'" + user + "\' and product_id='" + productID + "\'";

                    pst2 = conn.prepareStatement(sql2);
                    rs2 = pst2.executeQuery();
                    if (rs2.next()) {
                        purchaseRate += (int) rs2.getDouble(1) * Quantity;
                    }

                } catch (SQLException ex) {
                    System.out.println(ex);
                }

            }
            purchaseValueLabel.setText("" + purchaseRate);
            total = Total.intValue();
            salesValueLabel.setText("" + total);
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        profit = total - purchaseRate;
        if (profit >= 0) {
            currencyLabel3.setText(currency);
            profitLabel.setText(Integer.toString(profit));
        } else {
            profitLabel.setText("No profit");
            currencyLabel3.setText("");
        }

    }

    public void setCurrencyValues() {

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

    public void showUserDetails() {
        String sql = "select * from users where user=" + "\'" + user + "\'";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                userNameLabel.setText(rs.getString("name"));
                orgDetailsLabel.setText("Organization : " + rs.getString("org"));
                emailLabel.setText("Email : " + rs.getString("email"));
                phoneLabel.setText("Phone : " + rs.getString("phone"));
                this.currency = rs.getString("currency");
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void showChart() {
        getDataFromServer();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        DefaultPieDataset pieDataset = new DefaultPieDataset();

        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

        Random rand = new Random();
        for (int i = 0; i < 12; i++) {
            int rand_int1 = salesInMonths[i];
//            int rand_int1 = rand.nextInt(100);
            dataset.setValue(rand_int1, "Month", months[i]);
            pieDataset.setValue(months[i], rand_int1);

        }

        createAllCharts(dataset, pieDataset);

    }

    public void createAllCharts(DefaultCategoryDataset dataset, DefaultPieDataset pieDataset) {
        //-------------------------Bar Chart-------------------------------//
        JFreeChart chart = ChartFactory.createBarChart3D("Bar Chart", "Months", "Total number of Sales", dataset, PlotOrientation.VERTICAL, false, true, true);
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setRangeGridlinePaint(Color.BLUE);
        ChartPanel barPanel = new ChartPanel(chart);
        barChartPanel.removeAll();
        barChartPanel.add(barPanel, BorderLayout.CENTER);
        barChartPanel.validate();

        //-------------------------Line Chart------------------------//
        chart = ChartFactory.createLineChart("Line Chart", "Months", "Total number of Sales", dataset, PlotOrientation.VERTICAL, false, true, true);
        plot = chart.getCategoryPlot();
        plot.setRangeGridlinePaint(Color.BLUE);
        ChartPanel linePanel = new ChartPanel(chart);
        lineChartPanel.removeAll();
        lineChartPanel.add(linePanel, BorderLayout.CENTER);
        lineChartPanel.validate();

        //-------------------------Area Chart-------------------//
        chart = ChartFactory.createAreaChart("Area Chart", "Months", "Total number of Sales", dataset, PlotOrientation.VERTICAL, false, true, true);
        plot = chart.getCategoryPlot();
        plot.setRangeGridlinePaint(Color.BLUE);
        ChartPanel areaPanel = new ChartPanel(chart);
        areaChartPanel.removeAll();
        areaChartPanel.add(areaPanel, BorderLayout.CENTER);
        areaChartPanel.validate();

        //------------------------Pie Chart---------------------//
        chart = ChartFactory.createPieChart3D(
                "Pie Chart", pieDataset, true, true, true);

        PiePlot3D piePlot3D = (PiePlot3D) chart.getPlot();
        piePlot3D.setForegroundAlpha(0.5f);
        piePlot3D.setBackgroundAlpha(0.2f);

        chart.setBackgroundPaint(Color.white);
        chart.setAntiAlias(true);
        chart.setBorderVisible(true);

        ChartPanel piePanel = new ChartPanel(chart);
        pieChartPanel.removeAll();
        pieChartPanel.add(piePanel, BorderLayout.CENTER);
        pieChartPanel.validate();
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
        iconLabel = new javax.swing.JLabel();
        userNameLabel = new javax.swing.JLabel();
        phoneLabel = new javax.swing.JLabel();
        orgDetailsLabel = new javax.swing.JLabel();
        emailLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        dashboardPanel = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        newItemLabel = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        purchasedItemLabel = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        profitLabel = new javax.swing.JLabel();
        currencyLabel3 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        purchaseValueLabel = new javax.swing.JLabel();
        currencyLabel2 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        salesValueLabel = new javax.swing.JLabel();
        currencyLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        barChartPanel = new javax.swing.JPanel();
        lineChartPanel = new javax.swing.JPanel();
        areaChartPanel = new javax.swing.JPanel();
        pieChartPanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        dashboardImg = new javax.swing.JLabel();
        dashboardButton = new javax.swing.JButton();
        purchasesImg = new javax.swing.JLabel();
        purchasesButton = new javax.swing.JButton();
        salesImg = new javax.swing.JLabel();
        salesButton = new javax.swing.JButton();
        accountsImg = new javax.swing.JLabel();
        accountsButton = new javax.swing.JButton();
        reportsImg = new javax.swing.JLabel();
        reportsButton = new javax.swing.JButton();
        taxReportsImg = new javax.swing.JLabel();
        taxReportsButton = new javax.swing.JButton();
        chequeAlartsImg = new javax.swing.JLabel();
        chequeAlartsButton = new javax.swing.JButton();
        chequeAlartsImg1 = new javax.swing.JLabel();
        logoutButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                formMouseEntered(evt);
            }
        });
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        iconLabel.setBackground(new java.awt.Color(0,0,0,0
        ));
        iconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/store-bg.png"))); // NOI18N
        jPanel1.add(iconLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 70, 60));

        userNameLabel.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        userNameLabel.setForeground(new java.awt.Color(0, 51, 102));
        userNameLabel.setText("User");
        jPanel1.add(userNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(457, 0, 122, 38));

        phoneLabel.setFont(new java.awt.Font("Arial", 3, 16)); // NOI18N
        phoneLabel.setForeground(new java.awt.Color(0, 102, 102));
        phoneLabel.setText("Phone:");
        jPanel1.add(phoneLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 40, 242, -1));

        orgDetailsLabel.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        orgDetailsLabel.setForeground(new java.awt.Color(0, 102, 0));
        orgDetailsLabel.setText("Organization:");
        jPanel1.add(orgDetailsLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 0, 250, 30));

        emailLabel.setFont(new java.awt.Font("Arial", 3, 16)); // NOI18N
        emailLabel.setForeground(new java.awt.Color(0, 102, 102));
        emailLabel.setText("Email:");
        jPanel1.add(emailLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 60, 350, -1));

        jPanel2.setBackground(new java.awt.Color(96, 112, 128));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("FRP Softwares");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 540, 110, -1));

        dashboardPanel.setBackground(new java.awt.Color(255, 255, 255));

        jPanel5.setBackground(new java.awt.Color(1, 193, 240));
        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel5MouseClicked(evt);
            }
        });
        jPanel5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanel5KeyPressed(evt);
            }
        });
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(0,0,0,60));
        jPanel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel6MouseClicked(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Arial Narrow", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("New Items");
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel11)
                .addContainerGap(35, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel5.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(-2, 74, 140, 40));

        newItemLabel.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        newItemLabel.setForeground(new java.awt.Color(255, 255, 255));
        newItemLabel.setText("0");
        jPanel5.add(newItemLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 110, 40));

        jPanel7.setBackground(new java.awt.Color(0, 165, 89));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel8.setBackground(new java.awt.Color(0,0,0,60));
        jPanel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel8MouseClicked(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Arial Narrow", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Purchased Items");
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel13)
                .addContainerGap(10, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(-2, 74, 140, 40));

        purchasedItemLabel.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        purchasedItemLabel.setForeground(new java.awt.Color(255, 255, 255));
        purchasedItemLabel.setText("0");
        jPanel7.add(purchasedItemLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 110, 40));

        jPanel9.setBackground(new java.awt.Color(243, 156, 17));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel10.setBackground(new java.awt.Color(0,0,0,60));

        jLabel15.setFont(new java.awt.Font("Arial Narrow", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Profit");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel9.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(-2, 74, 140, 40));

        profitLabel.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        profitLabel.setForeground(new java.awt.Color(255, 255, 255));
        profitLabel.setText("0");
        jPanel9.add(profitLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 120, 40));

        currencyLabel3.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        currencyLabel3.setForeground(new java.awt.Color(255, 255, 255));
        currencyLabel3.setText("$");
        jPanel9.add(currencyLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, 40, 20));

        jPanel11.setBackground(new java.awt.Color(1, 193, 240));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel12.setBackground(new java.awt.Color(0,0,0,60));

        jLabel17.setFont(new java.awt.Font("Arial Narrow", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Purchased Value");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17)
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel11.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(-2, 74, 140, 40));

        purchaseValueLabel.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        purchaseValueLabel.setForeground(new java.awt.Color(255, 255, 255));
        purchaseValueLabel.setText("0  ");
        jPanel11.add(purchaseValueLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 120, 40));

        currencyLabel2.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        currencyLabel2.setForeground(new java.awt.Color(255, 255, 255));
        currencyLabel2.setText("$");
        jPanel11.add(currencyLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, 40, 20));

        jPanel13.setBackground(new java.awt.Color(222, 75, 57));
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel14.setBackground(new java.awt.Color(0,0,0,60));

        jLabel19.setFont(new java.awt.Font("Arial Narrow", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Sales Value");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel19)
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel13.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(-2, 74, 140, 40));

        salesValueLabel.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        salesValueLabel.setForeground(new java.awt.Color(255, 255, 255));
        salesValueLabel.setText("0");
        jPanel13.add(salesValueLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 120, 40));

        currencyLabel1.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        currencyLabel1.setForeground(new java.awt.Color(255, 255, 255));
        currencyLabel1.setText("$");
        jPanel13.add(currencyLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, 40, 20));

        barChartPanel.setMinimumSize(new java.awt.Dimension(700, 700));
        barChartPanel.setPreferredSize(new java.awt.Dimension(0, 0));
        barChartPanel.setLayout(new javax.swing.BoxLayout(barChartPanel, javax.swing.BoxLayout.LINE_AXIS));
        jTabbedPane1.addTab("Bar Chart", barChartPanel);

        lineChartPanel.setLayout(new javax.swing.BoxLayout(lineChartPanel, javax.swing.BoxLayout.LINE_AXIS));
        jTabbedPane1.addTab("Line Chart", lineChartPanel);

        areaChartPanel.setLayout(new javax.swing.BoxLayout(areaChartPanel, javax.swing.BoxLayout.LINE_AXIS));
        jTabbedPane1.addTab("Area Chart", areaChartPanel);

        pieChartPanel.setLayout(new javax.swing.BoxLayout(pieChartPanel, javax.swing.BoxLayout.LINE_AXIS));
        jTabbedPane1.addTab("Pie Chart", pieChartPanel);

        javax.swing.GroupLayout dashboardPanelLayout = new javax.swing.GroupLayout(dashboardPanel);
        dashboardPanel.setLayout(dashboardPanelLayout);
        dashboardPanelLayout.setHorizontalGroup(
            dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(74, Short.MAX_VALUE))
            .addComponent(jTabbedPane1)
        );
        dashboardPanelLayout.setVerticalGroup(
            dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardPanelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(dashboardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 437, Short.MAX_VALUE))
        );

        jPanel2.add(dashboardPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 0, 840, 580));

        jPanel4.setBackground(new java.awt.Color(96, 112, 128));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        dashboardImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dashboard (1).png"))); // NOI18N
        jPanel4.add(dashboardImg, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 20, 20));

        dashboardButton.setText("     Dashboard");
        dashboardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dashboardButtonActionPerformed(evt);
            }
        });
        jPanel4.add(dashboardButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 130, 40));

        purchasesImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/basket.png"))); // NOI18N
        jPanel4.add(purchasesImg, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 20, 20));

        purchasesButton.setText("     Purchases");
        purchasesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                purchasesButtonActionPerformed(evt);
            }
        });
        jPanel4.add(purchasesButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 130, 40));

        salesImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/coupon.png"))); // NOI18N
        jPanel4.add(salesImg, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 20, 20));

        salesButton.setText("     Sale Items");
        salesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salesButtonActionPerformed(evt);
            }
        });
        jPanel4.add(salesButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 130, 40));

        accountsImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/money.png"))); // NOI18N
        jPanel4.add(accountsImg, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 20, 20));

        accountsButton.setText("     Accounts");
        accountsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accountsButtonActionPerformed(evt);
            }
        });
        jPanel4.add(accountsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 130, 40));

        reportsImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/market.png"))); // NOI18N
        jPanel4.add(reportsImg, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 20, 20));

        reportsButton.setText("     Reports");
        reportsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportsButtonActionPerformed(evt);
            }
        });
        jPanel4.add(reportsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 130, 40));

        taxReportsImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tax.png"))); // NOI18N
        jPanel4.add(taxReportsImg, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 20, 20));

        taxReportsButton.setText("     Tax Reports");
        jPanel4.add(taxReportsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 130, 40));

        chequeAlartsImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cheque.png"))); // NOI18N
        jPanel4.add(chequeAlartsImg, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 20, 20));

        chequeAlartsButton.setText("     Cheque Alarts");
        chequeAlartsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chequeAlartsButtonActionPerformed(evt);
            }
        });
        jPanel4.add(chequeAlartsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 130, 40));

        chequeAlartsImg1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logout.png"))); // NOI18N
        jPanel4.add(chequeAlartsImg1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 20, 20));

        logoutButton.setText("     Log out");
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });
        jPanel4.add(logoutButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 280, 130, 40));

        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 130, 330));

        jLabel2.setFont(new java.awt.Font("Cambria Math", 3, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Welcome ");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, -1, -1));

        jLabel3.setFont(new java.awt.Font("Cambria Math", 3, 20)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("CREDITS");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 490, 90, -1));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("All copyrights ©");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 520, 120, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void salesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salesButtonActionPerformed
        new saleItemForm(user).setVisible(true);
    }//GEN-LAST:event_salesButtonActionPerformed

    private void accountsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accountsButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_accountsButtonActionPerformed

    private void reportsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportsButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_reportsButtonActionPerformed

    private void dashboardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dashboardButtonActionPerformed
        dashboardPanel.setVisible(true);
        showChart();
    }//GEN-LAST:event_dashboardButtonActionPerformed

    private void jPanel5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel5KeyPressed

    }//GEN-LAST:event_jPanel5KeyPressed

    private void jPanel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseClicked

    }//GEN-LAST:event_jPanel5MouseClicked

    private void jPanel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel6MouseClicked
        new addNewItemForm(this, true, user).setVisible(true);
    }//GEN-LAST:event_jPanel6MouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        new addNewItemForm(this, true, user).setVisible(true);
    }//GEN-LAST:event_jLabel11MouseClicked

    private void jPanel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel8MouseClicked
        new purchasedItemFrom(this, true, user).setVisible(true);
    }//GEN-LAST:event_jPanel8MouseClicked

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        new purchasedItemFrom(this, true, user).setVisible(true);
    }//GEN-LAST:event_jLabel13MouseClicked

    private void purchasesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_purchasesButtonActionPerformed
        new purchasedItemFrom(this, true, user).setVisible(true);
    }//GEN-LAST:event_purchasesButtonActionPerformed

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed
        String sql = "update users set active=false where user=\'" + user + "\'";
        try {
            pst = conn.prepareStatement(sql);
            pst.execute();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Can not logout from this session");
        }

        new LoginPage().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_logoutButtonActionPerformed

    private void formMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_formMouseEntered

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        setAllValues();
        showChart();
    }//GEN-LAST:event_formWindowGainedFocus

    private void chequeAlartsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chequeAlartsButtonActionPerformed
        JOptionPane.showMessageDialog(null, "No cheque alarts right now.");
    }//GEN-LAST:event_chequeAlartsButtonActionPerformed

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        setAllValues();
        showChart();
    }//GEN-LAST:event_formFocusGained

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
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton accountsButton;
    private javax.swing.JLabel accountsImg;
    private javax.swing.JPanel areaChartPanel;
    private javax.swing.JPanel barChartPanel;
    private javax.swing.JButton chequeAlartsButton;
    private javax.swing.JLabel chequeAlartsImg;
    private javax.swing.JLabel chequeAlartsImg1;
    private javax.swing.JLabel currencyLabel1;
    private javax.swing.JLabel currencyLabel2;
    private javax.swing.JLabel currencyLabel3;
    private javax.swing.JButton dashboardButton;
    private javax.swing.JLabel dashboardImg;
    private javax.swing.JPanel dashboardPanel;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JLabel iconLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel lineChartPanel;
    private javax.swing.JButton logoutButton;
    private javax.swing.JLabel newItemLabel;
    private javax.swing.JLabel orgDetailsLabel;
    private javax.swing.JLabel phoneLabel;
    private javax.swing.JPanel pieChartPanel;
    private javax.swing.JLabel profitLabel;
    private javax.swing.JLabel purchaseValueLabel;
    private javax.swing.JLabel purchasedItemLabel;
    private javax.swing.JButton purchasesButton;
    private javax.swing.JLabel purchasesImg;
    private javax.swing.JButton reportsButton;
    private javax.swing.JLabel reportsImg;
    private javax.swing.JButton salesButton;
    private javax.swing.JLabel salesImg;
    private javax.swing.JLabel salesValueLabel;
    private javax.swing.JButton taxReportsButton;
    private javax.swing.JLabel taxReportsImg;
    private javax.swing.JLabel userNameLabel;
    // End of variables declaration//GEN-END:variables
}
