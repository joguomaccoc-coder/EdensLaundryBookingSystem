/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.laundry_booking_system;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.text.DecimalFormat;
import com.toedter.calendar.JDateChooser;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.sql.Statement;

public class Book_page extends javax.swing.JFrame {

    private JDateChooser dateChooser;
    
    private final String weightPlaceholder =
        "Enter weight (kg) / Quantity...";
    
    
    
    private void cancelBooking() {
    int choice = JOptionPane.showConfirmDialog(
        this,
        "Are you sure you want to cancel this booking?",
        "Confirm Cancel",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE
    );

    if (choice == JOptionPane.YES_OPTION) {
        jComboBox1.setSelectedIndex(0);
        txtWeight.setText("");
        jTextField3.setText("");
        txtNote.setText("");

        if (dateChooser != null) {
            dateChooser.setDate(new java.util.Date());
        }
    }
}
     private final String pricePlaceholder =
        "Auto-filled based on service...";
     
    public Book_page() {
        initComponents();
        
        
String notePlaceholder = "Note...";

txtNote.setText(notePlaceholder);
txtNote.setForeground(java.awt.Color.GRAY);

txtNote.addFocusListener(new java.awt.event.FocusAdapter() {
    @Override
    public void focusGained(java.awt.event.FocusEvent evt) {
        if (txtNote.getText().equals(notePlaceholder)) {
            txtNote.setText("");
            txtNote.setForeground(java.awt.Color.BLACK);
        }
    }

    @Override
    public void focusLost(java.awt.event.FocusEvent evt) {
        if (txtNote.getText().trim().isEmpty()) {
            txtNote.setText(notePlaceholder);
            txtNote.setForeground(java.awt.Color.GRAY);
        }
    }
});

String note = txtNote.getText().trim();

if (note.equals("Note...")) {
    note = "";
}
        
        jTextField3.setText(pricePlaceholder);
jTextField3.setForeground(java.awt.Color.GRAY);
        
        txtWeight.setText(weightPlaceholder);
txtWeight.setForeground(java.awt.Color.GRAY);

txtWeight.addFocusListener(new java.awt.event.FocusAdapter() {
    @Override
    public void focusGained(java.awt.event.FocusEvent evt) {
        if (txtWeight.getText().equals(weightPlaceholder)) {
            txtWeight.setText("");
            txtWeight.setForeground(java.awt.Color.BLACK);
        }
    }

    @Override
    public void focusLost(java.awt.event.FocusEvent evt) {
        if (txtWeight.getText().trim().isEmpty()) {
            txtWeight.setText(weightPlaceholder);
            txtWeight.setForeground(java.awt.Color.GRAY);
        }
    }
});
        
        jButton1.addActionListener(new java.awt.event.ActionListener() {
    @Override
    public void actionPerformed(java.awt.event.ActionEvent evt) {
        cancelBooking();
    }
});
            // Create the calendar
    dateChooser = new JDateChooser();
    dateChooser.setDateFormatString("yyyy-MM-dd");
    dateChooser.setDate(new java.util.Date());

    // Hide the old date text field
    jTextField1.setVisible(false);

    // Add the calendar where the old date field was
    jPanel1.add(
        dateChooser,
        new org.netbeans.lib.awtextra.AbsoluteConstraints(
            131, 333, 298, 27
        )
    );

    jPanel1.revalidate();
    jPanel1.repaint();

    jTextField3.setEditable(false);

    txtWeight.getDocument().addDocumentListener(
        new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updatePrice();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updatePrice();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updatePrice();
            }
        }
    );

    updatePrice();

    }
    
private double getServiceRate() {
    String service = (String) jComboBox1.getSelectedItem();

    switch (service) {
        case "Wash": return 30.00;
        case "Fold": return 15.00;
        case "Dry Cleaning": return 100.00;
        case "Wash & Iron": return 50.00;
        case "Dry Clean & Iron": return 120.00;
        case "Wash & Dry Clean": return 100.00;
        case "Complete Care Package": return 20.00;
        default: return 0.00;
    

    }
}


