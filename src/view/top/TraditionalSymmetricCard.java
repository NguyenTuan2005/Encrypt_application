package view.top;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;

public class TraditionalSymmetricCard extends JPanel {
    private CardLayout layout;
    private Header header;
    private JPanel panel;
    private CaesarPanel caesarPanel;

    public TraditionalSymmetricCard() {
        setLayout(new BorderLayout());
        header = new Header();
        add(header, BorderLayout.NORTH);

        panel = new JPanel();
        layout = new CardLayout();
        panel.setLayout(layout);
        caesarPanel = new CaesarPanel();

        panel.add(caesarPanel, "CAESAR");
        add(panel, BorderLayout.CENTER);
    }

    public void showCard(String cardName) {
        layout.show(panel, cardName);
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
                }
            });
            add(cbMethod);
        }
    }

    class CaesarPanel extends JPanel {
        private JLabel lblShift;
        private JFormattedTextField tfShift;
        private NumberFormatter formatter;
        private int maxShift = 25;

        public CaesarPanel() {
            setLayout(new FlowLayout(FlowLayout.LEFT));

            lblShift = new JLabel("Giá trị dịch chuyển");
            add(lblShift);

            NumberFormat intFormat = NumberFormat.getIntegerInstance();
            formatter = new NumberFormatter(intFormat);
            formatter.setValueClass(Integer.class);
            formatter.setMinimum(1);
            formatter.setMaximum(maxShift);
            formatter.setAllowsInvalid(false);
            formatter.setCommitsOnValidEdit(true);

            tfShift = new JFormattedTextField(formatter);
            tfShift.setColumns(3);
            tfShift.setValue(3);
            add(tfShift);
        }

        public void setTfShift(int newMax) {
            maxShift = newMax;
            formatter.setMaximum(maxShift);
        }
    }
}