package view.top;

import javax.swing.*;
import java.awt.*;

public class HeaderPanel extends JPanel {
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem symmetricItem, asymmetricItem, hashItem, enItem, vnItem;

    public HeaderPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        menuBar = new JMenuBar();
        menu = new JMenu("Loại mã hóa");
        symmetricItem = new JMenuItem("Mã hóa đối xứng");
        symmetricItem.setSelected(true);
        menu.add(symmetricItem);

        asymmetricItem = new JMenuItem("Mã hóa bất đối xứng");
        menu.add(asymmetricItem);

        hashItem = new JMenuItem("Hàm băm");
        menu.add(hashItem);
        menuBar.add(menu);

        menu = new JMenu("Ngôn ngữ mã hóa");
        enItem = new JMenuItem("Tiếng Anh");
        enItem.setSelected(true);
        menu.add(enItem);

        vnItem = new JMenuItem("Tiếng Việt");
        menu.add(vnItem);
        menuBar.add(menu);
        add(menuBar);
    }

    public void addCardEvent(ConfigurationPanel configPanel) {
        symmetricItem.addActionListener((e) -> configPanel.showSymmetric());
        asymmetricItem.addActionListener((e) -> configPanel.showAsymmetric());
        hashItem.addActionListener((e) -> configPanel.showHash());
    }
}
