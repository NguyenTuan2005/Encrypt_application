package view.builders;

import javax.swing.*;

public class TextFieldBuilder extends JTextField {
    public TextFieldBuilder(String text) {
        super(text);
    }

    public static TextFieldBuilder builder(String text) {
        return new TextFieldBuilder(text);
    }

    public TextFieldBuilder text(String text) {
        this.setText(text);
        return this;
    }

    public TextFieldBuilder editable(boolean enabled) {
        this.setEditable(enabled);
        return this;
    }
}
