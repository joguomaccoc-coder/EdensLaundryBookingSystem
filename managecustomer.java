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
public class homeManageCust extends javax.swing.JFrame {
    private void showActionOptions(int customerId, String name, String contact, String email) {
    String[] options = {"Edit Customer", "Delete Customer", "Cancel"};
    int choice = JOptionPane.showOptionDialog(
        this,
        "Select an action for customer: " + name + " (ID: " + customerId + ")",
        "Manage Customer",
        JOptionPane.DEFAULT_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,
        options,
        options[0]
    );

    if (choice == 0) {
        editCustomerDialog(customerId, name, contact, email);
    } else if (choice == 1) {
        deleteCustomer(customerId, name);
    }
}

private void editCustomerDialog(int customerId, String currentName, String currentContact, String currentEmail) {
    javax.swing.JTextField nameField = new javax.swing.JTextField(currentName);
    javax.swing.JTextField contactField = new javax.swing.JTextField(currentContact);
    javax.swing.JTextField emailField = new javax.swing.JTextField(currentEmail);

    Object[] message = {
        "Username/Name:", nameField,
        "Contact No (11 digits):", contactField,
        "Email:", emailField
    };

    int option = JOptionPane.showConfirmDialog(
        this, message, "Edit Customer #" + customerId, 
        JOptionPane.OK_CANCEL_OPTION
    );

    if (option == JOptionPane.OK_OPTION) {
        String newName = nameField.getText().trim();
        String newContact = contactField.getText().trim();
        String newEmail = emailField.getText().trim();

        if (!newContact.matches("\\d{11}")) {
            JOptionPane.showMessageDialog(this, "Contact number must be exactly 11 digits!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String url = "jdbc:mysql://localhost:3306/laundry_booking_system";
        String user = "root";
        String password = "";

        String updateSql = "UPDATE tbl_users SET username = ?, contact_no = ?, email = ? WHERE user_id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(updateSql)) {

            ps.setString(1, newName);
            ps.setString(2, newContact);
            ps.setString(3, newEmail);
            ps.setInt(4, customerId);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Customer updated successfully!");
                loadCustomersTable(""); 
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error updating customer: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}

private void deleteCustomer(int customerId, String name) {
    int confirm = JOptionPane.showConfirmDialog(
        this,
        "Are you sure you want to delete customer '" + name + "' (ID: " + customerId + ")?\nThis action cannot be undone.",
        "Confirm Delete",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.WARNING_MESSAGE
    );

    if (confirm == JOptionPane.YES_OPTION) {
        String url = "jdbc:mysql://localhost:3306/laundry_booking_system";
        String user = "root";
        String password = "";

        String deleteSql = "DELETE FROM tbl_users WHERE user_id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(deleteSql)) {

            ps.setInt(1, customerId);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Customer deleted successfully!");
                loadCustomerMetrics();
                loadCustomersTable("");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                this, 
                "Cannot delete customer because they have active bookings linked to their account.\nDelete their bookings first.", 
                "Foreign Key Constraint Error", 
                JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }
}
    private void loadCustomerMetrics() {
    String url = "jdbc:mysql://localhost:3306/laundry_booking_system";
    String user = "root";
    String password = "";

    // Queries filtered for Customers (role_id = 2)
    String totalSql = "SELECT COUNT(*) FROM tbl_users WHERE role_id = 2";
    String todaysSql = "SELECT COUNT(*) FROM tbl_users WHERE role_id = 2 AND DATE(created_at) = CURDATE()";

    try (Connection conn = DriverManager.getConnection(url, user, password)) {
        // Load Total Customers
        try (PreparedStatement ps = conn.prepareStatement(totalSql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                jLabel50.setText(String.valueOf(rs.getInt(1)));
            }
        }

        // Load Today's New Customers
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

private void loadCustomersTable(String searchQuery) {
    String url = "jdbc:mysql://localhost:3306/laundry_booking_system";
    String user = "root";
    String password = "";

    String baseSql = "SELECT user_id, CONCAT(first_name, ' ', last_name) AS full_name, " +
                     "contact_no, email, 'Active' AS status " +
                     "FROM tbl_users WHERE role_id = 2 ";

    String finalSql = baseSql;
    boolean isSearching = searchQuery != null && !searchQuery.trim().isEmpty() 
                          && !searchQuery.equals("Search by name, phone number, or email...");

    if (isSearching) {
        finalSql += "AND (CONCAT(first_name, ' ', last_name) LIKE ? " +
                    "OR username LIKE ? " +
                    "OR contact_no LIKE ? " +
                    "OR email LIKE ?) ";
    }

    finalSql += "ORDER BY user_id DESC";

    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.setRowCount(0);

    try (Connection conn = DriverManager.getConnection(url, user, password);
         PreparedStatement ps = conn.prepareStatement(finalSql)) {

        if (isSearching) {
            String keyword = "%" + searchQuery.trim() + "%";
            ps.setString(1, keyword);
            ps.setString(2, keyword);
            ps.setString(3, keyword);
            ps.setString(4, keyword);
        }

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("user_id"),
                    rs.getString("full_name"),
                    rs.getString("contact_no"),
                    rs.getString("email"),
                    rs.getString("status"),
                    "Manage / Edit"
                });
            }
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error loading customer list: " + e.getMessage());
        e.printStackTrace();
    }
}

    /**
     * Creates new form homeManageCust
     */
    public homeManageCust() {
    initComponents();
    setLocationRelativeTo(null);
    
    // MouseListener for Edit / Delete on row click
    jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            int selectedRow = jTable1.getSelectedRow();
            int selectedColumn = jTable1.getSelectedColumn();

            // Column 5 is the "Actions" column
            if (selectedRow != -1 && selectedColumn == 5) {
                int customerId = Integer.parseInt(jTable1.getValueAt(selectedRow, 0).toString());
                String customerName = jTable1.getValueAt(selectedRow, 1).toString();
                String contact = jTable1.getValueAt(selectedRow, 2).toString();
                String email = jTable1.getValueAt(selectedRow, 3).toString();

                showActionOptions(customerId, customerName, contact, email);
            }
        }
    });

