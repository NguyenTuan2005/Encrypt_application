package view.builders;

import javax.swing.*;

public class LabelBuilder extends JLabel {
    public LabelBuilder(String text) {
        super(text);
    }

    public static LabelBuilder builder(String text){
        return new LabelBuilder(text);
    }
}
