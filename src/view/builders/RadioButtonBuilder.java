package view.builders;

import javax.swing.*;

public class RadioButtonBuilder extends JRadioButton {
    public RadioButtonBuilder(String text) {
        super(text);
    }

    public static RadioButtonBuilder builder(String text) {
        return new RadioButtonBuilder(text);
    }

    public RadioButtonBuilder actionListener(Runnable action) {
        this.addActionListener((e) -> action.run());
        return this;
    }

    public RadioButtonBuilder selected(boolean isSelected) {
        this.setSelected(isSelected);
        return this;
    }
}
