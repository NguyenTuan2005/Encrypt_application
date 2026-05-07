package view.center;

import javax.swing.*;
import java.awt.*;

public class ButtonGroupPanel extends JPanel {
    private JRadioButton rbText, rbFile;
    private ButtonGroup bgInputType;

    public ButtonGroupPanel() {
        setLayout(new FlowLayout());
        rbText = new JRadioButton("Text");
        rbFile = new JRadioButton("File");
        bgInputType = new ButtonGroup();

        bgInputType.add(rbText);
        bgInputType.add(rbFile);
        add(rbText);
        add(rbFile);
    }

    public void addActionRbText(Runnable runnable) {
        rbFile.setSelected(true);
        this.rbText.addActionListener((e) -> {
            rbFile.setSelected(true);
            runnable.run();
        });
    }

    public void addActionRbFile(Runnable runnable) {
        rbText.setSelected(true);
        this.rbFile.addActionListener((e) -> {
            rbText.setSelected(true);
            runnable.run();
        });
    }
}
