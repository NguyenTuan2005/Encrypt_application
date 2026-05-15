package controller;

import model.ILanguageModel;
import model.Language;

public class LanguageController {
    private ILanguageModel language;

    public LanguageController() {
        language = Language.getInstance();
    }

    public void setEnglishLanguage() {
        language.setEnglish();
    }

    public void setVietnameseLanguage() {
        language.setVietnamese();
    }
}
