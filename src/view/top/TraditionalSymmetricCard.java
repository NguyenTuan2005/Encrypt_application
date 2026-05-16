package view.top;

import cipher.symmetric.tradition.TraditionSymmetricCipher;
import controller.EncryptionController;
import controller.strategy.TraditionSymmetricControllerStrategy;
import model.ILanguageModel;
import model.Observer;
import view.bottom.BottomPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.*;
import java.awt.*;
import java.io.File;
import java.text.NumberFormat;

public class TraditionalSymmetricCard extends JPanel implements Observer {
    private CardLayout layout;
    private Header header;
    private JPanel cardPanel;
    private CaesarPanel caesarPanel;
    private SubstitutionPanel substitutionPanel;
    private AffinePanel affinePanel;
    private VigenerePanel vigenerePanel;
    private HillPanel hillPanel;
    private PermutationPanel permutationPanel;
    private ILanguageModel language;
    private TraditionSymmetricControllerStrategy controller = (TraditionSymmetricControllerStrategy) EncryptionController.getInstance().get("Tradition");
    private CurrentPanel currentPanel;

    public TraditionalSymmetricCard(ILanguageModel language) {
        this.language = language;
        controller.setView(this);
        setLayout(new BorderLayout());
        header = new Header();
        add(header, BorderLayout.NORTH);

        cardPanel = new JPanel();
        layout = new CardLayout();
        cardPanel.setLayout(layout);
        caesarPanel = new CaesarPanel();
        substitutionPanel = new SubstitutionPanel();
        affinePanel = new AffinePanel();
        vigenerePanel = new VigenerePanel();
        hillPanel = new HillPanel();
        permutationPanel = new PermutationPanel();
        currentPanel = caesarPanel;

        cardPanel.add(caesarPanel, "CAESAR");
        cardPanel.add(substitutionPanel, "SUBSTITUTION");
        cardPanel.add(affinePanel, "AFFINE");
        cardPanel.add(vigenerePanel, "VIGENERE");
        cardPanel.add(hillPanel, "HILL");
        cardPanel.add(permutationPanel, "PERMUTATION");
        add(cardPanel, BorderLayout.CENTER);
    }

    public void showCard(String cardName) {
        layout.show(cardPanel, cardName);
    }

    @Override
    public void update() {
        caesarPanel.updateNumberFormat();
        affinePanel.updateNumberFormat();
        hillPanel.updateNumberFormat();
    }

    public void updateConfig(TraditionSymmetricCipher traditionSymmetricCipher) {
        this.currentPanel.updateConfig(traditionSymmetricCipher);
    }

    public interface CurrentPanel {
        void updateConfig(TraditionSymmetricCipher cipher);
    }

