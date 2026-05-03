package view.center;

import view.builders.*;

import javax.swing.*;
import java.awt.*;

public class TextInputCard extends JPanel {
    private CenterPanel parent;
    private JLabel lblText;
    private JTextArea txtInput;
    private JScrollPane scrollPane;

    public TextInputCard(CenterPanel parent) {
        this.parent = parent;
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout());
        lblText = LabelBuilder.builder("Input your text");
        panel.add(lblText, BorderLayout.WEST);
        panel.add(this.parent.panel, BorderLayout.EAST);
        add(panel, BorderLayout.NORTH);

        txtInput = TextAreaBuilder.builder()
                .size(100, 100);

        scrollPane = ScrollPaneBuilder.builder()
                .textArea(txtInput);
        add(scrollPane, BorderLayout.CENTER);
    }
}
