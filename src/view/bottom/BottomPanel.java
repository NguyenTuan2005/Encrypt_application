package view.bottom;

import controller.EncryptionController;
import view.center.CenterPanel;
import view.utils.ColorView;

import javax.crypto.IllegalBlockSizeException;
import javax.swing.*;
import java.awt.*;

public class BottomPanel extends JPanel {
    private JLabel lblResult;
    private JButton btnEncrypt, btnDecrypt, btnGenHash;
    private static JTextArea txtResult;
    private JScrollPane scrollPane;
    private CenterPanel centerPanel;
    private static JPanel currentPanel, cipherPanel, hashPanel;
    private EncryptionController controller = EncryptionController.getInstance();

    public BottomPanel(CenterPanel centerPanel) {
        this.centerPanel = centerPanel;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(ColorView.BORDER_COLOR));

        currentPanel = new JPanel(new BorderLayout());
        lblResult = new JLabel("  Kết quả:");
        currentPanel.add(lblResult, BorderLayout.WEST);

        cipherPanel = new JPanel(new FlowLayout());
        btnEncrypt = new JButton("Mã hóa");
        btnDecrypt = new JButton("Giải mã");
        cipherPanel.add(btnEncrypt);
        cipherPanel.add(btnDecrypt);
        currentPanel.add(cipherPanel, BorderLayout.EAST);
        add(currentPanel, BorderLayout.NORTH);

        hashPanel = new JPanel();
        btnGenHash = new JButton(" Tạo mã băm");
        hashPanel.add(btnGenHash);

        txtResult = new JTextArea();
        txtResult.setPreferredSize(new Dimension(100, 100));
        txtResult.setEditable(false);

        scrollPane = new JScrollPane();
        scrollPane.setViewportView(txtResult);
        add(scrollPane, BorderLayout.CENTER);

        addEvent();
    }

    public static void toggleHash(boolean toggle) {
        if (toggle) {
            currentPanel.remove(cipherPanel);
            currentPanel.add(hashPanel, BorderLayout.EAST);
        } else {
            currentPanel.remove(hashPanel);
            currentPanel.add(cipherPanel, BorderLayout.EAST);
        }
        currentPanel.revalidate();
        currentPanel.repaint();
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

        btnGenHash.addActionListener(e -> {
            try {
                String data = centerPanel.getData();
                controller.genHash(data);
            } catch (Exception ex) {
                txtResult.setText(ex.getMessage());
            }
        });
    }

    public static void updateResult(String text) {
        txtResult.setText(text);
    }
}
