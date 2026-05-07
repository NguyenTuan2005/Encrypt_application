package view.center;

import javax.swing.*;
import java.awt.*;

public class CenterPanel extends JPanel {
    private CardLayout layout;
    private TextInputCard textInputCard;
    private FileInputCard fileInputCard;

    public CenterPanel() {
        layout = new CardLayout();
        setLayout(layout);

        textInputCard = new TextInputCard(this::showFile);
        fileInputCard = new FileInputCard(this::showText);

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
