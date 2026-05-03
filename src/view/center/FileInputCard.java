package view.center;

import view.builders.ButtonBuilder;
import view.builders.LabelBuilder;
import view.builders.TextFieldBuilder;

import javax.swing.*;
import java.awt.*;

public class FileInputCard extends JPanel {
    private JRadioButton rbText, rbFile;
    private ButtonGroup bgInputType;
    private CenterPanel parent;
    private JLabel lblFile;
    private JTextField txtFilePath;
    private JButton btnBrowseFile;

    public FileInputCard(CenterPanel parent) {
        this.parent = parent;
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout());
        lblFile = LabelBuilder.builder("Insert your file here");
        panel.add(lblFile, BorderLayout.WEST);
        panel.add(this.parent.panel, BorderLayout.EAST);
        add(panel, BorderLayout.NORTH);

        btnBrowseFile = ButtonBuilder.builder("Browse");
        add(btnBrowseFile);

        txtFilePath = TextFieldBuilder.builder("")
                .editable(false);
        add(txtFilePath);
    }
}
