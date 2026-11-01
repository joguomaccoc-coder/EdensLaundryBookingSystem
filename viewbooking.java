/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.laundry_booking_system;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author ADMIN
 */
public class homebooking extends javax.swing.JFrame {
    private void loadBookingMetrics() {
    String url = "jdbc:mysql://localhost:3306/laundry_booking_system";
    String user = "root";
    String password = "";

    String totalSql = "SELECT COUNT(*) FROM tbl_bookings";
    String todaysSql = "SELECT COUNT(*) FROM tbl_bookings WHERE DATE(booking_date) = CURDATE()";

    try (Connection conn = DriverManager.getConnection(url, user, password)) {
        // Load Total Bookings
        try (PreparedStatement ps = conn.prepareStatement(totalSql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                jLabel52.setText(String.valueOf(rs.getInt(1)));
            }
        }

        // Load Today's New Bookings
        try (PreparedStatement ps = conn.prepareStatement(todaysSql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                jLabel51.setText(String.valueOf(rs.getInt(1)));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

private void loadBookingsTable(String searchQuery) {
    String url = "jdbc:mysql://localhost:3306/laundry_booking_system";
    String user = "root";
    String password = "";

    String baseSql = "SELECT b.booking_id, u.username AS customer_name, s.service_name, " +
                     "b.quantity, b.total_price, b.booking_date, b.booking_status " +
                     "FROM tbl_bookings b " +
                     "JOIN tbl_users u ON b.user_id = u.user_id " +
                     "JOIN tbl_services s ON b.service_id = s.service_id ";

    String finalSql = baseSql;
    boolean isSearching = searchQuery != null && !searchQuery.trim().isEmpty() 
                          && !searchQuery.equals("Search by Order ID, Customer name, or status...");

    if (isSearching) {
        finalSql += "WHERE CAST(b.booking_id AS CHAR) LIKE ? " +
                    "OR u.username LIKE ? " +
                    "OR b.booking_status LIKE ? ";
    }

    finalSql += "ORDER BY b.booking_id DESC";

    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.setRowCount(0);

    try (Connection conn = DriverManager.getConnection(url, user, password);
         PreparedStatement ps = conn.prepareStatement(finalSql)) {

        if (isSearching) {
            String keyword = "%" + searchQuery.trim() + "%";
            ps.setString(1, keyword);
            ps.setString(2, keyword);
            ps.setString(3, keyword);
        }

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("booking_id"),
                    rs.getString("customer_name"),
                    rs.getString("service_name"),
                    rs.getString("quantity") + " kg",
                    "₱" + String.format("%.2f", rs.getDouble("total_price")),
                    rs.getDate("booking_date"),
                    rs.getString("booking_status")
                });
            }
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error loading bookings: " + e.getMessage());
        e.printStackTrace();
    }
}

    /**
     * Creates new form homeManageCust
     */
   
        
       
public homebooking() {
    initComponents();

    final String placeholder = "Search by Order ID, Customer name, or status...";

    jTextField1.setText(placeholder);
    jTextField1.setForeground(java.awt.Color.GRAY);

    jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
        @Override
        public void focusGained(java.awt.event.FocusEvent evt) {
            if (jTextField1.getText().equals(placeholder)) {
                jTextField1.setText("");
                jTextField1.setForeground(java.awt.Color.BLACK);
            }
        }

        @Override
        public void focusLost(java.awt.event.FocusEvent evt) {
            if (jTextField1.getText().trim().isEmpty()) {
                jTextField1.setText(placeholder);
                jTextField1.setForeground(java.awt.Color.GRAY);
            }
        }
    });

    // Add Live Search Listener
    jTextField1.getDocument().addDocumentListener(new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) { filter(); }
        @Override
        public void removeUpdate(DocumentEvent e) { filter(); }
        @Override
        public void changedUpdate(DocumentEvent e) { filter(); }

