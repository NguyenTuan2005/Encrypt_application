package controller.strategy;

import enums.InputType;

public interface CipherControllerStrategy {
    void encrypt(String data) throws Exception;
    void decrypt(String data) throws Exception;
    void inputTypeChanged(InputType type);
}
