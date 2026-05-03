package view.bottom;

import view.builders.ButtonBuilder;

import javax.swing.*;
import java.awt.*;

public class ActionPanel extends JPanel {
    private JButton btnEncrypt, btnDecrypt;

    public ActionPanel() {
        setLayout(new FlowLayout());
        btnEncrypt = ButtonBuilder.builder("Encrypt");
        btnDecrypt = ButtonBuilder.builder("Decrypt");

        add(btnEncrypt);
        add(btnDecrypt);
    }
}