        private void filter() {
            loadBookingsTable(jTextField1.getText());
        }
    });

    // Load initial metrics & full table
    loadBookingMetrics();
    loadBookingsTable("");
}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jLabel5 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel52 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();

        jLabel5.setForeground(new java.awt.Color(0, 102, 102));
        jLabel5.setIcon(new javax.swing.ImageIcon("C:\\Users\\ADMIN\\Downloads\\Sunlit Aqua Laundry Corner (1) (1).png")); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel9.setBackground(new java.awt.Color(0, 102, 102));
        jPanel9.setForeground(new java.awt.Color(0, 102, 102));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/abcde-removebg-preview (1).png"))); // NOI18N
        jPanel9.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 160, 140));

        jButton1.setBackground(new java.awt.Color(0, 153, 153));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setText("Logout");
        jButton1.setBorder(null);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 630, 130, 40));

        jButton2.setBackground(new java.awt.Color(0, 153, 153));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setText("Dashboard");
        jButton2.setBorder(null);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 130, 40));

        jButton3.setBackground(new java.awt.Color(0, 153, 153));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Bookings");
        jButton3.setBorder(null);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 130, 40));

        jButton4.setBackground(new java.awt.Color(0, 153, 153));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton4.setText("Manage Customers");
        jButton4.setBorder(null);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 130, 40));

        jButton5.setBackground(new java.awt.Color(0, 153, 153));
        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton5.setText("Create New Customer ");
        jButton5.setBorder(null);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 130, 40));

        getContentPane().add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 170, 700));

        jPanel1.setBackground(new java.awt.Color(201, 227, 253));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Rockwell Condensed", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 51));
        jLabel7.setText("EDEN'S LAUNDRY BOOKINGS SYSTEM");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 153));
        jLabel1.setText("View and manage bookings.");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 255), 1, true));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel51.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel51.setText("-");
        jPanel6.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 40, -1));

        jLabel56.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(0, 51, 51));
        jLabel56.setText("Today's new bookings");
        jPanel6.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, -1, 30));

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 100, 290, 90));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 255), 1, true));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel52.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel52.setText("-");
        jPanel7.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 40, -1));

        jLabel57.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(0, 51, 51));
        jLabel57.setText("Total bookings");
        jPanel7.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, -1, 30));

        jPanel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 290, 90));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 255), 1, true));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jPanel2.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 460, 30));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 920, 50));

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel4.setBackground(new java.awt.Color(153, 204, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel44.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(0, 51, 51));
        jLabel44.setText("REPORT RESULTS");
        jPanel4.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 30));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"                   -", "                   -", "                   -", "                   -", "                   -", "                   -", "                   -"},
                {"                   -", "                   -", "                   -", "                   -", "                   -", "                   -", "                   -"},
                {"                   -", "                   -", "                   -", "                   -", "                   -", "                   -", "                   -"}
            },
            new String [] {
                "Order ID", "Customer Name", "Service", "Quantity", "Amount", "Date", "Status"
            }
        ));
        jTable1.setShowGrid(true);
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 918, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 863, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(93, 93, 93))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, 920, 340));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 30, 980, 640));

        jLabel6.setForeground(new java.awt.Color(0, 102, 102));
        jLabel6.setIcon(new javax.swing.ImageIcon("C:\\Users\\ADMIN\\Downloads\\Sunlit Aqua Laundry Corner (1) (1).png")); // NOI18N
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 700));

        pack();
    }// </editor-fold>                        

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
    logoutadmin logout = new logoutadmin();
    logout.setVisible(true);
    this.dispose();     // TODO add your handling code here:
    }                                        

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                         
    homeadmin home = new homeadmin();
    home.setVisible(true);
    this.dispose();     // TODO add your handling code here:
    }                                        

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {                                         
    homebooking booking = new homebooking();
    booking.setVisible(true);
    this.dispose();    // TODO add your handling code here:
    }                                        

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {                                         
    homeManageCust manage = new homeManageCust();
    manage.setVisible(true);
    this.dispose();    // TODO add your handling code here:
    }                                        

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {                                         
    AdminSignup signup = new AdminSignup();
    signup.setVisible(true);
    this.dispose();     // TODO add your handling code here:
    }                                        

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
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
            java.util.logging.Logger.getLogger(homebooking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(homebooking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(homebooking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(homebooking.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new homebooking().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration                   
}
