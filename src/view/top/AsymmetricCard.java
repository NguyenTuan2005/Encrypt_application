package view.top;

import cipher.asymmetric.AsymmetricCipher;
import controller.EncryptionController;
import controller.strategy.AsymmetricControllerStrategy;
import controller.strategy.ModernSymmetricControllerStrategy;
import view.bottom.BottomPanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class AsymmetricCard extends JPanel {
    private CardLayout layout;
    private JPanel cardPanel;
    private Header header;
    private BasicPanel basicPanel;
    private AdvancePanel advancePanel;
    private AsymmetricControllerStrategy controller = (AsymmetricControllerStrategy) EncryptionController.getInstance().get("Asymmetric");

    public AsymmetricCard() {
        setLayout(new BorderLayout());
        controller.setAsymmetricCard(this);
        header = new Header();
        add(header, BorderLayout.NORTH);

        cardPanel = new JPanel();
        layout = new CardLayout();
        cardPanel.setLayout(layout);
        basicPanel = new BasicPanel();
        advancePanel = new AdvancePanel();

        cardPanel.add(basicPanel, "BASIC");
        cardPanel.add(advancePanel, "ADVANCE");
        add(cardPanel, BorderLayout.CENTER);
    }

    public void showCard(String cardName) {
        layout.show(cardPanel, cardName);
    }

    public void saveConfig(AsymmetricCipher asymmetricCipher) {
        int keySize = getSelectedKeySize();
        asymmetricCipher.setKeySize(keySize);
    }

    private int getSelectedKeySize() {
        for (JRadioButton rb : basicPanel.rbKeySizes) {
            if (rb.isSelected()) {
                return Integer.parseInt(rb.getText());
            }
        }
        return 2048;
    }

    public class Header extends JPanel {
        private JLabel lblConfig;
        private JComboBox<String> cbConfig;

        public Header() {
            setLayout(new FlowLayout(FlowLayout.LEFT));
            lblConfig = new JLabel("Chế độ chỉnh sửa");
            cbConfig = new JComboBox<>(new String[]{"Cơ bản", "Kết hợp đối xứng"});

            add(lblConfig);
            add(cbConfig);
            addConfigEvent();
        }

        private void addConfigEvent() {
            cbConfig.addActionListener(e -> {
                String selected = (String) cbConfig.getSelectedItem();

                switch (selected) {
                    case "Cơ bản":
                        showCard("BASIC");
                        break;
                    case "Kết hợp đối xứng":
                        showCard("ADVANCE");
                        break;
                }
            });
        }
    }

    public class BasicPanel extends JPanel {
        private JLabel lblTransformation, lblKeySize, lblKeyPair, lblPublicKey, lblPrivateKey;
        private JComboBox<String> cbTransformation;
        private JRadioButton[] rbKeySizes;
        private ButtonGroup buttonGroup;
        private JTextField tfPublicKey, tfPrivateKey;
        private JButton btnGenKeyPair, btnImportPublicKey, btnExportPublicKey, btnImportPrivateKey, btnExportPrivateKey;
        private JFileChooser fileChooser;

        public BasicPanel() {
            setLayout(new BoxLayout(BasicPanel.this, BoxLayout.Y_AXIS));

            JPanel groupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            lblTransformation = new JLabel("Thuật toán");
            lblTransformation.setPreferredSize(new Dimension(100, 20));

            cbTransformation = new JComboBox<>(controller.getTransformations());
            groupPanel.add(lblTransformation);
            groupPanel.add(cbTransformation);
            add(groupPanel);

            initKeySize();
            initKeyPair();
            addEvents();
        }

        private void addEvents() {
            fileChooser = new JFileChooser();
            cbTransformation.addActionListener(e -> {
                String selected = (String) cbTransformation.getSelectedItem();
                controller.setAsymmetricCipher(selected);
                tfPrivateKey.setText("");
                tfPublicKey.setText("");
            });

            btnGenKeyPair.addActionListener(e -> {
                try {
                    String[] keypair = controller.genKeyPair();
                    tfPublicKey.setText(keypair[0]);
                    tfPrivateKey.setText(keypair[1]);
                } catch (NoSuchAlgorithmException ex) {
                    BottomPanel.updateResult(ex.getMessage());
                }
            });

            btnExportPublicKey.addActionListener(e -> {
                int result = fileChooser.showSaveDialog(BasicPanel.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        controller.exportPublicKey(file);
                    } catch (Exception ex) {
                        BottomPanel.updateResult(ex.getMessage());
                    }
                }
            });

            btnImportPublicKey.addActionListener(e -> {
                int result = fileChooser.showOpenDialog(BasicPanel.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        tfPublicKey.setText(controller.importPublicKey(file));
                    } catch (Exception ex) {
                        BottomPanel.updateResult(ex.getMessage());
                    }
                }
            });

            btnExportPrivateKey.addActionListener(e -> {
                int result = fileChooser.showSaveDialog(BasicPanel.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        controller.exportPrivateKey(file);
                    } catch (Exception ex) {
                        BottomPanel.updateResult(ex.getMessage());
                    }
                }
            });

            btnImportPrivateKey.addActionListener(e -> {
                int result = fileChooser.showOpenDialog(BasicPanel.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        tfPrivateKey.setText(controller.importPrivateKey(file));
                    } catch (Exception ex) {
                        BottomPanel.updateResult(ex.getMessage());
                    }
                }
            });
        }

        private void initKeyPair() {
            JPanel groupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            lblKeyPair = new JLabel("Cặp khóa");
            lblKeyPair.setPreferredSize(new Dimension(100, 20));

            btnGenKeyPair = new JButton("Tạo cặp khóa");
            groupPanel.add(lblKeyPair);
            groupPanel.add(btnGenKeyPair);
            add(groupPanel);

            groupPanel = new JPanel(new BorderLayout(10,0));
            Dimension d = groupPanel.getPreferredSize();
            groupPanel.setPreferredSize(new Dimension((int) d.getWidth(), 25));
            groupPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
            lblPublicKey = new JLabel("  Khóa công khai");
            lblPublicKey.setPreferredSize(new Dimension(100, 20));

            tfPublicKey = new JTextField("");
            tfPublicKey.setEditable(false);
            groupPanel.add(lblPublicKey, BorderLayout.WEST);
            groupPanel.add(tfPublicKey, BorderLayout.CENTER);
            add(groupPanel);

            groupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            groupPanel.add(Box.createHorizontalStrut(100));
            btnImportPublicKey = new JButton("Chèn");
            btnExportPublicKey = new JButton("Xuất");
            groupPanel.add(btnImportPublicKey);
            groupPanel.add(btnExportPublicKey);
            add(groupPanel);

            groupPanel = new JPanel(new BorderLayout(10, 0));
            d = groupPanel.getPreferredSize();
            groupPanel.setPreferredSize(new Dimension((int) d.getWidth(), 25));
            groupPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
            lblPrivateKey = new JLabel("  Khóa riêng tư");
            lblPrivateKey.setPreferredSize(new Dimension(100, 20));

            tfPrivateKey = new JTextField("");
            tfPrivateKey.setEditable(false);
            groupPanel.add(lblPrivateKey, BorderLayout.WEST);
            groupPanel.add(tfPrivateKey, BorderLayout.CENTER);
            add(groupPanel);

            groupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            groupPanel.add(Box.createHorizontalStrut(100));
            btnImportPrivateKey = new JButton("Chèn");
            btnExportPrivateKey = new JButton("Xuất");
            groupPanel.add(btnImportPrivateKey);
            groupPanel.add(btnExportPrivateKey);
            add(groupPanel);
        }

        private void initKeySize() {
            String[] keySizes = controller.findKeySizes();

            JPanel groupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            buttonGroup = new ButtonGroup();
            lblKeySize = new JLabel("Kích thước khóa");
            lblKeySize.setPreferredSize(new Dimension(100, 20));
            groupPanel.add(lblKeySize);

            rbKeySizes = new JRadioButton[keySizes.length];
            for (int i = 0; i < keySizes.length; i++) {
                rbKeySizes[i] = new JRadioButton(keySizes[i]);
            }

            for (int i = 0; i < rbKeySizes.length; i++) {
                JRadioButton rbSize = rbKeySizes[i];
                if (i == 0) rbSize.setSelected(true);
                buttonGroup.add(rbSize);
                groupPanel.add(rbSize);
            }
            add(groupPanel);
        }
    }

    public class AdvancePanel extends JPanel {
        private SymmetricCard symmetricCard;

        public AdvancePanel() {
            setLayout(new BoxLayout(AdvancePanel.this, BoxLayout.Y_AXIS));
            symmetricCard = new SymmetricCard();
            ModernSymmetricControllerStrategy.setView(symmetricCard);
            add(symmetricCard);
        }
    }
}
