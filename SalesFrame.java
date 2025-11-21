package medical.store.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class SalesFrame extends JFrame {
    private JTextField idField, qtyField;
    private JButton sellBtn;

    public SalesFrame() {
        setTitle("Sell (Decrease Stock)");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(360, 180);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 8, 8));

        add(new JLabel("Medicine ID:"));
        idField = new JTextField();
        add(idField);

        add(new JLabel("Quantity Sold:"));
        qtyField = new JTextField();
        add(qtyField);

        add(new JLabel());
        sellBtn = new JButton("Record Sale");
        add(sellBtn);

        sellBtn.addActionListener(e -> sell());
    }

    private void sell() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            int qty = Integer.parseInt(qtyField.getText().trim());

            try (Connection con = DBConnection.getConnection()) {
                // Check stock
                PreparedStatement check = con.prepareStatement("SELECT quantity FROM medicines WHERE id = ?");
                check.setInt(1, id);
                ResultSet rs = check.executeQuery();
                if (!rs.next()) { JOptionPane.showMessageDialog(this, "Medicine not found."); return; }
                int current = rs.getInt(1);
                if (qty > current) { JOptionPane.showMessageDialog(this, "Insufficient stock."); return; }

                PreparedStatement upd = con.prepareStatement("UPDATE medicines SET quantity = ? WHERE id = ?");
                upd.setInt(1, current - qty);
                upd.setInt(2, id);
                upd.executeUpdate();

                // Record sale
                PreparedStatement ins = con.prepareStatement("INSERT INTO sales(medicine_id, quantity) VALUES(?,?)");
                ins.setInt(1, id);
                ins.setInt(2, qty);
                ins.executeUpdate();

                JOptionPane.showMessageDialog(this, "Sale recorded.");
                idField.setText(""); qtyField.setText(""); 
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter valid numbers.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "DB Error: " + ex.getMessage());
        }
    }
}
