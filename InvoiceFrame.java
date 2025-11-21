package medical.store.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class InvoiceFrame extends JFrame {
    private DefaultTableModel model;
    private JLabel totalLabel;

    // InvoiceItem is expected from InvoiceGenerator.InvoiceItem
    public InvoiceFrame(int invoiceId, List<InvoiceGenerator.InvoiceItem> items) {
        setTitle("Invoice - ID: " + invoiceId);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        model = new DefaultTableModel(new Object[]{"Name", "Quantity", "Price", "Subtotal"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // view only
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(24);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout());
        totalLabel = new JLabel("Total: ₹0.00");
        totalLabel.setFont(totalLabel.getFont().deriveFont(Font.BOLD, 16f));
        bottom.add(totalLabel, BorderLayout.EAST);

        // optional header panel with invoice info
        JPanel header = new JPanel(new GridLayout(2, 1));
        header.add(new JLabel("Medical Store Invoice", SwingConstants.CENTER));
        header.add(new JLabel("Invoice ID: " + invoiceId + "    Date: " + java.time.LocalDateTime.now().toString(), SwingConstants.CENTER));
        add(header, BorderLayout.NORTH);

        add(bottom, BorderLayout.SOUTH);

        // populate rows
        double total = 0.0;
        for (InvoiceGenerator.InvoiceItem it : items) {
            model.addRow(new Object[]{it.name, it.qty, it.price, it.subtotal});
            total += it.subtotal;
        }
        totalLabel.setText(String.format("Total: ₹%.2f", total));
    }
}
