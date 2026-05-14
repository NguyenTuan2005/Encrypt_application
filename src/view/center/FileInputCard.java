package view.center;

import view.EncryptApplication;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class FileInputCard extends JPanel {
    private JLabel lblFile;
    private JTextField tfFilePath;
    private JButton btnBrowseFile;
    private JFileChooser fileChooser;

    public FileInputCard(Runnable runnable) {
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(Box.createVerticalStrut(30), BorderLayout.NORTH);
        lblFile = new JLabel("  Thêm tệp để bắt đầu");
        panel.add(lblFile, BorderLayout.WEST);

        ButtonGroupPanel buttonGroupPanel = new ButtonGroupPanel();
        buttonGroupPanel.addActionRbText(runnable);
        panel.add(buttonGroupPanel, BorderLayout.EAST);
        add(panel, BorderLayout.NORTH);

        panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        btnBrowseFile = new JButton("Tìm kiếm");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(btnBrowseFile, gbc);

        tfFilePath = new JTextField(30);
        tfFilePath.setEditable(false);
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panel.add(tfFilePath, gbc);
        add(panel, BorderLayout.CENTER);
        addEvent();
    }

    private void addEvent() {
        fileChooser = new JFileChooser();
        btnBrowseFile.addActionListener(e -> {
            int result = fileChooser.showOpenDialog(FileInputCard.this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                tfFilePath.setText(file.getPath());
            }
        });
    }

    public String getData() {
        return tfFilePath.getText();
    }
}
