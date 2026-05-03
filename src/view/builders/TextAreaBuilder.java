package view.builders;

import javax.swing.*;
import java.awt.*;

public class TextAreaBuilder extends JTextArea {
    public static TextAreaBuilder builder() {
        return new TextAreaBuilder();
    }

    public TextAreaBuilder size(int width, int height) {
        this.setPreferredSize(new Dimension(width, height));
        return this;
    }

    public TextAreaBuilder editable(boolean enabled) {
        this.setEditable(enabled);
        return this;
    }
}
