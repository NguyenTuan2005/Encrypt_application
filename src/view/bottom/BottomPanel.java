package view.bottom;

import view.utils.ColorView;

import javax.swing.*;
import java.awt.*;

public class BottomPanel extends JPanel {
    private ActionPanel actionPanel;
    private ResultPanel resultPanel;

    public BottomPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(ColorView.BORDER_COLOR));

        actionPanel = new ActionPanel();
        add(actionPanel, BorderLayout.NORTH);

        resultPanel = new ResultPanel();
        add(resultPanel, BorderLayout.CENTER);
    }
}
