package view.top;

import model.ILanguageModel;
import model.Observer;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;

public class TraditionalSymmetricCard extends JPanel implements Observer {
    private CardLayout layout;
    private Header header;
    private JPanel panel;
    private CaesarPanel caesarPanel;
    private SubstitutionPanel substitutionPanel;
    private AffinePanel affinePanel;
    private ILanguageModel language;

    public TraditionalSymmetricCard(ILanguageModel language) {
        this.language = language;
        setLayout(new BorderLayout());
        header = new Header();
        add(header, BorderLayout.NORTH);

        panel = new JPanel();
        layout = new CardLayout();
        panel.setLayout(layout);
        caesarPanel = new CaesarPanel();
        substitutionPanel = new SubstitutionPanel();
        affinePanel = new AffinePanel();

        panel.add(caesarPanel, "CAESAR");
        panel.add(substitutionPanel, "SUB");
        panel.add(affinePanel, "AFFINE");
        add(panel, BorderLayout.CENTER);
    }

    public void showCard(String cardName) {
        layout.show(panel, cardName);
    }

    @Override
    public void update() {
        caesarPanel.update();
        affinePanel.update();
    }

    class Header extends JPanel {
        private JLabel lblMethod;
        private JComboBox<String> cbMethod;

        public Header() {
            setLayout(new FlowLayout(FlowLayout.LEFT));

            lblMethod = new JLabel("Phương pháp");
            lblMethod.setPreferredSize(new Dimension(100, 20));
            add(lblMethod);

            cbMethod = new JComboBox<>(new String[]{"Mã hóa dịch chuyển", "Mã hóa thay thế", "Affine", "Vigenere", "Hill", "Mã hóa hoán vị"});
            cbMethod.addActionListener(e -> {
                String selected = (String) cbMethod.getSelectedItem();

                switch (selected) {
                    case "Mã hóa dịch chuyển":
                        showCard("CAESAR");
                        break;
                    case "Mã hóa thay thế":
                        showCard("SUB");
                        break;
                    case "Affine":
                        showCard("AFFINE");
                        break;
                }
            });
            add(cbMethod);
        }
    }

    class CaesarPanel extends JPanel {
        private JLabel lblShift;
        private JFormattedTextField tfShift;
        private NumberFormatter formatter;

        public CaesarPanel() {
            setLayout(new FlowLayout(FlowLayout.LEFT));

            lblShift = new JLabel("Giá trị dịch chuyển");
            add(lblShift);

            NumberFormat intFormat = NumberFormat.getIntegerInstance();
            formatter = new NumberFormatter(intFormat);
            formatter.setValueClass(Integer.class);
            formatter.setMinimum(1);
            formatter.setMaximum(language.getAlphabetSize());
            formatter.setAllowsInvalid(false);
            formatter.setCommitsOnValidEdit(true);

            tfShift = new JFormattedTextField(formatter);
            tfShift.setColumns(3);
            tfShift.setValue(3);
            add(tfShift);
        }

        public void update() {
            formatter.setMaximum(language.getAlphabetSize());
        }
    }

    class SubstitutionPanel extends JPanel {
        private JLabel lblMapKey;
        private JTextField tfMapKey;
        private JButton btnGenMapKey, btnImportMapKey;

        public SubstitutionPanel() {
            setLayout(new BoxLayout(SubstitutionPanel.this, BoxLayout.Y_AXIS));
            JPanel groupPanel = new JPanel(new BorderLayout());
            Dimension d = groupPanel.getPreferredSize();
            groupPanel.setPreferredSize(new Dimension((int) d.getWidth(), 20));
            groupPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
            lblMapKey = new JLabel("  Khóa");
            lblMapKey.setPreferredSize(new Dimension(110, 20));
            tfMapKey = new JTextField("");
            tfMapKey.setEditable(false);
            groupPanel.add(lblMapKey, BorderLayout.WEST);
            groupPanel.add(tfMapKey, BorderLayout.CENTER);
            add(groupPanel);

            groupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
            btnGenMapKey = new JButton("Tạo");
            btnImportMapKey = new JButton("Chèn");
            groupPanel.add(Box.createHorizontalStrut(100));
            groupPanel.add(btnGenMapKey);
            groupPanel.add(btnImportMapKey);
            add(groupPanel);
        }
    }

    class AffinePanel extends JPanel {
        private JLabel lblKeyA, lblKeyB, lblModule;
        private JFormattedTextField tfKeyA, tfKeyB;
        private NumberFormatter formatterKeyA, formatterKeyB;
        private JTextField tfModulo;

        public AffinePanel() {
            setLayout(new BoxLayout(AffinePanel.this, BoxLayout.Y_AXIS));

            JPanel groupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            Dimension d = groupPanel.getPreferredSize();
            groupPanel.setPreferredSize(new Dimension((int) d.getWidth(), 25));
            groupPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
            lblKeyA = new JLabel("Giá trị a");
            lblKeyA.setPreferredSize(new Dimension(100 , 20));

            NumberFormat intFormat = NumberFormat.getIntegerInstance();
            formatterKeyA = new NumberFormatter(intFormat);
            formatterKeyA.setValueClass(Integer.class);
            formatterKeyA.setMinimum(1);
            formatterKeyA.setAllowsInvalid(false);
            formatterKeyA.setCommitsOnValidEdit(true);

            tfKeyA = new JFormattedTextField(formatterKeyA);
            tfKeyA.setColumns(10);
            tfKeyA.setValue(7);
            groupPanel.add(lblKeyA);
            groupPanel.add(tfKeyA);
            add(groupPanel);

            groupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            groupPanel.setPreferredSize(new Dimension((int) d.getWidth(), 25));
            groupPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
            lblKeyB = new JLabel("Giá trị b");
            lblKeyB.setPreferredSize(new Dimension(100, 20));

            formatterKeyB = new NumberFormatter(intFormat);
            formatterKeyB.setValueClass(Integer.class);
            formatterKeyB.setMinimum(1);
            formatterKeyB.setAllowsInvalid(false);
            formatterKeyB.setCommitsOnValidEdit(true);

            tfKeyB = new JFormattedTextField(formatterKeyB);
            tfKeyB.setColumns(10);
            tfKeyB.setValue(3);
            groupPanel.add(lblKeyB);
            groupPanel.add(tfKeyB);
            add(groupPanel);

            groupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            lblModule = new JLabel("Giá trị n");
            lblModule.setPreferredSize(new Dimension(100 , 20));
            tfModulo = new JTextField(String.valueOf(language.getAlphabetSize()));
            tfModulo.setPreferredSize(new Dimension(113, 20));
            tfModulo.setEditable(false);
            groupPanel.add(lblModule);
            groupPanel.add(tfModulo);
            add(groupPanel);
        }

        public void update() {
            tfModulo.setText(String.valueOf(language.getAlphabetSize()));
        }
    }
}