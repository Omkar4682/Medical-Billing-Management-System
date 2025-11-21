package medical.store.system;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {

    public Dashboard() {
        setTitle("Dashboard - Medical Billing System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen
        setLayout(new BorderLayout());

        
        ImageIcon bgIcon = new ImageIcon("D:\\Project\\medical.JPG");
        Image bgImg = bgIcon.getImage();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Image scaledImg = bgImg.getScaledInstance(screenSize.width, screenSize.height, Image.SCALE_SMOOTH);
        JLabel bgLabel = new JLabel(new ImageIcon(scaledImg));
        bgLabel.setLayout(new GridBagLayout()); // Center components
        add(bgLabel, BorderLayout.CENTER);

        //transparent panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // transparent background
        buttonPanel.setLayout(new GridLayout(7, 1, 15, 15)); // 7 buttons vertically with spacing
        buttonPanel.setPreferredSize(new Dimension(300, 500));

        //Buttons
        JButton addMedicineBtn = new JButton("Add Medicine");
        JButton viewMedicinesBtn = new JButton("View Medicines");
        JButton purchaseBtn = new JButton("Purchase");
        JButton sellBtn = new JButton("Sell");
        JButton invoiceBtn = new JButton("Invoice / Billing");
        JButton changePassBtn = new JButton("Change Password");
        JButton exitBtn = new JButton("Exit");

        // buttons style
        Font btnFont = new Font("Segoe UI", Font.BOLD, 18);
        JButton[] allBtns = { addMedicineBtn, viewMedicinesBtn, purchaseBtn, sellBtn, invoiceBtn, changePassBtn, exitBtn };
        for (JButton btn : allBtns) {
            btn.setFont(btnFont);
            btn.setFocusPainted(false);
            btn.setBackground(new Color(0, 102, 204));
            btn.setForeground(Color.WHITE);
            btn.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));

            // Hover effect
            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    btn.setBackground(new Color(21, 101, 192));
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btn.setBackground(new Color(0, 102, 204));
                }
            });

            buttonPanel.add(btn);
        }

        // Adding button panel to background label 
        bgLabel.add(buttonPanel, new GridBagConstraints());

        // ✅ Button actions
        addMedicineBtn.addActionListener(e -> new AddMedicine().setVisible(true));
        viewMedicinesBtn.addActionListener(e -> new ViewMedicines().setVisible(true));
        purchaseBtn.addActionListener(e -> new PurchaseFrame().setVisible(true));
        sellBtn.addActionListener(e -> new SalesFrame().setVisible(true));
        invoiceBtn.addActionListener(e -> new InvoiceGenerator().setVisible(true));
        changePassBtn.addActionListener(e -> new ChangePassword().setVisible(true));
        exitBtn.addActionListener(e -> System.exit(0));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Dashboard().setVisible(true));
    }
}
