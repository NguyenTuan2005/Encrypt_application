package view.top;

import javax.swing.*;
import java.awt.*;

public class TopPanel extends JPanel {
    private HeaderPanel headerPanel;
    private ConfigurationPanel configPanel;

    public TopPanel() {
        setLayout(new BorderLayout());

        headerPanel = new HeaderPanel();
        configPanel = new ConfigurationPanel();
        headerPanel.addCardEvent(configPanel);
        add(headerPanel, BorderLayout.NORTH);
        add(configPanel, BorderLayout.CENTER);
    }
}
