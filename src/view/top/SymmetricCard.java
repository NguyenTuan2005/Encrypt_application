package view.top;

import cipher.symmetric.modern.ModernSymmetricCipher;
import controller.EncryptionController;
import controller.strategy.ModernSymmetricControllerStrategy;
import view.bottom.BottomPanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class SymmetricCard extends JPanel {
    private JLabel lblAlgorithm, lblSecretKey, lblKeySize, lblIV;
    private JTextField tfSecretKey, tfIV;
    private JComboBox<String> cbAlgorithm;
    private JButton btnGenKey, btnImportKey, btnExportKey, btnGenIV, btnImportIV, btnExportIV;
    private ButtonGroup buttonGroup;
    private JRadioButton[] rbKeySizes;
    private JPanel keySizePanel;
    private JFileChooser fileChooser;
    private ModernSymmetricControllerStrategy controller = (ModernSymmetricControllerStrategy) EncryptionController.getInstance().get("Modern");

    public SymmetricCard() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel groupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblAlgorithm = new JLabel("Thuật toán");
        lblAlgorithm.setPreferredSize(new Dimension(100, 20));
        cbAlgorithm = new JComboBox<>(controller.getAlgorithms());
        groupPanel.add(lblAlgorithm);
        groupPanel.add(cbAlgorithm);
        add(groupPanel);

        initialKeySize();

        initialSecretKey();

        initialIV();

        addEvent();
    }

    private void addEvent() {
        fileChooser = new JFileChooser();
        btnGenKey.addActionListener(e -> {
            try {
                tfSecretKey.setText(controller.genKey());
            } catch (Exception ex) {
                BottomPanel.updateResult(ex.getMessage());
            }
        });

        btnGenIV.addActionListener(e -> {
            tfIV.setText(controller.genIV());
        });

        cbAlgorithm.addActionListener(e -> {
            String selected = (String) cbAlgorithm.getSelectedItem();
            controller.setModernSymmetric(selected);
            updateKeySize();
            tfSecretKey.setText("");
            tfIV.setText("");
        });

        btnExportKey.addActionListener(e -> {
            int result = fileChooser.showSaveDialog(SymmetricCard.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    controller.exportKey(file);
                } catch (Exception ex) {
                    BottomPanel.updateResult(ex.getMessage());
                }
            }
        });

        btnImportKey.addActionListener(e -> {
            int result = fileChooser.showOpenDialog(SymmetricCard.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    tfSecretKey.setText(controller.importKey(file));
                } catch (Exception ex) {
                    BottomPanel.updateResult(ex.getMessage());
                }
            }
        });

        btnExportIV.addActionListener(e -> {
            int result = fileChooser.showSaveDialog(SymmetricCard.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    controller.exportIV(file);
                } catch (Exception ex) {
                    BottomPanel.updateResult(ex.getMessage());
                }
            }
        });

        btnImportIV.addActionListener(e -> {
            int result = fileChooser.showOpenDialog(SymmetricCard.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    tfIV.setText(controller.importIV(file));
                } catch (Exception ex) {
                    BottomPanel.updateResult(ex.getMessage());
                }
            }
        });
    }

    private void updateKeySize() {
        String[] keySizes = controller.findKeySizes();

        keySizePanel.removeAll();
        buttonGroup = new ButtonGroup();
        lblKeySize = new JLabel("Kích thước khóa");
        lblKeySize.setPreferredSize(new Dimension(100, 20));
        keySizePanel.add(lblKeySize);

        rbKeySizes = new JRadioButton[keySizes.length];
        for (int i = 0; i < keySizes.length; i++) {
            rbKeySizes[i] = new JRadioButton(keySizes[i]);
        }

        for (int i = 0; i < rbKeySizes.length; i++) {
            JRadioButton rbSize = rbKeySizes[i];
            if (i == 0) rbSize.setSelected(true);
            buttonGroup.add(rbSize);
            keySizePanel.add(rbSize);
        }

        keySizePanel.revalidate();
        keySizePanel.repaint();
    }

    private void initialIV() {
        JPanel groupPanel = new JPanel();
        groupPanel.setLayout(new BoxLayout(groupPanel, BoxLayout.Y_AXIS));
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        Dimension d = panel.getPreferredSize();
        panel.setPreferredSize(new Dimension((int) d.getWidth(), 25));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        lblIV = new JLabel("  IV");
        lblIV.setPreferredSize(new Dimension(100, 20));
        tfIV = new JTextField("");
        tfIV.setEditable(false);
        panel.add(lblIV, BorderLayout.WEST);
        panel.add(tfIV, BorderLayout.CENTER);
        groupPanel.add(panel);

        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnGenIV = new JButton("Tạo");
        btnImportIV = new JButton("Chèn");
        btnExportIV = new JButton("Xuất");
        panel.add(Box.createHorizontalStrut(100));
        panel.add(btnGenIV);
        panel.add(btnImportIV);
        panel.add(btnExportIV);
        groupPanel.add(panel);
        add(groupPanel);
    }

    private void initialKeySize() {
        keySizePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblKeySize = new JLabel("Kích thước khóa");
        lblKeySize.setPreferredSize(new Dimension(100, 20));
        keySizePanel.add(lblKeySize);

        String[] keySizes = controller.findKeySizes();
        rbKeySizes = new JRadioButton[keySizes.length];
        for (int i = 0; i < keySizes.length; i++) {
            rbKeySizes[i] = new JRadioButton(keySizes[i]);
        }

        buttonGroup = new ButtonGroup();
        for (int i = 0; i < rbKeySizes.length; i++) {
            JRadioButton rbSize = rbKeySizes[i];
            if (i == 0) rbSize.setSelected(true);
            buttonGroup.add(rbSize);
            keySizePanel.add(rbSize);
        }
        add(keySizePanel);
    }

    public void initialSecretKey() {
        JPanel groupPanel = new JPanel();
        groupPanel.setLayout(new BoxLayout(groupPanel, BoxLayout.Y_AXIS));
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        Dimension d = panel.getPreferredSize();
        panel.setPreferredSize(new Dimension((int) d.getWidth(), 25));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        lblSecretKey = new JLabel("  Khóa");
        lblSecretKey.setPreferredSize(new Dimension(100, 20));
        tfSecretKey = new JTextField("");
        tfSecretKey.setEditable(false);
        panel.add(lblSecretKey, BorderLayout.WEST);
        panel.add(tfSecretKey, BorderLayout.CENTER);
        groupPanel.add(panel);

        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnGenKey = new JButton("Tạo");
        btnImportKey = new JButton("Chèn");
        btnExportKey = new JButton("Xuất");
        panel.add(Box.createHorizontalStrut(100));
        panel.add(btnGenKey);
        panel.add(btnImportKey);
        panel.add(btnExportKey);
        groupPanel.add(panel);
        add(groupPanel);
    }

    public void saveConfig(ModernSymmetricCipher modernSymmetricCipher) {
        int keySize = getSelectedKeySize();
        modernSymmetricCipher.setKeySize(keySize);
    }

    private int getSelectedKeySize() {
        for (JRadioButton rb : rbKeySizes) {
            if (rb.isSelected()) {
                return Integer.parseInt(rb.getText());
            }
        }
        return 128;
    }

    public void update(ModernSymmetricCipher modernSymmetricCipher) {
        tfSecretKey.setText(modernSymmetricCipher.getSecretKey());
        tfIV.setText(modernSymmetricCipher.getIV());
        String keySize = String.valueOf(modernSymmetricCipher.getKeySize());
        for (JRadioButton rb : rbKeySizes) {
            if (keySize.equals(rb.getText())) {
                rb.setSelected(true);
                return;
            }
        }
    }
}
