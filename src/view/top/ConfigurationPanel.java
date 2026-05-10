package view.top;

import javax.swing.*;
import java.awt.*;

public class ConfigurationPanel extends JPanel {
    private CardLayout layout;
    private SymmetricCard symmetricCard;
    private AsymmetricCard asymmetricCard;
    private HashCard hashCard;

    public ConfigurationPanel() {
        layout = new CardLayout();
        setLayout(layout);

        symmetricCard = new SymmetricCard();
        asymmetricCard = new AsymmetricCard();
        hashCard = new HashCard();
        add(symmetricCard, "SYM");
        add(asymmetricCard, "ASYM");
        add(hashCard, "HASH");
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
