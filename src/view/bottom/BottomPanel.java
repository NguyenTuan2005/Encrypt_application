package view.bottom;

import view.utils.ColorView;

import javax.swing.*;
import java.awt.*;

public class BottomPanel extends JPanel {
    private JLabel lblResult;
    private JButton btnEncrypt, btnDecrypt;
    private JTextArea txtResult;
    private JScrollPane scrollPane;

    public BottomPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(ColorView.BORDER_COLOR));

        JPanel panel = new JPanel(new BorderLayout());
        lblResult = new JLabel("Kết quả:");
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
    }
}
