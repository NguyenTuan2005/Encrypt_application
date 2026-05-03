package view.builders;

import javax.swing.*;

public class ButtonBuilder extends JButton {

    public ButtonBuilder(String text) {
        super(text);
    }

    public static ButtonBuilder builder(String text) {
        return new ButtonBuilder(text);
    }

    public ButtonBuilder text(String text) {
        this.setText(text);
        return this;
    }
}
