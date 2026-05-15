package controller.strategy;

import cipher.symmetric.tradition.TraditionSymmetricCipher;
import enums.InputType;

public class TraditionSymmetricControllerStrategy implements CipherControllerStrategy {
    private TraditionSymmetricCipher traditionSymmetricCipher;
    private InputType type = InputType.TEXT_TYPE;

    public TraditionSymmetricControllerStrategy() {
    }

    @Override
    public void encrypt(String data) throws Exception {

    }

    @Override
    public void decrypt(String data) throws Exception {

    }

    @Override
    public void inputTypeChanged(InputType type) {
    }
}
