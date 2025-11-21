package medical.store.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class PurchaseFrame extends JFrame {
    private JTextField idField, qtyField;
    private JButton buyBtn;

    public PurchaseFrame() {
        setTitle("Purchase (Increase Stock)");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(360, 180);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 8, 8));

        add(new JLabel("Medicine ID:"));
        idField = new JTextField();
        add(idField);

        add(new JLabel("Quantity Purchased:"));
        qtyField = new JTextField();
        add(qtyField);

        add(new JLabel());
        buyBtn = new JButton("Record Purchase");
        add(buyBtn);

        buyBtn.addActionListener(e -> purchase());
    }

    private void purchase() {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            int qty = Integer.parseInt(qtyField.getText().trim());

            try (Connection con = DBConnection.getConnection()) {
                // Update stock
                PreparedStatement check = con.prepareStatement("SELECT quantity FROM medicines WHERE id = ?");
                check.setInt(1, id);
                ResultSet rs = check.executeQuery();
                if (!rs.next()) { JOptionPane.showMessageDialog(this, "Medicine not found."); return; }
                int current = rs.getInt(1);

                PreparedStatement upd = con.prepareStatement("UPDATE medicines SET quantity = ? WHERE id = ?");
                upd.setInt(1, current + qty);
                upd.setInt(2, id);
                upd.executeUpdate();

                // Record purchase
                PreparedStatement ins = con.prepareStatement("INSERT INTO purchases(medicine_id, quantity) VALUES(?,?)");
                ins.setInt(1, id);
                ins.setInt(2, qty);
                ins.executeUpdate();

                JOptionPane.showMessageDialog(this, "Purchase recorded.");
                idField.setText(""); qtyField.setText(""); 
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter valid numbers.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "DB Error: " + ex.getMessage());
        }
    }
}
