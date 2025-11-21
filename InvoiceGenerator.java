package medical.store.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceGenerator extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JButton generateBtn, refreshBtn;
    private JLabel infoLabel;

    public InvoiceGenerator() {
        setTitle("Invoice Generator");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));

        // Columns: Select, ID, Name, Company, Price, Stock, QtyToSell
        model = new DefaultTableModel(new Object[]{"Select", "ID", "Name", "Company", "Price", "Stock", "Qty to sell"}, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) return Boolean.class;
                if (columnIndex == 1 || columnIndex == 5 || columnIndex == 6) return Integer.class;
                if (columnIndex == 4) return Double.class;
                return String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                // only Select (0) and QtyToSell (6) are editable
                return column == 0 || column==6;
            }
        };

        table = new JTable(model);
        table.setRowHeight(26);
        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        // Top panel - info
        JPanel top = new JPanel(new BorderLayout());
        infoLabel = new JLabel("Medical Biliing System ");
        top.add(infoLabel, BorderLayout.CENTER);
        add(top, BorderLayout.NORTH);

        // Bottom panel - buttons
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        refreshBtn = new JButton("Refresh List");
        generateBtn = new JButton("Generate Invoice");
        bottom.add(refreshBtn);
        bottom.add(generateBtn);
        add(bottom, BorderLayout.SOUTH);

        // Button actions
        refreshBtn.addActionListener(e -> loadMedicines());
        generateBtn.addActionListener(e -> generateInvoice());

        // initial load
        loadMedicines();
    }

    private void loadMedicines() {
        model.setRowCount(0);
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT id, name, company, price, quantity FROM medicines");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String company = rs.getString("company");
                double price = rs.getDouble("price");
                int stock = rs.getInt("quantity");
                // default Qty to sell = 0
                model.addRow(new Object[]{Boolean.FALSE, id, name, company, price, stock, 0});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading medicines: " + ex.getMessage());
        }
    }

    // small helper class to pass to InvoiceFrame
    public static class InvoiceItem {
        public final int medicineId;
        public final String name;
        public final int qty;
        public final double price;
        public final double subtotal;
        public InvoiceItem(int medicineId, String name, int qty, double price) {
            this.medicineId = medicineId;
            this.name = name;
            this.qty = qty;
            this.price = price;
            this.subtotal = price * qty;
        }
    }

    private void generateInvoice() {
        // collect selected rows
        List<InvoiceItem> items = new ArrayList<>();
        for (int r = 0; r < model.getRowCount(); r++) {
            Boolean sel = (Boolean) model.getValueAt(r, 0);
            if (sel != null && sel) {
                int id = (Integer) model.getValueAt(r, 1);
                String name = (String) model.getValueAt(r, 2);
                double price = (Double) model.getValueAt(r, 4);
                Object qtyObj = model.getValueAt(r, 6);
                int qty = 0;
                if (qtyObj instanceof Integer) qty = (Integer) qtyObj;
                else {
                    try { qty = Integer.parseInt(qtyObj.toString()); }
                    catch (Exception ex) { qty = 0; }
                }
                if (qty <= 0) {
                    JOptionPane.showMessageDialog(this, "Enter a valid quantity for: " + name);
                    return;
                }
                int stock = (Integer) model.getValueAt(r, 5);
                if (qty > stock) {
                    JOptionPane.showMessageDialog(this, "Insufficient stock for: " + name + " (available: " + stock + ")");
                    return;
                }
                items.add(new InvoiceItem(id, name, qty, price));
            }
        }

        if (items.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No medicines selected.");
            return;
        }

        // Save invoice and items in DB inside transaction
        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);
            try {
                // compute total
                double total = 0.0;
                for (InvoiceItem it : items) total += it.subtotal;

                // insert invoice header
                PreparedStatement invStmt = con.prepareStatement("INSERT INTO invoice(total_amount) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
                invStmt.setDouble(1, total);
                invStmt.executeUpdate();
                ResultSet keys = invStmt.getGeneratedKeys();
                keys.next();
                int invoiceId = keys.getInt(1);

                // prepare statements
                PreparedStatement itemStmt = con.prepareStatement(
                        "INSERT INTO invoice_items(invoice_id, medicine_id, quantity, price, subtotal) VALUES(?,?,?,?,?)");
                PreparedStatement updStock = con.prepareStatement(
                        "UPDATE medicines SET quantity = quantity - ? WHERE id = ?");

                for (InvoiceItem it : items) {
                    itemStmt.setInt(1, invoiceId);
                    itemStmt.setInt(2, it.medicineId);
                    itemStmt.setInt(3, it.qty);
                    itemStmt.setDouble(4, it.price);
                    itemStmt.setDouble(5, it.subtotal);
                    itemStmt.addBatch();

                    updStock.setInt(1, it.qty);
                    updStock.setInt(2, it.medicineId);
                    updStock.addBatch();
                }

                itemStmt.executeBatch();
                updStock.executeBatch();

                con.commit();

                // success: open invoice view
                JOptionPane.showMessageDialog(this, "Invoice created successfully (ID: " + invoiceId + ").");
                // refresh table stock view
                loadMedicines();

                // open viewer
                InvoiceFrame invoiceFrame = new InvoiceFrame(invoiceId, items);
                invoiceFrame.setVisible(true);

            } catch (SQLException ex) {
                con.rollback();
                throw ex;
            } finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error saving invoice: " + ex.getMessage());
        }
    }

    // Simple main for quick testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InvoiceGenerator().setVisible(true));
    }
}
