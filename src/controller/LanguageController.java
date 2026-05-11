package controller;

import model.ILanguageModel;
import model.Language;

import javax.swing.*;

public class LanguageController implements ILanguage {
    private ILanguageModel language;

    public LanguageController() {
        language = Language.getInstance();
    }

    @Override
    public void setEnglishLanguage() {
        language.setEnglish();
    }

    @Override
    public void setVietnameseLanguage() {
        language.setVietnamese();
    }
}
