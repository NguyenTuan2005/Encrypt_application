package cipher.symmetric.tradition;

import cipher.TextCipher;
import model.Language;

public abstract class TraditionSymmetricCipher implements TextCipher {
    protected Language language;

    public TraditionSymmetricCipher() {
        this.language = Language.getInstance();
    }

    public abstract void updateConfig(String... args);
}
