package view.top;

import controller.EncryptionController;
import controller.strategy.HashControllerStrategy;

import javax.swing.*;
import java.awt.*;

public class HashCard extends JPanel {
    private JLabel lblAlgorithm;
    private JComboBox<String> cbAlgorithm;
    private HashControllerStrategy controller = (HashControllerStrategy) EncryptionController.getInstance().get("Hash");

    public HashCard() {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        lblAlgorithm = new JLabel("Giải thuật băm");
        lblAlgorithm.setPreferredSize(new Dimension(100, 20));

        cbAlgorithm = new JComboBox<>(controller.getAlgorithms());
        add(lblAlgorithm);
        add(cbAlgorithm);

        addEvent();
    }

    private void addEvent() {
        cbAlgorithm.addActionListener(e -> {
            String selected = (String) cbAlgorithm.getSelectedItem();

            controller.setHashCipher(selected);
        });
    }
}
