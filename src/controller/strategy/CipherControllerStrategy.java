package controller.strategy;

import enums.InputType;

public interface CipherControllerStrategy {
    void encrypt(String data) throws Exception;
    void decrypt(String data);
    void inputTypeChanged(InputType type);
}
