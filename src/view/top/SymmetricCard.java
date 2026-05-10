package view.top;

import javax.swing.*;
import java.awt.*;

public class SymmetricCard extends JPanel {
    private JLabel lblAlgorithm, lblMode, lblPadding, lblSecretKey, lblKeySize, lblIV;
    private JTextField tfSecretKey, tfIV;
    private JComboBox<String> cbAlgorithm, cbMode, cbPadding;
    private JButton btnGenKey, btnImportKey, btnExportKey, btnGenIV, btnImportIV;
    private ButtonGroup buttonGroup;
    private JPanel keySizePanel;

    public SymmetricCard() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel groupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblAlgorithm = new JLabel("Thuật toán");
        lblAlgorithm.setPreferredSize(new Dimension(100, 20));
        cbAlgorithm = new JComboBox<>(new String[]{"AES"});
        groupPanel.add(lblAlgorithm);
        groupPanel.add(cbAlgorithm);
        add(groupPanel);

        groupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        lblMode = new JLabel("Mode");
        lblMode.setPreferredSize(new Dimension(100, 20));
        cbMode = new JComboBox<>(new String[]{"CBC"});
        groupPanel.add(lblMode);
        groupPanel.add(cbMode);
        add(groupPanel);

        groupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblPadding = new JLabel("Padding");
        lblPadding.setPreferredSize(new Dimension(100, 20));
        cbPadding = new JComboBox<>(new String[]{"PKCS5Padding"});
        groupPanel.add(lblPadding);
        groupPanel.add(cbPadding);
        add(groupPanel);

        initialSecretKey();

        initialKeySize();

        initialIV();
    }

    private void initialIV() {
        JPanel groupPanel = new JPanel();
        groupPanel.setLayout(new BoxLayout(groupPanel, BoxLayout.Y_AXIS));
        JPanel panel = new JPanel(new BorderLayout(10, 0));
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
        panel.add(Box.createHorizontalStrut(100));
        panel.add(btnGenIV);
        panel.add(btnImportIV);
        groupPanel.add(panel);
        add(groupPanel);
    }

    private void initialKeySize() {
        keySizePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblKeySize = new JLabel("Kích thước khóa");
        lblKeySize.setPreferredSize(new Dimension(100, 20));
        keySizePanel.add(lblKeySize);

        String[] list = new String[]{"128"};
        buttonGroup = new ButtonGroup();
        for (int i = 0; i < list.length; i++) {
            JRadioButton rbSize = new JRadioButton(list[i]);
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
        panel.add(Box.createHorizontalStrut(100));
        panel.add(btnGenKey);
        panel.add(btnImportKey);
        groupPanel.add(panel);
        add(groupPanel);
    }
}
