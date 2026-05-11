package model;

import java.util.ArrayList;
import java.util.List;

public class Language implements ILanguageModel, Observable {
    private static Language instance;
    private final List<Observer> observers;
    private int alphabetSize = ENGLISH_ALPHABET;

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
        alphabetSize = ENGLISH_ALPHABET;
        notifyObservers();
    }

    @Override
    public void setVietnamese() {
        alphabetSize = VIETNAM_ALPHABET;
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
}
