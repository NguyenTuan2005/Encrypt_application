package model;

public interface ILanguageModel extends Observable{
    int ENGLISH_ALPHABET = 26;
    int VIETNAM_ALPHABET = 255;

    void setEnglish();
    void setVietnamese();
    int getAlphabetSize();
}
