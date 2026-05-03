package view.center;

import view.builders.ButtonGroupBuilder;
import view.builders.RadioButtonBuilder;

import javax.swing.*;
import java.awt.*;

public class CenterPanel extends JPanel {
    private JRadioButton rbText, rbFile;
    private ButtonGroup bgInputType;
    private CardLayout layout;
    private TextInputCard textInputCard;
    private FileInputCard fileInputCard;
    JPanel panel;

    public CenterPanel() {
        layout = new CardLayout();
        setLayout(layout);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        rbText = RadioButtonBuilder.builder("Text")
                .actionListener(CenterPanel.this::showText)
                .selected(true);
        rbFile = RadioButtonBuilder.builder("File")
                .actionListener(CenterPanel.this::showFile);
        bgInputType = ButtonGroupBuilder.builder();

        bgInputType.add(rbText);
        bgInputType.add(rbFile);

        panel.add(rbText);
        panel.add(rbFile);

        textInputCard = new TextInputCard(this);
        fileInputCard = new FileInputCard(this);

        add(textInputCard, "TEXT");
        add(fileInputCard, "FILE");
    }

    public void showText() {
        layout.show(this, "TEXT");
    }

    public void showFile() {
        layout.show(this, "FILE");
    }
}
