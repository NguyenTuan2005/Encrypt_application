package view.top;

import javax.swing.*;
import java.awt.*;

public class HashCard extends JPanel {
    private JLabel lblAlgorithm;
    private JComboBox<String> cbAlgorithm;

    public HashCard() {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        lblAlgorithm = new JLabel("Giải thuật băm");
        lblAlgorithm.setPreferredSize(new Dimension(100, 20));

        cbAlgorithm = new JComboBox<>(new String[]{"MD5"});
        add(lblAlgorithm);
        add(cbAlgorithm);
    }
}
