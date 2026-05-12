package view;

import view.bottom.BottomPanel;
import view.center.CenterPanel;
import view.top.TopPanel;

import javax.swing.*;
import java.awt.*;

public class EncryptApplication extends JFrame {
    private TopPanel topPanel;
    private CenterPanel centerPanel;
    private BottomPanel bottomPanel;

    public EncryptApplication() {
        setTitle("Công cụ mật mã");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(750, 500);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        initial();
    }

    public void initial() {
        topPanel = new TopPanel();
        centerPanel = new CenterPanel();
        bottomPanel = new BottomPanel();
        add(topPanel, BorderLayout.WEST);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(centerPanel, BorderLayout.CENTER);
        rightPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(rightPanel, BorderLayout.CENTER);
    }

    public void showApp() {
        setVisible(true);
    }
}
