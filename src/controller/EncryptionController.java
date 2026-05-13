package controller;

import controller.strategy.CipherControllerStrategy;
import enums.InputType;

import java.util.HashMap;
import java.util.Map;

public class EncryptionController {
    private static EncryptionController instance;
    private CipherControllerStrategy currentController;
    private Map<String, CipherControllerStrategy> controllers;

    private EncryptionController() {
        controllers = new HashMap<>();
    }

    public static synchronized EncryptionController getInstance() {
        if (instance == null)
            instance = new EncryptionController();
        return instance;
    }

    public void encrypt(String data) throws Exception{
        currentController.encrypt(data);
    };

    public void decrypt(String data) {
        currentController.decrypt(data);
    }

    public void inputTypeChanged(InputType type) {
        for (CipherControllerStrategy controller : controllers.values()) {
            controller.inputTypeChanged(type);
        }
    };

    public void setCurrentController(String name) {
        this.currentController = controllers.get(name);
    }

    public void put(String name, CipherControllerStrategy controller) {
        this.controllers.put(name, controller);
    }

    public CipherControllerStrategy get(String name) {
        return this.controllers.get(name);
    }
}