    public class Header extends JPanel {
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
                        currentPanel = caesarPanel;
                        break;
                    case "Mã hóa thay thế":
                        showCard("SUBSTITUTION");
                        currentPanel = substitutionPanel;
                        break;
                    case "Affine":
                        showCard("AFFINE");
                        break;
                    case "Vigenere":
                        showCard("VIGENERE");
                        currentPanel = vigenerePanel;
                        break;
                    case "Hill":
                        showCard("HILL");
                        break;
                    case "Mã hóa hoán vị":
                        showCard("PERMUTATION");
                        currentPanel = permutationPanel;
                        break;
                }
                controller.setTraditionSymmetricCipher(selected);
            });
            add(cbMethod);
        }
    }

    public class CaesarPanel extends JPanel implements CurrentPanel{
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
            formatter.setMaximum(language.getAlphabetSize() - 1);
            formatter.setAllowsInvalid(false);
            formatter.setCommitsOnValidEdit(true);

            tfShift = new JFormattedTextField(formatter);
            tfShift.setColumns(3);
            tfShift.setValue(3);
            add(tfShift);
        }

        public void updateNumberFormat() {
            formatter.setMaximum(language.getAlphabetSize() - 1);
        }

        @Override
        public void updateConfig(TraditionSymmetricCipher cipher) {
            cipher.updateConfig(tfShift.getText());
        }
    }

    public class SubstitutionPanel extends JPanel implements CurrentPanel {
        private JLabel lblMapKey;
        private JTextField tfMapKey;
        private JButton btnGenMapKey, btnImportMapKey, btnExportMapKey;
        private JFileChooser fileChooser;

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
            btnExportMapKey = new JButton("Xuất");
            groupPanel.add(Box.createHorizontalStrut(100));
            groupPanel.add(btnGenMapKey);
            groupPanel.add(btnImportMapKey);
            groupPanel.add(btnExportMapKey);
            add(groupPanel);

            addEvent();
        }

        private void addEvent() {
            fileChooser = new JFileChooser();
            btnGenMapKey.addActionListener(e -> {
                tfMapKey.setText(controller.genKey());
            });

            btnExportMapKey.addActionListener(e -> {
                int result = fileChooser.showSaveDialog(SubstitutionPanel.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        BottomPanel.updateResult(controller.exportKey(file));
                    } catch (Exception ex) {
                        BottomPanel.updateResult(ex.getMessage());
                    }
                }
            });

            btnImportMapKey.addActionListener(e -> {
                int result = fileChooser.showOpenDialog(SubstitutionPanel.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        tfMapKey.setText(controller.importKey(file));
                    } catch (Exception ex) {
                        BottomPanel.updateResult(ex.getMessage());
                    }
                }
            });
        }

        @Override
        public void updateConfig(TraditionSymmetricCipher cipher) {
            cipher.updateConfig(tfMapKey.getText());
        }
    }

    public class AffinePanel extends JPanel {
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

        public void updateNumberFormat() {
            tfModulo.setText(String.valueOf(language.getAlphabetSize()));
        }
    }

    public class VigenerePanel extends JPanel implements CurrentPanel {
        private JLabel lblKeyword;
        private JTextField tfKeyword;

        public VigenerePanel() {
            setLayout(new BoxLayout(VigenerePanel.this, BoxLayout.Y_AXIS));
            JPanel groupPanel = new JPanel(new BorderLayout(10, 0));
            Dimension d = groupPanel.getPreferredSize();
            groupPanel.setPreferredSize(new Dimension((int) d.getWidth(), 30));
            groupPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
            lblKeyword = new JLabel("  Từ khóa");
            lblKeyword.setPreferredSize(new Dimension(100, 20));

            tfKeyword = new JTextField("");
            AbstractDocument document = (AbstractDocument) tfKeyword.getDocument();
            document.setDocumentFilter(new DocumentFilter() {
                @Override
                public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                    String filtered = controller.filterValid(string);
                    if (!filtered.isEmpty()) {
                        super.insertString(fb, offset, filtered, attr);
                    }
                }

                @Override
                public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                    String filtered = controller.filterValid(text);
                    int textlength = filtered.length();
                    int newLength = fb.getDocument().getLength() - length + textlength;
                    if (newLength >= 0) {
                        super.replace(fb, offset, length, filtered, attrs);
                    }
                }
            });
            groupPanel.add(lblKeyword, BorderLayout.WEST);
            groupPanel.add(tfKeyword, BorderLayout.CENTER);
            add(groupPanel);
        }

        @Override
        public void updateConfig(TraditionSymmetricCipher cipher) {
            cipher.updateConfig(tfKeyword.getText());
        }
    }

    public class HillPanel extends JPanel {
        private JLabel lblKeySize, lblMatrix;
        private JComboBox<String> cbKeySize;
        private JTable matrix;
        private DefaultTableModel matrixModel;
        private NumberFormatter formatterMatrix;
        private JButton btnGenerateMatrix, btnImportMatrix, btnExportMatrix;

        public HillPanel() {
            setLayout(new BoxLayout(HillPanel.this, BoxLayout.Y_AXIS));
            JPanel groupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            Dimension d = groupPanel.getPreferredSize();
            groupPanel.setPreferredSize(new Dimension((int) d.getWidth(), 30));
            groupPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
            lblKeySize = new JLabel("Kích cỡ ma trận (m x m)");
            cbKeySize = new JComboBox<>(new String[]{"2x2", "3x3"});
            groupPanel.add(lblKeySize);
            groupPanel.add(cbKeySize);
            add(groupPanel);

            groupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            d = groupPanel.getPreferredSize();
            groupPanel.setPreferredSize(new Dimension((int) d.getWidth(), 30));
            groupPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
            lblMatrix = new JLabel("Ma trận");
            lblMatrix.setPreferredSize(new Dimension(100, 20));

            btnGenerateMatrix = new JButton("Tạo");
            btnImportMatrix = new JButton("Chèn");
            btnExportMatrix = new JButton("Xuất");
            groupPanel.add(lblMatrix);
            groupPanel.add(btnGenerateMatrix);
            groupPanel.add(btnImportMatrix);
            groupPanel.add(btnExportMatrix);
            add(groupPanel);

            groupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 10));
            matrixModel = new DefaultTableModel(2, 2) {
                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    return Integer.class;
                }
            };
            matrix = new JTable(matrixModel);

            NumberFormat intFormat = NumberFormat.getIntegerInstance();
            intFormat.setGroupingUsed(false);
            formatterMatrix = new NumberFormatter(intFormat);
            formatterMatrix.setValueClass(Integer.class);
            formatterMatrix.setMinimum(0);
            formatterMatrix.setMaximum(language.getAlphabetSize() - 1);
            formatterMatrix.setAllowsInvalid(false);
            formatterMatrix.setCommitsOnValidEdit(true);

            DefaultCellEditor editor = getDefaultCellEditor();

            matrix.setDefaultEditor(Integer.class, editor);
            groupPanel.add(Box.createHorizontalStrut(100));
            groupPanel.add(matrix);
            add(groupPanel);
            addMatrixEvent();
        }

        private DefaultCellEditor getDefaultCellEditor() {
            JFormattedTextField formattedTextField = new JFormattedTextField(formatterMatrix);

            return new DefaultCellEditor(formattedTextField) {
                @Override
                public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                    formattedTextField.setValue(value != null ? value : 0);

                    SwingUtilities.invokeLater(formattedTextField::selectAll);

                    return formattedTextField;
                }

                @Override
                public Object getCellEditorValue() {
                    return formattedTextField.getValue();
                }
            };
        }

        public void addMatrixEvent() {
            cbKeySize.addActionListener(e -> {
                String selected = (String) cbKeySize.getSelectedItem();
                DefaultTableModel model = (DefaultTableModel) matrix.getModel();

                switch (selected) {
                    case "2x2":
                        model.setRowCount(2);
                        model.setColumnCount(2);
                        break;
                    case "3x3":
                        model.setRowCount(3);
                        model.setColumnCount(3);
                        break;
                }

                matrix.revalidate();
                matrix.repaint();
            });
        }

        public void updateNumberFormat() {
            formatterMatrix.setMaximum(language.getAlphabetSize() - 1);

            for (int i = 0; i < matrixModel.getRowCount(); i++) {
                for (int j = 0; j < matrixModel.getColumnCount(); j++) {
                    if (matrixModel.getValueAt(i, j) == null) continue;
                    if ((int) matrixModel.getValueAt(i, j) > (language.getAlphabetSize() - 1))
                        matrixModel.setValueAt(language.getAlphabetSize() - 1, i, j);
                }
            }
        }
    }

    public class PermutationPanel extends JPanel implements CurrentPanel {
        private JLabel lblBlockSize, lblKey, lblPaddingChar;
        private JFormattedTextField tfBlockSize;
        private NumberFormatter formatterBlockSize;
        private JTextField tfKey, tfPaddingChar;

        public PermutationPanel() {
            setLayout(new BoxLayout(PermutationPanel.this, BoxLayout.Y_AXIS));

            JPanel groupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            Dimension d = groupPanel.getPreferredSize();
            groupPanel.setPreferredSize(new Dimension((int) d.getWidth(), 25));
            groupPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
            lblBlockSize = new JLabel("Kích thước khối");
            lblBlockSize.setPreferredSize(new Dimension(100, 20));

            NumberFormat intFormat = NumberFormat.getIntegerInstance();
            formatterBlockSize = new NumberFormatter(intFormat);
            formatterBlockSize.setValueClass(Integer.class);
            formatterBlockSize.setMinimum(1);
            formatterBlockSize.setMaximum(10);
            formatterBlockSize.setAllowsInvalid(false);
            formatterBlockSize.setCommitsOnValidEdit(true);
            tfBlockSize = new JFormattedTextField(formatterBlockSize);
            tfBlockSize.setColumns(3);
            tfBlockSize.setValue(6);
            groupPanel.add(lblBlockSize);
            groupPanel.add(tfBlockSize);
            add(groupPanel);

            groupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            d = groupPanel.getPreferredSize();
            groupPanel.setPreferredSize(new Dimension((int) d.getWidth(), 25));
            groupPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
            lblKey = new JLabel("Khóa");
            lblKey.setPreferredSize(new Dimension(100, 20));

            tfKey = new JTextField("");
            tfKey.setColumns(12);
            AbstractDocument document = (AbstractDocument) tfKey.getDocument();
            document.setDocumentFilter(new DocumentFilter() {
                @Override
                public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                    String filtered = controller.filterValid(string);
                    int blockSize = Integer.parseInt(tfBlockSize.getText());
                    if (filtered.length() <= blockSize) {
                        super.insertString(fb, offset, filtered, attr);
                    }
                }

                @Override
                public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                    String filtered = controller.filterValid(text);
                    int blockSize = Integer.parseInt(tfBlockSize.getText());
                    int currentLength = fb.getDocument().getLength() - length;
                    int availableSpace = blockSize - currentLength;

                    if (availableSpace > 0 && !filtered.isEmpty()) {
                        String subFiltered = filtered.substring(0, Math.min(filtered.length(), availableSpace));
                        super.replace(fb, offset, length, subFiltered, attrs);
                    }
                }
            });
            groupPanel.add(lblKey);
            groupPanel.add(tfKey);
            add(groupPanel);

            groupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            d = groupPanel.getPreferredSize();
            groupPanel.setPreferredSize(new Dimension((int) d.getWidth(), 25));
            groupPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
            lblPaddingChar = new JLabel("Ký tự đệm");
            lblPaddingChar.setPreferredSize(new Dimension(100, 20));

            tfPaddingChar = new JTextField("X");
            tfPaddingChar.setPreferredSize(new Dimension(50, 25));
            document = (AbstractDocument) tfPaddingChar.getDocument();
            document.setDocumentFilter(new DocumentFilter() {
                @Override
                public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                    int currentLength = fb.getDocument().getLength();
                    if (currentLength + string.length() <= 1) {
                        super.insertString(fb, offset, string, attr);
                    }
                }

                @Override
                public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                    int textLength = (text != null) ? text.length() : 0;
                    int newLength = fb.getDocument().getLength() - length + textLength;
                    if (newLength <= 1) {
                        super.replace(fb, offset, length, text, attrs);
                    }
                }
            });
            groupPanel.add(lblPaddingChar);
            groupPanel.add(tfPaddingChar);
            add(groupPanel);
        }

        @Override
        public void updateConfig(TraditionSymmetricCipher cipher) {
            cipher.updateConfig(tfBlockSize.getText(), tfKey.getText(), tfPaddingChar.getText());
        }
    }
}