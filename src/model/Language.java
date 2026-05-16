package model;

import java.util.*;

public class Language implements ILanguageModel {
    private static Language instance;
    private final List<Observer> observers;
    private int alphabetSize = ENGLISH_ALPHABET_SIZE;
    private String currentAlphabet = "ENGLISH";

    private Language() {
        observers = new ArrayList<>();
    }

    public static synchronized Language getInstance() {
        if (instance == null)
            instance = new Language();
        return instance;
    }

    @Override
    public void setEnglish() {
        alphabetSize = ENGLISH_ALPHABET_SIZE;
        currentAlphabet = "ENGLISH";
        notifyObservers();
    }

    @Override
    public void setVietnamese() {
        alphabetSize = VIETNAM_ALPHABET_SIZE;
        currentAlphabet = "VIETNAM";
        notifyObservers();
    }

    @Override
    public int getAlphabetSize() {
        return alphabetSize;
    }

    @Override
    public void addObserver(Observer observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    public boolean contains(char c) {
        switch (currentAlphabet) {
            case "ENGLISH": {
                return ENGLISH_ALPHABET.indexOf(c) != -1;
            }
            case "VIETNAM": {
                return VIETNAM_ALPHABET.indexOf(c) != -1;
            }
        }
        return false;
    }

    public boolean containInvalidAlphabet(String plainText) {
        plainText = plainText.toUpperCase(Locale.ROOT);
        for (char c : plainText.toCharArray()) {
            if (!Character.isLetter(c)) continue;
            return !contains(c);
        }
        return false;
    }

    public int getIndexOf(char c) {
        switch (currentAlphabet) {
            case "ENGLISH": {
                return ENGLISH_ALPHABET.indexOf(c);
            }
            case "VIETNAM": {
                return VIETNAM_ALPHABET.indexOf(c);
            }
        }
        return -1;
    }

    public char getCharAt(int index) {
        switch (currentAlphabet) {
            case "ENGLISH": {
                return ENGLISH_ALPHABET.charAt(index);
            }
            case "VIETNAM": {
                return VIETNAM_ALPHABET.charAt(index);
            }
        }
        return ' ';
    }

    public char getDigitAt(int index) {
        return NUMBER_VALUE.charAt(index);
    }

    public int getIndexDigitOf(char c) {
        return NUMBER_VALUE.indexOf(c);
    }
}
