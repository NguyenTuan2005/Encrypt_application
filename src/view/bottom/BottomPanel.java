package view.bottom;

import controller.EncryptionController;
import view.center.CenterPanel;
import view.utils.ColorView;

import javax.crypto.IllegalBlockSizeException;
import javax.swing.*;
import java.awt.*;

public class BottomPanel extends JPanel {
    private JLabel lblResult;
    private JButton btnEncrypt, btnDecrypt;
    private static JTextArea txtResult;
    private JScrollPane scrollPane;
    private CenterPanel centerPanel;
    private EncryptionController controller = EncryptionController.getInstance();

    public BottomPanel(CenterPanel centerPanel) {
        this.centerPanel = centerPanel;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(ColorView.BORDER_COLOR));

        JPanel panel = new JPanel(new BorderLayout());
        lblResult = new JLabel("  Kết quả:");
        panel.add(lblResult, BorderLayout.WEST);

        JPanel btnPanel = new JPanel(new FlowLayout());
        btnEncrypt = new JButton("Mã hóa");
        btnDecrypt = new JButton("Giải mã");
        btnPanel.add(btnEncrypt);
        btnPanel.add(btnDecrypt);
        panel.add(btnPanel, BorderLayout.EAST);
        add(panel, BorderLayout.NORTH);

        txtResult = new JTextArea();
        txtResult.setPreferredSize(new Dimension(100, 100));
        txtResult.setEditable(false);

        scrollPane = new JScrollPane();
        scrollPane.setViewportView(txtResult);
        add(scrollPane, BorderLayout.CENTER);

        addEvent();
    }

    private void addEvent() {
        btnEncrypt.addActionListener(e -> {
            try {
                String data = centerPanel.getData();
                controller.encrypt(data);
            } catch (Exception ex) {
                txtResult.setText(ex.getMessage());
            }
        });

        btnDecrypt.addActionListener( e -> {
            try {
                String data = centerPanel.getData();
                controller.decrypt(data);
            } catch (Exception ex) {
                txtResult.setText(ex.getMessage());
            }
        });
    }

    public static void updateResult(String text) {
        txtResult.setText(text);
    }
}
