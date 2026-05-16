package cipher.symmetric.tradition;

import cipher.TextCipher;
import model.Language;

import java.io.File;
import java.io.IOException;

public abstract class TraditionSymmetricCipher implements TextCipher {
    protected Language language;

    public TraditionSymmetricCipher() {
        this.language = Language.getInstance();
    }

    public abstract void updateConfig(String... args);

    public String genKey() {
        return null;
    }

    public String exportKey(File des) throws IOException {
        return "";
    }

    public String importKey(File src) throws IOException {
        return "";
    }

    public String filterValid(String text) {
        if (text == null) return "";
        StringBuilder filtered = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c) && language.contains(Character.toUpperCase(c))) {
                filtered.append(c);
            }
        }
        return filtered.toString();
    }
}
