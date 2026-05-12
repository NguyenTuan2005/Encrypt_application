package view.top;

import controller.ILanguage;
import controller.LanguageController;
import model.ILanguageModel;
import model.Language;

import javax.swing.*;
import java.awt.*;

public class HeaderPanel extends JPanel {
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem symmetricTraditionalItem,symmetricModernItem, asymmetricItem, hashItem, enItem, vnItem;

    public HeaderPanel() {

        setBackground(Color.BLACK);
        setLayout(new FlowLayout(FlowLayout.LEFT));

        menuBar = new JMenuBar();
        menu = new JMenu("Loại mã hóa");

        symmetricTraditionalItem = new JMenuItem("Mã hóa đối xứng cổ điển");
        symmetricTraditionalItem.setSelected(true);
        menu.add(symmetricTraditionalItem);

        symmetricModernItem = new JMenuItem("Mã hóa đối xứng hiện đại");
        menu.add(symmetricModernItem);

        asymmetricItem = new JMenuItem("Mã hóa bất đối xứng");
        menu.add(asymmetricItem);

        hashItem = new JMenuItem("Hàm băm");
        menu.add(hashItem);
        menuBar.add(menu);

        menu = new JMenu("Ngôn ngữ hỗ trợ");
        enItem = new JMenuItem("Tiếng Anh");
        enItem.setSelected(true);
        menu.add(enItem);

        vnItem = new JMenuItem("Tiếng Việt");
        menu.add(vnItem);
        menuBar.add(menu);
        add(menuBar);

        addLanguageEvent(new LanguageController());
    }

    public void addCardEvent(ConfigurationPanel configPanel) {
        symmetricTraditionalItem.addActionListener(e -> configPanel.showTraditionalSymmetric());
        symmetricModernItem.addActionListener(e -> configPanel.showSymmetric());
        asymmetricItem.addActionListener(e -> configPanel.showAsymmetric());
        hashItem.addActionListener(e -> configPanel.showHash());
    }

    public void addLanguageEvent(ILanguage languageController) {
        enItem.addActionListener(e -> languageController.setEnglishLanguage());
        vnItem.addActionListener(e -> languageController.setVietnameseLanguage());
    }
}
