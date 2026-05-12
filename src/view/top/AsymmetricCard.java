package view.top;

import javax.swing.*;
import java.awt.*;

public class AsymmetricCard extends JPanel {
    private CardLayout layout;
    private JPanel cardPanel;
    private Header header;
    private BasicPanel basicPanel;
    private AdvancePanel advancePanel;

    public AsymmetricCard() {
        setLayout(new BorderLayout());
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
        private JRadioButton radioButton;
        private ButtonGroup buttonGroup;
        private JTextField tfPublicKey, tfPrivateKey;
        private JButton btnGenKeyPair, btnImportPublicKey, btnExportPublicKey, btnImportPrivateKey, btnExportPrivateKey;

        public BasicPanel() {
            setLayout(new BoxLayout(BasicPanel.this, BoxLayout.Y_AXIS));

            JPanel groupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            lblTransformation = new JLabel("Thuật toán");
            lblTransformation.setPreferredSize(new Dimension(100, 20));

            cbTransformation = new JComboBox<>(new String[]{"RSA/ECB/PKCS1Padding"});
            groupPanel.add(lblTransformation);
            groupPanel.add(cbTransformation);
            add(groupPanel);

            groupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            lblKeySize = new JLabel("Kích thước khóa");
            lblKeySize.setPreferredSize(new Dimension(100, 20));

            JPanel buttonPanel = new JPanel();
            buttonGroup = new ButtonGroup();
            radioButton = new JRadioButton("1024");
            radioButton.setSelected(true);
            buttonGroup.add(radioButton);
            buttonPanel.add(radioButton);

            radioButton = new JRadioButton("2048");
            buttonGroup.add(radioButton);
            buttonPanel.add(radioButton);
            groupPanel.add(lblKeySize);
            groupPanel.add(buttonPanel);
            add(groupPanel);

            initKeyPair();
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
    }

    public class AdvancePanel extends JPanel {
        private SymmetricCard symmetricCard;

        public AdvancePanel() {
            setLayout(new BoxLayout(AdvancePanel.this, BoxLayout.Y_AXIS));
            symmetricCard = new SymmetricCard();
            add(symmetricCard);
        }
    }
}