private void updatePrice() {
    String text = txtWeight.getText().trim();

    if (text.isEmpty() || text.equals(weightPlaceholder)) {
        jTextField3.setText(pricePlaceholder);
        jTextField3.setForeground(java.awt.Color.GRAY);
        return;
    }

    try {
        double quantity = Double.parseDouble(text);

        if (quantity <= 0) {
            jTextField3.setText(pricePlaceholder);
            jTextField3.setForeground(java.awt.Color.GRAY);
            return;
        }

        double rate = getServiceRate();
        double total = quantity * rate;

        java.text.DecimalFormat df =
                new java.text.DecimalFormat("#,##0.00");

        jTextField3.setForeground(java.awt.Color.BLACK);
        jTextField3.setText(
            "₱" + df.format(rate)
            + " / kg | Total: ₱" + df.format(total)
        );

    } catch (NumberFormatException e) {
        jTextField3.setText(pricePlaceholder);
        jTextField3.setForeground(java.awt.Color.GRAY);
    }
}



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jLabel8 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtWeight = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtNote = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/abcde-removebg-preview (1).png"))); // NOI18N

        jButton9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton9.setForeground(new java.awt.Color(0, 153, 204));
        jButton9.setText("LOGOUT");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1244, 700));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton6.setForeground(new java.awt.Color(0, 153, 204));
        jButton6.setText("HOME");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 50, -1, -1));

        jButton7.setBackground(new java.awt.Color(0, 153, 204));
        jButton7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("BOOKING");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 50, -1, -1));

        jButton8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton8.setForeground(new java.awt.Color(0, 153, 204));
        jButton8.setText("HISTORY");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 50, -1, -1));

        jButton10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton10.setForeground(new java.awt.Color(0, 153, 204));
        jButton10.setText("LOGOUT");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 50, -1, -1));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pastel Blue Laundry Service Icon Set (3) (2).png"))); // NOI18N
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 126, -1, -1));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pastel Blue Laundry Service Icon Set (2) (2).png"))); // NOI18N
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 18, 88, -1));

        jLabel11.setFont(new java.awt.Font("Perpetua Titling MT", 1, 24)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Create NEW BOOKING");
        jLabel11.setToolTipText("");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(106, 36, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Enter the laundry details below.");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(106, 71, -1, -1));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("Price per kg | Total Amount");
        jLabel12.setToolTipText("");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 130, 280, -1));

        jComboBox1.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Wash", "Fold", "Dry Cleaning", "Wash & Iron", "Dry Clean & Iron", "Wash & Dry Clean", "Complete Care Package" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(131, 152, 298, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pastel Blue Laundry Service Icon Set (4) (2).png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(56, 217, 63, -1));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Laundry Weight (kg) / Quantity");
        jLabel13.setToolTipText("");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(131, 217, -1, -1));

        jTextField1.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(131, 333, 298, -1));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pastel Blue Laundry Service Icon Set (5) (1).png"))); // NOI18N
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(56, 302, -1, -1));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("Booking Date");
        jLabel14.setToolTipText("");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(131, 302, -1, -1));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel15.setText("Laundry Service");
        jLabel15.setToolTipText("");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(131, 126, 150, -1));

        txtWeight.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        txtWeight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtWeightActionPerformed(evt);
            }
        });
        jPanel1.add(txtWeight, new org.netbeans.lib.awtextra.AbsoluteConstraints(131, 250, 298, -1));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pastel Blue Laundry Service Icon Set (6) (1).png"))); // NOI18N
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 130, -1, -1));

        jTextField3.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 160, 298, -1));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pastel Blue Laundry Service Icon Set (7) (2).png"))); // NOI18N
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 190, -1, -1));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText("Note");
        jLabel17.setToolTipText("");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 200, 237, -1));

        txtNote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNoteActionPerformed(evt);
            }
        });
        jPanel1.add(txtNote, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 230, 294, 60));

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 153, 204));
        jButton1.setText("CANCEL");
        jButton1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 153, 204), 1, true));
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 330, 80, 30));

        jButton2.setBackground(new java.awt.Color(0, 153, 204));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("CREATE BOOKING");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 330, 140, 30));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 130, 900, 400));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel10.setText("EDEN'S LAUNDRY BOOKINGS SYSTEM");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, -1, -1));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/abcde-removebg-preview (1).png"))); // NOI18N
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 0, 140, 140));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Gemini_Generated_Image_t3fn88t3fn88t3fn (2).jpg"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1170, 560));

        pack();
    }// </editor-fold>                        

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        homepage1 home = new homepage1();
        home.setVisible(true);
        this.dispose();
    }                                        

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        Book_page booknow = new Book_page();
        booknow.setVisible(true);
        this.dispose();
    }                                        

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        BOOKKING bookings = new BOOKKING();
        bookings.setVisible(true);
        this.dispose();
    }                                        

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        logout out = new logout();
        out.setVisible(true);
        this.dispose();
    }                                        

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        logout out = new logout();
        out.setVisible(true);
        this.dispose();
    }                                         

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {                                           
    
    updatePrice();
    // TODO add your handling code here:
    }                                          

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void txtWeightActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
    }                                         

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void txtNoteActionPerformed(java.awt.event.ActionEvent evt) {                                        
        // TODO add your handling code here:
    }                                       

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                         

    int customerId = UserSession.userId;

    if (customerId == -1) {
        JOptionPane.showMessageDialog(
            this,
            "Please log in before creating a booking.",
            "Login Required",
            JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    String serviceName = (String) jComboBox1.getSelectedItem();

    double quantity;

    try {
        quantity = Double.parseDouble(txtWeight.getText().trim());

        if (quantity <= 0) {
            throw new NumberFormatException();
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(
            this,
            "Please enter a valid quantity greater than zero.",
            "Invalid Quantity",
            JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    java.util.Date selectedDate = dateChooser.getDate();

    if (selectedDate == null) {
        JOptionPane.showMessageDialog(
            this,
            "Please select a booking date.",
            "Missing Date",
            JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    java.sql.Date bookingDate = new java.sql.Date(selectedDate.getTime());

    String url = "jdbc:mysql://localhost:3306/laundry_booking_system";
    String dbUsername = "root";
    String dbPassword = "";

    String findServiceSql = "SELECT service_id, price FROM tbl_services WHERE service_name = ?";

    String insertSql = "INSERT INTO tbl_bookings " +
        "(user_id, service_id, quantity, total_price, booking_date, note) VALUES (?, ?, ?, ?, ?, ?)";

    try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
         PreparedStatement findService = conn.prepareStatement(findServiceSql)) {

        findService.setString(1, serviceName);

        try (ResultSet rs = findService.executeQuery()) {
            if (!rs.next()) {
                JOptionPane.showMessageDialog(
                    this,
                    "This service is unavailable or was not found.",
                    "Service Not Found",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            int serviceId = rs.getInt("service_id");
            BigDecimal rate = rs.getBigDecimal("price");
            BigDecimal total = rate.multiply(BigDecimal.valueOf(quantity));

            try (PreparedStatement insert = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {

                insert.setInt(1, customerId);
                insert.setInt(2, serviceId);
                insert.setBigDecimal(3, BigDecimal.valueOf(quantity));
                insert.setBigDecimal(4, total);
                insert.setDate(5, bookingDate);

                String note = txtNote.getText().trim();
                if (note.equals("Note...")) {
                    note = "";
                }
                insert.setString(6, note);

                int rows = insert.executeUpdate();

                if (rows > 0) {
                    int createdBookingId = -1;
                    ResultSet generatedKeys = insert.getGeneratedKeys();

                    if (generatedKeys.next()) {
                        createdBookingId = generatedKeys.getInt(1);
                    }

                    JOptionPane.showMessageDialog(
                        this,
                        "Booking created successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                    );

                    txtWeight.setText("");
                    jTextField3.setText("");
                    txtNote.setText("");
                    dateChooser.setDate(new java.util.Date());

                    Verified verify = new Verified(createdBookingId);
                    verify.setLocationRelativeTo(null);
                    verify.setVisible(true);
                    this.dispose();
                }
            }
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(
            this,
            "Unable to save booking:\n" + e.getMessage(),
            "Database Error",
            JOptionPane.ERROR_MESSAGE
        );
        e.printStackTrace();
    }




    // Next: validate form fields and save the booking.
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
            java.util.logging.Logger.getLogger(Book_page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Book_page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Book_page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Book_page.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Book_page().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField txtNote;
    private javax.swing.JTextField txtWeight;
    // End of variables declaration                   
}
