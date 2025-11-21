package medical.store.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ChangePassword extends JFrame {
    private JPasswordField oldField, newField;
    private JButton changeBtn;

    public ChangePassword() {
        setTitle("Change Password");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(360, 180);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 8, 8));

        add(new JLabel("Old Password:"));
        oldField = new JPasswordField();
        add(oldField);

        add(new JLabel("New Password:"));
        newField = new JPasswordField();
        add(newField);

        add(new JLabel());
        changeBtn = new JButton("Change");
        add(changeBtn);

        changeBtn.addActionListener(e -> change());
    }

    private void change() {
        String oldP = new String(oldField.getPassword());
        String newP = new String(newField.getPassword());

        if (oldP.isEmpty() || newP.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Both fields required.");
            return;
        }

        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement check = con.prepareStatement("SELECT id FROM users WHERE password = ?");
            check.setString(1, oldP);
            ResultSet rs = check.executeQuery();
            if (!rs.next()) { JOptionPane.showMessageDialog(this, "Old password incorrect."); return; }

            PreparedStatement upd = con.prepareStatement("UPDATE users SET password = ? WHERE password = ?");
            upd.setString(1, newP);
            upd.setString(2, oldP);
            upd.executeUpdate();
            JOptionPane.showMessageDialog(this, "Password updated.");
            oldField.setText(""); newField.setText(""); 
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "DB Error: " + e.getMessage());
        }
    }
}
