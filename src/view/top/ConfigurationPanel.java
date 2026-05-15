package view.top;

import controller.EncryptionController;
import controller.strategy.ModernSymmetricControllerStrategy;
import model.ILanguageModel;
import model.Language;

import javax.swing.*;
import java.awt.*;

public class ConfigurationPanel extends JPanel {
    private CardLayout layout;
    private TraditionalSymmetricCard traditionalSymmetricCard;
    private SymmetricCard symmetricCard;
    private AsymmetricCard asymmetricCard;
    private HashCard hashCard;
    private ILanguageModel language;

    public ConfigurationPanel() {
        layout = new CardLayout();
        setLayout(layout);

        language = Language.getInstance();
        traditionalSymmetricCard = new TraditionalSymmetricCard(language);
        language.addObserver(traditionalSymmetricCard);

        symmetricCard = new SymmetricCard();
        ModernSymmetricControllerStrategy.setView(symmetricCard);

        asymmetricCard = new AsymmetricCard();
        hashCard = new HashCard();
        add(traditionalSymmetricCard, "TSYM");
        add(symmetricCard, "SYM");
        add(asymmetricCard, "ASYM");
        add(hashCard, "HASH");
    }

    public void showTraditionalSymmetric() {
        layout.show(this, "TSYM");
    }

    public void showSymmetric() {
        layout.show(this, "SYM");
    }

    public void showAsymmetric() {
        layout.show(this, "ASYM");
    }

    public void showHash() {
        layout.show(this, "HASH");
    }
}
