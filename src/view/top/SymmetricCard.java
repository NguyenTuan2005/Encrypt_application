package view.top;

import cipher.symmetric.modern.ModernSymmetric;
import controller.EncryptionController;
import controller.strategy.ModernSymmetricControllerStrategy;
import view.bottom.BottomPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class SymmetricCard extends JPanel {
    private JLabel lblAlgorithm, lblSecretKey, lblKeySize, lblIV;
    private JTextField tfSecretKey, tfIV;
    private JComboBox<String> cbAlgorithm;
    private JButton btnGenKey, btnImportKey, btnExportKey, btnGenIV, btnImportIV, btnExportIV;
    private ButtonGroup buttonGroup;
    private JRadioButton[] rbKeySizes;
    private JPanel keySizePanel;
    private ModernSymmetricControllerStrategy controller = (ModernSymmetricControllerStrategy) EncryptionController.getInstance().get("Modern");

    public SymmetricCard() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        controller.setView(this);

        JPanel groupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblAlgorithm = new JLabel("Thuật toán");
        lblAlgorithm.setPreferredSize(new Dimension(100, 20));
        cbAlgorithm = new JComboBox<>(controller.getAlgorithms());
        groupPanel.add(lblAlgorithm);
        groupPanel.add(cbAlgorithm);
        add(groupPanel);

        initialSecretKey();

        initialKeySize();

        initialIV();

        addEvent();
    }

    private void addEvent() {
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
            updateKeySize(selected);
        });
    }

    private void updateKeySize(String algorithm) {
        String[] keySizes = controller.findKeySizes(algorithm);

        keySizePanel.removeAll();
        buttonGroup = new ButtonGroup();
        lblKeySize = new JLabel("Kích thước khóa");
        lblKeySize.setPreferredSize(new Dimension(100, 20));
        keySizePanel.add(lblKeySize);

        rbKeySizes = Arrays.stream(keySizes)
                .map(s -> new JRadioButton(s))
                .toArray(JRadioButton[]::new);

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

        rbKeySizes = Arrays.stream(controller.getKeySizes())
                .map(s -> new JRadioButton(s))
                .toArray(JRadioButton[]::new);
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

    public void saveConfig(ModernSymmetric modernSymmetric) {
        int keySize = getSelectedKeySize();
        modernSymmetric.setKeySize(keySize);
    }

    private int getSelectedKeySize() {
        for (JRadioButton rb : rbKeySizes) {
            if (rb.isSelected()) {
                return Integer.parseInt(rb.getText());
            }
        }
        return 128;
    }
}
