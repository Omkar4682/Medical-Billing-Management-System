package medical.store.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AddMedicine extends JFrame {
    private JTextField nameField, companyField, priceField, qtyField;
    private JButton addBtn;

    public AddMedicine() {
        setTitle("Add Medicine");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(420, 260);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 8, 8));

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Company:"));
        companyField = new JTextField();
        add(companyField);

        add(new JLabel("Price:"));
        priceField = new JTextField();
        add(priceField);

        add(new JLabel("Quantity:"));
        qtyField = new JTextField();
        add(qtyField);

        add(new JLabel());
        addBtn = new JButton("Add");
        add(addBtn);

        addBtn.addActionListener(e -> addMedicine());
    }

    private void addMedicine() {
        String name = nameField.getText().trim();
        String company = companyField.getText().trim();
        String priceStr = priceField.getText().trim();
        String qtyStr = qtyField.getText().trim();

        if (name.isEmpty() || company.isEmpty() || priceStr.isEmpty() || qtyStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return;
        }

        try {
            double price = Double.parseDouble(priceStr);
            int qty = Integer.parseInt(qtyStr);

            try (Connection con = DBConnection.getConnection()) {
                PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO medicines(name, company, price, quantity) VALUES(?,?,?,?)"
                );
                ps.setString(1, name);
                ps.setString(2, company);
                ps.setDouble(3, price);
                ps.setInt(4, qty);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Medicine added.");
                nameField.setText(""); companyField.setText(""); priceField.setText(""); qtyField.setText(""); 
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Price and Quantity must be numbers.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
