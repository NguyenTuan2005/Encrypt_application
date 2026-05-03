package view.bottom;

import view.builders.ScrollPaneBuilder;
import view.builders.TextAreaBuilder;
import view.builders.LabelBuilder;

import javax.swing.*;

public class ResultPanel extends JPanel{
    private JLabel lblResult;
    private JTextArea txtResult;
    private JScrollPane scrollPane;

    public ResultPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        lblResult = LabelBuilder.builder("Console result");
        add(lblResult);

        txtResult = TextAreaBuilder.builder()
                .size(100, 100)
                .editable(false);

        scrollPane = ScrollPaneBuilder.builder()
                .textArea(txtResult);
        add(scrollPane);
    }
}
