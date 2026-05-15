package view.top;

import view.center.CenterPanel;

import javax.swing.*;
import java.awt.*;

public class TopPanel extends JPanel {
    private HeaderPanel headerPanel;
    private ConfigurationPanel configPanel;

    public TopPanel(CenterPanel centerPanel) {
        setLayout(new BorderLayout());

        headerPanel = new HeaderPanel(centerPanel);
        configPanel = new ConfigurationPanel();
        headerPanel.addCardEvent(configPanel);
        add(headerPanel, BorderLayout.NORTH);
        add(configPanel, BorderLayout.CENTER);
    }
}
