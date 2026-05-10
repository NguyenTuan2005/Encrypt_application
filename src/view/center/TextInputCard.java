package view.center;

import javax.swing.*;
import java.awt.*;

public class TextInputCard extends JPanel {
    private JLabel lblText;
    private JTextArea txtInput;
    private JScrollPane scrollPane;

    public TextInputCard(Runnable runnable) {
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout());

        lblText = new JLabel("Nhập văn bản");
        panel.add(lblText, BorderLayout.WEST);

        ButtonGroupPanel buttonGroupPanel = new ButtonGroupPanel();
        buttonGroupPanel.addActionRbFile(runnable);
        panel.add(buttonGroupPanel, BorderLayout.EAST);
        add(panel, BorderLayout.NORTH);

        txtInput = new JTextArea();
        txtInput.setPreferredSize(new Dimension(100, 100));

        scrollPane = new JScrollPane();
        scrollPane.setViewportView(txtInput);
        add(scrollPane, BorderLayout.CENTER);
    }
}
