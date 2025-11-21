package medical.store.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewMedicines extends JFrame {
    private JTable table;
    private DefaultTableModel model;

    public ViewMedicines() {
        setTitle("Medicines");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 380);
        setLocationRelativeTo(null);
        model = new DefaultTableModel(new String[]{ "ID", "Name", "Company", "Price", "Qty" }, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);
        loadData();
    }

    private void loadData() {
        model.setRowCount(0);
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT id, name, company, price, quantity FROM medicines")) {
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getInt(5)
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}
