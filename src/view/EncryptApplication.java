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
        setResizable(false);
        setSize(600, 600);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        initial();
    }

    public void initial() {
        topPanel = new TopPanel();
        centerPanel = new CenterPanel();
        bottomPanel = new BottomPanel();
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void showApp() {
        setVisible(true);
    }
}