    final String placeholder = "Search by name, phone number, or email...";

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

    // Add Live Search DocumentListener
    jTextField1.getDocument().addDocumentListener(new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) { filter(); }
        @Override
        public void removeUpdate(DocumentEvent e) { filter(); }
        @Override
        public void changedUpdate(DocumentEvent e) { filter(); }

        private void filter() {
            loadCustomersTable(jTextField1.getText());
        }
    });

    // Load initial metrics & customer list from MySQL
    loadCustomerMetrics();
    loadCustomersTable("");
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
        jPanel5 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
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
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
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
        jLabel1.setText("View and manage your customer accounts.");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 255), 1, true));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel51.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel51.setText("-");
        jPanel6.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 40, -1));

        jLabel56.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(0, 51, 51));
        jLabel56.setText("Today's New customers");
        jPanel6.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, -1, 30));

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 100, 290, 90));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 255), 1, true));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel50.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel50.setText("-");
        jPanel5.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 40, -1));

        jLabel55.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(0, 51, 51));
        jLabel55.setText("Total Customers");
        jPanel5.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, -1, 30));

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 290, 90));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 255), 1, true));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextField1.setText("Search by name, phone number, or email...");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jPanel2.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 460, 30));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 920, 50));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 255), 1, true));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Customer ID", "Name", "Phone number", "Email Address", "Status", "Actions"
            }
        ));
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTable1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTable1.setRowHeight(31);
        jTable1.setShowGrid(true);
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 906, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, -1, 350));

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
    this.dispose();       // TODO add your handling code here:
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
            java.util.logging.Logger.getLogger(homeManageCust.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(homeManageCust.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(homeManageCust.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(homeManageCust.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new homeManageCust().setVisible(true);
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
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration                   
}
