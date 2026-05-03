package view.builders;

import javax.swing.*;

public class ScrollPaneBuilder extends JScrollPane {
    public static ScrollPaneBuilder builder() {
        return new ScrollPaneBuilder();
    }

    public ScrollPaneBuilder textArea(JTextArea jTextArea) {
        this.setViewportView(jTextArea);
        return this;
    }
}
